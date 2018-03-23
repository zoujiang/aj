<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String albumId = request.getParameter("albumId");
if(albumId == null){
	albumId = "";
}
%>
<!doctype html>
<meta charset='utf-8'>

        <script type="text/javascript" src="<%=path%>/resources/hplus4.1/js/jquery.min.js"></script>
        <script>
        var _speedMark = new Date();
        
       
        function init_viewport()
        {
        	var w, h;
            if(/Android (\d+\.\d+)/.test(navigator.userAgent))
            {
                var version = parseFloat(RegExp.$1);

                if(version>2.3)
                {
                    var width = window.outerWidth == 0 ? window.screen.width : window.outerWidth;
                    w = width;
                    h = window.outerHeight == 0 ? window.screen.height : window.outerHeight;
                    var phoneScale = parseInt(width)/500;
                    document.write('<meta name="viewport" content="width=500, minimum-scale = '+ phoneScale +', maximum-scale = '+ phoneScale +', target-densitydpi=device-dpi">');
                }
                else
                {
                    document.write('<meta name="viewport" content="width=500, target-densitydpi=device-dpi">');    
               		w = 500; h = 750;
                }
            }
            else if(navigator.userAgent.indexOf('iPhone') != -1)
            {
                var phoneScale = parseInt(window.screen.width)/500;
                document.write('<meta name="viewport" content="width=500; min-height=750;initial-scale=' + phoneScale +'; user-scalable=no;" /> ');         //0.75   0.82
           		w = 500; h = 750;
            }
            else 
            {
                document.write('<meta name="viewport" content="width=500, height=750, initial-scale=0.64" /> ');         //0.75   0.82
				w = 500; h = 750;
            }
            $("#container").width(w);
       		$("#container").height(h);
            $("#viewer-iframe").width(w);
       		$("#viewer-iframe").height(h);
        }

        init_viewport();

        </script>
    
        <script>
            document.ontouchmove = function(e)
            {
                e.preventDefault();
            } 
        </script>
    
        <script src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>
        <script>

            var module_inits = [];

            var upload_permission = true;

            wx.config({
                debug     : false,
                appId     : '',
                timestamp : 10000,
                nonceStr  : '',
                signature : '',
                jsApiList : [
                    'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareQZone', 'closeWindow', 'hideAllNonBaseMenuItem', 'showMenuItems', 'showAllNonBaseMenuItem', 'chooseImage', 'uploadImage']
                });
            
            function load_init_modules()
            {
                for(var i=0; i<module_inits.length; i++)
                {
                    module_inits[i]();
                }
            }

            function call_me(fun)
            {
                module_inits.push(fun);
            }

            var ua = navigator.userAgent.toLowerCase();  

            if(ua.match(/MicroMessenger/i) == 'micromessenger')
            {  
                wx.ready(load_init_modules);
            }
            else
            {
                onload = load_init_modules;
            }

        </script>

<script>

//内容图片zouj
		var slider_images_url = [];
		<%--  $.ajax({
             type: "GET",
             url: "<%=path %>/admin/shop/dynamicAlbum/find?id=<%=albumId%>",
             dataType: "json",
             async:false,
             success: function(data){
            	 if(data.success){
	            	 var d = data.data;
	            	 var photoUrls = d.photoUrls;
	            	 if(photoUrls != ""){
	            		 slider_images_url =  photoUrls.split(",");
	            	 }
            	 }
            	 var html = "";
            	  $.each( arr, function(index, c)
            	  { 
            		  html += "<option value='"+c.id+"'>"+c.name+"</option>";
            	  });
            	  $("#shopCategoryId").html(html);
         	 }
    	}); --%>
		slider_images_url.push('http://img.mp.itc.cn/upload/20170221/d53ee4b494c64d45adee300a775b4fdf_th.jpeg');
		slider_images_url.push('http://imgsrc.baidu.com/baike/pic/item/7aec54e736d12f2ee289bffe4cc2d5628435689b.jpg');
		slider_images_url.push('http://120.25.74.162:808/aj/service/img?img=/clt/1478866988150.jpg');
		var date = 20170221;
		//进入相册中间的描述文字zouj
		var e_desc = '';
		var e_openid = null;
		var editmode = false;
		var shareid = '';
		var words = {};
		var app = '';
		var onmake = 'true';
		var editdesc = '0';
		//音乐地址zouj
		var e_music_url = 'music/fushishanxia.mp3';
		var e_music_title = '';
		var tbssaveoff = false;
		var isguangchang = '';
		var isshow = '';
		var gotomake = 'make';
		var istiaozhuan = '';
		var isfinish = '';
