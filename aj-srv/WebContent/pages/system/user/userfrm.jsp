<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>用户管理</title>
		 
	</head>
	<body>
		<!-- begin of 用户列表 -->
		<div id="usertoolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="userAction.addUser()">新增用户</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit"
				plain="true" onclick="userAction.updateUser()">修改用户</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove"
				plain="true" onclick="userAction.deleteUser()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-redo"
				plain="true" onclick="userAction.configureRole()">配置角色</a>	
			<!-- 	
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove"
				plain="true" onclick="userAction.batchDelete()">批量删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-redo"
				plain="true" onclick="userAction.batchEnabled()">批量启用</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-undo"
				plain="true" onclick="userAction.batchUnabled()">批量禁用</a>
			-->
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
							姓名：
						</td>
						<td>
							<input type="text" name="name" style="width: 200px;" /><font color='red'>*</font>
						</td>
						<td>
							账户名：
						</td>
						<td>
							<input type="text" name="account" style="width: 200px;" 
								onblur="userAction.validateAccountIsExists()"/>
								<font color='red'>*</font>
						</td>
					</tr>
					<tr>
						<td>
							固话：
						</td>
						<td>
							<input type="text" name="tele" style="width: 200px;" />
						</td>
						<td>
							手机号码：
						</td>
						<td>
							<input type="text" name="mobile" style="width: 200px;" 
								onblur="userAction.validateMobileIsExists()"/><font color='red'>*</font>
						</td>
					</tr>
					<tr>
						<td>
							密码：
						</td>
						<td>
							<input type="password" name="pwd" style="width: 200px;" /><font color='red'>*</font>
						</td>
						<td>
							重复密码：
						</td>
						<td>
							<input type="password" name="rePwd" style="width: 200px;" /><font color='red'>*</font>
						</td>
					</tr>
					<tr>
						<td>
							Email：
						</td>
						<td>
							<input type="text" name="email" style="width: 200px;" />
								<!-- onblur="userAction.validateEmailIsExists()" -->
						</td>
						<td>
							是否启用：
						</td>
						<td>
							<input type="checkbox" name="isEnabled" value="1" checked="checked" />
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
			url:"<%=mainWeb%>/admin/user/getUsers",
			columns:[[
				{field:'name',title:'姓名',width:100,align:'center',sortable:true},
				{field:'account',title:'账户名',width:100,align:'center',sortable:true},
				{field:'tele',title:'固话',width:100,align:'center',sortable:true},
				{field:'mobile',title:'手机号码',width:100,align:'center',sortable:true},				
				{field:'email',title:'Email',width:100,align:'center',sortable:true},
				{field:'isEnabled',title:'启用状态',width:100,align:'center',sortable:true,
					formatter:function(value,rec){
						if("1"==value){
							return "已启用";
						}else{
							return "未启用";
						}
					}
				}
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
				url = '<%=mainWeb%>/admin/user/addUser'; 
				//启用用户名/账号输入框
				$("#userForm input[name='name']").removeAttr("disabled");
				$("#userForm input[name='account']").removeAttr("disabled");
			}
			
			//用户 --> 修改用户 --> 弹出对话框
			this.updateUser = function(){
				var row = $('#userDatagrid').datagrid('getSelected');   
				if (row){
					$.get("<%=mainWeb%>/admin/user/getUserById",{'id':row.id},function(data){
						//填充form数据						
						$("#userForm input[name='id']").val(data.id);
						$("#userForm input[name='name']").val(data.name);
						$("#userForm input[name='account']").val(data.account);
						$("#userForm input[name='tele']").val(data.tele);
						$("#userForm input[name='mobile']").val(data.mobile);
						$("#userForm input[name='pwd']").val(data.pwd);
						$("#userForm input[name='rePwd']").val(data.pwd);
						$("#userForm input[name='email']").val(data.email);
						if(data.isEnabled == "1")$("#userForm input[name='isEnabled']").attr("checked",'true');
						//打开对话框
					   	$('#userDialog').dialog('open').dialog('setTitle','修改用户');
					    $("#userForm input[name='rePwd']").val($("#userForm input[name='pwd']").val()); 
					   	url = '<%=mainWeb%>/admin/user/updateUser';
					   	//禁止用户名/账号输入框
					   // $("#userForm input[name='name']").attr("disabled","disabled");
					   	$("#userForm input[name='account']").attr("disabled","disabled");
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
			                $.post('<%=mainWeb%>/admin/user/deleteUser',{id:row.id},function(result){
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
				$('#userForm').form('submit',{
					url:url,
				    onSubmit:function(){		    	
				    	return validateUserForm();   
				    },   
				    success:function(data){
				    	$.messager.alert('提示','用户保存成功!','info');//保存成功给出提示信息
				    	$("#userDialog").dialog('close');	//关闭用户对话框
				    	$('#userDatagrid').datagrid('reload');  //刷新用户列表信息
				    }   
				}); 
			}
			
			//用户 -->  验证 --> 验证【账号】是否已存在
			this.validateAccountIsExists = function(){	
				//判断账号是否存在
				var id = $("#userForm input[name='id']").val();				
				var account = $.trim($("#userForm input[name='account']").val());
				 $.get('<%=mainWeb%>/admin/user/checkUserAccount',{id:id,account:account},function(result){
		         	if(result){
		         		$.messager.alert('提示','账号已存在!','warning');
		         		$("#userForm input[name='account']").val("");
		         	}
		         },'json');		         		         
			}
			
			//用户 -->  验证 --> 验证【手机】是否已存在
			this.validateMobileIsExists = function(){	
				//判断手机是否存在
				var id = $("#userForm input[name='id']").val();
				var mobile = $.trim($("#userForm input[name='mobile']").val());
				 $.get('<%=mainWeb%>/admin/user/checkUserMobile',{id:id,mobile:mobile},function(result){
		         	if(result){
		         		$.messager.alert('提示','手机号码已存在!','warning');
		         		$("#userForm input[name='mobile']").val("");
		         	}
		         },'json');		         
			}
			
			//用户 -->  验证 --> 验证【Email】是否已存在
			this.validateEmailIsExists = function(){
				//判断邮箱是否存在
				var id = $("#userForm input[name='id']").val();
		        var email = $.trim($("#userForm input[name='email']").val());				
				$.get('<%=mainWeb%>/admin/user/checkUseEmail',{id:id,email:email},function(result){
		        	if(result){
		         		$.messager.alert('提示','邮箱已存在!','warning');
		         		$("#userForm input[name='email']").val("");
		         	}
		        },'json');		         
			}
					        
			//用户 --> 保存 --> 验证 --> 验证用户表单数据的一致性
			function validateUserForm(){
				if($.trim($("#userForm input[name='name']").val()) == ''){
					$.messager.alert('提示','姓名未填写!','warning'); //姓名未填写
					return false;
				}
				if($.trim($("#userForm input[name='account']").val()) == ''){
					$.messager.alert('提示','账号未填写!','warning'); //账号未填写
					return false;
				}
				if($.trim($("#userForm input[name='mobile']").val()) == ''){
					$.messager.alert('提示','手机号码未填写!','warning'); //手机号码未填写
					return false;
				}
				/* if($.trim($("#userForm input[name='email']").val()) == ''){
					$.messager.alert('提示','邮箱未填写!','warning'); //邮箱未填写
					return false;
				} */
				if($.trim($("#userForm input[name='email']").val()) != ''){
					var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
					if(!emailReg.test($.trim($("#userForm input[name='email']").val()))){
						$.messager.alert('提示','邮箱格式不正确!','warning'); //手机号码未填写
						return false;
					}
				}
				if($("#userForm input[name='pwd']").val() != $("#userForm input[name='rePwd']").val()){
					$.messager.alert('提示','密码不一致!','warning');
					$("#userForm input[name='pwd']").val("");
					$("#userForm input[name='rePwd']").val("");
					return false;
				}
				return true;
			}
			
			//批量处理 --> 批量启用用户
			this.batchEnabled = function(){
				var ids = getSelectedIDS("#userDatagrid"); //获取批量处理的ID
				if(ids == ''){
					$.messager.alert('提示','请选择要批量启用的用户!','warning');
				}else{
					$.post('<%=mainWeb%>/admin/user/batchEnabled',{'ids[]':ids},
							function(){
								$('#userDatagrid').datagrid('reload');  //刷新用户列表信息
							}
					);
				}
				
			}
			
			//批量处理 --> 批量禁用用户
			this.batchUnabled = function(){
				var ids = getSelectedIDS("#userDatagrid"); //获取批量处理的ID
				if(ids == ''){
					$.messager.alert('提示','请选择要批量禁用的用户!','warning');
				}else{
					$.post('<%=mainWeb%>/admin/user/batchUnabled',{'ids[]':ids},
							function(){
								$('#userDatagrid').datagrid('reload');  //刷新用户列表信息
							}
					);
				}
			}
			
			//批量处理 --> 批量删除用户
			this.batchDelete = function(){
				var ids = getSelectedIDS("#userDatagrid"); //获取批量处理的ID
				if(ids == ''){
					$.messager.alert('提示','请选择要批量删除的用户!','warning');
				}else{
					$.messager.confirm('删除','是否确定删除?',function(isSure){
						if(isSure){
							$.post('<%=mainWeb%>/admin/user/batchDelete',{'ids[]':ids},
							  function(){
								$('#userDatagrid').datagrid('reload');  //刷新用户列表信息
							  }
							);
						}
					}); 
				}
			}
			
			//配置角色 --> 打开角色对话框
			var configUserId = ''; //配置角色的用户ID
			
			this.configureRole = function(){
				var row = $('#userDatagrid').datagrid('getSelected');
				if(!row){
					$.messager.alert('提示','请选择用户!','warning');
					return;
				}
				$("#configRoleDialog").dialog('open');
				$('#configRoleDatagrid').datagrid({
					onLoadSuccess: function (data){
						$("#configRoleDatagrid").datagrid("uncheckAll");
						for(var i=0;i<data.rows.length;i++){
							if(data.rows[i].ck == true){
								$("#configRoleDatagrid").datagrid("checkRow",i);
							}
						}
					},
					url:"<%=mainWeb%>/admin/user/getRolesOfUser/"+row.id,
					columns:[[
						{field:'ck',checkbox:true},
						{field:'code',title:'角色编码',width:50,align:'center'},
						{field:'title',title:'角色名称',width:50,align:'center'}
					]]
					
				});
				configUserId = row.id;
			}
			
			//配置角色 --> 确定选择
			this.userSelectedRole = function(){
				var ids = getSelectedIDS("#configRoleDatagrid"); //获取批量处理的ID
				if(ids == ''){
					$.messager.alert('提示','请选择该用户的角色!','warning');
				}else{
					$.post('<%=mainWeb%>/admin/user/assignRoles',{'ids[]':ids,'id':configUserId}
						,function(){
							$("#configRoleDialog").dialog('close');
							$('#userDatagrid').datagrid('reload');  //刷新用户列表信息
						}
						,'json'
					);
					
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