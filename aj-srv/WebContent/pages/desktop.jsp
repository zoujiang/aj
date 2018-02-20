<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>爱家后台管理系统</title>
		<link rel="shortcut icon" href="<%=mainWeb%>/images/page/icon-7.ico" type="image/x-icon" />
		<style type="text/css">
		/*操作栏样式*/
		a.operateChannel,
		a.operateChannel:link{
			color: #00F;
			text-decoration: none;
		}
		a.operateChannel:hover{
			color: #F00;
			text-decoration: underline;
		}
		</style>	
	</head>
	
	<body style="visibility: hidden;" class="easyui-layout" id="pageContent">
		<!-- logo区 -->
		<div data-options="region:'north',border:false,noheader:true"
			style="height: 66px;">
			<%@ include file="/pages/desktop/logo.jsp"%>
		</div>
		<!-- 菜单导航区 -->
		<div data-options="region:'west',title:'菜单导航',split:true"
			style="width: 200px;" id="desktopNavigation">
			<%@ include file="/pages/desktop/navigation.jsp"%>
		</div>
		<!-- 内容区 -->
		<div data-options="region:'center',border:false">
			<div id="contentRegion" class="easyui-tabs"
				data-options="fit:true,border:true">
			</div>
		</div>
		<!-- 版权区 -->
		<div data-options="region:'south',border:false,noheader:true"
			style="height: 26px;">
			<%@ include file="/pages/desktop/copyRight.jsp"%>			
		</div>
		<script type="text/javascript">
    	//解决界面出现时，无样式问题
		$(function(){
			var timer = setTimeout(function() {
				$("body").css("visibility","visible");
				clearTimeout(timer);
			}, 50);
		});
    </script>
	</body>
</html>
