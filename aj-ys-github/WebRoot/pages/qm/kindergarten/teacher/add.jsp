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


    <title>管理平台-添加幼儿园教师</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
	function AppMgr(){
		 this.initDatas = function(){
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/kindergarten/kindergarten/all",
	             dataType: "json",
	             success: function(data){
	            	 var arr = data.data;
	            	 var html = "";
	            	  $.each( arr, function(index, content)
	            	  { 
	            		  html += "<option value='"+content.id+"'>"+content.name+"</option>";
	            	  });
	            	  $("#kindergartenId").html(html);
	         	 }
	    	});
			 
	  	 }
	 }
	 $(document).ready(function() {
	  		new AppMgr().initDatas();
		});
		function saveUser(){
			
			$("#btn_save").attr("disabled", true);
			var kindergartenId = $("#kindergartenId").val();
			if(kindergartenId == ""){
				layer.msg("所属幼儿园不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var username = $("#name").val();
			if(username == ""){
				layer.msg("教师姓名不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
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
			var rechargeTelNo = $("#rechargeTelNo").val();
			if(rechargeTelNo == ""){
				layer.msg("充值话费号码不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
		
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/kindergarten/teacher/add",
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
               	 	<form class="form-horizontal" id="userForm" action="<%=path %>/admin/shop/account/add" method="post">
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><span style="color: red;">*</span>所属幼儿园：</label> 
	               				<div class="col-sm-8">
	               					<select class="form-control" name="kindergartenId" id="kindergartenId">
								    </select>
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                        </div>
                        </div>
                        <div class="row">
                        <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><span style="color: red;">*</span>教师姓名：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="name" id="name"> 
	               				</div>
	                        </div>
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><span style="color: red;">*</span>性别：</label> 
	               				<div class="col-sm-8" style="margin-top: 5px;>
	               					<input type="radio" name="sex" value="0" checked="checked"> 男
	               					<input type="radio" name="sex" value="1"> 女
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
	                            <label class="col-sm-4 control-label">教师类别：</label>
	                            <div class="col-sm-8">
	               					<select id="type" name="type" class="form-control input-inline"  width="280px">
                    				<option >---请选择---</option>
                    				<option value="1">园长</option>
                    				<option value="2">副园长</option>
                    				<option value="3">管理人员</option>
                    				<option value="4">主教</option>
                    				<option value="5">副教</option>
                    				<option value="6">保育员</option>
                    				<option value="7">其他</option>
                    			</select>
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">年龄：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="age" id="age">  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><span style="color: red;">*</span>充值话费号码：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="rechargeTelNo" id="rechargeTelNo">  
	               				</div>
						    </div>
                        </div>
                        <div class="row">
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">教师头像：</label>
	                            <div class="col-sm-8">
	               					<input class="form-control" type="file" id="logo" name="logoImg" >
	               				</div>
	                        </div>
	                        <div class="form-group col-sm-6"> 
	               				<div class="col-sm-12" style="margin-left: -20px;margin-top: 8px;">
				                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (支持png，大小50k以内)
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
