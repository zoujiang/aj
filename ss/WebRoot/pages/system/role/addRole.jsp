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
	<style type="text/css">
	.form-group{
		margin-bottom: 5px;
	}
	</style>
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
		function saveApply(){
			var url = getProjectName()+"/admin/role/edit";
    		var queryString = $('#roleForm').formSerialize();
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
				saveApply();
			});
			
		});
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="row wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content">
               	 	<form class="form-horizontal" id="roleForm">
               	 		<input type="hidden" name="id" id="roleId" value="<%= roleId %>">
               			<div class="form-group col-sm-12"> 
               				<label class="col-sm-4 control-label" style="float: left;">名称：</label> 
               				<div class="col-sm-8">
               					<input type="text" class="form-control col-sm-12" name="title" id="title"style="max-width: 240px;">  
               				</div>
					    </div>
					    <div class="form-group  col-sm-12">
                            <label class="col-sm-4 control-label" style="float: left;">代码：</label>
                            <div class="col-sm-8">
               					<input type="text" class="form-control col-sm-12" name="code" id="code"style="max-width: 240px;">  
               				</div>
                        </div>
               			<div class="form-group col-sm-12"> 
               				<label class="col-sm-4  checkbox-inline control-label" style="float: left;font-weight: 700;">状态：</label> 
               				<div class="col-sm-8">
	              				<div class="radio i-checks checkbox-inline col-sm-6">
	                                 <label><input type="radio" checked="checked" value="1" name="state"> <i></i>启用</label>
	                            </div>
	                            <div class="radio i-checks checkbox-inline col-sm-6">
	                                 <label><input type="radio" value="0" name="state"> <i></i>禁用</label>
	                            </div>
               				</div>
					    </div>
                		<div class="clearfix"></div>
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
