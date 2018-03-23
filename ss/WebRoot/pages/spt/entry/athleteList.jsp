<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>马拉松-参赛用户信息查询</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#athlete_table");
			//修改
			AppMgr.showDetail = function(id, groupId,e){
				var index = layer.open({
	    			type: 2,
	    			title: "详情",
	    			content: getProjectName() +"/pages/spt/entry/athleteInfo.jsp?applyId="+ id+"&groupId="+groupId
	    		});
	    		layer.full(index);
				//layer_show("修改区域", getProjectName() +"/pages/demo/demoEdit.jsp?id=" + id,"800","600");
			};
			AppMgr.edit = function(id, groupId,e){
				if(groupId == 4){
					layer_show("修改选手信息", getProjectName() +"/pages/spt/entry/editapplyc.jsp?applyId="+ id+"&groupId="+groupId,"800","400");
				} else {
					layer_show("修改选手信息", getProjectName() +"/pages/spt/entry/editapply.jsp?applyId="+ id+"&groupId="+groupId,"800","400");
				}
			}
			
    	this.initDatas = function(){
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/athlete/getAthleteList",
                showExport:false,
                toolbar:'#toolbar',
               // exportDataType:"selected",
    			pagination:true,//是否显示分页栏
    			pageSize:20,
    			sidePagination:'server',//怎么分也？server or client
    			showRefresh:false,//是否显示刷新按钮
    			showColumns:false,//是否显示隐藏列按钮
                iconSize: "outline",//工具栏是否显示边框
                showFooter:false,//显示表底部
                striped:true,//隔行变色
                queryParams: function(params) {
                	return{
                		groupId:$("#group").val(),
                		searchType:$("#searchType").val(),
                		searchValue:$("#searchValue").val(),
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
    		        field: 'gameCode',
    		        title: '参赛号码'
    		    }, {
    		        field: 'groupName',
    		        title: '组别名称'/*,
    		        visible:false*/
    		    }, {
    		        field: 'tradeNo',
    		        title: '订单号'
    		    }, {
    		        field: 'trueName',
    		        title: '选手姓名'
    		    }, {
    		        field: 'mobile',
    		        title: '手机号码'
    		    },{
    		    	field:"certNo",
    		    	title:"证件号码"
    		    },{
    		    	field:"teamName",
    		    	title:"团队名称"
    		    },{
    		    	field:"addDt",
    		    	title:"报名日期",
    		    	formatter:function(value, row, index){
    		    		//var date = new Date(value);
    		    		return new Date(value * 1000).format("yyyy-MM-dd hh:mm:ss");
    		    	}
    		    },{
    		    	field:"isGet",
    		    	title:"领物",
    		        formatter:function(value, row, index){
    		        	if(value == 0){
    		        		return "否";
    		        	}else if(value == 1){
    		        		return "是";
    		        	}
    		        }
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
    		        	return "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.edit('" + row.id + "','"+row.groupId+"',event)\" style='cursor: pointer' >编辑&nbsp;</a></span><span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.showDetail('" + row.id + "','"+row.groupId+"',event)\" style='cursor: pointer' >查看&nbsp;</a></span>";
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
			
			//导出数据
			$('#btn_exp').click(function(e){
				location.href = getProjectName() + "/admin/athlete/export?type=athlete";
			});
		};
			
		
		this.init = function() {
		 	this.initDatas();
		 	this.initActions();
		}
    }

    function initSelect(){
    	//$("#group").selectpicker({});  //初始化
    	$.ajax({
   	        type: "POST",
   	        url: getProjectName() + "/admin/athlete/getSelect",
   	        dataType: "text",
   	        data:{},
   	        success: function(data){
   	        	if(data == null || data == ''){
   					return;
   				}
   	        	//$("#group").empty();
                $("#group").append(data);    
                $("#group").selectpicker('render');
                $("#group").selectpicker('refresh');
   	        }
   		});	
    }
    $(document).ready(function() {
  		new AppMgr().init();
  		initSelect();
	});
    </script>
</head>

<body class="gray-bg" style="height: 100%;">
    <div class="wrapper wrapper-content animated fadeInRight" style="height: auto;">
        <div class="ibox float-e-margins"  style="height: 100%;">
            <div class="ibox-content"  style="height: 100%;">
            	<div class="btn-group hidden-xs" id="toolbar" style="margin-top: 10px;">
		            <div class="form-inline" style="float: left; margin-left: 10px; margin-bottom: 15px;">
		                <div class="form-group">
		                    <select class="form-control w70" name="searchType" id="searchType">
		                    	<option>请选择查询类型</option>
                                <option value="gameCode">参赛号码</option>
                                <option value="mobile">手机号码</option>
                                <option value="certNo">证件号码</option>
                                <option value="trueName">跑者姓名</option>
                            </select>
		                </div>
		                <div class="input-group" style="margin-top: 5px;">
		                    <input id="searchValue" name="searchValue" class="form-control" type="text" placeholder="输入查询值">
		                </div>
		                <div class="input-group"  style="margin-top: 7px;">
		                    <select id="group" class="selectpicker form-control w70" title="请选择赛事组别"></select>
					        <div class="input-group-btn">
					            <button id="btn_search" class="btn btn-default" type="button"><i class="glyphicon glyphicon-search"></i></button>
					        </div>
		                </div>
		                <button id="btn_exp" type="button" class="btn btn-outline btn-default" title="导出" style="margin-top: 5px;">
	                        <i class="glyphicon glyphicon-export" aria-hidden="true" ></i>
	                    </button>
		            </div>
		        </div>
                <table id="athlete_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
