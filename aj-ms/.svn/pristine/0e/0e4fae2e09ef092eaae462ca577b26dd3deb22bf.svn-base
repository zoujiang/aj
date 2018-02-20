<%@page import="com.frame.core.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>接入审批</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript">
		function saveApprove(){
			var url = getProjectName()+"/admin/apply/approve";
    		var queryString = $('#applyForm').formSerialize();
			var obj = ajaxSubmit(url,queryString);
			if(obj.success){
				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
					var index = parent.layer.getFrameIndex(window.name);
					parent.$('#apv_table').bootstrapTable('refresh');
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
			$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green"})
			$("#btn_save").click(function(){
				$("#btn_save").attr("disabled", true);
				saveApprove();
			});
			
		});
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="row wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content">
               	 	<form class="form-horizontal" id="applyForm">
               	 		<input type="hidden" name="appId" id="appId" value="<%= request.getParameter("appId") %>">
               	 		<input type="hidden" name="opt" value="approve">
					    <div class="form-group col-sm-12"> 
               				<label class="col-sm-2  checkbox-inline control-label" style="float: left;font-weight: 700;">审批结果：</label> 
               				<div class="col-sm-10">
	              				<div class="radio i-checks checkbox-inline col-sm-6">
	                                 <label><input type="radio" checked="checked" value="1" name="apvSts"> <i></i>同意</label>
	                            </div>
	                            <div class="radio i-checks checkbox-inline col-sm-6">
	                                 <label><input type="radio" value="2" name="apvSts"> <i></i>不同意</label>
	                            </div>
               				</div>
					    </div>
               			<div class="form-group col-sm-12"> 
               				<label class="col-sm-2 control-label" style="float: left;">审批理由：</label>  
					        <div class="col-sm-10">
					        	<textarea id="apvReason" name="apvReason" class="form-control col-sm-12"></textarea>
					   		</div>
					    </div>
                		<div class="clearfix"></div>
               		</form>
               		<div class="hr-line-dashed"></div>
               		<div class="text-center">
               		 	<button id = "btn_save" type="button" class="btn btn-primary">审批</button>
                    </div>
                </div>
            </div>
      </div>
  </body>
</html>
