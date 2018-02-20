<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String roleId = request.getParameter("roleId");
if(roleId == null || "".equals(roleId)){
	roleId = "";
}
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>管理平台-添加店铺</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>

	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>
	

	<script type="text/javascript">
	
		function AppMgr(){
			 this.initDatas = function(){
				 $.ajax({
		             type: "GET",
		             url: "<%=path %>/admin/shop/category/all?type=3",
		             dataType: "json",
		             success: function(data){
		            	 var arr = data.data;
		            	 var html = "<option value=''>--请选择--</option>";
		            	  $.each( arr, function(index, c)
		            	  { 
		            		  html += "<option value='"+c.id+"'>"+c.name+"</option>";
		            	  });
		            	  $("#shopCategory").html(html);
		         	 }
		    	});
		    	 $.ajax({
		             type: "GET",
		             url: "<%=path %>/admin/shop/brand/all?type=3",
		             dataType: "json",
		             success: function(data){
		            	 var arr = data.data;
		            	 var html = "<option value=''>--请选择--</option>";
		            	  $.each( arr, function(index, c)
		            	  { 
		            		  html += "<option value='"+c.id+"'>"+c.brand_name+"</option>";
		            	  });
		            	  $("#brandId").html(html);
		         	 }
		    	});

				 $('#showStationGc').click(function(e){
						AppMgr.edit(e);
					});
		  	 }
			//编辑
			AppMgr.edit = function(id,valid,e){
				var gps = $("#gps").val();
				layer_show("选择经纬度", getProjectName() +"/pages/map/amap.jsp?gps="+gps,"800","420");
			}
		 }
		 $(document).ready(function() {
		  		new AppMgr().initDatas();
				 $('#summernote').summernote({
					 toolbar:[
						 
					    ['font', ['bold', 'underline', 'clear']],
					    ['fontname', ['fontname']],
					    ['style', ['style']],
					    ['color', ['color']],
					    ['para', ['ul', 'ol', 'paragraph']],
					    ['table', ['table']],
					    ['insert', ['link', 'picture', 'video']],
					    ['view', ['fullscreen', 'codeview']]
				        ],
					 height: 300,                 // set editor height
					 minHeight: 200,             // set minimum height of editor
					 maxHeight: 400,
					 maxWidth : 400,
					 lang : 'zh-CN',
                     callbacks : {
                         onImageUpload : function(files, editor, $editable){
                             sendFile(files);
						 }
                     }
				 });
		 });
        function sendFile(files) {
            var formdata = new FormData();
            //$(".note-toolbar.btn-toolbar").append('正在上传图片');
            formdata.append("file", $('.note-image-input')[0].files[0]);
            $.ajax({
                data : formdata,
                type : "POST",
                //url : "/ys/admin/fileUploadCommon/f/fileUpload", //图片上传出来的url，返回的是图片上传后的路径，http格式
                url : "<%=path %>/service/fileUpload", //图片上传出来的url，返回的是图片上传后的路径，http格式
                cache : false,
                contentType : false,
                processData : false,
                dataType : "json",
                success: function(data) {
                    //data是返回的hash,key之类的值，key是定义的文件名
                    //{"errorMsg":"","result":{"picPath":"http://localhost:8080/ys/service/img?img=/clt/1515171484518.png","shortPicPath":"/clt/1515171484518.png","succMsg":""},"returnCode":"000000","serviceName":"tis_file_upload_resp"}
                    console.info("----00000----"+data.returnCode)
					if(data.returnCode == '000000'){
						 console.info("----data.result.picPath----"+data.result.picPath)
	                    $('#user-work-content').summernote('insertImage', data.result.picPath);
						// $(".note-alarm").html("上传成功,请等待加载");
						// setTimeout(function(){$(".note-alarm").remove();},3000);
					}
                },
                error:function(){
                    alert("上传失败");
                }
            });
        }
	
		function saveUser(){
			
			var shopName = $("#shopName").val();
			if(shopName == ""){
				layer.msg("店铺名称不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var brandId = $("#brandId").val();
			if(brandId == ""){
				layer.msg("店铺品牌不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var logo = $("#logo").val();
			if(logo == ""){
				layer.msg("店铺logo不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var gps = $("#gps").val();
			if(gps == ""){
				layer.msg("店铺坐标地址不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}

			var serviceBeginTime = $("#serviceBeginTime").val();
			if(serviceBeginTime == ""){
				layer.msg("服务开始时间不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var serviceEndTime = $("#serviceEndTime").val();
			if(serviceEndTime == ""){
				layer.msg("服务结束时间不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var tel = $("#tel").val();
			if(tel == ""){
				layer.msg("客服电话不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var content = $('#summernote').summernote('code');
			console.info(content);
			$("#description").val(content);
			
		
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/coupon/shop/add",
			     dataType: "json",
			     success: function(obj){
			    	 if(obj.success){
							layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
								var index = parent.layer.getFrameIndex(window.name);
								//parent.$('#apv_table').bootstrapTable('refresh');
								parent.$("button[name='refresh']").click();
								parent.layer.close(index);
					  		});
						}
						else{
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
			
		}); 
		 function addLine(){
			 
			 var osp = $("#oldShowPics").val(); 
			 var i=0;
			 if(osp != null && osp != ""){
				 i = osp.split(",").length;
			 }
			 var j = $("input[name='showPicsImg']").length
			if( (i +  j)> 4){
				layer.msg("最大可上传5张", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				return;
			}
				var id=Math.round(Math.random()*100000);
		        var fid="pic"+id;
				var html = "<div class='row' id='"+fid+"'><div class='form-group col-sm-6'> <label class='col-sm-4 control-label'></label> <div class='col-sm-8'><input type='file'  name='showPicsImg' accept='image/*' class='form-control'/></div> </div>";
				html += "<div class='form-group col-sm-6'> <div class='col-sm-8' style='margin-left: -20px;margin-top: 8px;'><input type='button' value='+' onclick='addLine()' style='height:19px;'>&nbsp;&nbsp;&nbsp;<input type='button' value='-' onclick='deleteLine(\""+fid+"\")' style='height:19px;'></div>  </div></div>";
				
				$("#pic1").after(html);
			}
		function deleteLine(id)
		{
		  $("#"+id).remove();
		}
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content row">
               	 	<form class="form-horizontal" id="userForm" action="<%=path %>/admin/shop/category/add" enctype="multipart/form-data" method="post">
               	 		<input type="hidden" name="id" id="roleId" value="<%= roleId %>">
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><b style="color:red;">*</b>店铺名称：</label>
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="shopName" id="shopName">
	               				</div>
						    </div>
							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label">联系电话：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" name="registTel" id="registTel">
								</div>
							</div>
						   
                        </div>
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">注册人姓名：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="registName" id="registName">  
	               				</div>
						    </div>

							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label"><b style="color:red;">*</b>店铺地址：</label>
								<div class="col-sm-8">
									<input  type="text" name="address" id="address" class="form-control">
								</div>
							</div>
                        </div>
						<div class="row">
							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label"><b style="color:red;">*</b>客服电话：</label>
								<div class="col-sm-8">
									<input class="form-control" type="text" id="tel" name="tel" >
								</div>
							</div>
							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label"><b style="color:red;">*</b>坐标地址：</label>
								<div class="col-sm-7">
									<input class="form-control" type="text" name="gps" id="gps">
								</div>
								<img id="showStationGc"  style="cursor:pointer;margin-top: 8px;" alt="点击配置坐标" title="点击配置坐标" src="<%=path %>/pages/map/imags/world.png" />
							</div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">商户类型：</label>
	               				<div class="col-sm-8">
	               					<select class="form-control" name="shopCategory" id="shopCategory">
								    </select>
	               				</div>
						    </div>
							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label"><b style="color:red;">*</b>商户品牌：</label>
								<div class="col-sm-8">
									<select class="form-control" name="brandId" id="brandId">
									</select>
								</div>
							</div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><b style="color:red;">*</b>服务开始时间：</label> 
	               				<div class="col-sm-8">
	               					<!-- <input class="form-control" type="text" placeholder="2017-01-01"  name="serviceBeginTime" id="serviceBeginTime"> -->
	               					<input id="serviceBeginTime" class="laydate-icon form-control layer-date" placeholder="服务开始时间"  style="width: 207px;" name="serviceBeginTime" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><b style="color:red;">*</b>服务结束时间：</label>
	                            <div class="col-sm-8">
	               				<!--	<input class="form-control" type="text" placeholder="2018-01-01"  name="serviceEndTime" id="serviceEndTime">-->
	               					<input id="serviceEndTime" class="laydate-icon form-control layer-date" placeholder="服务结束时间"  style="width: 207px;" name="serviceEndTime" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
	               				</div>
	                        </div>
                        </div>
                       
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">是否推荐：</label> 
	               				<div class="col-sm-8">
	               					<input  type="radio" id="isRecommend_0" name="isRecommend" value="0"> 是&nbsp;&nbsp;&nbsp;&nbsp;
	               					<input  type="radio" id="isRecommend_1" name="isRecommend" value="1" checked="checked"> 否
	               				</div>
						    </div>
							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label">推荐排序：</label>
								<div class="col-sm-8">
									<input class="form-control" type="text" name="recommendIx" id="recommendIx" >
								</div>
							</div>
                        </div>
                        <div class="row" id="pic1">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">商户横条图：</label>
	               				<div class="col-sm-8">
				                       <input type="file" id="tsmpPic" multiple="multiple" name="showPicsImg" accept="image/*" class="form-control"/> &nbsp;&nbsp;&nbsp;
	               				</div>
						    </div>
	               			<div class="form-group col-sm-6"> 
	               				<div class="col-sm-12" style="margin-left: -20px;margin-top: 8px;">
				                       <input type="button" value="+" onclick="addLine()" style="height:19px;float: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (支持jpg，png，大小100k以内)
	               				</div>
						    </div>
                        </div>
                        <div class="row">
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><b style="color:red;">*</b>商户LOGO：</label>
	                            <div class="col-sm-8">
	               					<input class="form-control" type="file" id="logo" name="logoImg" >
	               				</div>
	                        </div>
	                        <div class="form-group col-sm-6"> 
	               				<div class="col-sm-12" style="margin-left: -20px;margin-top: 8px;">
				                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (支持png，大小50k以内)
	               				</div>
						    </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">商户介绍：</label>
	                            <div class="col-sm-8"></div>
	                            <label class="col-sm-4 control-label"></label>
	                            <div class="col-sm-8"></div>
                            </div>
                          </div>
						<div class="row">
                         	<input type="hidden" name="description" id="description">
							<div  id="summernote"></div>
						</div>
               		</form>
               		<div class="hr-line-dashed"></div>
               		<div class="text-center">
               		 	<button id = "btn_save" type="button" class="btn btn-primary">保存</button>
                       </div>
                </div>
            </div>
      </div>
  </body>
   <style >
	.modal-backdrop{
		display :none;
		
	}
	.modal-dialog{
		z-index: 2 !important
	}

</style>
</html>