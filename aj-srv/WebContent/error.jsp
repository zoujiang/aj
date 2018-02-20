<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
.body{
background-color:#000;
margin:0px;
padding:0px;
}
.www_zzjs_net{
width:400px;
margin:0px auto auto;

font-size:13px;
}
.zzjs_net{

height:20px;
line-height:20px;
color:#F00;
font-weight:700;
text-align:center;
}
._zzjs_net{
height:55px;

color:#555555;
text-align:center;
padding:10px 0px;
}
._zzjs_net a{
color:#00F;
text-decoration:none;
}
._zzjs_net a:hover{
color:#00F;
text-decoration:underline;
}
._zzjs_net p a{
margin:0px 5px;
color:#F00;
font-weight:700;
}
</style>
</head>
<body>
<center>
<div class="www_zzjs_net">
  <!-- <div class="zzjs_net">提示信息</div> -->
    <div class="_zzjs_net">
     <p>抱歉，您所查找的页面不存在，可能已被删除或您输错了网址！</p>
    </div>
</div>
<div align="center"><img src="<%=basePath%>images/404.gif" /></div>
</center>
</body>
</html>