</script>

<style type="text/css">
    
*
{
    padding: 0px;
    margin: 0px;
    -webkit-box-sizing: border-box;
}

body
{
    background-color: black;
} 

#container
{
    width:100%;
    height:100%;
    position: absolute;
    overflow: hidden;
}
#container > img, #container > div
{
    position: absolute;
}
#yunbiao >img , #yunbiao1>img
{
    position: absolute;
}

.div
{
    border: 8px solid #fff;
    opacity: 0;
}
.wordspan
{
    width: 50%;
    left: 25%;
    position: absolute;
    color: #fff;
    font-size: 25px;
    text-align: center;
    text-shadow: 1px 1px 2px #000;
    font-weight: bold;
}
#dibu
{
    bottom: 0px;
}


#titlebg{
   left: 0px;
   top:1000px;   
   position: absolute;
}
#biaoti
{
    top: -402px;
    position: absolute;
    overflow: hidden;
}

@-webkit-keyframes div1_in
{
    0%{-webkit-transform:translate(0px,-500px) scale(1.2);opacity: 0}
    20%{-webkit-transform:translate(0px,30px) scale(0.8);opacity: 1;-webkit-animation-timing-function: ease-out;}
    80%{-webkit-transform:translate(0px,0px) scale(1);opacity: 1}
    100%{-webkit-transform:translate(0px,-10px) scale(1.4);opacity: 0}

}
@-webkit-keyframes div2_in
{
    0%{-webkit-transform:translate(0px,500px);opacity: 0}
    20%{-webkit-transform:translate(0px,10px);opacity: 1;-webkit-animation-timing-function: ease-out;}
    80%{-webkit-transform:translate(0px,-10px);opacity: 1}
    100%{-webkit-transform:translate(0px,-500px);opacity: 0}
}
@-webkit-keyframes div3_in
{
    0%{-webkit-transform:translate(500px,0px);opacity: 0}
    20%{-webkit-transform:translate(40px,0px);opacity: 1;-webkit-animation-timing-function: ease-out;}
    80%{-webkit-transform:translate(-40px,0px);opacity: 1}
    100%{-webkit-transform:translate(-500px,0px);opacity: 0}
}
@-webkit-keyframes div4_in
{
    0%{-webkit-transform:translate(0px,500px);opacity: 0}
    20%{-webkit-transform:translate(0px,40px);opacity: 1;-webkit-animation-timing-function: ease-out;}
    80%{-webkit-transform:translate(0px,-40px);opacity: 1}
    100%{-webkit-transform:translate(0px,-500px);opacity: 0}
}
@-webkit-keyframes div5_in
{
    0%{-webkit-transform:translate(0px,500px);opacity: 0}
    20%{-webkit-transform:translate(0px,-20px);opacity: 1;-webkit-animation-timing-function: ease-out;}
    80%{-webkit-transform:translate(0px,20px);opacity: 1}
    100%{-webkit-transform:translate(0px,-500px);opacity: 0}
}
</style>

<div id='container'>
<iframe  id="viewer-iframe" class="viewer-iframe" src="../demo/demo.html"></iframe>
     <div id='pagetitle'>
       
       <img id='titlebg' src='' style="display: none;">
        <div id='biaoti'>
            <div id='titlecontent' style='width:287px;height:500px;display:flex;font-size:32px;line-height:50px;color:#fff;position:absolute;top:501px;left:106px; display:flex;justify-content:center;align-items:center;text-align: center;font-weight:900;    text-shadow: 0px 0px 12px #FFF;'>
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

 

<script>
function  id (name) 
{
    return document.getElementById(name);
}

function liangziyun_kawa()
{
    setTimeout(function(){
    id('biaoti').style.webkitAnimation='titlewaiout 3s linear both';
    id('titlecontent').style.webkitAnimation='titleneiout 3s linear both';
    id('titlebg').style.webkitAnimation='titlebgout 1s 3s linear both';
    },5000)
    timeout[0]=setTimeout(show1, 8500);
}

function show1()
{
    id('div5').style.webkitAnimation = '';
    id('word5').style.top = '';
    id('word5').style.bottom = '';
    setImage('1');
    id('div1').style.webkitAnimation = 'div1_in 8s ease both';
    timeout[1] = setTimeout(show2, 8000)
}

