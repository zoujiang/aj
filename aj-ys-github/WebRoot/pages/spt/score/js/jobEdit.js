function AppMgr(){

	AppMgr.score_crt = function(){
		if (isEmptyById("group")) {
			layer.msg("请选择赛事组别", {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
				parent.layer.close(index);
	  		});
			return;
		}
		if (isEmptyById("uploadFile")) {
			layer.msg("输选择导入文件", {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
				parent.layer.close(index);
			});
			return;
		}
		$("#btn_score").hide();
		$("#btn_score_crt").hide();
		
		$.ajaxFileUpload({
	        url: mainWeb + "/admin/score/job/uploadAndCert",   //和AppMgr.score就这里不同
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: 'uploadFile', // file标签的id
	        dataType: 'json', //返回值类型，一般设置为json、application/json
	        //data:{groupId: $("#group").val(), jobName:$("#jobName").val()},
	        data:{groupId: $("#group").val()},
	        
	        success: function(data){  
	        	if (data.error) {
					layer.msg(data.msg, {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
						parent.layer.close(index);
						$("#btn_score").show();
						$("#btn_score_crt").show();
						
						if (data.code == 'file_error') {
							window.location.href = mainWeb + data.obj;
						}
						
			  		});
				} else {
					layer.msg(data.msg, {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
						parent.$('#div_table').bootstrapTable('refresh');
						layer_close();
	   		  		});
				}
	        }
		});
	}
	
	
	AppMgr.score = function(){
		
		if (isEmptyById("group")) {
			layer.msg("请选择赛事组别", {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
				parent.layer.close(index);
	  		});
			return;
		}
		if (isEmptyById("uploadFile")) {
			layer.msg("输选择导入文件", {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
				parent.layer.close(index);
			});
			return;
		}
		$("#btn_score").hide();
		$("#btn_score_crt").hide();
		
		$.ajaxFileUpload({
	        url: mainWeb + "/admin/score/job/upload", 
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: 'uploadFile', // file标签的id
	        dataType: 'json', //返回值类型，一般设置为json、application/json
	        //data:{groupId: $("#group").val(), jobName:$("#jobName").val()},
	        data:{groupId: $("#group").val()},
	        
	        success: function(data){  
	        	if (data.error) {
					layer.msg(data.msg, {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
						parent.layer.close(index);
						$("#btn_score").show();
						$("#btn_score_crt").show();
						
						if (data.code == 'file_error') {
							window.location.href = mainWeb + data.obj;
						}
						
			  		});
				} else {
					layer.msg(data.msg, {time:15000,title:'提示', btn: ['确定'],icon: 6}, function(index){
						parent.$('#div_table').bootstrapTable('refresh');
						layer_close();
	   		  		});
					
					/*layer.msg("保存成功.生成成绩及证书请求已提交,将在后台进行导入中..", {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
						parent.$('#div_table').bootstrapTable('refresh');
						layer_close();
	   		  		});*/
					
					AppMgr.doImp();
				}
	        }
		});
		
		
	};
	
	
	AppMgr.scoreSyncCert = function(){
		
		if (isEmptyById("group")) {
			layer.msg("请选择赛事组别", {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
				parent.layer.close(index);
	  		});
			return;
		}
		if (isEmptyById("uploadFile")) {
			layer.msg("输选择导入文件", {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
				parent.layer.close(index);
			});
			return;
		}
		$("#btn_score").hide();
		$("#btn_score_crt").hide();
		
		$.ajaxFileUpload({
	        url: mainWeb + "/admin/score/job/upload", 
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: 'uploadFile', // file标签的id
	        dataType: 'json', //返回值类型，一般设置为json、application/json
	        //data:{groupId: $("#group").val(), jobName:$("#jobName").val()},
	        data:{groupId: $("#group").val()},
	        
	        success: function(data){  
	        	if (data.error) {
					layer.msg(data.msg, {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
						parent.layer.close(index);
						$("#btn_score").show();
						$("#btn_score_crt").show();
						
						if (data.code == 'file_error') {
							window.location.href = mainWeb + data.obj;
						}
						
			  		});
				} else {
					/*layer.msg(data.msg, {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
						parent.$('#div_table').bootstrapTable('refresh');
						layer_close();
	   		  		});*/
					
					layer.msg("保存成功.生成成绩及证书请求已提交,将在后台进行导入中..", {time:15000,title:'提示', btn: ['确定'],icon: 6}, function(index){
						parent.$('#div_table').bootstrapTable('refresh');
						layer_close();
	   		  		});
					
					AppMgr.doImpAndCert();
				}
	        }
		});
		
		
	};
	
	AppMgr.doImp = function(){
		$.ajax({
				url: mainWeb + "/admin/score/job/doImp",
				data: 'id=1',
				dataType: 'json',
				async: true, //异步
				success: function(data) {
					
				}
		});
		
	};
	
	AppMgr.doImpAndCert = function(){
		$.ajax({
				url: mainWeb + "/admin/score/job/doImpAndCert",
				data: 'id=1',
				dataType: 'json',
				async: true, //异步
				success: function(data) {
					
				}
		});
		
	};
	
	

	this.initActions = function(){
		//$("#btn_get").hide();
		// 搜索
		$('#btn_close').click(function() {
			layer_close();
		}); 
		
		$('#btn_score').click(function() {
			AppMgr.score();
		}); 
		$('#btn_score_crt').click(function() {
			//AppMgr.score_crt();
			AppMgr.scoreSyncCert();
		}); 
		$('#btn_get').click(function() {
			AppMgr.get();
		}); 
		
		
		
		
		
	};
	
	this.init = function() {
	 	this.initActions();
	};
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