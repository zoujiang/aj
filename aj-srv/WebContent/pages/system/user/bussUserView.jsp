<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>区域详情</title>
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
	    var $backButton = $('#back');
		var $nextButton = $('#next');
		var $saveButton = $('#save');
		var $submitAppButton = $('#submitApp');
		var initParamter = function() {
			//查询填充表达
			$.post(mainWeb + "/admin/area/findArea",{"id":id},
				function(data){
					var areaVo=data;
					if (!areaVo) return;
					$("#areaId").val(areaVo.areaId);
					$("#areaName").val(areaVo.areaName);
					$("#createUser").val(areaVo.createUser);
					$("#createDt").val(areaVo.createDt);
					$("#modifyUser").val(areaVo.modifyUser);
					$("#modifyDt").val(areaVo.modifyDt);
					$("#areaOrder").val(areaVo.areaOrder);
					$("input[name='isValid'][type='radio']").removeAttr("checked");
					if("1"==areaVo.isValid){
						$("input[name='isValid'][type='radio'][value='1']").attr("checked",true);
					}else{
						$("input[name='isValid'][type='radio'][value='0']").attr("checked",true);
					}
					if(null!=areaVo.areaPic && ""!=areaVo.areaPic){
						$('#picViewload1').click(function(event){previewImage(event,areaVo.areaPic)});
						$('#picViewload1').show();
					}
				},"json");
			//选项卡点击
			$('#activityTabs').click(function(){
				var tab = $('#activityTabs').tabs('getSelected'); 
				var index = $('#activityTabs').tabs('getTabIndex',tab);
				if(index==0){
					$nextButton.show();
					$backButton.hide();
					$saveButton.hide();
					$submitAppButton.hide();
				}
				if(index==1){
					$nextButton.hide();
					$backButton.show();
					$saveButton.removeAttr("disabled");
					$submitAppButton.removeAttr("disabled");
					$saveButton.show();
					$submitAppButton.show();
				}
			});
			//上一步
			$backButton.click(function(){
				var tab = $('#activityTabs').tabs('getSelected'); 
				var index = $('#activityTabs').tabs('getTabIndex',tab);
				if(index==1){
					$nextButton.show();
					$backButton.hide();
					$saveButton.hide();
					$submitAppButton.hide();
					$('#activityTabs').tabs('select',0);
				}
			});
			//下一步
			$nextButton.click(function(){
				var tab = $('#activityTabs').tabs('getSelected'); 
				var index = $('#activityTabs').tabs('getTabIndex',tab);
				if(index==0){
					if(validate()){
						$nextButton.hide();
						$backButton.show();
						$saveButton.show();
						$submitAppButton.show();
						$('#activityTabs').tabs('select',1);
						return true;
					}else{
						return false;
					}
				}
			});
			//保存
			$saveButton.click(function(){
				window.close();
			});
		};
		initParamter();
	}
	
	function validate(){
		return true;
	};
	
	$(document).ready(function () {
	    var obj = window.dialogArguments;
		new AreaOpt(obj.areaId,obj.editType);
		com.eason.form.disableForm("activityForm",true);
	});
	</script>		
	</head>
	<body style="padding: 0;margin: 0" >
		<form id='activityForm' method='POST' enctype='multipart/form-data'>
		<div id="activityTabs" class="easyui-tabs" style="height: 450px ;">
		<div title="一：区域基础信息" style="padding: 5px 10px;">
				<input type="hidden" id="base_validate" value="0" />
				<div style='padding: 5px 20px;'>
					<table style='padding: 10px 30px;'>
						<tr style='height: 30px;'>
							<td>区域ID：</td>
							<td>
								<input type='text' id='areaId' name="areaId" disabled="disabled" style="width:160px;height:25px" />
							</td>
							<td>区域名称：</td>
							<td>
								<input type='text' id='areaName' name="areaName" style="width:160px;height:25px" />
								<font color='red'>*</font>
							</td>
						</tr>	
						<tr style='height: 30px;'>
							<td>显示排序：</td>
							<td>
								<input type='text' id='areaOrder' name="areaOrder" style="width:160px;height:25px" />
							</td>
							<td>是否生效：</td>
							<td>
								<label style="width: 100px; text-align: left; padding-right: 5px;"></label>
								<input name="isValid" type="radio" value="1" checked="checked"/>是
							  	<input name="isValid" type="radio" value="0"/>否
								<font color='red'>*</font>
							</td>
						</tr>
						<tr style='height: 30px;'>
							<td>创建人：</td>
							<td>
								<input type='text' id='createUser' name="createUser" disabled="disabled" style="width:160px;height:25px" />
							</td>
							<td>创建时间：</td>
							<td>
								<input class="Wdate" type='text' id='createDt' name="createDt" readonly="true" disabled="disabled" style="width:160px;height: 25px" 
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'startDt\',{s:-1});}'})" />
							</td>
						</tr>
						<tr style='height: 30px;'>
							<td>修改人：</td>
							<td>
								<input type='text' id='modifyUser' name="modifyUser" disabled="disabled" style="width:160px;height:25px" />
							</td>
							<td>修改时间：</td>
							<td>
								<input class="Wdate" type='text' id='modifyDt' name="modifyDt" disabled="disabled" readonly="true" style="width:160px;height: 25px" 
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'startDt\',{s:-1});}'})" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div title="二：图片链接 " style="padding: 10px">
           		<div style='padding: 5px 20px;'>
					<table style='padding: 10px 30px;'>
						<tr style='height: 30px;'>
							<td>区域图片：</td>
							<td colspan='3'>
								<input type='file' name='areaPicFile'  id='areaPicFile' title="规格：245*150的等比例尺寸,如720*440" style="height: 20px; width: 200px" />
								<span id="picViewload1" title="点击预览图片" style="display: none">点击预览图片</span>
							</td>
						</tr>
				</table>
				</div>
		    </div>	
		</div>
		<div style="heigth: 25px; padding: 10px 0 0 0; text-align: right;">
			<a href="#" style="margin-right: 10px;display: none;" id="back" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">上一步</a>
			<a href="#" style="margin-right: 10px;" id="next" class="easyui-linkbutton" data-options="iconCls:'icon-redo'">下一步</a>
			<a href="#" style="margin-right: 10px;display: none;" id="save" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">关闭</a>
		</div>
	</form>
	</body>
</html>