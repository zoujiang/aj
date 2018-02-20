function AppMgr(){

	var $table=$("#div_table");
	AppMgr.edit = function(id,groupName){
		//alert(groupName);
		layer_show("证书生成规则", mainWeb +"/pages/spt/score/cfgEdit.jsp?id="+ id + "&name=" + encodeURIComponent(groupName),"630","460");
	};
	
	AppMgr.doCrt = function(){
		$.ajax({
				url: mainWeb + "/admin/score/job/doCrt",
				data: 'id=a',
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
	
	
		
	
	this.initDatas = function(){
		 $table.bootstrapTable({
			url: mainWeb + "/admin/score/certCfg/list",
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
           idField:'ID',
		   columns: [{
		        field: 'GROUP_NAME',
		        title: '赛事组别'
		    }, {
		        field: 'ID',
		        title: '类型',
		        formatter:function(value, row, index){
		        	/*亲子：4
		    		迷你：3,11
		    		半程：1.9
		    		全场：2,10*/
		        	if ("4" == value) {
		    			return "亲子";
		    		} else if ("3" == value || "11" == value ) {
		    			return "迷你";
		    		} else if ("2" == value || "10" == value ) {
		    			return "全场";
		    		} else if ("1" == value || "9" == value ) {
		    			return "半程";
		    		} else {
		    			return "";
		    		}
		        	
		        }
		    }, {
		        field: 'IS_NEED_SCORE',
		        title: '是否需要成绩',
		        formatter:function(value, row, index){
		        	if(value == 1){
		        		return "是";
		        	}
		        	return "";
		        	
		        }
		    }, {
		        field: 'MIN_SCORE',
		        title: '生成证书成绩'
		    }, {
		    	field:"BACK_IMG",
		    	title:"背景图片",
		        formatter:function(value, row, index){
		        	
		        	if (value != null && value != undefined) {
		        		return "<a href='" + mainWeb + value + "' target='_blank' >查看</a>";
		        	}
		        	
		        	return "";
		        }
		    }, {
		    	field: 'action',
		    	title : '详情',
		        formatter : function(value, row, index){
		        	return "<a href=\"javascript:AppMgr.edit('" + row.ID + "','" + row.GROUP_NAME + "')\" >编辑</a>";
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