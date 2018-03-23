function AppMgr(){

	
	AppMgr.check = function() {
		
		$("#ps_check_before").val("");
		$("#ps_check_ok").val("");
		
		$("#btn_get_dis").show();
		$("#btn_get").hide();
		
		AppMgr.formatPs();
		
		var psLine = $("#psLine").val();
		
		if (psLine.length == 0){
			layer.msg("请输入信息", {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
				parent.layer.close(index);
	  		});
			return;
		}
		
		AppMgr.loadCheck();
		
	}
	AppMgr.loadCheck1 = function() {
		$("#div_check").show();
		$('#table_check').bootstrapTable('refresh',{url:mainWeb + "/admin/get/checkList?ps=" + $('#ps_check_before').val()});
		alert("re");
		
	}
	
	AppMgr.loadCheck = function() {
		// $('#table-large-columns').bootstrapTable('refresh',{url:'meter_data.php?bm=2'});
		$("#div_check").show();
		$('#table_check').bootstrapTable('destroy').bootstrapTable({
			url: mainWeb + "/admin/get/checkList",
			sidePagination:'server',//怎么分也？server or client
            iconSize: "outline",//工具栏是否显示边框
            showFooter:false,//显示表底部
            striped:true,//隔行变色
            queryParams: function(params) {
            	params.ps = $('#ps_check_before').val();
            	return params;
            },
           idField:'APPLY_ID',
 		   columns: [{
 		        field: 'GAMECODE',
 		        title: '参赛号码'
 		    }, {
 		        field: 'GROUP_NAME',
 		        title: '组别名称'
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
 		    }]
		     //,data:data.prodList
		  }); 
	}
	
	AppMgr.formatPs = function(){
		var psLine = $("#psLine").val();
		//psLine = psLine.replace(/(^\s*)|(\s*$)/g, "");
		psLine = psLine.replace(/\s*/g, "");
		psLine = psLine.replace(/，/g,",");
		
		$("#psLine").val("");
		$("#ps_check_before").val("");
		
		if (psLine.length == 0){
			return;
		}
		
		var psa = psLine.split(",");
		psLine = "";
		var searchText = "";
		var isFirst = true;
		for (var i = 0; i < psa.length; i++) {
			var temp = psa[i];
			if (temp.length == 0) {
				continue;
			}
			if (isFirst) {
				psLine = temp;
				searchText = "'" + temp + "'";
				isFirst = false;
			} else {
				psLine += "," + temp;
				searchText += ",'" + temp + "'";
			}
		}
		$("#psLine").val(psLine);
		$("#ps_check_before").val(searchText);
		
		return psLine;
		
		
	}
	
	AppMgr.get = function(){
		
		AppMgr.formatPs();
		
		var ps = $("#ps_check_ok").val();
		var check = $("#ps_check_before").val();
		
		if (ps.length == 0 || check != ps){
			layer.msg("信息有改变,请先核对信息", {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
				parent.layer.close(index);
	  		});
			
			$("#div_check").hide();
			$("#ps_check_ok").val("");
			$("#btn_get_dis").show();
			$("#btn_get").hide();
			
			return;
		}
		
		$("#btn_get_dis").show();
		$("#btn_get").hide();
		
		
		$.ajax({
				type:"POST",
				url: mainWeb + "/admin/get/batchGet",
				data: 'ps=' + ps,
				dataType: 'json',
				success: function(data) {
					if (data.error) {
						layer.msg(data.msg, {time:2000,title:'提示', btn: ['确定'],icon: 6}, function(index){
							parent.layer.close(index);
							$("#btn_get_dis").hide();
							$("#btn_get").show();
				  		});
					} else {
						layer.msg(data.msg, {time:2000,title:'提示', btn: ['确定'],icon: 6}, function(index){
							parent.$('#div_table').bootstrapTable('refresh');
							layer_close();
		   		  		});
					}
				}
		});
	
	};
	

	this.initActions = function(){
		$("#btn_get").hide();
		// 搜索
		$('#btn_close').click(function() {
			layer_close();
		}); 
		
		$('#btn_check').click(function() {
			AppMgr.check();
		}); 
		$('#btn_get').click(function() {
			AppMgr.get();
		}); 
		
		$('#btn_check_ok').click(function() {
			
			$("#div_check").hide();
			$("#ps_check_ok").val($("#ps_check_before").val());
			$("#btn_get_dis").hide();
			$("#btn_get").show();
		}); 
		
		
		
	};
	
	this.init = function() {
	 	this.initActions();
	};
} 

$(document).ready(function() {
		new AppMgr().init();
}); 