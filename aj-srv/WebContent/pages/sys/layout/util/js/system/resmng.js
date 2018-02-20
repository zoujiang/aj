/**
 * 初始化sys_res_treegrid
 */
var init_sys_res_treegrid = function(){
	var url = getProjectName() + "/menuctrl/listTree.do";
	$('#sys_res_treegrid').treegrid({
		url:url,
		idField : 'id',
		treeField : 'text',
		fit : true,
		lines : true,
		toolbar:'#sys_res_toolbar',
		frozenColumns:[[{
			title : '编号',
			field : 'id',
			width : 150,
			checkbox:true
		},{
			field:'text',
			title:'资源名称',
			width:200
		}]],
		columns:[[{
			field:'url',
			title:'资源路径'/*,
			width:200*/
		},{
			field:'method',
			title:'资源方法'/*,
			width:120*/
		},{
			field:'seq',
			title:'排序',
			width:50
		},{
			field:'type',
			title:'类型',
			width:80,
			formatter:function(val,row,index){
				if(val == 'menu'){
					return '菜单';
				}
				else if(val == 'button'){
					return '操作';
				}
				else if(val == 'list'){
					return '列表';
				}
				else if(val == 'link'){
					return '链接';
				}
				else if(val == 'relate'){
					return '相关';
				}
				else{
					return '其它';
				}
			}
		},{
			field:'remark',
			title:'备注'
		}]]
	});
};

/**
 * 初始化resedit_dialog对话框
 * @returns
 */
var init_resedit_dialog = function(){
	var url = getProjectName()+'/app/system/res/edit.jsp';
	$('#resedit_dialog').dialog({
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
				var url = getProjectName()+"/menuctrl/editRes.do";
				if($('#reseditForm').form('validate')){
					var queryString = $('#reseditForm').formSerialize();
					var obj = ajaxSubmit(url,queryString);
					if(obj.success){
						$('#resedit_dialog').dialog('close');
						showMsg("系统提示",obj.message);
						$('#sys_res_treegrid').treegrid('reload');
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
				$('#resedit_dialog').dialog('close');
			}
		}]
	});
};

/**
 * 编辑用户
 * @param {} type：add,edit
 */
var editRes = function(type){
	if(type == 'edit'){
		var record = $('#sys_res_treegrid').treegrid("getSelected");
		if(record != null){
			$('#resedit_dialog').dialog({
				iconCls:"icon-edit",
				title:"编辑资源",
				onLoad : function() {
					$('#reseditForm').form('clear');
					$("input[name='oper']").val(type);
					$('#reseditForm').form('load',record);
				}
			});
			$('#resedit_dialog').dialog('open');
		}
		else{
			showMsg("系统提示","请选择一条记录。");
			return;
		}
	}
	else{
		$('#resedit_dialog').dialog({
			iconCls:"icon-add",
			title:"添加资源",
			onLoad : function() {
				$("input[name='oper']").val(type);
			}
		});
		$('#resedit_dialog').dialog('open');
	}
};
/**
 * 删除资源
 */
var removeRes = function(){
	$.messager.confirm('系统提示', '确定删除资源吗?', function(r){
		if (r){
			var url = getProjectName() + "/menuctrl/delRes.do";
			var records = $('#sys_res_treegrid').treegrid("getSelections");
			var resIds=new Array();
			$.each(records, function(i, item){      
		          resIds.push(item.id);
		　　	});	
			var obj = ajaxSubmit(url,"resIds="+resIds.join(',').toString());
			if(obj.success){
				showMsg("系统提示",obj.message);
				$('#sys_res_treegrid').treegrid('reload');
			}
			else{
				showMsg("系统提示",obj.message);
			}
		}
	});
};

/**
 * 移动资源
 * @param {} type
 */
var seqRes = function(type){
	var url = getProjectName() + "/menuctrl/seqRes.do";
	var record = $('#sys_res_treegrid').treegrid("getSelected");
	if(record != null){
		var obj = ajaxSubmit(url,"resId="+record.id+"&type="+type);
		if(obj.success){
			showMsg("系统提示",obj.message);
			$('#sys_res_treegrid').treegrid('reload');
		}
		else{
			showMsg("系统提示",obj.message);
		}
	}
	else{
		showMsg("系统提示","请选择一条记录。");
		return;
	}
}