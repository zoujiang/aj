<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>角色管理</title>
		 
	</head>
	<body>
		<!--角色列表开始 -->
		<div id="roletoolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="roleAction.addRole()">新增角色</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit"
				plain="true" onclick="roleAction.updateRole()">修改角色</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove"
				plain="true" onclick="roleAction.deleteRole()">删除角色</a>
		</div>
		<table id="roleDatagrid" toolbar="#roletoolbar" fit="true"
			border="false" idField="id" rownumbers="true" fitColumns="true"
			singleSelect="true" url="<%=mainWeb%>/admin/role/getRoles">

		</table>
		<!-- 角色列表结束 -->
		
		<!--角色编辑开始 -->
		<div id="roleDialog" class="easyui-dialog" closed="true" buttons="#role-dlg-buttons" closable="true"
			modal="true"
			style="width: 350px; height: 200px; padding: 30px 30px 20px 30px">
			<form id="roleForm" method="post" modelAttribute="role">
				<input type="hidden" name="id" />
				<table>
					<tr>
						<td>
							角色编码：
						</td>
						<td>
							<input type="text" name="code" style="width: 180px;" />
						</td>
					</tr>
					<tr>
						<td>
							角色名称：
						</td>
						<td>
							<input type="text" name="title" style="width: 180px;" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="role-dlg-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="roleAction.roleSaveOrUpdate()">保 存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
				onclick="javascript:$('#roleDialog').dialog('close')">关 闭</a>
		</div>
		<!-- 角色编辑结束 -->

		<!-- 配置资源开始 -->
		<div id="configResourceDialog" class="easyui-dialog" buttons="#configResource-dlg-buttons"
			data-options="modal:true,closable:true,maximizable:false,closed:true"
			style="width: 300px; height: 400px; padding: 5px" title="配置资源"
			closed="true" iconCls="icon-save">
			<ul id="configResourceTree" lines="true"
				checkbox="true">
			</ul>
		</div>
		<div id="configResource-dlg-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="roleConfigResourceAction.roleSelectedResource()">确定</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
				onclick="javascript:$('#configResourceDialog').dialog('close')">关闭</a>
		</div>
		<!-- 配置资源结束 -->

		<script type="text/javascript">
	//初始化 
	$(function(){
		//初始化 --> 角色 --> 定义角色列表列
		$('#roleDatagrid').datagrid({
			remoteSort:false,
			columns:[[
				{field:'code',title:'角色编码',width:250,align:'center',sortable:true},
				{field:'title',title:'角色名称',width:250,align:'center',sortable:true},
				{field:'id',title:'操作',width:200,align:'center', formatter:function(value,rec){return "<a href='#' class='operateChannel' onClick=roleConfigResourceAction.configureResource('"+value+"')>配置资源</a>";}}
			]]
		});
		
		//初始化 --> 处理Dialog的Debug
		var roleDialogDebug = $("#pageContent #roleDialog");
		for(var i =1;i< roleDialogDebug.length;i++){
			$(roleDialogDebug[i]).dialog("destroy");
		}
		var configResourceDialogDebug = $("#pageContent #configResourceDialog");
		for(var i = 1;i < configResourceDialogDebug.length;i++){
			$(configResourceDialogDebug[i]).dialog("destroy");
		}
	});
		
	//角色 --> 增，删，改
	var roleAction;
	$(function(){
		//实例化roleAction
		roleAction = new RoleAction();
		//角色 --> 定义RoleAction类
		function RoleAction(){
			//全局变量
			var weburl = "";
			//角色 --> 弹出新增角色对话框
			this.addRole = function(){
				$('#roleForm').form('clear'); //清空form
			  	$('#roleDialog').dialog('open').dialog('setTitle','新增角色');
			   	weburl = '<%=mainWeb%>/admin/role/addRole';
			} 
			
			//角色 --> 弹出修改角色对话框
			this.updateRole = function(){
				$('#roleForm').form('clear'); //清空form
				var row = $('#roleDatagrid').datagrid('getSelected');   
				if (row){
					$('#roleForm').form('load',row);	   
				  	$('#roleDialog').dialog('open').dialog('setTitle','修改角色');			   
				   	weburl = '<%=mainWeb%>/admin/role/updateRole';  
				}else{
					$.messager.alert('提示','请选择要修改的角色！','warning');
				} 		
			}
			
			//角色 --> 删除角色
			this.deleteRole = function(){
				var row = $('#roleDatagrid').datagrid('getSelected');  
			    if (row){   
			        $.messager.confirm('删除','是否确定删除?',function(r){   
			          if (r){
			          		$.ajax({
							  url: '<%=mainWeb%>/admin/role/deleteRole',
							  data:{id:row.id},
							  complete: function(data) {
							    $('#roleDatagrid').datagrid('reload');
							  }
							});  
			      	}});
			    }else{
			    	$.messager.alert('提示','请选择要删除的角色！','warning');
			    }
			    //清空选中的行
			    $('#roleDatagrid').datagrid('clearSelections');  
			} 
			
			//角色 --> 保存角色
			this.roleSaveOrUpdate = function(){
				$('#roleForm').form('submit',{url:weburl,
					    onSubmit:function(){return validateRoleForm();}, 
			  			contentType:'application/json;charset=UTF-8',
					    success:function(data){
					    	$.messager.alert('提示','角色保存成功!','info');//保存成功给出提示信息
					    	$("#roleDialog").dialog('close');	//关闭新增角色对话框
					    	$('#roleDatagrid').datagrid('reload');  //刷新角色列表信息  
					    	return false;
					    }   
					}); 
			}
			
			//角色 --> 保存角色 --> 验证from
			function validateRoleForm(){
				if($.trim($("#roleForm input[name='code']").val()) == ''){
					$.messager.alert('提示','角色编码未填写!','warning'); //角色编码未填写
					return false;
				}
				if($.trim($("#roleForm input[name='title']").val()) == ''){
					$.messager.alert('提示','角色名称未填写!','warning'); //角色名称未填写
					return false;
				}		
				return true;
			}
		}
		
	});	
	
	//配置资源
	var roleConfigResourceAction;
	$(function(){
		//配置资源 --> 初始化RoleConfigResourceAction类
		roleConfigResourceAction = new RoleConfigResourceAction();
		 
		//配置资源 --> 定义RoleConfigResourceAction类
		var configRoleId = ''; //当前配置资源的角色ID
		function RoleConfigResourceAction(){
			//配置资源 --> 打开资源对话框
			this.configureResource = function(roleId){
				$('#configResourceTree').tree({  
				    url:"<%=mainWeb%>/admin/role/getResourceOfRole?roleId="+roleId 
				}); 
				$("#configResourceDialog").dialog("open");
				configRoleId = roleId;
			}
			
			//配置资源 --> 完成资源选择
			this.roleSelectedResource = function(){
				var ids = getSelectedIDS("#configResourceTree");
				if(ids == ''){
					$.messager.alert('提示','请选择该角色的资源!','warning');
				}else{
					$.post('<%=mainWeb%>/admin/role/roleConfigResource',{'ids[]':ids,'id':configRoleId});
					$("#configResourceDialog").dialog('close');
					$('#roleDatagrid').datagrid('reload');  //刷新角色列表信息   
				}
			}
		}
		
		
		//配置资源 --> 展开节点 --> 点击展开节点
		$('#configResourceTree').tree({
			onExpand: function(node){
				//判断是否是根节点
				node.id = typeof(node.id) == "undefined" ? '' : node.id;
				//判断是否已经追加过子节点
				if(isHasNodes(node)==false){
					//从服务器获取子节点
					getNodesFormServer(node);
				}
			}
		});
		
		//配置资源 --> 展开节点 --> 从服务器获取子节点
		function getNodesFormServer(node){
			$.ajax({
			  url: "<%=mainWeb%>/admin/role/getResourceOfRole",
			  type: "POST", 
	 		  data: {id : node.id},
			  success: function(data) {
			  	//将子节点添加到父节点
			  	showNodes(node,data);
			  }
			});	
		}
		
		//配置资源 --> 展开节点 --> 添加子节点到父节点
		function showNodes(node,resourceJSON){
			$('#configResourceTree').tree('append', {
				parent: node.target,
				data:resourceJSON
			});
		}
		
		//配置资源 --> 展开节点 --> 判断该节点是否有子节点（如果已经有子节点，不追加;否则追加）
		function isHasNodes(node){
			return $('#configResourceTree').tree('getChildren',node.target) != "";
		} 
		
		//公共方法 --> 获取选中的Ids
		function getSelectedIDS(targetTree){
			var parents = $(targetTree).tree('getChecked', 'indeterminate');
			var childrens = $(targetTree).tree('getChecked');
			var ids='';
			if(parents){
				for(var i = 0;i<parents.length;i++){
					ids += parents[i].id;
					ids += ",";
				}
			}
			if(childrens){
				for(var i = 0;i<childrens.length;i++){
					ids += childrens[i].id;
					if(i != childrens.length-1){
						ids += ",";
					}
				}
			}
			return ids;
		}
	});
	
</script>
	</body>
</html>

