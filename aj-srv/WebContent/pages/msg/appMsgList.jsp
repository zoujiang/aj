<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>消息管理</title>
 	<script type="text/javascript">
 		function ActMgr(){
 			var $table=$("#table_act");
 			//添加
 			ActMgr.add = function(e){
 				var area=new Object();
 				area.editType="add";                                                       
 				com.eason.window.winUtils.showDialogWindowOfWH(700,500,e,mainWeb + "/pages/msg/appMsgAdd.jsp",area,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 				
 			};
 			//修改
 			ActMgr.modify = function(id,e){
 				var actMgrVo=new Object();
 				actMgrVo.actId=id;
 				actMgrVo.editType="edit";
 				actMgrVo.apr="0";
 				com.eason.window.winUtils.showDialogWindowOfWH(700,500,e,mainWeb + "/pages/msg/appMsgEdit.jsp",actMgrVo,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			//审批
 			ActMgr.approve = function(id,e){
 				var actMgrVo=new Object();
 				actMgrVo.actId=id;
 				actMgrVo.editType="edit";
 				actMgrVo.apr="1";
 				com.eason.window.winUtils.showDialogWindowOfWH(700,500,e,mainWeb + "/pages/msg/appMsgEdit.jsp",actMgrVo,function(returnValue){
 					if("success"==returnValue){
	                	$table.datagrid('reload');
               		}
 				});
 			};
 			//删除
 			ActMgr.del = function(id,e){
 				$.messager.confirm('删除','是否确定删除？',function(r){
				if(r){
						$.ajax({
							url: mainWeb + "/admin/act/deleteAct",
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
 			//详情
 			ActMgr.view = function(id,e){
 				var actMgrVo=new Object();
 				actMgrVo.actId=id;
 				actMgrVo.editType="view";
 				com.eason.window.winUtils.showDialogWindowOfWH(700,500,e,mainWeb + "/pages/msg/msgView.jsp",actMgrVo,function(returnValue){
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
					url:mainWeb + "/admin/push/message/getMessage?type=3",
				    columns:[[  
				     	{field:'msgPlatform',title:'推送设备',width:100,align:'center',sortable:false,
					formatter:function(value,rec){
						if("IOS"==value){
							return "IOS设备";
						}else if("ANDROID"==value){
							return "ANDROID设备";
						}else{
							return "所有设备";
						}
					}
				},
				
				{field:'msgSentUserGroup',title:'推送群体',width:100,align:'center',sortable:false,
					formatter:function(value,rec){
						if("2"==value){
							return "注册会员";
						} else if("3"==value){
							return "公寓用户";
						} else {
							return "用户";
						}
					}
				},
				{field:'msgTitle',title:'消息标题',width:100,align:'center',sortable:false},
				{field:'msgContent',title:'消息内容',width:200,align:'center',sortable:false},
				{field:'msgType',title:'消息类型',width:100,align:'center',sortable:false,
					formatter:function(value,rec){
						if("1"==value){
							return "短信消息";
						}else{
							return "推送信息";
						}
					}
				},
				{field:'msgFrom',title:'消息来源',width:100,align:'center',sortable:false,
					formatter:function(value,rec){
						if("0"==value){
							return "消息系统创建";
						}else{
							return "管理系统创建";
						}
					}	
				},
				{field:'msgSentDelay',title:'发送方式',width:100,align:'center',sortable:false,
					formatter:function(value,rec){
						if("0"==value){
							return "即时发送";
						}else{
							return "延迟发送";
						}
					}	
				},
						{field:'msgSentDelayTime',title:'发送时间',width:120,align:'center',sortable:false},
				     	
					   
				    	{field:'doSend',title:'发送状态',width:120,align:'center', sortable: false,
							formatter:function(value,rec){
								
								if("1"==value){
									return "推送成功";
								} else if("-1"==value){
									return "推送失败";
								}else if("0"==value){
									return "未推送";
								}
							}},
				     	
				    	{field:'action',title:'操作',width:'100',align: 'left',
				        	formatter : function(value, row, index) {
								var mod = "";
								var del = "";
								var det="";	
									if(row.doSend!="1"){
										mod += "&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"ActMgr.modify('" + row.msgId + "',event)\" >编辑&nbsp;</a></span>";

										<%-- mod = "<span  height = '23px'  width = '23px'><img src='<%=mainWeb%>/images/icon/edit.png' title='编辑'"
											+" onclick=\"ActMgr.modify('" + row.msgId + "',event)\" style='cursor: pointer'></IMG></span>"; --%>
									}
									if(row.doAudit!="1"&&row.doSend!="1"){
									<%-- del = "<span  height = '23px'  width = '23px'><img src='<%=mainWeb%>/images/icon/approve.png' title='审批'"
											+" onclick=\"ActMgr.approve('" + row.msgId + "',event)\" style='cursor: pointer'></IMG></span>"; --%>
											del += "&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"ActMgr.approve('" + row.msgId + "',event)\" >审批&nbsp;</a></span>";
									}
									det += "&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"ActMgr.view('"
										+ row.msgId + "',event)\" >查看&nbsp;</a></span>";
								<%--  det = "<span  height = '23px'  width = '23px'><img src='<%=mainWeb%>/images/icon/detail.png' title='详情' href='#' onclick=\"ActMgr.view('"
										+ row.msgId + "',event)\" style='cursor: pointer'></a></span>" --%>
								
								return mod + del  + det;
							}
				       }
				    ]]  
				}); 
 			};
 			
 			this.initActions = function(){
 				// 搜索
				$('#search_act').click(function() {
					
				
					var searchMsgFrom  = $('#searchSendPlat').combobox('getValues');

					var searchUserTel = $('#searchUserTel').val();
					
					var searchStartDt = $('#searchText_StartDt').val();
					var searchEndDt = $('#searchText_EndDt').val();	
					var reqText = "{'searchMsgFrom':'"+searchMsgFrom+"','searchUserTel':'"+searchUserTel+"','searchStartDt':'"+searchStartDt+"','searchEndDt':'"+searchEndDt+"'}";
					$table.datagrid('load',  {'req':reqText});
				});
				
				//新增订单
				$('#btn_newAct').click(function(e){
					ActMgr.add(e);
				});
 			};
 			this.init = function() {
			 	this.initTable();
			 	this.initActions();
			};
 		}
 		$(document).ready(function() {
	  		new ActMgr().init();
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
					style="width: 160px; height: 35px; background-color: #EEF;" border="false">
					推送设备&nbsp;<select id="searchSendPlat" name="searchSendPlat" class="easyui-combobox" style="width:80px;margin:0px 0px 0px 0px;">
					          			<option value="0">-请选择-</option>
										<option value="ALL">所有设备</option>
										<option value="IOS">IOS平台</option>
										<option value="ANDROID">Android平台</option>
								
									</select>
					
					
						 
						
						 发送时间&nbsp;<input class="Wdate" id='searchText_StartDt' type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 140px;height: 20px;margin-top:5px;"/>
			        	-&nbsp;<input class="Wdate" id='searchText_EndDt' type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 140px;height: 20px;margin-top:5px;"/>
						消息关键字&nbsp;<input id="searchUserTel" name="searchUserTel" type="text" style="width: 160px; height: 23px;" />
						<a id='search_act' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
						<a id="btn_newAct" href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增通知</a>
						
				</div>
				<div region="center" style="padding: 0px;" border="false">
					<table id="table_act"></table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>