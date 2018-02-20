<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-用户列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#user_table");
    	//启用/冻结
		AppMgr.freeze = function(id,valid,e){
			parent.layer.confirm('确定要'+valid==1?'启用':'冻结'+'用户？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/user/modifyState";
    			var obj = ajaxSubmit(url,{uid:id, state:valid});
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
		//删除
		AppMgr.del = function(id,valid,e){
			parent.layer.confirm('确定要删除用户？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/user/modifyState";
    			var obj = ajaxSubmit(url,{uid:id, state:valid});
    			if(obj.success){
    				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
    					$table.bootstrapTable('refresh');
	   		  		});
    			}
    			else{
    				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
    					parent.layer.close(index);
    		  		});
    			}
    		}, function(index){
    			parent.layer.close(index);
    		});
		}
		AppMgr.setRole = function(id, e){
    		layer_show("角色配置", getProjectName() +"/pages/system/user/role.jsp?uid=" + id,"600","400");
		};
		AppMgr.add = function(e){
    		layer_show("添加用户", getProjectName() +"/pages/system/user/addUser.jsp","800","400");
		};
    	this.initDatas = function(){
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/user/list",
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
                		userName : $('#userName').val(),
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
    		        field: 'name',
    		        title: '姓名'/*,
    		        visible:false*/
    		    }, {
    		    	field:'account',
    		    	title:'帐号'
    		    }, {
    		    	field:'mobile',
    		    	title:'手机'
    		    }, {
    		    	field:'tele',
    		    	title:'固话'
    		    }, {
    		    	field:'email',
    		    	title:'邮箱'
    		    }, {
    		    	field:'isEnabled',
    		    	title:'状态',
    		    	formatter:function(value, row, index){
    		    		if(value == 0){
    		        		return "删除";
    		        	}else if(value == 2){
    		        		return "冻结";
    		        	} else{
    		        		return "启用"
    		        	}
    		    	}
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
   		        		var freeze="";
   		        		var del="";
   		        		var setRole="";
   		        		if(row.isEnabled != "0"){
	   		        		if(row.isEnabled == "2"){
	   							freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.id + "','1',event)\" style='cursor: pointer' >启用&nbsp;</a></span>";
	   		        		} else {
	   							freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.id + "','2',event)\" style='cursor: pointer' >冻结&nbsp;</a></span>";
	   		        		}
	   		        		del = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.del('" + row.id + "','0',event)\" style='cursor: pointer' >删除&nbsp;</a></span>";
	   		        		setRole = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.setRole('" + row.id + "',event)\" style='cursor: pointer' >配置角色&nbsp;</a></span>";
   		        		}
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
                    <button id="btn_new" type="button" class="btn btn-outline btn-default" title="添加">
                        <i class="glyphicon glyphicon-plus" aria-hidden="true" ></i>
                    </button>
                    <div class="input-group" style="width: 280px;padding-left: 10px;">
                        <input type="text" placeholder="用户名" class="form-control input-outline" id="userName" name="userName" width="280px"> 
                        <span class="input-group-btn">
                            <button  id="btn_search" type="button" class="btn btn-outline btn-default"> 搜索</button> 
                        </span>
                    </div>
                    
                </div>
                <table id="user_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
