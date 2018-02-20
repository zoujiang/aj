<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-商户意见反馈列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#user_table");
    	//启用/冻结
		AppMgr.freeze = function(id,e){
    		
			parent.layer.confirm('确定要取消该反馈？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/shop/feedback/cancel";
    			var obj = ajaxSubmit(url,{id:id});
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
		//新增反馈
		AppMgr.add = function(id,valid,e){
			layer_show("意见反馈", getProjectName() +"/pages/qm/shop/feedback/add.jsp","600","362");
		}
		//编辑
		AppMgr.edit = function(id,valid,e){
			layer_show("编辑意见反馈", getProjectName() +"/pages/qm/shop/feedback/edit.jsp?id="+id,"600","362");
		}
    	this.initDatas = function(){
    	
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/shop/feedback/list",
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
                		fbStartTime : $('#startTime').val(),
                		fbEndTime : $('#end').val(),
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
    		        field: 'fbCategory',
    		        title: '意见分类',
    		        width:125
    		    }, {
    		    	field:'fbContent',
    		    	title:'反馈内容',
    		    	width: 300,
    		    	formatter : function(value, row, index) {
                        if(value != null && value.length > 20){
                        	return "<span alt='"+value+"' title='"+value+"'>" +value.substring(0,19)+"...</span>";
                        }else{
                        	return value;
                        }
                    }
    		    }, {
    		    	field:'createTime',
    		    	title:'反馈时间',
    		    	width: 180
    		    }/* , {
    		    	field:'replyUserName',
    		    	title:'回复人'
    		    } */,  {
    		    	field:'replyContent',
    		    	title:'回复内容',
    		    	width: 300,
    		    	formatter : function(value, row, index) {
                        if(value != null && value.length > 20){
                        	return "<span alt='"+value+"' title='"+value+"'>" +value.substring(0,19)+"...</span>";
                        }else{
                        	return value;
                        }
                    }
    		    },{
    		    	field:'replyTime',
    		    	title:'回复时间',
    		    	width: 180
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
   		        		var freeze="";
   		        		var edit ="";
   		        		var setRole="";
   		        		if(row.replyTime == null || row.replyTime == ""){
   		        			if(row.is_show != 1 ){
		   						freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.id + "',event)\" style='cursor: pointer' > 取消反馈  &nbsp;</a></span>";
   		        			}
   						edit = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.edit('" + row.id + "',event)\" style='cursor: pointer' >编辑</a></span>";
   		        		}
    		        	return freeze +edit+ setRole;
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
                    	<%--商户名称：<input type="text" placeholder="商户名称" class="form-control input-inline"  id="shopName" name="shopName" width="280px">  --%>
                    	 反馈时间：<input id="startTime" class="laydate-icon form-control layer-date" placeholder="开始时间"  style="width: 150px;" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
			                  - <input id="end" class="laydate-icon form-control layer-date" placeholder="结束时间"  style="width: 150px;" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"> 
                           <button  id="btn_search" type="button" class="btn btn-inline btn-default" style="margin-top: 4px;"> 搜索</button> 
                           <button  id="btn_new" type="button" class="btn btn-inline btn-normal" style="margin-top: 4px;">意见反馈</button> 
                    </div>
                </div>
                <table id="user_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
