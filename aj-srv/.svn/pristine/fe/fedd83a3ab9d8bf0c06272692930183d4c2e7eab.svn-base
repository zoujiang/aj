<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>用户管理</title>
		 
	</head>
	<body>
		<!-- begin of 用户列表 -->
		<div class="easyui-layout" fit="true" style = "min-height: 80%; border-radius: 5px 5px 5px 5px;border: 6px solid rgb(222, 222, 222);
width: 800;background: none repeat scroll 0% 0% rgb(255, 255, 255);">
<div id="content" region="center" id="right_area" 
			style="padding: 0px;margin : 2px 4px 4px 2px;border-radius: 5px 5px 5px 5px;border: 1px solid #ECECEC;" fit="false" border="true">
			
		<div id="usertoolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="userAction.addUser()">新增用户</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit"
				plain="true" onclick="userAction.updateUser()">修改用户</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove"
				plain="true" onclick="userAction.deleteUser()">删除</a>
		</div>
		<table id="userDatagrid">
		</table>
		<!-- end of 用户列表 -->

		<!-- begin of 用户编辑 -->
		<div id="userDialog" class="easyui-dialog"
			data-options="modal:true,closable:true,maximizable:false"
			style="width: 600px; height: 240px; padding: 20px 10px 0px;"
			title="用户" buttons="#user-dlg-buttons" closed="true">
			<form id="userForm" method="post" modelAttribute="user">
				<input type="hidden" name="id" />
				<table style="font-size: 14px;">
					<tr>
						<td>
							手机号码：
						</td>
						<td>
							<input type="text" name="phoneVer" style="width: 200px;"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="user-dlg-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="userAction.userSaveOrUpdate()">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
				onclick="javascript:$('#userDialog').dialog('close')">关闭</a>
		</div>
		<!-- end of 用户编辑 -->

		<!-- begin of 配置角色 -->
		<div id="configRoleDialog" class="easyui-dialog" buttons="#configRole-dlg-buttons"
			data-options="modal:true,closable:true,maximizable:false"
			style="width: 600px; height: 300px; padding: 0px" title="配置角色"
			closed="true" iconCls="icon-save">
			<table id="configRoleDatagrid" fit="true" border="false" idField="id"
				fitColumns="true">

			</table>
		</div>
		<div id="configRole-dlg-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="userAction.userSelectedRole()">确定</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
				onclick="javascript:$('#configRoleDialog').dialog('close')">关闭</a>
		</div>
		
		</div></div>
		<!-- end of 配置角色 -->

		<!--begin of script -->
		<script type="text/javascript" charset="GBK">
	//初始化
	$(function(){
		$.ajaxSetup({cache:false});
		//初始化 --> 解决Dialog的Debug
		var userDialogDebug = $("#pageContent #userDialog");
		for(var i =1;i< userDialogDebug.length;i++){
			$(userDialogDebug[i]).dialog("destroy");
		}
		var configRoleDialogDebug = $("#pageContent #configRoleDialog");
		for(var i = 1;i < configRoleDialogDebug.length;i++){
			$(configRoleDialogDebug[i]).dialog("destroy");
		}
	
		//初始化 --> 用户 --> 定义用户列表列
		$('#userDatagrid').datagrid({
			remoteSort:false,
			toolbar:"#usertoolbar",
			fit:true,
			border:false,
			idField:"id",
			rownumbers:true,
			fitColumns:true,
			singleSelect:true,
			url:"<%=mainWeb%>/admin/user/getSuperCusts",
			columns:[[
				{field:'phoneVer',title:'手机号码',width:100,align:'center',sortable:true}
			]]
		});
		
	});
	
	//用户 --> 增，删，改
	var userAction;
	$(function(){
		//用户 --> 初始化userAction类
		userAction = new UserAction();
				
		//用户 --> 全局变量 --> URL
		var url = "";
				
		//用户 --> 定义用户类(UserAction)
		function UserAction(){
			//用户 --> 新增用户 --> 弹出对话框
			this.addUser = function(){
			    
				$('#userForm').form('clear');  
				$('#userDialog').dialog('open').dialog('setTitle','新增用户');
			}
			
			//用户 --> 修改用户 --> 弹出对话框
			this.updateUser = function(){
				var row = $('#userDatagrid').datagrid('getSelected');   
				if (row){
					$.get("<%=mainWeb%>/admin/user/getSuperCustById",
							{'id':row.id},
							function(data){
								//填充form数据			
								$("#userForm input[name='id']").val(data.id);			
								$('#userDialog').dialog('open').dialog('setTitle','修改用户');
								$("#userForm input[name='phoneVer']").val(data.phoneVer);
					   			url = '<%=mainWeb%>/admin/user/updateSuperCust';
					});					
				}else{
					$.messager.alert('提示','请选择要修改的用户!','warning');
				} 		
			}
			
			//用户 --> 删除用户
			this.deleteUser = function(){
				var row = $('#userDatagrid').datagrid('getSelected');   
				if (row){
				    $.messager.confirm('删除','是否确定删除?',function(r){   
			            if (r){   
			                $.post('<%=mainWeb%>/admin/user/deleteSuperCust',{id:row.id},function(result){
			                	$('#userDatagrid').datagrid('reload');  //刷新用户列表信息   
			                },'json');		                  
			            }   
				    });
				}else{
					$.messager.alert('提示','请选择要删除的用户!','warning');
				}    
			}
					//用户 --> 保存用户
		this.userSaveOrUpdate = function(){
						
			if(validateUserForm()){	
			$.post('<%=mainWeb%>/admin/user/addSuperCust',$("#userForm").serialize(),function(data){
				if("1"==data.message){
					$.messager.alert('提示','保存成功!','info',function (){
						$('#userDatagrid').datagrid('reload');  //刷新用户列表信息
					});  
				};
				if("2"==data.message){
					$.messager.alert('提示','保存失败!','info',function (){
						$('#userDatagrid').datagrid('reload');  //刷新用户列表信息
					});  
				};
				if("3"==data.message){
					$.messager.alert('提示','用户已经存在!','info',function (){
						$('#userDatagrid').datagrid('reload');  //刷新用户列表信息
					});  
				};
				$("#userDialog").dialog('close');	//关闭用户对话框     
            },'json');	
		}
					}
						//用户 --> 保存 --> 验证 --> 验证用户表单数据的一致性
			function validateUserForm(){
			
				if($.trim($("#userForm input[name='phoneVer']").val()) == ''){
					$.messager.alert('提示','手机号码未填写!','warning'); //手机号码未填写
					return false;					
				}else{			
					return true;
				}
			}
			//公共方法 --> 获取选中的Ids
			function getSelectedIDS(targetDatagrid){
				var rows = $(targetDatagrid).datagrid('getSelections');
				var ids='';
				if(rows){
					for(var i = 0;i<rows.length;i++){
						ids += rows[i].id;
						if(i != rows.length-1){
							ids += ",";
						}
					}
				}
				return ids;
			}
		}
		
	});
</script>
	</body>
</html>