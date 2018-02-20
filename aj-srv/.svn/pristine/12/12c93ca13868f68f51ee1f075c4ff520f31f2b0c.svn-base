/**
 * 初始化sys_role_datagrid
 */
var init_sys_role_datagrid = function(){
	var url = getProjectName() + "/rolectrl/listRole.do";
	$('#sys_role_datagrid').datagrid({
		url:url,
		fit:true,
		striped:true,
		border:false,
		pagination:true,
		idField:'id',
		pageSize:20,
		//checkOnSelect:true,
		selectOnCheck:true,
		nowrap:false,
		toolbar:'#sys_role_toolbar',
		columns : [[{
			title :'编号',
			field :'id',
			checkbox:true
		},{
			title:'角色名称',
			width:150,
			field:'name'
		},{
			title:'权限',
			field:'resourceNames'/*,
			formatter:function(value, row){
				return '<a href="javascript:alert(\'111111\')" title="查询">查询</a> ';
			}*/
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
 * 初始化roleedit_dialog对话框
 * @returns
 */
var init_roleedit_dialog = function(){
	var url = getProjectName()+'/app/system/role/edit.jsp';
	$('#roleedit_dialog').dialog({
		iconCls:'icon-add',
		modal:true,
		width:600,
		height:310,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		inline:false,
		closed:true,
		href: url,
		buttons: [{
			text:'保存',
			iconCls:'icon-ok',
			plain:false,
			handler:function(){
				$.messager.progress({msg:'处理中……'});
				var url = getProjectName()+"/rolectrl/editRole.do";
				if($('#roleeditForm').form('validate')){
					var queryString = $('#roleeditForm').formSerialize();
					var obj = ajaxSubmit(url,queryString);
					if(obj.success){
						$('#roleedit_dialog').dialog('close');
						showMsg("系统提示",obj.message);
						$('#sys_role_datagrid').datagrid('reload');
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
				$('#roleedit_dialog').dialog('close');
			}
		}]
	});
};

/**
 * 编辑用户
 * @param {} type：add,edit
 */
var editRole = function(type){
	if(type == 'edit'){
		var record = $('#sys_role_datagrid').datagrid("getSelected");
		if(record != null){
			$('#roleedit_dialog').dialog({
				iconCls:"icon-edit",
				title:"编辑角色",
				onLoad : function() {
					$('#roleeditForm').form('clear');
					$("input[name='oper']").val(type);
					$('#roleeditForm').form('load',record);
				}
			});
			$('#roleedit_dialog').dialog('open');
		}
		else{
			showMsg("系统提示","请选择一条记录。");
			return;
		}
	}
	else{
		$('#roleedit_dialog').dialog({
			iconCls:"icon-add",
			title:"添加角色",
			onLoad : function() {
				$("input[name='oper']").val(type);
			}
		});
		$('#roleedit_dialog').dialog('open');
	}
};
/**
 * 删除角色
 */
var delRole = function(){
	$.messager.confirm('系统提示', '确定删除角色吗?', function(r){
		if (r){
			var url = getProjectName() + "/rolectrl/delRole.do";
			var records = $('#sys_role_datagrid').datagrid("getSelections");
			var roleIds=new Array();
			$.each(records, function(i, item){      
		          roleIds.push(item.id);
		　　	});	
			var obj = ajaxSubmit(url,"roleIds="+roleIds.join(',').toString());
			if(obj.success){
				showMsg("系统提示",obj.message);
				$('#sys_role_datagrid').datagrid('reload');
			}
			else{
				showMsg("系统提示",obj.message);
			}
		}
	});
};
/**
 * 查找角色
 */
var searchRole = function(){
	$('#sys_role_datagrid').datagrid('load',{
		roleName:$("input[name='roleName']").val()
	});
};