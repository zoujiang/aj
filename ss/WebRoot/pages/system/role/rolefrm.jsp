<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-角色列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#role_table");
    	//启用/冻结
		AppMgr.freeze = function(id,valid,e){
			parent.layer.confirm('确定要'+valid==1?'启用':'禁用'+'角色？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/role/modifyState";
    			var obj = ajaxSubmit(url,{rid:id, state:valid});
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
		AppMgr.setRes = function(id, e){
    		layer_show("资源配置", getProjectName() +"/pages/system/role/resource.jsp?rid=" + id,"300","400");
		};
		AppMgr.add = function(e){
    		layer_show("添加角色", getProjectName() +"/pages/system/role/addRole.jsp","400","310");
		};
    	this.initDatas = function(){
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/role/list",
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
                		roleName : $('#roleName').val(),
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
    		    columns: [{
    		        checkbox:true
    		    }, {
    		        field: 'title',
    		        title: '角色名称'/*,
    		        visible:false*/
    		    }, {
    		    	field:'code',
    		    	title:'角色编码'
    		    }, {
    		    	field:'state',
    		    	title:'状态',
    		    	formatter:function(value, row, index){
    		    		if(value == 0){
    		        		return "禁用";
    		        	}else {
    		        		return "启用";
    		        	}
    		    	}
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
   		        		var freeze="";
   		        		var setRes="";
   		        		if(row.state == "0"){
   							freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.id + "','1',event)\" style='cursor: pointer' >启用&nbsp;</a></span>";
   		        		} else {
   							freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.id + "','0',event)\" style='cursor: pointer' >禁用&nbsp;</a></span>";
   		        		}
   		        		setRes = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.setRes('" + row.id + "','',event)\" style='cursor: pointer' >配置资源&nbsp;</a></span>";
    		        	return freeze + setRes;
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
                        <input type="text" placeholder="角色名称" class="form-control input-outline" id="roleName" name="roleName" width="280px"> <span class="input-group-btn">
                            <button  id="btn_search" type="button" class="btn btn-outline btn-default"> 搜索</button> </span>
                    </div>
                    
                </div>
                <table id="role_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
