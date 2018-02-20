/******************************************************************/
/******************************************************************/
/************************** 插件 扩展 *****************************/
/*****************************************************************/
/*****************************************************************/
/**
 * 为datagrid添加导出方法 
 */
$.extend($.fn.datagrid.methods, {
			/**
			 * 表格导出方法
			 * @param jq
			 * @param paramters
			 */
			doExport : function(jq, paramters) {
				var options = jq.datagrid('options');
				var $tempForm = $("<form method='POST'></form>");
				if(paramters){
					var $textInput = $("<input type='hidden' name='searchText' value=\"" + JsonUtil.toString(paramters) + "\" />");
					$textInput.appendTo($tempForm);
				}
				var $urlInput = $("<input type='hidden' name='columns' value=\"" + jq.datagrid('getSimplifiedColumn', options.columns[0]) + "\" />");
				$urlInput.appendTo($tempForm);
				var sort = options.sortName;
				var order = options.sortOrder;
				if(null!=sort && $.trim(sort)!=''){
					var $sortInput = $("<input type='hidden' name='sort' value=\"" +$.trim(sort) + "\" />");
					$sortInput.appendTo($tempForm);
				}
				if(null!=order && $.trim(sort)!=''){
					var $orderInput = $("<input type='hidden' name='order' value=\"" +$.trim(order) + "\" />");
					$orderInput.appendTo($tempForm);
				}
				$tempForm.appendTo($('body'));
				var exportURL;
				if(options.url.indexOf("?")!=-1){
					exportURL=options.url.substring(0,options.url.indexOf("?"));
				}else{
					exportURL=options.url;
				}
				$tempForm.attr('action', exportURL + "Export");
				$tempForm.submit();
			},
			
			/**
			 * 获得简化版的列信息(只包括field,title,align信息)，以JSON字符串形式返回
			 * @param jq
			 * @param columnArray
			 * @returns {String}
			 */
			getSimplifiedColumn : function(jq, columnArray) {
				var columnJsonStr = "[";
				for (var columnIndex = 0; columnIndex < columnArray.length; columnIndex++) {
					if ("action" == columnArray[columnIndex].field) {
						continue;
					}
					columnJsonStr += "{";
					columnJsonStr += "'field': '" + columnArray[columnIndex].field + "'," ;
					columnJsonStr += "'title': '" + columnArray[columnIndex].title + "'," ;
					columnJsonStr += "'align': 'center'" ;
					columnJsonStr += "}";
					if (columnIndex != columnArray.length - 1) {
						columnJsonStr += ",";
					}
				}
				columnJsonStr += "]";
				return columnJsonStr;
			}
		
		});

/******************************************************************/
/******************************************************************/
/************************** 工具方法 ******************************/
/*****************************************************************/
/*****************************************************************/
/**
 * JSON工具对象
 * 
 */
JsonUtil = {
	/**
	 * json对象转为字符串
	 * @param {} jsonObj
	 * @return string
	 */
	toString : function(jsonObj) {
		var arr = [];
		var fmt = function(s) {
			if (typeof s == 'object' && s != null)
				return this.toString(s);
			return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s;
		};
		for (var i in jsonObj)
			arr.push("'" + i + "':" + fmt(jsonObj[i]));
		return '{' + arr.join(',') + '}';
	}
};

