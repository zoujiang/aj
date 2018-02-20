<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>管理平台-优惠券兑换列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#user_table");
    	
		
		AppMgr.exp = function(id,valid,e){
			
			 var shopName = {
					 shopName:$("#name").val()
			        };
			 $.ajax({
	             type: "POST",
	             url: getProjectName()+"/admin/coupon/exchange/export",
	             data:shopName,
	             dataType: "json",
	             success: function(data){
	            	if(data.success){
	            		window.location.href = getProjectName()+ data.url;
	            	}else{
	            		layer.msg("导出失败", {title:'提示', btn: ['确定'],icon: 6}, function(index){
    						parent.layer.close(index);
    		  			});
	            	}
	         	 }
	    	});
		}
		
    	this.initDatas = function(){
    	
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/coupon/exchange/list",
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
                		shopName : $('#shopName').val(),
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
    		    },*/ {
    		        field: 'couponId',
    		        title: '兑换券ID',
    		        width:105/*,
    		        visible:false*/
    		    },{
    		        field: 'couponName',
    		        title: '兑换券名称',
    		        width:300/*,
    		        visible:false*/
    		    }, {
    		    	field:'shopName',
    		    	title:'商户名称',
    		        width:125
    		    }, {
    		    	field:'userNick',
    		    	title:'兑换昵称',
    		        width:125
    		    }, {
    		    	field:'userTel',
    		    	title:'兑换人电话号码',
        		    width:125
    		    }, {
    		    	field:'exchangeTime',
    		    	title:'兑换时间'
    		    }]
    		});
    		
       }
    	
    	this.initActions = function(){
				// 搜索
			$('#btn_search').click(function() {
				 $table.bootstrapTable('refresh');
			});
			//导出数据
			$('#btn_exp').click(function(e){
				AppMgr.exp(e);
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
                    <div class="form-inline" style="width: 480px;float: left;">
                    	 商户名称：<input type="text" placeholder="商户名称" class="form-control input-inline"  id="shopName" name="shopName" width="280px">
                           <button  id="btn_search" type="button" class="btn btn-inline btn-default" style="margin-top: 4px;"> 搜索</button> 
	                     <button id="btn_exp" type="button" style="margin-left: 10px;margin-top: 4px;" class="btn btn-inline btn-default" title="导出">
		                        <i class="glyphicon glyphicon-export" aria-hidden="true" ></i>
		                    </button>
                    </div>
                </div>
                <table id="user_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
