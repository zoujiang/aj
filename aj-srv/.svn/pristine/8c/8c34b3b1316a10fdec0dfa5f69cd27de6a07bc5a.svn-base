<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>系统操作日志查询</title>
 	<script type="text/javascript">
 		function AreaMgr(){
 			var $table=$("#table_area");
 			this.initTable = function(){
	 			$table.datagrid({
					fit: true,
					border: false,
					rownumbers: true,
					pagination: true,
					singleSelect: true,
					url:mainWeb + "/admin/push/message/getMessageLog",
				    columns:[[  
				     	{field:'phoneVer',title:'推送设备',width:150,align:'center', sortable: false,
							formatter:function(value,rec){
								if("IOS"==value){
									return "IOS设备";
								}else if("Android"==value){
									return "ANDROID设备";
								}else{
									return "默认设备";
								}
							}},
				     	{field:'ucode',title:'设备号',width:150,align:'center', sortable: false},
				      
					   	{field:'nickName',title:'用户名称',width:100,align:'center', sortable: false},
					    {field:'userTel',title:'电话号码',width:100,align:'center', sortable: false},
					    {field:'msgTitle',title:'消息标题',width:100,align:'center', sortable: false},
					    {field:'msgContent',title:'消息内容',width:100,align:'center', sortable: false},
					    {field:'msgType',title:'消息类型',width:100,align:'center', sortable: false,
							formatter:function(value,rec){
								if("1"==value){
									return "短信消息";
								}else{
									return "推送信息";
								}
							}},
				     	{field:'msgFrom',title:'消息来源',width:120,align:'center', sortable: false,
							formatter:function(value,rec){
								if("0"==value){
									return "消息系统创建";
								}else{
									return "管理系统创建";
								}
							}	},
				     	{field:'sendDt',title:'发送时间',align:'center', sortable: false},
				     	{field:'sendResult',title:'发送状态',align:'center', sortable: false,
							formatter:function(value,rec){
								if("1"==value){
									return "发送失败";
								} else if("2"==value){
									return "发送成功)";
								}
							}}

					    ]]  
				}); 
 			};
 			
 			this.initActions = function(){
 				// 搜索
				$('#search_area').click(function() {
					var searchText = $('#searchText').val();
					var searchUserTel =  $('#searchUserTel').val();
					
					var searchStartDt = $('#startDt').val();
					var searchEndDt = $('#endDt').val();
					var reqText = "{'searchText':'"+searchText+"','searchUserTel':'" + searchUserTel  + "','searchStartDt':'" + searchStartDt + "','searchEndDt':'" + searchEndDt + "'}";
					$table.datagrid('load',  {'req':reqText}); 
				});
				//导出数据
				$('#btn_exportArea').click(function() {
					var searchText = $('#searchText').val();
					var searchUserTel =  $('#searchUserTel').val();
					
					var searchStartDt = $('#startDt').val();
					var searchEndDt = $('#endDt').val();
					var reqText = "{'searchText':'"+searchText+"','searchUserTel':'" + searchUserTel  + "','searchStartDt':'" + searchStartDt + "','searchEndDt':'" + searchEndDt + "'}";
					
					$table.datagrid('doExport', {'req': reqText}); 
				});
				
 			};
 			this.init = function() {
			 	this.initTable();
			 	this.initActions();
			};
 		}
 		$(document).ready(function() {
	  		new AreaMgr().init();
		});
	</script>
</head>
<body>

	<div class="easyui-layout" fit="true" style = "min-height: 80%;
width: 800;background: none repeat scroll 0% 0% rgb(255, 255, 255);">
		<div id="content" region="center" id="right_area" 
			style="padding: 0px;margin : 2px 4px 4px 2px;border-radius: 5px 5px 5px 5px;border: 1px solid #ECECEC;"  fit="false" border="true">
			<div class="easyui-layout" fit="true" split="false" id="test_area"
				border="false">
				<div region="north" id="right_bottom_area"
					style="height: 35px; background-color: #EEF;" border="false">
						用户电话号码&nbsp;<input id="searchUserTel"  type="text" style="width: 100px; height: 23px;" />						
						发送时间
						&nbsp;<input class="Wdate" id='startDt' type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 140px;height: 23px"/>
							-<input class="Wdate" id='endDt' type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 140px;height: 23px"/>
						消息关键字&nbsp;<input id="searchText" name="searchText" type="text" style="width: 150px; height: 23px;" />
						<a id='search_area' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
						<a id="btn_exportArea" href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">导&nbsp;出</a>
				
				</div>
				<div region="center" style="padding: 0px;" border="false">
					<table id="table_area"></table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>