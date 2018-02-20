<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!--<script type="text/javascript"
	src="<%=basePath%>scripts/jquery/jquery-1.7.1.min.js"></script>

<script type="text/javascript"
	src="<%=basePath%>scripts/jqueryui/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css"
	href="<%=basePath%>scripts/jqueryui/themes/default/easyui.css">
--><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>用户参与活动绑定</title>
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
		<script type="text/javascript">
			$(function(){	
			  $("#testForm").submit(function(){
					return false;
				});
				$("#testSave").click(function(){
			                $("#testForm").form("submit", {
									url :"<%=mainWeb%>/service/fileUpload?jsonParam="+$("#jsonParam").val(),
									onSubmit : function() {
											return true;						
									},
									success : function(data) {
									 $("#textArea").val(data);
									  }
									});
				});
				$("#btn1").click(function(){
					$("#show1").toggle();
				});
			});	                  
		</script>
	</head>

	<%
		String flag = request.getParameter("flag");
		flag = flag == null ? "" : flag.trim();
	%>

	<body>
	<div style="float:left;clear: both;width: 500px;">
		<fieldset>
			<legend>
				<%=request.getParameter("name")%>
			</legend>
			<form id="testForm"  method="post" enctype ="multipart/form-data">
				上传文件：<input  name="file" type="file" />
				请输入正确的JSON字符串：
				</br>
				<textarea rows="20" cols="70" name="jsonParam" id="jsonParam"></textarea>
				</br>
					
				</br>
			</form>
		   <button id="testSave" style="width: 50px">测试</button>
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
				JSON测试例子
				</li>
			</ul>	
			<ul id="show1" style="display:inline">
			 <li>{</li>
			 <li>"serviceName": "ams_file_upload_req",</li>
			 <li>"callType":"android",</li>
			 <li>"params": {</li>
			 <li>"smallWidth":"124",</li>
			 <li>"smallHeigth":"124",</li>
			 <li>"fileType":"jpg"</li>
			 <li>}</li>
			 <li>}</li>
			</ul>	
		</fieldset>
		
	</body>
</html>