function show2()
{
    id('div1').style.webkitAnimation = '';
    id('word1').style.top = '';
    id('word1').style.bottom = '';
    setImage('2');
    id('div2').style.webkitAnimation = 'div2_in 8s  ease both';

    timeout[2] = setTimeout(show3, 8000)

}
function show3()
{
    id('div2').style.webkitAnimation = '';
    id('word2').style.top = '';
    id('word2').style.bottom = '';
    setImage('3');
    id('div3').style.webkitAnimation = 'div3_in 8s  ease both';

    timeout[3] = setTimeout(show4, 8000)

}
function show4()
{
    id('div3').style.webkitAnimation = '';
    id('word3').style.top = '';
    id('word3').style.bottom = '';
    setImage('4');
    id('div4').style.webkitAnimation = 'div4_in 8s  ease both';

    timeout[4] = setTimeout(show5, 8000)

}
function show5()
{
    id('div4').style.webkitAnimation = '';
    id('word4').style.top = '';
    id('word4').style.bottom = '';
    setImage('5');
    id('div5').style.webkitAnimation = 'div5_in 8s  ease both';

    timeout[5] = setTimeout(show1, 8000)

}


function loading () 
{
    load_images();
}


function setImage(idname)
{

    if(reshow == true)
        return;

    while(Onload_imgs_url[image_url_index] == 'not find' || Onload_imgs_url[image_url_index] == 'loading')
    {
        if(image_url_index % step_loadnum == 0)
        {
            step_load();
        }
        image_url_index++;
        if(image_url_index == Onload_imgs_url.length)
            image_url_index = 0;
    }

    if(image_url_index % step_loadnum == 0)
    {
        step_load();
    }
    var img_bili = image_size_width[image_url_index]/image_size_height[image_url_index];

    var div = id('div'+idname);

    div.style.backgroundImage = 'url('+Onload_imgs_url[image_url_index]+')';

    var word = id('word' + idname);
    var str = words[get_pid(Onload_imgs_url[image_url_index])];

    if(str != undefined)
    {
        word.innerHTML = str;
    }
    else
    {
        word.innerHTML = '';
    }
    //500 760
   if(img_bili < (500/760))
    {
        var height = 760;
        var width = 500 * img_bili;
        var top = 80;
        var left = (500-width)/2;
        word.style.top='12px';
    }
    else if(img_bili <= 1)
    {
        var width = 420;
        var height = 420 / img_bili;
        var left = 40;
        var top = (760-height)/2;
        word.style.top='12px';
    }
    else if(img_bili > 1)
    {
        var width = 500;
        var height = 760/img_bili;
        var left = 0;
        var top = (760-height)/2;
        word.style.bottom='7px';
    } 
     
    div.style.width = width + 'px';
    div.style.height = height + 'px';
    div.style.left = left + 'px';
    div.style.top = top + 'px';
    div.style.backgroundSize = (width-8) + 'px ' + (height-8) + 'px';
    div.style.backgroundColor='#fff';

    image_url_index ++;
    if(image_url_index == Onload_imgs_url.length)
      image_url_index = 0;
}

var get_pid = function(url)
{
    var index1 = url.indexOf("?");
    if(index1 != -1)
    {
        url = url.substr(0, index1);
    }
    return url.toString().substr(url.lastIndexOf("/") + 1);
}

//每次执行加载后5张图片
var bl_keepload = 'first';
var step_loadnum = 5;
var img_max_width = 300;
var img_max_height=450;
function step_load()
{
    var load_num = 0
    //初步加载X张
    if(image_url_index  == 0 && bl_keepload == 'first')
    {
        console.log('loading continue');
        if(slider_images_url.length > step_loadnum)
        {
            load_num = step_loadnum;
            bl_keepload = 'next';
        }
        else
        {
            load_num = slider_images_url.length;
            bl_keepload = 'end';
        }
        for(var i = 0; i< load_num; i++)
        {
            var img=new Image();
            img.index=i;
			console.log('-------'+slider_images_url[i]);
            img.src=slider_images_url[i];
            img.onload=image_onload;
            img.onerror= image_onerror;
            Onload_imgs_url[i] = 'loading';
        }
    }
    else if(bl_keepload == 'end')
    {
        return;
    }
    else
    {
         console.log('loading continue');
        if(slider_images_url.length - image_url_index >step_loadnum*2)
        { 
            load_num = step_loadnum;
        }
        else
        {
            load_num = slider_images_url.length - image_url_index - step_loadnum;
            bl_keepload = 'end';
        }
        for(var i = image_url_index +step_loadnum; i< image_url_index + step_loadnum + load_num; i++)
        {
            var img=new Image();
            img.index=i;
            img.src=slider_images_url[i];
            img.onload=image_onload;
            img.onerror= image_onerror;
            Onload_imgs_url[i] = 'loading';
        }
    }

}
function load_images()
{
    reshow = false;
    image_size_width=[];
    image_size_height=[];
    Onload_imgs_url=[];
    image_url_index=0;
    have_num = 0;
    error_num = 0;
    id('tcontent').innerHTML=e_desc;

    begin_titletime = new Date();
    begin_titletime = begin_titletime.getTime();
    canshow = true;
    bl_keepload = 'first';
    step_load();       
}

