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


    <title>管理平台-添加优惠券</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>

	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>
	

	<script type="text/javascript">
	
		function AppMgr(){
			 this.initDatas = function(){
				 $.ajax({
		             type: "GET",
		             url: "<%=path %>/admin/coupon/shop/all",
		             dataType: "json",
		             success: function(data){
		            	 var arr = data.data;
		            	 var html = "<option value=''>--请选择--</option>";
		            	  $.each( arr, function(index, c)
		            	  { 
		            		  html += "<option value='"+c.id+"'>"+c.shopName+"</option>";
		            	  });
		            	  $("#shopId").html(html);
		         	 }
		    	});
		    	 

				 $('#showStationGc').click(function(e){
						AppMgr.edit(e);
					});
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
	                   $("#summernote").summernote('insertImage', data.result.picPath, 'image name');
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
			
			var shopName = $("#name").val();
			if(shopName == ""){
				layer.msg("兑换券名称不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var startTime = $("#startTime").val();
			if(startTime == ""){
				layer.msg("有效期开始时间不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var endTime = $("#endTime").val();
			if(endTime == ""){
				layer.msg("有效期结束时间不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var totalNum = $("#totalNum").val();
			if(totalNum == ""){
				layer.msg("兑换券总份数不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}else{
				if(!validate(totalNum)){
					layer.msg("兑换券总份数只能是正整数", {title:'提示', btn: ['确定'],icon: 6}, function(index){
					});
					$("#btn_save").attr("disabled", false);
					return false;
				}
			}
			var leftNum = $("#leftNum").val();
			if(leftNum == ""){
				layer.msg("兑换券剩余份数不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}else{
				if(!validate(leftNum)){
					layer.msg("兑换券剩余份数只能是正整数", {title:'提示', btn: ['确定'],icon: 6}, function(index){
					});
					$("#btn_save").attr("disabled", false);
					return false;
				}
				if(Number(leftNum) >Number(totalNum)){
					layer.msg("兑换券剩余份数不能大于兑换券总份数", {title:'提示', btn: ['确定'],icon: 6}, function(index){
					});
					$("#btn_save").attr("disabled", false);
					return false;
				}
			}
			var limitNum = $("#limitNum").val();
			if(limitNum == ""){
				layer.msg("单人领取限制数不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}else{
				if(!validate(limitNum)){
					layer.msg("单人领取限制数只能是正整数", {title:'提示', btn: ['确定'],icon: 6}, function(index){
					});
					$("#btn_save").attr("disabled", false);
					return false;
				}
				if(Number(limitNum) - Number(totalNum) > 0){
					layer.msg("单人领取限制数不能大于兑换券总份数", {title:'提示', btn: ['确定'],icon: 6}, function(index){
					});
					$("#btn_save").attr("disabled", false);
					return false;
				}
			}

			var logo = $("#logo").val();
			if(logo == null || logo == ""){
				layer.msg("兑换券首页图片不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var isLink = $("#isLink").val();
			var linkAddress = $("#linkAddress").val();
			if(isLink == 1 && (linkAddress == null || linkAddress == "")){
				layer.msg("兑换券首类型是外链时，外链地址不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			
			var content = $('#summernote').summernote('code');
			console.info(content);
			$("#description").val(content);
			
		
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/coupon/baseinfo/add",
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
		 
		 function validate(value){
		    var reg = new RegExp("^[0-9]*$");
		    if(!reg.test(value) || value <= 0){
		       	return false;
		    }
		    return true;
		  }
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content row">
               	 	<form class="form-horizontal" id="userForm" action="" enctype="multipart/form-data" method="post">
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><b style="color:red;">*</b>所属商户：</label>
	               				<div class="col-sm-8">
	               					<select class="form-control" name="shopId" id="shopId"></select>
	               				</div>
						    </div>
							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label"></label>
								<div class="col-sm-8" style="margin-top: 5px;">
								</div>
							</div>
                        </div>
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><b style="color:red;">*</b>兑换券名称：</label>
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="name" id="name">
	               				</div>
						    </div>
							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label"><b style="color:red;">*</b>领取条件：</label>
								<div class="col-sm-8" style="margin-top: 5px;">
									<input type="radio"  name="getCondition" id="getCondition_all" value='0' checked="checked">&nbsp;所有人 &nbsp;&nbsp;&nbsp;
									<input type="radio"  name="getCondition" id="getCondition_vip" value='1'>&nbsp;VIP
								</div>
							</div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><b style="color:red;">*</b>有效期开始时间：</label> 
	               				<div class="col-sm-8">
	               					<input id="startTime" class="laydate-icon form-control layer-date" placeholder="有效期开始时间"  style="width: 207px;" name="startTime" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><b style="color:red;">*</b>有效期结束时间：</label>
	                            <div class="col-sm-8">
	               					<input id="endTime" class="laydate-icon form-control layer-date" placeholder="有效期结束时间"  style="width: 207px;" name="endTime" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
	               				</div>
	                        </div>
                        </div>
                        
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><b style="color:red;">*</b>兑换券总份数：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="totalNum" id="totalNum">  
	               				</div>
						    </div>

							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label"><b style="color:red;">*</b>兑换券剩余数：</label>
								<div class="col-sm-8">
									<input  type="text" name="leftNum" id="leftNum" class="form-control">
								</div>
							</div>
                        </div>
                        <div class="row">
                        <div class="form-group col-sm-6"> 
                      		   <label class="col-sm-4 control-label"><b style="color:red;">*</b>单人领取限制数：</label>
	               				<div class="col-sm-8">
	               					<input  type="text" name="limitNum" id="limitNum" class="form-control">
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">兑换券标签：</label>
	                            <div class="col-sm-8">
	               					<input class="form-control" type="text" id="tag" name="tag" >
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
									<input class="form-control" type="text" name="recommendIdx" id="recommendIdx" >
								</div>
							</div>
						</div>
                        <div class="row">
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><b style="color:red;">*</b>兑换券首页图片：</label>
	                            <div class="col-sm-8">
	               					<input class="form-control" type="file" id="logo" name="logoImg" >
	               				</div>
	                        </div>
	                        <div class="form-group col-sm-6"> 
	               				<div class="col-sm-12" style="margin-left: -20px;margin-top: 8px;">
				                       <span style="font-size: 6px;color: lightgray;">支持png，建议尺寸220x164，大小50k以内</span>
	               				</div>
						    </div>
                        </div>
                        
                         <div class="row" id="pic1">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">兑换券展示图：</label>
	               				<div class="col-sm-8">
				                       <input type="file" id="tsmpPic" multiple="multiple" name="showPicsImg" accept="image/*" class="form-control"/> 
	               				</div>
						    </div>
	               			<div class="form-group col-sm-6"> 
	               				<div class="col-sm-12" style="margin-left: -20px;margin-top: 8px;">
				                        <span style="font-size: 6px;color: lightgray;">支持选择多张jpg，png格式图片，建议尺寸1500x960，大小100k以内</span>
	               				</div>
						    </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">是否外链：</label> 
	               				<div class="col-sm-8">
	               					<input  type="radio" id="isLink_0" name="isLink" value="0"> 是&nbsp;&nbsp;&nbsp;&nbsp;
	               					<input  type="radio" id="isLink_1" name="isLink" value="1" checked="checked"> 否
	               				</div>
						    </div>
							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label"></label>
								<div class="col-sm-8">
								</div>
							</div>
						</div>
						<div class="row">
	               			
							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label">外链地址：</label>
								<div class="col-sm-8">
									<input class="form-control" type="text" name="linkAddress" id="linkAddress" style="width: 500px;">
								</div>
							</div>
						</div>
                        <div class="row">
                            <div class="form-group col-sm-6">
	                            <label class="col-sm-5 control-label">兑换券描述信息：</label>
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
