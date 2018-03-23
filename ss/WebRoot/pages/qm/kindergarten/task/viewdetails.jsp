<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String taskDate = request.getParameter("taskDate");
String teacherId = request.getParameter("teacherId");

%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>管理平台-学生照片管理</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    
    <link href="<c:url value='/resources/hplus4.1/css/font-awesome.min.css?v=4.4.0'></c:url>" rel="stylesheet">
    <link href="<c:url value='/resources/hplus4.1/css/animate.min.css'></c:url>" rel="stylesheet">
    <link href="<c:url value='/resources/hplus4.1/css/style.min.css?v=4.1.0'></c:url>" rel="stylesheet">
    
	
	<script type="text/javascript">
	
	 function AppMgr(){
	 
		var $table=$("#user_table");
		 
		 this.initDatas = function(){
	    	var tid = '<%=teacherId%>';
	    	var tdate = '<%=taskDate%>';
	    	$table.bootstrapTable({
    			url: getProjectName() + "/admin/kindergarten/task/viewDetails?teacherId="+tid+"&taskDate="+tdate,
                showExport:false,
                toolbar:'#toolbar',
               // exportDataType:"selected",
    			pagination:true,//是否显示分页栏
    			sidePagination:'server',//怎么分也？server or client
    			showRefresh:false,//是否显示刷新按钮
    			showColumns:false,//是否显示隐藏列按钮
                iconSize: "outline",//工具栏是否显示边框
                showFooter:false,//显示表底部
                striped:true,//隔行变色
                queryParams: function(params) {
                	return{
                		kindergartenId : $('#kindergartenId').val(),
                		name : $('#name').val(),
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
    		    columns: [/*{
    		        checkbox:true
    		    },*/{
    		        field: 'id',
    		        title: '序号'/*,
    		        visible:false*/
    		    },{
    		        field: 'type',
    		        title: '类型',
    		        formatter : function(value, row, index){
    		        		if(value == 1){
    		        			return "集体";
    		        		}else{
    		        			return "个人";
    		        		}
     		        }
    		    },{
    		    	field:'category',
    		    	title:'类别',
    		        formatter : function(value, row, index){
    		        		if(value == 1){
    		        			return "照片";
    		        		}else{
    		        			return "视频";
    		        		}
     		        }
    		    },{
    		    	field:'photoUrl',
    		    	title:'照片地址'
    		    },{
    		    	field:'videoUrl',
    		    	title:'视频地址'
    		    },{
    		    	field:'createTime',
    		    	title:'上传时间'
    		    }
    		    ]
    		});
	    	
	    	
	    	
	  	 }
	 }
	
	
	 $(document).ready(function() {
	  		new AppMgr().initDatas();
	  		 
	 });
	</script>
  </head>
  
 <body class="gray-bg" style="height: 100%;">
    <div class="wrapper wrapper-content animated fadeInRight" style="height: auto;">
        <div class="ibox float-e-margins"  style="height: 100%;">
            <div class="ibox-content"  style="height: 100%;">
                <table id="user_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
