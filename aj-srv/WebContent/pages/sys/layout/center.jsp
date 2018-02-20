<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/pages/sys/layout/util/jsp/tag.jsp"%>
<link rel="stylesheet" href="${ctx }/pages/sys/layout/util/css/layout/center.css" type="text/css"></link>
<script type="text/javascript" src="${ctx }/pages/sys/layout/util/js/layout/center.js" charset="utf-8"></script>
<script type="text/javascript"  charset="utf-8">
	$(function() {
		initPanel("${Login_User_Info.name}");
	});
</script>

<div id="layout_center_tabs" class="easyui-tabs">
	<div title="首页" data-options="href:'${ctx }/pages/sys/layout/index.jsp',iconCls:'icon-home'" style="overflow: visible;"></div>
</div>
