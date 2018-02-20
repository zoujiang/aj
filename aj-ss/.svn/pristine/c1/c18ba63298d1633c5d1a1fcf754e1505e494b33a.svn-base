<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-服务器列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#stat_table");
    	AppMgr.addServer = function(id, e){
			var index = layer.open({
    			type: 2,
    			title: "添加主机",
    			content: getProjectName() +"/pages/ctrl/serverAdd.jsp?appId="+ id
    		});
    		layer.full(index);
		};
    	AppMgr.serverList = function(id, srvType, e){
			var index = layer.open({
    			type: 2,
    			title: "主机列表",
    			content: getProjectName() +"/pages/ctrl/serverList.jsp?appId="+ id+"&srvType="+srvType
    		});
    		layer.full(index);
		};
			
    	this.initDatas = function(){
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/apply/statlist",
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
                		appName : $('#appName').val(),
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
                idField:'appId',
                //http://bootstrap-table.wenzhixin.net.cn/documentation/
    		    columns: [{
    		        checkbox:true
    		    }, {
    		        field: 'appName',
    		        title: '景区名称'/*,
    		        visible:false*/
    		    }, {
    		        field: 'appCnt',
    		        title: '主机数量',
    		        formatter : function(value, row, index){
    		        	if(value > 0){
    		        		return "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.serverList('" + row.appId + "','1',event)\" style='cursor: pointer' >"+value+"&nbsp;</a></span>";
    		        	}else{
    		        		return value;
    		        	}
    		        }
    		    }, {
    		        field: 'dataCnt',
    		        title: '数据库数量',
    		        formatter : function(value, row, index){
    		        	if(value > 0){
    		        		return "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.serverList('" + row.appId + "','2',event)\" style='cursor: pointer' >"+value+"&nbsp;</a></span>";
    		        	}else{
    		        		return value;
    		        	}
    		        }
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
				var records = $('#stat_table').bootstrapTable('getSelections');
				AppMgr.addServer(records[0].appId,e);
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
                    <button id="btn_new" type="button" class="btn btn-outline btn-default" title="添加">
                        <i class="glyphicon glyphicon-plus" aria-hidden="true" ></i>
                    </button>
                    <div class="input-group" style="width: 280px;padding-left: 10px;">
                        <input type="text" placeholder="景区名称" class="form-control input-outline" id="appName" name="appName" width="280px"> <span class="input-group-btn">
                            <button  id="btn_search" type="button" class="btn btn-outline btn-default"> 搜索</button> </span>
                    </div>
                    
                </div>
                <table id="stat_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
