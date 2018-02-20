var eason={};

/*注册命名空间*/
eason.register = function(path){    
    var arr = path.split(".");    
    var ns = "";    
    for(var i=0;i<arr.length;i++){    
        if(i>0) ns += ".";    
        ns += arr[i];    
        eval("if(typeof(" + ns + ") == 'undefined') " + ns + " = new Object();");    
    }    
} 
/*********************************************/
/*************校验检查************************/
/*********************************************/
eason.register("com.eason.check");
eason.register("com.eason.check.easyui");

com.eason.check.easyui.isSaleTime = function($target,labName){
	var targetText=$target.val();
	var array=[];
	array=targetText.split(',');
	var myreg = /^([0-9]{1,2}[:]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}[:]{1}[0-9]{1,2})$/;
	for(var i=0;i<array.length;i++){
	    if(!myreg.test(array[i])){
	    	$.messager.alert("提示", "【"+labName+"】格式不正确。", "info", function() {
				$target.focus();
			});
			return false;
	    }
	}
    return true;
};


/*电话号码长度限制*/
com.eason.check.easyui.telLength = function($target,labName,length){
	var targetText=$target.val();
	if(com.eason.check.getValueLen(targetText)!=length){
		$.messager.alert("提示", "【"+labName+"】位数必须是"+length+"位。", 'info', function() {
			$target.focus();
		});
		return false;
	}
	return true;
}

/*身份证号码长度限制*/
com.eason.check.easyui.citizenLength = function($target,labName,length1,length2){
	var targetText=$target.val();
	if(com.eason.check.getValueLen(targetText)!=length1&&com.eason.check.getValueLen(targetText)!=length2){
		$.messager.alert("提示", "【"+labName+"】位数不正确，请核查后重新输入", 'info', function() {
			$target.focus();
		});
		return false;
	}
	return true;
}


