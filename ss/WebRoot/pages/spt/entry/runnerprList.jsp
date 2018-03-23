<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<!-- runner-plate-rule -->
    <title>马拉松-选手号码生成规则管理</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#runnerpr_table");
		//修改
		AppMgr.add = function(e){
			layer_show("添加选手号码生成规则", getProjectName() +"/pages/spt/entry/runnerprAdd.jsp","800","400");
		};
		
		AppMgr.edit = function(cfgId, e){
			layer_show("编辑选手号码生成规则", getProjectName() +"/pages/spt/entry/runnerprEdit.jsp?cfgId="+cfgId,"800","400");
		};
		
    	this.initDatas = function(){
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/gamecode/getGameCodeCfgList",
                showExport:false,
                toolbar:'#toolbar',
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
                idField:'cfgId',
                //http://bootstrap-table.wenzhixin.net.cn/documentation/
    		    columns: [{
    		        field: 'groupName',
    		        title: '组别名称'/*,
    		        visible:false*/
    		    }, {
    		        field: 'cfgPre',
    		        title: '规则前缀'
    		    }, {
    		        field: 'startNum',
    		        title: '开始号码'
    		    }, {
    		        field: 'endNum',
    		        title: '结束号码'
    		    },{
    		    	field:"reservedNums",
    		    	title:"保留号码"
    		    },{
    		    	field: 'action',
    		    	title : '编辑',
    		        formatter : function(value, row, index){
    		        	return "<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.edit('" + row.cfgId + "',event)\" style='cursor: pointer' >编辑&nbsp;</a></span>";
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
			$('#btn_new').click(function(e){
				AppMgr.add(e);
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
		            <button id="btn_new" type="button" class="btn btn-outline btn-default" title="添加">
                        <i class="glyphicon glyphicon-plus" aria-hidden="true" ></i>
                    </button>
		            <div class="form-inline" style="float: left; margin-left: 10px; margin-bottom: 15px;">
		                <div class="input-group">
		                    <select id="group" class="selectpicker form-control w70" title="请选择赛事组别">
					        </select>
					        <div class="input-group-btn">
					            <button id="btn_search" class="btn btn-default" type="button"><i class="glyphicon glyphicon-search"></i></button>
					        </div>
		                </div>
		            </div>
		        </div>
               <table id="runnerpr_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
