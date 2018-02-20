<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-主机列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#srv_table");
			AppMgr.addServer = function(id, e){
				var index = layer.open({
	    			type: 2,
	    			title: "添加主机",
	    			content: getProjectName() +"/pages/ctrl/serverAdd.jsp?appId="+ id
	    		});
	    		layer.full(index);
			}
			//启用/冻结
			AppMgr.freeze = function(id, valid, e){
				layer.confirm('确定要'+(valid=="1"?'启用':'禁用')+'服务？', {
	    		    btn: ['确定','取消'], //按钮
	    		    shade: false //不显示遮罩
	    		}, function(index){
	    			layer.close(index);
	    			var url =  getProjectName() + "/admin/srv/isValid";
	    			var obj = ajaxSubmit(url,{srvId:id, isValid:valid, opt:"valid"});
	    			console.log(obj);
	    			if(obj.success){
	    				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
	    					$table.bootstrapTable('refresh');
	    					layer.close(index);
		   		  		});
	    			}
	    			else{
	    				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
	    					layer.close(index);
	    		  		});
	    			}
	    		}, function(index){
	    			parent.layer.close(index);
	    		});
			}
    	this.initDatas = function(){
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/srv/list",
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
                		appId:"<%= request.getParameter("appId") %>",
                		srvType:"<%= request.getParameter("srvType") %>",
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
                idField:'srvId',
                //http://bootstrap-table.wenzhixin.net.cn/documentation/
    		    columns: [{
    		        field: 'srvName',
    		        title: '服务名称'
    		    }, {
    		        field: 'srvPlace',
    		        title: '所在地'/*,
    		        visible:false*/
    		    }, {
    		        field: 'srvIp',
    		        title: 'IP地址'
    		    }, {
    		        field: 'srvCfg',
    		        title: '配置'
    		    }, {
    		        field: 'srvType',
    		        title: '服务器类型'
    		    },{
    		    	field:"startDt",
    		    	title:"服务开始时间"
    		    },{
    		    	field:"endDt",
    		    	title:"服务到期时间"
    		    },{
    		    	field:"isValid",
    		    	title:"状态",
    		        formatter:function(value, row, index){
    		        	if(value == 0){
    		        		return "未启用";
    		        	}else if(value == 1){
    		        		return "正常";
    		        	} else if(value == 2){
    		        		return "冻结";
    		        	}
    		        }
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
    		        	console.log(row);
   		        		var freeze="";
   		        		if(row.isValid == "1"){
   							freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.srvId + "', '2',event)\" style='cursor: pointer' >冻结&nbsp;</a></span>";
   		        		} else {
   							freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.srvId + "','1',event)\" style='cursor: pointer' >启用&nbsp;</a></span>";
   		        		}

    		        	return freeze;
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
				AppMgr.addServer("<%= request.getParameter("appId") %>",e);
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
                    <!-- 
                    <div class="input-group" style="width: 280px;padding-left: 10px;">
                        <input type="text" placeholder="景区名称" class="form-control input-outline" id="appName" name="appName" width="280px"> <span class="input-group-btn">
                            <button  id="btn_search" type="button" class="btn btn-outline btn-default"> 搜索</button> </span>
                    </div>
                     -->
                </div>
                <table id="srv_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
