<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = request.getParameter("id");
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>管理平台-幼儿园概况</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>

	<script type="text/javascript" src="${ctx }/resources/echarts/echarts.js"></script>
	

	<script type="text/javascript">
	
		function AppMgr(){
			 this.initDatas = function(){
				 
				 //展示3个空的曲线图
				 var photoDailyChart = echarts.init( document.getElementById("photoDaily"));
				 var videoDailyChart = echarts.init( document.getElementById("videoDaily"));
				 var honorDailyChart = echarts.init( document.getElementById("honorDaily"));

			       // 显示标题，图例和空的坐标轴
					photoDailyChart.setOption({
					    title: {
					        text: '照片数按日曲线'
					    },
					    tooltip: {},
					    legend: {
					        data:['数量']
					    },
					    xAxis: {
					        data: []
					    },
					    yAxis: {},
					    series: [{
					        name: '日期',
					        type: 'line',
					        data: []
					    }]
					});
					videoDailyChart.setOption({
					    title: {
					        text: '视频数按日曲线'
					    },
					    tooltip: {},
					    legend: {
					        data:['数量']
					    },
					    xAxis: {
					        data: []
					    },
					    yAxis: {},
					    series: [{
					        name: '日期',
					        type: 'line',
					        data: []
					    }]
					});
					honorDailyChart.setOption({
					    title: {
					        text: '荣誉数按日曲线'
					    },
					    tooltip: {},
					    legend: {
					        data:['数量']
					    },
					    xAxis: {
					        data: []
					    },
					    yAxis: {},
					    series: [{
					        name: '日期',
					        type: 'line',
					        data: []
					    }]
					});
				 
				 
				 
				 $.ajax({
		             type: "GET",
		             url: "<%=path %>/admin/kindergarten/kindergarten/statistics?id=<%=id %>",
		             dataType: "json",
		             success: function(data){
		            	if(data.success){
		            		console.info(data.data.seriesNum)
		            		$("#seriesShow").text(data.data.seriesNum);
		            		$("#gradeShow").text(data.data.gradeNum);
		            		$("#teacherShow").text(data.data.teacherNum);
		            		$("#studentShow").text(data.data.studentNum);
		            		
		            		photoDailyChart.setOption({
		         		        xAxis: {
		         		            data: data.data.dayList
		         		        },
		         		        series: [{
		         		            // 根据名字对应到相应的系列
		         		            name: '照片数量',
		         		            data: data.data.photoDailyList
		         		        }]
		         		    });
		            		videoDailyChart.setOption({
		         		        xAxis: {
		         		            data: data.data.dayList
		         		        },
		         		        series: [{
		         		            // 根据名字对应到相应的系列
		         		            name: '视频数量',
		         		            data: data.data.videoDailyList
		         		        }]
		         		    });
		            		honorDailyChart.setOption({
		         		        xAxis: {
		         		            data: data.data.dayList
		         		        },
		         		        series: [{
		         		            // 根据名字对应到相应的系列
		         		            name: '荣誉数量',
		         		            data: data.data.honorDailyList
		         		        }]
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
			
			$("#btn_save").attr("disabled", true);
			
		
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/kindergarten/kindergarten/add",
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
               	 		<span><b style="font-size: 20px;">年级数量：</b> <b id="seriesShow"  style="font-size: 16px;"></b> </span>
               	 		<span style="margin-left: 15px;"><b style="font-size: 20px;">班级数量：</b> <b id="gradeShow"  style="font-size: 16px;"></b> </span>
               	 		<span style="margin-left: 15px;"><b style="font-size: 20px;">教师数量：</b> <b id="teacherShow"  style="font-size: 16px;"></b> </span>
               	 		<span style="margin-left: 15px;"><b style="font-size: 20px;">学生数量：</b> <b id="studentShow"  style="font-size: 16px;"></b> </span>
               	 		<hr/>
               	 		<div id="photoDaily" style="width: 750px;height:400px;"></div>
               	 		<div id="videoDaily" style="width: 750px;height:400px;"></div>
               	 		<div id="honorDaily" style="width: 750px;height:400px;"></div>
               	 		
               		</form>
               		<div class="hr-line-dashed"></div>
               		<div class="text-center">
               		 	<button id = "btn_save" type="button" class="btn btn-primary">确定</button>
                       </div>
                </div>
            </div>
      </div>
  </body>
   <style >
	.modal-backdrop{
		display :none;
		
	}
	.modal-dialog{
		z-index: 2 !important
	}

</style>
</html>
