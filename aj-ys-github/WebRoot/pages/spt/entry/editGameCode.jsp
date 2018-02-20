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
	<link href="${ctx }/pages/spt/entry/css/wy-cx.css" rel="stylesheet">
	<style type="text/css">
		.wy-number .wy-number-xx .wy-xx-sr li .wy-xx-mc {
		    display: inline-block;
		    height: 30px;
		    line-height: 30px;
		    color: rgb(68, 68, 68);
		    text-align: right;
		}
		.wy-number .wy-number-xx .wy-xx-sr li .wy-xx-xx {
		    display: inline-block;
		    height: 30px;
		    line-height: 30px;
		    color: rgb(68, 68, 68);
		    text-align: left;
		    margin-left: 20px;
		}
	</style>
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
		function initData(){
			$.ajax({
	   	        type: "POST",
	   	        url: getProjectName() + "/admin/gamecode/getApplyById",
	   	        dataType: "json",
	   	        data:{
	   	        	applyId:"<%= request.getParameter("applyId")%>"
	   	        },
	   	        success: function(data){
	   	        	if(data == null || data == ''){
	   	        		var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
	   					return;
	   				} 
	   	        	if(data.success){
	   	        		$("#trueName").append(data.apply.trueName);
	   	        		$("#groupName").append(data.apply.groupName);
	   	        		$("#tradeNo").append(data.apply.tradeNo);
	   	        		$("#mobile").append(data.apply.mobile);
	   	        		$("#certNo").append(data.apply.certNo);
	   	        		$("#addDt").append(data.apply.addDt);
	   	        		$("#gamecode").val(data.apply.gameCode);
	   	        	} else {
	   	        		layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
	   	        			var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
	   					});
	   	        	}
	   	        }
	   		});	
		}
		$(function(){
			initData();
			$("#btn_ok").click(function(){
				$.ajax({
		   	        type: "POST",
		   	        url: getProjectName() + "/admin/gamecode/editGameCode",
		   	        dataType: "json",
		   	        data:{
		   	        	applyId:"<%= request.getParameter("applyId")%>",
		   	        	gameCode:$("#gamecode").val()
		   	        },
		   	        success: function(data){
		   	        	if(data == null || data == ''){
		   					return;
		   				} 
		   	        	if(data.success){
		   	        		layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
		   	        			var index = parent.layer.getFrameIndex(window.name);
		   	        			parent.$('#athlete_table').bootstrapTable('refresh');
								parent.layer.close(index);
		   					});
		   	        	} else {
		   	        		layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
		   					});
		   	        	}
		   	        }
		   		});	
			});
			
		});
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="row wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content">
                	<div class="wy-number">
					    <div class="wy-number-xx">
					        <!-- 大人信息 -->
					        <ul class="wy-xx-sr" style="width:60%;">
					            <li><span class="wy-xx-mc">选手编号 : </span><span class="wy-xx-xx">
					            	<input type="text" class="form-control" name="gamecode" id="gamecode" style="width:120px;">  
					            </span></li>
					        	<li><span class="wy-xx-mc">选手姓名 : </span><span class="wy-xx-xx" id="trueName"></span></li>
					        	<li><span class="wy-xx-mc">组别名称 : </span><span class="wy-xx-xx" id="groupName"></span></li>
					        	<li><span class="wy-xx-mc">订单号 : </span><span class="wy-xx-xx" id="tradeNo"></span></li>
					        	<li><span class="wy-xx-mc">手机号码 : </span><span class="wy-xx-xx" id="mobile"></span></li>
					        	<li><span class="wy-xx-mc">证件号码 : </span><span class="wy-xx-xx" id="certNo"></span></li>
					        	<li><span class="wy-xx-mc">团队名称 : </span><span class="wy-xx-xx" id="teamName"></span></li>
					            <li><span class="wy-xx-mc">报名时间 : </span><span class="wy-xx-xx" id="addDt"></span></li>
					        </ul>
					    </div>
					</div>
               		<div class="hr-line-dashed"></div>
               		<div class="text-center">
               		 	<button id = "btn_ok" type="button" class="btn btn-primary">确定</button>
                    </div>
                </div>
            </div>
      </div>
  </body>
</html>
