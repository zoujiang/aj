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
    		parent.layer.confirm('确定要删除该班级信息？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/kindergarten/grade/del";
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
			layer_show("编辑班级", getProjectName() +"/pages/qm/kindergarten/grade/edit.jsp?id="+id,"800","400");
		}
		AppMgr.add = function(e){
    		layer_show("新增班级", getProjectName() +"/pages/qm/kindergarten/grade/add.jsp","800","400");
		};
    	this.initDatas = function(){
    		
    		var date=new Date;
	   		 var year=date.getFullYear();
	   		 var serHtml = "<option value=''>---请选择---</option>";
	   		 for(var i=0 ; i<5; i++){
	   			 serHtml += "<option value="+(year -i)+">" + (year -i) +"</option>" ;
	   		 }
	   		 $("#series").html(serHtml)
    		
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
    			url: getProjectName() + "/admin/kindergarten/grade/list",
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
                		series : $('#series').val(),
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
    		    	field:'series',
    		    	title:'开学级数'
    		    },{
    		    	field:'classNo',
    		    	title:'班级排位'
    		    },{
    		    	field:'firstTeacherName',
    		    	title:'主教'
    		    },{
    		    	field:'secondTeacherName',
    		    	title:'副教'
    		    },{
    		    	field:'nurseName',
    		    	title:'保育员'
    		    },{
    		    	field:'rule',
    		    	title:'级数规则'
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
    		        	return edit +del;
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
                    	开学级数：<select id="series" name="series" class="form-control input-inline"  width="280px">
                    			</select>
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
