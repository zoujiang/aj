
//加载广告位
function loadPostion() {
	
	$.post(mainWeb + "/admin/position/listByType",{"type":page.posType},
		function(rel){
			if (!rel) {
				rel ={};
				rel.error = true;
				rel.msg = "没得到返回值";
			}
			
			if (rel.error) {
				/*$.messager.alert('提示', rel.msg, 'info', function() {
					window.returnValue="success";//刷新父页面
			    	window.close();
				  });*/
				return;
			}
			
			$("#ad_posid").combobox({
					valueField: 'id',
					textField: 'name',
					data: rel.obj});
			
		},"json");
}

//加载投放关系
function loadRelations() {
	
	$("#rel_table").find('[index=1]').siblings().remove();
	
	$.post(mainWeb + "/admin/ad/relationList",{"contentId":page.id},
		function(rel){
			if (!rel) {
				rel ={};
				rel.error = true;
				rel.msg = "没得到返回值";
			}
			
			if (rel.error) {
				//showMsg(rel.msg);
				return;
			}
			
			loadPositionToTable(rel.obj);
			
			
		},"json");
}

//加载投放关系
function loadRelations11(){
	//$("#table_adrelation").datagrid({
	$("#table_adrelation").propertygrid({
		fit: true,
		border: false,
		//rownumbers: true,
		//pagination: true,
		singleSelect: true,
		url:mainWeb + "/admin/ad/relationList?contentId=" + page.id,
	    columns:[[  
	     	{field:'pos_name',title:'广告位',width:180,align:'center', sortable: false},
	     	{field:'interval',title:'轮播时间',width:180,align:'center', sortable: false},
	       	{field:'seq',title:'排序',width:180,align:'center'},
	       	{field:'time',title:'时间段',width:120,align:'center'},
	     	{field:'status',title:'状态',width:80,align:'center', 
	        	formatter : function(value, row, index) {
	        		
	        		if (row.status == "1") {  //上线  --下线
						return "上线";
					}
					
					if (row.status == "0") {  //下线 --上线
						return "下线";
					}
					
	        		return "";
	       		}
	       	},
	     	{field:'create_user',title:'创建人',width:80,align:'center',sortable: false},
	    	{field:'action',title:'操作',width:'100',align: 'left',
	        	formatter : function(value, row, index) {
					var mod = "";
					
					if (!row.edit) {
						return "";
					}
					
					mod += "<span  height = '23px'  width = '23px'><a href='#'onclick=\"editRelation('" + row.id + "',event)\" >修改</a></span>";
					mod += "<span  height = '23px'  width = '23px'><a href='#'onclick=\"delRelation('" + row.id + "',event)\" >删除</a></span>";
					
					if (row.status == "1") {  //上线  --下线
						mod += "&nbsp;<span  height = '23px'  width = '23px'><a href='#'onclick=\"offlineRelation('" + row.id + "',event)\" >下线</a></span>";
					}
					
					if (row.status == "0") {  //下线 --上线
						mod += "&nbsp;<span  height = '23px'  width = '23px'><a href='#'onclick=\"onlineRelation('" + row.id + "',event)\" >上线</a></span>";
					}
					
					return mod;
				}
	        }
	     ]]  
	  }); 
	
}


//增加时间段
function addTimeSpan() {
	var temp = $("#temp_times").html();
	var container = $("#ad_times");
	$(temp).appendTo(container);
	var cur = container.children("span").last();
cur.children("input").timespinner();
	
}

//删除时间段
function delTimeSpan(dom) {
	var obj = $(dom);
	if (obj.size() > 0) {
		obj.parent().remove();
	}
}


//解析时间段到SPAN中
function parseTimeToSpan(times) {
	if (times == null || times == undefined) {
		//return;
		times = "";
	}
	var container = $("#ad_times");
	container.children("span").remove();
	
	var ts = times.split(",");
	for (var i = 0; i < ts.length; i++) {
		var t = ts[i];
		if (isEmpty(t)) {
			return;
		}
		var tt = t.split("-");
		if (tt.length < 2) {
			return;
		}
		
		addTimeSpan();
		var cur = container.children("span").last();
		
		cur.find("input[att='start']").timespinner("setValue",tt[0]);
		cur.find("input[att='end']").timespinner("setValue",tt[1]);
		
	}
}


//从SPAN中解析时间段
function parseTimeFromSpan() {
	var container = $("#ad_times");
	var ts = "";
	var lastEnd = "";
	var error = false;
	container.children("span").each(function() {
		//var start = $(this).find("input[att='start']").val();
		//var end = $(this).find("input[att='end']").val();
		var start = $(this).find("input[att='start']").timespinner("getValue");
		var end = $(this).find("input[att='end']").timespinner("getValue");
		if (isEmpty(start)) {
			showMsg("请选择时间段开始时间");
			error = true;
			return false;
		}
		if (isEmpty(end)) {
			showMsg("请选择时间段结束时间");
			error = true;
			return false;
		}
		if (start >= end) {
			//showMsg("时间段开始时间'" + start + "'不能大于结束时间'" + end + "'");
			showMsg("'时间段结束时间'" + end + "'必须大于开始时间'" + start + "'");
			error = true;
			return false;
		}
		if (isEmpty(lastEnd)) {
			lastEnd = end;
		} else if (lastEnd > start){
			showMsg("时间段开始时间'" + start + "'不能小于上一个时间段结束时间'" + end + "'");
			error = true;
			return false;
		}
		ts += start + "-" + end + ",";
	});
	if (error) {
		return false;
	}
	if (!isEmpty(ts)) {
		ts = ts.substring(0, ts.length -1);
	}
	$("#time").val(ts);
	return true;
}


