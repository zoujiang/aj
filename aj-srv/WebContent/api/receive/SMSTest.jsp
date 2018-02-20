<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>短信测试</title>
		<style type="text/css">
			* {
				font-size: 12px;
				margin: 0;
				padding: 0;
			}
			fieldset {
				padding: 10px;
				margin: 10px;
				width: 370px;
				color: #333;
				border: #06c dashed 1px;
				background-color: #EEE;
			}
			legend {
				color: #06c;
				font-weight: 800;
				background: #fff;
			}
			ul {
				list-style-type: none;
				margin: 8px 0 4px 0;
				color: #880;
			}
			li {
				margin-top: 4px;
			}
		</style>
		<script type="text/javascript" src="<%=basePath%>/scripts/jquery/jquery-1.8.1.min.js"></script>
		<script type="text/javascript">
			$(function(){
				var $form=$("#testForm");
				$form.submit(function(){
					return false;
				});
				$("#testForm > input[type='submit']").click(function(){
					if($("#testForm > textarea").val()==""){
						alert("不能为空哦~亲！");
						return false;
					}
					$("#textArea").val("");
					$.post("<%=basePath%>service/sendSMSTest",
					$form.serialize(),
					function(data){
							$("#textArea").val(data);
					},"text");
				});
				$("#btn1").click(function(){
					$("#show").toggle();
				});
			});
		</script>
	</head>
	<body>
	<div style="float:left;clear: both;width: 500px;">
		<fieldset>
			<legend>
				发送短信测试
			</legend>
			<form id="testForm" name="testForm" action="#"
				method="post">
				请输入正确的JSON字符串：
				</br>
				<textarea rows="20" cols="70" name="jsonRequest"></textarea>
				</br>
				<input type="submit" value="提交">
				</br>
			</form>
		</fieldset>
	<fieldset >
			<legend>
				返回结果
			</legend>
				<textarea id="textArea" rows="9" cols="70"></textarea>
			</form>
		</fieldset>
	</div>
		<fieldset style="float:left;">
			<legend>
				注意的相关事项
			</legend>
			<ul>
				<li>
					（1）测试地址:http://ip:port/mkt/service/proxy
				</li>
				<li>
					（2）JSON格式测试地址：http://www.bejson.com/go.php?u=http://www.bejson.com/index.php
				</li>
				<li>
					（3）JSON测试例子：<input id="btn1" type="button" value="点击展开例子"/>
				</li>
			</ul>
			<ul id="show" style="display: none">
				<li>{</li>
					<li>"action":"sendSms",</li>
				  	<li>"body":{</li>
			    	<li>&nbsp;&nbsp;&nbsp;&nbsp;"productId":"service0001",</li>
			    	<li>&nbsp;&nbsp;&nbsp;&nbsp;"password":"123456",</li>
			    	<li>&nbsp;&nbsp;&nbsp;&nbsp;"spId":"32432423",</li>
			    	<li>&nbsp;&nbsp;&nbsp;&nbsp;"address":"1566788999",</li>
			    	<li>&nbsp;&nbsp;&nbsp;&nbsp;"message":"尊敬的用户您好，欢迎使用中国移动重庆无线城市平台，因你的参与而更精彩。",</li>
			    	<li>&nbsp;&nbsp;&nbsp;&nbsp;"senderName":"10877"</li>
			    	<li>&nbsp;&nbsp;}</li>
				<li>}</li>
			</ul>
		</fieldset>
	</body>
</html>
