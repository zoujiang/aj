<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>接入申请</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
		function saveApply(){
			var url = getProjectName()+"/admin/apply/edit";
    		var queryString = $('#applyForm').formSerialize();
			var obj = ajaxSubmit(url,queryString);
			if(obj.success){
				layer.msg(obj.message, {time:2000,title:'提示', btn: ['确定'],icon: 6}, function(index){
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
			var start={
					elem:"#srvStartDt",
					format:"YYYY/MM/DD hh:mm:ss",
					min:laydate.now(),
					istime:true,
					istoday:false,
					event:"focus",
					choose:function(datas){
						end.min=datas;
						end.start=datas
				}};
			var end={
					elem:"#srvEndDt",
					format:"YYYY/MM/DD hh:mm:ss",
					min:laydate.now(),
					istime:true,
					istoday:false,
					event:"focus",
					choose:function(datas){
						start.max=datas
					}};
			laydate(start);
			laydate(end);
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
               	 	<form class="form-horizontal" id="applyForm">
               			<div class="form-group col-sm-6"> 
               				<label class="col-sm-4 control-label" style="float: left;">景区名称：</label> 
               				<div class="col-sm-8">
               					<input type="text" class="form-control col-sm-12" name="appName" id="appName">  
               				</div>
					    </div>
               			<div class="form-group col-sm-6"> 
               				<label class="col-sm-4 control-label" style="float: left;">景区密钥：</label>  
					        <div class="col-sm-8">
					        	<input type="text" class="form-control col-sm-12" name="appKey" id="appKey" >
					        </div>
					    </div>
					    <div class="form-group col-sm-12"> 
               				<label class="col-sm-2 control-label" style="float: left;">后台管理地址：</label> 
               				<div class="col-sm-10">
               					<input type="text" class="form-control col-sm-12" name="appMsUrl" id="appMsUrl">  
               				</div>
					    </div>
               			<div class="form-group col-sm-12"> 
               				<label class="col-sm-2 control-label" style="float: left;">管控平台地址：</label>  
					        <div class="col-sm-10" >
					        	<input type="text" class="form-control col-sm-12" name="appBiUrl" id="appBiUrl" >
					        </div>
					    </div>
					    <div class="form-group col-sm-12"> 
               				<label class="col-sm-2 control-label" style="float: left;">客户端接入地址：</label> 
               				<div class="col-sm-10">
               					<input type="text" class="form-control col-sm-12" name="appCltUrl" id="appCltUrl">  
               				</div>
					    </div>
               			<div class="form-group col-sm-6"> 
               				<label class="col-sm-4 control-label" style="float: left;">开通时间：</label>  
					        <div class="col-sm-8">
					        	<input type="text" class="laydate-icon form-control layer-date col-sm-12" name="srvStartDt" id="srvStartDt" >
					        </div>
					    </div>
					    <div class="form-group col-sm-6"> 
               				<label class="col-sm-4 control-label" style="float: left;">到期时间：</label>  
					        <div class="col-sm-8">
					        	<input type="text" class="laydate-icon form-control layer-date col-sm-12" name="srvEndDt" id="srvEndDt" >
					        </div>
					    </div>
               			<div class="form-group col-sm-12"> 
               				<label class="col-sm-2 control-label" style="float: left;">景区描述：</label>  
					        <div class="col-sm-10">
					        	<textarea id="appDesc" name="appDesc" class="form-control col-sm-12"></textarea>
					   		</div>
					    </div>
                		<div class="clearfix"></div>
               		</form>
               		<div class="hr-line-dashed"></div>
               		<div class="text-center">
               		 	<!-- 
               		 	<button id = "btn_add" type="button" class="btn btn-primary" disabled="disabled">新增 </button>
               		 	<button id = "btn_edit" type="button" class="btn btn-primary" disabled="disabled">编辑 </button>
               		 	<button id = "btn_del" type="button" class="btn btn-primary" disabled="disabled">删除 </button>
               		 	 -->
               		 	<button id = "btn_save" type="button" class="btn btn-primary">提交申请</button>
                       </div>
                </div>
            </div>
      </div>
  </body>
</html>
