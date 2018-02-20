<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>WebService接口测试</title>
	</head>

	<%
		String flag = request.getParameter("flag");
		flag = flag == null ? "" : flag.trim();
	%>
	
	<body>
		<form name="testForm" action="<%=flag%>" method="post">
			开始时间：
			<input type="text" name="stime" size="20">
			结束时间：
			<input type="text" name="etime" size="20">
			粒度类型：
			<select name="type">
				<option value="month">
					月粒度
				</option>
				<option value="day">
					日粒度
				</option>
			</select>
			<input type="hidden" name="flag" value="<%=flag%>">
			<input type="submit" value="查询">
		</form>
	</body>
</html>
