<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>查看通知</title>
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
	function AreaOpt(id,type,onConfirm) {

		var vo = null;
	 
		var $saveButton = $('#save');
	
		var initParamter = function() {
			//查询填充表达
			$.post(mainWeb + "/admin/push/message/single",{"id":id},
				function(data){
				
				$("#activityForm input[name='msgId']").val(data.msgId);
				$("#activityForm select[name='msgPlatform']").val(data.msgPlatform);
				
				$("#activityForm input[name='msgTitle']").val(data.msgTitle);
				$("#activityForm select[name='doAudit']").val(data.doAudit);
				$("#activityForm select[name='doSend']").val(data.doSend);
				$("#activityForm select[name='msgSentDelay']").val(data.msgSentDelay);
				if(data.msgSentDelay == 1){
					$("#activityForm input[name='msgSentDelayTime']").val(data.msgSentDelayTime);
					$("#TR_msgSentDelayTime").show();
				} else {
					$("#activityForm input[name='msgSentDelayTime']").val("");
					$("#TR_msgSentDelayTime").hide();
				}
				$("#activityForm textarea[name='msgContent']").val(data.msgContent);
				},"json");
			
			//保存
			$saveButton.click(function(){
				$('#activityForm').form('submit', {
					url:mainWeb + "/admin/act/addAct",
					onSubmit: function(param){
						if(validate()){
							$(this).form("validate");
							$saveButton.attr("disabled","true");
							return true;
						}
						$backButton.click();
						return false;
					},
					success:function(data){
						data=$.trim(data.replace(/<\/?.+?>/g,""));
			        	$submitAppButton.removeAttr("disabled");
					    $saveButton.removeAttr("disabled");
					    if(data=='fileUploadError'){
					    	$.messager.alert('提示', '上传文件失败。');
					    }
					    if(data=='fileMinSizeError'){
					    	$.messager.alert('提示', '您上传的文件为空文件。');
					    }
					    if(data=='fileMaxSizeError'){
					    	$.messager.alert('提示', '您上传的文件超过5M。');
					    }
					    if(data=='success'){
					    	$.messager.alert('提示', '修改成功', 'info', function() {
						    	window.returnValue="success";//刷新父页面
						    	window.close();
					    	});	
					    }
					    if(data=='error'){
						    $.messager.alert('提示', '修改失败。');
					    }
					}
				});
			});
		};
		initParamter();
	}
	
	function validate(){
		var flag=com.eason.check.easyui.isEmpty($("#actCfg"),"秒杀时间点") &&
		com.eason.check.easyui.isEmpty($("#actName"),"秒杀活动名称");
		return flag;
	};
	
	$(document).ready(function () {
	    var obj = window.dialogArguments;
		new AreaOpt(obj.actId,obj.editType);
			com.eason.form.disableForm("activityForm",true);
	});
	</script>		
	</head>
	<body style="padding: 0;margin: 0" >
		
		<form id='activityForm' method='POST' enctype='multipart/form-data'>
			
			<div id="activityTabs" class="easyui-tabs" style="height: 450px ;">
					<div title="秒杀活动" style="padding: 5px 10px;">
				<input type="hidden" name="msgId" />
				<table style="font-size: 14px;">
					<tr>
						<td>
							推送平台：
						</td>
						<td>
							<input type="hidden" name="msgFrom" style="width: 200px;" value="1"/>
							<select name="msgPlatform">
								<option value="0">-请选择-</option>
								<option value="ALL">所有设备</option>
								<option value="IOS">IOS设备</option>
								<option value="ANDROID">Android设备</option>
							</select>
						</td>
						
					</tr>
				
					<tr id="TR_msgSentDelayTime" style="display: none;">
						<td>
							发送时间：
						</td>
						<td colspan="3">
							<input class="Wdate"  type="text" name="msgSentDelayTime" style="width: 200px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /><font color='red'>*</font>
						</td>
					</tr>
					<tr>
						<td>
							消息标题：
						</td>
						<td colspan="3">
							<input type="text" id="msgTitle" name="msgTitle" style="width: 400px;" /><font color='red'>*</font>
						</td>
					</tr>
					<tr>
						<td>
							消息内容：
						</td>
						<td colspan="3">
							<textarea name="msgContent" id="msgContent" style="width: 400px; height:100px; resize:none;"></textarea><font color='red'>*</font>
						</td>
					</tr>
					<tr id="TR_STATUS">
						<td>
							发送状态：
						</td>
						<td>
							<select name="doSend">
								<option value="0">待发送</option>
								<option value="-1">发送失败</option>
								<option value="1">发送成功</option>
								<option value="2">发送中</option>
							</select>
						</td>
						<td>
							审核状态：
						</td>
						<td>
							<select name="doAudit">
								<option value="0">待审核</option>
								<option value="-1">审核失败</option>
								<option value="1">审核成功</option>
							</select>
						</td>
					</tr>
				</table>
				</div>
				</div>
		   
		</div>
		<div style="heigth: 25px; padding: 10px 0 0 0; text-align: right;">
			<a href="#"  id="save" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确定</a>
		</div>
	</form>

	</body>
</html>