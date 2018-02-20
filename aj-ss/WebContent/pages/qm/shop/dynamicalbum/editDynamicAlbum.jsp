<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = request.getParameter("id");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


     <title>商户管理平台-照片管理</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
    <link rel="shortcut icon" href="favicon.ico"> <link href="<c:url value='/resources/hplus4.1/css/bootstrap.min.css?v=3.3.6'></c:url>" rel="stylesheet">
    <link href="<c:url value='/resources/hplus4.1/css/font-awesome.min.css?v=4.4.0'></c:url>" rel="stylesheet">
    <link href="<c:url value='/resources/hplus4.1/css/animate.min.css'></c:url>" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/hplus4.1/css/plugins/webuploader/webuploader.css'></c:url>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/hplus4.1/css/demo/webuploader-demo.min.css'></c:url>">
    <link href="<c:url value='/resources/hplus4.1/css/style.min.css?v=4.1.0'></c:url>" rel="stylesheet">
    
    <script type="text/javascript">
		function AppMgr(){
			 this.initDatas = function(){
				 //初始化店铺信息
				 $.ajax({
		             type: "GET",
		             url: "<%=path %>/admin/shop/baseinfo/all",
		             dataType: "json",
		             success: function(data){
		            	 var arr = data.data;
		            	 var html = "";
		            	  $.each( arr, function(index, content)
		            	  { 
		            		  html += "<option value='"+content.id+"'>"+content.shopName+"</option>";
		            	  });
		            	  $("#shopId").html(html);
		         	 }
		    	});
		    	 $.ajax({
		             type: "GET",
		             url: "<%=path %>/admin/shop/dynamicAlbum/templateList",
		             dataType: "json",
		             success: function(data){
		            	 var arr = data.data;
		            	 var html = "";
		            	  $.each( arr, function(index, content)
		            	  { 
		            	  	console.info("index:"+index);
		            	  	$("#ra"+(index+1)).val(content.id);
		            	  	 $("#tp"+index).attr("src",content.templateLogo);
		            	  	 $("#tp"+index+"_span").text(content.templateName);
		            	  });
		         	 }
		    	});
				 //初始化相册表单信息
				 $.ajax({
		             type: "GET",
		             url: "<%=path %>/admin/shop/dynamicAlbum/find?id=<%=id %>",
		             dataType: "json",
		             success: function(data){
		            	 if(data.success){
		            		 var d = data.data;
		            		 $("#shopId").find("option[value='"+d.shopId+"']").attr("selected",true);
		            		 $("#albumName").val(d.albumName);
		            		 $("#userId").val(d.userId);
		            		 $("#customerUserTel").val(d.userTel);
		            		 if(d.albumLogo != null && d.albumLogo != ""){
		            			 $("#oldAlbumLogo").val(d.albumLogo);
		            			 $("#showAlbumLogo").html("<a href='#'  onclick='viewImg(\""+d.albumLogo+"\", 1)'><b>预览</b></a>");
		            		 }else{
		            			 $("#showAlbumLogo").html("暂无图片");
		            		 }
		            		 $("input[name='templateId'][value='"+d.templateId+"']").attr("checked",true); 
		            		 if(d.photoUrls != null && d.photoUrls != ""){
		            			 $("#oldPhotoUrls").val(d.photoUrls);
		            			 var oldPhotoSize = d.photoUrls.split(",").length;
		            			 $("#filePickerText").append(";<br/>已经上传了"+oldPhotoSize+ "张照片，还可以上传"+ (12-oldPhotoSize )+"张")
		            			 $("#showAlbumPhoto").html("<a href='#' onclick='viewImg(\""+d.photoUrls+"\", 2)'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>管理已上传图片</b></a>");
		            		 }else{
		            			 $("#showAlbumPhoto").html("暂无图片");
		            		 }
		            	 }else{
		            		 layer.msg('初始化失败', {title:'提示', btn: ['确定'],icon: 6}, function(index){
								});
		            	 }
		         	 }
		    	});
		  	 }
		 }
		 $(document).ready(function() {
		  		new AppMgr().initDatas();
			});
		function saveUser(){
			var pickerImgCount = $("#pickerImgCount").val();
			if(pickerImgCount != "" && pickerImgCount > 0){
				layer.msg("请先上传已经选择的图片", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var shopId = $("#shopId").val();
			if(shopId == ""){
				layer.msg("所属商户不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var userId = $("#userId").val();
			if(userId == ""){
				layer.msg("客户手机号码不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var albumName = $("#albumName").val();
			if(albumName == ""){
				layer.msg("相册名称不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}else{
				if(albumName.length > 20){
					layer.msg("相册名称不能超过20个字", {title:'提示', btn: ['确定'],icon: 6}, function(index){
					});
					$("#btn_save").attr("disabled", false);
					return false;
				}
			}
			//去掉shopId的disable属性
			$("#shopId").attr("disabled", false);
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/shop/dynamicAlbum/update",
			     dataType: "json",
			     success: function(obj){
			    	if(obj.success){
							layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
								var index = parent.layer.getFrameIndex(window.name);
								//parent.$('#apv_table').bootstrapTable('refresh');
								parent.$("button[name='refresh']").click();
								parent.layer.close(index);
					  		});
					}else{
						layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
						});
					}
			     }
			 });
			
			$("#btn_save").attr("disabled", false);
			
		}
		$(function(){
			$("#btn_save").click(function(){
				$("#btn_save").attr("disabled", true);
				saveUser();
			});
			 
			//这是我加的代码
			$("body").click(function (e) {
			    if (!$(e.target).closest("#datashow").length) {
			        $("#datashow").hide()
			    }
			});
			
		}); 
		function clearCustomerUserTel(){
		 	$("#customerUserTel").val("");
		 	$("#userId").val("");
		}
		
		function showNames(){
			
			var shopId = $("#shopId").val();
			var tel = $("#customerUserTel").val();
			
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/shop/customer/all?shopId="+shopId+"&userTel="+tel,
	             dataType: "json",
	             success: function(data){
	            	 var arr = data.value;
	            	 var html = "<table class='table table-condensed' style='margin-bottom: 0px;'><tbody>";
	            	 if(arr == null || arr.length == 0){
	            		 html = "<span style='color:red;'>未找到该手机号客户，请到商户客户管理页面添加该客户</span>";
	            		 setValue("", "");
	            	 }else{
		            	  $.each( arr, function(index, content)
		            	  { 
		            		  html += "<tr onclick=\"setValue('"+content.id+"','"+content.userTel+"')\" onmouseover=\"this.bgColor='lightblue'\" onmouseout=\"this.bgColor=''\" data-index='"+index+"' data-id='"+ content.id +"' data-key='"+ content.id +"' class=''><td data-name='id' style='display:none;'>"+content.id+"</td><td data-name='userTel'>"+content.userTel+"</td><td data-name='userName'>"+content.userName+"</td></tr>";
		            	  	  if(tel == content.userTel){
		            	  		  //可能直接输入了完整的手机号，没有选择下拉框，那么直接设置值
		            	  			setValue(content.id, content.userTel);
		            	  	  }
		            	  });
	            	  html += "</tbody></table>";
	            	 }
	            	  $("#datashow").html(html);
	            	  $("#datashow").show();
	         	 }
	    	});
		}
		function hideNames(){
			
	        $("#datashow").hide();
		}
		function setValue(id, userTel){
			$("#userId").val(id);
			$("#customerUserTel").val(userTel)
			hideNames();
		}
		 //t : 1代表Logo  2代表showpic
	    function viewImg(url, t){
				//layer_show("图片浏览", getProjectName() +"/pages/qm/shop/baseinfo/viewImg.jsp?url="+url,"850","800");
	    
	    	//iframe层
	        layer.open({
	            type: 2,
	            title: '图片浏览',
	            shadeClose: true,
	            shade: 0.8,
	            area: ['750px', '500px'],
	            btn:['确定','取消'],
	            closeBtn:false,
	            content: getProjectName() +"/pages/qm/shop/baseinfo/viewImg.jsp?url="+url,
	            yes: function(index, lay){
	            	var data = $(lay).find("iframe")[0].contentWindow.formData();
	            	if(t == 1){
	            		$("#oldAlbumLogo").val(data);
	            	}else if(t == 2){
	            		$("#oldPhotoUrls").val(data);
	            	}
	            	layer.close(index);
	            },
	        }); 
	    }
	</script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeIn">

		<div class="ibox float-e-margins" style="margin-bottom: 0px;">
			<div class="ibox-content row">
				<form class="form-horizontal" id="userForm" method="post">
				<input type="hidden" name="id" id="id" value="<%=id %>">
					<div>
						<div style="float: left;">
							<div style="float: left;">
								<div class="row">
									<div class="form-group col-sm-6">
										<label class="col-sm-4 control-label">商户名称：</label>
										<div class="col-sm-8">
											<select class="form-control col-sm-12" name="shopId"
												id="shopId" onchange="clearCustomerUserTel();" disabled="disabled">
											</select>
										</div>
									</div>
									<div class="form-group col-sm-6">
										<label class="col-sm-4 control-label">客户手机号码：</label>
										<div class="col-sm-8" id="testDiv">
											<div class="input-group">
												<input type="hidden" name="userId" id="userId"> <input
													type="text" class="form-control" id="customerUserTel"
													onclick="showNames()" oninput="showNames(event)"
													onporpertychange="showNames(event)" autocomplete="off">
												<div class="input-group-btn">
													<button type="button" class="btn btn-white dropdown-toggle"
														data-toggle="dropdown">
														<span class="caret"></span>
													</button>
													<ul class="dropdown-menu dropdown-menu-right" role="menu"
														id="datashow" style="width: 275px;">
													</ul>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-sm-6">
										<label class="col-sm-4 control-label">相册名称：</label>
										<div class="col-sm-8">
											<input type="text" class="form-control" name="albumName" placeholder="字数不超过20个字"
												id="albumName">
										</div>
									</div>
									<div class="form-group col-sm-6">
										<label class="col-sm-4 control-label">相册封面：<br/><span style="font-size: 3mm ;font-weight: normal;">(100K内jpg,png图片)</span></label>
										<input type="hidden" name="oldAlbumLogo" id="oldAlbumLogo">
										<div class="col-sm-6">
											<input type="file" class="form-control" name="albumLogo" id="albumLogo">
										</div>
										<span id="showAlbumLogo" style="line-height: 35px;"></span>
									</div>
								</div>
								
								
								<div class="form-group">
		                            <label class="col-sm-2 control-label">动感影集选项：</label>
		
		                            <div class="col-sm-10">
		                                <div class="col-sm-8" style="float: left;">
											<div style="float: left;">
												<img id="tp0"  width="80px;" height="80px;"/>
												<span class="help-block m-b-none" style="text-align: center;"><input type="radio" name="templateId" value="1" id="ra1"><span style=""  id="tp0_span"></span></span>
											</div>
											<div style="float: left;margin-left: 15px;">
												<img id="tp1"  width="80px;" height="80px;"/>
												<span class="help-block m-b-none" style="text-align: center;"><input type="radio" name="templateId" value="2" id="ra2"><span style=""  id="tp1_span"></span></span>
											</div>
											<div style="float: left;margin-left: 15px;">
												<img id="tp2"  width="80px;" height="80px;"/>
												<span class="help-block m-b-none" style="text-align: center;"><input type="radio" name="templateId" value="3" id="ra3"><span style=""  id="tp2_span"></span></span>
											</div>
											<div style="float: left;margin-left: 15px;">
												<img id="tp3"  width="80px;" height="80px;"/>
												<span class="help-block m-b-none" style="text-align: center;"><input type="radio" name="templateId" value="4" id="ra4"><span style=""  id="tp3_span"></span></span>
											</div>
											<div style="float: left;margin-left: 15px;">
												<img id="tp4"  width="80px;" height="80px;"/>
												<span class="help-block m-b-none" style="text-align: center;"><input type="radio" name="templateId"  value="5" id="ra5"><span style=""  id="tp4_span"></span></span>
											</div>
										</div>
		                            </div>
		                        </div>
							</div>
						</div>
						<div style="width: 998px;">
							<div class="row">
								<div class="form-group col-sm-11">
									<label class="col-sm-2 control-label">相册照片管理：</label> 
									<input type="hidden" name="oldPhotoUrls" id="oldPhotoUrls">
									<input type="hidden" name="photoUrls" id="photoUrls">
									<input type="hidden" id="pickerImgCount">
									<span id="showAlbumPhoto" style="line-height: 30px;"></span>
									<div class="page-container" style="margin-left: 150px;">
										<div id="uploader" class="wu-example">
											<div class="queueList">
												<div id="dndArea" class="placeholder">
													<div id="filePicker"></div>
													<p id="filePickerText">单次最多可选12张，支持jpg,png，单张大小10M以内</p>
												</div>
											</div>
											<div class="statusBar" style="display:none;">
												<div class="progress">
													<span class="text">0%</span> <span class="percentage"></span>
												</div>
												<div class="info"></div>
												<div class="btns">
													<div id="filePicker2"></div>
													<div class="uploadBtn">开始上传</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

						</div>
					</div>
				</form>
				<div class="hr-line-dashed"></div>
				<div class="text-center">
					<button id="btn_save" type="button"
						class="btn btn-secondary radius">保存</button>
				</div>
			</div>
		</div>
	</div>

	<script src="<c:url value='/resources/hplus4.1/js/bootstrap.min.js?v=3.3.6' ></c:url>"></script>
	<script src="<c:url value='/resources/hplus4.1/js/content.min.js?v=1.0.0' ></c:url>"></script>
	<script type="text/javascript">
        var BASE_URL = '<c:url value="/resources/hplus4.1/js/plugins/webuploader"></c:url>';
    </script>
	<script src="<c:url value='/resources/hplus4.1/js/plugins/webuploader/webuploader.min.js' ></c:url>"></script>
	<script src="<c:url value='/resources/hplus4.1/js/demo/webuploader-demo.min.js' ></c:url>"></script>
	<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
	<script src="<c:url value='/resources/hplus4.1/js/plugins/suggest/bootstrap-suggest.min.js' ></c:url>"></script>
</body>

</html>
