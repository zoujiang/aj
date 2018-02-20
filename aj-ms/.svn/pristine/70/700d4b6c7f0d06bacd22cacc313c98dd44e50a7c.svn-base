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


    <title>马拉松-添加选手号码生成规则</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript">
		function saveUser(){
			var url = getProjectName()+"/admin/gamecode/edit";
    		var queryString = $('#gameCodeForm').formSerialize();
			var obj = ajaxSubmit(url,queryString);
			if(obj.success){
				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
					var index = parent.layer.getFrameIndex(window.name);
					parent.$('#runnerpr_table').bootstrapTable('refresh');
					//parent.$("button[name='refresh']").click();
					parent.layer.close(index);
		  		});
			}
			else{
				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
			}

			$("#btn_save").attr("disabled", false);
		}
		function initSelect(){
	    	//$("#group").selectpicker({});  //初始化
	    	$.ajax({
	   	        type: "POST",
	   	        url: getProjectName() + "/admin/athlete/getSelect",
	   	        dataType: "text",
	   	     	async: false,
	   	        data:{},
	   	        success: function(data){
	   	        	if(data == null || data == ''){
	   					return;
	   				}
	   	        	//$("#group").empty();
	                $("#group").append(data);    
	                $("#group").selectpicker('render');
	                $("#group").selectpicker('refresh');
	   	        }
	   		});	
	    }
		function initData(){
	    	//$("#group").selectpicker({});  //初始化
	    	$.ajax({
	   	        type: "POST",
	   	        url: getProjectName() + "/admin/gamecode/getGameCode",
	   	        dataType: "json",
	   	     	async: false,
	   	        data:{
	   	        	cfgId:"<%= request.getParameter("cfgId")%>"
	   	        },
	   	        success: function(data){
	   	        	if(data == null || data == ''){
	   					return;
	   				}
	   	        	$('#group').selectpicker('val', data.groupId);//默认选中
	                //$("#group").val(data.groupId);    
	                $("#cfgPre").val(data.cfgPre);
	                $("#startNum").val(data.startNum);
	                $("#endNum").val(data.endNum);
	                $("#reservedNums").val(data.reservedNums);
	   	        }
	   		});	
	    }
		$(function(){
			initSelect();
			initData();
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
               	 	<form class="form-horizontal" id="gameCodeForm">
               	 		<input name="cfgId" type="hidden" value="<%= request.getParameter("cfgId")%>">
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">小组名称：</label> 
	               				<div class="col-sm-8">
	               					<select id="group" class="selectpicker form-control w70" title="请选择赛事组别" name="groupId">
					        		</select>
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">规则前缀：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="cfgPre" id="cfgPre">  
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">开始号码：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="startNum" id="startNum">  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">结束号码：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="endNum" id="endNum">  
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-12"> 
	               				<label class="col-sm-2 control-label" style="float: left;">保留号码：</label>  
						        <div class="col-sm-10">
						        	<textarea id="reservedNums" name="reservedNums" class="form-control col-sm-12"></textarea>
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
