<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/pages/sys/layout/util/jsp/tag.jsp"%>
<script type="text/javascript" src="${ctx }/pages/sys/layout/util/js/layout/user/modifypass.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx }/pages/sys/layout/util/js/layout/user/userinfo.js" charset="utf-8"></script>
<script type="text/javascript">
	$.includePath = '${ctx}/pages/sys/layout/util/css/system/'; 
 	if(jQuery.browser.msie){
		//使用方法
		$.include(['form_ie.css']);
	}
	else{
		$.include(['form_chrome.css']);
	}
	$(function() {
		/*修改个人信息*/
		init_personalinfo_dialog('${ctx}');
		/*修改密码*/
		init_modifypass_dialog('${ctx}');
	});
</script>
<!-- 修改个人信息 -->
<div id="personalinfo_dialog" class="easyui-dialog"></div>
<!-- 修改密码 -->
<div id="modifypass_dialog" class="easyui-dialog"></div>