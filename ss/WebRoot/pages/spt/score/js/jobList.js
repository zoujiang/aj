function AppMgr(){

	var $table=$("#div_table");
	AppMgr.batch = function(id){
		layer_show("成绩导入", mainWeb +"/pages/spt/score/jobEdit.jsp?id="+ id,"630","460");
	};
	
	AppMgr.doImp = function(id){
		$.ajax({
				url: mainWeb + "/admin/score/job/doImp",
				data: 'id=' + id,
				dataType: 'json',
				async: true, //异步
				success: function(data) {
					if (data.error) {
						layer.msg(data.msg, {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
							//layer_close();
							parent.layer.close(index);
				  		});
					} else {
						layer.msg(data.msg, {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
							//layer_close();
							$table.bootstrapTable('refresh');
		   		  		});
					}
				}
		});
		layer.msg("生成成绩请求已提交，正在导入中...", {time:15000,title:'提示', btn: ['确定'],icon: 6}, function(index){
			//layer_close();
			parent.layer.close(index);
  		});
		
	};
	
	AppMgr.doCert = function(id){
		$.ajax({
				url: mainWeb + "/admin/score/job/doCert",
				data: 'id=' + id,
				dataType: 'json',
				async: true, //异步
				success: function(data) {
					if (data.error) {
						layer.msg(data.msg, {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
							//layer_close();
							parent.layer.close(index);
				  		});
					} else {
						layer.msg(data.msg, {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
							//layer_close();
							$table.bootstrapTable('refresh');
		   		  		});
					}
				}
		});
		layer.msg("证书生成请求已提交，正在生成中...", {time:15000,title:'提示', btn: ['确定'],icon: 6}, function(index){
			//layer_close();
			parent.layer.close(index);
  		});
		
	};
	
	
		
	
	this.initDatas = function(){
		 $table.bootstrapTable({
			url: mainWeb + "/admin/score/job/list",
           showExport:false,
           toolbar:'#toolbar',
			pagination:true,//是否显示分页栏
			sidePagination:'server',//怎么分也？server or client
			showRefresh:true,//是否显示刷新按钮
			showColumns:true,//是否显示隐藏列按钮
           iconSize: "outline",//工具栏是否显示边框
           showFooter:false,//显示表底部
           striped:true,//隔行变色
           queryParams: function(params) {
        	   params.groupId = $('#group').val();
           		//params.type = $('#type').val();
           		//params.typeVal = encodeURI($('#typeVal').val());
           		//params.isCrt = $('#isCrt').val();
           		return params;
           },
           //search:true,//是否显示搜索
           //searchText:'名称',
           strictSearch: true,
           searchOnEnterKey:true,//点击回车查询
           icons: {
               refresh: "glyphicon-repeat",
               columns: "glyphicon-list"
           },
           clickToSelect:true,//点击选中
           singleSelect:true,//是否单选
           idField:'APPLY_ID',
		   columns: [{
		        field: 'GROUP_NAME',
		        title: '组别名称'
		    },{
		        field: 'createDtText',
		        title: '导入时间'
		    },{
		        field: 'JOB_FILE_NAME',
		        title: '导入文件'
		    }, {
		        field: 'CREATE_USER',
		        title: '导入人'
		    }, {
		    	field:"JOB_STATUS",
		    	title:"成绩状态",
		        formatter:function(value, row, index){
		        	//作业执行状态0-待导入，1-导入中，2-导入成功，3-导入失败)
		        	if(row.JOB_STATUS == 0){
		        		return "待导入";
		        	}
		        	if(row.JOB_STATUS == 1){
		        		return "导入中";
		        	}
		        	if(row.JOB_STATUS == 2){
		        		return "导入成功";
		        	}
		        	if(row.JOB_STATUS == 3){
		        		return "导入失败";
		        	}
		        	return "";
		        }
		    },{
			 field:"CERT_STATUS",
			 title:"证书状态",
			 formatter:function(value, row, index){
				 //证书生成状态(0-待生成，1-生成中，2-生成成功，3-生成失败)
				 if(row.CERT_STATUS == 0){
					 return "待生成";
				 }
				 if(row.CERT_STATUS == 1){
					 return "生成中";
				 }
				 if(row.CERT_STATUS == 2){
					 return "生成成功";
				 }
				 if(row.CERT_STATUS == 3){
					 return "生成失败";
				 }
				 return "";
				 
			 }
		 }/*,
		    
		    {
		    	field: 'action',
		    	title : '详情',
		        formatter : function(value, row, index){
		        	
		        	if(row.CERT_STATUS == 0){
		        		return "";
		        	}
		        	
		        	if(row.CERT_STATUS == 2){
		        		// "已生成";
		        	}
		        	
		        	if(row.CERT_STATUS == 0 || row.CERT_STATUS == 3){
		        		return "<a href='#' onclick=\"AppMgr.doCert('" + row.APPLY_ID + "')\" >生成证书</a>";
		        	}
		        	
		        	
		        },
		        width: 200
		    }*/]
		});
		
  }
	
	this.initActions = function(){
			// 搜索
		$('#btn_search').click(function() {
			 $table.bootstrapTable('refresh');
		});
		
		//导出数据
		$('#btn_exp').click(function(e){
			//offset=0&limit=10&groupId=4&type=code&typeVal=&isGet=
			var ps  = "groupId=" +  $('#group').val() 
					+ "&type=" + $('#type').val()
					+ "&typeVal=" + encodeURI($('#typeVal').val()) 
					+ "&isCrt=" + $('#isCrt').val();
			location.href = mainWeb + "/admin/score/export?" + ps;
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
        url: mainWeb + "/admin/athlete/getSelectType",
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