<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String gps = request.getParameter("gps");
if(gps == null ){
	gps = "";
}
%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="chrome=1">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <style type="text/css">
      body,html,#container{
        height: 100%;
        margin: 0px
      }
      .panel {
        background-color: #ddf;
        color: #333;
        border: 1px solid silver;
        box-shadow: 3px 4px 3px 0px silver;
        position: absolute;
        top: 10px;
        right: 10px;
        border-radius: 5px;
        overflow: hidden;
        line-height: 20px;
      }
      #input{
        width: 250px;
        height: 25px;
        border: 0;
      }
    </style>
    <title>选择经纬度</title>
    
  </head>
  <body>
   <div id="container" tabindex="0"></div>
   <div class ='panel'>
     <input id = 'input' value = '点击地图选择经纬度' onfocus = 'this.value=""' style="width: 160px;"></input><button onclick="selectedValue()" style="height: 29px;">确定</button>
     <div id = 'message'></div>
   </div>
   <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=b4dc1cf7ecb7c81e619f5b3a3093ecb1"></script>
   <script type="text/javascript">
   		
   		var old = '<%=gps %>';
   		var ot ;
		if(old == ''){
			old = [106.567922,29.556852] ;
			ot = "106.567922,29.556852";
		}else{
			ot = old ;
			old = old.split(",")
		}
		if(ot != ""){
			
			document.getElementById("input").value = ot;
		}
   		
	    var map = new AMap.Map('container',{
	            resizeEnable: true,
	            zoom: 13,
	            center: old
	    });
	    
	    AMap.plugin(['AMap.ToolBar','AMap.Scale','AMap.OverView'],
	    	    function(){
	    	        map.addControl(new AMap.ToolBar());
	    	        map.addControl(new AMap.Scale());
	    	      //  map.addControl(new AMap.OverView({isOpen:true}));
	    	});
	    
	    
	    
	    AMap.plugin('AMap.Geocoder',function(){
	        var geocoder = new AMap.Geocoder({city: "023"});
	        var marker = new AMap.Marker({
	            map:map,
	            bubble:true
	        })
	        var input = document.getElementById('input');
	        var message = document.getElementById('message');
	        map.on('click',function(e){
	            marker.setPosition(e.lnglat);
	            input.value = e.lnglat;
	            message.innerHTML = '';
	        })
	        
	         input.onchange = function(e){
	            var address = input.value;
	            geocoder.getLocation(address,function(status,result){
	              if(status=='complete'&&result.geocodes.length){
	                marker.setPosition(result.geocodes[0].location);
	                map.setCenter(marker.getPosition())
	                message.innerHTML = ''
	              }else{
	               // message.innerHTML = ''
	              }
	            })
	        } 
	    });
	    
	    function selectedValue(){
	    	
	    	var gps = document.getElementById("input").value;
	    	if(gps == ""){
	    		document.getElementById('message').innerHTML = "请选择地址";
	    	}else{
	    		var lnglat = gps.split(",");
	    		if(lnglat.length != 2){
		    		document.getElementById('message').innerHTML = "经纬度格式错误";
	    		}else{
	    			var reg = /^[1-9]\d*\.\d*$|0\.\d*[1-9]\d*$/;
	    			var n = lnglat[0].match(reg); 
	    			var a = lnglat[1].match(reg); 
	    			if(n ==null || a == null ){
	    				document.getElementById('message').innerHTML = "经纬度格式错误";
	    			}else{
	    				var index = parent.layer.getFrameIndex(window.name);
						parent.$("#gps").val(gps);
						parent.layer.close(index);
	    			}
	    		}
	    	}
	    	
	    }
    
   </script>
   <script type="text/javascript" src="http://webapi.amap.com/demos/js/liteToolbar.js"></script>
    <%@ include file="/pages/comm/jsp/inc.jsp"%>
  </body>
</html>