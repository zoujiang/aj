<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/pages/sys/layout/util/jsp/tag.jsp"%>
<script type="text/javascript" src="${ctx }/pages/sys/layout/util/js/layout/west.js" charset="utf-8"></script>
<script type="text/javascript"  charset="utf-8">
	$(function() {
		initPanel();
		initTree();
	});
</script>

<div id="westPanel" class="easyui-panel" style="background:#fafafa;overflow-x: hidden;overflow-y:auto;">  
   <ul id="layout_west_tree" class="easyui-tree" ></ul>
</div> 