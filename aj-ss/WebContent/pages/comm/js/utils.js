var obj=new Object();
var ajaxSubmit = function(url,params){
	obj.success = false;
	$.ajax({
		type:'POST',
		url:url,
		data:params,
		async: false,
		success:function(data){
			obj = JSON.parse(data);
		}
	});
	return obj;
};
var showMsg = function(msg, icon){
	layer.alert(msg, {title:'提示', icon: icon});
}
$.ajaxSetup({
	complete: function(req, status) {  
		var reqText = req.responseText; 
		// 如果后台返回的是login字符串则可以跳转到登录页面         
		if(reqText && reqText == 'login'){                
			alertMsg("系统提示","用户未登陆，或登陆超时，请确认。");   
			window.location.href = getProjectName() +"/app/system/login.jsp";     
		}   
	}, 
    error: function (XMLHttpRequest, textStatus, errorThrown) { 
    	if(errorThrown == 'Not Found'){
    		obj.message = "未找到链接资源。";
    	}
    	else if(errorThrown == 'Internal Server Error'){
    		obj.message = "服务器异常，如有疑问，请与管理员联系。";
    	}
    	else{
    		obj.message = errorThrown;
    	}
    }
});

/**
 * js占位符替换
 * @param source
 * @param params
 * @returns
 */
var formatParams = function(source,params){  
    $.each(params, function(i, n) {  
        source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);  
    });  
    return source;  
};

/**
 * 扩展Date的format方法   
 * @param format
 * @returns
 */
Date.prototype.format = function (format) {  
	var o = {  
		"M+": this.getMonth() + 1,  
		"d+": this.getDate(),  
		"h+": this.getHours(),  
		"m+": this.getMinutes(),  
		"s+": this.getSeconds(),  
		"q+": Math.floor((this.getMonth() + 3) / 3),  
		"S": this.getMilliseconds()  
	}  
	if (/(y+)/.test(format)) {  
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
	}  
	for (var k in o) {  
		if (new RegExp("(" + k + ")").test(format)) {  
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
		}  
	}  
	return format;  
}

/**
 *获取工程名，如：/demo
 */
var getProjectName = function(){
	var pathName=window.document.location.pathname; 
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
	return projectName;
}

/*弹出层*/
/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
function layer_show(title,url,w,h){
	if (title == null || title == '') {
		title=false;
	};
	if (url == null || url == '') {
		url="404.html";
	};
	if (w == null || w == '') {
		w=800;
	};
	if (h == null || h == '') {
		h=($(window).height() - 50);
	};
	layer.open({
		type: 2,
		area: [w+'px', h +'px'],
		fix: true, //不固定
		maxmin: true,
		shade:0.4,
		title: title,
		content: url
	});
}
/*关闭弹出框口*/
function layer_close(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}


function isEmpty(val) {
	//if (val == "" || val == null || val == "null" || val == "NULL" || val == undefined) {
	if (val == "" || val == null || val == undefined) {
		 return true;
	 }
	
	 if(val.replace(/(^\s*)|(\s*$)/g, "").length==0){
		 return true;
	 }
	 return false;
}
function isEmptyById(id) {
	var val = $("#" + id).val();
	//if (val == "" || val == null || val == "null" || val == "NULL" || val == undefined) {
	if (val == "" || val == null || val == undefined) {
		 return true;
	 }
	
	 if(val.replace(/(^\s*)|(\s*$)/g, "").length==0){
		 return true;
	 }
	 return false;
}

function isEmptyNull(val) {
	if (val == "" || val == null || val == "null" || val == "NULL" || val == undefined) {
		 return true;
	 }
	
	 if(val.replace(/(^\s*)|(\s*$)/g, "").length==0){
		 return true;
	 }
	 return false;
}