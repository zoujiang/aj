<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = request.getParameter("id");

%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>管理平台-编辑幼儿园帐号</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
	 function AppMgr(){
		 var shopId = "";
		 this.initDatas = function(){
		
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/kindergarten/account/find",
	             data: "id=<%=id %>",
	             dataType: "json",
	             success: function(data){
	                  if(data.success){
	                	  shopId = data.message.kindergartenId;
	                	  $("#username").val(data.message.username);
	                	  $("#shopId").val(data.message.kindergartenId);
	                	  $("#nickname").val(data.message.nickname);
	                	  $("#tel").val(data.message.tel);
	                	  $("#email").val(data.message.email);
	                	  $("#password").val(data.message.password);
	                	  if(data.message.status == 1){
	                		  $("#ra0").attr("checked","checked");
	                	  }else{
	                		  $("#ra1").attr("checked","checked");
	                	  }
	                  }else{
	                	  layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
							});
	                  }
	         	 }
	    	});
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/kindergarten/kindergarten/all",
	             dataType: "json",
	             success: function(data){
	            	 var arr = data.data;
	            	 var html = "";
	            	  $.each( arr, function(index, content)
	            	  { 
	            		  if(content.id == shopId){
	            		  		html += "<option selected='selected' value='"+content.id+"'>"+content.name+"</option>";
	            		  }else{
		            		    html += "<option value='"+content.id+"'>"+content.name+"</option>";
	            		  }
	            	  });
	            	  $("#shopId").html(html);
	         	 }
	    	});
			 
	  	 }
	 }
	 $(document).ready(function() {
	  		new AppMgr().initDatas();
		});
	 
	 function saveUser(){
			
		$("#btn_save").attr("disabled", true);
		 var shopId = $("#shopId").val();
			if(shopId == ""){
				layer.msg("所属幼儿园不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var username = $("#username").val();
			if(username == ""){
				layer.msg("登录帐号不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}else{
				var b = existAccount(username);
				if(b){
					layer.msg("登录帐号被占用", {title:'提示', btn: ['确定'],icon: 6}, function(index){
					});
					$("#btn_save").attr("disabled", false);
					return false;
				}
			}
			var nickname = $("#nickname").val();
			if(nickname == ""){
				layer.msg("姓名不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var tel = $("#tel").val();
			if(tel == ""){
				layer.msg("联系电话不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var password = $("#password").val();
			if(password == ""){
				layer.msg("登陆密码不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
		
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/kindergarten/account/update",
			     dataType: "json",
			     success: function(obj){
			    	 if(obj.success){
							layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
								var index = parent.layer.getFrameIndex(window.name);
								//parent.$('#apv_table').bootstrapTable('refresh');
								parent.$("button[name='refresh']").click();
								parent.layer.close(index);
					  		});
						}
						else{
							layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
							});
						}
			     }
			 });
			
			$("#btn_save").attr("disabled", false);
		}
	 function existAccount(username){
		 var boo = false;
		 $.ajax({
             type: "POST",
             url: "<%=path %>/admin/kindergarten/account/findByUsername",
             data : "username="+username,
             dataType: "json",
             async : false,
             success: function(data){
            	 if(data.success){
            		 if(data.message.id != $("#roleId").val()){
	            		 boo = true;
            		 }
                  }
         	 }
    	});
		 return boo;
	}
	 function showmsg(){
		 layer.msg("登录帐号被占用", {title:'提示', btn: ['确定'],icon: 6}, function(index){
			});
			$("#btn_save").attr("disabled", false);
			return false;
	 }
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content row">
               	 	<form class="form-horizontal" id="userForm"  method="post">
               	 		<input type="hidden" name="id" id="roleId" value="<%=id %>">
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><span style="color: red;">*</span>所属幼儿园：</label> 
	               				<div class="col-sm-8">
	               					<select class="form-control" name="kindergartenId" id="shopId">
								    </select>
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">状态：</label>
	                             <div class="col-sm-8">
	               					<input type="radio" id="ra0" name="status" value="0" checked="checked">正常&nbsp;&nbsp;&nbsp;&nbsp;
	               					<input type="radio" id="ra1" name="status" value="1">冻结
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
                        <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><span style="color: red;">*</span>登陆帐号：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="username" readonly="readonly" id="username"> 
	               				</div>
	                        </div>
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><span style="color: red;">*</span>姓名：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="nickname" id="nickname">  
	               				</div>
						    </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><span style="color: red;">*</span>联系电话：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="tel" id="tel">  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">邮箱：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="email" id="email" autocomplete="off"> 
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><span style="color: red;">*</span>登录密码：</label> 
	               				<div class="col-sm-8">
	               					<input type="password" class="form-control" name="password" id="password" autocomplete="new-password">  
	               				</div>
						    </div>
                        </div>
               		</form>
               		<div class="hr-line-dashed"></div>
               		<div class="text-center">
               		 	<button id = "btn_save" onclick="saveUser()" type="button" class="btn btn-primary">保存</button>
                       </div>
                </div>
            </div>
      </div>
  </body>
</html>
