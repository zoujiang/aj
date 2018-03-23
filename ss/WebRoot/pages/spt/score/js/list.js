function AppMgr(){

	var $table=$("#div_table");
	AppMgr.batch = function(id){
		/*var index = layer.open({
			type: 2,
			title: "发放物品",
			content: mainWeb +"/pages/spt/get/get.jsp?id="+ id
		});
		layer.full(index);*/
		
		layer_show("成绩管理", mainWeb +"/pages/spt/score/jobEdit.jsp?id="+ id,"630","460");
	};
		
	
	AppMgr.doCert = function(id){
		$.ajax({
				url: mainWeb + "/admin/score/job/makeCertByScore",
				async: true, //异步
				data: 'id=' + id,
				dataType: 'json',
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
		layer.msg("证书生成请求已提交，正在生成中...", {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
			//layer_close();
			parent.layer.close(index);
  		});
		
	};
	
	AppMgr.view = function(id){
		
		
		
		alert("view");
	}
	
	
		
	
	this.initDatas = function(){
		 $table.bootstrapTable({
			url: mainWeb + "/admin/score/list",
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
           		params.type = $('#type').val();
           		params.typeVal = encodeURI($('#typeVal').val());
           		params.isCrt = $('#isCrt').val();
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
		        field: 'GAMECODE',
		        title: '参赛号码'
		    }, {
		        field: 'TRUENAME',
		        title: '跑者姓名'
		    }, {
		        field: 'MOBILE',
		        title: '手机号码'
		    },{
		    	field:"CERTNO",
		    	title:"证件号码"
		    },{
		    	field:"TEAM_NAME",
		    	title:"团队名称"
		    },{
		    	field:"SCORE_TIME1",
		    	title:"5KM净计时"
		    },/*{
		    	field:"SCORE_TIME2",
		    	title:"10KM净计时"
		    },*/{
		    	field:"SCORE_TIME2",
		    	title:"15KM净计时"
		    },{
		    	field:"SCORE_TIME",
		    	title:"净计时"
		    },{
		    	field:"SCORE_RANK",
		    	title:"排名"
		    },
		    
		    {
		    	field:"CERT_STATUS",
		    	title:"证书状态",
		        formatter:function(value, row, index){
		        	//证书状态(0-无证书，1-带生成，2-已生成，3-生成失败)
		        	if(row.CERT_STATUS == 0){
		        		return "无证书";
		        	}
		        	if(row.CERT_STATUS == 1){
		        		return "待生成";
		        	}
		        	if(row.CERT_IF_DOWN == 1){
		        		return "已下载";
		        	}
		        	
		        	if(row.CERT_STATUS == 2){
		        		return "已生成";
		        	}
		        	if(row.CERT_STATUS == 3){
		        		return "生成失败";
		        	}
		        }
		    },/*{
		    	field:"getDtText",
		    	title:"成绩生成时间"
		    },*/{
		    	field: 'action',
		    	title : '详情',
		        formatter : function(value, row, index){
		        	
		        	if(row.CERT_STATUS == 0){
		        		return "";
		        	}
		        	
		        	if(row.CERT_STATUS == 2){
		        		// "已生成";
		        		return "<a href='" + mainWeb + row.CERT_URL + "' target='_blank' >查看证书</a>";
		        	}
		        	
		        	if(row.CERT_STATUS == 1 || row.CERT_STATUS == 3){
		        		//return "<a href=\"javascript:AppMgr.doCert('" + row.APPLY_ID + "')\" >生成证书</a>";
		        		return "";
		        	}
		        	
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
        url: mainWeb + "/admin/athlete/getSelect",
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