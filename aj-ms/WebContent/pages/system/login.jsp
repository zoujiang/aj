<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    
    <title>亲脉商家管理系统</title>
    <%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link href="${ctx }/resources/hplus4.1/css/login.min.css" rel="stylesheet">
    <link href="${ctx }/resources/opentip/opentip.css" rel="stylesheet">
    <script src="${ctx }/resources/hplus4.1/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${ctx }/resources/hplus4.1/js/plugins/validate/additional-methods.js"></script>
    <script src="${ctx }/resources/hplus4.1/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${ctx }/resources/opentip/opentip-jquery.min.js"></script>
    <script src="${ctx }/resources/jquery.form.js"></script>
    
    <style type="text/css">
    .logo-name2 {
		color: #e6e6e6;
	font-size: 40px;
	font-weight: 80;
	
	margin-bottom: 0	
		
	}
	
	.sys-name {
		color: #e6e6e6;
	font-size: 35px;
	font-weight: 60;
	margin-bottom: 0	
		
	}
	
	.gray-bg2{
    /*background-color: #f3f3f4;*/
background: #18c8f6;
    height: auto;
    background:url("${ctx }/resources/img/bg.jpg") no-repeat center fixed;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover
   
	}
    </style>
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
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
			onfocusout: function(element){
		        $(element).valid();
		    },
			rules: {
	        	account: {
	                required: true,
	                minlength: 2,
	                account:true
	            },
	            pwd: {
	                required: true,
	                minlength: 6,
	                password:true
	            }
	        },
	        messages: {
	        	account: "请输入不小于 2个字符的由英文字母、数字及下划线组成的字符串",
	            pwd: "请输入不小于 6个字符的由英文字母、数字及下划线以及-_~!@#$%^&*.()[]{}<>?\/'\"组成的字符串"
	        },
	        errorPlacement: function(error, element) {
	        	var msgbox = $(element);
	        	new Opentip(msgbox, $(error).text(), { style: "glass", target: true, tipJoint: "bottom", targetJoint: "top", containInViewport: true });
	        },
	        submitHandler: function(form) {
	        	//alert("xxxxxx");
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
	        		  			//showMsg("登录失败，请输入正确的账号及密码", 5);
		        		  		layer.msg("登录失败，请输入正确的账号及密码", {time:20,title:'提示', btn: ['确定'],icon: 6}, function(){
		        		  			$("#account").focus();
		        		  		});
	        		  		}
	        		  		
	        		  	}
	        		});
	      		}
		});
	});
    
    
    </script>
</head>

<body class="gray-bg2">

    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>
                <span class="logo-name2">亲脉</span>

            </div>
             <span class="sys-name">商家后台管理系统</span>

            <form class="m-t" role="form" id="loginForm">
                <div class="form-group">
                    <input name = "account" id="account"  type="text" class="form-control" placeholder="用户名" required="true">
                </div>
                <div class="form-group">
                    <input name="pwd" id="pwd"  type="password" class="form-control" placeholder="密码" required="true">
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">登 录</button>
                </p>

            </form>
        </div>
    </div>

    
</body>

</html>
