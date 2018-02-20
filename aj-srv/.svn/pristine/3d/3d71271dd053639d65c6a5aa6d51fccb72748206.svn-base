/**
 * 初始化sys_user_datagrid
 */
var init_sys_user_datagrid = function(){
	var url = getProjectName() + "/userctrl/listUser.do";
	$('#sys_user_datagrid').datagrid({
		url:url,
		fit:true,
		border:false,
		pagination:true,
		idField:'id',
		pageSize:20,
		checkOnSelect:true,
		selectOnCheck:true,
		nowrap:false,
		toolbar:'#sys_user_toolbar',
		columns : [[{
			title :'编号',
			field :'id',
			checkbox:true
		},{
			title:'姓名',
			width:150,
			field:'name'
		},{
			title:'帐号',
			width:150,
			field:'account'
		},{
			title:'手机',
			width:120,
			field:'mobile'
		},{
			title:'邮箱',
			width:150,
			field:'email'
		},{
			title:'角色',
			field:'roleNames'
		},{
			title:'创建时间',
			width:120,
			field:'createDate'
		},{
			title:'最后修改时间',
			width:120,
			field:'modifyDate'
		}]]
	});
};

/**
 * 初始化useredit_dialog对话框
 * @returns
 */
var init_useredit_dialog = function(){
	var url =  getProjectName()+'/app/system/user/edit.jsp';
	$('#useredit_dialog').dialog({
		iconCls:'icon-add',
		modal:true,
		width:600,
		height:310,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		inline:false,
		closed:true,
		href:url,
		buttons: [{
			text:'保存',
			iconCls:'icon-ok',
			plain:false,
			handler:function(){
				$.messager.progress({msg:'处理中……'});
				var url =  getProjectName()+"/userctrl/editUser.do";
				if($('#usereditForm').form('validate')){
					var queryString = $('#usereditForm').formSerialize();
					var obj = ajaxSubmit(url,queryString);
					if(obj.success){
						$('#useredit_dialog').dialog('close');
						showMsg("系统提示",obj.message);
						$('#sys_user_datagrid').datagrid('reload');
					}
					else{
						showMsg("系统提示",obj.message);
					}
				}
				$.messager.progress('close');
			}
		},{
			text:'关闭',
			iconCls:'icon-cancel',
			plain:false,
			handler:function(){
				$('#useredit_dialog').dialog('close');
			}
		}]
	});
};

/**
 * 编辑用户
 * @param {} type：add,edit
 */
var editUser = function(type){
	if(type == 'edit'){
		var record = $('#sys_user_datagrid').datagrid("getSelected");
		if(record != null){
			$('#useredit_dialog').dialog({
				iconCls:"icon-edit",
				title:"编辑用户",
				onLoad : function() {
					$('#usereditForm').form('clear');
					$("input[name='oper']").val(type);
					$('#usereditForm').form('load',record);
					$("#usereditForm input[name='name']").attr("readonly","readonly");
					$("#usereditForm input[name='account']").attr("readonly","readonly");
				}
			});
			$('#useredit_dialog').dialog('open');
		}
		else{
			showMsg("系统提示","请选择一条记录。");
			return;
		}
	}
	else{
		$('#useredit_dialog').dialog({
			iconCls:"icon-add",
			title:"添加用户",
			onLoad : function() {
				$("input[name='oper']").val(type);
				$("#acc").validatebox({
					required: true,
					validType:['account','isExist','length[1,20]']
				});
			}
		});
		$('#useredit_dialog').dialog('open');
	}
};

/**
 * 删除用户
 */
var removeUser = function(){
	$.messager.confirm('系统提示', '确定删除用户吗?', function(r){
		if (r){
			var url =  getProjectName() + "/userctrl/delUser.do";
			var records = $('#sys_user_datagrid').datagrid("getSelections");
			var userIds=new Array();
			$.each(records, function(i, item){      
		          userIds.push(item.id);
		　　	});	
			var obj = ajaxSubmit(url,"userIds="+userIds.join(',').toString());
			if(obj.success){
				showMsg("系统提示",obj.message);
				$('#sys_user_datagrid').datagrid('reload');
			}
			else{
				showMsg("系统提示",obj.message);
			}
		}
	});
};

/**
 * 查询用户
 */
var searchUser = function(){
	$('#sys_user_datagrid').datagrid('load',{
		userName:$("input[name='userName']").val(),
		userAcc:$("input[name='userAcc']").val()
	});
};