function showMsg(msg) {
	$.messager.alert("提示",msg);
}

function addPostion() {
	var json = {};
	json.id = $("#rid").val();
	json.interval = $("#interval").val();
	json.seq = $("#seq").val();
	json.posid = $("#ad_posid").combobox("getValue");
	
	json.content_id = page.id;
	
	if (isEmpty(json.posid)) {
		showMsg("请选择广告位");
		return;
	} 
	if (isEmpty(json.interval)) {
		showMsg("请输入轮播时间");
		return;
	} 
	if (isEmpty(json.seq)) {
		showMsg("请输入排序");
		return;
	} 
	
	var flag = parseTimeFromSpan();
	if (!flag) {
		return;
	}
	json.time = $("#time").val();
	if (isEmpty(json.time)) {
		showMsg("请输入时间段");
		return;
	} 
	
	//alert(JSON.stringify(json) );
	
	$.messager.confirm('提示',"是否确认投放", function(r){
		if(r){
			
			$.ajax({
				type: 'POST',
				url: mainWeb + "/admin/ad/saveRelation",
				data: json,
				dataType: 'json',
				success: function(data) {
					if (!data) {
						$.messager.alert('提示', "访问异常", 'info');
						return;
					}
					
					$.messager.alert('提示', data.msg, 'info', function() {
						
						if (data.error) {
							return;
						}
						clearPositionInput();
						
						loadRelations();
						//$("#table_adrelation").datagrid("reload");
					  });
				   }
			     });
			 }
	});
	
	
	
}


function clearPositionInput() {
	
	
	$("#interval").val('');
	$("#seq").val('');
	//$("#ad_posid").combobox("getValue");
	$("#time").val('');
	
	parseTimeToSpan("");
}



//加载位置到表格
function addPositionToTable(json) {
	//<tr><td>广告位</td><td>轮播时间</td><td>排序</td><td>时间段</td><td>操作</td></tr>
	
	//ad_web_rel_table
	var table = $("#rel_table");
	//var index = table.find("tr").size() + 1;
	var index = json.id;
	
	td_html = "<tr index='" + index + "'><td>" + json.pos_name + "</td><td>"
			  + json.interval + "</td><td>"
			  + json.seq + "</td><td>" 
			 // + json.time + "</td><td>"
			  + json.timeBr + "</td><td>"
			  + "<input type=\"hidden\" id=\"pval_" + index + "\"/>"
			  + "<span style=\"cursor:pointer;color:blue;\" onclick=\"editPostion('" + index + "')\">修改</span>&nbsp;&nbsp;"
			  + "<span style=\"cursor:pointer;color:blue;\" onclick=\"delPostion('" + index + "')\">删除</span>&nbsp;&nbsp;"
			  + "</td></tr>";
	table.append(td_html);
	$("#pval_" + index).val(JSON.stringify(json));
	
	
	//table.find("input[name='pval_" + index + "']").val(JSON.stringify(json));
	
}



function editPostion(index) {
	
	var val = $("#pval_" + index).val();
	//alert(val);
	var json = JSON.parse(val);
	
	$("#interval").val(json.interval);
	$("#seq").val(json.seq);
	$("#ad_posid").combobox("setValue", json.posid);
	$("#rid").val(json.id);
	
	
	parseTimeToSpan(json.time);
}


function loadPositionToTable(jsonArray) {
	
	//alert("load" + JSON.stringify(jsonArray));
	if (isEmpty(jsonArray) || jsonArray.length == 0) {
		return;
	}
	for (var i = 0; i < jsonArray.length; i++) {
		var json = jsonArray[i];
		if (isEmpty(json)) {
			continue;
		}
		addPositionToTable(json);
	}
}



function delPostion(index) {
	
	$.post(mainWeb + "/admin/ad/delRelation",{"relationId":index},
		 function(rel){
			if (!rel) {
				rel ={};
				rel.error = true;
				rel.msg = "没得到返回值";
			}
			
			showMsg(rel.msg);
			if (rel.error) {
				return;
			}
			
			$("#rel_table").find("[index='" + index + "']").remove();
			
			//loadPositionToTable(rel.obj);
			
			
		},"json");
	
	/*var table = $("#" + table_id);
	table.find("tr").each(function() {
		if ($(this).attr("index") == index) {
			$(this).remove();
			return;
		}
	});*/
	
}

function isEmpty(t) {
	return t == null || t == undefined || t == '';
}
