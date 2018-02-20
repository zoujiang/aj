<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<title>个人状态查询</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<link rel="stylesheet" type="text/css" href="css/APPmls.css">
	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<script type="text/javascript" src="js/fn.js"></script>
	<link rel="stylesheet" href="<%=basePath %>resources/layer/2.1/skin/layer.css"/>
    <link rel="stylesheet" href="<%=basePath %>resources/layer/2.1/skin/layer.ext.css"/>
	<script type="text/javascript" src="<%=basePath %>resources/hplus4.1/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>resources/layer/2.1/layer.js"></script>
	
	<script type="text/javascript">
		$(function(){
			$(".wy-submit").click(function(){
				if($("input[name='certNo']").val() == ""){
					layer.msg("请输入证件号码", {title:'提示', btn: ['确定'],icon: 6}, function(index){});
					return;
				}
				if($("input[name='mobile']").val() == ""){
					layer.msg("请输入手机号码", {title:'提示', btn: ['确定'],icon: 6}, function(index){});
					return;
				}
				$.ajax({
		   	        type: "POST",
		   	        url:"<%=basePath %>service/h5/exist",
		   	        dataType: "json",
		   	        data:{
		   	        	certNo:$("input[name='certNo']").val(),
		   	        	mobile:$("input[name='mobile']").val()
		   	        },
		   	        success: function(data){
		   	        	if(data == null || data == ''){
		   					return;
		   				} 
		   	        	if(data.success){
		   	        		//layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){});
		   	        		location.href = "<%=basePath %>service/h5/toInfoPage?groupId="+data.groupId+"&certNo="+$("input[name='certNo']").val()+"&mobile="+$("input[name='mobile']").val();
		   	        	} else {
		   	        		//layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){});
		   	        		location.href = "<%=basePath %>pages/spt/h5/noexist.jsp";
		   	        	}
		   	        }
		   		});	
			});
		});
	</script>
</head>
<body>
<header>
	<div class="wy-header">
		<div class="wy-header-personal">
			<a href=""> < </a>
			<p>个人状态查询</p>
		</div>
	</div>
</header>

<section>
	<div class="wy-section">
		<div class="wy-section-certificate">
			<span>证件号码</span>
			<input type="text" name="certNo" class="">
	
			<span style="margin-top:10px;">手机号码</span>
			<input type="text" name="mobile" style="margin-top:10px;">
		</div>
		<p style="margin-top: 0.1rem;">&nbsp;</p>
		<a class="wy-submit">提交查询</a>
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