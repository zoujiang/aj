<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String ownerId = request.getParameter("ownerId");
String type = request.getParameter("type");

%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>管理平台-下载列表</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <link rel="shortcut icon" href="favicon.ico"> 
    <script type="text/javascript">
    function AppMgr(){

    	var $table=$("#user_table");
    	
    	AppMgr.del = function(id,valid,e){
    		parent.layer.confirm('确定要删除该学生信息？', {
    		    btn: ['确定','取消'], //按钮
    		    shade: false //不显示遮罩
    		}, function(index){
    			parent.layer.close(index);
    			var url =  getProjectName() + "/admin/kindergarten/student/del";
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
    	//片源地址查看
		AppMgr.view = function(id,shortAddress, sec,e){
			if(shortAddress != 'undefined' && shortAddress != ""){
				var ad = '<%=basePath%>kd.html?'+shortAddress;
				layer_show("下载", getProjectName() +"/pages/qm/shop/album/viewAlbumDownload.jsp?ad="+ad+"&sec="+sec,"558","250");
			}else{
				layer.msg("暂无照片可以下载", {title:'提示', btn: ['确定'],icon: 6}, function(index){
        			
   		  		}); 
			}
		}
    	this.initDatas = function(){
    		
			 $table.bootstrapTable({
    			url: getProjectName() + "/admin/kindergarten/photo/albumList?ownerId=<%=ownerId %>&type=<%=type%>",
                showExport:false,
                toolbar:'#toolbar',
               // exportDataType:"selected",
    			pagination:false,//是否显示分页栏
    			sidePagination:'server',//怎么分也？server or client
    			showRefresh:false,//是否显示刷新按钮
    			showColumns:false,//是否显示隐藏列按钮
                iconSize: "outline",//工具栏是否显示边框
                showFooter:false,//显示表底部
                striped:true,//隔行变色
                queryParams: function(params) {
                	return{
                		kindergartenId : $('#kindergartenId').val(),
                		gradeId : $('#gradeId').val(),
                		name : $('#name').val(),
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
    		        title: 'ID'/*,
    		        visible:false*/
    		    },  {
    		    	field:'currentGradeName',
    		    	title:'班级'
    		    },{
    		    	field: 'action',
    		    	title : '操作',
    		        formatter : function(value, row, index){
   		        		var down="<span  height = '23px'  width = '23px'><a style='color:blue;' href='#' onclick=\"AppMgr.view('" + row.id + "','"+row.shortAddress +"', '"+ row.downloadSecret+"', event)\" style='cursor: pointer' >下载&nbsp;</a></span>";
    		        	return  down;
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
                    </div>
                </div>
                <table id="user_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
