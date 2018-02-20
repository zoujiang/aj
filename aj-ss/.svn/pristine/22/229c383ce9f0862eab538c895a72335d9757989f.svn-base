<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台- 实例</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#demo_table");
			//添加
			AppMgr.add = function(e){
				var index = layer.open({
	    			type: 2,
	    			title: "新增区域",
	    			content: getProjectName() +"/pages/demo/demoAdd.jsp"
	    		});
	    		layer.full(index);
				//layer_show("新增区域", getProjectName() +"/pages/demo/demoAdd.jsp","800","600");
			};
			//修改
			AppMgr.modify = function(id,e){
				var index = layer.open({
	    			type: 2,
	    			title: "修改区域",
	    			content: getProjectName() +"/pages/demo/demoEdit.jsp?id="+ id
	    		});
	    		layer.full(index);
				//layer_show("修改区域", getProjectName() +"/pages/demo/demoEdit.jsp?id=" + id,"800","600");
			};
			//删除
			AppMgr.del = function(id,e){
				parent.layer.confirm('确定要删除数据？', {
	    		    btn: ['确定','取消'], //按钮
	    		    shade: false //不显示遮罩
	    		}, function(index){
	    			parent.layer.close(index);
	    			var url =  getProjectName() + "/admin/area/deleteArea";
	    			var obj = ajaxSubmit(url,{id:id});
	    			if(obj.result){
	    				layer.msg(obj.message, {time:200,title:'提示', btn: ['确定'],icon: 6}, function(index){
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
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/area/queryAreaList",
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
                		searchText : $('#searchText').val(),
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
                idField:'areaId',
                //http://bootstrap-table.wenzhixin.net.cn/documentation/
    		    columns: [{
    		        field: 'areaName',
    		        title: '名称'
    		    }, {
    		        field: 'areaOrder',
    		        title: '排序',
    		        visible:false
    		    }, {
    		        field: 'createUser',
    		        title: '创建人'
    		    }, {
    		        field: 'createDt',
    		        title: '创建时间'
    		    }, {
    		        field: 'isValid',
    		        title: '是否有效',
    		        formatter:function(value, row, index){
    		        	if(value == 1){
    		        		return "是";
    		        	}else{
    		        		return "否";
    		        	}
    		        }
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
    		        	//if(value == "1"){
    		        		var mod = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.modify('" + row.areaId + "',event)\" style='cursor: pointer' >编辑&nbsp;</a></span>";
							var del = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.del('" + row.areaId + "',event)\" style='cursor: pointer' >删除&nbsp;</a></span>";

    		        	//}else{
    		        	//}
    		        	//act += '&nbsp;<a href="#"  onclick="javascript:showWin(\'edit\');" title="详情">详情</a>';
    		        	return mod + del;
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
    	<input type="hidden" id="id" value="">
        <div class="ibox float-e-margins"  style="height: 100%;">
            <div class="ibox-content"  style="height: 100%;">
            	<div class="btn-group hidden-xs" id="toolbar" role="group" style="margin-top: 10px;">
                    <button id="btn_new" type="button" class="btn btn-outline btn-default" title="添加">
                        <i class="glyphicon glyphicon-plus" aria-hidden="true" ></i>
                    </button>
                   <!--  <button  id="btn_exp" type="button" class="btn btn-outline btn-default"   title="导出">
                        <i class="glyphicon glyphicon-export" aria-hidden="true"></i>
                    </button> -->
                    <div class="input-group" style="width: 280px;padding-left: 10px;">
                        <input type="text" placeholder="区域名称" class="form-control input-outline" id="searchText" name="searchText" width="280px"> <span class="input-group-btn">
                            <button  id="btn_search" type="button" class="btn btn-outline btn-default"> 搜索</button> </span>
                    </div>
                    
                </div>
                <table id="demo_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
