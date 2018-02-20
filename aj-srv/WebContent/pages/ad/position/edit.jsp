<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广告位管理</title>
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
			
			if (!id) {
				//alert("新增");
				//return;
			} else {
				//修改，获取值
				$.post(mainWeb + "/admin/position/detail",{"id":id},
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
						
						$('#id').val(data.id);
						$('#name').val(data.name);
						$('#image').val(data.image);
						
						$("input[name='type'][value='" + data.type + "']").attr("checked",true); 
						//$("input[name='position'][value='" + data.position + "']").attr("checked",true); 
						$('#page').val(data.page);
						
					},"json");
			
			}
			
			//确认
			$okButton.click(function(){
				if(!validate()) {
					return;
				}
				
				
				$.messager.confirm('提示','是否确定保存？',function(r){
					if(r){
						var param = $("#activityForm").serialize();
						
						//alert(param.id);
						
						//param.name = $("#name").val();
						//alert(JSON.stringify(param));
						
						$.ajax({
							type: 'POST',
							url: mainWeb + "/admin/position/save",
							//data: 'id=' + Pub_Id + "&logisName=" + $("#logisName").val() + "&logisNum=" + $("#logisNum").val(),
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
		var flag = //com.eason.check.easyui.isCheckedTrue($("input[name='position']"),"位置") && 
					//com.eason.check.easyui.isCheckedTrue($("input[name='type']"),"类型")  && 
					com.eason.check.easyui.isEmpty($("#page"),"页面") && 
		 			com.eason.check.easyui.limitLength($("#page"),"页面",30) && 
		 			com.eason.check.easyui.isEmpty($("#image"),"预览图片");
		
		return flag;
	};
	
	function makeName() {
		<%--
		var pos_text = $("input[name='position']:checked").next("label").text();
		var type_text = $("input[name='type']:checked").next("label").text();
		var app_text = "鹿象APP";
		
		var page_text = $("#page").val();
		$("#name").val(app_text + "-" + page_text + "(" + pos_text + ")" + type_text);
		--%>
		var app_text = "鹿象APP";
		var page_text = $("#page").val();
		var type_text = $("input[name='type']:checked").next("label").text();
		
		$("#name").val(app_text + "-" + page_text + "(" + type_text + ")");
	};
	
	var Pub_Id;
	
	$(function() {
	    var obj = window.dialogArguments;
	    Pub_Id = obj.id;
		new PageOpt(obj.id);
		
		$("input[type='radio']").change(function() {
			makeName();
		});
		$("#page").keyup(function() {
			makeName();
		}); 
		
	});
	</script>		
	</head>
	<body style="padding: 0;margin: 0" >
		<form id='activityForm' method='POST' enctype='multipart/form-data'>
		<div id="activityTabs" class="easyui-tabs" style="height: 450px ;">
		<div title="广告位管理" style="padding: 5px 10px;">
		    	<input type="hidden" id="id" name="id"/>
				<div style='padding: 5px 20px;'>
					<table style='padding: 10px 30px;'>
						<tr style='height: 30px;'>
							<td height="30" align="right">广告位类型：</td>
							<td height="30">
								<input type="radio" name="type" value="ios" id="radio3" checked/><label for="radio3">IOS</label>
			   					<input type="radio" name="type" value="android" id="radio4"/><label for="radio4">Android</label>
			   					<input type="radio" name="type" value="wxh5" id="radio5"/><label for="radio5">微信H5</label>
							</td>
						</tr>
						<%--
						<tr style='height: 30px;'>
							 <td height="30" align="right">位置：</td>
						     <td height="30">
					        	<input type="radio" name="position" id="radio8" value="top" checked/><label for="radio8">上</label> 
					       		<input type="radio" name="position" id="radio10" value="bottom" /><label for="radio10">下</label> 
					        </td>
							        
						</tr>	
						 --%>
						
						<tr style='height: 30px;'>
						    <td height="30" align="right">页面：</td>
						    <td height="30" >
						    	<input type='text' id='page' name="page" style="width:150px;height:25px" />
						    </td>
						</tr>	
						<tr>
						    <td height="35" align="right">预览图片</td>
						    <td height="35" >
						       <input  readonly  name="image" type="text" id="image" style="border:1px solid #ccc; width:150px;"/>
						       <a onclick="fileUpload('image',event)">上传</a>&nbsp;&nbsp;
						       <a onclick="viewFile('image',event)"  style="color:blue;cursor:pointer">预览</a>
						   <a onclick="deleteFile('image',event)" style="color:blue;cursor:pointer">删除</a>
						    </td>
						</tr>
						
						<tr style='height: 30px;'>
							<td height="30" align="right">广告位名称：</td>
							<td>
								<%--
								 <td height="30" colspan="2">页面名称-首页（上）android</td>
								 --%>
								<input type='text' id='name' name="name" readonly style="width:200px;height:25px,border:0px solid #ccc; " />
								<span id="name"></span>
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