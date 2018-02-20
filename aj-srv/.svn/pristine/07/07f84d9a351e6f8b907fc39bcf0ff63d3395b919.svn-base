<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广告位商户申请审核</title>
<script type="text/javascript">
function PageMgr(){
 	var $table=$("#table_area");
 			
 			
	//审核
	PageMgr.audit = function(id,e){
		var obj = new Object();
		obj.id = id;
		
		com.eason.window.winUtils.showDialogWindowOfWH(800,500,e,mainWeb + "/pages/ad/position/merchantAuditEdit.jsp",obj,function(returnValue){
			if("success"==returnValue){
              	$table.datagrid('reload');
            		}
		}); 
	};
 			
 	this.initTable = function(){
		$table.datagrid({
			fit: true,
			border: false,
			rownumbers: true,
			pagination: true,
			singleSelect: true,
			url:mainWeb + "/admin/position/audit/list",
		    columns:[[  
		     	{field:'positionCode',title:'广告位编码',width:65,align:'center', sortable: false},
		     	{field:'positionName',title:'广告位名称',width:180,align:'center', sortable: false},
		     	{field:'merchantName',title:'商家名称',width:180,align:'center', sortable: false},
		     	{field:'createDateStr',title:'申请时间',width:120,align:'center', sortable: false},
		     	{field:'positionImge',title:'广告位预览图',width:80,align:'center', 
		     		formatter : function(value, row, index) {
		     			if (isEmpty(value)) {
		     				return "";
		     			} else {
		     				return "<a href=\"#\" onclick=\"previewImage(event,'" + value + "')\">查看</a>&nbsp;";
		     			}
	       			}
		     	},{field:'statusText',title:'状态',width:100,align:'left', 
		        	formatter : function(value, row, index) {
		        		
		        		var mod = row.statusText;
		        		
		        		if (row.status == "0") {  //申请 --审核
					     	<%--操作拿到这里，页面上好操作些--%>
							mod += "&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"PageMgr.audit('" + row.id + "',event)\" >审核</a></span>";
						}
		        		
		        		return mod ;
		       		}
		       	},{field:'startTimeStr',title:'生效时间',width:120,align:'left', 
		     		formatter : function(value, row, index) {
		     			if (row.status != "1") {
		     				return "";
		     			}
		     			var mod = "";
		     			if (!isEmpty(row.effStartTimeStr)) {
		     				mod += "生效时间:" + row.effStartTimeStr;
		     			}
		     			if (!isEmpty(row.effEndTimeStr)) {
		     				
		     				if (!isEmpty(mod)) {
		     					mod += "<br/>";
		     				}
		     				
		     				mod += "失效时间:" + row.effEndTimeStr;
		     			}
		     			
		     			return mod;
	       			}
		     	},
		     	{field:'createTimeStr',title:'操作时间',width:180,align:'left', 
		     		formatter : function(value, row, index) {
		     			
		     			var mod = "";
		     			if (row.status == "1") {
		     				mod += "通过时间:" + row.passDateStr;
		     				mod += "<br/>操作人:" + row.passUser;
		     			} else if (row.status == "4") {
		     				mod += "退回时间:" + row.backDateStr;
		     				mod += "<br/>操作人:" + row.backUser;
		     			}
		     			
		     			return mod;
	       			}
		     	}
		     	<%--
		     	,{field:'action',title:'操作',width:'100',align: 'left',
		        	formatter : function(value, row, index) {
						var mod = "";
						
						
						if (row.status == "1") {  //已通过  --下线
							//mod += "&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"PageMgr.offline('" + row.id + "',event)\" >下线</a></span>";
							//mod += "&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"PageMgr.offline('" + row.id + "',event)\" >下线</a></span>";
							mod = "";
						} else if (row.status == "0") {  //申请 --审核
							mod += "&nbsp;<span  height = '23px'  width = '23px'><a style='color:blue;' href='#'onclick=\"PageMgr.audit('" + row.id + "',event)\" >审核</a></span>";
						}
						return mod;
					}
		        }
		     	--%>
		     	
		       	
		    	
		     ]]  
		  }); 
	};
 			
	this.initActions = function(){
		// 搜索
		$('#search').click(function() {
			var json = {};
			json.positionName =  $('#positionName').val();
			json.positionCode =  $('#positionCode').val();
			json.merchantName =  $('#merchantName').val();
			json.status = $('#status').combobox('getValue');
			
			//alert(JSON.stringify(json));
			
			$table.datagrid('load',  json);
		});
	
	};
	this.init = function() {
		
 		this.initTable();
 		this.initActions();
    
 		$('#status').combobox({editable:false}); 
 	
	};
}
 		
function isEmpty(t) {
	return t == null || t == undefined || t == '';
}
 		
 		
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
						商户名称 <input id="merchantName" name="merchantName" type="text" style="width:160px; height: 23px;" />
						广告位名称 <input id="positionName" name="positionName" type="text" style="width:120px; height: 23px;" />
						广告位编码 <input id="positionCode" name="positionCode" type="text" style="width:40px; height: 23px;" />
						
						状态 
						 <select id="status" name="status" >
						 	<option value="">全部</option>
						    <option value="0">申请</option>
						    <option value="1">通过</option>
						    <option value="4">退回</option>
						 </select>
						 
				        			

						<a id='search' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
						
						
						<%--
						<a id="export" href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">导&nbsp;出</a>
						 --%>
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