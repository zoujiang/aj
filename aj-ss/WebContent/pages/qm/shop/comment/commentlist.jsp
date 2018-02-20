<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-评论列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#user_table");
    	//启用/冻结
		AppMgr.freeze = function(id,valid,e){
    		var m = valid == 0?'审批通过':'审批不通过';
			parent.layer.confirm('确定要'+ m +'该评论？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/shop/cmt/modifyState";
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
    	this.initDatas = function(){
    		
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/shop/cmt/list",
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
                		cmtUserName : $('#username').val(),
                		beginTime : $('#startTime').val(),
                		endTime : $('#end').val(),
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
    		        field: 'nickName',
    		        title: '评论人'/*,
    		        visible:false*/
    		    }, {
    		        field: 'userTel',
    		        title: '评论人电话号码'/*,
    		        visible:false*/
    		    }, {
    		    	field:'shopName',
    		    	title:'被评论店铺'
    		    }, {
    		    	field:'score',
    		    	title:'打分'
    		    }, {
    		    	field:'cmtContent',
    		    	title:'评论内容'
    		    }, {
    		    	field:'cmtImg',
    		    	title:'图片',
    		    	formatter:function(value, row, index){
    		    		if(value != null && value != ''){
    		        		return "<a href='#' onclick=viewImg('"+ value +"')>查看</a>";
    		        	}
    		    	}
    		    }, {
    		    	field:'type',
    		    	title:'类型'
    		    }, {
    		    	field:'cmtTime',
    		    	title:'评论时间'
    		    },{
    		    	field:'status',
    		    	title:'审批状态',
    		    	formatter:function(value, row, index){
    		    		if(value == 0){
    		        		return "已审批";
    		        	}else if(value == 1){
    		        		return "审批不通过";
    		        	} else{
    		        		return "删除";
    		        	}
    		    	}
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
   		        		var freeze="";
   		        		if(row.status == "0" || row.status == "1"){
	   		        		if(row.status == "0"){
	   							freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.id + "','1',event)\" style='cursor: pointer' >审批不通过&nbsp;</a></span>";
	   		        		} else {
	   							freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.id + "','0',event)\" style='cursor: pointer' >审批通过&nbsp;</a></span>";
	   		        		}
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
    
    function viewImg(url){
		layer_show("图片浏览", getProjectName() +"/pages/qm/shop/comment/viewImg.jsp?url="+url,"850","555");

		//iframe层
	   /*  layer.open({
	        type: 2,
	        title: '图片浏览',
	        shadeClose: true,
	        shade: 0.8,
	        area: ['750px', '500px'],
	       // btn:['确定','取消'],
	        closeBtn:false,
	        content: getProjectName() +"/pages/qm/shop/comment/viewImg.jsp?url="+url,
	       
	    });  */
	}
    </script>
</head>

<body class="gray-bg" style="height: 100%;">
    <div class="wrapper wrapper-content animated fadeInRight" style="height: auto;">
        <div class="ibox float-e-margins"  style="height: 100%;">
            <div class="ibox-content"  style="height: 100%;">
            	<div class="btn-group hidden-xs" id="toolbar" role="group" style="margin-top: 10px;">
                    <div class="form-inline" style="width: 1080px;float: left;">
                          <div class="col-sm-10">
                                  	  评论时间：<input id="startTime" class="laydate-icon form-control layer-date" placeholder="开始时间"  style="width: 150px;" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
			                                  - <input id="end" class="laydate-icon form-control layer-date" placeholder="结束时间"  style="width: 150px;" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
			                    	商户名称：<input type="text" placeholder="商户名称" class="form-control input-inline"  id="shopName" name="shopName" style="width: 150px;"> 
			                    	客户名称：<input type="text" placeholder="客户名称" class="form-control input-inline"  id="username" name="username" style="width: 150px;" > 
			                           <button  id="btn_search" type="button" class="btn btn-inline btn-default" style="padding-left:5px;margin-top: 0px;float: right;"> 搜索</button> 
                          </div>
                    </div>
                </div>
                <table id="user_table"></table>
            </div>
        </div>
    </div>
    <script>
       
    </script>
</body>
</html>
