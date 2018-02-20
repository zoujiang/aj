<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = request.getParameter("id");
if(id == null || "".equals(id)){
	id = "";
}
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-意见反馈</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
	
	function AppMgr(){
		 this.initDatas = function(){
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/shop/feedback/find",
	             data: "id=<%=id %>",
	             dataType: "json",
	             success: function(data){
	                  if(data.success){
	                  	  $("#id").val(data.message.id);
	                	  $("#fbCategory").find("option[value='"+data.message.fbCategory+"']").attr("selected",true);
	                	  $("#fbContent").text(data.message.fbContent);
	                  }else{
	                	  layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
							});
	                  }
	         	 }
	    	});
			 
	  	 }
	 }
	 $(document).ready(function() {
	  		new AppMgr().initDatas();
		});
	 
		function saveUser(){
			
			var fbContent = $("#fbContent").val();
			if(fbContent == ""){
				layer.msg("反馈内容不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
		
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/shop/feedback/update",
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
               	 	<form class="form-horizontal" id="userForm" action=""  method="post">
               	 		<input type="hidden" name="id" id="id">
                         <div class="form-group col-sm-12">
	                            <label class="col-sm-4 control-label" style="float: left;padding-top: 10px;">&nbsp;&nbsp;意见分类</label>
	                            <div class="col-sm-8">
	               					<select class="form-control" style="width: 300px" name="fbCategory" id="fbCategory">
	               						<option value="系统使用类">系统使用类</option>
	               						<option value="财务类">财务类</option>
	               						<option value="BUG类">BUG类</option>
	               						<option value="新增功能申请类">新增功能申请类</option>
	               					</select>
	               				</div>
	                        </div>
	               			<div class="form-group col-sm-12"> 
	               				<label class="col-sm-4 control-label" style="float: left;padding-top: 10px;"><span style="color: red;">*</span>反馈内容</label> 
	               				<div class="col-sm-6">
	               					<textarea class="form-control" style="width: 300px" rows="5" cols="50" id ="fbContent" name="fbContent"></textarea>
	               				</div>
						    </div>
               		</form>
               		<div class="hr-line-dashed"></div>
               		<div class="text-center">
               		 	<button id = "btn_save" type="button" class="btn btn-primary">提交</button>
                       </div>
                </div>
            </div>
      </div>
  </body>
</html>
