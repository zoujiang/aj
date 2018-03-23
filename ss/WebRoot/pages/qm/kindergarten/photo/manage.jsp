<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = request.getParameter("id");
String type = request.getParameter("type");

%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>管理平台-学生照片管理</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	<link rel="stylesheet" href="${ctx }/resources/Gallery/css/blueimp-gallery.min.css" />
	<link rel="stylesheet" href="${ctx }/resources/Gallery/css/blueimp-gallery-indicator.css" />
    <link rel="stylesheet" href="${ctx }/resources/Gallery/css/blueimp-gallery-video.css" /> 
    
    <link href="<c:url value='/resources/hplus4.1/css/font-awesome.min.css?v=4.4.0'></c:url>" rel="stylesheet">
    <link href="<c:url value='/resources/hplus4.1/css/animate.min.css'></c:url>" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/hplus4.1/css/demo/webuploader-demo.min.css'></c:url>">
    <link href="<c:url value='/resources/hplus4.1/css/style.min.css?v=4.1.0'></c:url>" rel="stylesheet">
    
    
    <link href="<c:url value='/resources/bootstrap-fileinput/css/fileinput.css'></c:url>" rel="stylesheet">
    <script type="text/javascript" src="<c:url value='/resources/bootstrap-fileinput/js/fileinput.js'></c:url>" ></script>
    <script type="text/javascript" src="<c:url value='/resources/bootstrap-fileinput/js/locales/zh.js'></c:url>"  ></script>
    
	<style>
        .lightBoxGallery img {
            margin: 20px 10px;
            width: 260px;
        }
        video {
		    width: 100% !important;
		    height: 90% !important;
		}
    </style>
	
	<script type="text/javascript">
	
	
	 function AppMgr(){
		 
		 AppMgr.add = function(e,ownerId){
				// layer_show("上传图片/视频", getProjectName() +"/pages/qm/kindergarten/photo/add.jsp?type=<%=type%>&ownerId=<%=id %>","850","400");
			};
		 
		 
		 var kindergartenId = "";
		 var firstTeacher = "";
		 var secondTeacher = "";
		 var nurse = "";
		 this.initDatas = function(){
		
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/kindergarten/photo/init",
	             data: "owerId=<%=id %>&type=<%=type%>",
	             dataType: "json",
	             success: function(data){
	                  if(data.success){
	                	  var html ="";
	                	  var data = data.data;
	                	  
	                	  var jsonstr = "[]";
	                	  var jsonarray = eval('('+jsonstr+')');
	                	  
	                	  data.forEach(function( p, index ) {
	                		  		console.info("p.category:"+p.category)
	                		  		html += "<div style='position:relative;float: left;width:275px;height:325px;border:1px solid gray;margin:5px;text-align: center;'> ";
	                		  		if(p.category == 1){
	                		  			//照片
		                		  		html += "<a  href='"+ p.photoUrl+ "' title='' data-gallery=''  >";
		                                html += "<img style='max-width:275px;max-height:300px;' src='"+ p.photoUrl +"'> </a>";
	                		  		}else if(p.category == 2){
	                		  			//视频
	                		  			html += "<a  href='"+ p.videoUrl+ "' title='' data-gallery=''  >";
		                                html += "<video style='max-width:275px;max-height:300px;' src='"+ p.videoUrl +"'  controls='controls'>您的浏览器不支持 video 标签。</video> </a>";
	                		  		}
	                                html += "<button onclick=deleteFile('"+p.id+"') type='button' class='kv-file-remove btn btn-sm btn-kv btn-default btn-outline-secondary' style='position:absolute;z-indent:2;left:233px;top:0px;width:40px;height:40px' title='删除文件'><i class='glyphicon glyphicon-trash'></i></button>";
	                                var pName = p.name;
	                                if(pName == null || pName == ''){
	                                	pName = "添加名称";
	                                }
	                                html += "<div style='position:absolute;z-indent:2;left:10px;bottom:0;width:250px'  onclick=setIdToHideModel('"+p.id+"')><a id='PID_"+p.id+"' data-toggle='modal'  href='form_basic.html#modal-form'>"+ pName +"</a> </div></div>";
	                				
	                	  });
	                	  html += " <div id=\"blueimp-gallery\" class=\"blueimp-gallery blueimp-gallery-controls\"><div class=\"slides\"></div><h3 class=\"title\"></h3><a class=\"prev\">‹</a> <a class=\"next\">›</a><a class=\"close\">×</a><a class=\"play-pause\"></a><ol class=\"indicator\"></ol></div>";
	                	  $("#img_content").html(html);
	                  }else{
	                	  layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
							});
	                  }
	         	 }
	    	});
	  	 }
	 }
	 function deleteFile(pId){
		 layer.confirm('确定删除文件?删除后将不能恢复！', {icon: 3, title:'警告'}, function(index){
			  //do something
			  $.ajax({
		             type: "GET",
		             url: "<%=path %>/admin/kindergarten/photo/deleteFile",
		             data: "id="+pId,
		             dataType: "json",
		             success: function(data){
		                  if(data.success){
		                	  layer.msg("删除成功", {title:'提示', btn: ['确定'],icon: 6}, function(index){
		                		  
		                		  new AppMgr().initDatas();
								});
		                  }else{
		                	  layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
								});
		                  }
		         	 }
		    	});
			  layer.close(index);
			});
		 
	 }
	 function setIdToHideModel(pId){
		 $("#hidePhotoId").val(pId);
		 $("#photoName").val($("#PID_"+pId).text());
	 }
	 function alterPhotoName(){
		 var pId = $("#hidePhotoId").val();
		 var pName = $("#photoName").val();
		 if(pId != null && pId != '' && pName != null && pName != ''){
			 if(pName.length > 40){
				 layer.msg("名称不能超过40个字", {title:'提示', btn: ['确定'],icon: 6}, function(index){
					});
			 }else{
				 
				 $.ajax({
		             type: "GET",
		             url: "<%=path %>/admin/kindergarten/photo/alterPhotoName",
		             data: "id="+pId+"&name="+pName,
		             dataType: "json",
		             success: function(data){
		                  if(data.success){
		                	  $("#PID_"+pId).html(pName);
		                	  layer.msg("修改成功", {title:'提示', btn: ['确定'],icon: 6}, function(index){
		                		  
		                		  $("#modal-form").click();
								});
		                  }else{
		                	  layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
								});
		                  }
		         	 }
		    	});
			 }
			 
		 }
		 
	 }
	
	 $(document).ready(function() {
	  		new AppMgr().initDatas();
	  		 
	  		$("#f_upload").fileinput({
	            theme: 'fa',
	            language:'zh',
	            uploadUrl: '<%=path %>/admin/fileUploadCommon/files/fileUpload', // you must set a valid URL here else you will get an error
	            overwriteInitial: false,
	            uploadExtraData:{"ownerId": '<%=id %>', "type":'<%=type %>', "cate":'1'},
	            showRemove:false,
	            maxFileSize: 1000000,
	            maxFilesNum: 100,
	            allowedFileTypes: ['image', 'video', 'flash'],
	            slugCallback: function (filename) {
	            	return filename.replace('(', '_');
	            }
	        }).on("fileuploaded", function (event, data, previewId, index) {
	        	var form = data.form, files = data.files, extra = data.extra,
	            response = data.response, reader = data.reader;
	            console.log("index:"+index);//打印出返回的json
	            console.log(response.success);//打印出返回的json
	            console.log(response.url);//打印出返回的json
	            console.log(response.type);//打印出路径
	        });
	 });
	 function uploadFiles(){
		 
		 $("#modal-form-2").click();
		 
	 }
	 function returnToListPage(){
			
		var index = parent.layer.getFrameIndex(window.name);
		parent.$("button[name='refresh']").click();
		parent.layer.close(index);
			
		}
	 function closePage(){
			
		$("#fileupload-modal-form").click();
			
		}
	</script>
  </head>
  
  <body class="gray-bg" >
   <div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">

                <div class="ibox-content">
                    <p>
                       <button  id="btn_search" type="button" onclick="AppMgr.add(event)" data-toggle='modal'  href='form_basic.html#fileupload-modal-form'  class="btn btn-inline btn-default" style="margin-top: 4px;">上传照片/视频</button> 
                    </p>
                </div>
                <div class="lightBoxGallery" id="img_content">
                  
			</div>
        </div>
    </div>
