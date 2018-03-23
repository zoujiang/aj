<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-商户分类列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#user_table");
    	//启用/冻结
		AppMgr.freeze = function(id,valid,e){
			parent.layer.confirm('确定要'+valid==0?'启用':'冻结'+'分类？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/shop/category/modifyState";
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
			layer_show("编辑分类", getProjectName() +"/pages/qm/shop/category/editcategory.jsp?id="+id,"800","420");
		}
		AppMgr.add = function(e){
    		layer_show("添加分类", getProjectName() +"/pages/qm/shop/category/addcategory.jsp","800","420");
		};
    	this.initDatas = function(){
    	
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/shop/category/list",
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
                		name : $('#categoryname').val(),
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
    		        title: '分类ID'/*,
    		        visible:false*/
    		    }, {
    		        field: 'name',
    		        title: '分类名称'/*,
    		        visible:false*/
    		    }, {
                    field: 'type',
                    title: '类型',
                    formatter:function(value, row, index){
                        if(value == 1){
                            return "像馆";
                        }else if(value == 2){
                            return "幼儿园";
                        } else if(value == 3){
                            return "券商"
                        }
                    }
                }, {
    		    	field:'description',
    		    	title:'分类描述'
    		    }, {
    		    	field:'sort',
    		    	title:'分类排序'
    		    }, {
    		    	field:'status',
    		    	title:'状态',
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
   		        		var setRole="";
   		        		if(row.status == "0" || row.status == "1"){
	   		        		if(row.status == "0"){
	   							freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.id + "','1',event)\" style='cursor: pointer' >冻结&nbsp;</a></span>";
	   		        		} else {
	   							freeze = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.freeze('" + row.id + "','0',event)\" style='cursor: pointer' >启用&nbsp;</a></span>";
	   		        		}
	   		        		del = "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.edit('" + row.id + "','0',event)\" style='cursor: pointer' >编辑&nbsp;</a></span>";
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
                    <div class="form-inline" style="width: 480px;float: left;">
                    	 一级分类名称：<input type="text" placeholder="一级分类名称" class="form-control input-inline"  id="categoryname" name="categoryname" width="280px"> 
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
