<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>WebService�ӿڲ���</title>
	</head>

	<%
		String flag = request.getParameter("flag");
		flag = flag == null ? "" : flag.trim();
	%>
	
	<body>
		<form name="testForm" action="<%=flag%>" method="post">
			��ʼʱ�䣺
			<input type="text" name="stime" size="20">
			����ʱ�䣺
			<input type="text" name="etime" size="20">
			�������ͣ�
			<select name="type">
				<option value="month">
					������
				</option>
				<option value="day">
					������
				</option>
			</select>
			<input type="hidden" name="flag" value="<%=flag%>">
			<input type="submit" value="��ѯ">
		</form>
	</body>
</html>
