<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>管理平台-选择用户</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#user_table");
    	
		
    	this.initDatas = function(){
    	
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/msgpublish/queryUser",
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
                pageList: [10, 25, 50, 100],
                queryParams: function(params) {
                	return{
                		type:$('input:radio:checked').val(),
	                	limit:params.limit,
	                	offset:params.offset
	                };
                },
                search:false,//是否显示搜索
                //searchText:'名称',
                strictSearch: true,
                searchOnEnterKey:false,//点击回车查询
                icons: {
                    refresh: "glyphicon-repeat",
                    columns: "glyphicon-list"
                },
                clickToSelect:true,//点击选中
                singleSelect:false,//是否单选
                idField:'tel',
                //http://bootstrap-table.wenzhixin.net.cn/documentation/
    		    columns: [{
    		        checkbox:true
    		    }, {
    		        field: 'nick',
    		        title: '昵称',
    		        width:175/*,
    		        visible:false*/
    		    },{
    		        field: 'tel',
    		        title: '电话号码',
    		        width:300/*,
    		        visible:false*/
    		    }]
    		});
    		
       }
    	
    	this.initActions = function(){
    		$(':radio').click(function() {
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
    
	var formData = function(){
		
		var row=$.map($("#user_table").bootstrapTable('getSelections'),function(row){return row ;});
		var tels="";
		for(var i=0;i<row.length;i++){
			if(i==0 || i=="0"){
				tels+=row[i].tel;
			}else{
				tels+=","+row[i].tel;
			}
		}
		return tels;
	}
    </script>
</head>

<body class="gray-bg" style="height: 100%;">
    <div class="wrapper wrapper-content animated fadeInRight" style="height: auto;">
        <div class="ibox float-e-margins"  style="height: 100%;">
            <div class="ibox-content"  style="height: 100%;">
            	<div class="btn-group hidden-xs" id="toolbar" role="group" style="margin-top: 10px;">
                    <div class="form-inline" style="width: 480px;float: left;">
                    	<input type="radio" id="btn_search" name="type" value="1" checked="checked"> 用户  &nbsp;&nbsp;&nbsp;
                    	<input type="radio" id="btn_search" name="type" value="2"> 老师  &nbsp;&nbsp;&nbsp;
                    	<input type="radio" id="btn_search" name="type" value="3"> 家长
                    </div>
                </div>
                <table id="user_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
