<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/incold.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广告内容</title>
<script type="text/javascript" src="<%=mainWeb%>/scripts/eason.js"></script>
<script type="text/javascript" src="<%=mainWeb%>/scripts/json.js"></script>
<script type="text/javascript" src="<%=mainWeb%>/scripts/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=mainWeb%>/scripts/ad/content_relation.js"></script>
<%--
 --%>
<script type="text/javascript" src="<%=mainWeb%>/scripts/ad/upload.js"></script>
<style type="text/css">
	#activityTabs tr.dateTr td.labletd {
		width: 75px;
	}
	#activityTabs tr.dateTr .Wdate {
		width: 120px;
	}
</style>	
<script type="text/javascript" charset="utf-8">
	function PageOpt() {
	    var $backButton = $('#back');
		var $okButton = $('#ok');
		var initParamter = function() {
			
			
			$('#id').val(page.id);
			$('#adid').val(page.adid);
			$('#position_type').val(page.posType);
			
			//alert(JSON.stringify(page));
			
			//page.action = "add";
			
			if ("add" == page.action) {
				$('#activityTabs').tabs('close', 1);
			} else {
			
				//修改，获取值
				$.post(mainWeb + "/admin/ad/content",{"id":page.id},
					function(rel){
						if (!rel) {
							rel ={};
							rel.error = true;
							rel.msg = "没得到返回值";
						}
						
						if (rel.error) {
							$.messager.alert('提示', rel.msg, 'info', function() {
								//window.returnValue="success";//刷新父页面
						    	window.close();
							  });
							return;
						}
						
						var data = rel.obj;
						$('#position_type').val(data.position_type);
						//$('#type').combobox("setValue", data.type);
						$('#text').val(data.text);
						$('#url').val(data.url);
						$('#video').val(data.video);
						$('#img_small').val(data.img_small);
						$('#img_big').val(data.img_big);
						$('#relation_id').val(data.relation_id);
						$('#relation_title').val(data.relation_title);
						$('#id').val(data.id);
						$('#adid').val(data.adid);
						
						$('#type').combobox("select", data.type);
						
						page.posType = data.position_type;
						
						loadRelations(); //加载投放关系
						loadPostion();   //加载广告位
						
					},"json");
			}	
			
			//确认
			$okButton.click(function(){
				
				if(!validate()) {
					return;
				}
				
				var _msg = "是否确定保存？";
				if (page.action == "edit") {
					//_msg = "修改之后，广告位上的广告将会下线。<br/>是否确定修改？不修改请直点击‘返回’";
					_msg = "是否确定修改？不修改请直点击‘返回’";
				}
				
				postParam = $("#activityForm").serialize();
				
				$.messager.confirm('提示',_msg, function(r){
					if(r){
						
						$.ajax({
							type: 'POST',
							url: mainWeb + "/admin/ad/saveContent",
							data: postParam,
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
					window.returnValue="success";//刷新父页面
			    	window.close();
				});
				
				//选项卡点击
				$('#activityTabs').click(function(){
					var tab = $('#activityTabs').tabs('getSelected'); 
					var index = $('#activityTabs').tabs('getTabIndex',tab);
					if(index==0){
						//$backButton.show();
						$okButton.show();
					}
					if(index==1){
						//$backButton.hide();
						$okButton.hide();
						//$("#table_adrelation").datagrid("resizeHandle");
						
					}
				});
				
				
				PageOpt.fileUpload = function(domid) {
					
					//alert("upload:" + domid );
					var obj = new Object();
					obj.domid = id;
					
					com.eason.window.winUtils.showDialogWindowOfWH(400,200,e,mainWeb + "/pages/ad/pub/upload.jsp",obj,function(returnValue){
						
						if (returnValue == "" || returnValue == null  || returnValue == undefined) {
							return;
						}
						
						//alert("--save:" + returnValue);
						
						
					});
				};
				
				
		};
		
		initParamter();
	}
	
	
	var postParam;
	
	function validate() {
		var flag = true;
					//com.eason.check.easyui.isEmpty($("#name"),"广告名称") && 
		 			//com.eason.check.easyui.limitLength($("#name"),"广告名称",30);
		
		if (!flag) {
			return false;
		}
		
		var type = $('#type').combobox("getValues");
		if(type==null || type==""){
			$.messager.alert("警告","请选择【内容类型】");
			return false;
		}
		
		postParam = "type=" + type + "&position_type=" + $("#position_type").val();
		
		if (type == "text") {
			
			flag = com.eason.check.easyui.isEmpty($("#text"),"文字内容") && 
 					com.eason.check.easyui.limitLength($("#text"),"文字内容",300);
			if (!flag) {
				return false;
			}
 					
			postParam += "&text=" + $("#text").val();
			
			$("#url").val('');
			$("#video").val('');
			$("#relation_id").val('');
			$("#relation_title").val('');
			
		} else if (type == "url") {
			flag = com.eason.check.easyui.isEmpty($("#url"),"网址链接") && 
				com.eason.check.easyui.limitLength($("#url"),"网址链接",300);
			if (!flag) {
				return false;
			}
			
			flag = com.eason.check.easyui.isEmpty($("#img_small"),"小图地址") && 
				com.eason.check.easyui.limitLength($("#img_small"),"小图地址",300)&&
				com.eason.check.easyui.isEmpty($("#img_big"),"大图地址") && 
				com.eason.check.easyui.limitLength($("#img_big"),"大图地址",300);
			if (!flag) {
			 	return false;
			}
			 
			postParam += "&url=" + $("#url").val();
			
			$("#video").val('');
			$("#relation_id").val('');
			$("#relation_title").val('');
			$("#text").val('');
	
		} else if (type == "video") {
			flag = com.eason.check.easyui.isEmpty($("#video"),"视频地址") && 
				com.eason.check.easyui.limitLength($("#video"),"视频地址",300);
			if (!flag) {
				return false;
			}
			flag = com.eason.check.easyui.isEmpty($("#img_small"),"小图地址") && 
				com.eason.check.easyui.limitLength($("#img_small"),"小图地址",300)&&
				com.eason.check.easyui.isEmpty($("#img_big"),"大图地址") && 
				com.eason.check.easyui.limitLength($("#img_big"),"大图地址",300);
			if (!flag) {
			 	return false;
			}
			postParam += "&video=" + $("#video").val();
			
			$("#url").val('');
			$("#relation_id").val('');
			$("#relation_title").val('');
			$("#text").val('');
		} else if (type == "img") {
			 flag = com.eason.check.easyui.isEmpty($("#img_small"),"小图地址") && 
				com.eason.check.easyui.limitLength($("#img_small"),"小图地址",300)&&
				com.eason.check.easyui.isEmpty($("#img_big"),"大图地址") && 
				com.eason.check.easyui.limitLength($("#img_big"),"大图地址",300);
			 if (!flag) {
			 	return false;
			 }
			 
			//postParam += "&img_small=" + $("#img_small").val() + "&img_big=" + $("#img_big").val();
			
			 $("#url").val('');
			 $("#video").val('');
			 $("#relation_id").val('');
			 $("#relation_title").val('');
			 $("#text").val('');
				
		} else if (type == "product" || type == "merchant") {
			
			flag = com.eason.check.easyui.isEmpty($("#img_small"),"小图地址") && 
				com.eason.check.easyui.limitLength($("#img_small"),"小图地址",300)&&
				com.eason.check.easyui.isEmpty($("#img_big"),"大图地址") && 
				com.eason.check.easyui.limitLength($("#img_big"),"大图地址",300)&&
				com.eason.check.easyui.isEmpty($("#relation_id"),"ID") && 
				com.eason.check.easyui.limitLength($("#relation_id"),"ID",50)&&
				com.eason.check.easyui.isEmpty($("#relation_title"),"名称") && 
				com.eason.check.easyui.limitLength($("#relation_title"),"名称",30);
			if (!flag) {
			 	return false;
			}
			 
			//postParam += "&img_small=" + $("#img_small").val() + "&img_big=" + $("#img_big").val();
			postParam += "&relation_id=" + $("#relation_id").val() + "&relation_title=" + $("#relation_title").val();
			
			$("#url").val('');
			$("#video").val('');
			$("#text").val('');
			
		}
		postParam += "&img_small=" + $("#img_small").val() + "&img_big=" + $("#img_big").val();
		 
		postParam += "&id=" + $("#id").val() + "&adid=" + $("#adid").val();
		
		return flag;
	};
	
	

	
	
	var Pub_Id;
	var page = {};
	
	$(function() {
	    var obj = window.dialogArguments;
	    page.id = obj.id;
	    page.adid = obj.adid;
	    page.action = obj.action;
	    page.posType = obj.posType;
	    
		new PageOpt();
		
		$('#type').combobox({
			 editable:false,
			 onSelect: function(rec){
				 
				 //alert(rec.value);
				 var val = rec.value;
					
				 if (val == "text") {
					 $("#_text").show();
					 $("#_text").siblings().hide();
					 $("#_img_big").show();
					 $("#_img_small").show();
					 $(".c_for_img").show();
				 } else if (val == "url") {
					 $("#_url").show();
					 $("#_url").siblings().hide();
					 $("#_img_big").show();
					 $("#_img_small").show();
					 $(".c_for_img").show();
				 } else if (val == "video") {
					 $("#_video").show();
					 $("#_video").siblings().hide();
					 $("#_img_big").show();
					 $("#_img_small").show();
					 $(".c_for_img").show();
				 } else if (val == "img") {
					 $("#_img_big").siblings().hide();
					 $("#_img_big").show();
					 $("#_img_small").show();
					 $(".c_for_img").show();
				 } else if (val == "product" || val == "merchant") {
					 
					 $("#_relation").show();
					 $("#_relation").siblings().hide();
					 $("#_img_big").show();
					 $("#_img_small").show();
					 //$(".c_for_img").show();
					 if (val == "product") {
						 $(".for_product").show();
						 $(".for_merchant").hide();
					 }
					 if (val == "merchant") {
						 $(".for_merchant").show();
						 $(".for_product").hide();
					 }
				 }
				 
				// alert(JSON.stringify(rec));
			 }
		}); 
		
	});
	</script>		
	</head>
	<body style="padding: 0;margin: 0" >
		<form id='activityForm' method='POST' enctype='multipart/form-data'>
		<div id="activityTabs" class="easyui-tabs" style="height: 450px ;">
		<div title="广告内容" style="padding: 5px 10px;width:650px">
		    	<input type="hidden" id="id" name="id"/>
		    	<input type="hidden" id="adid" name="adid"/>
		    	<input type="hidden" id="position_type" name="position_type"/>
				<div style='padding: 5px 20px;'>
					<table style='padding: 10px 30px;'>
						
						<tr style='height: 30px;'>
							 <td height="30" align="left">内容类型：</td>
						     <td height="30" align="left">
						     	 <select class="easyui-combobox" id="type" name="type" style="width:150px;">
									<option value="url">网址链接</option>
									<option value="img">图片</option>
									<%-- 
									<option value="text">文字</option>
									<option value="video">视频</option>
									<option value="product">商品</option>
									<option value="merchant">商家</option>
									 --%>
								 </select>
					        </td>
						</tr>
						<tr>
							<td colspan="2">
								<table border="0" cellspacing="0" cellpadding="0">
									<tr id="_text">
									    <td height="35" align="left">输入文字：</td>
									    <td height="35" >
										    <textarea id="text" name="text" style="border:1px solid #ccc; width:350px;"></textarea>
									    </td>
									</tr>
									<tr id="_url" style="display:none;">
									    <td height="35" align="left">网址链接：</td>
									    <td height="35">
										    <input name="url" type="text" id="url" style="width:350px; height:24px; line-height:24px; border:1px solid #ccc" />
									    </td>
									</tr>
									<tr id="_video" style="display:none;">
									    <td height="35" align="left">视频地址：</td>
									    <td height="35" >
										     <input readonly name="video" type="text" id="video" style="width:350px; height:24px; line-height:24px; border:1px solid #ccc" />
										     <a onclick="fileUpload('video',event)">上传</a>&nbsp;&nbsp;
									         <a onclick="viewFile('video',event)" style="color:blue;cursor:pointer">预览</a>
									         <a onclick="deleteFile('video',event)" style="color:blue;cursor:pointer">删除</a>
									    </td>
									</tr>
									<tr id="_img_small">
									    <td height="35" align="left">上传图片(小)：</td>
									    <td height="35" >
									    	<%--
									       <input  readonly name="img_small" type="text" id="img_small" style="width:350px; height:24px; line-height:24px; border:1px solid #ccc" value="请上传宽度为480像素的图片" />
									    	 --%>
									       <input  readonly  name="img_small" type="text" id="img_small" style="border:1px solid #ccc; width:350px;"/>
									       <a onclick="fileUpload('img_small',event)">上传</a>&nbsp;&nbsp;
									       <a onclick="viewFile('img_small',event)" style="color:blue;cursor:pointer">预览</a>
									        <a onclick="deleteFile('img_small',event)" style="color:blue;cursor:pointer">删除</a>
									    </td>
									</tr>
									<tr id="_img_big">
									    <td height="35" align="left">上传图片(大)：</td>
									    <td height="35">
									       <input  readonly name="img_big" type="text" id="img_big" style="border:1px solid #ccc; width:350px;" />
									       <a onclick="fileUpload('img_big',event)">上传</a>&nbsp;&nbsp;
									       <a onclick="viewFile('img_big',event)" style="color:blue;cursor:pointer">预览</a>
									       <a onclick="deleteFile('img_big',event)" style="color:blue;cursor:pointer">删除</a>
									       
									    </td>
									</tr>
									
									<tr id="_relation" style="display:none;">
									    <td height="35" align="left">
									    	<span class="for_product">商品信息：</span>
									    	<span class="for_merchant">商家信息：</span><br><br>&nbsp;
									    </td>
									    <td height="35">
									       &nbsp;&nbsp;ID:<input name="relation_id" type="text" id="relation_id" style="width:220px; height:24px; line-height:24px; border:1px solid #ccc" /> <br/>
									    	名称:<input name="relation_title" type="text" id="relation_title" style="width:220px; height:24px; line-height:24px; border:1px solid #ccc" /><br/>
									       <br/>
									       &nbsp;&nbsp;&nbsp;*:上传图片尺寸300X300 格式JPG，建议50K内
									    </td>
									</tr>
									<tr class="c_for_img">
										<td></td>
										<td>	
											<span style="color:blue;">首页广告位</span>的图片尺寸1080X520格式JPG，建议100K内<br/>
											<span style="color:blue;">鹿象APP Banner</span>广告位图片的尺寸1080x330格式JPG，建议100K内<br/>
										</td>
									</tr>
								</table>
							</td>
						  </tr>	
						</table>
					</div>
				</div>
				<div title="投放情况"  style="padding: 10px;width:650px">
	           		<div style='padding: 5px 20px;'>
	           			 <%--
						<table style='padding: 10px 30px; width:98%;'>
							<tr style='height: 30px;'>
								<td>
								</td>
								<td>
								</td>
							</tr>	
						</table>
	           			  --%>
						
						<table width="650px"  style='padding: 10px 10px; width:650px'>
				  		   <tr >
							    <td height="35" align="right" width="120px">广告位：</td>
							    <td height="35" width="260px" >
							    	<%--
							    	<div style="display:none;"><input type="text" name="index" value=""/></div>
							    	 --%>
							    	 <input id='ad_posid' name="ad_posid" class="easyui-combobox"  style="width: 260px;height:23px; margin-top: 1px;"/>
	<!-- 						        <select name="posid" id="ad_posid" width="260"   class="width:260px;height:35px" >
							          	<option value="" style="width:260px;height:35px">选择广告位</option>
							        </select> -->
						        </td>
						        <td align="right" width="120px">轮播时间/秒：</td>
						        <td align="left" width="80px">
						        	<input id="interval" name="interval" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="3" style="width:60px; height:24px; line-height:24px; border:1px solid #ccc" type="text"/>
						        </td>
						          <td align="right" width="80px">排序：</td>
						        <td align="left" width="80px">
						        	<input id="seq" name="seq" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="3" style="width:60px; height:24px; line-height:24px; border:1px solid #ccc" type="text"/>
						        </td>
							</tr>
							<tr>
							    <td height="35" align="right" width="120px">时间段：</td>
							    <td width="226" height="35" colspan="5" >
							    	 <div id="ad_times" name="forTime">
							    	  	<input type="hidden" name="time" id="time"/>
							    	  	<input type="hidden" name="rid" id="rid"/>
							    	 </div>
							    	 <a href="javascript:addTimeSpan()" style="display:block; width:100px; height:30px; line-height:30px; color:#fff; background:#ec3939; text-align:center" >增加时间段</a>
							    	 
							    </td>
							</tr>
							<tr>
							    <td height="35" align="right"></td>
							    <td height="35" align="right"></td>
							    <td width="226" height="35"  align="left">
							    	 <a href="javascript:addPostion()" style="margin-right: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存投放</a>
							    </td>
							    <td height="35" align="right"></td>
							</tr>
							
							 <tr>
							    <td height="35" colspan="6" align="center">
							    	<a style="display:block; width:600px; height:30px; margin:0 auto; color:#fff; font-size:14px; background:#5f87b4; line-height:30px; border:1px solid #4d647f"><strong>已投放广告位↓</strong></a>
							    </td>
							  </tr>
							  <tr>
							  <%--
						  	<td colspan="4" align="center">
						  		<table id="table_area"></table>
						  	</td>
							   --%>
						  	<td colspan="6" align="center">
						  		<table id="rel_table" width="600px" border="1" >
						  			<tr index="1"> 
						  				<td>广告位</td>
							  			<td>轮播时间</td>
							  			<td>排序</td>
							  			<td>时间段</td>
							  			<td>操作</td>
						  			</tr>
						  		</table>
						  	</td>
							   
						  </tr>
				  		   
				  		   </table>
	  		   
				  		   <%--定义模板 --%>
				  		   <div style="display:none;">
							    <div id="temp_times">
								    <span>
									    <input readonly style="width:60px; "  type="text" att="start" />-
									    <input readonly style="width:60px;"  type="text" att="end" />
									    <span onclick="delTimeSpan(this)" style="cursor:pointer;"><a>删除</a><br/></span>
								    </span>
							    </div>
						   </div>
		    
					</div>
					<div style="padding: 0px;" >
						<!-- 
						<table id="table_adrelation" class="easyui-datagrid" style="width:350px;height:250px"></table>
						<table id="table_adrelation"></table>
						 -->
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