<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String url = request.getParameter("url");
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>商户管理平台-图片浏览</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>
	

	<script type="text/javascript">
		 $(document).ready(function() {
			 
			 var img_urls = '<%=url%>'
			 var url = img_urls.split(',');
			 var html = "";
			 for(var i=0; i< url.length; i++){
				getWH(url[i], i);
			 }
		 });
		function getWH(url, imgIndex){
			    
			var div_h = 300; //div固定高
			var div_w = 300;//div固定宽
			var img = new Image();
			// 改变图片的src
			img.src = url;
			// 定时执行获取宽高
			
			var check = function(){
				
			    // 只要任何一方大于0
			    // 表示已经服务器已经返回宽高
			    if(img.width>0 || img.height>0){
			      	var h = img.height;
			      	var w = img.width ;
			      	var html = "";
			      	if(h/w > div_h/div_w ){
			      		
			      		html += "<div style='width:303px;height:303px;float:left;margin:10px;border:1px solid #000;' align='center'><img id='IMG_"+imgIndex+"' src='"+url+"' style='height:"+ div_h+"px;'/><button id='BTN_"+imgIndex+"' class='btn btn-primary' style='margin-top:266px;position: absolute;margin-left:-"+ (w * (div_h /h)+55) /2+"px;' onclick='delImg(\""+imgIndex+"\")'>删除</button></div>";
			      	}else{
			      		html += "<div style='width:303px;height:303px;float:left;margin:10px;border:1px solid #000;line-height:300px;' align='center'><img id='IMG_"+imgIndex+"' src='"+url +"' style='width:"+ div_w+"px;'/><button id='BTN_"+imgIndex+"' class='btn btn-primary' style='margin-top:266px;position: absolute;margin-left:-"+ (w * (div_h /w)+55) /2+"px;' onclick='delImg(\""+imgIndex+"\")'>删除</button></div>";
			      	}
			      	$("#imgview").append(html)
			      	//$(html).appendTo($("#imgview"))
			      	
			      	var vl = $("#hideValues").val();
			      	$("#hideValues").val(vl +url +",");
			        clearInterval(set);
			    }
			};
				
			var set = setInterval(check,40);
		 }
		function closeW(){
			
			layer.confirm("退出预览？", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				var index = parent.layer.getFrameIndex(window.name);
				//parent.$('#apv_table').bootstrapTable('refresh');
				parent.$("button[name='refresh']").click();
				parent.layer.close(index);
	  		});
		}
		var formData = function(){
			var  data = $("#hideValues").val();
			return data;
		}
		function delImg(index){
			var src =$("#IMG_"+index).attr("src");
			var hv = $("#hideValues").val();
			var hvs = hv.split(",");
			var newHv = "";
			for(var i=0; i< hvs.length; i++){
				if(hvs[i] != src){
					newHv += hvs[i]+",";
				}
			}
			if(newHv.length > 0){
				newHv = newHv.substring(0,newHv.length-1);
			}
			$("#hideValues").val(newHv);
			$("#IMG_"+index).remove();
			$("#BTN_"+index).remove();
		}
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content row">
                	<input type="hidden" id="hideValues">
               	 	<div id="imgview" style="text-align:center;float: left;">
               	 	</div>
               		<!-- <div class="hr-line-dashed"></div>
               		<div class="text-center">
               		 	<button id = "btn_save" type="button" onclick="closeW()" class="btn btn-primary">关闭</button>
                       </div> -->
                </div>
            </div>
      </div>
  </body>
</html>
