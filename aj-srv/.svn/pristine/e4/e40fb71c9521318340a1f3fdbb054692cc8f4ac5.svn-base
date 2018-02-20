<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广告位申请审核</title>
<script type="text/javascript" src="<%=mainWeb%>/scripts/eason.js"></script>
<script type="text/javascript" src="<%=mainWeb%>/scripts/json.js"></script>
<script type="text/javascript" src="<%=mainWeb%>/scripts/ajaxfileupload.js"></script>
<style type="text/css">
	#activityTabs tr.dateTr td.labletd {
		width: 75px;
	}
	#activityTabs tr.dateTr .Wdate {
		width: 150px;
	}
</style>	
<script type="text/javascript" charset="utf-8">
	function PageOpt(id) {
	    var $backButton = $('#back');
		var $okButton = $('#ok');
		var $doBackButton = $('#doBack');
		var initParamter = function() {
			
			if (!id) {
				$.messager.alert("警告","无效访问");
				return;
			} else {
				//获取值
				$.post(mainWeb + "/admin/position/audit/detail",{"id":id},
					function(rel){
						if (!rel) {
							rel ={};
							rel.error = true;
							rel.msg = "没得到返回值";
						}
						
						if (rel.error) {
							$.messager.alert('提示', rel.msg, 'info', function() {
								window.returnValue="success";//刷新父页面
						    	window.close();
							  });
							return;
						}
						
						var data = rel.obj;
						
						//alert(JSON.stringify(data));
						
						//alert(data.id);
						
						$('#merchantName').text(data.merchantName);
						$('#positionName').text(data.positionName);
						$('#reason').value(data.reason);
						
						
						//$("#online_timeStr").datetimebox("setValue", data.online_timeStr);
						//$("#offline_timeStr").datetimebox("setValue", data.offline_timeStr);
						
						
					},"json");
			
			}
			
			//确认
			$okButton.click(function(){
				
				if(!validate()) {
					return;
				}
				
				$.messager.confirm('提示','是否确定通过申请？',function(r){
					if(r){
						var param = "id=" + Pub_Id  + 
									  "&reason=" + $("#reason").val() + 
									  "&effStartTimeStr=" + $("#online_timeStr").datetimebox("getValue") + 
									  "&effEndTimeStr=" + $("#offline_timeStr").datetimebox("getValue");
						
						$.ajax({
							type: 'POST',
							url: mainWeb + "/admin/position/audit/pass",
							data: param,
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
									window.returnValue="success";//刷新父页面
							    	window.close();
								  });
							   }
						     });
						 }
					});
				});
			
			//退回
			$doBackButton.click(function(){
				
				if(!validateBack()) {
					return;
				}
				
				$.messager.confirm('提示','是否确定退回申请？',function(r){
					if(r){
						var param = "id=" + Pub_Id  + 
									  "&reason=" + $("#reason").val();
						
						$.ajax({
							type: 'POST',
							url: mainWeb + "/admin/position/audit/back",
							data: param,
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
									window.returnValue="success";//刷新父页面
							    	window.close();
								  });
							   }
						     });
						 }
					});
				});
					
				//返回
				$backButton.click(function(){
					//window.returnValue="success";//刷新父页面
			    	window.close();
				});
				
		};
		
		
		initParamter();
	}
	
	
	
	function validate() {
		var flag = com.eason.check.easyui.isEmpty($("#reason"),"备注说明") && 
				com.eason.check.easyui.limitLength($("#reason"),"备注说明",120);

		if (!flag) {
			return false;
		}
		
		var startTime = $("#online_timeStr").datetimebox("getValue");
		var endTime = $("#offline_timeStr").datetimebox("getValue");
		
		if(startTime==null || startTime==""){
			$.messager.alert("警告","请选择【开始时间】");
			return false;
		}
		if(endTime==null || endTime==""){
			$.messager.alert("警告","请选择【结束时间】");
			return false;
		}
		//var check = com.eason.check.compareTime(startTime,endTime);
		if (startTime >= endTime) {
			$.messager.alert("警告","【开始时间】必须早于【结束时间】");
			return false;
		}
		
		return true;
	};
	
	function validateBack() {
		var flag = com.eason.check.easyui.isEmpty($("#reason"),"退回理由") && 
		 			com.eason.check.easyui.limitLength($("#reason"),"退回理由",120);
		
		if (!flag) {
			return false;
		}
		
		return true;
	};
	
	
	
	
	var Pub_Id;
	
	$(function() {
	    var obj = window.dialogArguments;
	    Pub_Id = obj.id;
		new PageOpt(obj.id);
	 
		var dataParam = {editable:false, showSeconds:false};
		
		
		$('#online_timeStr').datebox(dataParam); 
		$('#offline_timeStr').datebox(dataParam); 
		
		$("#ok").hide();
		$("#doBack").hide();
		
		$('#auditType').combobox({
			 editable:false,
			 onSelect: function(rec){
				 
				 var val = rec.value;
				 $("#_reason").show();
				 if (val == "pass") {
					 $("#_time").show();
					 $("#_text_pass").show();
					 $("#_text_pass").siblings().hide();
					 
					 $("#ok").show();
					 $("#doBack").hide();
				 } else if (val == "back") {
					 $("#_text_back").show();
					 $("#_text_back").siblings().hide();
					 $("#_time").hide();
					 $("#ok").hide();
					 $("#doBack").show();
				 } else {
					 $("#_time").hide();
					 $("#_reason").hide();
					 $("#ok").hide();
					 $("#doBack").hide();
				 }
				 
			 }
		}); 
		
		
		
		
		
	});
	</script>		
	</head>
	<body style="padding: 0;margin: 0" >
		<form id='activityForm' method='POST' enctype='multipart/form-data'>
		<div id="activityTabs" class="easyui-tabs" style="height: 450px ;">
		<div title="广告位申请审核" style="padding: 5px 10px;">
		    	<input type="hidden" id="id" name="id"/>
				<div style='padding: 5px 20px;'>
					<table style='padding: 10px 30px;'>
						<tr style='height: 30px;'>
							<td height="30" align="right">商户名称：</td>
							<td height="30">
								<span id='merchantName'></span>
							</td>
						</tr>
						
						<tr style='height: 30px;'>
							<td height="30" align="right">广告位名称：</td>
							<td height="30">
								<span id='positionName'></span>
							</td>
						</tr>
						
						<tr style='height: 30px;'>
							 <td height="30" align="right">审核结果：</td>
						     <td height="30">
						     	 <select class="easyui-combobox" id="auditType" name="type" style="width:150px;">
									<option value="">--请选择--</option>
									<option value="pass">通过</option>
									<option value="back">退回</option>
								</select>
					        </td>
							        
						</tr>	
						
						
						<tr id="_time" style="display:none;">
						    <td height="30" align="right">开始时间：</td>
						    <td height="30" >
						    	<input type='text' id='online_timeStr' name="online_timeStr" style="width:150px;height:25px" />
						    	结束时间：
						    	<input type='text' id='offline_timeStr' name="offline_timeStr"  style="width:150px;height:25px" />
						    </td>
						</tr>
						
						<tr id="_reason" style="display:none;">
						    <td height="35" align="right">
						    	<span id="_text_pass">备注说明：</span>
						    	<span id="_text_back" style="display:none;">退回理由：</span>
						    </td>
						    <td height="35" >
							    <textarea id="reason" name="reason" style="border:1px solid #ccc; width:460px;"></textarea>
						    </td>
						</tr>
										
					</table>
				</div>
			</div>
			
		   
		   
		</div>
		<div style="heigth: 25px; padding: 10px 0 0 0; text-align: right;">
			<a href="#" style="margin-right: 10px;" id="ok" style="display:none;" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">通过</a>
			<a href="#" style="margin-right: 10px;" id="doBack" style="display:none;" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">退回</a>
			 
			<a href="#" style="margin-right: 10px;" id="back" class="easyui-linkbutton" data-options="iconCls:'icon-back'">返回</a>
		</div>
	</form>
	</body>
</html>