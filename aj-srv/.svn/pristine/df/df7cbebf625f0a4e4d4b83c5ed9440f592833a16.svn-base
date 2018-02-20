<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广告位广告管理</title>
<script type="text/javascript" src="<%=mainWeb%>/scripts/ad/position_adList.js"></script>
<script type="text/javascript">
var page = {};

$(function() {
    var obj = window.dialogArguments;
    page.id = obj.id;
    
    
    
   //返回
	$("#back").click(function(){
		//window.returnValue="success";//刷新父页面
    	window.close();
	});
	//确定
	$("#ok").click(function(){
		saveRelation();
	});
  
    
    initDatagrid();
    
    loadRelations();
    
    
    
	
});		
</script>

</head>

<body>
<div class="easyui-layout" fit="true" split="false" id="test_area" border="false">
	<div region="center" style="padding: 0px;" border="false">
		<table id="table_area"></table>
	</div>
	
	<div region="south" id="right_bottom_area" style="height: 180px; background-color: #EEF;" border="false">
		<form id='activityForm' method='POST' enctype='multipart/form-data'>
			<div id="activityTabs" class="easyui-tabs" style="height: 135px ;">
			<div title="广告位管理" style="padding: 5px 10px;">
			    	<input type="hidden" id="id" name="id"/>
					<div style='padding: 5px 20px;'>
						<table style='padding: 10px 30px;'>
							<tr style='height: 30px;'>
								<td height="30" align="left">广告名称：</td>
								<td height="30" colspan="2" align="left">
									<input type="hidden" id="rel_id" value=""/>
									<span id="rel_ad_name"></span>
								</td>
							</tr>
							
							<tr style='height: 30px;'>
								<td align="left">轮播时间/秒：
						        </td>
						        <td align="left">
						        	<input id="rel_interval" name="interval" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="3" style="width:60px; height:24px; line-height:24px; border:1px solid #ccc" type="text"/>
						        	排序：
						        	<input id="rel_seq" name="seq" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="3" style="width:60px; height:24px; line-height:24px; border:1px solid #ccc" type="text"/>
						        </td>
								        
							</tr>	
							
							<tr style='height: 30px;'>
								<td colspan="3" id="rel_text">
								</td>
							</tr>
							
						</table>
					</div>
				</div>
				
			   
			   
			</div>
			<div style="heigth: 25px; padding: 10px 0 0 0; text-align: right;">
				<a href="#" style="margin-right: 10px;" id="ok" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
				 
				<a href="#" style="margin-right: 10px;" id="back" class="easyui-linkbutton" data-options="iconCls:'icon-back'">返回</a>
			</div>
		</form>
	</div>
				
</div>


</body>

</html>
