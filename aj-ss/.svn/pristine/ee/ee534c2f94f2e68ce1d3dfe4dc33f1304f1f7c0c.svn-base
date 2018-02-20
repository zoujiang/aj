<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String albumId = request.getParameter("albumId");
String tpUrl = request.getParameter("tpUrl");
String musicUrl = request.getParameter("musicUrl");
if(albumId == null){
	albumId = "";
}
if(tpUrl == null){
	tpUrl = "";
}
if(musicUrl == null){
	musicUrl = "";
}
%>
<!doctype html>
<meta charset='utf-8'>
<head>
<link href="./css/endCss.css" rel="stylesheet">
  <script type="text/javascript" src="<%=path%>/resources/hplus4.1/js/jquery.min.js"></script>
    
        <script>
        	
            document.ontouchmove = function(e)
            {
                e.preventDefault();
            } 
             function initPhoto(){
	             console.info("---------------------");
	             
		      //内容图片zouj
		      	
		      		  $.ajax({
		                   type: "GET",
		                   url: "<%=path %>/admin/shop/dynamicAlbum/find?id=<%=albumId%>",
		                   dataType: "json",
		                   //crossDomain: true,
		                   async:false,
		                   success: function(data){
		                  	 if(data.success){
		      	            	 var d = data.data;
		      	            	 var albumLogo = d.albumLogo;
		      	            	 $("#shopLogo").attr("src",albumLogo);
		      	            	 var shopName = d.shopName;
		      	            	 $("#shopName").text(shopName);
		      	            	 var shopTel = d.tel;
		      	            	 $("#shopTel").text(shopTel);
		      	            	 var shopAddress = d.address;
		      	            	 $("#shopAddress").text(shopAddress);
		      	            	 var photoUrls = d.photoUrls;
		      	            	 console.info("加载图片："+photoUrls);
		      	            	 if(photoUrls != ""){
		      	            		 slider_images_url =  photoUrls.split(",");
		      	            	 }
		      	            	 document.title = d.albumName;
		      	            	 //viewer-iframe
		                  	 }
		               	 }
		          	}); 
		          	//音乐地址zouj
		      		e_music_url = 'music/fushishanxia.mp3';
		      		//e_music_url = 'http://localhost:8080/ys/dynamic/music/jh.M3U';
		      		var musicUrl = '<%=musicUrl%>';
		      		if(musicUrl != ""){
		      			e_music_url = musicUrl;
		      		}
             }
        </script>
        
        <script src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>
</head>

<body>
<div id="enddiv" style="display: none;">
    	<div class="logo"><img src="./img/tanchu_logo.png"></div>
        <div class="kouhao">亲脉APP与各大影楼一起，传承家的回忆</div>
        <div class="xiazai"><a href="http://qm.dbfish.net/d">下载APP</a></div>
        <!--<div class="fenxiang"><a href="#">分享给朋友</a></div>-->
        <ul class="sj clearfix">
        	<li class="sj_logo" >
        		<img id="shopLogo" /></li>
        	<li class="sj_info">
        		<strong ><span id="shopName"></span></strong>
        		<span id="shopTel"></span>
        		<p style="margin-top: 50px;margin-left: -40px;" id="shopAddress"></p>
        	</li>
        </ul>
        <div class="bofang" id='reshowbtn' onclick="reload_scene()" ontouchstart=''><a href="#">重新播放</a></div>
</div>
	<div class="zp_big" id="zp_big" onclick="colseCurrentDiv()"><img id="zp_big_img" src=""></div>
 		<div class="zhezhao" id="zhezhao"></div>

<div id='container'>
<iframe  id="viewer-iframe" class="viewer-iframe" style="border-width: 0"
<%
	if(tpUrl != ""){
	%>
	src="..<%=tpUrl %>"
	<%
	}else{
	%>
	 src="../demo/demo.html"
	 <%
	}
 %>
 ></iframe>
     <div id='pagetitle'>
       <img id='titlebg' src='' style="display: none;">
        <div id='biaoti'>
            <div id='titlecontent' style='width:287px;height:500px;display:none;font-size:32px;line-height:50px;color:#fff;position:absolute;top:501px;left:106px; display:flex;justify-content:center;align-items:center;text-align: center;font-weight:900;    text-shadow: 0px 0px 12px #FFF;'>
                <div id='tcontent'></div>
            </div>
        </div>
    </div>

    <div id='div1' class='div'>
        <div id='word1' class='wordspan'></div>
    </div>
    <div id='div2' class='div'>
        <div id='word2' class='wordspan'></div>
    </div>
    <div id='div3' class='div'>
        <div id='word3' class='wordspan'></div>
    </div>
    <div id='div4' class='div'>
        <div id='word4' class='wordspan'></div>
    </div>
    <div id='div5' class='div'>
        <div id='word5' class='wordspan'></div>
    </div>

</div>

	<link rel="stylesheet" type="text/css" href="css/buttonsdev.css" />
	


<div id='loadingdiv' style="display: none;">
<!-- 	<div class='loadingword'>相册正在加载中<br>请稍等……</div> -->
</div>


	<script type="text/javascript" src="js/xmlHttp.js"></script>
	<script type="text/javascript" src="<%=path %>/dynamic/js/demo.js"></script>
	</body>