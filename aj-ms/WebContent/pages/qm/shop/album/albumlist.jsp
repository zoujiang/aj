<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String shopId = request.getParameter("shopId");
if(shopId == null){
	shopId = "";
}
String userId = request.getParameter("userId");
if(userId == null){
	userId = "";
}
%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<style type="text/css">
		.table{
		    table-layout: fixed;
		}
	</style>

    <title>商户管理平台-相册列表</title>
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
		//新增
		AppMgr.add = function(id,valid,e){
			layer_show("照片管理", getProjectName() +"/pages/qm/shop/album/addAlbum.jsp","1000","520");
		}
		//编辑
		AppMgr.edit = function(id,valid,e){
			layer_show("照片管理", getProjectName() +"/pages/qm/shop/album/editAlbum.jsp?id="+id,"1000","520");
		}
		//片源地址查看
		AppMgr.view = function(id,shortAddress, sec, valid,e){
			if(shortAddress != 'undefined' && shortAddress != ""){
				var ad = '<%=basePath%>d.html?'+shortAddress;
				layer_show("片源地址查看", getProjectName() +"/pages/qm/shop/album/viewAlbumDownload.jsp?ad="+ad+"&sec="+sec,"558","250");
			}else{
				layer.msg("付费相册在用户购买之后才会生成片源地址", {title:'提示', btn: ['确定'],icon: 6}, function(index){
        			
   		  		}); 
			}
		}
		//删除
		AppMgr.del = function(id,valid,e){
			
			//询问框
			parent.layer.confirm('确定要删除该相册？删除之后将不能恢复！', {
			    btn: ['删除','取消'], //按钮
			    shade: false //不显示遮罩
			}, function(index){
				parent.layer.close(index);
				 $.ajax({
		             type: "GET",
		             url: getProjectName() + "/admin/shop/album/del?id="+id,
		             dataType: "json",
		             success: function(data){
		            	if(data.success){
		            		layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
		    					$table.bootstrapTable('refresh');
			   		  		});
		            	}else{
		            		layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
		            			
			   		  		}); 
			         	}
		         	 }
		    	});
			}, function(){
			   
			});
		}
    	this.initDatas = function(){
    		var param = "";
    		var sid = '<%=shopId%>';
    		if(sid != null && sid != ""){
    			if(param == ""){
	    			param += "?shopId="+sid;
    			}else{
	    			param += "&shopId="+sid;
    			}
    		}
    		var uid = '<%=userId%>';
    		if(uid != null && uid != ""){
    			if(param == ""){
    				param += "?userId="+uid;
    			}else{
	    			param += "&userId="+uid;
    			}
    		}
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/shop/album/list"+param,
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
    		    }, */{
    		        field: 'id',
    		        title: 'ID',
    		       // visible:false,
    		        width:175
    		    }, {
    		    	field:'userTel',
    		    	title:'客户手机号码',
    		        width:100
    		    }, {
    		    	field:'albumName',
    		    	title:'相册名称',
    		    	width: 100
    		    }, {
    		    	field:'photoCount',
    		    	title:'照片张数',
    		    	width:80
    		    }, {
    		    	field:'isPay',
    		    	title:'赠送/付费',
    		    	formatter : function(value, row, index) {
                        if(value == 0){
                        	return "赠送";
                        }else{
                        	return "付费";
                        }
                    },
    		    	width:80
    		    }, {
    		    	field:'originalPrice',
    		    	title:'原价',
    		    	formatter : function(value, row, index) {
                        if(row.isPay == 0){
                        	return "0";
                        }else{
                        	return value;
                        }
                    },
                    width:100
    		    }, {
    		    	field:'payType',
    		    	title:'付费方式',
    		    	formatter : function(value, row, index) {
                        if(row.isPay == 0){
                        	return "";
                        }else{
                        	return value;
                        }
                    },
                    width:155
    		    },  {
    		    	field:'photoTime',
    		    	title:'摄影时间',
                    width:150
    		    },  {
    		    	field:'createTime',
    		    	title:'开通时间',
                    width:150
    		    }, {
    		    	field:'payTime',
    		    	title:'购买时间',
                    width:150
    		    }, {
    		    	field:'buyType',
    		    	title:'支付方式',
    		    	formatter : function(value, row, index) {
                        if(value == 0){
                        	return "支付宝";
                        }else if(value == 1){
                        	return "微信";
                        }
                    },
                    width:100
    		    }, {
    		    	field:'transactionId',
    		    	title:'支付三方流水号',
    		    	width:250
    		    }, {
    		    	field:'totalFee',
    		    	title:'支付金额',
    		    	formatter : function(value, row, index) {
                        return value * 0.01;
                    },
    		    	width:100
    		    }, {
    		    	field:'payStatus',
    		    	title:'支付状态',
    		    	formatter : function(value, row, index) {
                        if(value == 1){
                        	return "成功";
                        }
                    },
    		    	width:80
    		    },{
    		    	field:'viewCount',
    		    	title:'浏览次数',
    		    	width:80
    		    }, {
    		    	field: 'action',
    		    	title : '操&nbsp;&nbsp;&nbsp;作',
    		        formatter : function(value, row, index){
   		        		var freeze="";
   		        		var del="";
   		        		var setRole="";
   		        		if(!row.hideOper){
   		        			
	   						freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.edit('" + row.id + "',event)\" style='cursor: pointer' >照片管理&nbsp;&nbsp; </a></span>";
	   						del = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.del('" + row.id + "',event)\" style='cursor: pointer' >删除&nbsp;&nbsp;&nbsp;</a></span>";
   		        		}
   						setRole = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.view('" + row.id + "','"+row.shortAddress+"', '"+ row.downloadSecret +"', event)\" style='cursor: pointer' >片源地址查看</a></span>";
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

                    	<%--商户名称：<input type="text" placeholder="商户名称" class="form-control input-inline"  id="shopName" name="shopName" width="280px">--%> 
                    	客户名称：<input type="text" placeholder="客户名称" class="form-control input-inline"  id="userName" name=""userName"" width="280px"> 
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
