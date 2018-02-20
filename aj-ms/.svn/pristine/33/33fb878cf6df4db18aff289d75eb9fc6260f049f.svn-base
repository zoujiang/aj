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
    function saveUserRole(){
    	var records = $('#role_table').bootstrapTable('getSelections');
    	var ids= new Array();
		$.each(records, function(index, item){
			ids.push(item.id);
		});
		var url = getProjectName()+"/admin/user/setUserRoles";
		var obj = ajaxSubmit(url,{rids:ids.join(','), uid:"<%= request.getParameter("uid") %>"});
		if(obj.success){
			layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
	  		});
		}
		else{
			layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
				$("#btn_save").attr("disabled", false);
			});
		}
    }
    function AppMgr(){
    	var ckindex = [];
    	var $table=$("#role_table");
    	this.initDatas = function(){
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/user/getRolesOfUser/<%= request.getParameter("uid") %>",
                showExport:false,
               // exportDataType:"selected",
    			pagination:false,//是否显示分页栏
    			sidePagination:'server',//怎么分也？server or client
    			showRefresh:false,//是否显示刷新按钮
    			showColumns:false,//是否显示隐藏列按钮
                iconSize: "outline",//工具栏是否显示边框
                showFooter:false,//显示表底部
                striped:true,//隔行变色
                search:false,//是否显示搜索
                //searchText:'名称',
                strictSearch: true,
                searchOnEnterKey:true,//点击回车查询
                icons: {
                    refresh: "glyphicon-repeat",
                    columns: "glyphicon-list"
                },
                clickToSelect:true,//点击选中
                singleSelect:false,//是否单选
                idField:'id',
                //http://bootstrap-table.wenzhixin.net.cn/documentation/
    		    columns: [{
    		    	field:'ck',
    		        checkbox:true,
    		    	formatter:function(value, row, index){
    		    		if(value){
    		    			ckindex.push(index);
    		    		}
    		    	}
    		    }, {
    		        field: 'title',
    		        title: '角色名称'/*,
    		        visible:false*/
    		    }, {
    		    	field:'code',
    		    	title:'角色编码'
    		    }],
    		    onLoadSuccess:function(data){
    		    	//$table.bootstrapTable('check', 0);
    		    	$.each(ckindex, function(i, item){
    		    		$table.bootstrapTable('check', item);
    		    	})
    		    	//var data = ajaxSubmit("${ctx}/admin/user/getUserRoleIds",{uid:"<%= request.getParameter("id") %>"});
    		    }
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
  		$("#btn_save").click(function(){
			saveUserRole();
			$("#btn_save").attr("disabled", true);
		});
	});
    </script>
</head>

<body class="gray-bg" style="height: 100%;">
    <div class="wrapper wrapper-content animated fadeInRight" style="height: auto;">
        <div class="ibox float-e-margins"  style="height: 100%;">
            <div class="ibox-content"  style="height: 100%;">
                <table id="role_table"></table>
                <div class="hr-line-dashed"></div>
        		<div class="text-center">
        		 	<!-- 
        		 	<button id = "btn_add" type="button" class="btn btn-primary" disabled="disabled">新增 </button>
        		 	<button id = "btn_edit" type="button" class="btn btn-primary" disabled="disabled">编辑 </button>
        		 	<button id = "btn_del" type="button" class="btn btn-primary" disabled="disabled">删除 </button>
        		 	 -->
        		 	<button id = "btn_save" type="button" class="btn btn-primary">保存 </button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
