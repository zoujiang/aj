/**
 * 登陆
 * @returns
 */
var onLogin = function(path){
	$.messager.progress({msg:'登陆中……'});
	var url = path+"/admin/userAct/login";
	if($('#loginForm').form('validate')){
		var queryString = $('#loginForm').formSerialize();
		var obj = ajaxSubmit(url,queryString);
		if(obj.success){
			window.location.href = path+"/app/layout/index.jsp";
		}
		else{
			alertMsg("系统提示",obj.message);
		}
	}
	$.messager.progress('close');
};
/**
 * 重置
 * @returns
 */
var onReset = function(){
	$('#loginForm').form("reset");
};