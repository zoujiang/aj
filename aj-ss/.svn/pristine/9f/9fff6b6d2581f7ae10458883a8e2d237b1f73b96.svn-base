<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-商户客户列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#user_table");
    	//启用/冻结
		AppMgr.freeze = function(id,valid,e){
    		var m = valid == 0?'启用':'冻结';
			parent.layer.confirm('确定要'+ m +'该账户？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/shop/account/modifyState";
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
		//普通相册管理
		AppMgr.dynamicAlbum = function(id,shopId,valid,e){
			window.location.href = getProjectName() +"/pages/qm/shop/dynamicalbum/dynamicAlbumList.jsp?shopId="+shopId+"&userId="+id;
		}
		//动态相册管理
		AppMgr.album = function(id,shopId,valid,e){
			window.location.href = getProjectName() +"/pages/qm/shop/album/albumlist.jsp?shopId="+shopId+"&userId="+id;
		}
		AppMgr.add = function(e){
    		layer_show("添加客户帐号", getProjectName() +"/pages/qm/shop/customer/addcustomer.jsp","600","350");
		};
    	this.initDatas = function(){
    	
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/shop/customer/list",
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
                		userName : $('#username').val(),
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
    		    }, {
    		        field: 'shopAccountName',
    		        title: '登录帐号'/*,
    		        visible:false*/
    		    }, {
    		    	field:'shopName',
    		    	title:'商户名'
    		    }, {
    		    	field:'userTel',
    		    	title:'客户联系电话'
    		    }, {
    		    	field:'shopCustomerName',
    		    	title:'客户姓名'
    		    }, {
    		    	field:'dynamicAlbumInfo',
    		    	title:'动感影集',
    		    	 formatter : function(value, row, index){
    		    	 	var albumNames ="";
    		    	 	for(var i = 0; i < value.length; i++){
    		    	 		albumNames +="<"+ value[i].albumName +">";
    		    	 	}
    		        	return albumNames;
    		        }, 
    		        width: 200
    		    }, {
    		    	field:'albumInfo',
    		    	title:'相册',
    		    	 formatter : function(value, row, index){
    		    	 	var albumNames ="";
    		    	 	for(var i = 0; i < value.length; i++){
    		    	 		albumNames +="<"+ value[i].albumName +">";
    		    	 	}
    		        	return albumNames;
    		        }, 
    		        width: 200
    		    }, {
    		    	field:'totalZoneSize',
    		    	title:'总占用空间(单位M)',
    		    	formatter:function(value, row, index){
    		    		return Math.round(value*1000)/1000;
    		    	}
    		    },{
    		    	field:'payAlbumZoneSize',
    		    	title:'计费占用空间(单位M)',
    		    	formatter:function(value, row, index){
    		    		return Math.round(value*1000)/1000;
    		    	}
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
   		        		var freeze="";
   		        		var del="";
   		        		var setRole="";
   						freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.dynamicAlbum('" + row.id + "','"+row.shopId+"','0',event)\" style='cursor: pointer' >动感影集管理</a></span>";
   		        		del = "<br/><span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.album('" + row.id + "','"+row.shopId+"','0',event)\" style='cursor: pointer' >相册管理</a></span>";
    		        	return freeze +del+ setRole;
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
                    	商户名称：<input type="text" placeholder="商户名称" class="form-control input-inline"  id="shopName" name="shopName" width="280px"> 
                    	客户名称：<input type="text" placeholder="客户名称" class="form-control input-inline"  id="username" name="username" width="280px"> 
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
