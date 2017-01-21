<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
    <%  
    String path = request.getContextPath();  
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
    %>  
      
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
    <html>  
      
      <head>  
        <base href="<%=basePath%>">  
          
          
        <title>欢迎登录</title>  
        <meta http-equiv="pragma" content="no-cache">  
        <meta http-equiv="cache-control" content="no-cache">  
        <meta http-equiv="expires" content="0">      
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">  
        <meta http-equiv="description" content="This is my page">       
    <script type="text/javascript">  
    function login(){  
        var th = document.form1;  
        if(th.username.value==""){  
            alert("用户名不能为空！");  
            th.username.focus();  
            return;  
        }  
        if(th.pswd.value==""){  
            alert("密码不能为空！");  
            th.pswd.focus();  
            return;  
        }  
          
        th.action = "<%=path%>/LoginAction";  
        th.submit();  
      
      
    }  
      
    </script>  
          
      </head>  
        
      <body>  
       
       <section class="container"> 
       <div class="login" >
         <h1>用户登录</h1>     
         <form name="form1" action="" method="post">  
           <p><input type="text" name="username" value=""  placeholder="用户名"></p>
           <p><input type="password" name="pswd" value=""  placeholder="密码"></p>
           <p class="submit"><button type="button" name="" value="" onclick="login()">登录</button>            
		   <button type="button" name="" value="" onclick="javascript:location.href='register.jsp'">注册</button> </p>            
         </form>  
       </div>  
     </section> 
         
      </body>  
    </html>  