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


    <title>主机添加</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
		function saveApply(){
			var url = getProjectName()+"/admin/srv/edit";
    		var queryString = $('#serverForm').formSerialize();
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
               	 	<form class="form-horizontal" id="serverForm">
               	 		<input type="hidden" name="appId" id="appId" value="<%= request.getParameter("appId") %>">
               			<div class="form-group col-sm-6"> 
               				<label class="col-sm-4 control-label" style="float: left;">服务器名称：</label> 
               				<div class="col-sm-8">
               					<input type="text" class="form-control col-sm-12" name="srvName" id="srvName">  
               				</div>
					    </div>
					    <div class="form-group  col-sm-6">
                            <label class="col-sm-4 control-label" style="float: left;">服务器类型</label>

                            <div class="col-sm-8">
                                <select class="form-control m-b col-sm-12" name="srvType">
                                    <option value="1" selected="selected">app</option>
                                    <option value="2">数据库</option>
                                </select>
                            </div>
                        </div>
               			<div class="form-group col-sm-12"> 
               				<label class="col-sm-2 control-label" style="float: left;">所在地：</label>  
					        <div class="col-sm-10">
					        	<input type="text" class="form-control col-sm-12" name="srvPlace" id="srvPlace" >
					        </div>
					    </div>
					    <div class="form-group col-sm-12"> 
               				<label class="col-sm-2 control-label" style="float: left;">IP地址：</label> 
               				<div class="col-sm-10">
               					<input type="text" class="form-control col-sm-12" name="srvIp" id="srvIp">  
               				</div>
					    </div>
					    <div class="form-group col-sm-12"> 
               				<label class="col-sm-2 control-label" style="float: left;">配置信息：</label>  
					        <div class="col-sm-10">
					        	<textarea id="srvCfg" name="srvCfg" class="form-control col-sm-12"></textarea>
					   		</div>
					    </div>
					    <div class="form-group col-sm-6"> 
               				<label class="col-sm-4 control-label" style="float: left;">开通时间：</label>  
					        <div class="col-sm-8">
					        	<input type="text" class="laydate-icon form-control layer-date col-sm-12" name="startDt" id="srvStartDt" >
					        </div>
					    </div>
					    <div class="form-group col-sm-6"> 
               				<label class="col-sm-4 control-label" style="float: left;">到期时间：</label>  
					        <div class="col-sm-8">
					        	<input type="text" class="laydate-icon form-control layer-date col-sm-12" name="endDt" id="srvEndDt" >
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
               		 	<button id = "btn_save" type="button" class="btn btn-primary">保存</button>
                       </div>
                </div>
            </div>
      </div>
  </body>
</html>
