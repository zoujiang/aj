<%@page import="java.io.PrintWriter"%>
<%@page import="com.frame.core.constant.Constant"%>
<%@page import="com.frame.system.vo.UserExtForm"%>
<%@page import="com.frame.system.vo.RoleExtForm"%>
<%
	request.setCharacterEncoding("utf-8");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.addHeader("Cache-Control", "no-store"); //Firefox
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
	response.setDateHeader("max-age", 0);
	String mainWeb = request.getContextPath();
	String jqueryUIPath = mainWeb + "/scripts/jqueryui";
	UserExtForm user = (UserExtForm) request.getSession().getAttribute(
			Constant.LoginAdminUser);
%>
<link rel="stylesheet" type="text/css"
	href="<%=mainWeb%>/css/201202/all_icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=mainWeb%>/css/201202/manage.css">
<link rel="stylesheet" type="text/css"
	href="<%=jqueryUIPath%>/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=jqueryUIPath%>/themes/icon.css">

<script type="text/javascript"
	src="<%=mainWeb%>/scripts/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="<%=mainWeb%>/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=jqueryUIPath%>/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=jqueryUIPath%>/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=mainWeb%>/scripts/json.js"></script>
<script type="text/javascript" src="<%=mainWeb%>/scripts/eason.js"></script>
<script type="text/javascript" src="<%=mainWeb%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=mainWeb%>/scripts/ajaxfileupload.js"></script>
<script type="text/javascript">
  var mainWeb = "<%=mainWeb%>";
  var webPath = mainWeb;
  window.UEDITOR_HOME_URL = "<%=mainWeb%>/scripts/ueditor/";
  $.ajaxSetup({
         contentType:"application/x-www-form-urlencoded;charset=utf-8",
         cache:false,
         dataType:"json",
         complete:function(XHR,TS){
	        var resText=XHR.responseText;
	        if(resText.indexOf("sessionState")!=-1 && resText.indexOf("loginUrl")!=-1){
		        var resJson=$.parseJSON(resText);
		       
		        if(resJson.sessionState==0){
			        window.top.location.href=resJson.loginUrl;
			    }
	        }
		 }
  }); 
</script>

<link rel="stylesheet" type="text/css" href="<%=mainWeb%>/css/common.css">


