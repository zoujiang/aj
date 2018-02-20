<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>区域管理</title>
 	<script type="text/javascript">
 		function AreaMgr(){
 			var $table=$("#table_area");
 			//添加
 			AreaMgr.add = function(e){
 				var area=new Object();
 				area.editType="add";
 				com.eason.window.winUtils.showDialogWindowOfWH(700,500,e,mainWeb + "/pages/demo/area/areaAdd.jsp",area,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 				
 			};
 			//修改
 			AreaMgr.modify = function(id,e){
 				var area=new Object();
 				area.areaId=id;
 				area.editType="edit";
 				com.eason.window.winUtils.showDialogWindowOfWH(700,500,e,mainWeb + "/pages/demo/area/areaEdit.jsp",area,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			//删除
 			AreaMgr.del = function(id,e){
 				$.messager.confirm('删除','是否确定删除？',function(r){
				if(r){
						$.ajax({
							url: mainWeb + "/admin/area/deleteArea",
							data: 'id=' + id,
							dataType: 'json',
							success: function(data) {
								$.messager.alert('提示', data.message, 'info', function() {
									$table.datagrid('reload');
								  });
							   }
						     });
					  }
				});
 			};
 			//复制新建
 			AreaMgr.copynew = function(id,e){
 				var area=new Object();
 				area.areaId=id;
 				area.editType="copynew";
 				com.eason.window.winUtils.showDialogWindowOfWH(700,500,e,mainWeb + "/pages/demo/area/areaCopyNew.jsp",area,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			//详情
 			AreaMgr.view = function(id,e){
 				var area=new Object();
 				area.areaId=id;
 				area.editType="view";
 				com.eason.window.winUtils.showDialogWindowOfWH(700,500,e,mainWeb + "/pages/demo/area/areaView.jsp",area,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			this.initTable = function(){
	 			$table.datagrid({
					fit: true,
					border: false,
					rownumbers: true,
					pagination: true,
					singleSelect: true,
					url:mainWeb + "/admin/area/queryAreaList",
				    columns:[[  
				     	{field:'areaId',title:'区域ID',width:80,align:'center', sortable: true},
				     	{field:'areaName',title:'区域名称',width:120,align:'center', sortable: true},
				        {field:'isValid',title:'生效',width:80,
					        	formatter : function(value, row, index) {
					        		if(value=="1"){
					        			return "是";
					        		}else{
					        			return "否"; 
					        		}
					       		}
					       	},  
					    {field:'createUser',title:'创建人',width:80,align:'center', sortable: true},
				     	{field:'createDt',title:'创建时间',width:80,align:'center', sortable: true},
				     	{field:'modifyUser',title:'修改人',width:80,align:'center', sortable: true},
				     	{field:'modifyDt',title:'修改时间',width:80,align:'center', sortable: true},
				    	{field:'action',title:'操作',width:'100',align: 'left',
				        	formatter : function(value, row, index) {
								var mod = "";
								var del = "";
								if(row.edit){
									mod = "<span  height = '23px'  width = '23px'><img src='<%=mainWeb%>/images/icon/edit.png' title='编辑'"
										+" onclick=\"AreaMgr.modify('" + row.areaId + "',event)\" style='cursor: pointer'></IMG></span>";
								}
								if(row['delete']){
									del = "<span  height = '23px'  width = '23px'><img src='<%=mainWeb%>/images/icon/delete.png' title='删除' href='#' onclick=\"AreaMgr.del('"
										+ row.areaId + "')\" style='cursor: pointer'></img></span>"
								}
								var cop = "<span  height = '23px'  width = '23px'><img src='<%=mainWeb%>/images/icon/copy.png' title='复制新建' onclick=\"AreaMgr.copynew('"
										+ row.areaId + "',event)\"  style='cursor: pointer'></a></span>"
								var det = "<span  height = '23px'  width = '23px'><img src='<%=mainWeb%>/images/icon/detail.png' title='详情' href='#' onclick=\"AreaMgr.view('"
										+ row.areaId + "',event)\" style='cursor: pointer'></a></span>"
								
								return mod + del + cop + det;
							}
				       }
				    ]]  
				}); 
 			};
 			
 			this.initActions = function(){
 				// 搜索
				$('#search_area').click(function() {
					var searchText = $('#searchText').val();
					var reqText = "{'searchText':'"+searchText+"'}";
					$table.datagrid('load',  {'req':reqText});
				});
				//导出数据
				$('#btn_exportArea').click(function() {
					var searchText = $('#searchText').val();
					var reqText = "{'searchText':'"+searchText+"'}";
					$table.datagrid('doExport', {'req': reqText});
				});
				//新增订单
				$('#btn_newArea').click(function(e){
					AreaMgr.add(e);
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
						区域名称&nbsp;<input id="searchText" name="searchText" type="text" style="width: 220px; height: 23px;" />
						<a id='search_area' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
						<a id="btn_newArea" href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增区域</a>
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