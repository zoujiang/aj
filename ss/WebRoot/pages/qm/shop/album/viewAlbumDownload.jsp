<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String ad = request.getParameter("ad");
if(ad == null ){
	ad = "";
}
String sec = request.getParameter("sec");
if(sec == null ){
	sec = "";
}
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


     <title>商户管理平台-照片地址</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
    <link rel="shortcut icon" href="favicon.ico"> <link href="<c:url value='/resources/hplus4.1/css/bootstrap.min.css?v=3.3.6'></c:url>" rel="stylesheet">
    <link href="<c:url value='/resources/hplus4.1/css/font-awesome.min.css?v=4.4.0'></c:url>" rel="stylesheet">
    <link href="<c:url value='/resources/hplus4.1/css/animate.min.css'></c:url>" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/hplus4.1/css/plugins/webuploader/webuploader.css'></c:url>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/hplus4.1/css/demo/webuploader-demo.min.css'></c:url>">
    <link href="<c:url value='/resources/hplus4.1/css/style.min.css?v=4.1.0'></c:url>" rel="stylesheet">
    
    <script type="text/javascript">
	</script>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeIn">
     	<div class="ibox float-e-margins" style="margin-bottom: 0px;">
             <div class="ibox-content row">
    			<form class="form-horizontal" id="userForm" method="post">
	               	 <div class="row">
		               	<div class="form-group col-sm-6"> 
		               		<label class="col-sm-4 control-label" >片源下载地址：</label> 
		               		<div class="col-sm-12">
		               			<%=ad %>
		               		</div>
						</div>
					</div>
               	 	<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label" >提取密码：</label> 
	               				<div class="col-sm-12">
	               					<%=sec %>
	               				</div>
						    </div>
                    </div>
    		</form>
   		 </div>
    </div>
    
    <script src="<c:url value='/resources/hplus4.1/js/bootstrap.min.js?v=3.3.6' ></c:url>"></script>
    <script src="<c:url value='/resources/hplus4.1/js/content.min.js?v=1.0.0' ></c:url>"></script>
</body>

</html>
