package com.jdbc;

import java.lang.reflect.Field;  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.ResultSetMetaData;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.util.ArrayList;  
import java.util.HashMap;   
import java.util.List;  
import java.util.Map;  
  
import com.mysql.jdbc.Driver;  
  
public class JdbcUtils {  
  
	// 定义数据库的用户名  
    private final String USERNAME = "root";  
    // 定义数据库的密码  
    private final String PASSWORD = "root";  
    // 定义数据库的驱动信息  
    private final String DRIVER = "com.mysql.jdbc.Driver";  
    // 定义访问数据库的地址  
    private final String URL = "jdbc:mysql://localhost:3306/mydb";  
  
    // 定义访问数据库的连接  
    private Connection connection;  
    // 定义sql语句的执行对象  
    private PreparedStatement pstmt;  
    // 定义查询返回的结果集合  
    private ResultSet resultSet; 
      
    // 批处理
    private Statement stmt;  
  
    public JdbcUtils() {  
        // TODO Auto-generated constructor stub  
        try {  
            Class.forName(DRIVER);  
        } catch (ClassNotFoundException e) {  
            // TODO Auto-generated catch block  
            System.out.println("注册驱动失败");  
        }  
  
    }  
  
    //获得数据库连接
    public Connection getConnection() {  
  
        try {  
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);  
  
        } catch (Exception e) {  
            // TODO: handle exception  
            System.out.println("Connection exception !");  
        }  
  
        return connection;  
  
    }  
      
      

    public boolean deleteByBatch(String[] sql) throws SQLException{  
        boolean flag = false;  
        stmt = connection.createStatement();  
        if (sql!=null) { 
              
            for(int i = 0 ; i<sql.length ; i++){  
                stmt.addBatch(sql[i]);  
            }  
              
            int[] count = stmt.executeBatch();  
            if (count!=null) {  
                flag = true;  
            }  
              
        }     
        return flag;          
    }  
  

    public boolean updateByPreparedStatement(String sql, List<Object> params)  
            throws SQLException {  
        boolean flag = false;  
        int result = -1;//表示当用户执行增加删除和修改的操作影响的行数  
        int index = 1; // 表示 占位符 ，从1开始
        pstmt = connection.prepareStatement(sql);  
        if (params != null && !params.isEmpty()) {  
            for (int i = 0; i < params.size(); i++) {  
                pstmt.setObject(index++, params.get(i)); //填充占位符  
            }  
        }  
  
        result = pstmt.executeUpdate();  
        flag = result > 0 ? true : false;  
        return flag;  
  
    }  
  
  
    public  Map<String, Object> findSimpleResult(String sql, List<Object> params)  
            throws SQLException {  
        Map<String, Object> map = new HashMap<String, Object>();  
        pstmt = connection.prepareStatement(sql);  
        int index = 1;  
        if (params != null && !params.isEmpty()) {  
            for (int i = 0; i < params.size(); i++) {  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery(); // 返回查询结果 
  
        ResultSetMetaData metaData = pstmt.getMetaData(); //获取 结果中，一行所有列的结果
        int cols_len = metaData.getColumnCount(); // 获得列数
  
        while (resultSet.next()) {  
            for (int i = 0; i < cols_len; i++) {  
                String col_name = metaData.getColumnName(i + 1); // 获得第i列的字段名称 
                Object col_value = resultSet.getObject(col_name);// 返回 第i列的内容值 
                if (col_value == null) {  
                    col_value = "";  
                }  
                map.put(col_name, col_value);  
            }  
  
        }  

        return map;  
    }  
  

    public List<Map<String, Object>> findMoreResult(String sql,  
            List<Object> params) throws SQLException {  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        pstmt = connection.prepareStatement(sql);  
        int index = 1; 
        if (params != null && !params.isEmpty()) {  
            for (int i = 0; i < params.size(); i++) {  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery(); 
        ResultSetMetaData metaData = resultSet.getMetaData(); 
  
        while (resultSet.next()) {  
            Map<String, Object> map = new HashMap<String, Object>();  
            int cols_len = metaData.getColumnCount(); 
            for (int i = 0; i < cols_len; i++) {  
                String col_name = metaData.getColumnName(i + 1); 
                                                                    
                Object col_value = resultSet.getObject(col_name); 
                if (col_value == null) {  
                    col_value = "";  
                }  
  
                map.put(col_name, col_value);  
            }  
            list.add(map);  
        }  
  
        return list;  
  
    }  
  

    public <T> T findSimpleRefResult(String sql, List<Object> params,  
            Class<T> cls) throws Exception {  
        T resultObject = null;  
        int index = 1; // 表示占位符 
        pstmt = connection.prepareStatement(sql);  
        if (params != null && !params.isEmpty()) {  
            for (int i = 0; i < params.size(); i++) {  
                pstmt.setObject(index++, params.get(i)); 
            }  
        }  
        resultSet = pstmt.executeQuery(); // 返回查询结果集合  
  
        ResultSetMetaData metaData = resultSet.getMetaData(); //获得列的结果 
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {  
            resultObject = cls.newInstance(); // java反射机制
            for (int i = 0; i < cols_len; i++) {  
            	String col_name = metaData.getColumnName(i + 1); // 获取第i列的名称  
                Object col_value = resultSet.getObject(col_name); // 获取第i列的值   
                if (col_value == null) {  
                    col_value = "";  
                }  
                Field field = cls.getDeclaredField(col_name);  
                field.setAccessible(true);
                field.set(resultObject, col_value);  
            }  
  
        }  
  
        return resultObject;  
    }  
  

    public <T> List<T> findMoreRefResult(String sql, List<Object> params,  
            Class<T> cls) throws Exception {  
        List<T> list = new ArrayList<T>();  
        int index = 1; 
        pstmt = connection.prepareStatement(sql);  
        if (params != null && !params.isEmpty()) {  
            for (int i = 0; i < params.size(); i++) {  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery(); 
  
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount(); 
        while (resultSet.next()) {  
            T resultObject = cls.newInstance();  
            for (int i = 0; i < cols_len; i++) {  
                String col_name = metaData.getColumnName(i + 1); 
                Object col_value = resultSet.getObject(col_name); 
                if (col_value == null) {  
                    col_value = "";  
                }  
                Field field = cls.getDeclaredField(col_name);  
                field.setAccessible(true); // ��JavaBean�ķ���privateȨ��  
                field.set(resultObject, col_value);  
            }  
            list.add(resultObject);  
  
        }  
  
        return list;  
    }  
      

    public void releaseConn(){  
        if (resultSet!=null) {  
            try {  
                resultSet.close();  
            } catch (Exception e) {  
                // TODO: handle exception  
                e.printStackTrace();  
            }  
              
        }  
        if(stmt!=null){  
              
            try {  
                stmt.close();  
            } catch (Exception e) {  
                // TODO: handle exception  
                e.printStackTrace();  
            }  
        }  
        if (pstmt!=null) {  
            try {  
                pstmt.close();  
            } catch (Exception e) {  
                // TODO: handle exception  
                e.printStackTrace();  
            }  
        }  
        if (connection!=null) {  
            try {  
                connection.close();  
            } catch (Exception e) {  
                // TODO: handle exception  
                e.printStackTrace();  
            }  
        }  
    }  
  
      
} 