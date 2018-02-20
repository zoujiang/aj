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
	<style>
        .lightBoxGallery img {
            margin: 20px 10px;
            width: 260px;
        }
    </style>
	
	<script type="text/javascript">
	
	
	 function AppMgr(){
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
	                		  		html += "<div style='position:relative;float: left'> ";
	                		  		html += "<a href='"+ p.photoUrl+ "' title='' data-gallery='' >";
	                                html += "<img src='"+ p.photoUrl +"'> </a>";
	                                var pName = p.name;
	                                if(pName == null || pName == ''){
	                                	pName = "添加名称";
	                                }
	                                html += "<div style='position:absolute;z-indent:2;left:10px;bottom:0'  onclick=setIdToHideModel('"+p.id+"')><a id='PID_"+p.id+"' data-toggle='modal'  href='form_basic.html#modal-form'>"+ pName +"</a></div></div>";
	                				
	                	  });
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
	 
	  		
	  		 blueimp.Gallery([
	  	        {
	  	            title: 'Winform框架增量开发过程',
	  	            href: 'aa.mp4',
	  	            type: 'video/mp4',
	  	            poster: 'bg.jpg'
	  	        },
	  	        {
	  	            title: '混合框架图片显示及存储',
	  	            href: 'aa.mp4',
	  	            type: 'video/mp4',
	  	            poster: 'bg.jpg'
	  	        },
	  	        {
	  	            title: '混合框架增量开发过程',
	  	            href: 'aa.mp4',
	  	            type: 'video/mp4',
	  	            poster: 'bg.jpg'
	  	        },
	  	        {
	  	            title: '混合框架之WebAPI接入的增量开发过程',
	  	            href: 'aa.mp4',
	  	            type: 'video/mp4',
	  	            poster: 'bg.jpg'
	  	        }        
	  	    ], 
	  	    {
	  	        container: '#blueimp-video-carousel',
	  	        carousel: true
	  	    });
	 });
	</script>
  </head>
  
  <body class="gray-bg" >
   <div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">

                <div class="ibox-content">

                    <p>
                       <button  id="btn_search" type="button" class="btn btn-inline btn-default" style="margin-top: 4px;">上传照片/视频</button> 
                    </p>
                </div>
                <div class="lightBoxGallery" id="img_content">
                	<!--   <div style="position:relative;float: left">
                        <a href="http://ozwpnu2pa.bkt.clouddn.com/p_big1.jpg" title="图片" data-gallery="" >
                            <img src="http://ozwpnu2pa.bkt.clouddn.com/p1.jpg">
                        </a>
                            <div style="position:absolute;z-indent:2;left:10px;bottom:0">新学期</div>
                            </div>
                	<div style="position:relative;float: left">
                        <a href="http://ozwpnu2pa.bkt.clouddn.com/p_big1.jpg" title="图片" data-gallery="" >
                            <img src="http://ozwpnu2pa.bkt.clouddn.com/p1.jpg">
                        </a>
                            <div style="position:absolute;z-indent:2;left:10px;bottom:0">新学期</div>
                            </div>
                	<div style="position:relative;float: left">
                        <a href="http://ozwpnu2pa.bkt.clouddn.com/p_big1.jpg" title="图片" data-gallery="" >
                            <img src="http://ozwpnu2pa.bkt.clouddn.com/p1.jpg">
                        </a>
                            <div style="position:absolute;z-indent:2;left:10px;bottom:0">新学期</div>
                            </div>
                	<div style="position:relative;float: left">
                        <a href="http://ozwpnu2pa.bkt.clouddn.com/p_big1.jpg" title="图片" data-gallery="" >
                            <img src="http://ozwpnu2pa.bkt.clouddn.com/p1.jpg">
                        </a>
                            <div style="position:absolute;z-indent:2;left:10px;bottom:0">新学期</div>
                            </div>
                        -->

                        <div id="blueimp-gallery" class="blueimp-gallery  blueimp-gallery-controls">
                            <div class="slides"></div>
                            <h3 class="title"></h3>
                            <a class="prev">‹</a>
                            <a class="next">›</a>
                            <a class="close">×</a>
                            <a class="play-pause"></a>
                            <ol class="indicator"></ol>
                        </div>
                        
                    </div>
                        <div id="blueimp-video-carousel" class="blueimp-gallery blueimp-gallery-controls  blueimp-gallery-carousel">
						    <div class="slides"></div>
						    <h3 class="title"></h3>
						    <a class="prev">‹</a>
						    <a class="next">›</a>
						    <a class="play-pause"></a>
						    <ol class="indicator"></ol>
						</div>
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
