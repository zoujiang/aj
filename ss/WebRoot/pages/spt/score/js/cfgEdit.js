function AppMgr(){

	AppMgr.score = function(){
		
		
		if (isEmptyById("isNeedScore")) {
			layer.msg("请选择是否需要成绩", {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
				parent.layer.close(index);
	  		});
			return;
		}
		
		if ($("#isNeedScore").val() == 1) {
			if (isEmptyById("minScore")) {
				layer.msg("请输入达标成绩", {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
					parent.layer.close(index);
				});
				return;
			} else {
				
			}
		} else {
			$("#isNeedScore").val(0);
			$("#minScore").val("");
		}
		
		
		if (isEmptyById("uploadFile")) {
			layer.msg("输选择证书背景图片", {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
				parent.layer.close(index);
			});
			return;
		}
		$("#btn_score").hide();
		
		$.ajaxFileUpload({
	        url: mainWeb + "/admin/score/certCfg/save", 
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: 'uploadFile', // file标签的id
	        dataType: 'json', //返回值类型，一般设置为json、application/json
	        //data:{groupId: $("#group").val(), jobName:$("#jobName").val()},
	        data:{groupId: thisId, 
	        		isNeedScore:$("#isNeedScore").val(), 
	        		minScore:$("#minScore").val()
	        },
	        
	        success: function(data){  
	        	if (data.error) {
					layer.msg(data.msg, {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
						parent.layer.close(index);
						$("#btn_score").show();
			  		});
				} else {
					layer.msg(data.msg, {time:5000,title:'提示', btn: ['确定'],icon: 6}, function(index){
						parent.$('#div_table').bootstrapTable('refresh');
						layer_close();
	   		  		});
				}
	        }
		});
		
		
	};
	
	
	//初始化：
	this.initDatas = function(){
		
		
		$.post(mainWeb + "/admin/score/certCfg/list",{"groupId":thisId},
			function(data){
				if (!data || !data.total || data.total == 0) return;
				
				$('#groupName').val(data.rows[0].GROUP_NAME);
				$('#isNeedScore').val(data.rows[0].IS_NEED_SCORE);
				$('#minScore').val(data.rows[0].MIN_SCORE);
				
				
				$('#isNeedScore').change();
				
				if (data.rows[0].BACK_IMG) {
					var img = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='" + mainWeb + data.rows[0].BACK_IMG + "' target='_blank' >查看</a>"
					$('#div_showView').html(img);
				}
				
				
			},"json");
	}

	this.initActions = function(){
		//$("#btn_get").hide();
		// 搜索
		$('#btn_close').click(function() {
			layer_close();
		}); 
		
		$('#btn_score').click(function() {
			AppMgr.score();
		}); 
		
		$("#isNeedScore").change(function() {
			var val = $("#isNeedScore").val();
			if (val == 1) {
				$("#div_minScore").show();
			} else {
				$("#div_minScore").hide();
			}
		});
		
	};
	
	this.init = function() {
		this.initActions();
		this.initDatas();
	};
} 


$(document).ready(function() {
		new AppMgr().init();
}); 