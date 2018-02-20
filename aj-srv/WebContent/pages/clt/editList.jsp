<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>客户端升级管理</title>
 	<script type="text/javascript">
 		function PageMgr(){
 			var $table=$("#table_area");
 			//编辑
 			PageMgr.edit = function(id,e){
 				var obj = new Object();
 				obj.id = id;
 				com.eason.window.winUtils.showDialogWindowOfWH(800,500,e,mainWeb + "/pages/clt/edit.jsp",obj,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			//新增
 			PageMgr.add = function(e){
 				var obj = new Object();
 				com.eason.window.winUtils.showDialogWindowOfWH(800,500,e,mainWeb + "/pages/clt/edit.jsp",obj,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			//删除
 			PageMgr.delet = function(id,e){
 				$.messager.confirm('提示','确定要删除版本吗？',function(r){
					if (r) {
						$.ajax({
							url: mainWeb + "/admin/edit/deleteEdit",
							data:
								{
									id:id
								},
							dataType: 'json',
							success: function(data) {
								$.messager.alert('提示', data.message, 'info', function() {
									if (data.result) {
										$table.datagrid('reload');
									}
								  });
							   }
						     });
					} 
				});
 			};
 			//上线
 			PageMgr.onOffEdit = function(id,model,status,e){
 				$.messager.confirm('提示','确定要改变版本状态吗？',function(r){
					if (r) {
						$.ajax({
							url: mainWeb + "/admin/edit/onOffEdit",
							data:
								{
									id:id,
									status:status,
									model:model
								},
							dataType: 'json',
							success: function(data) {
								$.messager.alert('提示', data.message, 'info', function() {
									if (data.result) {
										$table.datagrid('reload');
									}
								  });
							   }
						     });
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
					url:mainWeb + "/admin/edit/queryEditList",
				    columns:[[ 
				        {field:'clientver',title:'客户端版本号',width:80,align:'center',sortable: false},
				     	{field:'model',title:'模式',width:80,align:'center',sortable: false,
				        	formatter : function(value, row, index) {
				     			var mod = "";
								if (value == "11") { 
									mod = "<span  height = '23px'  width = '23px'>安卓用户端</span>";
								}else if (value == "12") { 
									mod = "<span  height = '23px'  width = '23px'>IOS用户端</span>";
								}else if (value == "21") { 
									mod = "<span  height = '23px'  width = '23px'>安卓商户端</span>";
								}else if (value == "22") { 
									mod = "<span  height = '23px'  width = '23px'>IOS商户端</span>";
								}else if (value == "31") { 
									mod = "<span  height = '23px'  width = '23px'>安卓配送端</span>";
								}else if (value == "32") { 
									mod = "<span  height = '23px'  width = '23px'>IOS配送端</span>";
								}else{
									mod = "<span  height = '23px'  width = '23px'>版本类型错误</span>";
								}
								return mod;
							}
				        },
				     	{field:'clienturl',title:'客户端程序URL',width:300,align:'center',sortable: false},
				     	{field:'forceupdate',title:'是否强制更新',width:80,align:'center',sortable: false,
				     		formatter : function(value, row, index) {
				     			var mod = "";
								if (value == "Y") {  
									mod = "<span  height = '23px'  width = '23px'>是</span>";
								}
								if (value == "N") {  
									mod = "<span  height = '23px'  width = '23px'>否</span>";
								}
								return mod;
							}
				     	},
				     	{field:'updatecontent',title:'修改说明',width:80,align:'center',sortable: false},
			     	{field:'status',title:'状态',width:80,align:'center',sortable: false,
				     		formatter : function(value, row, index) {
				     			var mod = "";
								if (value == "1") {  
									mod = "<span  height = '23px'  width = '23px'>上线</span>";
								}
								if (value == "0") {  
									mod = "<span  height = '23px'  width = '23px'>下线</span>";
								}
								return mod;
							}
				     	},
				     	 {field:'update_userid',title:'更新用户',width:80,align:'center',sortable: false},
				     	/*{field:'create_userid',title:'创建用户',width:80,align:'center',sortable: false}, */
				     	{field:'update_date',title:'更新时间',width:100,align:'center',sortable: false},
				     	/* {field:'create_date',title:'创建时间',width:80,align:'center',sortable: false}, */
				     	{field:'action',title:'操作',width:'100',align:'center',
				        	formatter : function(value, row, index) {
								var mod = "";
								mod += "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"PageMgr.edit('" + row.id + "',event)\" >修改</a></span>";
								if (row.status == "1") {  //上线  --下线
									mod += "&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"PageMgr.onOffEdit('" + row.id + "','" + row.model + "',0,event)\" >下线</a></span>";
								}
								if (row.status == "0") {  //下线 --上线
									mod += "&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"PageMgr.onOffEdit('" + row.id + "','" + row.model + "',1,event)\" >上线</a></span>";
								}
								mod += "&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"PageMgr.delet('" + row.id + "',event)\" >删除</a></span>";
								return mod;
							}
				        }
				     ]]  
				  }); 
 			  };
 			  
 			//};
 			
 			this.initActions = function(){
 				// 搜索
				$('#search').click(function() {
					var model = $('#modelSearch').val();
					var status = $('#statusSearch').val();
					json = {model:model,status:status};
					$table.datagrid('load',json);
				});
 				
				//新增
				$('#add').click(function(e){
					PageMgr.add(e);
				});
 			};
 			this.init = function() {
			 	this.initTable();
			 	this.initActions();
			 	$('#status').combobox({editable:false}); 
			};
 		}
 		
 		$(document).ready(function() {
	  		new PageMgr().init();
		});
	</script>
</head>
<body>

	<div class="easyui-layout" fit="true" style = "min-height: 80%;width: 800;background: none repeat scroll 0% 0% rgb(255, 255, 255);">
		<div id="content" region="center" id="right_area" style="padding: 0px;margin : 2px 4px 4px 2px;border-radius: 5px 5px 5px 5px;border: 1px solid #ECECEC;"  fit="false" border="true">
			<div class="easyui-layout" fit="true" split="false" id="test_area" border="false">
				<div region="north" id="right_bottom_area" style="height: 35px; background-color: #EEF;" border="false">
						模式
						 <select id="modelSearch" name="model">
						 	<option value="" selected="selected">全部</option>
						    <option value="11">安卓用户端</option>
						    <option value="12">IOS用户端</option>
						    <option value="21">安卓商户端</option>
						    <option value="22">IOS商户端</option>
						    <option value="31">安卓配送端</option>
						    <option value="32">IOS配送端</option>
						 </select>
						状态 
						 <select id="statusSearch" name="status" >
						 	<option value="">全部</option>
						    <option value="1">上线</option>
						    <option value="0">下线</option>
						 </select>

						<a id='search' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
						
						<a id="add" href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增版本</a>
						
						<%--
						<a id="export" href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">导&nbsp;出</a>
						 --%>
						<br/>
						
				</div>
				<div region="center" style="padding: 0px;" border="false">
					<table id="table_area"></table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>