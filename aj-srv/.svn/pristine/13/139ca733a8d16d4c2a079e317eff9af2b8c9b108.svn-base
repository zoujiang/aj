<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>客户端升级管理</title>
<script type="text/javascript" src="<%=mainWeb%>/scripts/eason.js"></script>
<script type="text/javascript" src="<%=mainWeb%>/scripts/json.js"></script>
<script type="text/javascript" src="<%=mainWeb%>/scripts/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=mainWeb%>/scripts/ad/upload.js"></script>

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
		var initParamter = function() {
			
			if (id) {
				//修改，获取值
				$.post(mainWeb + "/admin/edit/findEdit",{"id":id},
					function(rel){
						if (!rel) {
							rel ={};
							rel.result = true;
							rel.message = "没得到返回值";
						}
						
						if (!rel.result) {
							$.messager.alert('提示', rel.message, 'info', function() {
								window.returnValue="success";//刷新父页面
						    	window.close();
							});
							return;
						}
						var data = rel.returnObj;
						
						$('#id').val(data.id);
						$('#model').val(data.model);
						$('#clientver').val(data.clientver);
						$('#channel_id').val(data.channel_id);
						$('#clienturl').val(data.clienturl);
						//$('#property').val(data.property);
						$('#status').val(data.status);
						$('#forceupdate').val(data.forceupdate);
						$('#updatecontent').val(data.updatecontent);
					},"json");
			}
			
			//确认
			$okButton.click(function(){
				if(!validate()) {
					return;
				}
				var editUrl = "";
				if (id) {
					editUrl = mainWeb + "/admin/edit/editEdit";
				}else{
					editUrl = mainWeb + "/admin/edit/addEdit";
				}
				$.messager.confirm('提示','是否确定保存？',function(r){
					if(r){
						var param = $("#activityForm").serialize();
						$.ajax({
							type: 'POST',
							url: editUrl,
							data: param,
							dataType: 'json',
							success: function(data) {
								if (!data) {
									$.messager.alert('提示', "访问异常", 'info');
									return;
								}
								$.messager.alert('提示', data.message, 'info', function() {
									if (data.result) {
										window.returnValue="success";//刷新父页面
								    	window.close();
									}
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
		var flag =  
		com.eason.check.easyui.isEmpty($("#clientver"),"客户端版本号") && 
		com.eason.check.easyui.isEmpty($("#model"),"版本模式") &&
		com.eason.check.easyui.isEmpty($("#status"),"状态") &&
		com.eason.check.easyui.isEmpty($("#forceupdate"),"是否强制更新") &&
		com.eason.check.easyui.limitLength($("#clienturl"),"客户端程序URL",2048) &&
		com.eason.check.easyui.isEmpty($("#clienturl"),"客户端程序URL") && 
		com.eason.check.easyui.isEmpty($("#updatecontent"),"客户端升级内容说明");
		return flag;
	};
	
	var Pub_Id;
	
	$(function() {
	    var obj = window.dialogArguments;
	    Pub_Id = obj.id;
		new PageOpt(obj.id);
	});
	</script>		
	</head>
	<body style="padding: 0;margin: 0" >
		<form id='activityForm' method='POST' enctype='multipart/form-data'>
		<div id="activityTabs" class="easyui-tabs" style="height: 450px ;">
		<div title="客户端升级管理" style="padding: 5px 10px;">
		    	<input type="hidden" id="id" name="id"/>
		    	<input type='hidden' id='channel_id' name="channel_id" value="100" style="width:150px;height:25px" />
				<div style='padding: 5px 20px;'>
					<table style='padding: 10px 30px;'>
						<tr style='height: 30px;'>
							<td height="30" align="right">版本模式：</td>
							<td height="30">
								<select id="model" name="model" style="width:150px;height:25px">
								 	<option value="" selected="selected">请选择...</option>
								    <option value="11">安卓用户端</option>
								    <option value="12">IOS用户端</option>
								    <option value="21">安卓商户端</option>
								    <option value="22">IOS商户端</option>
								    <option value="31">安卓配送端</option>
								    <option value="32">IOS配送端</option>
								 </select>
								 <font color='red'>*</font>
							</td>
						</tr>
						
						<tr style='height: 30px;'>
						    <td height="30" align="right">客户端版本号：</td>
						    <td height="30" >
						    	<input type='text' id="clientver" name="clientver" style="width:150px;height:25px" />
						    <font color='red'>*三位数字中间用‘.’隔开,如:1.0.1,</font>
						    </td>
						   
						</tr>	
						
						<tr style='height: 30px;'>
						    <td height="30" align="right">客户端程序URL：</td>
						    <td height="30" >
						    	<input type='text' id='clienturl' name="clienturl" style="width:300px;height:25px" />
						    	 <font color='red'>*绝对地址，如http://xxx.com/62?mt=8</font>
						    
						    </td>
						</tr>	
						
						<!-- <tr style='height: 30px;'>
						    <td height="30" align="right">包属性：</td>
						    <td height="30" >
						    	<input type='text' id='property' name="property" style="width:150px;height:25px" />
						    </td>
						</tr>	 -->
						
						<tr style='height: 30px;'>
						    <td height="30" align="right">状态：</td>
						    <td height="30" >
						    	<select id="status" name="status" style="width:150px;height:25px">
								    <option value="" selected="selected">请选择...</option>
								    <option value="1">上线</option>
								    <option value="0">下线</option>
								 </select>
								  <font color='red'>*</font>
						    </td>
						</tr>
						
						<tr style='height: 30px;'>
						    <td height="30" align="right">是否强制更新：</td>
						    <td height="30" >
						    	<select id="forceupdate" name="forceupdate" style="width:150px;height:25px">
								    <option value="" selected="selected">请选择...</option>
								    <option value="Y">是</option>
								    <option value="N">否</option>
								    
								 </select>
								  <font color='red'>*</font>
						    </td>
						</tr>
						
						<tr style='height: 30px;'>
						    <td height="30" align="right">客户端升级内容：</td>
						    <td height="30" >
						    	<textarea id='updatecontent' name="updatecontent"  style="width: 400px; height:100px; resize:none;"></textarea>
						     <font color='red'>*</font>
						    </td>
						   
						</tr>	
						
					</table>
				</div>
			</div>
		   
		</div>
		<div style="heigth: 25px; padding: 10px 0 0 0; text-align: right;">
			<a href="javascript:void(0)" style="margin-right: 10px;" id="ok" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
			 
			<a href="javascript:void(0)" style="margin-right: 10px;" id="back" class="easyui-linkbutton" data-options="iconCls:'icon-back'">返回</a>
		</div>
	</form>
	</body>
</html>