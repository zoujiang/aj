<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广告点击量统计</title>
<script type="text/javascript">
function PageMgr(){
	var $table=$("#table_area");
	
	this.initTable = function(){
		$table.datagrid({
		fit: true,
		border: false,
		rownumbers: true,
		pagination: true,
		singleSelect: true,
		url:mainWeb + "/admin/stat/list",
	    columns:[[  
	     	{field:'posName',title:'广告位名称',width:180,align:'center', sortable: false,
	     		formatter : function(value, row, index) {
	        			return value; 
	       		}
	     	},
	     	{field:'adName',title:'广告名称',width:180,align:'center', sortable: false,
	     		formatter : function(value, row, index) {
	        			return value; 
	       		}
	     	},
	     	{field:'title',title:'统计项',width:180,align:'center', sortable: false},
	     	{field:'num',title:'统计值',width:180,align:'center', sortable: false}
	     	
	     ]]  
	  }); 
	};
		
		
	this.initActions = function(){
			// 搜索
		$('#search').click(function() {
			
			if (!validate()) {
				return;
			}
			
			var json = {};
			json.startTime = $("#startTimeStr").datetimebox("getValue");
			json.endTime = $("#endTimeStr").datetimebox("getValue");
			json.ad = $('#adName').combobox("getValue");
			json.pos = $('#posName').combobox("getValue");
			
			
			$table.datagrid('load',  json);
		});
	};
	this.init = function() {
	 	this.initTable();
	 	this.initActions();
	    

		var dataParam = {editable:false, showSeconds:false};
		
		
		//$('#online_timeStr').datetimebox(dataParam); 
		//$('#offline_timeStr').datetimebox(dataParam); 
		$('#startTimeStr').datebox(dataParam); 
		$('#endTimeStr').datebox(dataParam); 
		
	 	
	    $('#posName').combobox({
	        url:mainWeb + '/admin/stat/pos',
	        valueField:'id',
	        textField:'text',
	        editable:false
	    });

	    $('#adName').combobox({
	        url:mainWeb + '/admin/stat/ads',
	        valueField:'id',
	        textField:'text',
	        editable:false
	    });
	    
	  //  $('#posName').combobox("reload");
	   // $('#adName').combobox("reload");
	 	
	};
}


function validate() {
	
	/* var type = $('#posName').combobox("getValues");
	if(type==null || type==""){
		$.messager.alert("警告","请选择【广告位】");
		return false;
	} */
	
	var startTime = $("#startTimeStr").datetimebox("getValue");
	var endTime = $("#endTimeStr").datetimebox("getValue");
	
	if(startTime==null || startTime==""){
		$.messager.alert("警告","请选择【开始时间】");
		return false;
	}
	if(endTime==null || endTime==""){
		$.messager.alert("警告","请选择【结束时间】");
		return false;
	}
	
	flag = true;
	
	/* flag = com.eason.check.compareTime(startTime,endTime);
	if (flag == "-1") {
		$.messager.alert("警告","【开始时间】必须早于【结束时间】");
		return false;
	} */
	
	return flag;
};

	
	
$(document).ready(function() {
	new PageMgr().init();
});
</script>
</head>
<body>

	<div class="easyui-layout" fit="true" style = "min-height: 80%;width: 800;background: none repeat scroll 0% 0% rgb(255, 255, 255);">
		<div id="content" region="center" id="right_area" style="padding: 0px;margin : 2px 4px 4px 2px;border-radius: 5px 5px 5px 5px;border: 1px solid #ECECEC;"  fit="false" border="true">
			<div class="easyui-layout" fit="true" split="false" id="test_area" border="false">
				<div region="north" id="right_bottom_area" style="height: 35px; background-color: #EEF;" border="false">
						
						开始时间
						<input type='text' id='startTimeStr' name="startTimeStr" style="width:100px;height:25px" />
						结束时间：
						<input type='text' id='endTimeStr' name="endTimeStr"  style="width:100px;height:25px" />
						    
						
						广告位名称 
						 <select id="posName" name="posName" style="width:220px;height:25px">
						 </select>
						 广告名称 
						 <select id="adName" name="adName" style="width:220px;height:25px">
						 </select>
						 

						<a id='search' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
						
						<br/>
						
				</div>
				<div region="center" style="padding: 0px;" border="false">
					<table id="table_area"></table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>