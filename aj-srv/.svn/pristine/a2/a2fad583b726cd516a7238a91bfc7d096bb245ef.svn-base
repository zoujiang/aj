<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
			$(function(){	
			$("#testSave").click(
			    function(){
			       $("#imageshow").attr("src",$("#imageurl").val());
			    }
			)
			 
			})
</script>
  </head>
  
  <body>
   <table style='padding: 10px 30px;'>
						<tr style='height: 30px;'>
							<td>图片url：</td>
							<td colspan="2">
								<input type='text' id='imageurl'  style="width:560px;height : 25px" />
								<font color='red'>*</font>
							</td>
						</tr>	
						<tr>
						  <td colspan="2"><img src="" id="imageshow" ></img></td>
						</tr>
						<tr>
						  <td colspan="2"> <button id="testSave" style="width: 50px">测试</button></td>
						</tr>
						</tr>
						</table>
	<fieldset style="float:left;">
			<legend>
				注意的相关事项
			</legend>

			<ul>
				<li>
					（1）http://IP:PORT/ams/service/img?img=/pref/perf1_small.jpg&viewType=2&width=200&height=200
				</li>
				<li>序号	名称	必填	类型	最大长度	参数说明</li>
                <li> img	Y	String		缩略原始图片地址</li>
					<li> 2	viewType	Y	String		放映模式：</li>
					<li> 1：缩小图预览</li>
					<li> 2：原图预览</li>
					<li> 3：固定宽高浏览（仅当width和heigth同时给出有效）</li>
					<li> 3	width	N	Int		图片宽度（仅当viewType=3时，生效）</li>
					<li> 4	height	N	Int		图片高度（仅当viewType=3时，生效）</li>
			</ul>	
		</fieldset>

  </body>
</html>
