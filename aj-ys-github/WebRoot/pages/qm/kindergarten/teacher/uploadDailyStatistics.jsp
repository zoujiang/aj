<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
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


<title>管理平台-幼儿园教师上传统计</title>
<%@ include file="/pages/comm/jsp/inc.jsp"%>
<link rel="shortcut icon" href="favicon.ico">
<script type="text/javascript">
		    function AppMgr(){
		    	var $table=$("#user_table");
		    	this.initDatas = function(){
		    		
		    		$.ajax({
			             type: "GET",
			             url: "<%=path %>/admin/kindergarten/kindergarten/all",
			             dataType: "json",
			             success: function(data){
			            	 var arr = data.data;
			            	 var html = "<option value=''>---请选择---</option>";
			            	  $.each( arr, function(index, content)
			            	  { 
			            		  html += "<option value='"+content.id+"'>"+content.name+"</option>";
			            	  });
			            	  $("#kindergartenId").html(html);
			         	 }
			    	});
		    		
					 $table.bootstrapTable({
		    			url: getProjectName() + "/admin/kindergarten/teacher/uploadDailyStatistics",
		                showExport:false,
		                toolbar:'#toolbar',
		               // exportDataType:"selected",
		    			pagination:true,//是否显示分页栏
		    			sidePagination:'server',//怎么分也？server or client
		    			showRefresh:true,//是否显示刷新按钮
		    			showColumns:true,//是否显示隐藏列按钮
		                iconSize: "outline",//工具栏是否显示边框
		                showFooter:false,//显示表底部
		                striped:true,//隔行变色
		                queryParams: function(params) {
		                	return{
		                		kindergartenId : $('#kindergartenId').val(),
		                		username : $('#username').val(),
		                		statistics : $('#statistics').val(),
			                	limit:params.limit,
			                	offset:params.offset
			                };
		                },
		                search:false,//是否显示搜索
		                //searchText:'名称',
		                strictSearch: true,
		                searchOnEnterKey:true,//点击回车查询
		                icons: {
		                    refresh: "glyphicon-repeat",
		                    columns: "glyphicon-list"
		                },
		                clickToSelect:true,//点击选中
		                singleSelect:true,//是否单选
		                idField:'id',
		                //http://bootstrap-table.wenzhixin.net.cn/documentation/
		    		    columns: [
		    		    	{field: 'kindergartenName',
		    		    		title:'学校名称'
		    		    	},
		    		    {
		    		    	field:'staticticsDate',
		    		    	title:'日期'
		    		    },{
		    		    	field:'teacherName',
		    		    	title:'教师名称'
		    		    },{
		    		    	field:'photoNum',
		    		    	title:'上传照片张数'
		    		    },{
		    		    	field:'videoNum',
		    		    	title:'上传视频个数'
		    		    },{
		    		    	field:'honorNum',
		    		    	title:'上传荣誉个数'
		    		    }]
		    		});
		    	}
		    	this.initActions = function(){
						// 搜索
					$('#btn_search').click(function() {
						 $table.bootstrapTable('refresh');
					});
				};
					
				
				this.init = function() {
				 	this.initDatas();
				 	this.initActions();
		    	}	
		    }
		    $(document).ready(function() {
		  		new AppMgr().init();
			});
		    
    </script>
</head>

<body class="gray-bg" style="height: 100%;">
	<div class="wrapper wrapper-content animated fadeInRight"
		style="height: auto;">
		<div class="ibox float-e-margins" style="height: 100%;">
			<div class="ibox-content" style="height: 100%;">
				<div class="btn-group hidden-xs" id="toolbar" role="group"
					style="margin-top: 10px;">
					<div class="form-inline" style="width: 880px;float: left;">
						幼儿园名称： <select id="kindergartenId"
							class="form-control input-inline" name="kindergartenId"
							width="280px"></select> 教师姓名：<input type="text"
							placeholder="教师姓名" class="form-control input-inline"
							id="username" name="username" width="280px"> 日期：<input
							id="statistics" class="laydate-icon form-control layer-date"
							placeholder="日期" style="width: 150px;"
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
						<button id="btn_search" type="button"
							class="btn btn-inline btn-default" style="margin-top: 4px;">
							搜索</button>
					</div>
				</div>
				<table id="user_table"></table>
			</div>
		</div>
	</div>
</body>
</html>
