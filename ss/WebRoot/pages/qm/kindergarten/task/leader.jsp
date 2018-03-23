<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>管理平台-校长任务情况</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#user_table");
    	AppMgr.send = function(id,valid,e){
    		parent.layer.confirm('确定已经发放奖励？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/kindergarten/task/send";
    			var obj = ajaxSubmit(url,{id:id});
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
		};
    	this.initDatas = function(){
    		
    		$.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/kindergarten/kindergarten/all",
	             dataType: "json",
	             success: function(data){
	            	 console.info(data)
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
    			url: getProjectName() + "/admin/kindergarten/task/leaderList",
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
    		        field: 'taskDate',
    		        title: '月份'/*,
    		        visible:false*/
    		    },{
    		    	field:'teacherName',
    		    	title:'校长名称'
    		    },{
    		    	field:'visitGradeNum',
    		    	title:'查看班级数'
    		    },{
    		    	field:'isGetReward',
    		    	title:'是否获得话费',
    		    	formatter : function(value, row, index){
    		        		if(value == 0){
    		        			return "是";
    		        		}else{
    		        			return "否";
    		        		}
     		        }
    		    },{
    		    	field:'remark',
    		    	title:'备注'
    		    },{
    		    	field:'renewPhone',
    		    	title:'充值话费手机号码'
    		    },{
    		    	field:'isSend',
    		    	title:'是否已下发',
    		    	formatter : function(value, row, index){
    		        		if(value == 0){
    		        			return "是";
    		        		}else{
    		        			return "否";
    		        		}
     		        }
    		    },{
    		    	field:'sendTime',
    		    	title:'话费下发时间'
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
   		        		var edit="<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.edit('" + row.id + "','0',event)\" style='cursor: pointer' >查看明细&nbsp;</a></span>";
   		        		if(row.isGetReward ==0 && row.isSend == 1){
   		        			edit += "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.send('" + row.id + "','0',event)\" style='cursor: pointer' >发放</a></span>";
   		        		}
    		        	return edit;
    		        },
    		        width: 200
    		    }
    		    ]
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
                    幼儿园： <select id="kindergartenId" class="form-control input-inline" name="kindergartenId" width="280px"></select>  
                    	校长姓名：<input type="text" placeholder="校长姓名" class="form-control input-inline"  id="name" name="name" width="280px"> 
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
