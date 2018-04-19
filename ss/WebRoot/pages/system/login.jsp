<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    
    <title>幼教汇后台管理系统</title>
<link type="text/css" rel="stylesheet" href="<c:url value='/resources/login.css' />" />
<script type="text/javascript" src="<c:url value='/resources/hplus4.1/js/jquery.min.js' />"></script>
 <script src="<c:url value='/resources/hplus4.1/js/plugins/validate/jquery.validate.min.js' />"></script>
    <script src="<c:url value='/resources/hplus4.1/js/plugins/validate/additional-methods.js' />"></script>
    <script src="<c:url value='/resources/hplus4.1/js/plugins/validate/messages_zh.min.js' />"></script>
    <script src="<c:url value='/resources/opentip/opentip-jquery.min.js' />"></script>
    <script src="<c:url value='/resources/jquery.form.js' />"></script>
<script type="text/javascript">
    $(function(){
		if(window.top!==window.self){
        	window.top.location=window.location;
        }
		$("#loginForm").find('input').on('keyup', function(event) {/* 增加回车提交功能 */
			if (event.keyCode == '13') {
				onLogin('${ctx }');
			}
		});
		//var e = "<i class='fa fa-times-circle'></i> ";
		$("#loginForm").validate({
			
		//	onfocusout: function(element){
		 //       $(element).valid();
		 //   },
		//	rules: {
	      //  	account: {
	      //          required: true,
	     //           minlength: 2,
	      //          account:true
	     //       },
	    //        pwd: {
	    //            required: true,
	    //            minlength: 6,
	    //            password:true
	    //        }
	   //     },
	   //     messages: {
	   //     	account: "请输入不小于 2个字符的由英文字母、数字及下划线组成的字符串",
	   //         pwd: "请输入不小于 6个字符的由英文字母、数字及下划线以及-_~!@#$%^&*.()[]{}<>?\/'\"组成的字符串"
	   //     },
	   //     errorPlacement: function(error, element) {
	   //     	var msgbox = $(element);
	   //     	new Opentip(msgbox, $(error).text(), { style: "glass", target: true, tipJoint: "bottom", targetJoint: "top", containInViewport: true });
	   //     },
	        submitHandler: function(form) {
	        	//alert("xxxxxx");
	        		$("#regMsg").hide();
	        		$(form).ajaxSubmit({
	        			url:"${ctx }/admin/user/login",
	        			async: false,
	        		  	success: function (result) { //表单提交后更新页面显示的数据
	        		  		var obj = eval("("+result+")");
	        		  		if(obj.result){
	        		  			//showMsg(obj.message, 6);
	        		  			//layer.msg(obj.message, {time:20,title:'提示', btn: ['确定'],icon: 6}, function(){
  	        		  			  location.href="${ctx}/pages/system/index.jsp"
  	        		  		//});
	        		  		} else {
	        		  			$("#regMsg").show();
	        		  			$("#regMsg").text("登录失败，账号或密码错误");
	        		  			$("#account").focus();
	        		  			//showMsg("登录失败，请输入正确的账号及密码", 5);
	        		  		//	layer.msg("登录失败，请输入正确的账号及密码", {time:20,title:'提示', btn: ['确定'],icon: 6}, function(){
		        		  	//		$("#account").focus();
		        		  	//	});
	        		  		}
	        		  		
	        		  	}
	        		});
	      		}
		});
	});
 </script>
</head>

<body>
<div class="contnet">
	<div class="logo"><img src="<c:url value='/resources/img/logo.png'/>"></div>
    <form role="form" id="loginForm">
    	<div class="form">
        	<div class="error" id="regMsg" style="display: none;">账户或密码错误</div>
            <ul>
            	<li><img src="<c:url value='/resources/img/zhanghu.png' />"><input  name = "account" id="account"  type="text" placeholder="请输入账户"  autocomplete="new-password" ></li>
                <li><img src="<c:url value='/resources/img/mima.png' />"><input name="pwd" id="pwd"  type="password" placeholder="请输入密码" autocomplete="new-password" ></li>
                <li class="btn"><input type="submit" style="padding-left: 8px;" value="登录"></li>
             </ul>
        </div>
    </form>
</div>
<div class="banquan">亲脉版权所有Copyright © 2018</div>
<div class="footer"></div>
</body>
</html>
