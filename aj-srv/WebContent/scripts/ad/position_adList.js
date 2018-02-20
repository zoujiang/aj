/*var page = {};
	
$(function() {
    var obj = window.dialogArguments;
    page.id = obj.id;
    alert('open');
    
    initDatagrid();
    
    loadRelations();
	
});*/

//加载投放关系
function loadRelations() {
	
	
	$.post(mainWeb + "/admin/position/relationList",{"id":page.id},
		function(rel) {
		
			if (!rel) {
				rel ={};
				rel.error = true;
				rel.msg = "没得到返回值";
			}
			
			if (rel.error) {
				showMsg(rel.msg);
				return;
			}
			
			//alert(JSON.stringify(rel.obj));
			
			var gridData = {};
			gridData.total = 1;
			gridData.rows = rel.obj.rels;
			
			$("#table_area").datagrid("loadData", gridData);
			
		},"json");
}

//加载投放关系
function initDatagrid(){
	
	$("#table_area").datagrid({
		fit: true,
		border: false,
		//rownumbers: true,
		//pagination: true,
		singleSelect: true,
		//url:mainWeb + "/admin/ad/relationList?contentId=" + page.id,
	    columns:[[  
            {field:'seq',title:'排序',width:30,align:'center'},
            {field:'type',title:'类型',width:35,align:'center',
	     		formatter : function(value, row, index) {
	     			
	    	       	if (row.content.type == "text") {
	    				return "文字";
	    			} else if (row.content.type == "url") {
	    				return "网址";
	    			} else if (row.content.type == "video") {
	    				return "视频";
	    			} else if (row.content.type == "img") {
	    				return "图片";
	    			} else if (row.content.type == "product") {
	    				return "商品";
	    			} else if (row.content.type == "merchant") {
	    				return "商家";
	    			} 
	    	       	return "";
					
	       		}
	       	},
	     	{field:'ad_name',title:'广告名称',width:180,align:'center',
				formatter : function(value, row, index) {
					return row.content.ad_name;
				}
            	
	     	},//${u.content.online_time_str}-${u.content.offline_time_str}<br/>
	     	{field:'interval',title:'轮播',width:30,align:'center', sortable: false},
	       	{field:'time',title:'展示时间',width:220,align:'center',
	     		formatter : function(value, row, index) {
	        		
	     			var text = row.content.online_time_str + "&nbsp;-&nbsp;" + row.content.offline_time_str;
	     			
	     			if (!isEmpty(row.timeBr)) {
	     				text += "<br>" + row.timeBr;
	     			}
					
	        		return text;
	       		}
	       	}, {field:'url',title:'连接/图片',width:120,align:'center',
	     		formatter : function(value, row, index) {
	        		
	     			var text = "";
	     			if (row.content.type == "url") {
	     				 text += "<a href=\"" + row.content.url + "\" target=\"_blank\">网址</a>&nbsp;";
	     			}
	     			if (row.content.type == "video" && !isEmpty(row.content.video)) {
	     				text += "<a href=\"#\" onclick=\"previewImage(event,'" + row.content.video + "')\">视频</a>&nbsp;";
	     			}
	     			
	     			if (!isEmpty(row.content.img_big)) {
	     				//text += "<a href=\"" + row.content.img_big + "\" target=\"_blank\">大图</a>&nbsp;";
	     				text += "<a href=\"#\" onclick=\"previewImage(event,'" + row.content.img_big + "')\">大图</a>&nbsp;";
	     				
	     			}
	     			if (!isEmpty(row.content.img_small)) {
	     				//text += "<a href=\"" + row.content.img_small + "\" target=\"_blank\">小图</a>&nbsp;";
	     				text += "<a href=\"#\" onclick=\"previewImage(event,'" + row.content.img_small + "')\">小图</a>&nbsp;";
	     			}
	     			
	     			if (isEmpty()) {}
	     			
					
	        		return text;
	       		}
	       	},
	     	{field:'status',title:'状态',width:80,align:'center', 
	        	formatter : function(value, row, index) {
	        		
	        		if (row.content.ad_status == 1 || row.content.ad_status-5 == 1) { //广告状态为‘上线’
						if (row.status == "0") {  
							return "无效";
						}
						
						if (row.status == 1) {  
							if (row.content.ad_status - 5 == 1) { //待上线, 后台把 '待上线'的广告内容状态 值 加了5，方便这里区分
								return "待上线";
							} else {
								return "有效";
							}
						}
					} else if (row.content.ad_status == 3) {  //广告状态为‘已过期'
						return "过期";
					} else { //已下线
						return "下线";
					}
	        		
	       		}
	       	},
	     	
	    	{field:'action',title:'操作',width:'100',align: 'left',
	        	formatter : function(value, row, index) {
					var mod = "aaaa";
					
					mod = "<input type='hidden' id='row_" + row.id + "' value='" + JSON.stringify(row) + "'/>"
					
					//广告状态为‘上线’的才能操作
					if (row.content.ad_status == 1 || row.content.ad_status-5 == 1) { 
						if (row.status == "0") {  //无效 -》有效
							mod += "&nbsp;&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"ralationOnline('" + row.id + "',event)\" >生效</a></span>";
						}
						
						if (row.status == "1") {  //有效 -》无效
							mod += "&nbsp;&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"ralationOffline('" + row.id + "',event)\" >无效</a></span>";
						}
						
						mod += "&nbsp;&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"relationEdit('" + row.id + "',event)\" >调整</a></span>";
					} 
					
					
					return mod;
				}
	        }
	     ]] 
	  }); 
	
}

