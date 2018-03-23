<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String roleId = request.getParameter("roleId");
if(roleId == null || "".equals(roleId)){
	roleId = "";
}
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-主机添加</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
		function saveUser(){
			var url = getProjectName()+"/admin/user/edit";
    		var queryString = $('#userForm').formSerialize();
			var obj = ajaxSubmit(url,queryString);
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

			$("#btn_save").attr("disabled", false);
		}
		$(function(){
			$("#btn_save").click(function(){
				$("#btn_save").attr("disabled", true);
				saveUser();
			});
			
		});
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content row">
               	 	<form class="form-horizontal" id="userForm">
               	 		<input type="hidden" name="id" id="roleId" value="<%= roleId %>">
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">名称：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="name" id="name">  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">账号：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="account" id="account">  
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">固话：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="tele" id="tele">  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">手机：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="mobile" id="mobile">  
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">密码：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="pwd" id="pwd">  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">确认密码：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="ckpwd" id="ckpwd">  
	               				</div>
	                        </div>
                        </div>
                        
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">邮箱：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="tele" id="tele">  
	               				</div>
						    </div>
						     <div class="form-group col-sm-6">
                                <label class="col-sm-4 control-label">是否启用</label>
                                <div class="col-sm-8">
                                	<label class="radio-inline">
                                		<input type="radio" value="1" name="isEnabled">启用</label>
                                	<label class="radio-inline">
                                		<input type="radio" value="2" name="isEnabled">冻结</label>
                                </div>
                            </div>
						</div>
               		</form>
               		<div class="hr-line-dashed"></div>
               		<div class="text-center">
               		 	<button id = "btn_save" type="button" class="btn btn-primary">保存</button>
                       </div>
                </div>
            </div>
      </div>
  </body>
</html>
