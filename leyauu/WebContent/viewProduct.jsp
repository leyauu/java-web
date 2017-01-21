    <%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>  
    <%  
    String path = request.getContextPath();  
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
    Map<String,Object> map = (Map<String,Object>)request.getAttribute("productMap");  
      
    %>  
      
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
    <html>  
      <head>  
        <base href="<%=basePath%>">  
          
        <title>查看产品</title>  
          
        <meta http-equiv="pragma" content="no-cache">  
        <meta http-equiv="cache-control" content="no-cache">  
        <meta http-equiv="expires" content="0">      
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">  
        <meta http-equiv="description" content="This is my page">  
      </head>  
        
      <body>  
       <% 
           request.setCharacterEncoding("utf-8");
      %>
      <div align="center">  
              
            <table width=60% style="margin:auto" border="0" cellspacing="1" >  
                  
                <tr style="border: solid 1px #a0c6e5; height: 20px;">  
                    <td height=100>  
                     <h1 align="center">产品信息</h1>     
                    </td>  
                </tr>  
				
                <tr style="border: solid 1px #a0c6e5; height: 20px;">  
                    <td>  
                        <table width = 99% border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;">  
                            <tr align="center" >  
                                <td width = 20% style="border: solid 1px #a0c6e5; height: 20px;">产品名称</td>  
                                <td width = 30% style="border: solid 1px #a0c6e5; height: 20px;"><%=map.get("proname") %></td>  
                                <td width = 20% style="border: solid 1px #a0c6e5; height: 20px;">产品价格</td>  
                                <td style="border: solid 1px #a0c6e5; height: 20px;"><%=map.get("proprice") %></td>  
                                  
                              
                            </tr>  
                            <tr align="center">  
                                <td style="border: solid 1px #a0c6e5; height: 20px;">产品产地</td>  
                                <td colspan=3 style="border: solid 1px #a0c6e5; height: 20px;"><%=map.get("proaddress") %></td>                      
                                  
                              
                            </tr>  
                            <tr align="center">  
                                <td style="border: solid 1px #a0c6e5; height: 20px;">产品图片</td>  
                                <td colspan=3 style="border: solid 1px #a0c6e5; height: 20px;"><img src="<%=path%>/upload/<%=map.get("proimage") %>"></td>                           
                              
                            </tr>  
                          
                          
                        </table>  
                    </td>  
                </tr>  
                <tr>  
                    <td align="center">  
                        <button type="button" onclick="javascript:location.href='<%=path %>/main.jsp'">确定</button>  
                        <button type="button" onclick="javascript:history.go(-1)">返回</button>  
                    </td>  
                </tr>  
              
            </table>  
              
          
      </div>  
      </body>  
    </html>  