<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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


    <title>管理平台-学生照片管理</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	<link rel="stylesheet" href="${ctx }/resources/Gallery/css/blueimp-gallery.min.css" />
	<link rel="stylesheet" href="${ctx }/resources/Gallery/css/blueimp-gallery-indicator.css" />
    <link rel="stylesheet" href="${ctx }/resources/Gallery/css/blueimp-gallery-video.css" /> 
    
    <link href="<c:url value='/resources/hplus4.1/css/font-awesome.min.css?v=4.4.0'></c:url>" rel="stylesheet">
    <link href="<c:url value='/resources/hplus4.1/css/animate.min.css'></c:url>" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/hplus4.1/css/demo/webuploader-demo.min.css'></c:url>">
    <link href="<c:url value='/resources/hplus4.1/css/style.min.css?v=4.1.0'></c:url>" rel="stylesheet">
    
	<style>
        .lightBoxGallery img {
            margin: 20px 10px;
            width: 260px;
        }
    </style>
	
	<script type="text/javascript">
	
	
	 function AppMgr(){
		 
		 AppMgr.add = function(e,ownerId){
				layer_show("上传图片/视频", getProjectName() +"/pages/qm/kindergarten/photo/add.jsp?type=2&ownerId=<%=id %>","850","400");
			};
		 
		 
		 var kindergartenId = "";
		 var firstTeacher = "";
		 var secondTeacher = "";
		 var nurse = "";
		 this.initDatas = function(){
		
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/kindergarten/photo/init",
	             data: "owerId=<%=id %>",
	             dataType: "json",
	             success: function(data){
	                  if(data.success){
	                	  var html ="";
	                	  var data = data.data;
	                	  
	                	  var jsonstr = "[]";
	                	  var jsonarray = eval('('+jsonstr+')');
	                	  
	                	  data.forEach(function( p, index ) {
	                		  		html += "<div style='position:relative;float: left;width:275px;height:325px'> ";
	                		  		html += "<a href='"+ p.photoUrl+ "' title='' data-gallery='' >";
	                                html += "<img src='"+ p.photoUrl +"'> </a>";
	                                var pName = p.name;
	                                if(pName == null || pName == ''){
	                                	pName = "添加名称";
	                                }
	                                html += "<div style='position:absolute;z-indent:2;left:10px;bottom:0'  onclick=setIdToHideModel('"+p.id+"')><a id='PID_"+p.id+"' data-toggle='modal'  href='form_basic.html#modal-form'>"+ pName +"</a></div></div>";
	                				
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
	 function setIdToHideModel(pId){
		 $("#hidePhotoId").val(pId);
		 $("#photoName").val($("#PID_"+pId).text());
	 }
	 function alterPhotoName(){
		 var pId = $("#hidePhotoId").val();
		 var pName = $("#photoName").val();
		 if(pId != null && pId != '' && pName != null && pName != ''){
			 
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
	
	 $(document).ready(function() {
	  		new AppMgr().initDatas();
	  		 
	 });
	 function uploadFiles(){
		 
		 $("#modal-form-2").click();
		 
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
                       <button  id="btn_search" type="button" onclick="AppMgr.add(event)"  class="btn btn-inline btn-default" style="margin-top: 4px;">上传照片/视频</button> 
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
