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


    <title>商户管理平台-添加商户品牌</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
		function saveUser(){
			
			var name = $("#brandName").val();
			if(name == ""){
				layer.msg("品牌名称不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
		    var sortIndex = $("#sortIndex").val();
		    if(sortIndex.length > 5){
				layer.msg("排序长度不能超过5位数", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/shop/brand/add",
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
	
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content row">
               	 	<form class="form-horizontal" id="userForm" action="<%=path %>/admin/shop/category/add" enctype="multipart/form-data" method="post">
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">名称：</label> 
	               				<div class="col-sm-7">
	               					<input type="text" class="form-control" name="brandName" id="brandName">  
	               				</div>
						    </div>
							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label">类型：</label>
								<div class="col-sm-7">
									<select class="form-control" name="type">
										<option  value="1">像馆</option>
										<option  value="2">幼儿园</option>
										<option  value="3">券商</option>
									</select>
								</div>
							</div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">排序：</label> 
	               				<div class="col-sm-7">
	               					<input type="text" class="form-control" name="sortIndex" id="sortIndex">  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">状态：</label>
	                            <div class="col-sm-7" style="line-height: 35px;">
	               					<input type="radio" name="status" value="0" checked="checked">正常&nbsp;&nbsp;&nbsp;&nbsp;
	               					<input type="radio" name="status" value="1">冻结
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label">品牌图标： </label>
								<div class="col-sm-7">
									<input type="file" class="form-control" name="file" id="icon">
								</div>
								<div class="form-group col-sm-6">
									<div class="col-sm-13" style="">
										<span style="font-size: 8px;"> (支持png,大小50k以内)</span>
									</div>
								</div>
							</div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">是否推荐：</label>
	                            <div class="col-sm-7" style="line-height: 35px;">
	               					<input type="radio" name="isRecommend" value="1" checked="checked">是&nbsp;&nbsp;&nbsp;&nbsp;
	               					<input type="radio" name="isRecommend" value="0">否
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
