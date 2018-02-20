<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-景区接入申请列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#apv_table");
			//添加
			AppMgr.add = function(e){
				var index = layer.open({
	    			type: 2,
	    			title: "新增申请",
	    			content: getProjectName() +"/pages/ctrl/apply.jsp"
	    		});
	    		layer.full(index);
				//layer_show("新增区域", getProjectName() +"/pages/demo/demoAdd.jsp","800","600");
			};
			//审批
			AppMgr.approve = function(id, e){
				layer_show("接入审批", getProjectName() +"/pages/ctrl/approve.jsp?appId=" + id,"600","375");
			}
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
			
			AppMgr.addServer = function(id, e){
				var index = layer.open({
	    			type: 2,
	    			title: "添加主机",
	    			content: getProjectName() +"/pages/ctrl/serverAdd.jsp?appId="+ id
	    		});
	    		layer.full(index);
			}
			//启用/冻结
			AppMgr.freeze = function(id,valid,e){
				parent.layer.confirm('确定要'+valid==1?'启用':'禁用'+'服务？', {
	    		    btn: ['确定','取消'], //按钮
	    		    shade: false //不显示遮罩
	    		}, function(index){
	    			parent.layer.close(index);
	    			var url =  getProjectName() + "/admin/apply/isValid";
	    			var obj = ajaxSubmit(url,{appId:id, isValid:valid, opt:"valid"});
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
			AppMgr.serverList = function(id, srvType, e){
				var index = layer.open({
	    			type: 2,
	    			title: "主机列表",
	    			content: getProjectName() +"/pages/ctrl/serverList.jsp?appId="+ id+"&srvType="+srvType
	    		});
	    		layer.full(index);
			}
			//删除
			AppMgr.del = function(id,e){
				parent.layer.confirm('确定要删除数据？', {
	    		    btn: ['确定','取消'], //按钮
	    		    shade: false //不显示遮罩
	    		}, function(index){
	    			parent.layer.close(index);
	    			var url =  getProjectName() + "/admin/appInfo/deleteArea";
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
    			url: getProjectName() + "/admin/apply/list",
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
    		        field: 'appKey',
    		        title: '景区密钥'
    		    }, {
    		        field: 'appName',
    		        title: '景区名称'/*,
    		        visible:false*/
    		    }, {
    		        field: 'appMsUrl',
    		        title: '景区后台地址'
    		    }, {
    		        field: 'appBiUrl',
    		        title: '景区管控平台地址'
    		    }, {
    		        field: 'appCltUrl',
    		        title: '景区客户端接入地址'
    		    },{
    		    	field:"srvStartDt",
    		    	title:"开通时间"
    		    },{
    		    	field:"srvEndDt",
    		    	title:"服务到期时间"
    		    },{
    		    	field:"isValid",
    		    	title:"状态",
    		        formatter:function(value, row, index){
    		        	if(value == 0){
    		        		return "未启用";
    		        	}else if(value == 1){
    		        		return "正在服务";
    		        	} else if(value == 2){
    		        		return "终止服务";
    		        	}
    		        }
    		    },{
    		    	field:"apvSts",
    		    	title:"审批状态",
    		        formatter:function(value, row, index){
    		        	if(value == 0){
    		        		return "待审批";
    		        	}else if(value == 1){
    		        		return "审批通过";
    		        	} else if(value == 2){
    		        		return "审批未通过";
    		        	}
    		        }
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
    		        	console.log(row);
    		        	//if(value == "1"){
    		        		var approve="";
    		        		var freeze="";
    		        		var addServer="";
    		        		if(row.apvSts == "0"){
    		        			approve = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.approve('" + row.appId + "',event)\" style='cursor: pointer' >审批&nbsp;</a></span>";
    		        		}
    		        		if(row.isValid == "1"){
    							freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.appId + "','2',event)\" style='cursor: pointer' >终止服务&nbsp;</a></span>";
    		        		} else {
    							freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.appId + "','1',event)\" style='cursor: pointer' >启用服务&nbsp;</a></span>";
    		        		}
    		        		if(row.apvSts == "1"){
    		        			addServer = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.addServer('" + row.appId + "',event)\" style='cursor: pointer' >添加服务器&nbsp;</a></span>";
    		        		}
							var hostmng = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.serverList('" + row.appId + "','',event)\" style='cursor: pointer' >服务器管理&nbsp;</a></span>";

    		        	//}else{
    		        	//}
    		        	//act += '&nbsp;<a href="#"  onclick="javascript:showWin(\'edit\');" title="详情">详情</a>';
    		        	return approve + freeze +addServer+ hostmng;
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
                    <button id="btn_new" type="button" class="btn btn-outline btn-default" title="添加">
                        <i class="glyphicon glyphicon-plus" aria-hidden="true" ></i>
                    </button>
                    <div class="input-group" style="width: 280px;padding-left: 10px;">
                        <input type="text" placeholder="景区名称" class="form-control input-outline" id="appName" name="appName" width="280px"> <span class="input-group-btn">
                            <button  id="btn_search" type="button" class="btn btn-outline btn-default"> 搜索</button> </span>
                    </div>
                    
                </div>
                <table id="apv_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