function image_onerror(event)
{
    var img = event.target;
    var index = img.index;
    if(index<step_loadnum)
        error_num ++;
    Onload_imgs_url[index] = 'not find';

    if((have_num+error_num >= step_loadnum || slider_images_url.length == (have_num+error_num)) && canshow == true)
    {   
        reshow = false;
        canshow =false;
        if(have_num == 0)
            return;
        var end_titletime = new Date();
        end_titletime = end_titletime.getTime();
        var dis_titletime = Math.abs(end_titletime-begin_titletime);
        if(dis_titletime>delaytime)
        {
            liangziyun_kawa();
        }
        else
        {
            dis_titletime = delaytime- dis_titletime;
            timeout[8] = setTimeout(function()
                {
                    liangziyun_kawa();
                },dis_titletime);
        }
    }
}

function image_onload(event)
{
    if(reshow == true)
        return;

    var img = event.target;
    var index = img.index;

    if(index<step_loadnum)
    {
        have_num++;
    }
    Onload_imgs_url[index] = img.src;
    image_size_height[index] = img.height;
    image_size_width[index] = img.width;
    console.log(Onload_imgs_url[index]);
    // console.log(have_num + '-' + error_num);

    if((have_num + error_num >= step_loadnum ||slider_images_url.length == (have_num+error_num)) && canshow == true)
    {   
        reshow = false;
        canshow =false;
        if(have_num == 0)
            return;
        var end_titletime = new Date();
        end_titletime = end_titletime.getTime();
        var dis_titletime = Math.abs(end_titletime-begin_titletime);
        if(dis_titletime>delaytime)
        {
            liangziyun_kawa();
        }
        else
        {
            dis_titletime = delaytime- dis_titletime;
            timeout[8] = setTimeout(function()
                {
                    liangziyun_kawa();
                },dis_titletime);
        }

    }
}
var image_size_width=[];
var image_size_height=[];
var image_url_index=0;
var have_num = 0;
var error_num = 0;
var canshow = true;
var reshow = false;
var delaytime=5000;
var timeout = [];




function reload_scene()
{
    clearnode();
    reshow = true;
    setTimeout(load_images,500);
}

function clearnode()
{

    for(var i = 0; i<timeout.length; i++)
    {
        clearTimeout(timeout[i]);
    }
    for(var i =0; i<16;i++)
    {
        var xin = 'tbgxin'+ i;
        console.log(xin)
        id(xin).style.display='none';
    }
    var itemlist = ['biaoti','titlecontent','titlebg','div1','div2','div3','div4','div5'];

    for(var i =0; i<itemlist.length;i++)
    {
        id(itemlist[i]).style.webkitAnimation = '';
    }

}
call_me(load_images);
</script>

	<link rel="stylesheet" type="text/css" href="css/buttonsdev.css" />
	<style type="text/css">
		@-webkit-keyframes zhuan
		{
			0%
			{
				-webkit-transform:rotate(0deg);
			}

			100%
			{
				-webkit-transform:rotate(360deg);
			}
		}
		
		#musicinfo
		{
			color: #fff;
			text-shadow: 1px 1px 2px #000;
			font-size: 15px;
			position: fixed;
			left: 432px;
		    top: 82px;
		    width: 60px;
		    height: 20px;
		    z-index: 100;
		    display: none;
		    opacity: 1;
		}
#enddiv
{
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: rgba(0,0,0,0.7);
    z-index: 10000000;
    display: none;
    opacity: 0;
}
#title_div
{
    width: 320px;
    height: 200px;
    left: 90px;
    top: 250px;
    color: #fff;
    font-size: 25px;
    position: relative;
    line-height: 45px;
    text-align: center;
    font-family: '微软雅黑';
    display:table;
}
#title_line
{
    position: absolute;
    width: 370px;
    height: 2px;
    background-color: #fff;
    top: 415px;
    left: 65px;
}
#reshowbtn
{
    position: absolute;
    width: 150px;
    height: 44px;
    border-radius: 10px;
    background-color: rgba(94,159,177,0.9);
    color: #fff;
    font-size: 22px;
    text-align: center;
    line-height: 44px;
    top: 446px;
    left: 74px;
}

