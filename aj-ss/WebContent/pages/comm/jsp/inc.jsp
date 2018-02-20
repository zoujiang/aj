<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%
	request.setCharacterEncoding("utf-8");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.addHeader("Cache-Control", "no-store"); //Firefox
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
	response.setDateHeader("max-age", 0);
	//String mainWeb = request.getContextPath() + "/tms";
%>


<link rel="shortcut icon" href="${ctx }favicon.ico">

<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/json.js"></script>
<script type="text/javascript" src="${ctx }/resources/layer/2.1/layer.js"></script>
<script type="text/javascript" src="${ctx }/pages/comm/js/utils.js"></script>
<script type="text/javascript">
  var mainWeb = "${pageContext.request.contextPath}";
  var mainService = "${pageContext.request.contextPath}/service";
</script>

    <link href="${ctx }/resources/hplus4.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx }/resources/hplus4.1/css/font-awesome.min.css" rel="stylesheet">
    <link href="${ctx }/resources/hplus4.1/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="${ctx }/resources/hplus4.1/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${ctx }/resources/hplus4.1/css/animate.min.css" rel="stylesheet">
    <link href="${ctx }/resources/hplus4.1/css/style.min.css" rel="stylesheet">
    
    <link href="${ctx }/resources/hplus4.1/css/plugins/jsTree/style.min.css" rel="stylesheet">
    <!-- 
    <link href="${ctx }/resources/hplus4.1/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">
     -->
    <link href="${ctx }/resources/silviomoreto/css/bootstrap-select.css" rel="stylesheet">
    
    <script src="${ctx }/resources/hplus4.1/js/bootstrap.min.js"></script>
    <script src="${ctx }/resources/hplus4.1/js/content.min.js"></script>
    <script src="${ctx }/resources/hplus4.1/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${ctx }/resources/hplus4.1/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/iCheck/icheck.min.js"></script>
    <script src="${ctx }/resources/hplus4.1/js/plugins/bootstrap-table/extensions/export/bootstrap-table-export.min.js"></script>    
    <script src="${ctx }/resources/hplus4.1/js/plugins/bootstrap-table/extensions/export/tableExport.js"></script>
    <script src="${ctx }/resources/hplus4.1/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="${ctx }/resources/hplus4.1/js/plugins/suggest/bootstrap-suggest.min.js"></script>
        <script src="${ctx }/resources/silviomoreto/js/bootstrap-select.js"></script>
    
    <script src="${ctx }/resources/hplus4.1/js/plugins/treeview/bootstrap-treeview.js"></script>
    
    <script src="${ctx }/resources/hplus4.1/js/plugins/jsTree/jstree.min.js"></script>
    <script src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>
    
    <script src="${ctx }/resources/hplus4.1/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${ctx }/resources/hplus4.1/js/plugins/validate/additional-methods.js"></script>
    <script src="${ctx }/resources/hplus4.1/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${ctx }/resources/jquery.form.js"></script>
    
    <link href="${ctx }/resources/opentip/opentip.css" rel="stylesheet">
    <script src="${ctx }/resources/opentip/opentip-jquery.min.js"></script>    
    <script src="${ctx }/resources/hplus4.1/js/plugins/dropzone/dropzone.js"></script>
    <link href="${ctx }/resources/hplus4.1/css/plugins/dropzone/basic.css" rel="stylesheet">
    <link href="${ctx }/resources/hplus4.1/css/plugins/dropzone/dropzone.css" rel="stylesheet">
    