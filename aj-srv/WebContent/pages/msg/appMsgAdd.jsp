<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加通知</title>
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
			function validateMessageForm(){
				
				if($.trim($("#activityForm select[name='msgPlatform']").val()) == '0'){
					$.messager.alert('提示','请选择推送设备!','warning'); 
					return false;
				}
				if($.trim($("#activityForm select[name='msgType']").val()) == '0'){
					$.messager.alert('提示','请选择消息类型!','warning'); 
					return false;
				}
				if($.trim($("#activityForm select[name='msgSentUserGroup']").val()) == '-1'){
					$.messager.alert('提示','请选择消息接收群体!','warning'); 
					return false;
				}
				if($.trim($("#activityForm select[name='msgSentDelay']").val()) == ''){
					$.messager.alert('提示','请选择发送方式!','warning'); 
					return false;
				}
				if($.trim($("#activityForm select[name='msgSentDelay']").val()) == '1'){
					if($.trim($("#messageForm input[name='msgSentDelayTime']").val()) == ''){
						$.messager.alert('提示','请选择延迟发送时间!','warning'); 
						return false;
					}
				}
				
				return true;
			};
	function AreaOpt(Id,type,onConfirm) {
		var vo = null;
		var $saveButton = $('#save');
		var select = document.getElementById('msgSentUserGroup');	
		select.onchange = function(){	
			
			//value  document.getElementById("sect").value  
			var userGroup= document.getElementById("msgSentUserGroup").value  ;
			
			if(userGroup=="3"){
				  $('.partInfo').show();
				  $('.userTelInfo').hide();
				
			}else if(userGroup=="4"){
				$('.partInfo').hide();
				  $('.userTelInfo').show();
			}else if(userGroup=="2"){
				$('.partInfo').hide();
				  $('.userTelInfo').hide();
			}
		};
		var initParamter = function() {
			//选项卡点击
			//保存
			$('#partId').combobox({
			url : mainWeb + "/admin/combo/getPartNameComboList",
			valueField : 'id',
			textField : 'name'
      		});
			$("#partId").combobox('setValue',"0");
			
			
			$saveButton.click(function(){
				if(!validateMessageForm()){
					return;
				}
				$('#activityForm').form('submit', {
					url:mainWeb + "/admin/push/message/addMessage",
					onSubmit: function(param){
						if(validate()){
							$(this).form("validate");
							$saveButton.attr("disabled","true");
							return true;
						}
						
						return false;
					},
					error:function(xhr){
				    	doSubmit = false;
				    },
					success:function(data){
						
						$.messager.alert('提示', '保存成功。', 'info', function() {
							window.returnValue = "success";
							window.close();
						});
					}
				});
			});
		};
		initParamter();
	}
	
	function validate(){
		var flag=com.eason.check.easyui.isEmpty($("#msgTitle"),"消息标题") &&
		com.eason.check.easyui.isEmpty($("#msgContent"),"消息内容");
		return flag;
	};
	
	$(document).ready(function () {
		
	    var obj = window.dialogArguments;
		new AreaOpt(obj.areaId,obj.editType);
	});
	</script>		
	</head>
	<body style="padding: 0;margin: 0" >
		
		<form id='activityForm' method='POST' enctype='multipart/form-data'>
			
			<div id="activityTabs" class="easyui-tabs" style="height: 450px ;">
					<div title="添加通知" style="padding: 5px 10px;">
				<input type="hidden" name="msgId" />
				<table style="font-size: 14px;">
					<tr>
						<td>
							推送设备：
						</td>
						<td>
							<input type="hidden" name="msgFrom" style="width: 200px;" value="1"/>
							
						    <select name="msgPlatform" style="width: 120px;">
								<option value="0">-请选择-</option>
								<option value="ALL">所有设备</option>
								<option value="IOS">IOS平台</option>
								<option value="ANDROID">Android平台</option>
								
							</select>
							
						</td>
						<td>
							接收群体：
						</td>
						<td>
							<select id="msgSentUserGroup" name="msgSentUserGroup"  onchange="showTab()" style="width: 120px;"> 
								
								<option value="2" selected = "selected">所有用户</option>
								<option value="3" >指定公寓</option>
								<option value="4" >指定用户</option>
								<!-- <option value="2">配送人员</option>
								<option value="3">商家</option> -->
							</select>
						</td>
						<td style="display: none;">
							消息类型：
						</td>
						<td style="display: none;">
							<select name="msgType">
								<option value="0">-请选择-</option>
								<!-- <option value="1">短信消息</option> -->
								<option value="3" selected = "selected">推送信息</option>
							</select>
						</td>
					</tr>
					<tr style="display: none;">
						
						<td>
							发送方式：
						</td>
						<td>
							<select name="msgSentDelay" onchange="messageAction.judgeIsSentDelay(this);">
								<!-- 
								<option value="">-请选择-</option>
								<option value="1">延迟发送</option>
								 -->
								<option value="0" selected = "selected">立即发送</option>
							</select>
						</td>
					</tr>
					
					<tr >
					  
						<td class="partInfo" style="display: none;">公寓选择：</td>
						<td class="partInfo" style="display: none;">
								 <input id='partId' name="partId"  type="text"  editable="false" class='easyui-combobox' style="width: 120px" />
								<font color='red'>*</font>
						</td>
						
						
						<td class="userTelInfo" style="display: none;" >
							用户手机号码：
						</td>
						<td class="userTelInfo" style="display: none;">
							<input type="text" id="userPhone" name="userPhone" style="width: 120px;" /><font color='red'>*</font>
						</td>
						
					</tr>
					<tr id="TR_msgSentDelayTime">
						<td>
							生效时间：
						</td>
						<td >
							<input class="Wdate"  type="text" name="msgSentDelayTime" style="width: 120px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /><font color='red'>*</font>
						</td>
						
					</tr>
					<tr>
						
						<td>
							消息标题：
						</td>
						<td colspan="3">
							<input type="text" id="msgTitle" name="msgTitle" style="width: 380px;" /><font color='red'>*</font>
						</td>
					</tr>
					<tr>
						<td>
							消息内容：
						</td>
						<td colspan="3">
							<textarea name="msgContent" id="msgContent" style="width: 380px; height:100px; resize:none;"></textarea><font color='red'>*</font>
						</td>
					</tr>
					<tr id="TR_STATUS" style="display: none;">
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