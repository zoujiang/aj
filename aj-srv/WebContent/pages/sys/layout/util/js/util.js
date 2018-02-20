/**
 *获取工程名，如：/msfx
 */
var getProjectName = function(){
	var pathName=window.document.location.pathname; 
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
	return projectName;
}
/**
 * 提示窗口
 * @param title
 * @param msg
 * @returns
 */
var showMsg = function (title,msg) {
	$.messager.show({
		title:title,
		msg:msg,
		timeout:5000,
		showType:'slide'
	});
};
var alertMsg = function(title,msg){
	$.messager.alert(title,msg,'info');
};

/**
 * 退出登陆
 */
var logout = function(){
	//alert("sss");
	$.messager.progress({msg:'退出登录中'});
	
		window.location.href = getProjectName() +"/admin/user/logout";
};

/**
 * 系统初始化
 */
var init = function(){
	$.messager.progress({msg:'初始化系统……'});
	var url = getProjectName() +"/initctrl/init.do";
	var obj = ajaxSubmit(url,'');
	if(obj.success){
		window.location.href = "login.jsp";
	}
	showMsg("系统提示",obj.message);
};

var ajaxSubmit = function(url,queryString){
	var obj=new Object();
	obj.success = false;
	$.ajax({
		type:'POST',
		url:url,
		data:queryString,
		async: false,
		success:function(data){
			console.log(data);
			//var resText=XHR.responseText;
			//alert(data);
	        if(data.indexOf("sessionState")!=-1 && data.indexOf("loginUrl")!=-1){
	        	  window.top.location.href="/pages/login/login.jsp";
	        }else{
			obj = JSON.parse(data);
	        }
		}, 
        error: function (XMLHttpRequest, textStatus, errorThrown) { 
        	if(errorThrown == 'Not Found'){
        		obj.message = "未找到链接资源。";
        	}
        	 
        	else if(resText.indexOf("sessionState")!=-1 && resText.indexOf("loginUrl")!=-1){
			        window.top.location.href="/pages/login/login.jsp";
	        }
        	
        	else{
        		obj.message = errorThrown;
        	}
        } 
	});
	return obj;
};

var banBackSpace = function(e){   
    var ev = e || window.event;//获取event对象   
    var obj = ev.target || ev.srcElement;//获取事件源   
    
    var t = obj.type || obj.getAttribute('type');//获取事件源类型  
    
    //获取作为判断条件的事件类型
    var vReadOnly = obj.getAttribute('readonly');
    var vEnabled = obj.getAttribute('enabled');
    var vDisabled = obj.getAttribute('disabled');
    //处理null值情况
    vReadOnly = (vReadOnly == null) ? false : (vReadOnly == 'readonly'?true:false);
    vDisabled = (vDisabled == null) ? false : (vDisabled == 'disabled'?true:false);
    vEnabled = (vEnabled == null) ? true : vEnabled;
    
    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
    //并且readonly属性为true或enabled属性为false的，则退格键失效
    var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea") && (vReadOnly==true || vEnabled!=true || vDisabled == true))?true:false;
   
    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
    var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")?true:false;        
    
    //判断
    if(flag2){
        return false;
    }
    if(flag1){   
        return false;   
    }   
};