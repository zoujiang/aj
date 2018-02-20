<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  request.setCharacterEncoding("utf-8");
  String mainWeb = request.getContextPath();
  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="shortcut icon" href="favicon.ico" />
<link rel="stylesheet" type="text/css" href="<%=mainWeb%>/css/201202/login.css" />
<script type="text/javascript" src="<%=mainWeb%>/scripts/jquery/jquery-1.7.1.min.js"></script>
	<script>window.onerror=function(){return true;};</script>


<style type="text/css">
.dongtai{ float: right;
    margin-right: 20px;
    margin-top: 18px;
}
.dongtai a{color:#005BAC;}
.dongtai a:hover{text-decoration:underline;}
/** 验证码  **/
#checkCode {
	cursor: hand;
	margin-top: 2px;
	margin-left: 5px;
	/*float: left;*/
	display: inline;
	width: 65px;
	height: 15px;
	color: red;
	font-size: 16px;
	font-family: Arial;
	font-style: italic;
	border: 0;
	padding: 2px 3px;
	letter-spacing: 3px;
	font-weight: bolder;
	background-image: url('<%=mainWeb%>/images/checkCodeBg.png');
}
</style>
<script type="text/javascript">
var checkCode;
var validateCode = null;
var createCode = function(isInner) {
				if (!isInner) {
					 validateCode = "";
				}
				var codeLength = 4;// 验证码的长度
				$("#checkCode").html();
				var selectChar = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C',
						'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q',
						'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
		
				for ( var i = 0; i < codeLength; i++) {
					var charIndex = Math.floor(Math.random() * 32);
					validateCode += selectChar[charIndex];
				}
				if (validateCode.length != codeLength) {  
					this.createCode(true); 
				}
				$("#checkCode").html(validateCode);
			};
if(parent && parent.frames.length>0){
		top.location.href = "login.jsp";
}
document.onkeypress = keyListener;
function keyListener(e) {
		var keyCode = e ? e.which : event.keyCode;
		if(keyCode == 13) {
			// 回车事件
			do_login();
		}
}

$(document).ready(function() {	
		
			createCode();
			//验证码校验
			var $validateCodeEditor = $('#checkNum');
		   $validateCodeEditor.bind('focusout', checkCode=function(){
		   		if($validateCodeEditor.val()==""){
		   			$("#msgDiv").html("验证码不能为空");
		   			return false;
		   		}
			 	if($validateCodeEditor.val().toUpperCase() != validateCode.toUpperCase()){
					$("#msgDiv").html("验证码输入有误");
					createCode();
					return false;
				} else {
					$("#msgDiv").empty();
					return true;
				}
		   });
});
function do_login() {
		  var frm = document.frm;
		  var user_code = frm.user_code.value;
		 
		  if (user_code == "") {
		       alert("请输入用户名！");
		       frm.user_code.focus();
		       return;
		   }	
		  var user_pwd = frm.user_pwd.value;
		   if (user_pwd == "") {
		       alert("请输入密码！");
		       frm.user_pwd.focus();
		       return;
		   }
		   //验证码校验
		   var flag=checkCode();
		   if(!flag){
		   		return;
		   }
		   
		   $.post(
		   		"<%=mainWeb%>/admin/user/login?date="+new Date(),
		   		$(frm).serialize(),
		   		function(data){
		   			if(!data.result){
		   				$("#msgDiv").html("用户名或密码错误");
		   			}else{
		   				document.location.href = "<%=mainWeb%>/pages/sys/index.jsp?id="+data.message;
		   			}
		   		},"json");
		 
   }
function do_getPassword() {
        	var frm = document.frm;
        	var user_code = frm.user_code.value;
        	if (user_code == "") {
            		alert("请输入用户名！");
            		frm.user_code.focus();
            	return;
        	}
 }
function do_register(){
	alert("功能未提供！");
}
	
</script>
</head>

<body >
<div class="sme_main">
    <div class="sme_right">
    	<form id="loginForm" name="frm" method="post" >
    	<h1 align="center">管理系统</h1>
    	<input type="hidden"   value="${request.userLogic}"   id="userLogic"/>
          	<h1>登录账户：</h1>
            <input type="text" id="user_code" name="account" value="${user.userCode}" />
            
            <h1  style="display:inline-block; float:left;">登录密码：</h1>
            <input type="password" id="user_pwd" name="pwd" />
            
           	<h1 style="display:inline-block; float:left;">验证码:</h1>
           	<div>
					<input type="text" id="checkNum" style='display: inline; width: 80px;' />
					<div id="checkCode" onclick="createCode();" style='display: inline;'></div>
					<a href="#" onclick="createCode();">
					<font style="font-size: 13px; display: inline;">看不清换一张</font> </a>
			</div>        	             
            <input class="sme_sub" align="right" name="aa" type="submit" id="sub" value="" onclick="do_login();return false;"/>
        	<span id="msgDiv" style="color:red;text-align:center;font-size:15px"></span>
        </form>
  </div>
</div>

</body>
</html>
