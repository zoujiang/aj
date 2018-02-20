<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/pages/sys/layout/util/jsp/tag.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>爱家</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="爱家">
	<meta http-equiv="description" content="爱家">
	<%@ include file="/pages/sys/layout/util/jsp/inc.jsp"%>
	<script type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			//禁止后退键 作用于Firefox、Opera
			document.onkeypress=banBackSpace;
			//禁止后退键  作用于IE、Chrome
			document.onkeydown=banBackSpace;
		});
		  $.ajaxSetup({
		         contentType:"application/x-www-form-urlencoded;charset=utf-8",
		         cache:false,
		         dataType:"json",
		         complete:function(XHR,TS){
		        	// alert(XHR);
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
  </head>
  
  <body class="easyui-layout" id="cc">
  	<div data-options="region:'north',href:'${ctx}/pages/sys/layout/north.jsp'" style="height: 78px;overflow: hidden;"  class="north"></div>
	<div data-options="region:'west',href:'${ctx}/pages/sys/layout/west.jsp'" style="width: 200px;overflow: hidden;border-bottom: none;border-top: none;" ></div>
	<div data-options="region:'center',href:'${ctx}/pages/sys/layout/center.jsp',border:false" style="overflow: hidden;"></div>
	<div data-options="region:'south',href:'${ctx}/pages/sys/layout/south.jsp'" style="height: 27px;overflow: hidden;"></div>
  	<%@ include file="/pages/sys/layout/util/jsp/plug.jsp"%>
  </body>
</html>
