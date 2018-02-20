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
        var index = 1;
        var index_last = 1;
        var _speedMark = new Date();
        var canShow = true;
      
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
                    // if(phoneScale < 2)
                    // {
                        document.write('<meta name="viewport" content="width=500, minimum-scale = '+ phoneScale +', maximum-scale = '+ phoneScale +', target-densitydpi=device-dpi">');
                         
                    // }
                    // else
                    // {
                    //     document.write('<meta name="viewport" content="target-densitydpi=320, width=500, min-height:850px, user-scalable=no"/>');
                    // }
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
            console.info("=========w:"+w +"--h:"+h);
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
				$.ajax({
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
		         	 }
		    	}); 
//slider_images_url.push('http://img.mp.itc.cn/upload/20170221/d53ee4b494c64d45adee300a775b4fdf_th.jpeg');
//slider_images_url.push('http://imgsrc.baidu.com/baike/pic/item/7aec54e736d12f2ee289bffe4cc2d5628435689b.jpg');
//slider_images_url.push('http://120.25.74.162:808/aj/service/img?img=/clt/1478866988150.jpg');
var date = 20170221;
var zan_num = 0;
var e_bookid = '';
//进入相册中间的描述文字zouj
var e_desc = '';
//{"de":"\u6211\u7528\u7167\u7247\u5236\u4f5c\u4e00\u4e2a\u76f8\u518c\uff0c\u5feb\u6253\u5f00\u770b\u770b\u5427"}["de"];
var e_openid = null;
var e_scene = 'qingren17';
var editmode = false;
var wxid = 'mxq';
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
@-webkit-keyframes xinshan
{
    from{opacity: 1}
    to{opacity: 0}
}
@-webkit-keyframes xinshan1
{
    from{-webkit-transform:translate(0px,-4px) scale(0.4)}
    to{-webkit-transform:translate(0px,4px) scale(0.3)}
}
@-webkit-keyframes xinshan2
{
    from{-webkit-transform:translate(0px,-4px) scale(0.3)}
    to{-webkit-transform:translate(0px,4px) scale(0)}
}
@-webkit-keyframes xiabuhuadong
{
    from{-webkit-transform:translate(-100px,0px) }
    to{-webkit-transform:translate(600px,0px) }
}
@-webkit-keyframes xiabuhuadongshan
{
    0%{-webkit-transform:translate(-100px,0px) scale(1.2) }
    10%{-webkit-transform:translate(-30px,0px) scale(0.3) }
    20%{-webkit-transform:translate(40px,0px) scale(1.2) }
    30%{-webkit-transform:translate(110px,0px) scale(0.3) }
    40%{-webkit-transform:translate(180px,0px) scale(1.2) }
    50%{-webkit-transform:translate(250px,0px) scale(0.3) }
    60%{-webkit-transform:translate(320px,0px) scale(1.2) }
    70%{-webkit-transform:translate(390px,0px) scale(0.3) }
    80%{-webkit-transform:translate(460px,0px) scale(1.2) }
    90%{-webkit-transform:translate(530px,0px) scale(0.3) }
    100%{-webkit-transform:translate(600px,0px) scale(1.2)}
}
@-webkit-keyframes xiabuhuadong1
{
    from{-webkit-transform:translate(-100px,0px) scale(1.2)}
    to{-webkit-transform:translate(600px,0px) scale(1.2)}
}

