<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=basePath %>pages/spt/web/css/wy-cx.css"/>
	<script type="text/javascript" src="<%=basePath %>resources/hplus4.1/js/jquery.min.js"></script>
    <title></title>
    <script type="text/javascript">
		function initData(){
			$.ajax({
	   	        type: "POST",
	   	        url: "<%=basePath %>service/web/getAthleteInfo",
	   	        dataType: "text",
	   	        data:{
	   	        	certNo:"${certNo}",
	   	        	mobile:"${mobile}",
	   	        	groupId:"${groupId}"
	   	        },
	   	        success: function(data){
	   	        	if(data == null || data == ''){
	   					return;
	   				}
	   	        	$(".wy-main03").append(data);
	   	        }
	   		});	
		}
		$(function(){
			initData();
		});
	</script>
</head>
<body>
<!-- 页头 -->
<header id="head">
    <div class="container">
        <div class="logo">
            <img src="<%=basePath %>pages/spt/web/images/wy-mls01.png" alt=""/>
        </div>
    </div>
</header>

<section id="main">
    <div class="wy-main">
        <div class="wy-main-dh">
            <a onclick="javascript:history.go(-1);"><img src="<%=basePath %>pages/spt/web/images/wy-mls02.gif" alt=""/></a><!--logo/-->
        </div>
        <div class="wy-main-dh02">
            <ul>
                <li><a href="">首页</a></li>
                <li><a href="">赛事信息</a></li>
                <li><a href="">新闻公告</a></li>
                <li><a href="">合作伙伴</a></li>
                <li><a href="">关于我们</a></li>
            </ul>
        </div>
    </div>
    <div class="wy-main02">
        <p>您的位置:&nbsp;&nbsp;<a href="">首页></a><a href="">报名查询</a></p>
    </div>
    <div class="wy-main03">
        <div class="wy-fonts">个人状态查询</div>
    </div>
</section>
<!-- 主办单位介绍 -->
<footer id="footer">
    <div class="wy-foot">

    </div>
    <div class="wy-foot-js">
        <p>主办单位：重庆市体育局 重庆日报报业集团 重庆南岸区人民政府 乐视体育文化发展（北京）有限公司 </p>
        <p>承办单位：重庆乐视体育产业发展有限公司</p>
        <p>联合承办单位：咪咕互动娱乐有限公司</p>
    </div>
</footer>

</body>
</html>