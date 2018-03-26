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


    <title>管理平台-添加幼儿园学生</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
	function AppMgr(){
		 this.initDatas = function(){
			 showGradeInfo();
	  	 }
		 
	 }
	 $(document).ready(function() {
	  		new AppMgr().initDatas();
		});
		function saveUser(){
			
			var gradeId = $("#gradeId").val();
			if(gradeId == null || gradeId == ""){
				layer.msg("所属班级不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var name = $("#name").val();
			if(name == null || name == ''){
				layer.msg("学生姓名能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var parentsName = $("#parentsName").val();
			if(parentsName == null || parentsName == ''){
				layer.msg("家长姓名能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var parentsTel = $("#parentsTel").val();
			if(parentsTel == null || parentsTel == ''){
				layer.msg("家长手机号码不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
		
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/kindergarten/student/add",
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
		 
		 
		 function showGradeInfo(){
		    	
				 var kindergartenId = '<%=request.getSession().getAttribute("Login_User_Shop_Id")%>';
		    	if(kindergartenId != ''){
		    		$.ajax({
			             type: "GET",
			             url: "<%=path %>/admin/kindergarten/grade/list?offset=0&limit=100000&kindergartenId="+kindergartenId,
			             dataType: "json",
			             success: function(data){
			            	 var arr = data.rows;
			            	 var html = "<option value=''>---请选择---</option>";
			            	  $.each( arr, function(index, content)
			            	  { 
			            		  html += "<option value='"+content.id+"'>"+content.name+"</option>";
			            	  });
			            	  $("#gradeId").html(html);
			         	 }
			    	});
		    		
		    	}
		    }
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content row">
               	 	<form class="form-horizontal" id="userForm" action="<%=path %>/admin/shop/account/add" method="post">
               	 		<div class="row">
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><span style="color: red;">*</span>学生姓名：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="name" id="name">
	               				</div>
	                        </div>
	                         <div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">所属班级：</label> 
	               				 <div class="col-sm-8">
	               					<select id="gradeId" name="gradeId" class="form-control input-inline"  width="280px">
                    				</select>	
	               				</div>
						    </div>
                        </div>
                        <div class="row">
                       
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">年龄：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control"  name="age" id="age" > 
	               				</div>
						    </div>
						    <div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><span style="color: red;">*</span>性别：</label> 
	               				<div class="col-sm-8" style="margin-top: 5px;">
	               					<input type="radio" name="sex" id="sex_0" value="0" checked="checked"> 男 &nbsp;&nbsp;
	               					<input type="radio" name="sex" id="sex_1" value="1"> 女
	               				</div>
						    </div>
                        </div>
                        <div class="row">
	               			
						    <div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><span style="color: red;">*</span>家长姓名：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control"  name="parentsName" id="parentsName" > 
	               				</div>
						    </div>
						    <div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><span style="color: red;">*</span>手机号码：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control"  name="parentsTel" id="parentsTel" > 
	               				</div>
						    </div>
                        </div>
                        <div class="row">
	               			
						   
						    <div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">学生头像：</label> 
	               				 <div class="col-sm-8">
	               					<input class="form-control" type="file" id="logo" name="logoImg" >
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
