<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="tag.jsp"%>
<!-- easyui控件 -->
<link id="easyuiTheme" rel="stylesheet" href="${ctx}/pages/sys/resource/jquery-easyui-1.4.4/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx }/pages/sys/resource/jquery-easyui-1.4.4/themes/icon.css" type="text/css"></link>
<script type="text/javascript" src="${ctx }/pages/sys/resource/jquery-1.9.0.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx }/pages/sys/resource/jquery-easyui-1.4.4/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx }/pages/sys/resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

<!-- cookie插件 -->
<script type="text/javascript" src="${ctx }/pages/sys/resource/jquery.cookie.js" charset="utf-8"></script>

<!-- JSON插件 -->
<script type="text/javascript" src="${ctx }/pages/sys/resource/json.js" charset="utf-8"></script>

<!-- form ajax提交插件  -->
<script type="text/javascript" src="${ctx }/pages/sys/resource/jquery.form.js" charset="utf-8"></script>

<!-- 自定义样式及js扩展 -->
<script type="text/javascript" src="${ctx }/pages/sys/layout/util/js/ext.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx }/pages/sys/layout/util/js/util.js" charset="utf-8"></script>
