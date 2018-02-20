<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广告内容管理</title>
 	<script type="text/javascript">
 		function PageMgr(){
 			var $table=$("#table_area");
 			
 			
 			//编辑
 			PageMgr.edit = function(id,e){
 				var obj = new Object();
 				obj.id = id;
 				
 				com.eason.window.winUtils.showDialogWindowOfWH(800,500,e,mainWeb + "/pages/ad/ad/edit.jsp",obj,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			//新增
 			PageMgr.add = function(e){
 				var obj = new Object();
 				com.eason.window.winUtils.showDialogWindowOfWH(800,500,e,mainWeb + "/pages/ad/ad/edit.jsp",obj,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			//新增内容
 			PageMgr.addContentIos = function(adid,e){
 				var obj = new Object();
 				obj.adid = adid;
 				obj.posType = "ios";
 				obj.action = "add";
 				com.eason.window.winUtils.showDialogWindowOfWH(800,500,e,mainWeb + "/pages/ad/ad/content.jsp",obj,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			//新增内容
 			PageMgr.addContentAndroid = function(adid,e){
 				var obj = new Object();
 				obj.adid = adid;
 				obj.posType = "android";
 				obj.action = "add";
 				com.eason.window.winUtils.showDialogWindowOfWH(800,500,e,mainWeb + "/pages/ad/ad/content.jsp",obj,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			
 			//新增内容
 			PageMgr.addContentWxh5 = function(adid,e){
 				var obj = new Object();
 				obj.adid = adid;
 				obj.posType = "wxh5";
 				obj.action = "add";
 				com.eason.window.winUtils.showDialogWindowOfWH(800,500,e,mainWeb + "/pages/ad/ad/content.jsp",obj,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			
 			//修改内容
 			PageMgr.editContent = function(contentid,e){
 				var obj = new Object();
 				obj.id = contentid;
 				obj.action = "edit";
 				com.eason.window.winUtils.showDialogWindowOfWH(800,500,e,mainWeb + "/pages/ad/ad/content.jsp",obj,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			
 			//上线
 			PageMgr.online = function(id,e){
 				$.messager.confirm('提示','确定要上线这个广告吗？',function(r){
					if (r) {
						$.ajax({
							url: mainWeb + "/admin/ad/online",
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
 				$.messager.confirm('提示','确定要下线这个广告吗？',function(r){
					 if (r) {
						$.ajax({
							url: mainWeb + "/admin/ad/offline",
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
					url:mainWeb + "/admin/ad/list",
				    columns:[[  
				     	{field:'name',title:'广告名称',width:180,align:'center', sortable: false},
				     	{field:'time',title:'有效时间',width:180,align:'center', sortable: false,
				     		formatter : function(value, row, index) {
				        		return row.online_timeStr + " -> " + row.offline_timeStr;
				       		}
				     	},
				       	
				       	{field:'_content',title:'广告内容',width:140,align:'center', 
				        	formatter : function(value, row, index) {
				        		var url = "";
				        		if (row.type.indexOf("ios") > -1) {
				        			if (!row.ios) {
					        			url += "IOS:<span  height='23px' width='23px'><a style='color:blue;' href='#' onclick=\"PageMgr.addContentIos('" + row.id + "',event)\" >添加</a></span>";
				        			} else {
				        				url += "IOS:<span  height='23px' width='23px'><a style='color:blue;' href='#' onclick=\"PageMgr.editContent('" + row.ios.id + "',event)\" >修改</a></span>";
				        			}
				        		}
				        		if (row.type.indexOf("android") > -1) {
				        			if (!row.android) {
					        			url += "&nbsp;&nbsp;Android:<span  height='23px' width='23px'><a style='color:blue;' href='#' onclick=\"PageMgr.addContentAndroid('" + row.id + "',event)\" >添加</a></span>";
				        			} else {
					        			url += "&nbsp;&nbsp;Android:<span  height='23px' width='23px'><a style='color:blue;' href='#' onclick=\"PageMgr.editContent('" + row.android.id + "',event)\" >修改</a></span>";
				        				
				        			}
				        		}
				        		if (row.type.indexOf("wxh5") > -1) {
				        			if (!row.wxh5) {
					        			url += "&nbsp;&nbsp;微信H5:<span  height='23px' width='23px'><a style='color:blue;' href='#' onclick=\"PageMgr.addContentWxh5('" + row.id + "',event)\" >添加</a></span>";
				        			} else {
					        			url += "&nbsp;&nbsp;微信H5:<span  height='23px' width='23px'><a style='color:blue;' href='#' onclick=\"PageMgr.editContent('" + row.wxh5.id + "',event)\" >修改</a></span>";
				        				
				        			}
				        		}
				        		return url;
				       		}
				       	},
				       	
				       	{field:'rels',title:'投放广告位名称/排序',width:250,align:'left', 
				        	formatter : function(value, row, index) {
				        		var url = "";
				        		if (row.ios && row.ios.rels) {
				        			
				        			for (var i=0; i < row.ios.rels.length; i++) {
				        				var o = row.ios.rels[i];
				        				
				        				url += o.pos_name + "/" + o.seq + "<br/>";
				        				
				        			}
				        			
				        		}
				        		
								if (row.android && row.android.rels) {
				        			
				        			for (var i=0; i < row.android.rels.length; i++) {
				        				var o = row.android.rels[i];
				        				
				        				url += o.pos_name + "/" + o.seq + "<br/>";
				        				
				        			}
				        			
				        		}
				        		
				        		return url;
				       		}
				       	},
				     	//{field:'adcount',title:'数量',width:30,align:'center', sortable: false}, 
				     	
				     	{field:'statusText',title:'状态',width:40,align:'center', 
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
					var createUser = $('#createUser').val();
					
					var status = $('#status').combobox('getValue');
					
					json = {'name':name,'status':status,'createUser':createUser};
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
						
						状态 
						 <select id="status" name="status" >
						 	<option value="">全部</option>
						    <option value="1">上线</option>
						    <option value="0">下线</option>
						 </select>
						 

						<a id='search' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
						
						<a id="add" href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
						
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