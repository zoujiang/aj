/**
 * 初始化personalinfo_dialog对话框
 * @returns
 */
var init_personalinfo_dialog = function(){
	var url = getProjectName()+'/app/layout/user/userInfo.jsp';
	$('#personalinfo_dialog').dialog({
		iconCls:'icon-add',
		modal:true,
		width:600,
		height:280,
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
				var url = getProjectName()+"/userctrl/editUser.do";
				if($('#personalForm').form('validate')){
					var queryString = $('#personalForm').formSerialize();
					var obj = ajaxSubmit(url,queryString);
					if(obj.success){
						$('#personalinfo_dialog').dialog('close');
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
				$('#personalinfo_dialog').dialog('close');
			}
		}]
	});
};