<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>管理平台-短信发送任务列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <style type="text/css">
		.table{
		    table-layout: fixed;
		}
	</style>
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#user_table");
    	
    	//删除
    	AppMgr.del = function(id,valid,e){
    		parent.layer.confirm('确定要删除该信息？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/msgpublish/del";
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
		AppMgr.add = function(e){
    		layer_show("添加", getProjectName() +"/pages/qm/msgpublish/send.jsp","1000","520");
		};
    	this.initDatas = function(){
    	
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/msgpublish/list",
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
    		        title: 'ID',
    		        width : 60
    		    }, {
    		        field: 'sendTimeStr',
    		        title: '发送时间',
    		        width : 180
    		    }, {
    		    	field:'reciveUserTel',
    		    	title:'接收用户电话',
    		    	width : 280,
    		    	formatter:function(value, row, index){
    		    		if(value.length > 35){
    		        		return  "<span alt='"+value+"' title='"+value+"'>"+ value.substring(0, 35) + "...</span>";
    		        	}else{
    		        		return value;
    		        	}
    		    	}
    		    }, {
    		    	field:'msgContent',
    		    	title:'短信内容',
    		    	width : 280,
    		    	formatter:function(value, row, index){
    		    		if(value.length > 35){
    		        		return  "<span alt='"+value+"' title='"+value+"'>"+ value.substring(0, 35) + "...</span>";
    		        	}else{
    		        		return value;
    		        	}
    		    	}
    		    }, {
    		    	field:'status',
    		    	title:'状态',
    		        width : 80,
    		    	formatter:function(value, row, index){
    		    		if(value == 1){
    		        		return "已发送";
    		        	}else{
    		        		return "未发送";
    		        	}
    		    	}
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
   		        		var freeze="";
   		        		if(row.status != 1){
	   						freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.del('" + row.id + "','1',event)\" style='cursor: pointer' >删除</a></span>";
   		        		}
    		        	return freeze ;
    		        },
    		        width: 80
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
