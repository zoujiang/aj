<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<title>亲脉</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>pages/spt/h5/css/common.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>pages/spt/h5/css/APPmls.css">
	<script type="text/javascript" src="<%=basePath %>pages/spt/h5/js/fn.js"></script>
	<script type="text/javascript" src="<%=basePath %>resources/hplus4.1/js/jquery.min.js"></script>
	<script type="text/javascript">
		function initData(){
			$.ajax({
	   	        type: "POST",
	   	        url: "<%=basePath %>service/h5/getAthleteInfo",
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
	   	        	$(".wy-section").append(data);
	   	        }
	   		});	
		}
		$(function(){
			initData();
		});
	</script>
</head>
<body>
<header>
	<div class="wy-header">
		<div class="wy-header-personal">
			<a onclick="javascript:history.go(-1);"> < </a>
			<p>个人状态查询</p>
		</div>
	</div>
</header>

<section>
	<!-- 信息部分  改变 报名信息不存在-->
	<div class="wy-section">
	</div>
</section>

<footer>
	<div class="footer">
		<div class="wy-foot">

		</div>
		<div class="wy-foot-js">
			<p>主办单位：重庆市体育局 重庆日报报业集团 </p>
			<p>重庆南岸区人民政府 乐视体育文化发展（北京）有限公司 </p>
			<p>承办单位：重庆乐视体育产业发展有限公司</p>
			<p>联合承办单位：咪咕互动娱乐有限公司</p>
		</div>
	</div>

</footer>




</body>
</html>