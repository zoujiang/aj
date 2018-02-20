<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广告管理</title>
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
		var initParamter = function() {
			
			if (!id) {
				//alert("新增");
				//return;
			} else {
				//修改，获取值
				$.post(mainWeb + "/admin/ad/detail",{"id":id},
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
						
						var types = new Array();
						
						if (data.type.indexOf("ios") > -1) {
							types.push("ios");
						}
						if (data.type.indexOf("android") > -1) {
							types.push("android");
						}
						if (data.type.indexOf("wxh5") > -1) {
							types.push("wxh5");
						}
						
						$('#type').combobox("setValues", types);
						
						$("#online_timeStr").datetimebox("setValue", data.online_timeStr);
						$("#offline_timeStr").datetimebox("setValue", data.offline_timeStr);
						
						
					},"json");
			
			}
			
			//确认
			$okButton.click(function(){
				
				//alert($('#type').combobox('getValues'));
				
				if(!validate()) {
					return;
				}
				
				
				$.messager.confirm('提示','是否确定保存？',function(r){
					if(r){
						var param = "id=" + $("#id").val() + 
									  "&name=" + $("#name").val() + 
									  "&type=" + $('#type').combobox("getValues") + 
									  "&online_timeStr=" + $("#online_timeStr").datetimebox("getValue") + 
									  "&offline_timeStr=" + $("#offline_timeStr").datetimebox("getValue");
						
						$.ajax({
							type: 'POST',
							url: mainWeb + "/admin/ad/save",
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
					com.eason.check.easyui.isEmpty($("#name"),"广告名称") && 
		 			com.eason.check.easyui.limitLength($("#name"),"广告名称",30);
					//com.eason.check.easyui.checkStartEndTime($("#online_timeStr"),"开始时间", $("#offline_timeStr"),"结束时间"); 
		
		if (!flag) {
			return false;
		}
		
		var type = $('#type').combobox("getValues");
		if(type==null || type==""){
			$.messager.alert("警告","请选择【广告类型】");
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
		
		flag = com.eason.check.compareTime(startTime,endTime);
		if (flag == "-1") {
			$.messager.alert("警告","【开始时间】必须早于【结束时间】");
			return false;
		}
		
		return flag;
	};
	
	
	
	
	var Pub_Id;
	
	$(function() {
	    var obj = window.dialogArguments;
	    Pub_Id = obj.id;
		new PageOpt(obj.id);
	 
		$('#type').combobox({editable:false,multiple:true}); 
		
		var dataParam = {editable:false, showSeconds:false};
		
		
		$('#online_timeStr').datetimebox(dataParam); 
		$('#offline_timeStr').datetimebox(dataParam); 
		
		
		//$("#type").combobox("select",'ios');
		//$("#type").combobox("select",'android');
		
		
		
		
	});
	</script>		
	</head>
	<body style="padding: 0;margin: 0" >
		<form id='activityForm' method='POST' enctype='multipart/form-data'>
		<div id="activityTabs" class="easyui-tabs" style="height: 450px ;">
		<div title="广告管理" style="padding: 5px 10px;">
		    	<input type="hidden" id="id" name="id"/>
				<div style='padding: 5px 20px;'>
					<table style='padding: 10px 30px;'>
						<tr style='height: 30px;'>
							<td height="30" align="right">广告名称：</td>
							<td height="30">
								<input type='text' id='name' name="name" style="width:150px;height:25px" />
							</td>
						</tr>
						
						<tr style='height: 30px;'>
							 <td height="30" align="right">终端类型：</td>
						     <td height="30">
						     	 <select class="easyui-combobox" id="type" name="type" style="width:150px;">
									<option value="ios">IOS</option>
									<option value="android">Android</option>
									<option value="wxh5">微信H5</option>
								</select>

								<%--
					        	<input type="checkbox" name="type" id="type_ios" value="ios"/><label for="type_ios">IOS</label>&nbsp;&nbsp;
		    					<input type="checkbox" name="type" id="type_android" value="android"/><label for="type_android">Android</label>
		    					
		    					<input type="hidden" name="ad.type" id="ad_type"/>
								 --%>
					        </td>
							        
						</tr>	
						
						<tr style='height: 30px;'>
						    <td height="30" align="right">开始时间：</td>
						    <td height="30" >
						    	<input type='text' id='online_timeStr' name="online_timeStr" style="width:150px;height:25px" />
						    	结束时间：
						    	<input type='text' id='offline_timeStr' name="offline_timeStr"  style="width:150px;height:25px" />
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