function ralationOnline(id, event) {
	$.messager.confirm('提示','确定要显示这个广告位吗？',function(r){
		if (r) {
			$.ajax({
				url: mainWeb + "/admin/position/ralationOnline",
				data: 'id=' + id,
				dataType: 'json',
				success: function(data) {
					$.messager.alert('提示', data.msg, 'info', function() {
						
						if (data.error) {
							return;
						}
						loadRelations();
					  });
				   }
			     });
		} 
	});
}

function ralationOffline(id, event) {
	$.messager.confirm('提示','确定不显示这个广告位吗？',function(r){
		if (r) {
			$.ajax({
				url: mainWeb + "/admin/position/ralationOffline",
				data: 'id=' + id,
				dataType: 'json',
				success: function(data) {
					$.messager.alert('提示', data.msg, 'info', function() {
						
						if (data.error) {
							return;
						}
						loadRelations();
					  });
				   }
			     });
		} 
	});
}
function relationEdit(id, event) {
	
	
	
	var row = $("#row_" + id).val();
	
	
	
	if (isEmpty(row)) {
		showMsg("数据获取失败");
		return;
	}
	
	var json = JSON.parse(row);
	$("#rel_interval").val(json.interval);
	$("#rel_seq").val(json.seq);
	$("#rel_ad_name").text(json.content.ad_name);
	$("#rel_id").val(json.id);
	$("#rel_text").text(json.content.text);
	
}



function saveRelation() {
	var json = {};
	json.id = $("#rel_id").val();
	json.interval = $("#rel_interval").val();
	json.seq = $("#rel_seq").val();
	
	json.content_id = page.id;
	
	if (isEmpty(json.interval)) {
		showMsg("请输入轮播时间");
		return;
	} 
	if (isEmpty(json.seq)) {
		showMsg("请输入序号");
		return;
	} 
	
	$.messager.confirm('提示',"是否确认修改", function(r){
		if(r){
			
			$.ajax({
				type: 'POST',
				url: mainWeb + "/admin/position/relationUpdate",
				data: json,
				dataType: 'json',
				success: function(data) {
					if (!data) {
						showMsg("访问异常");
						return;
					}
					
					$.messager.alert('提示', data.msg, 'info', function() {
						
						if (data.error) {
							return;
						}
						$("#rel_id").val('');
						$("#rel_interval").val('');
						$("#rel_seq").val('');
						$("#rel_ad_name").text('');
						
						loadRelations();
					  });
				   }
			     });
			 }
	});
	
	
	
}



function showMsg(msg) {
	$.messager.alert("提示",msg, 'info');
}

function isEmpty(t) {
	return t == null || t == undefined || t == '';
}
