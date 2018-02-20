function AppMgr(){

	var $table=$("#div_table");
	AppMgr.batch = function(id){
		/*var index = layer.open({
			type: 2,
			title: "发放物品",
			content: mainWeb +"/pages/spt/get/get.jsp?id="+ id
		});
		layer.full(index);*/
		
		layer_show("发放物品", mainWeb +"/pages/spt/get/get.jsp?id="+ id,"930","460");
	};
		
	
	AppMgr.get = function(id,flag){
		
			$.ajax({
					url: mainWeb + "/admin/get/doGet",
					data: 'id=' + id + "&isGet=" + flag,
					dataType: 'json',
					success: function(data) {
						if (data.error) {
							layer.msg(data.msg, {time:2000,title:'提示', btn: ['确定'],icon: 6}, function(index){
								//layer_close();
								parent.layer.close(index);
					  		});
						} else {
							layer.msg(data.msg, {time:2000,title:'提示', btn: ['确定'],icon: 6}, function(index){
								//layer_close();
								$table.bootstrapTable('refresh');
			   		  		});
						}
					}
			});
		
		
	};
	
	AppMgr.get1 = function(id,flag,e){
		var flagText = "已领取";
		if ("0" == flag) {
			flagText = "未领取";
		}
		
		parent.layer.confirm('确定要设置为' + flagText + '吗？', {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(index){
			parent.layer.close(index);
			
			$.ajax({
					url: mainWeb + "/admin/get/doGet",
					data: 'id=' + id + "&isGet=" + flag,
					dataType: 'json',
					success: function(data) {
						if (data.error) {
							layer.msg(data.msg, {title:'提示', btn: ['确定'],icon: 6}, function(index){
								parent.layer.close(index);
					  		});
						} else {
							layer.msg(data.msg, {time:2000,title:'提示', btn: ['确定'],icon: 6}, function(index){
								$table.bootstrapTable('refresh');
			   		  		});
						}
					}
			});
		
			
		}, function(index){
			parent.layer.close(index);
		});
	};
		
	
	this.initDatas = function(){
		 $table.bootstrapTable({
			url: mainWeb + "/admin/get/list",
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
           		params.isGet = $('#isGet').val();
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
		   columns: [/*{
		        field: 'APPLY_ID',
		        title: '申请ID'
		    },*/{
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
		    	field:"IS_GET",
		    	title:"是否领物",
		        formatter:function(value, row, index){
		        	if(value == 0){
		        		return "否";
		        	}else if(value == 1){
		        		return "是";
		        	}
		        }
		    },{
		    	field:"getDtText",
		    	title:"操作时间"
		    },{
		    	field: 'action',
		    	title : '详情',
		        formatter : function(value, row, index){
		        	
		        	
		        	if(row.IS_GET == 1){
		        		return "<a href='#' onclick=\"AppMgr.get('" + row.APPLY_ID + "', 0)\" >撤销领取</a>";
		        	} else if(row.IS_GET == 0){
		        		return "<a href='#' onclick=\"AppMgr.get('" + row.APPLY_ID + "', 1)\" >领取物品</a>";
		        	}
		        	return "";
		        	
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
					+ "&isGet=" + $('#isGet').val();
			location.href = mainWeb + "/admin/get/export?" + ps;
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