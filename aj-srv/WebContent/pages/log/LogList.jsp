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
					url:mainWeb + "/admin/log/queryList",
				    columns:[[  
				     	{field:'logId',title:'日志ID',width:150,align:'center', sortable: true},
				        {field:'oprType',title:'操作类型',width:80,
					        	formatter : function(value, row, index) {
					        		if(value=="1"){
					        			return "新增";
					        		}else if(value=="2"){
					        			return "修改";
					        		}else if(value=="3"){
					        			return "删除";
					        		}else if(value=="4"){
					        			return "登陆";
					        		}else if(value=="5"){
					        			return "登出";
					        		}else if(value=="6"){
					        			return "审批";
					        		}else if(value=="7"){
					        			return "启停";
					        		}else{
					        			return "其他"; 
					        		}
					       		}
					       	},  
					   	{field:'oprSysModule',title:'系统模块',width:100,align:'center', sortable: true},
					    {field:'oprUserAccount',title:'操作账号',width:100,align:'center', sortable: true},
				     	{field:'oprDt',title:'操作时间',width:120,align:'center', sortable: true},
				     	{field:'oprContent',title:'操作内容',align:'center', sortable: true}

					    ]]  
				}); 
 			};
 			
 			this.initActions = function(){
 				// 搜索
				$('#search_area').click(function() {
					 var searchText = $('#searchText').val();
					var oprUserAccount =  $('#oprUserAccount').val();
					var oprType  = $('#oprType').combobox('getValues');
					var startDt = $('#startDt').val();
					var endDt = $('#endDt').val();
					var reqText = "{'searchText':'"+searchText+"','oprUserAccount':'" + oprUserAccount  + "','oprType':'" + oprType + "','startDt':'" + startDt + "','endDt':'" + endDt + "'}";
					$table.datagrid('load',  {'req':reqText}); 
				});
				//导出数据
				$('#btn_exportArea').click(function() {
					 var searchText = $('#searchText').val();
						var oprUserAccount =  $('#oprUserAccount').val();
						var oprType  = $('#oprType').combobox('getValues');
						var startDt = $('#startDt').val();
						var endDt = $('#endDt').val();
					var reqText = "{'searchText':'"+searchText+"','oprUserAccount':'" + oprUserAccount + "','oprUserAccount':'" + oprUserAccount + "','oprType':'" + oprType + "','startDt':'" + startDt + "','endDt':'" + endDt + "'}";
					$table_area.datagrid('doExport', {'req': reqText}); 
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
						操作人账号ID&nbsp;<input id="oprUserAccount"  type="text" style="width: 100px; height: 23px;" />
						日志类型&nbsp;<select id="oprType" name="oprType" class="easyui-combobox" style="width:80px;margin:0px 0px 0px 0px;">
					          			<option value="" selected="selected">全部</option>
					          			<option value="1">新增</option>
										<option value="2">修改</option>
										<option value="3">删除</option>
										<option value="4">登陆</option>
										<option value="5">登出</option>
										<option value="6">审批</option>
										<option value="7">启停</option>
										<option value="9">其他</option>
									</select>
						操作日志时间
						&nbsp;<input class="Wdate" id='startDt' type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 140px;height: 23px"/>
							-<input class="Wdate" id='endDt' type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 140px;height: 23px"/>
						关键字&nbsp;<input id="searchText" name="searchText" type="text" style="width: 150px; height: 23px;" />
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