 		var _speedMark = new Date();
 		var slider_images_url = [];
 		var e_music_url
		//每次执行加载后5张图片
		var bl_keepload = 'first';
		var step_loadnum = 5;
		var last_index = 1;
		var reload = false;
		var isFinshed = false;
		var firstInPage = true;
		var inPageCount = 1;
       
		var w, h;
        function init_viewport()
        {
            if(/Android (\d+\.\d+)/.test(navigator.userAgent))
            {
                var version = parseFloat(RegExp.$1);

                if(version>2.3)
                {
                    var width = window.outerWidth == 0 ? window.screen.width : window.outerWidth;
                    w = width;
                    h = window.outerHeight == 0 ? window.screen.height : window.outerHeight;
                    var phoneScale = parseInt(width)/500;
                    phoneScale = 1;
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
            	var wd = document.documentElement.clientWidth; 
            	var hi = document.documentElement.clientHeight;
                //var phoneScale = parseInt(window.screen.width)/500;
                var phoneScale = parseInt(wd)/500;
                document.write('<meta name="viewport" content="width=500; min-height=700;initial-scale=' + phoneScale +'; user-scalable=no;" /> ');         //0.75   0.82
           		//w = 500; h = 750;
           		w = wd; h = hi;
            }
            else 
            {
                document.write('<meta name="viewport" content="width=500, height=750, initial-scale=0.67" /> ');         //0.75   0.82
				w = 500; h = 750;
            }
            $("#container").width(w);
       		$("#container").height(h);
       		$("#zp_big").width(w);
       		$("#zp_big").height(h);
       		$("#enddiv").width(w* 0.9);
       		$("#enddiv").height(h* 0.8);
            $("#viewer-iframe").width(w);
       		$("#viewer-iframe").height(h);
        }

        init_viewport();
        
        wx.config({
            debug     : false,
            appId     : '',
            timestamp : 10000,
            nonceStr  : '',
            signature : '',
            jsApiList : [
                'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareQZone', 'closeWindow', 'hideAllNonBaseMenuItem', 'showMenuItems', 'showAllNonBaseMenuItem', 'chooseImage', 'uploadImage']
            });
        
        var module_inits = [];

        var upload_permission = true;

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

        console.info("ua:"+ua)
        initPhoto();
        if(ua.match(/MicroMessenger/i) == 'micromessenger')
        {  
            wx.ready(load_init_modules);
        }
        else
        {
            onload = load_init_modules;
        }
        
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
      		
      		var e_music_title = '';
      		var tbssaveoff = false;
      		var isguangchang = '';
      		var isshow = '';
      		var gotomake = 'make';
      		var istiaozhuan = '';
      		var isfinish = '';
      		
      		
      		function colseCurrentDiv(){
      			$("#zp_big").hide();
      			$("#zhezhao").hide();
      			$("#zhezhao").css("opacity","0.6");
      		}
      		
      		
      		
      		
      		function  id (name) 
      		{
      		    return document.getElementById(name);
      		}

      		function liangziyun_kawa()
      		{
      			
      		   // setTimeout(function(){
      		   // id('biaoti').style.webkitAnimation='titlewaiout 3s linear both';
      		   // id('titlecontent').style.webkitAnimation='titleneiout 3s linear both';
      		   // id('titlebg').style.webkitAnimation='titlebgout 1s 3s linear both';
      		  //  },5000)
      		   if(reload){
      		   	//如果是重新浏览，减少延迟时间
      		    	timeout[0]=setTimeout(show1, 100);
      		   }else{
      		   	//如果是首次载入，延迟时间可根据进入动画的时间，适当调整延迟时间
      			    timeout[0]=setTimeout(show1, 8500);
      		   }
      		}
      		function showEndDiv(){
      			clearnode();
			    $("#enddiv").show();
			    $("#zhezhao").show();
			    //播放完了之后，停止播放，
  				isFinshed=true;
      		}
      		function show1()
      		{
      			console.info("show1:"+new Date());
  				var tempIndex = image_url_index + 1;
  				id('div5').style.webkitAnimation = '';
  				id('word5').style.top = '';
  				id('word5').style.bottom = '';
  				setImage('1');
  				id('div1').style.webkitAnimation = 'div1_in 8s ease both';
  				if( tempIndex != slider_images_url.length){
      				timeout[1] = setTimeout(show2, 8000)
      			}else{
      				setTimeout(showEndDiv, 8000)
      			}
      		}
      		
      		function show2()
      		{
      			console.info("show2:"+new Date());
      			var tempIndex = image_url_index + 1;
      		    id('div1').style.webkitAnimation = '';
      		    id('word1').style.top = '';
      		    id('word1').style.bottom = '';
      		    setImage('2');
      		    id('div2').style.webkitAnimation = 'div2_in 8s  ease both';
	      		  if( tempIndex != slider_images_url.length){
		      		    timeout[2] = setTimeout(show3, 8000)
	      			}else{
	      				setTimeout(showEndDiv, 8000)
	      			}
      		}
      		function show3()
      		{
      			console.info("show3:"+new Date());
      			var tempIndex = image_url_index + 1;
      		    id('div2').style.webkitAnimation = '';
      		    id('word2').style.top = '';
      		    id('word2').style.bottom = '';
      		    setImage('3');
      		    id('div3').style.webkitAnimation = 'div3_in 8s  ease both';
	      		  if( tempIndex != slider_images_url.length){
		      		    timeout[3] = setTimeout(show4, 8000)
	      			}else{
	      				setTimeout(showEndDiv, 8000)
	      			}
      		}
      		function show4()
      		{
      			console.info("show4:"+new Date());
      			var tempIndex = image_url_index + 1;
      		    id('div3').style.webkitAnimation = '';
      		    id('word3').style.top = '';
      		    id('word3').style.bottom = '';
      		    setImage('4');
      		    id('div4').style.webkitAnimation = 'div4_in 8s  ease both';
	      		  if( tempIndex != slider_images_url.length){
		      		    timeout[4] = setTimeout(show5, 8000)
	      			}else{
	      				setTimeout(showEndDiv, 8000)
	      			}
      		}
      		function show5()
      		{
      			console.info("show5:"+new Date());
      			var tempIndex = image_url_index + 1;
      		    id('div4').style.webkitAnimation = '';
      		    id('word4').style.top = '';
      		    id('word4').style.bottom = '';
      		    setImage('5');
      		    id('div5').style.webkitAnimation = 'div5_in 8s  ease both';
	      		  if( tempIndex != slider_images_url.length){
		      		    timeout[5] = setTimeout(show1, 8000)
	      			}else{
	      				setTimeout(showEndDiv, 8000)
	      			}
      		}

      		function downloadApp(){

      			alert($("#shopLogo").attr('src'));
      			alert($("#shopTel").text());
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

      		    if(bl_keepload != 'first' && (image_url_index % (step_loadnum -1 ) == 0))
      		    {
      		        step_load();
      		    }
      		    
      		    var orgWidth = image_size_width[image_url_index];
      		    var orgHight = image_size_height[image_url_index];
      		  console.info("原始图片:"+Onload_imgs_url[image_url_index]+"大小:w:"+orgWidth +"-h:"+orgHight);
      		  
      		    var div = id('div'+idname);
      		//    console.info("setImage id:"+idname +  " image_url_index:"+image_url_index +" url:"+Onload_imgs_url[image_url_index]);
  		      div.style.backgroundImage = 'url('+Onload_imgs_url[image_url_index]+')';
  		      var urlAddr = '';
  		      var urlAddr = Onload_imgs_url[image_url_index];
  		      var mTop = 0;
      		  if(orgHight <= orgWidth){
      			  mTop = (h - orgHight) / 2;
      		  }
      		setClickBigImg("div"+idname, mTop, image_url_index);
	    	//	$("#div"+idname).bind("click",setClickBigImg(mTop, image_url_index));
	    	//	console.info("show"+idname+"-end:"+new Date());
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
      		    //500 760  w  h
      		    var height = 0, width = 0,top = 0, left = 0;
      		  var img_bili = orgWidth/orgHight;
      		 // console.info("原始图片112"+Onload_imgs_url[image_url_index]+ "orgWidth:"+orgWidth +"orgHight"+orgHight +"img_bili:"+img_bili+"-"+w/h +"-"+ (img_bili > w/h))
      		 console.info("原始图片:orgWidth:"+orgWidth+"~orgHight:"+orgHight+"~w:"+w+"~h:"+h);
      			  
  			  if(img_bili < w/h){
  				  height = h;
  				  width = orgWidth * ( h / orgHight);
  				  left = (w-width)/2;
  			  }else if(img_bili == w/h){
  				  var width = w * 0.8;
  				  var height = w * 0.8;
  				  var left = w * 0.1;
  				  var top = (h -w*0.8) /2;
  			  } else if(img_bili > w/h){
  				  width = w;
  				  height = orgHight * (w/orgWidth);
  				  top = (h-height)/2;
  			  } 
      		  // console.info(img_bili +"*"+w/h+"*******width:"+width+"-height:"+height); 
      		    div.style.width = (width ) + 'px';
      		    div.style.height = (height)+ 'px';
      		    div.style.left = left + 'px';
      		    div.style.top = top + 'px';
      		    div.style.backgroundSize = (width-8) + 'px ' + (height-8) + 'px';
      		    div.style.backgroundColor='#fff';
      		  console.info(img_bili +"*"+w/h+"*******width:"+width+"-height:"+height+"-top"+top+"-left"+left); 
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
      		var fIndex = 0;
      		var fTop = 0;
      		function setClickBigImg(divName, mTop,iui){
      			console.info("setImage id: urlAddr"+iui+"Onload_imgs_url.length:"+Onload_imgs_url.length);
      			fIndex = iui;
      			fTop = mTop;
      			$("#"+divName).bind('click', function(){
      				$("#zp_big_img").attr("src",'');
      				$("#zp_big").show();
		    		$("#zhezhao").show();
		    		console.info("set iui:"+fIndex)
					$("#zp_big_img").attr("src",Onload_imgs_url[fIndex]);
					$("#zp_big_img").css("margin-top", fTop+"px");
					$("#zhezhao").css("opacity","0.9");
      			});
		    		
      		}
      		
      		
      		function step_load()
      		{
      		    var load_num = 0
      		    //初步加载X张
      		  //  console.info("image_url_index:"+image_url_index+"---bl_keepload:"+bl_keepload)
      		    if(image_url_index  == 0 && bl_keepload == 'first'){
      		        console.log('loading continue');
      		        if(slider_images_url.length > step_loadnum)
      		        {
      		            load_num = step_loadnum;
      		            bl_keepload = 'next';
      		        }else{
      		            load_num = slider_images_url.length;
      		            bl_keepload = 'end';
      		        }
      		        console.info("load_num~"+load_num);
      		        for(var i = 0; i< load_num; i++)
      		        {
      		            var img=new Image();
      		            img.index=i;
      					console.log('-------'+slider_images_url[i]);
      		            img.src=slider_images_url[i];
      		            console.info("begin:"+new Date().getTime());
      		            img.onload=image_onload;
      		            img.onerror= image_onerror;
      		           
      		            Onload_imgs_url[i] = 'loading';
      		        }
      		    }else if(bl_keepload == 'end'){
      			   console.info("end~~~~~~~~~~~~~~~~~");
      			   if(last_index > 1){
      				   clearnode();
      				   $("#enddiv").show();
      				   $("#zhezhao").show();
      				   //播放完了之后，停止播放，
      				 isFinshed=true;
      			   }else{
      			   		last_index ++;
      			   }
      		        return;
      		    }else{
      		    	//console.log('loading continue image_url_index:'+(image_url_index +1));
      		    		
  		    		if(slider_images_url.length - (image_url_index +1) >step_loadnum){ 
  		    			load_num = step_loadnum;
  		    		}else{
  		    			load_num = slider_images_url.length - (image_url_index +1);
  		    			bl_keepload = 'end';
  		    		}
  		    		for(var i = (image_url_index +1); i< (image_url_index +1) + load_num; i++)
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
      		        
      		        //不加载标题等数据，直接展示图片，所以这里固定设置dis_titletime > delaytime， 以便直接展示图片
      		        //if(dis_titletime>delaytime)
      		        if(true)
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

      		    if((have_num + error_num >= step_loadnum ||slider_images_url.length == (have_num+error_num)) && canshow == true)
      		    {   
      		        reshow = false;
      		        canshow =false;
      		        if(have_num == 0)
      		            return;
      		        var end_titletime = new Date();
      		        end_titletime = end_titletime.getTime();
      		        var dis_titletime = Math.abs(end_titletime-begin_titletime);
      		        //不加载标题等数据，直接展示图片，所以这里固定设置dis_titletime > delaytime， 以便直接展示图片
      		       // if(dis_titletime>delaytime)
      		        if(true)
      		        {
      		            liangziyun_kawa();
      		        }
      		        else
      		        {
      		            dis_titletime = delaytime- dis_titletime;
      		           // console.log("~~~~~~~~~~dis_titletime"+dis_titletime);
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
      		    reload = true;
      		    last_index = 1;
      		    var itemlist = ['biaoti','titlecontent','titlebg','div1','div2','div3','div4','div5'];
      		    for(var i =0; i<itemlist.length;i++){
      		         id(itemlist[i]).style.display='block';
      		    }
      		    $("#enddiv").hide();
      		    $("#zhezhao").hide();
      		    $("#zp_big").hide();
      		  isFinshed=false;
      		    setTimeout(load_images,500);
      		}

      		function clearnode()
      		{

      		    for(var i = 0; i<timeout.length; i++)
      		    {
      		        clearTimeout(timeout[i]);
      		    }
      		  /*   for(var i =0; i<16;i++)
      		    {
      		        //var xin = 'tbgxin'+ i;
      		        //console.log(xin)
      		        //id(xin).style.display='none';
      		    } */
      		    var itemlist = ['biaoti','titlecontent','titlebg','div1','div2','div3','div4','div5'];

      		    for(var i =0; i<itemlist.length;i++)
      		    {
      		        id(itemlist[i]).style.webkitAnimation = '';
      		         id(itemlist[i]).style.display='none';
      		    }

      		}
      		call_me(load_images);
      		
      		

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
    			//	document.title = e_desc.replace("<br>", "\n").replace("<br/>", "\n");;
    			}
    			else
    			{
    			//	document.title = "音乐相册";
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

    		    bg_htm  = "<img id='sound_image' onclick='startOrPause(1)' src='./img/music_note_big.png' style='-webkit-animation:zhuan 2s linear infinite'>";
    		    txt_htm = "<div id='music_txt' style='color:white;position:absolute;left:-40px;top:30px;width:60px'></div>"

    		    sound_div.innerHTML = bg_htm  + txt_htm;

    		    document.body.appendChild(sound_div);
    		} 
    		function startOrPause(i){
    			//alert(inPageCount+"music status:"+e_music_player.paused +" firstInPage:"+firstInPage);
    			if(inPageCount == 1){
    				inPageCount = 2;
    				firstInPage = false;
    			}else{
    				
    				if(!firstInPage){
    					
    					if(e_music_player.paused ){
    						e_music_player.play();
    						$("#sound_image").attr("src","./img/music_note_big.png");
    						$("#sound_image").attr("style","-webkit-animation:zhuan 2s linear infinite");
    					}else{
    						$("#sound_image").attr("src","./img/nop.png");
    						$("#sound_image").removeAttr("style");
    						$("#sound_image").css("width","44px");
    						$("#sound_image").css("height","44px");
    						e_music_player.pause();
    					}
    				}
    			}
    			
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

    		  //  console.log(e_music_player.src);
    		    
    		    e_music_player.play();
    		    
    		    //检查音乐是否缓冲成功
    		    setTimeout(function()
    		    {
    				var timeRanges = e_music_player.buffered;
    				var timeBuffered = timeRanges.end(timeRanges.length - 1);
    				var bufferPercent = timeBuffered /e_music_player.duration; 
    			//	console.log('music:'+bufferPercent+"--e_music_url:"+e_music_url);
    				
    				if(isNaN(bufferPercent) || bufferPercent == 0)
    				{
    			    	e_music_player.src = url;
    			    	e_music_player.play();
    			    	curPlayStatus = "playing";
    				}
    		    },5000);


    		    if(objid('sound_image'))
    		    {
    		        objid('sound_image').style.webkitAnimation     = "zhuan 3s linear infinite";
    		    }
    		    
    		    bplay = 1;
    		}

    		$(function(){
  			  $('body').on('touchstart load',function(){
  				  if(e_music_player.paused && firstInPage){
  					  e_music_player.play();     
  					  firstInPage = false;
  					  inPageCount = 1;
  				  }
  			    })
  			});
    		
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
      		
      		
      	 // console.info("wh:"+ $("#container").width()+"hi:"+$("#container").height());
         // console.info("wh:"+ $("#loadingdiv").width()+"hi:"+$("#loadingdiv").height());
         // console.info("wh:"+ $("#enddiv").width()+"hi:"+$("#enddiv").height());
           $("#viewer-iframe").width($("#container").width());
          $("#viewer-iframe").height($("#container").height()); 
           $("#zhezhao").width($("#container").width());
          $("#zhezhao").height($("#container").height()); 