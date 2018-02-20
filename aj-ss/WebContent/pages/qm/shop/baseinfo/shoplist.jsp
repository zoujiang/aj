<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-商户列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#user_table");
    	//启用/冻结
		AppMgr.freeze = function(id,valid,e){
			parent.layer.confirm('确定要'+valid==0?'启用':'冻结'+'商户？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/shop/baseinfo/modifyState";
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
			layer_show("编辑商户", getProjectName() +"/pages/qm/shop/baseinfo/editshop.jsp?id="+id,"800","520");
		}
		AppMgr.exp = function(id,valid,e){
			
			 var shopName = {
					 shopName:$("#shopname").val()
			        };
			 $.ajax({
	             type: "POST",
	             url: getProjectName()+"/admin/shop/baseinfo/export",
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
		AppMgr.add = function(e){
    		layer_show("新建商户", getProjectName() +"/pages/qm/shop/baseinfo/addshop.jsp","800","520");
    		
    	//iframe层
		/* parent.layer.open({
		    type: 2,
		    title: '新建商户',
		    shadeClose: true,
		    shade: 0.8,
		    area: ['800px', '90%'],
		    content:  getProjectName() +"/pages/qm/shop/baseinfo/addshop.jsp" //iframe的url
		}); */


		};
    	this.initDatas = function(){
    	
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/shop/baseinfo/list",
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
                		shopName : $('#shopname').val(),
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
    		        field: 'id',
    		        title: '商户ID',
    		        width:175/*,
    		        visible:false*/
    		    },{
    		        field: 'shopName',
    		        title: '商户名称',
    		        width:300/*,
    		        visible:false*/
    		    }, {
    		    	field:'shopAccountName',
    		    	title:'商户帐号',
    		        width:125
    		    }, {
    		    	field:'registName',
    		    	title:'注册人姓名',
    		        width:125
    		    }, {
    		    	field:'registTel',
    		    	title:'联系电话'
    		    }, {
    		    	field:'idCard',
    		    	title:'注册人身份证'
    		    }, {
    		    	field:'bankName',
    		    	title:'商户银行'
    		    }, {
    		    	field:'bankCardName',
    		    	title:'结算账号名称'
    		    }, {
    		    	field:'bankCardNo',
    		    	title:'分成账号'
    		    }, {
    		    	field:'percentage',
    		    	title:'分成比例'
    		    }, {
    		    	field:'zoneSize',
    		    	title:'存储总量（G）',
    		    	width : 80
    		    }, {
    		    	field:'usedSize',
    		    	title:'已用存储(G)',
    		    	width : 80,
    		    	formatter:function(value, row, index){
    		    		return Math.round(value*1000)/1000;
    		    	}
    		    }, {
    		    	field:'registName',
    		    	title:'使用百分比',
    		    	formatter:function(value, row, index){
    		    		return Math.round((  Math.round(row.usedSize*1000)/1000  / row.zoneSize) * 100 *1000)/1000  +"%";
    		    	}
    		    }, {
    		    	field:'photoCount',
    		    	title:'照片张数'
    		    },{
    		    	field:'createTime',
    		    	title:'注册时间',
    		    	width : 200
    		    },{
    		    	field:'serviceBeginTime',
    		    	title:'服务开始时间'
    		    },{
    		    	field:'serviceEndTime',
    		    	title:'服务结束时间'
    		    },{
    		    	field:'status',
    		    	title:'商户状态',
    		    	formatter:function(value, row, index){
    		    		if(value == 0){
    		        		return "正常";
    		        	}else if(value == 1){
    		        		return "冻结";
    		        	} else{
    		        		return "删除"
    		        	}
    		    	}
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
   		        		var freeze="";
   		        		var del="";
   		        		if(row.status == "0" || row.status == "1"){
	   		        		if(row.status == "0"){
	   							freeze = "<span  height = '23px'  width = '30px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.id + "','1',event)\" style='cursor: pointer' >冻结 </a></span>";
	   		        		} else {
	   							freeze = "<span  height = '23px'  width = '30px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.id + "','0',event)\" style='cursor: pointer' >启用 </a></span>";
	   		        		}
	   		        		del = "<span  height = '23px'  width = '30px'><a style='color:blue;' href='#' onclick=\"AppMgr.edit('" + row.id + "','0',event)\" style='cursor: pointer' > 编辑</a></span>";
   		        		}
    		        	return freeze +del;
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
			//新增订单
			$('#btn_new').click(function(e){
				AppMgr.add(e);
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
                    	 商户名称：<input type="text" placeholder="商户名称" class="form-control input-inline"  id="shopname" name="shopname" width="280px"> 
                           <button  id="btn_search" type="button" class="btn btn-inline btn-default" style="margin-top: 4px;"> 搜索</button> 
	                    <button id="btn_new" type="button" style="margin-left: 10px;margin-top: 4px;" class="btn btn-inline btn-default"  title="添加">
	                        <i class="glyphicon glyphicon-plus" aria-hidden="true" ></i>
	                    </button>
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
