<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广告管理-文件上传</title>
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
	function PageOpt() {
	    var $backButton = $('#back');
		var $saveButton = $('#ok');
		
		//保存
		$saveButton.click(function(){
			
			$.messager.progress({
				title:'温馨提示',
				text:'<font style="font-size:11px">正在提交请求,请勿关闭页面...</font>',
				interval:'150'
			});
			
			$('#activityForm').form('submit', {
				url:mainWeb + "/service/pub/fileUpload",
				onSubmit: function(param){
					if(validate()){
						$(this).form("validate");
						//$saveButton.attr("disabled","true");
						$saveButton.hide();
						return true;
					}
					$.messager.progress('close');
					return false;
				},
				success:function(data){
					
					$.messager.progress('close');
					data=$.trim(data.replace(/<\/?.+?>/g,""));
					
					var msg = JSON.parse(data);
					if (msg.error ) {
						$.messager.alert('提示', msg.msg);
						$saveButton.show();
						return;
					}
					
					page.url = msg.obj;
					//上传成功
					window.returnValue=JSON.stringify(page);//返回给父页面处理
			    	window.close();
				}
			});
		});
			
		//返回
		$backButton.click(function(){
			window.returnValue="";
	    	window.close();
		});
				
	};
		
	
	
	
	function validate() {
		var flag = ""; 
		
		return true;
	};
	
	
	$('#activityForm').ajaxStart(function(){
			$.messager.progress({
				title:'温馨提示',
				text:'<font style="font-size:11px">正在提交请求,请勿关闭页面...</font>',
				interval:'150'
			});
	   }).ajaxStop(function() {
		   $.messager.progress('close');
	   });
	
	
	var page = {};
	
	$(function() {
	    var obj = window.dialogArguments;
	    page.domid = obj.domid;
	    
		new PageOpt();
		
	});
	</script>		
	</head>
	<body style="padding: 0;margin: 0" >
		<form id='activityForm' method='POST' enctype='multipart/form-data'>
		<div id="activityTabs" class="easyui-tabs" style="height: 150px ;">
			<div title="文件上传" style="padding: 5px 10px;">
				<div style='padding: 5px 20px;'>
					<table style='padding: 10px 30px;'>
						<tr style='height: 30px;'>
							<td height="30" align="right">文件(大小1M以内)：</td>
							<td height="30">
								<input type='file' name='file'  id='file' style="height: 25px; width: 150px" />
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
	</body>
</html>