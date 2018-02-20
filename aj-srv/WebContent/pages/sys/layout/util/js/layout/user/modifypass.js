/**
 * 初始化modifypass_dialog对话框
 * @returns
 */
var init_modifypass_dialog = function(){
	var url = getProjectName()+'/pages/sys/layout/user/modifyPass.jsp';
	$('#modifypass_dialog').dialog({
		iconCls:'icon-add',
		modal:true,
		width:300,
		height:200,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		//closable:false,
		inline:false,
		closed:true,
		href: url,
		buttons: [{
			text:'保存',
			iconCls:'icon-ok',
			plain:false,
			handler:function(){
				$.messager.progress({msg:'处理中……'});
				var url =  getProjectName()+"/admin/user/updatePwd";
				if($('#passForm').form('validate')){
					var queryString = $('#passForm').formSerialize();
					var obj = ajaxSubmit(url,queryString);
					if(obj.result){
						showMsg("系统提示",obj.message);
						$('#modifypass_dialog').dialog('close');
						//logout(path);
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
				$('#modifypass_dialog').dialog('close');
			}
		}]
	});
};