<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>办事指南</title>
	<style type="text/css">
	/*整体布局*/
	#logo{
		width:100%;
		height:66px;
		background: url("<%=mainWeb%>/images/admin/HT-topbg.png") repeat-x top left; 
		overflow: hidden;
	}
	/*logo图片*/
	#logo .logoImg{
		float:left;
		height:66px;
		width:550px;
		background: url("") no-repeat top left;
	}
	/*修改密码/退出*/
	#logo .logoLogin{
		float:right;
		width:130px;
		line-height: 25px;
		margin:10px;30px;5px;10px;
	}
	#logo .logoLogin img{
		cursor: pointer;
	}
	/*修改密码对话框*/
	#logo_updatePwd_dialog{
		text-align: center;
	}
	#logo_updatePwd_dialog table{
		margin-top: 15px;
	}
	#logo_updatePwd_dialog td.td_label{
		width: 65px;
		text-align: right;
	}
	</style>
	<script type="text/javascript">
		$(function(){
			//初始化 -> 绑定事件 -> 修改密码
			$("#logo .logoLogin .pwdImg").bind("mouseover",function(){
				$(this).attr("src","<%=mainWeb%>/images/admin/key02.png");
			}).bind("mouseout",function(){
				$(this).attr("src","<%=mainWeb%>/images/admin/key01.png");
			}).bind("click",function(){
				logoAction.updatePwd();
			});
			//初始化 -> 绑定事件 -> 退出
			$("#logo .logoLogin .logoutImg").bind("mouseover",function(){
				$(this).attr("src","<%=mainWeb%>/images/admin/logout021.png");
			}).bind("mouseout",function(){
				$(this).attr("src","<%=mainWeb%>/images/admin/logout01.png");
			}).bind("click",function(){
				logoAction.logOut();
			});
			//初始化 --> 解决Dialog的Debug
			var logo_updatePwd_dialogDebug = $("#pageContent #logo_updatePwd_dialog");
			for(var i =1;i< logo_updatePwd_dialogDebug.length;i++){
				$(logo_updatePwd_dialogDebug[i]).dialog("destroy");
			}
			
		});
		
		var logoAction = new LogoAction();
		function LogoAction(){
			//修改密码 -> 打开修改密码对话框
			this.updatePwd = function(){
				$("#logo_updatePwd_dialog input").val("");
				$('#logo_updatePwd_dialog').dialog('open');
			}
			//修改密码 -> 执行保存
			this.save = function(){
				if(!logoAction.validate()){
					return;
				}
				var oldPwd = $.trim($("#logo_updatePwd_dialog input[name='oldPwd']").val());
				var newPwd = $.trim($("#logo_updatePwd_dialog input[name='newPwd1']").val());
				$.get("<%=mainWeb%>/admin/user/updatePwd",{'oldPwd':oldPwd,'newPwd':newPwd},function(data){
					//data = eval("("+data+")");
				 	if(data.result){
				 		$.messager.alert('提示',data.message,'warning');
				 		$('#logo_updatePwd_dialog').dialog('close')
				 	}else{
				 		$.messager.alert('提示',data.message,'warning');
				 	}
				});
			}
			//修改密码 -> 验证密码
			this.validate = function(){
				if($.trim($("#logo_updatePwd_dialog input[name='oldPwd']").val()) == ""){
					$.messager.alert('提示','原始密码未填写!','warning');
					return false;
				}else if($.trim($("#logo_updatePwd_dialog input[name='newPwd1']").val()) == ""){
					$.messager.alert('提示','新密码未填写!','warning');
					return false;
				}else if($.trim($("#logo_updatePwd_dialog input[name='newPwd2']").val()) == ""){
					$.messager.alert('提示','重复密码未填写!','warning');
					return false;
				}else if($.trim($("#logo_updatePwd_dialog input[name='newPwd1']").val()) != $.trim($("#logo_updatePwd_dialog input[name='newPwd2']").val())){
					$.messager.alert('提示','新密码与重复密码不一致!','warning');
					$("#logo_updatePwd_dialog input[name='newPwd1']").val("");
					$("#logo_updatePwd_dialog input[name='newPwd2']").val("");
					return false;
				}
				return true;
			}
			//退出
			this.logOut = function(){
				$.messager.confirm("退出","确认退出系统？",function(result){
					if(result){
						document.location.href="<%=mainWeb%>/admin/user/logout";
					}
				})
			}		
		}
		
	</script>
  </head>
  
  <body>
	<div id="logo">
   		<div class="logoImg">   
   			<h1>办事指南</h1>
   		</div>
   		<div class="logoLogin">
   			<span class="02-3">
   				<image  src="<%=mainWeb%>/images/admin/key01.png" class="pwdImg"/><image onclick="logOut()" src="<%=mainWeb%>/images/admin/logout01.png" class="logoutImg" />
	   		</span>
	   		<span  class="02-4">
		 		<c:choose>
		    		<c:when test="${empty Login_User_Info}">
		    			用户名：游客&nbsp;|&nbsp;
			        			<a href="#" title="登陆" onclick="btnLogin_Click();">登陆</a>
			        	
		    		</c:when>
		    		<c:otherwise>
		    			 用户名：${Login_User_Info.name}
		    		</c:otherwise>
		    	</c:choose>
	   		</span> 
   		</div>
	   	<div id="logo_updatePwd_dialog" class="easyui-dialog" title="修改密码" style="width:400px;height:200px;"  
		        data-options="iconCls:'icon-save',resizable:true,modal:true" buttons="#logo-updatePwd-buttons" closed="true">  
		    <table>
		    	<tr>
		    		<td class="td_label">
		    			原始密码：
		    		</td>
		    		<td>
		    			<input type="password" name="oldPwd" style="width:250px;"/>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td class="td_label">
		    			新密码：
		    		</td>
		    		<td>
		    			<input type="password" name="newPwd1"  style="width:250px;"/>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td class="td_label">
		    			重复密码：
		    		</td>
		    		<td>
		    			<input type="password" name="newPwd2"  style="width:250px;"/>
		    		</td>
		    	</tr>
		    </table>  
		</div>
		<div id="logo-updatePwd-buttons">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
					onclick="logoAction.save()">确定</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
					onclick="javascript:$('#logo_updatePwd_dialog').dialog('close')">关闭</a>
		</div>
   	</div>   
  </body>
</html>
