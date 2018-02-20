<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广告位管理</title>
 	<script type="text/javascript">
 		function PageMgr(){
 			var $table=$("#table_area");
 			
 			
 			//编辑
 			PageMgr.edit = function(id,e){
 				var obj = new Object();
 				obj.id = id;
 				
 				com.eason.window.winUtils.showDialogWindowOfWH(800,500,e,mainWeb + "/pages/ad/position/edit.jsp",obj,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			//新增
 			PageMgr.add = function(e){
 				var obj = new Object();
 				com.eason.window.winUtils.showDialogWindowOfWH(800,500,e,mainWeb + "/pages/ad/position/edit.jsp",obj,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			//广告
 			PageMgr.relationList = function(id, e){
 				var obj = new Object();
 				obj.id = id;
 				com.eason.window.winUtils.showDialogWindowOfWH(800,500,e,mainWeb + "/pages/ad/position/adList.jsp",obj,function(returnValue){
 					if("success"==returnValue){
	                	//$table.datagrid('reload');
               		}
 				});
 			};
 			
 			//上线
 			PageMgr.online = function(id,e){
 				$.messager.confirm('提示','确定要上线这个广告位吗？',function(r){
					if (r) {
						$.ajax({
							url: mainWeb + "/admin/position/online",
							data: 'id=' + id,
							dataType: 'json',
							success: function(data) {
								$.messager.alert('提示', data.msg, 'info', function() {
									
									if (data.error) {
										return;
									}
									$table.datagrid('reload');
								  });
							   }
						     });
					} 
				});
 			};
 			
 			//下线
 			PageMgr.offline = function(id,e){
 				$.messager.confirm('提示','确定要下线这个广告位吗？',function(r){
					 if (r) {
						$.ajax({
							url: mainWeb + "/admin/position/offline",
							data: 'id=' + id,
							dataType: 'json',
							success: function(data) {
								$.messager.alert('提示', data.msg, 'info', function() {
									
									if (data.error) {
										return;
									}
									$table.datagrid('reload');
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
					url:mainWeb + "/admin/position/list",
				    columns:[[  
				     	{field:'code',title:'广告位编码',width:80,align:'center', sortable: false},
				     	{field:'name',title:'广告位名称',width:280,align:'center', sortable: false},
				     	{field:'image',title:'预览图片',width:80,align:'center', 
				     		formatter : function(value, row, index) {
				     			if (value == null || value == undefined || value == '') {
				     				return "";
				     			} else {
				     				return "<a href=\"#\" onclick=\"previewImage(event,'" + value + "')\">查看</a>&nbsp;";
				     			}
			       			}
				     	},
				     	//{field:'adcount',title:'数量',width:30,align:'center', sortable: false}, 
				     	
				     	{field:'statusText',title:'状态',width:80,align:'center', 
				        	formatter : function(value, row, index) {
				        		return row.statusText;
				       		}
				       	},
				     	{field:'createUser',title:'创建人',width:80,align:'center',sortable: false},
				       	
				       	
				    	{field:'action',title:'操作',width:'100',align: 'left',
				        	formatter : function(value, row, index) {
								var mod = "";
								
								if (!row.edit) {
									return "";
								}
								
								mod += "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"PageMgr.edit('" + row.id + "',event)\" >修改</a></span>";
								
								if (row.status == "1") {  //上线  --下线
									mod += "&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"PageMgr.offline('" + row.id + "',event)\" >下线</a></span>";
								}
								
								if (row.status == "0") {  //下线 --上线
									mod += "&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"PageMgr.online('" + row.id + "',event)\" >上线</a></span>";
								}
								mod += "&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"PageMgr.relationList('" + row.id + "',event)\" >广告</a></span>";
								
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
					var name = $('#name').val();
					var code = $('#code').val();
					var createUser = $('#createUser').val();
					
					var status = $('#status').combobox('getValue');
					
					json = {'name':name,'code':code,'status':status,'createUser':createUser};
					//alert(JSON.stringify(json));
					
					$table.datagrid('load',  json);
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
						名称 <input id="name" name="name" type="text" style="width:120px; height: 23px;" />
						编码 <input id="code" name="code" type="text" style="width:60px; height: 23px;" />
						
						状态 
						 <select id="status" name="status" >
						 	<option value="">全部</option>
						    <option value="1">上线</option>
						    <option value="0">下线</option>
						 </select>
						 
				        			
				        			

						<a id='search' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
						
						<a id="add" href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增广告位</a>
						
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