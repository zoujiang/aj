<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/pages/sys/layout/util/jsp/tag.jsp"%>
<script type="text/javascript" src="${ctx }/pages/sys/layout/util/js/layout/north.js" charset="utf-8"></script>

 <div style="position: absolute; left: 0px; top: 0px;right: 0px; bottom: 0px; "  ><img  src="${ctx }/pages/sys/layout/util/img/backhead-logo.jpg" style="width: 100%;"></div> 
<div style="position: absolute; right: 0px; bottom: 0px; ">
 	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-home',plain:true" onclick="logout();">退出登录</a>
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-ctrl'">控制面板</a>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<!-- <div data-options="iconCls:'icon-user-edit'" onclick="userInfo();">个人信息</div> -->
	<div data-options="iconCls:'icon-password-edit'" onclick="modifyPass();">修改密码</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'icon-css'">
		<span>更换主题</span>
		<div style="width: 120px;">
			<!-- <div onclick="changeTheme('bootstrap');">default</div> -->
			<div onclick="changeTheme('bootstrap');">default</div>
			<div onclick="changeTheme('gray');">gray</div>
			<div onclick="changeTheme('metro');">metro</div>
			<div onclick="changeTheme('ui-cupertino');">cupertino</div>
			<div onclick="changeTheme('ui-sunny');">sunny</div>
			<div onclick="changeTheme('ui-pepper-grinder');">pepper grinder</div>
		</div>
	</div>
	<!-- <div class="menu-sep"></div>
	<div data-options="iconCls:'icon-restore'" onclick="init();">初始化数据</div> 
	<div class="menu-sep"></div>
	<!-- <div data-options="iconCls:'icon-user-quit'" onclick="logOut();">退出系统</div> -->
</div>