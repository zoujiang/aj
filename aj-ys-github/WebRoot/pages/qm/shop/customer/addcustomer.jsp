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


    <title>商户管理平台-添加客户帐号</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
	function AppMgr(){
		 this.initDatas = function(){
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/shop/baseinfo/all",
	             dataType: "json",
	             success: function(data){
	            	 var arr = data.data;
	            	 var html = "";
	            	  $.each( arr, function(index, content)
	            	  { 
	            		  html += "<option value='"+content.id+"'>"+content.shopName+"</option>";
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
			
			var shopId = $("#shopId").val();
			if(shopId == ""){
				layer.msg("所属商户不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var username = $("#userName").val();
			if(username == ""){
				layer.msg("客户名称不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			
			var tel = $("#userTel").val();
			if(tel == ""){
				layer.msg("客户手机号码不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}else{
				//验证客户手机号码是否存在
				var b = existAccount(tel, shopId);
				if(b){
					layer.msg("客户手机号已经存在", {title:'提示', btn: ['确定'],icon: 6}, function(index){
					});
					$("#btn_save").attr("disabled", false);
					return false;
				}
			}
			
		
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/shop/customer/add",
			     dataType: "json",
			     success: function(obj){
			    	if(obj.success){
							layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
								var index = parent.layer.getFrameIndex(window.name);
								//parent.$('#apv_table').bootstrapTable('refresh');
								parent.$("button[name='refresh']").click();
								parent.layer.close(index);
					  		});
					}else{
						layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
						});
					}
			     }
			 });
			
			$("#btn_save").attr("disabled", false);
			
		}
		 $(function(){
			$("#btn_save").click(function(){
				$("#btn_save").attr("disabled", true);
				saveUser();
			});
			
		}); 
		function existAccount(username, shopId){
			 var boo = false;
			 $.ajax({
	             type: "POST",
	             url: "<%=path %>/admin/shop/customer/findByUserTel",
	             data : "userTel="+username+"&shopId="+shopId,
	             dataType: "json",
	             async : false,
	             success: function(data){
	            	 if(data.success){
		            	 boo = true;
	                  }
	         	 }
	    	});
			 return boo;
		}
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content row">
               	 	<form class="form-horizontal" id="userForm" method="post">
               	 	
	               			<div class="form-group col-sm-12"> 
	               				<label class="col-sm-4 control-label" style="float: left;padding-top: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>所属商户：</label> 
	               				<div class="col-sm-8">
	               					<select class="form-control col-sm-12" name="shopId" id="shopId" style="max-width: 240px;">
								    </select>
	               				</div>
						    </div>
                       	    <div class="form-group col-sm-12">
	                            <label class="col-sm-4 control-label" style="float: left;padding-top: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>客户名称：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="userName" id="userName" style="max-width: 240px;"> 
	               				</div>
	                        </div>
	               			<div class="form-group col-sm-12"> 
	               				<label class="col-sm-4 control-label" style="float: left;padding-top: 10px;"><span style="color: red;">*</span>客户手机号码：</label> 
	               				<div class="col-sm-6">
	               					<input type="text" class="form-control" name="userTel" id="userTel" style="max-width: 240px;">  
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
