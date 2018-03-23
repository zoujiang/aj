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
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
		function initData(){
			$.ajax({
	   	        type: "POST",
	   	        url: getProjectName() + "/admin/athlete/getAthleteInfo",
	   	        dataType: "text",
	   	        data:{
	   	        	applyId:"<%= request.getParameter("applyId")%>",
	   	        	groupId:"<%= request.getParameter("groupId")%>"
	   	        },
	   	        success: function(data){
	   	        	if(data == null || data == ''){
	   					return;
	   				}
	   	        	$(data).insertBefore(".hr-line-dashed");
	   	        }
	   		});	
		}
		$(function(){
			initData();
			$("#btn_ok").click(function(){
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			});
			
		});
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="row wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content">
               		<div class="hr-line-dashed"></div>
               		<div class="text-center">
               		 	<button id = "btn_ok" type="button" class="btn btn-primary">确定</button>
                    </div>
                </div>
            </div>
      </div>
  </body>
</html>