/**
 * Easyui validatebox扩充
 * 

$.extend($.fn.validatebox.defaults.rules, {
    CHS: {
        validator: function (value, param) {
            return /^[/u0391-/uFFE5]+$/.test(value);
        },
        message: '请输入汉字'
    },
    ZIP: {
        validator: function (value, param) {
            return /^[1-9]/d{5}$/.test(value);
        },
        message: '邮政编码不存在'
    },
    QQ: {
        validator: function (value, param) {
            return /^[1-9]/d{4,10}$/.test(value);
        },
        message: 'QQ号码不正确'
    },
    mobile: {
        validator: function (value, param) {
            return /^((/(/d{2,3}/))|(/d{3}/-))?13/d{9}$/.test(value);
        },
        message: '手机号码不正确'
    },
    loginName: {
        validator: function (value, param) {
            return /^[/u0391-/uFFE5/w]+$/.test(value);
        },
        message: '登录名称只允许汉字、英文字母、数字及下划线。'
    },
    safepass: {
        validator: function (value, param) {
            return safePassword(value);
        },
        message: '密码由字母和数字组成，至少6位'
    },
    equalTo: {
        validator: function (value, param) {
            return value == $(param[0]).val();
        },
        message: '两次输入的字符不一至'
    },
    number: {
    	validator: function (value, param) {
            return /^[1-9]\d*|0$.test(value);
        },
        message: '请输入数字'
    }
});     */   
            /* 密码由字母和数字组成，至少6位 */
            var safePassword = function (value) {
                return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
            }
            
            
            /** 
            * 校验输入域是否含有特殊符号 
            */ 
            function checkTextValid(inputValue) {
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
            /**
             * 返回中英文字符串长度
             * @param nameValue
             * @return
             */
            function getValueLen(nameValue){  
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
            /**
             * 
             * @param sString
             * @return
             */
 			function trim(sString)
 			{
 			    return sString.replace(/(^\s*)|(\s*$)/g, "");
 			}
 			function isEmpty(sString)
 			{   
 				if(sString==null) return true;
 				var pat_hd=/^\s*$/;
 				return pat_hd.test(sString);
 			}
 			function isInt(sString)
 			{	if(sString==null) return false;
 				var s = trim(sString);
 				var pat_hd=/^[0-9]+$/;
 				return pat_hd.test(s);
 			}
 			/**
 			 * 判断是否为价格，小数点后做多2位
 			 * @param sString
 			 * @return
 			 */
 			function isPrice(sString){
 				if(sString==null) return false;
 				var s = trim(sString);
 				var pat_hd=/^(?:\d+|\d+\.\d{1,2})$/ ;
 				return pat_hd.test(s);
 			}
 			/**
 			 * 判断是否为数学表达式
 			 * @param sString
 			 * @return
 			 */
 			function isMathExpression(sString){
 				try{
 				    eval("var ret = "+sString);
 				    if(ret!=Infinity){
 				        return true;
 				    }else{
 				    	return false;
 				    }
 				}catch(e){
 					return false;
 				}
 			}
 			function dump(arr,level) {   
 			 	if(arr==null)return;
 			 	if(typeof(arr)=="undefined")return ;
 			    var dumped_text = "";   
 			    if(!level) level = 0;   
 			      
 			   
 			    var level_padding = "";   
 			    for(var j=0;j<level;j++) level_padding += "    ";   
 			    if(typeof(arr) == 'object') {     
 			        for(var item in arr) {   
 			            var value = arr[item];   
 			            if(typeof(value) == 'object') { //If it is an array,   
 			                dumped_text += dump(value,level+1);   
 			            } else {   
 			                dumped_text += level_padding + "\"" + item + "\" : \"" + value + "\"\n";   
 			            }   
 			        }   
 			    } else {  
 			        dumped_text = "===>"+arr+"<===("+typeof(arr)+")";   
 			    }   
 			    return dumped_text;   
 			} 
/**获取格式系统时间字符串*/
function getSYSTime(format){
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

function previewImage(e,url) {
	 var whparamObj = { width:670, height:470};  
	 // 相对于浏览器的居中位置  
	 var bleft = ($(window).width() - whparamObj.width) / 2;  
	 var btop = ($(window).height() - whparamObj.height) / 2;  
	 // 根据鼠标点击位置算出绝对位置  
	 var tleft = e.screenX - e.clientX;  
	 var ttop = e.screenY - e.clientY;  
	 // 最终模态窗口的位置  
	 var left = bleft + tleft;  
	 var top = btop + ttop;  
	 // 参数  
	 var p = "help:no;status:no;center:yes;location:no;resizable=yes;";  
	     p += 'dialogWidth:'+(whparamObj.width)+'px;';  
	     p += 'dialogHeight:'+(whparamObj.height)+'px;';  
	     p += 'dialogLeft:' + left + 'px;';  
	     p += 'dialogTop:' + top + 'px;';
	 var parameter = {"url":url};    
	 if(url.indexOf('mp4') > 0 ){
		 window.showModalDialog(mainWeb + "/scripts/jPlayer-2.9.2/previewMp4.jsp?res=" + url,parameter,p);
	 }else if(url.indexOf('amr') > 0 || url.indexOf('aac') > 0 ){
		 window.showModalDialog(mainWeb + "/scripts/jPlayer-2.9.2/previewMp3.jsp?res=" + url,parameter,p);
	 }
	 else  if(url.indexOf('png') > 0 || url.indexOf('jpg') > 0 || url.indexOf('jpeg') > 0){
		 window.showModalDialog(mainWeb + "/common/previewImage.jsp",parameter,p);
	 }else{
	     window.open(mainWeb+"/service/resdown?url="+ url,"_blank");
	 } 
};

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "H+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒 
 };
 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 for (var k in o)
 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt;
}