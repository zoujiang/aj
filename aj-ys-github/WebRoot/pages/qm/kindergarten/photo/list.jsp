<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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


    <title>管理平台-幼儿园学生照片/视频管理</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#user_table");
    	
    	AppMgr.del = function(id,valid,e){
    		parent.layer.confirm('确定要删除该学生信息？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/kindergarten/student/del";
    			var obj = ajaxSubmit(url,{id:id, status:valid});
    			if(obj.success){
    				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
    					$table.bootstrapTable('refresh');
	   		  		});
    			}
    			else{
    				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
    					parent.layer.close(index);
    					//$("#testForm").resetForm();
    		  		});
    			}
    		}, function(index){
    			parent.layer.close(index);
    		});
		}
		//编辑
		AppMgr.manage = function(id,type,e){
			layer_show("管理学生照片/视频", getProjectName() +"/pages/qm/kindergarten/photo/manage.jsp?id="+id+"&type="+type,"930","500");
		}
		AppMgr.add = function(id, valid,e){
			layer_show("上传图片/视频", getProjectName() +"/pages/qm/kindergarten/photo/add.jsp?type=2&ownerId="+id,"850","400");
		};
		AppMgr.download = function(id, type,e){
			layer_show("下载", getProjectName() +"/pages/qm/kindergarten/photo/downloadlist.jsp?type=2&ownerId="+id,"850","400");
		};
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
    			url: getProjectName() + "/admin/kindergarten/photo/list",
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
                		gradeId : $('#gradeId').val(),
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
    		        title: 'ID'/*,
    		        visible:false*/
    		    },  {
    		    	field:'kindergartenName',
    		    	title:'幼儿园名称'
    		    },  {
    		    	field:'name',
    		    	title:'学生名称'
    		    },{
    		    	field:'gradeName',
    		    	title:'班级'
    		    },{
    		    	field:'photoNum',
    		    	title:'照片张数'
    		    },{
    		    	field:'firstTeacherName',
    		    	title:'教师'
    		    },/*{
    		    	field:'createUser',
    		    	title:'添加人'
    		    }, */{
    		    	field:'createTime',
    		    	title:'添加时间'
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
    		        	var edit = "";
    		        	if(row.photoNum > 0){
    		        		
   		        			 edit="<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.manage('" + row.id + "','2',event)\" style='cursor: pointer' >管理&nbsp;</a></span>";
    		        	}else{
    		        		
   		        			 edit="<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.add('" + row.id + "','0',event)\" style='cursor: pointer' >上传&nbsp;</a></span>";
    		        	}
   		        		var down="<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.download('" + row.id + "','2',event)\" style='cursor: pointer' >下载&nbsp;</a></span>";
    		        	return edit +down;
    		        },
    		        width: 200
    		    }]
    		});
    		
       }
    	
    	this.initActions = function(){
				// 搜索
			$('#btn_search').click(function() {
				 $table.bootstrapTable('refresh');
			});
			/* //导出数据
			$('#btn_search').click(function() {
			}); */
			//新增订单
			$('#btn_new').click(function(e){
				AppMgr.add(e);
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
    
    function showGradeInfo(){
    	
    	var kindergartenId = $("#kindergartenId").val();
    	if(kindergartenId != ''){
    		$.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/kindergarten/grade/list?offset=0&limit=100000&kindergartenId="+kindergartenId,
	             dataType: "json",
	             success: function(data){
	            	 var arr = data.rows;
	            	 var html = "<option value=''>---请选择---</option>";
	            	  $.each( arr, function(index, content)
	            	  { 
	            		  html += "<option value='"+content.id+"'>"+content.name+"</option>";
	            	  });
	            	  $("#gradeId").html(html);
	         	 }
	    	});
    		
    	}
    }
    function viewTeamPhoto(){
    	var gradeId = $("#gradeId").val();
    	if(gradeId == null || gradeId == ''){
    		layer.msg("请先选择班级", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				parent.layer.close(index);
	  		});
    	}else{
    		layer_show("管理集体照片/视频", getProjectName() +"/pages/qm/kindergarten/photo/manage.jsp?id="+gradeId+"&type=1","930","500");
    	}
    }
    function downloadTeamPhoto(){
    	var gradeId = $("#gradeId").val();
    	if(gradeId == null || gradeId == ''){
    		layer.msg("请先选择班级", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				parent.layer.close(index);
	  		});
    	}else{
    		layer_show("下载", getProjectName() +"/pages/qm/kindergarten/photo/downloadlist.jsp?type=1&ownerId="+gradeId,"850","400");
    	}
    }
    function addTeamPhoto(){
    	var gradeId = $("#gradeId").val();
    	if(gradeId == null || gradeId == ''){
    		layer.msg("请先选择班级", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				parent.layer.close(index);
	  		});
    	}else{
    		layer_show("上传集体图片/视频", getProjectName() +"/pages/qm/kindergarten/photo/add.jsp?type=1&ownerId="+gradeId,"850","400");
    	}
    }
    </script>
</head>

<body class="gray-bg" style="height: 100%;">
    <div class="wrapper wrapper-content animated fadeInRight" style="height: auto;">
        <div class="ibox float-e-margins"  style="height: 100%;">
            <div class="ibox-content"  style="height: 100%;">
            	<div class="btn-group hidden-xs" id="toolbar" role="group" style="margin-top: 10px;">
                    <div class="form-inline" style="width: 880px;float: left;">
                    	幼儿园名称： <select id="kindergartenId" class="form-control input-inline" name="kindergartenId" onchange="showGradeInfo()" width="280px"></select>  
                    	班级：<select id="gradeId" name="gradeId" class="form-control input-inline"  width="280px">
                    			</select>
                         学生姓名：<input type="text" placeholder="学生姓名" class="form-control input-inline"  id="name" name="name" width="280px">
                           <button  id="btn_search" type="button" class="btn btn-inline btn-default" style="margin-top: 4px;"> 搜索</button> 
                           <button  id="btn_search" type="button" class="btn btn-inline btn-default" style="margin-top: 4px;" onclick="addTeamPhoto()">上传集体照片/视频</button> 
                           <button  id="btn_search" type="button" class="btn btn-inline btn-default" style="margin-top: 4px;" onclick="viewTeamPhoto()">查看集体照片/视频</button> 
                           <button  id="btn_search" type="button" class="btn btn-inline btn-default" style="margin-top: 4px;" onclick="downloadTeamPhoto()">下载集体照片/视频</button> 
                    </div>
                </div>
                <table id="user_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
