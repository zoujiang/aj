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


    <title>管理平台-短信发送</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
	function AppMgr(){
		 this.initDatas = function(){
		 }
	 }
	 $(document).ready(function() {
	  		new AppMgr().initDatas();
		});
		function saveUser(){
			
			var sendTimeStr = $("#sendTimeStr").val();
			var currentDate=new Date();   
			var d1 = new Date(sendTimeStr.replace(/\-/g, "\/"));    
			if(sendTimeStr=="" || d1 < currentDate)    
			 {    
				  layer.msg("发送时间不能为空且必须大于当前时间", {title:'提示', btn: ['确定'],icon: 6}, function(index){
					});
					$("#btn_save").attr("disabled", false);
					return false; 
			 }  
			 var reciveUserTel = $("#reciveUserTel").val();
			 if(reciveUserTel == ""){
				 layer.msg("短信发送用户不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
					});
					$("#btn_save").attr("disabled", false);
					return false; 
			 }
			 var msgContent = $("#msgContent").val();
			 if(msgContent.trim() == ""){
				 layer.msg("短信发送内容不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
					});
					$("#btn_save").attr("disabled", false);
					return false; 
			 }
			
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/msgpublish/send",
			     dataType: "json",
			     success: function(obj){
			    	if(obj.success){
							layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
								var index = parent.layer.getFrameIndex(window.name);
								parent.$("button[name='refresh']").click();
								parent.layer.close(index);
								//window.location.reload();
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
		 
		 function pickUser(){
	    
	    	//iframe层
	        layer.open({
	            type: 2,
	            title: '用户选择',
	            shadeClose: true,
	            shade: 0.8,
	            area: ['1000px', '500px'],
	            btn:['确定','取消'],
	            closeBtn:false,
	            content: getProjectName() +"/pages/qm/msgpublish/pickuser.jsp",
	            yes: function(index, lay){
	            	var data = $(lay).find("iframe")[0].contentWindow.formData();
	            	var reciveUserTelIn = $("#reciveUserTel").val();
	            	if(reciveUserTelIn.trim() == ""){
	            		reciveUserTelIn = data;
	            	}else{
	            		reciveUserTelIn += "," + data;
	            	}
	            	$("#reciveUserTel").val(reciveUserTelIn);
	            	layer.close(index);
	            },
	        }); 
	    }
	</script>
  </head>
  
<body class="gray-bg" >
    <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content row">
            	<form class="form-horizontal" id="userForm" action="" enctype="multipart/form-data" method="post">
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><b style="color:red;">*</b>短信发送日期：</label> 
	               				<div class="col-sm-8">
	               					<input id="sendTimeStr" class="laydate-icon form-control layer-date" placeholder="短信发送日期"  style="width: 207px;" name="sendTimeStr" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <div class="col-sm-8">
	               				</div>
	                        </div>
                        </div>
                        
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><b style="color:red;">*</b>短信发送用户：</label> 
	               				<div class="col-sm-8">
	               					<textarea rows="5" cols="45" id="reciveUserTel" name="reciveUserTel" placeholder="多个用户以英文逗号“,”隔开"></textarea>  
	               				</div>
						    </div>

							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label"><button  type="button" class="btn btn-white" onclick='pickUser()'>点击选择</button></label> 
								<div class="col-sm-8">
								</div>
							</div>
                        </div>
                        <div class="row">
                        	<div class="form-group col-sm-6"> 
                      		   <label class="col-sm-4 control-label"><b style="color:red;">*</b>短信发送内容：</label>
	               				<div class="col-sm-8">
	               					<textarea rows="7" cols="45" id="msgContent" name="msgContent" placeholder="短信内容不能超过100个字"></textarea>  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <div class="col-sm-8" />
	                        </div>
                        </div>
               		</form>
               		</div>
               	<div class="hr-line-dashed"></div>
               		<div class="text-center">
               		 	<button id = "btn_save" type="button" class="btn btn-primary" style="float: left;margin-left: 50px;">保存</button>
                       </div>
                </div>
            </div>
      </div>
  </body>
</html>
