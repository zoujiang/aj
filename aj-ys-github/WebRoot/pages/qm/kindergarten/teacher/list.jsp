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


    <title>管理平台-幼儿园教师列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#user_table");
    	//启用/冻结
    	AppMgr.del = function(id,valid,e){
    		parent.layer.confirm('确定要删除该教师信息？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/kindergarten/teacher/del";
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
		AppMgr.edit = function(id,valid,e){
			layer_show("编辑幼儿园教师", getProjectName() +"/pages/qm/kindergarten/teacher/edit.jsp?id="+id,"800","452");
		}
		//查看
		AppMgr.view = function(id,valid,e){
			layer_show("查看幼儿园教师", getProjectName() +"/pages/qm/kindergarten/teacher/view.jsp?id="+id,"800","452");
		}
		AppMgr.add = function(e){
    		layer_show("添加幼儿园教师", getProjectName() +"/pages/qm/kindergarten/teacher/add.jsp","800","452");
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
    			url: getProjectName() + "/admin/kindergarten/teacher/list",
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
                		name : $('#username').val(),
                		type : $('#type').val(),
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
    		    }, {
    		    	field:'type',
    		    	title:'教师类别',
    		    	formatter:function(value, row, index){
    		    		if(value == 1){
    		        		return "园长";
    		        	}else if(value == 2){
    		        		return "副园长";
    		        	}else if(value == 3){
    		        		return "管理人员";
    		        	}else if(value == 4){
    		        		//return "主教";
    		        		return "教师";
    		        	}else if(value == 5){
    		        		return "副教";
    		        	}else if(value == 6){
    		        		return "保育员";
    		        	} else{
    		        		return "其他"
    		        	}
    		    	}
    		    },{
    		    	field:'name',
    		    	title:'教师名称'
    		    },{
    		    	field:'tel',
    		    	title:'联系电话'
    		    },{
    		    	field:'sex',
    		    	title:'性别',
    		    	formatter:function(value, row, index){
    		    		if(value == 0){
    		        		return "男";
    		        	}else{
    		        		return "女";
    		        	}
    		    	}
    		    },{
    		    	field:'age',
    		    	title:'年龄'
    		    },{
    		    	field:'gradeNumNames',
    		    	title:'教师任课班级'
    		    },{
    		    	field:'createUserName',
    		    	title:'添加人'
    		    },{
    		    	field:'createTime',
    		    	title:'添加时间'
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
   		        		var edit="<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.edit('" + row.id + "','0',event)\" style='cursor: pointer' >编辑&nbsp;</a></span>";
   		        		var del="<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.del('" + row.id + "','0',event)\" style='cursor: pointer' >删除&nbsp;</a></span>";
   		        		var view="<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.view('" + row.id + "','0',event)\" style='cursor: pointer' >查看&nbsp;</a></span>";
    		        	return edit +del+ view;
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
    </script>
</head>

<body class="gray-bg" style="height: 100%;">
    <div class="wrapper wrapper-content animated fadeInRight" style="height: auto;">
        <div class="ibox float-e-margins"  style="height: 100%;">
            <div class="ibox-content"  style="height: 100%;">
            	<div class="btn-group hidden-xs" id="toolbar" role="group" style="margin-top: 10px;">
                    <div class="form-inline" style="width: 880px;float: left;">
                    	幼儿园名称： <select id="kindergartenId" class="form-control input-inline" name="kindergartenId" width="280px"></select>  
                    	教师类别：<select id="type" name="type" class="form-control input-inline"  width="280px">
                    				<option value="">-请选择-</option>
                    				<option value="1">园长</option>
                    				<option value="2">副园长</option>
                    				<option value="3">管理人员</option>
                    			<!-- 	<option value="4">主教</option>
                    				<option value="5">副教</option> -->
                    				<option value="4">教师</option> 
                    				<option value="6">保育员</option>
                    				<option value="7">其他</option>
                    			</select>
                    	教师姓名：<input type="text" placeholder="教师姓名" class="form-control input-inline"  id="username" name="username" width="280px"> 
                           <button  id="btn_search" type="button" class="btn btn-inline btn-default" style="margin-top: 4px;"> 搜索</button> 
	                    <button id="btn_new" type="button" style="margin-left: 10px;margin-top: 4px;" class="btn btn-inline btn-default"  title="添加">
	                        <i class="glyphicon glyphicon-plus" aria-hidden="true" ></i>
	                    </button>
                    </div>
                </div>
                <table id="user_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