</div>
<div id="modal-form" class="modal fade" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div class="row">
                        <h3 class="m-t-none m-b">编辑名称</h3>
                        <form role="form">
                            <div class="form-group">
                            	<input type="hidden" id="hidePhotoId">
                                <input type="text" id="photoName" placeholder="请输入图片名称" class="form-control">
                            </div>
                            <div>
                                <button class="btn btn-sm btn-primary pull-right m-t-n-xs" type="button" onclick=alterPhotoName()>
                                    <strong>确定</strong>
                                </button>
                            </div>
                        </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="fileupload-modal-form" class="modal fade" aria-hidden="true">
    <div class="modal-dialog" style="width: 680px;">
        <div class="modal-content">
            <div class="modal-body" >
                <div class="wrapper wrapper-content animated fadeIn">
			     	<div class="ibox float-e-margins" style="margin-bottom: 0px;">
			             <div class="ibox-content row">
			    			<form class="form-horizontal" id="userForm" method="post" enctype="multipart/form-data">
							   <input id="f_upload" type="file" name="file" multiple="multiple" />
			    		</form>
					    <div class="hr-line-dashed"></div>
						<div class="text-center">
						 	<button id = "btn_save" onclick="returnToListPage()" type="button" class="btn btn-secondary radius">确定</button>
						 	<button id = "btn_save" onclick="closePage()" type="button" class="btn btn-secondary radius">关闭</button>
						      </div>
					    </div>
			   		 </div>
			    </div>
            </div>
        </div>
    </div>
</div>
</body>
  <!--图片展示脚本-->
<script src="${ctx }/resources/Gallery/js/blueimp-helper.js"></script>
<script src="${ctx }/resources/Gallery/js/blueimp-gallery.min.js"></script>
<script src="${ctx }/resources/Gallery/js/blueimp-gallery-fullscreen.js"></script>
<script src="${ctx }/resources/Gallery/js/blueimp-gallery-indicator.js"></script>
<script src="${ctx }/resources/Gallery/js/blueimp-gallery-video.js"></script>
<script src="${ctx }/resources/Gallery/js/blueimp-gallery-vimeo.js"></script>
<script src="${ctx }/resources/Gallery/js/blueimp-gallery-youtube.js"></script>
<script src="${ctx }/resources/Gallery/js/jquery.blueimp-gallery.min.js"></script>
</html>