/*密码由字母和数字组成，至少6位 */
com.eason.check.safePassword = function(value) {
    return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
};
/*校验输入域是否含有特殊符号 */ 
com.eason.check.checkTextValid = function(inputValue) {
    var reg =  /<script[^>]*?>[\s\S]*?<\/script>/;
	var reg1 =  /<script[^>]*?>/;
	var reg2 =  /<\/script>/;
	var reg3 = /<img[^>]+\>/;///<[^>]+>/;
	var reg4 = /<frameset[\s\S]+<\/frameset *>/;
	var reg5 = /<iframe[\s\S]+<\/iframe *>/;
	var reg6 = /<iframe>/;
	var reg7 = /<\/iframe>/;
	if (typeof(inputValue)=="undefined")return false;
	inputValue = inputValue.replace(/\s/ig,'').toLocaleLowerCase();
	flag = true;
	if(reg.test(inputValue)) {
			return true;
	}else{
		flag = false;
	}
	if(reg1.test(inputValue)) {
			return true;
	}else{
		flag = false;
	}
	if(reg2.test(inputValue)) {
			return true;
	}else{
		flag = false;
	}
	if(reg3.test(inputValue)) {
			return true;
	}else{
		flag = false;
	}
	if(reg4.test(inputValue)) {
			return true;
	}else{
		flag = false;
	}
	if(reg5.test(inputValue)) {
			return true;
	}else{
		flag = false;
	}
	if(reg6.test(inputValue)) {
			return true;
	}else{
		flag = false;
	}
	if(reg7.test(inputValue)) {
			return true;
	}else{
		flag = false;
	}
	var reg =  /[@#\$%&\'\"]/
	if(reg.test(inputValue)) {
		return true;
	}else{
		flag = false;
	}
		return flag;
	}         
/*返回中英文字符串长度*/
com.eason.check.getValueLen = function(nameValue){  
    var nameStr = $.trim(nameValue);  
    var len = 0;  
    for(var i=0; i<nameStr.length; i++){  
       //str = markerStr.charAt(i);  
       if(nameStr.charCodeAt(i)>255 || nameStr.charCodeAt(i)<0){  
           len +=2;  
       }else{  
           len++;  
       }  
    }  
    return len;  
} 
/*是否为空*/
com.eason.check.trim = function(sString){
	    return sString.replace(/(^\s*)|(\s*$)/g, "");
}
/*是否为数字*/
com.eason.check.isInt = function(sString){	
	if(sString==null) return false;
		var s = trim(sString);
		var pat_hd=/^[0-9]+$/;
		return pat_hd.test(s);
}

/*是否为邮箱*/
com.eason.check.easyui.isEmail = function($target,labName){
	var targetText=$target.val();
	 //对电子邮件的验证
	if(com.eason.check.getValueLen(targetText)!=0){
	    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	    if(!myreg.test(targetText)){
	    	$.messager.alert("提示", "【"+labName+"】不正确。", "info", function() {
				$target.focus();
			});
			return false;
	    }
	}
    return true;
}

/*数字价格校验*/
com.eason.check.easyui.isEffect = function($target,labName){	
	 
	if($target==null) return false;
	var s = trim($target.val());
	 var pat_hd=/^(?:\d{0,6}|\d{0,6}\.\d{1,2})$/;
	// var pat_hd=/^([0-9]{0,6}+\.[0-9]{0,2})$/;
		if(pat_hd.test(s)) { 
			return true;
		}else{
			 $.messager.alert("提示", "【"+labName+"】格式不正确，最多6位正数或加两位小数。", "info", function() {
					$target.focus();
		       });
			 return false;
		}
 
}
/*非空验证*/
com.eason.check.easyui.isEmpty = function($target,labName){
	var targetText=$target.val();
	if(com.eason.check.getValueLen(targetText)==0){
		$.messager.alert("提示", "【"+labName+"】不能为空。", "info", function() {
			$target.focus();
		});
		return false;
	}
	return true;
}
com.eason.check.easyui.isEmptyForDate = function($target,labName){
	var targetText=$target.val();
	if(com.eason.check.getValueLen(targetText)==0){
		$.messager.alert("提示", "【"+labName+"】不能为空。", "info", function() {
			//$target.focus();
		});
		return false;
	}
	return true;
}
/*非法字符验证*/
com.eason.check.easyui.isIllagalChar = function($target,labName){
	var targetText=$target.val();
	if(com.eason.check.checkTextValid(targetText)){
	    $.messager.alert("提示", "【"+labName+"】包含非法字符。", "info", function() {
			$target.focus();
		});
		return false;
	 }
	return true;
}
/*字符长度限制*/
com.eason.check.easyui.limitLength = function($target,labName,length){
	var targetText=$target.val();
	if(com.eason.check.getValueLen(targetText)>length){
		$.messager.alert("提示", "【"+labName+"】不能超过"+length+"个字符（一个中文算两个字符）。", 'info', function() {
			$target.focus();
		});
		return false;
	}
	return true;
}
/*单选框验证*/
com.eason.check.easyui.isCheckedTrue = function($target,labName){
	 var Obj=$target.get(0);
	 var flag=false;
	 for(var i=0;i<Obj.length;i++){ 
		 if(Obj[i].checked==true){ 
			 flag=true ;
		 } 
	 }
	 if (flag == false){
		 $.messager.alert("提示", "【"+labName+"】不能为空", 'info', function(){
			 target.focus();
		 });
	 }
	 return flag;
}
/*复选框选框验证*/
com.eason.check.easyui.isEmptyForCombobox = function($target,labName){
	 var targetText=$target.combobox('getValue')
	 if(com.eason.check.getValueLen(targetText)==0){
			$.messager.alert("提示", "【"+labName+"】不能为空。", "info", function() {
				//$target.focus();
			});
			return false;
		}
	return true;
}
/*必须输入数字校验*/
com.eason.check.easyui.isNum = function($target,labName){
	var targetText=$target.val();
	if(isNaN(targetText)){
		$.messager.alert("提示", "【"+labName+"】请输入正确的数字。", "info", function() {
			$target.focus();
		});
		return false;
	}
	return true;
}
/*必须输入正数校验*/
com.eason.check.easyui.isNumForN = function($target,labName){
	var targetText=$target.val();
	if(isNaN(targetText)){
		$.messager.alert("提示", "【"+labName+"】请输入正确的"+labName+"。", "info", function() {
			$target.focus();
		});
		return false;
	}else{
		var a=parseFloat(targetText);
		if(a<0){
			$.messager.alert("提示", "【"+labName+"】不能为负数。", "info", function() {
				$target.focus();
			});
			return false;
		}
	}
	return true;
}
/*开始时间和比较时间的校验*/
com.eason.check.compareTime = function(startTime,endTime){
	startTime=new Date(Date.parse(startTime.replace(/-/g,"/")));
	endTime=new Date(Date.parse(endTime.replace(/-/g,"/")));
	var a =endTime-startTime;
	if(a>0){
		return "1";
	}else{
		return "-1";
	}
}
/*检查结束时间*/
com.eason.check.easyui.checkEndTime = function($endTime,endLabName){
	var endTime=$endTime.val();
	if(endTime==null || endTime==""){
		$.messager.alert("警告","请选择【"+endLabName+"】");
		return false;
	}
	var startTime=new Date();
	endTime=new Date(Date.parse(endTime.replace(/-/g,"/")));
	if(endTime<startTime){
		$.messager.alert("警告","【"+endLabName+"】必须大于【当前时间】");
		return false;
	}
	return true;
}
/*检查开始和结束时间*/
com.eason.check.easyui.checkStartEndTime = function($startTime,startLabName,$endTime,endLabName){
	var startTime=$startTime.val();
	if(startTime==null || startTime==""){
		$.messager.alert("警告","请选择【"+startLabName+"】");
		return false;
	}
	var endTime=$endTime.val();
	if(endTime==null || endTime==""){
		$.messager.alert("警告","请选择【"+endLabName+"】");
		return false;
	}
	var flag=com.eason.check.compareTime(startTime,endTime);
	if(flag=="-1"){
		$.messager.alert("警告","【"+startLabName+"】必须大于【"+endLabName+"】");
		return false;
	}
	return true;
};
/*文件上传格式校验*/
com.eason.check.easyui.checkFileUpload = function($attachFile){
	var fileUpload = $attachFile.val();
	if(fileUpload!=''){
		var extend=fileUpload.substring(fileUpload.lastIndexOf('.')+1).toLowerCase();
		var express="txt,xls,xlsx,doc,docx,ppt,pptx,pdf,zip,rar";
		if(express.indexOf(extend) == -1){
			$.messager.alert('警告', '您上传的附件格式不对，请检查附件格式。', 'warning');
			return false;
		}		
	}
	return true;
};
/* data */
com.eason.check.easyui.queryByPara=function(gridId, formId) {
	var o = {};
	var form = $('#' + formId);
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	$('#' + gridId).datagrid('load', com.eason.check.easyui.serializeForm(formId));
};
com.eason.check.easyui.serializeForm=function(formId) {
	var o = {};
	var form = $('#' + formId);
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};
com.eason.check.easyui.resetData=function(formId) {
	$('#' + formId).form('reset');
};

com.eason.check.easyui.clearData=function(formId) {
	$('#' + formId).form('clear');
};
com.eason.openDiog=function(diogId, diogTitle, diogUrl, formId, loadedObj,callback) {
	var $diogId=$("#"+diogId);
	$('#' + diogId).dialog({
		title : diogTitle,
		closed : false,
		width: loadedObj.width||300,
		height: loadedObj.height||180,
		cache : false,
		href : diogUrl,
		modal : true,
		buttons : [{
					text : '保存',
					iconCls : 'icon-ok',
					handler : function() {
						var saveBtn = $(this);
						$('#' + formId).form('submit',{
							onSubmit : function() {
								var isValid = $('#' + formId).form('validate');
								if (isValid) {
									saveBtn.unbind("click");
									saveBtn.linkbutton('disable');
									return true;
								} else {
								  	return false;
								}
							},
							success : function(data) {
								var msg = "";
								if (JSON.parse(data).result) {
									msg = '保存成功';
									$.messager.alert('',msg,'info',callback);
								} else {
									msg = '保存失败';
									$.messager.alert('',msg,'error',function(){
										$diogId.dialog("close");
									});
								}
								
							}
						});
					}
				},
				{
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function() {
						$diogId.dialog('close');
					}
				}],
		onLoad : function() {
			if ((typeof loadedObj) == "object") {
				$('#' + formId).form('load', loadedObj);
			}
		}
	});
}
/*********************************************/
/*************常量获取************************/
/*********************************************/
eason.register("com.eason.system");
/*获取格式系统时间字符串*/
com.eason.system.getSYSTime = function(format){
	var now = new Date(); //当前时间    
    var yyyy = now.getFullYear(); //获取完整的年份(4位,1970-????)  
    var MM = now.getMonth() + 1; //获取当前月份(0-11,0代表1月)   
    var dd = now.getDate();  //获取当前日(1-31)
    var HH = now.getHours(); //获取当前小时数(0-23)
    var mm = now.getMinutes();  //获取当前分钟数(0-59)
    var ss = now.getSeconds(); //获取当前秒数(0-59)
    var mmm = now.getMilliseconds(); //获取当前毫秒数(0-999) 
    return format+yyyy+MM+dd+HH+mm+ss+mmm;
}
/*********************************************/
/*************弹出窗体获取*********************/
/*********************************************/
eason.register("com.eason.window");
com.eason.window.winUtils = {
		showDialogWindow : function(e,url,param,execute) {
			var whparamObj = { width: 520, height: 380 };  
				 // 相对于浏览器的居中位置  
			 var bleft = ($(window).width() - whparamObj.width) / 2;  
			 var btop = ($(window).height() - whparamObj.height) / 2;  
			 // 根据鼠标点击位置算出绝对位置  
			 var tleft = e.screenX - e.clientX;  
			 var ttop = e.screenY - e.clientY;  
			 // 最终模态窗口的位置  
			var left = bleft + tleft-120;  
		    var top = btop + ttop-100;   
			 var p = "help:no;status:no;center:yes;";  
			     p += 'dialogWidth:'+(whparamObj.width)+'px;';  
			     p += 'dialogHeight:'+(whparamObj.height)+'px;';  
			     p += 'dialogLeft:' + left + 'px;';  
			     p += 'dialogTop:' + top + 'px;'; 
				var returnValue = window.showModalDialog(url, param, p);
				execute(returnValue);
			},
			showDialogWindowOfWH : function(vwidth,vheight,e,url,param,execute) {
				var whparamObj = { width: vwidth, height: vheight };  
					 // 相对于浏览器的居中位置  
				 var bleft = ($(window).width() - whparamObj.width) / 2;  
				 var btop = ($(window).height() - whparamObj.height) / 2;  
				 // 根据鼠标点击位置算出绝对位置  
				 var tleft = e.screenX - e.clientX;  
				 var ttop = e.screenY - e.clientY;  
				 // 最终模态窗口的位置  
				var left = bleft + tleft-120;  
			    var top = btop + ttop-100;   
				 var p = "help:no;status:no;center:yes;";  
				     p += 'dialogWidth:'+(whparamObj.width)+'px;';  
				     p += 'dialogHeight:'+(whparamObj.height)+'px;';  
				     p += 'dialogLeft:' + left + 'px;';  
				     p += 'dialogTop:' + top + 'px;'; 
				     
				     if(navigator.userAgent.indexOf("Chrome") >0 ){
				    	 var returnValue = window.open(url,window, p);
				    	 execute(returnValue);
				     }else{
				    	 var returnValue = window.showModalDialog(url, param, p);
				    	 execute(returnValue);
				     }
				}
};
//禁用form表单中所有的input[文本框、复选框、单选框],select[下拉选],多行文本框[textarea] 
eason.register("com.eason.form");
com.eason.form.disableForm=function(formId,isDisabled) {  
    var attr="disable";  
    if(!isDisabled){  
       attr="enable";  
    }  
    $("form[id='"+formId+"'] :text").attr("disabled",isDisabled);  
    $("form[id='"+formId+"'] textarea").attr("disabled",isDisabled);  
    $("form[id='"+formId+"'] select").attr("disabled",isDisabled);  
    $("form[id='"+formId+"'] :radio").attr("disabled",isDisabled);  
    $("form[id='"+formId+"'] :checkbox").attr("disabled",isDisabled);  
    $("form[id='"+formId+"'] :file").attr("disabled",isDisabled);  
    
    //禁用jquery easyui中的下拉选（使用input生成的combox）  
  
    $("#" + formId + " input[class='combobox-f combo-f']").each(function () {  
        if (this.id) {alert("input"+this.id);  
            $("#" + this.id).combobox(attr);  
        }  
    });  
    //禁用jquery easyui中的下拉选（使用select生成的combox）  
    $("#" + formId + " select[class='combobox-f combo-f']").each(function () {  
        if (this.id) {  
        alert(this.id);  
            $("#" + this.id).combobox(attr);  
        }  
    });  
    //禁用jquery easyui中的日期组件dataBox  
    $("#" + formId + " input[class='datebox-f combo-f']").each(function () {  
        if (this.id) {  
        alert(this.id)  
            $("#" + this.id).datebox(attr);  
        }  
    });  
};