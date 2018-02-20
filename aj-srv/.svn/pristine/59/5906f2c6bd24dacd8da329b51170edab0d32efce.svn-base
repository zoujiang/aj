/**
 * 回首页
 * @returns
 */
var toHome = function(){
	var href = getProjectName() + "/pages/sys/layout/index.jsp"
	layout_center_addTabFun({
		title : "首页",
		closable : false,
		iconCls : "icon-home",
		href : href
	});
};




/**
 * 查看（修改）个人信息
 */
var userInfo = function(){
	$.messager.progress({msg:'加载中……'});
	var url =  getProjectName() +"/userctrl/loadUser.do";
	var obj = ajaxSubmit(url,'');
	if(obj.success){
		$('#personalinfo_dialog').dialog({
			iconCls:"icon-edit",
			title:"个人信息",
			onLoad : function() {
				$('#personalForm').form('clear');
				$("input[name='oper']").val("personal");
				$('#personalForm').form('load',obj.obj);
			}
		});
		$('#personalinfo_dialog').dialog('open');
	}
	else{
		showMsg("系统提示",obj.message);
	}
	$.messager.progress('close');
};
/**
 * 修改个人密码
 */
var modifyPass = function(){
	$('#modifypass_dialog').dialog({iconCls:"icon-edit",title:"修改密码"});
	$('#modifypass_dialog').dialog('open');
};

/**
 * 更换皮肤
 * @param {} themeName
 */
var changeTheme = function(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for ( var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			$(ifr).contents().find('#easyuiTheme').attr('href', href);
		}
	}

	$.cookie('easyuiThemeName', themeName, {
		expires : 7
	});
};