@-webkit-keyframes fadein
{
	from  {opacity: 0}
	to    {opacity: 1}
}
@-webkit-keyframes fadeout
{
	from  {opacity: 1}
	to    {opacity: 0}
}
#loadingdiv
{
	width: 100%;
	height: 100%;
	z-index: -20000;
	background-color: gray;
/* 	background-color: rgba(255,255,255,0.5); */
	position: absolute;
	pointer-events: none;
}
.loadingword
{
	font-size: 25px;
	color: rgba(82,82,82,0.9);
	font-family: '微软雅黑';
	text-align: center;
	width: 300px;
	position: relative;
	top: 300px;
	line-height: 50px;
}


</style>
	<script type="text/javascript" src="js/xmlHttp.js"></script>
	<script type="text/javascript">

		if(typeof(objid) === "undefined")
		{
			var objid = function(id)
			{
				return document.getElementById(id);
			}
		}

		function random(min,max)
		{
		    return Math.floor(min+Math.random()*(max-min));
		}

	
		function on_wx_music_init()
		{
			if(e_desc != "")
			{
				document.title = e_desc.replace("<br>", "\n").replace("<br/>", "\n");;
			}
			else
			{
				document.title = "音乐相册";
			}
			loadingdiv_out();
			create_music();

			if((isfinish == 'yes')&&editmode == false)
				read_localstorage();
			var locationurl = window.location.href;
		
		}

		
		call_me(on_wx_music_init);

		//音乐播放

		var e_music_player = new Audio();
		function create_music()
		{
		    if(e_music_url == '')
		    {
		        return ;
		    }
		    
		    play_music();

		    sound_div = document.createElement("div");
		    sound_div.setAttribute("ID", "cardsound");
		    sound_div.style.cssText = "position:fixed;right:20px;top:25px;z-index:50;visibility:visible;";

		    bg_htm  = "<img id='sound_image' src='./img/music_note_big.png' style='-webkit-animation:zhuan 2s linear infinite'>";
		    txt_htm = "<div id='music_txt' style='color:white;position:absolute;left:-40px;top:30px;width:60px'></div>"

		    sound_div.innerHTML = bg_htm  + txt_htm;

		    document.body.appendChild(sound_div);
		} 
		function play_music()
		{
		    if(e_music_url == '')
		    {
		        return ;
		    }
			mymusic = e_music_url;
		    e_music_player.src  = mymusic;
		    e_music_player.loop = 'loop';

		    console.log(e_music_player.src);
		    
		    e_music_player.play();
		    
		    //检查音乐是否缓冲成功
		    setTimeout(function()
		    {
				var timeRanges = e_music_player.buffered;
				var timeBuffered = timeRanges.end(timeRanges.length - 1);
				var bufferPercent = timeBuffered /e_music_player.duration; 
				console.log('music:'+bufferPercent+"--e_music_url:"+e_music_url);
				
				if(isNaN(bufferPercent) || bufferPercent == 0)
				{
			    	e_music_player.src = url;
			    	e_music_player.play();
				}
		    },5000);


		    if(objid('sound_image'))
		    {
		        objid('sound_image').style.webkitAnimation     = "zhuan 3s linear infinite";
		    }
		    
		    bplay = 1;
		}

	
	
	function trim(str)
	{ 
		return str.replace(/(^\s*)|(\s*$)/g,'');
	} 


	function loadingdiv_out()
	{
		objid('loadingdiv').style.webkitAnimation = 'fadeout 0.3s linear both';
		if(navigator.userAgent.indexOf('Windows Phone')!=1)
		{
			objid('loadingdiv').style.display = 'none';
		}

		setTimeout(function()
		{
			objid('loadingdiv').style.display = 'none';
		},400)
	}
	</script>

<div id='enddiv'>
    <div id='title_div'>
        <div id='title_text' style='top:0px;left:0px;right:0px;bottom:0px;vertical-align:middle;display:table-cell;'></div>
    </div>
    <div id='title_line'></div>
    <div id='reshowbtn' ontouchstart='reload_scene()'>再看一遍</div>
</div>

<div id='loadingdiv'>
<!-- 	<div class='loadingword'>相册正在加载中<br>请稍等……</div> -->
</div>
	
<script>

        console.info("wh:"+ $("#container").width()+"hi:"+$("#container").height());
        console.info("wh:"+ $("#loadingdiv").width()+"hi:"+$("#loadingdiv").height());
        console.info("wh:"+ $("#enddiv").width()+"hi:"+$("#enddiv").height());
         $("#viewer-iframe").width($("#enddiv").width());
        $("#viewer-iframe").height($("#enddiv").height()); 
</script>