@-webkit-keyframes filldown
{
    from{-webkit-transform:translate(0px,-200px);}
    to{-webkit-transform:translate(0px,820px);}
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
@-webkit-keyframes titlewaiin
{
    from{-webkit-transform:translate(0px,0px);}
    to{-webkit-transform:translate(0px,500px);}
}
@-webkit-keyframes titleneiin
{
    from{-webkit-transform:translate(0px,0px);}
    to{-webkit-transform:translate(0px,-500px);}
}
@-webkit-keyframes titlebgin
{
    from{-webkit-transform:translate(0px,0px);}
    to{-webkit-transform:translate(0px,-1000px);}
}
@-webkit-keyframes titlewaiout
{
    from{-webkit-transform:translate(0px,500px);}
    to{-webkit-transform:translate(0px,1000px);}
}
@-webkit-keyframes titleneiout
{
    from{-webkit-transform:translate(0px,-500px);}
    to{-webkit-transform:translate(0px,-1000px);}
}
@-webkit-keyframes titlebgout
{
    from{-webkit-transform:translate(0px,-1000px);opacity: 1;}
    to{-webkit-transform:translate(0px,-1000px);opacity: 0;}
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
   <!--  <img src='http://img1.ph.126.net/K8L6zZtlksJH17jkPCW4Hw==/2053922905157786659.png'>  -->
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

<!--背景图片zouj 
    <img src='https://qnphoto.kagirl.cn/pic/qingren17/shang.png'>
    
-->
</div>

 

<script>
function  id (name) 
{
    return document.getElementById(name);
}


function showtitle()
{
    id('titlebg').style.webkitAnimation='titlebgin 3s linear both';
    id('biaoti').style.webkitAnimation='titlewaiin 3s 3s linear both';
    id('titlecontent').style.webkitAnimation='titleneiin 3s 3s linear both';
    timeout[6]=setTimeout(function(){bgxin(0)},3000);
}
function bgxin(num)
{
    timeout[7]=setTimeout(function(){
        var xin = 'tbgxin'+ num;
        var j=num+8;
        var xin2 = 'tbgxin'+j;
        console.log(xin)
        id(xin).style.display='block';
        id(xin2).style.display='block';
        num++;
        if(num==8)
        {
            return;
        }
        bgxin(num);
    },300)
}
function bgxinout(num)
{
    timeout[9]=setTimeout(function(){
        var xin = 'tbgxin'+ num;
        var j=num+8;
        var xin2 = 'tbgxin'+j;
        console.log(xin)
        id(xin).style.display='none';
        id(xin2).style.display='none';
        num++;
        if(num==8)
        {
            return;
        }
        bgxinout(num);
    },300)
}

function liangziyun_kawa()
{
    setTimeout(function(){
    id('biaoti').style.webkitAnimation='titlewaiout 3s linear both';
    id('titlecontent').style.webkitAnimation='titleneiout 3s linear both';
    id('titlebg').style.webkitAnimation='titlebgout 1s 3s linear both';
    bgxinout(0);
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
    console.info("****************"+index_last+"**"+slider_images_url.length)
        
        if((index+1 == slider_images_url.length) || slider_images_url.length == 0){
   			 console.info("*@@@@@@@***************")
        	canShow = false;
        	load_images();
        }else{
            step_load();
        }
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
    if(image_url_index == Onload_imgs_url.length){
	      image_url_index = 0;
	      canShow = false;
    }
}

var get_pid = function(url)
{
	if(url != undefined && url != null && url != ''){
	    var index1 = url.indexOf("?");
	    if(index1 != -1)
	    {
	        url = url.substr(0, index1);
	    }
	    return url.toString().substr(url.lastIndexOf("/") + 1);
	}else{
		return "";
	}
}

//每次执行加载后5张图片
var bl_keepload = 'first';
var step_loadnum = 5;
var img_max_width = 300;
var img_max_height=450;

function step_load()
{
	
    var load_num = 0
   console.info("========="+ slider_images_url.length);
    //初步加载X张
    if(image_url_index  == 0 && bl_keepload == 'first' && canShow == true)
    {
        console.log('loading continue');
        if(slider_images_url.length > step_loadnum)
        {
            load_num = step_loadnum;
            bl_keepload = 'next';
        }
        else
        {
           // load_num = slider_images_url.length;
            load_num = 0;
            bl_keepload = 'end';
        }
        for(var i = 0; i< load_num; i++)
        {
            var img=new Image();
            img.index=i;
		//	console.log('-------'+slider_images_url[i]);
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
        console.info("!!!!!"+i+"!!!"+(image_url_index + step_loadnum + load_num))
        if(i+1 == slider_images_url.length){
        	console.info("$$$$$$$$$$$$$");
        	slider_images_url = [];
        }else{
            var img=new Image();
            img.index=i;
            img.src=slider_images_url[i];
            img.onload=image_onload;
            img.onerror= image_onerror;
            Onload_imgs_url[i] = 'loading';
        }
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
    //showtitle();

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
    console.log("!@#"+(have_num+error_num)+"!@#"+index);
    // console.log(have_num + '-' + error_num);

    //if((have_num + error_num >= step_loadnum ||slider_images_url.length == (have_num+error_num)) && canshow == true)
    if((have_num + error_num >= step_loadnum ||slider_images_url.length == index) && canshow == true)
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
   /* for(var i =0; i<16;i++)
    {
        var xin = 'tbgxin'+ i;
        console.log(xin)
        id(xin).style.display='none';
    } */
    var itemlist = ['biaoti','titlecontent','titlebg','div1','div2','div3','div4','div5'];

    for(var i =0; i<itemlist.length;i++)
    {
        id(itemlist[i]).style.webkitAnimation = '';
    }

}
call_me(load_images);
</script>


	<!-- <link type="text/css" rel="stylesheet" href="css/guanzhu.css?ver=3" />  -->
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


		@-webkit-keyframes aa_out_bounce_left
		{
		    0%{
		        -webkit-transform:translateX(0)
		    }
		    20%{
		        -webkit-transform:translateX(20px)
		    }
		    100%{
		        -webkit-transform:translateX(-125px)
		    }
		}
		@-webkit-keyframes aa_in_bounce_center
		{
		    0%{
		        opacity:0;
		        -webkit-transform:scale(.3)
		    }
		    50%{
		        opacity:1;
		        -webkit-transform:scale(1.05)
		    }
		    70%{
		        -webkit-transform:scale(.9)
		    }
		    100%{
		        -webkit-transform:scale(1)
		    }
		}
		@-webkit-keyframes jinbi
		{
			from {-webkit-transform: rotateY(0deg);}
			to   {-webkit-transform: rotateY(180deg);}
		}
		.jubao-item
		{
			position: relative;
			float: left;
			width: 360px;
			height: 55px;
			top: 0px;
			font-size: 20px;
			line-height: 55px;
			margin-left: 20px;
			border-bottom: 1px solid #EBEBEB;
		}
		
		@-webkit-keyframes guangzhu_ani
		{
			from  {-webkit-transform: scale(1);opacity: 1}
			to    {-webkit-transform: scale(1.05);opacity: 1}
		}
		@-webkit-keyframes che_ani
		{
		    0%    {-webkit-transform: translate(-20px,0px);opacity: 1}
		    8%  {-webkit-transform: translate(100px,0px) scale(0.95,1.05);opacity: 1}
		    10%  {-webkit-transform: translate(100px,0px) scale(1,1);opacity: 1}
		    12%   {-webkit-transform: translate(100px,0px); opacity: 1}
		    14%   {-webkit-transform: translate(100px,0px); opacity: 1}
		    16%   {-webkit-transform: translate(100px,0px); opacity: 1}
		    18%   {-webkit-transform: translate(100px,0px); opacity: 1}
		    20%   {-webkit-transform: translate(100px,0px); opacity: 1}
		    45%   {-webkit-transform: translate(100px,0px) scale(0.97,1.03); opacity: 1}
		    50%   {-webkit-transform: translate(100px,0px); opacity: 1}
		    60%   {-webkit-transform: translate(200px,0px); opacity: 0}
		    100%  {-webkit-transform: translate(200px,0px); opacity: 0}
		}
		@-webkit-keyframes che1_ani
		{
		    0%    {opacity: 1}
		    12%   {opacity: 1}
		    14%   {opacity: 0.5}
		    16%   {opacity: 1}
		    18%   {opacity: 0.5}
		    20%   {opacity: 1}
		    100%  {opacity: 1}

		}
		@-webkit-keyframes baoming
		{
		    0%  {opacity: 0}
		    58% {opacity: 0;-webkit-transform: scale(1.4);}
		    59% {opacity: 1;-webkit-transform: scale(1.4);}
		    70% {opacity: 1;-webkit-transform: scale(0.95);}
		    72% {opacity: 1;-webkit-transform: scale(1);}
		    80% {opacity: 0.8;-webkit-transform: scale(1.1);}
		    88% {opacity: 1;-webkit-transform: scale(1);}
		    93% {opacity: 0;-webkit-transform: scale(1.3);}
		    100%{opacity: 0}
		}
		@-webkit-keyframes shijia
		{
		    0%  {-webkit-transform: rotate(20deg) scale(0.8);opacity: 0}
		    10% {-webkit-transform: rotate(20deg) scale(0.75);opacity: 1}
		    20% {-webkit-transform: rotate(20deg) scale(0.85);}
		    28% {-webkit-transform: rotate(20deg) scale(0.75);}
		    38% {-webkit-transform: rotate(20deg) scale(0.85);}
		    47% {-webkit-transform: rotate(20deg) scale(0.75);opacity: 1}
		    55% {-webkit-transform: rotate(20deg) scale(0.90);opacity: 0}
		    100%{opacity: 0}

		}
		@-webkit-keyframes biaozhi_ani
		{
		    0%  {opacity: 0}
		    10% {opacity: 0}
		    20% {opacity: 1}
		    45% {opacity: 1}
		    55% {opacity: 0}
		    100%{opacity: 0}
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
    z-index: 10000;
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
.gspan1
{
	-webkit-animation: spanmove 3s linear infinite;
	position: absolute;
	left: 50px;
}
.gspan2
{
	-webkit-animation: spanmove 3s 0.6s linear infinite;
	position: absolute;
	left: 75px;
}
.gspan3
{
	-webkit-animation: spanmove 3s 1.2s linear infinite;
	position: absolute;
	left: 100px;
}
.gspan4
{
	-webkit-animation: spanmove 3s 1.8s linear infinite;
	position: absolute;
	left: 125px;
}
.gspan5
{
	-webkit-animation: spanmove 3s 2.4s linear infinite;
	position: absolute;
	left: 150px;
}
@-webkit-keyframes spanmove
{
	0%   {-webkit-transform: scale(1);}
	10%  {-webkit-transform: scale(1.5);}
	20%  {-webkit-transform: scale(1);}
	100% {-webkit-transform: scale(1);}
}
#letter
{
	/*position: fixed;*/
/*	left: 25px;
	bottom: 30px;*/
	width: 120px;
	height: 80px;
	/*-webkit-animation: lettermove 2s linear infinite alternate;*/
	z-index: 10000;
	position: absolute;
	top: 0px;
}
#letterpicdiv
{
/*	position: fixed;
	left: 25px;
	bottom: 30px;
	width: 120px;
	height: 80px;
	-webkit-animation: lettermove 2s linear infinite alternate;
	z-index: 10000;	
	display: none;*/
    position: fixed;
    left: -19px;
    bottom: -12px;
    width: 120px;
    height: 80px;
    z-index: 10000;
    -webkit-transform: scale(0.6);
    display: none;
}
@-webkit-keyframes lettermove
{
	from  {-webkit-transform: rotate(15deg) scale(1);}
	to    {-webkit-transform: rotate(-15deg) scale(1);}
}
#letterdiv
{
	width: 500px;
	position: absolute;
	height: 815px;
	background-color: rgba(0,0,0,0.7);
	z-index: 10001;
	display: none;
}
.lettercon
{
	width: 350px;
	height: 450px;
	background-color: #fff;
	left: 75px;
	top: 150px;
	position: absolute;
}
.lettertitle
{
	width: 100%;
	height: 45px;
	background-color: #F44336;
	color: #fff;
	font-size: 25px;
	text-align: center;
	line-height: 45px;
}
.lettercontent
{
	width: 310px;
	margin-top: 15px;
	margin-left: 20px;
	font-size: 22px;
	line-height: 35px;
	text-align: center;
}
.letterimg
{
	width: 150px;
	text-align: center;
	height: 150px;
	border-radius: 75px;
	/*margin-left: 75px;*/
}
#lettername
{
	width: 100%;
	font-size: 18px;
	text-align: right;
	margin-top: 10px;
}
.newxiangce
{
    width: 250px;
    height: 50px;
    background-color: #F44336;
    border-radius: 10px;
    margin-top: 10px;
    color: #fff;
    margin-left: 50px;
    font-size: 24px;
    line-height: 50px;
    text-align: center;
    -webkit-animation: guangzhu_ani 0.7s linear infinite alternate;
    position: absolute;
    bottom: 20px;
}
a
{
	text-decoration: none;
}
.letterclose
{
	font-size: 40px;
	width: 50px;
	height: 50px;
	position: absolute;
	left: 305px;
	top: 0px;
	color: #fff;
	line-height: 45px;

}
#letternotice
{
	position: absolute;
	width: 30px;
	height: 30px;
	background-color: #ff0000;
	border-radius: 15px;
	left: 103px;
	top: -10px;
	z-index: 10000;
	color: #fff;
	font-size: 22px;
	text-align: center;
	line-height: 30px;
	font-weight: bold;
	display: none;
}
#notice
{
    position: absolute;
    left: 15px;
    bottom: 15px;
    border-radius: 3px;
    width: 90px;
    height: 61px;
    background-size: 90px 61px;
    background-image: url(https://qnphoto.kagirl.cn/new/jingcai.jpg);
    /* background-position: -34px -33px; */
     display: none; 
    box-shadow: 2px 2px 6px rgba(0,0,0,0.4);
    z-index: 10000;
    color: #fff;
    text-align: center;
    line-height: 30px;
    padding-top: 22px;
    font-size: 18px;
    -webkit-animation: lettermove 2s linear infinite alternate;

}
@-webkit-keyframes guanzhu_test
{
	0%    {-webkit-transform: translate(-400px,0px) scale(1.1);opacity: 1}
	10%   {-webkit-transform: translate(0px,0px) scale(1.1);opacity: 1}
	20%   {-webkit-transform: translate(0px,0px) scale(1.15);opacity: 1}
	30%   {-webkit-transform: translate(0px,0px) scale(1.1);opacity: 1}
	40%   {-webkit-transform: translate(0px,0px) scale(1.15);opacity: 1}
	50%   {-webkit-transform: translate(0px,0px) scale(1.1);opacity: 1}
	60%   {-webkit-transform: translate(0px,0px) scale(1.15);opacity: 1}
	70%   {-webkit-transform: translate(0px,0px) scale(1.1);opacity: 1}
	80%   {-webkit-transform: translate(0px,0px) scale(1.15);opacity: 1}
	90%   {-webkit-transform: translate(0px,0px) scale(1.1);opacity: 1}
	94%   {-webkit-transform: translate(0px,0px) scale(1.2);opacity: 1}
	100%  {-webkit-transform: translate(0px,0px) scale(0.5);opacity: 0}
}
#madiv
{
	width: 500px;
	height: 815px;
	background-color: rgba(0,0,0,0.8);
	position: absolute;
	z-index: 20000;
	top: 0px;
	display: none;
}
.closema
{
    width: 70px;
    height: 70px;
    top: 0px;
    position: absolute;
    left: 430px;
}
#maimg
{
    position: absolute;
    width: 100px;
    bottom: 0px;
    z-index: 10000;
    display: none;
}
#guanzhu_container
{
/*	visibility:hidden;
	text-align:center;
	line-height:50px;
	font-size:22px;
	position:fixed;
	bottom:20px;
	left:130px;
	width:240px;
	color: #fff;
	border-radius: 5px;
	height:50px;
	z-index:10000;
	display:none;
	font-weight:bold;
	background-color: #09bb07;*/
	-webkit-animation: guangzhu_ani 0.7s linear infinite alternate;
    visibility: visible;
    text-align: center;
    line-height: 50px;
    font-size: 22px;
    position: fixed;
    bottom: -5px;
    left: 107px;
    width: 280px;
    color: rgb(255, 255, 255);
    border-radius: 5px;
    height: 87px;
    z-index: 10000;
    display: none; 
    font-weight: bold;
    background-image: url(https://qnphoto.kagirl.cn/new/mybtn.png);
    background-size: 280px 87px;
}
@-webkit-keyframes  songhua
{
    0%   {opacity: 0;-webkit-transform: translate(0px,0px);-webkit-animation-timing-function: ease-out;}
    20%  {opacity: 1;-webkit-transform: translate(0px,-15px);}
    40%  {opacity: 1;-webkit-transform: translate(0px,-30px) rotate(0deg);-webkit-animation-timing-function: linear;}
    70%  {opacity: 1;-webkit-transform: translate(-110px,-170px) scale(0.65) rotate(-5deg);}
    100% {opacity: 0;-webkit-transform: translate(-220px,-310px) scale(0.3) rotate(-10deg);}
}
#finishnotice
{
    width: 500px;
    height: 43px;
    background-color: rgba(0,0,0,0.6);
    position: absolute;
    top: -43px;
    z-index: 10000;
    color: #fff;
    font-size: 20px;
    text-align: center;
    line-height: 44px;
}
@-webkit-keyframes finish-ani
{
	0%   {-webkit-transform: translate(0px,0px);}
	20%  {-webkit-transform: translate(0px,43px);}
	80%  {-webkit-transform: translate(0px,43px);}
	100% {-webkit-transform: translate(0px,0px);}
}
#hidebtn
{
    position: absolute;
    top: 0px;
    font-size: 15px;
    left: 5px;
    width: 103px;
    height: 30px;
    border: 1px solid #fff;
    border-radius: 5px;
    top: 7px;
    line-height: 30px;
    text-align: center;
}
@-webkit-keyframes addpicnotice
{
	0%   {opacity: 0}
	10%  {opacity: 1}
	90%  {opacity: 1}
	100% {opacity: 0}
}
/*#savepic
{
	position: fixed;
    width: 90px;
    height: 50px;
    background-color: rgba(0,0,0,0.5);
    color: white;
    text-align: center;
    bottom: 20px;
    left: 0px;
    padding: 0px;
    margin: 0px;
    font-size: 14pt;
    z-index: 9999;
    line-height: 50px;
    border: 1px solid #fff;
    border-top-right-radius: 25px;
    border-bottom-right-radius: 25px;
    display: none;
}*/
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

		function share_url()
		{

			// if(editmode == false && (wxid == 'xinqing' || wxid == 'mofa' || wxid == 'youpin' || wxid == 'youmei' || wxid == 'xingfu' || wxid == 'sjyy' || wxid == 'keji' || wxid == 'youran' || wxid == 'donggan' || wxid == 'dongtai') )
			// {
				// objid('guanzhu_container').style.display = 'block';
			// }

			var locationurl = window.location.href;
			if(locationurl.indexOf('hidden')!=-1)
			{
				objid('guanzhu_container').style.display = 'none';
			}

			if(locationurl.indexOf('noshow')!=-1)
			{
				objid('guanzhu_container').style.display = 'none';
			}

			if(locationurl.indexOf('more=true')!=-1)
			{
				objid('morexiangce').style.display = 'block';
				if(!tbssaveoff)
				{
					objid('savepic').style.display = 'block';
				}
			}

			// objid('guanzhu_container').style.display = 'none';
			var index = random(0, 5);
			index = index>=5?4:index;
			// var index = 0;

			// if(((e_bookid == 'KPFArkq_BczWB-JZomFBnzTU-XRIgcos' && wxid == 'chuanqi') || (e_bookid == 'dD2nhG888UwtloewAuQhNpNkAKLhhjMbPVmwpJ5UxaM' && wxid == 'kawa') ) && editmode == false && e_scene == 'qian')
			// 	objid('fudai').style.display = 'block';


			// if(wxid == 'youyi' || shareid == 'youyi')
			// {
			// 	return "http://"+wxid+index+".kawabody.cn/kphoto/doubleshowbook.php?bookid=" + e_bookid + "&wxid=" + wxid + '&shareid=' + shareid;
			// }
			var urlname = '.kagirl.cn';
			if(locationurl.indexOf('kawabody')!=-1)
			{
				urlname = '.kawabody.cn';
			}

			// if(e_bookid == 'KPFArkq_BcwZfjCqkKubod2ZRYlNdNpb' && wxid == 'manman')
			// {
			// 	var index = random(0, 5);
			// 	return "http://manman"+index+".kawabody.cn/kphoto/doublebook.php?bookid=" + e_bookid + "&wxid=" + wxid + '&shareid=' + shareid+ "&onmake=" + onmake;
			// }


			if((wxid == 'xinqing' || wxid == 'liti' || wxid == 'dongtai'  || wxid == 'shiguang' || wxid == 'xuanmei' || wxid == 'zuimei' || wxid == 'chuangyi' || wxid == 'jingdian' || wxid == 'huoli' || wxid == 'quwei' || wxid == 'guangying' || wxid == 'qingling' || wxid == 'yywm' || wxid == 'kuaiyi' )&& locationurl.indexOf('showid')!=-1)
			{
				return "http://"+wxid+index+urlname+"/kphoto/doublebook.php?showid="+wxid+"&bookid=" + e_bookid + "&wxid=" + wxid + '&shareid=' + shareid + "&onmake=" + onmake;
			}
			if(shareid == "xuandong" && location.href.indexOf("app=xdxj") != -1)
			{
				return "http://"+wxid+index+urlname+"/kphoto/doublebook.php?bookid=" + e_bookid + "&wxid=" + wxid + '&shareid=' + shareid + "&app=xdxj"+ "&onmake=" + onmake;
			}

			return "http://"+wxid+index+urlname+"/kphoto/doublebook.php?bookid=" + e_bookid + "&wxid=" + wxid + '&shareid=' + shareid+ "&onmake=" + onmake;

		}

		function share_img()
		{
			var url = slider_images_url[0];
			var index = url.indexOf("?");
			if(index != -1)
			{
				return url.substr(0, index);
			}
			return url;
		}

		function on_weixin_share()
		{
			if(!wx)
			{
				return;
			}
			try{
				wx.hideAllNonBaseMenuItem();
				// var index = random(0, 5);
				// console.log('wxshow' + index);
				// if(e_bookid == 'KPFArkq_BcwZfjCqkKubod2ZRYlNdNpb' && wxid == 'manman' && index != 0)
				// {
				// 	wx.showMenuItems({
				// 	    menuList: ["menuItem:share:qq", "menuItem:share:QZone", "menuItem:favorite", "menuItem:profile", "menuItem:refresh", "menuItem:copyUrl"] // 要显示的菜单项，所有menu项见附录3
				// 	});						
				// }
				// else if(e_bookid == 'dD2nhG888UwSF8zBOtStmbqzioD8I1gWPVmwpJ5UxaM' && wxid == 'kawa' && index != 0)
				// {
				// 	wx.showMenuItems({
				// 	    menuList: ["menuItem:share:qq", "menuItem:share:QZone", "menuItem:favorite", "menuItem:profile", "menuItem:refresh", "menuItem:copyUrl"] // 要显示的菜单项，所有menu项见附录3
				// 	});						
				// // }
				// else
				// {
					wx.showMenuItems({
					    menuList: ["menuItem:share:appMessage", "menuItem:share:timeline", "menuItem:share:qq", "menuItem:share:QZone", "menuItem:favorite", "menuItem:profile", "menuItem:addContact", "menuItem:refresh", "menuItem:copyUrl"] // 要显示的菜单项，所有menu项见附录3
					});					
				// }

				var desc = e_desc.replace("<br>", "\n").replace("<br/>", "\n");



				wx.onMenuShareAppMessage({
				    title   : desc,
				    desc    : '这个相册，送给你',
				    link    : share_url(),
				    imgUrl  : share_img(),
				    type    : 'link',
				    success: function () { 
				            share_callback('message');
				    },
				    cancel: function () { 
				    }
				});




				wx.onMenuShareTimeline({
				    title  : desc,
				    link   : share_url(),
				    imgUrl : share_img(),
				    success: function () { 
				            share_callback('timeline');
				    },
				    cancel: function () { 
				    }
				});
				wx.onMenuShareQQ({
				    title   : '音乐相册',
				    desc    : desc,
				    link    : share_url(),
				    imgUrl  : share_img(),
				    type    : 'link',
				    success: function () { 
				            share_callback('message');
				    },
				    cancel: function () { 
				    }
				});
				wx.onMenuShareQZone({
				    title   : '音乐相册',
				    desc    : desc,
				    link    : share_url(),
				    imgUrl  : share_img(),
				    type    : 'link',
				    success: function () { 
				            share_callback('message');
				    },
				    cancel: function () { 
				    }
				});
			}
			catch(e){

			}
		}

		function share_callback(type)
		{
			if(editmode == false)
			{
				// XMLHttp.sendReq('GET', 'adpv.php?click=8', '', function(){})
			}
			else
			{

				XMLHttp.sendReq('GET', 'tongjiscene.php?scene='+e_scene+'&type=edit', '', function(){});
				// XMLHttp.sendReq('GET', 'adpv.php?click=9', '', function(){})
				try{

					if(tbssaveoff)
					{
						XMLHttp.sendReq('GET', 'adpv.php?click=saveshare', '', function(){
					    });
					}
					else{
						XMLHttp.sendReq('GET', 'adpv.php?click=nosaveshare', '', function(){
					    });
					}	
				}catch(e){
				}
			}
			var url = "editbook.php?type=" + type + "&bookid=" + e_bookid + "&wxid=" + wxid + "&shareid=" + shareid;

			if(e_openid != null)
			{
				url = url + '&openid=' + e_openid;
			}

			url = url + "&desc=" + encodeURIComponent(e_desc);

			if(e_music_url !== "")
			{
				url = url + "&music=" + encodeURIComponent(e_music_url);
			}

			url = url + "&scene=" +e_scene;

			if(gotomake == 'make' &&  istiaozhuan != 'no')
			{
				location.href = url;
				return;
			}
			else if(e_bookid == 'KPFArkq_BcwZfjCqkKubod2ZRYlNdNpb' && wxid == 'manman')
			{
				location.href = url;
				return;
			}

			// if(shareid == "xuandong" && location.href.indexOf("app=xdxj") != -1)
			// {
			// 	location.href = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIyNzA4NDU1Ng==#wechat_webview_type=1&wechat_redirect";
			// 	return;
			// }


			if(editmode == true)
			{
				var print_scenes = {
					"lovetrip" : 1,
					"xiangyin" : 1,
					"baby" : 1,
					"naxienian" : 1,
					"minion" : 1,
					"maxituan" : 1,
					"qingchun" : 1,
					"shengri" : 1,
					"shatan" : 1,
					"summer" : 1
				}
				if(wxid == 'lzy' && print_scenes[e_scene] && slider_images_url.length >= 12)
				{
					XMLHttp.sendReq('GET',url, '', function(){});
					location.href = 'printinfo.php?wxid='+wxid+'&bookid='+e_bookid+'&openid='+e_openid+'&scene='+e_scene;
				}
				else if((wxid == 'lzy') && (e_scene == 'naxienian' || e_scene == 'shengri') && slider_images_url.length>21)
				{
					XMLHttp.sendReq('GET',url, '', function(){});
					location.href = 'http://nap.naturalgift.cc/nap/doc/print/buttons.php?wxid='+wxid+'&bookid='+e_bookid+'&openid='+e_openid+'&scene='+e_scene;
				}
				else
					XMLHttp.sendReq('GET', url, '', function(){});
					// location.href = url;

			}

			else
			{
				if(editmode == false && objid('guanzhu_container').style.display != 'none')
				{
					XMLHttp.sendReq('GET', url, '', function(){});
				}
				else
				{
					XMLHttp.sendReq('GET', url, '', function(){});	
				}
			
			}
			
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
			on_weixin_share();
			//setTimeout(get_address, 500);

			if((isfinish == 'yes')&&editmode == false)
				read_localstorage();
			var locationurl = window.location.href;
			// if(locationurl.indexOf('debug')!=-1)
			// {
				check_cookie();
			// }

			if(editmode == false)
			{
				var obj = document.getElementById("guanzhu_container");
				obj.style.visibility = 'visible';
				if(navigator.userAgent.match(/MicroMessenger/) == null)
				{
					// obj.innerHTML = "点我更多精彩";
					obj.style.display = 'none';
				}
				// objid('guanzhu_container').style.display = 'none';
				//var obj = document.getElementById("kawa-youli");
				//obj.style.visibility = 'visible';
			}
			var locationurl = window.location.href;
			if(locationurl.indexOf('hidden')!=-1)
			{
				objid('guanzhu_container').style.display = 'none';
			}


			if(locationurl.indexOf('noshow')!=-1)
			{
				objid('guanzhu_container').style.display = 'none';
			}

			if(editmode == false && app == "xdxj")
			{
				objid('guanzhu_container').style.display = 'block';	
			}
			// if(isguangchang == 'guangchang')
				objid('guanzhu_container').style.display = 'none';
			if((isshow == 'show' || isshow == 'ma') && editmode == false)
			{
				objid('notice').style.display = 'block';
			}

			if(editmode == true && locationurl.indexOf('onmake=true')!=-1 && gotomake == 'make')
			{
				objid('backtomake').style.display = 'block';
			}
			else if( locationurl.indexOf('noshow=true') !=-1 || navigator.userAgent.match(/MicroMessenger/) == null )
			{
				//objid('notice').style.display = 'none';
				// objid('maimg').style.display = 'none';
			}
			else if(editmode == false && locationurl.indexOf('noshow=true') ==-1 && gotomake == 'make' && navigator.userAgent.match(/MicroMessenger/) != null)
			{
				objid('guanzhu_container').style.display = 'block';
			}

			if(tbssaveoff && isguangchang == "guangchang")
			{
				objid("saveoff").style.display = "block";
			}

			if(locationurl.indexOf('printmodule=true')!=-1)
			{
				objid("guanzhu_container").style.display = "none";
				var container = objid('morexiangce');
				container.style.display = "block";
				container.style.backgroundImage = "";
				container.innerText = "立即打印";
				container.onclick = function(){
					event.stopPropagation();
					location.href = "hzprint.php?wxid=" + wxid + "&bookid=" + e_bookid + "&scene=" + e_scene;
				}
			}
		}

		//创建存储cookie的函数
		function set_cookie(value)
		{
			// value = "";
			var locationurl = window.location.href;
			if(locationurl.indexOf('kawabody')!=-1)
				document.cookie = "books="+value+';domain=.kawabody.cn';
			else
				document.cookie = "books="+value+';domain=.kagirl.cn';
			// alert('write:'+document.cookie);
		}

		function get_cookie(c_name)
		{
			// alert(document.cookie);
			if(document.cookie.length > 0)
			{	
				var c_start = document.cookie.indexOf(c_name + '=');
				if (c_start != -1)
				{
					c_start = c_start + c_name.length + 1;
					var c_end = document.cookie.indexOf(';', c_start);
					if(c_end == -1)
						c_end = document.cookie.length;
					return unescape(document.cookie.substring(c_start,c_end));
				}

			}
			return '';
		}


		function check_cookie()
		{


			var thisbook = e_bookid + '&' + wxid;

			books = get_cookie('books');
			// alert(books);
			console.log('cookie:'+books);
			

			var locationurl = window.location.href;

			if(editmode == true || locationurl.indexOf('hidden') != -1)
			{
				objid('guanzhu_container').style.display = 'none';
				if(objid('guanzhubtn'))
				{
					objid('guanzhubtn').style.display = 'none';
					objid('reshowbtn').style.left = '163px';
				}

				//还没存进去
				cookie_str = '';
				if(books != null && books != '' && books.indexOf(thisbook) == -1)
				{
					cookie_str = books+ ':' + thisbook;

				}
				else if(books.indexOf(thisbook) != -1)
				{
					return;
				}
				else if(books == null || books == '')
				{
					cookie_str = thisbook;
				}

				set_cookie(cookie_str);
			}
			else
			{
				if(books !=null && books != '' && books.indexOf(thisbook) !=-1)
				{
					objid('guanzhu_container').style.display = 'none'
					if(objid('guanzhubtn'))
					{
						objid('guanzhubtn').style.display = 'none';
						objid('reshowbtn').style.left = '163px';
					}
				}
				else
				{
					objid('guanzhu_container').style.display = 'block';
				}
			}


			// objid('guanzhu_container').style.webkitAnimation = 'guangzhu_ani 0.8s linear infinite alternate';


		}



		call_me(on_wx_music_init);

		//音乐播放

		var music_header   = 'http://kawaweika.qiniudn.com/sound/';
		var e_music_player = new Audio();

		function play_music()
		{
		    if(e_music_url == '')
		    {
		        return ;
		    }

		    if(e_music_url.indexOf('qqmusic') != -1)
		    {
		    	mymusic = 'https://qnphoto.kagirl.cn/music/688042.m4a';
		    }
		    else
		    {
		    	mymusic = e_music_url;
		    }

		    e_music_player.src  = mymusic;
		    e_music_player.loop = 'loop';

		    console.log(e_music_player.src);
		    e_music_player.onerror = function(){
		    	if(e_music_url.indexOf("http://sound.kagirl.net") != -1)
		    	{
		    		var url = e_music_url.replace("http://sound.kagirl.net", "https://music.kagirl.cn");
		    	}
		    	else
		    	{
		    		var url = e_music_url.replace("https://music.kagirl.cn", "http://sound.kagirl.net");	
		    	}

		    	e_music_player.src = url;
		    	e_music_player.play();
		    	e_music_player.onerror = null;
		    }
		    
		    e_music_player.play();
		    
		    //检查音乐是否缓冲成功
		    setTimeout(function()
		    {
				var timeRanges = e_music_player.buffered;
				var timeBuffered = timeRanges.end(timeRanges.length - 1);
				var bufferPercent = timeBuffered /e_music_player.duration; 
				console.log('music:'+bufferPercent);

				if(isNaN(bufferPercent) || bufferPercent == 0)
				{
			    	if(e_music_url.indexOf("http://sound.kagirl.net") != -1)
			    	{
			    		var url = e_music_url.replace("http://sound.kagirl.net", "http://kawaweika.qiniudn.com");
			    	}
			    	else
			    	{
			    		var url = e_music_url.replace("https://music.kagirl.cn", "http://sound.kagirl.net");	
			    	}
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
		    sound_div.onclick = switchsound;

		    bg_htm  = "<img id='sound_image' src='https://tu.kagirl.cn/pic/music_note_big.png' style='-webkit-animation:zhuan 2s linear infinite'>";
		    txt_htm = "<div id='music_txt' style='color:white;position:absolute;left:-40px;top:30px;width:60px'></div>"

		    sound_div.innerHTML = bg_htm  + txt_htm;

		    document.body.appendChild(sound_div);
		} 

		var bplay = 0;              
		
		function switchsound()
		{
		    au = e_music_player;
		    ai = objid('sound_image');
		    if(au.paused)
		    {
		        bplay = 1;
		        au.play();
		        ai.style.webkitAnimation     = "zhuan 2s linear infinite";
		        objid("music_txt").innerHTML = "打开";
		        objid("music_txt").style.visibility = "visible";
		        setTimeout(function(){objid("music_txt").style.visibility="hidden"}, 2500);
		    }
		    else
		    {
		        bplay = 0;
		        au.pause();
		        ai.style.webkitAnimation     = "";
		        objid("music_txt").innerHTML = "关闭";
		        objid("music_txt").style.visibility = "visible";
		        setTimeout(function(){objid("music_txt").style.visibility="hidden"}, 2500);
		    }
		}

		function visit_guanzhu()
		{
			if(gotomake == 'make')
			{
				location.href = 'makebook.php?wxid='+ wxid;
			}

			if(navigator.userAgent.match(/MicroMessenger/))
			{
				


			}
			else
			{
				// location.href = "http://article.mp.qq.com/index.php/preview/show?p=cd8c8cf61eb8c661b2163815a8f35a59&m=1442370092841159&i=0&t=c005e9f4d683429f35044a50ba3c4ab1&_wv=134217729&v=3&env=10003&mpu=33303236323133363530";
			}
		}

	function close_searchmusic()
	{
		objid('search_music').style.display = 'none';
		objid('search_music_result_div').innerHTML = '';
		e_music_player.play();
		reflush();
	}
	function search_musicshow()
	{
		objid('search_music').style.display='block';
		var width = document.body.clientWidth;
		var height = document.body.clientHeight;

		var div_height = 500/(width/height);
		objid('search_music_result').style.height = div_height-170 +'px';

	}
	add_music_xml = new XMLHttpRequest();
	function submit_addmusic () {

		var musicname = objid('music_name').value;
		if(musicname == null || musicname == '')
		{
			alert('音乐名称不能为空！');
		}
		else
		{
			var url = 'music_feedback.php?musicname='+musicname;
			add_music_xml.open('GET',url,true);
			add_music_xml.onreadystatechange = add_music_callback;
			add_music_xml.setRequestHeader('Content-type','application/x-www-form-urlencoded'); 
			add_music_xml.send(null);
		}
	}
	function trim(str)
	{ 
		return str.replace(/(^\s*)|(\s*$)/g,'');
	} 
	function add_music_callback () {
		if (add_music_xml.readyState == 4) 
		{
			if (add_music_xml.status== 200)
			{
				var result = add_music_xml.responseText;
				console.log(result);
				if(result.indexOf('1'))
				{
					objid('add_music_result').style.display = 'block';
				}
				else
					alert('提交失败，请稍后再试。')
			}
			// else
			// 	alert('提交失败，请稍后再试。');
		}
		// else
		// {
		// 	alert('提交失败，请稍后再试。');
		// }
	}
	music_xml = new XMLHttpRequest();
	function search_music () 
	{
		objid('search_music_result_div').innerHTML = '';
		objid('search_music_notice').style.display = 'block';

		var word = objid('search_musicname').value;
		word = trim(word);
		if(word == ''|| word == null || word == undefined)
		{
			alert('输入不能为空');
		}
		word = encodeURIComponent(word);

		// if(wxid == 'meiwen')
		// {
		// 	// url = 'http://photo.kagirl.cn/music/189/search.php?keyword='+word;
		// 	url = 'http://photo.kagirl.cn/music/search.php?music='+word;
			
		// 	objid('search_music_result_div').style.display = 'none';
		// }
		// else 
		// {
			url = '/music/search.php?music='+word;
			// url = 'http://photo.kagirl.cn/kphoto/musicsearch.php?music='+word;
		// }
		
		music_xml.open('GET',url, true);
		music_xml.onreadystatechange = SearchMusic_onCallback;
		music_xml.setRequestHeader('Content-type', 'application/x-www-form-urlencoded'); 
		music_xml.send(null);

	}

	function SearchMusic_onCallback()
	{
		
		if(music_xml.readyState ==4)
		{
			if(music_xml.status == 200)
			{
				var result = music_xml.responseText;
				result = JSON.parse(result);
				var obj = objid('search_music_result_div');
				// alert(result.length)
				if(result.length == undefined || result.length == 0)
				{	
					var music_value = objid('search_musicname').value;
					music_value = trim(music_value);
					obj.innerHTML = '<div style="line-height:30px;margin:50px 10px 10px 10px;font-size:16px;"><p style="font-size:22px;line-height30px;">找不到与<b>"'+music_value+'"</b>相关音乐</p></div>';
					obj.style.display = 'block';
				}
				else
				{
					var htm_txt = '';
					for(var i=0;i<result.length;i++)
					{
						htm_txt += '<div class="e-mp3box-item"><div class="e-mp3box-item-img"><img src="https://tu.kagirl.cn/icon/yinfu.png"></div><div id="search_music_result_text" class="e-mp3box-item-title" onclick="search_music_title_click('+"'"+result[i]['url']+"'"+','+i+')">'+result[i]['title']+'</div><div id="search_music_btn'+i+'" class="search-mp3box-item-ok" id="search-music-ok-'+i+'" onclick="search_music_select('+"'"+result[i]['musicname']+"'"+','+i+')">确定</div></div>';
					}
					obj.innerHTML = htm_txt;
					obj.style.display = 'block';
			// htm_txt += '<div class="e-mp3box-item-img"  id="e-music-img-'+(i+oks_num)+'"><img src="https://tu.kagirl.cn/icon/yinfu.png"></div>';

			// htm_txt += '<div class="e-mp3box-item-title" onclick="e_music_title_click(\''+url+'\','+(i+oks_num)+')">'+title+'</div>';
			
			// htm_txt += '<div class="e-mp3box-item-ok" id="e-music-ok-'+(i+oks_num)+'" onclick="e_music_select(\''+musicname+'\')">确定</div>';

			// 		var str = '<div class="e-mp3box-item-img"><img src="https://tu.kagirl.cn/icon/yinfu.png"></div><div id="search_music_result_text" class="e-mp3box-item-title" onclick="search_music_title_click('+"'"+result['url']+"'"+')">'+result['title']+'</div><div id="search_music_result_btn" class="e-mp3box-item-ok" id="e-music-ok-0" onclick="search_music_select('+"'"+result['musicname']+"'"+')">确定</div>';
			// 		obj.innerHTML = str;
			// 		obj.style.display = 'block';					
				}

				// objid('search_music_result_text').innerHTML = result['title'];
				// objid('search_music_result_text').onclick="e_music_title_click('"+result['url']+"',0)";
				// objid('search_music_result_btn').onclick = "e_music_select('"+result['musicname']+"')";
				// objid('search_music_result').innerHTML = result;
			}
			else
			{
				console.log('Error: '+ music_xml.status)
			}
		}

		objid('search_music_result_div').style.display = 'block';
		objid('search_music_notice').style.display = 'none';
	}
	function search_music_title_click(url,index)
	{
		var oks = document.getElementsByClassName('search-mp3box-item-ok');
		
		for(var i=0;i<oks.length;i++)
		{
			oks[i].style.display = 'none';
		}
		objid('search_music_btn'+index).style.display = 'block';
		// objid('e-music-ok-' + index).style.display = 'block';
		//objid('e-music-img-' + index).style.webkitAnimation = 'zhuan 2.5s linear infinite';

		if(e_music_player != null)
		{
		    e_music_player.pause();

		    if(objid('sound_image'))
		    {
		        objid('sound_image').style.webkitAnimation = "";
		    }
		}

		if(objid('e-player') == null)
	    {
	        var player  = document.createElement('audio');
	        player.id   = 'e-player';
	        player.src  = url;
	        player.loop = 'loop';
	        player.play();
	        document.body.appendChild(player);
	    }
		else if(objid('e-player').src == url)
		{
		    if(objid('e-player').paused)
		    {
		        objid('e-player').play();
		    }
		    else
		    {
		        objid('e-player').pause();
				objid('e-music-img-' + index).style.webkitAnimation = '';
		    }
		}
		else
		{
		    objid('e-player').src = url;
		    objid('e-player').play();
		}
	}

	function search_music_select(url)
	{
		objid('search_music').style.display = 'none';
		e_music_url = url;

		if(typeof(reload_scene) == "undefined")
		{
			var url = "editbook.php?";
		    url	= url + 'type=set_music';
		    url	= url + "&openid=" + e_openid;
		    url	= url + "&bookid=" + e_bookid;
		    url	= url + "&music=" + encodeURIComponent(e_music_url);
		    url = url + "&wxid=" + wxid;

		    XMLHttp.sendReq('GET', url, '', function(){
		    	location.reload();
		    });
		}
		else
		{
			tool_close();

			if(e_music_player)
			{
				if(!objid('cardsound'))
				{
					create_music();
					e_music_player.play();
				}
				
				e_music_player.src = objid('e-player').src;
				e_music_player.play();
			}
			else
			{
				create_music();
			}

			e_close_wind();
			var url = "editbook.php?";
		    url	= url + 'type=set_music';
		    url	= url + "&openid=" + e_openid;
		    url	= url + "&bookid=" + e_bookid;
		    url	= url + "&music=" + encodeURIComponent(e_music_url);
		    url = url + "&wxid=" + wxid;

		    XMLHttp.sendReq('GET', url, '', function(){
		    	//location.reload();
		    });
		}
	
		
	}

	
	function write_localstorage()
	{
		console.log('write');
		objid('like_div').onclick = havesonghua;


		var url = 'submit_like.php?bookid='+e_bookid+'&wxid='+wxid;
		XMLHttp.sendReq('GET',url,'',function()
		{
			// objid('like_heart').src='https://qnphoto.kagirl.cn/zan/like0.png';
			// objid('like_heart').style.webkitAnimation = 'heart_beating 0.4s linear 4 alternate';
			// objid('heart1').style.display = 'block';
			// objid('heart2').style.display = 'block';
			// objid('heart3').style.display = 'block';
			// objid('heart1').style.webkitAnimation = 'heart1_moving 1.6s 0.2s linear both';
			// objid('heart2').style.webkitAnimation = 'heart2_moving 1.6s 0.2s linear both';
			// objid('heart3').style.webkitAnimation = 'heart3_moving 1.6s 0.2s linear both';
			objid('songhua').style.display = 'block';
			objid('songhua').style.webkitAnimation = 'songhua 2s linear both';
			objid('like_num').innerHTML = zan_num +1;

			// var day = new Date();
			// var daystr = day.get
			var sendstr = e_bookid + wxid + date;

			if(localStorage.likestr == null || localStorage == undefined)
			{
				var like_arr = [];
			}
			else
			{
				var like_arr = JSON.parse(localStorage.likestr);
			}
				
			if(like_arr.length == 50)
			{
				like_arr.pop()
			}
			like_arr.unshift(sendstr);

			likestr = JSON.stringify(like_arr);
			// console.log(likestr);
			localStorage.likestr = likestr;

		})


	}


	function read_localstorage()
	{

		if(editmode == false )
		{
			objid('like_div').style.display = 'block';
		}
		console.log(localStorage.likestr);
		if(localStorage.likestr == null || localStorage == undefined)
		{
			objid('like_div').onclick = write_localstorage;
		}
		else
		{

			var like_arr = JSON.parse(localStorage.likestr);
			var sendstr = e_bookid + wxid + date;
			console.log(sendstr);
			for(var i=0;i<like_arr.length;i++)
			{
				if(like_arr[i] == sendstr)
				{
					objid('like_div').onclick = havesonghua;
					console.log('哈哈哈哈哈');
					// objid('like_heart').src = 'https://qnphoto.kagirl.cn/zan/like0.png';
					break;
				}
				else if(i == like_arr.length-1)
				{
					objid('like_div').onclick = write_localstorage;
					// console.log('canclick');
				}
			}
		}
		// console.log(objid("like_div").onclick);
		objid('like_num').innerHTML = zan_num;
	}
	
	function visit_guangchang()
	{
		var locationurl = location.href;
		if(shareid != '' && shareid != undefined)
		{
			if(locationurl.indexOf('hidden') != -1)
			{
				if(locationurl.indexOf('menu') != -1)
					location.href = 'http://photo.kagirl.cn/kphoto/menu.php?wxid='+shareid;
				else
					location.href = 'http://photo.kagirl.cn/kphoto/more1.php?back=true&wxid='+shareid;
			}
			else
				location.href = 'http://photo.kagirl.cn/kphoto/more1.php?back=true&show=true&wxid='+shareid;
		}
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

	function clickbtn()
	{
		XMLHttp.sendReq("get", 'tongjiscene.php?type=click', "", function(objXMLHttp){
		});
	}

	function saveoff()
	{
		location.href = "saveoffstat.php?type=guangchang&bookid=" + e_bookid + "&wxid=" + wxid + "&shareid=" + shareid;
	}
	function savepic()
	{
		var locationurl = location.href;
		location.href = locationurl.replace('showbook','psinfo1');

	}
	
	function hideguanzhu()
	{
		hidejubao();
		objid('guanzhu_container').style.display = 'none';
		objid('notice').style.display = 'none';
	}
	</script>


	<a id="guanzhu_container" ontouchstart="visit_guanzhu()" class="large"></a>

 <div id="savepic" onclick="savepic()">
 	保存图片
 </div>


<div id='like_div' style='position:absolute;width:100px;height:50px;background-color:rgba(0,0,0,0.5);border-radius: 0px 25px 25px 0px;top:60px;display:none;z-index:10000'>
	<div id='like_heart' style='position:absolute;left:44px;top:5px;width:50px;height:45px;background-color:44px;background-image:url(https://qnphoto.kagirl.cn/new/rose1.png)'></div>
	<div id='like_num' style='position:absolute;color:rgba(255,255,255,0.7);font-size:20px;top:0px;left:0px;width:50px;height:50px;text-align:center;line-height:50px;'></div>

<!-- 	<img src='https://qnphoto.kagirl.cn/zan/like0.png' id='heart1' style='position:absolute;width:25px;top:8px;left:44px;display:none'>
	<img src='https://qnphoto.kagirl.cn/zan/like0.png' id='heart2' style='position:absolute;width:25px;top:8px;left:63px;display:none'>
	<img src='https://qnphoto.kagirl.cn/zan/like0.png' id='heart3' style='position:absolute;width:25px;top:22px;left:52px;display:none'> -->

</div>
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