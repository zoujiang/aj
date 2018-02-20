<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>图片预览</title>
		<style type="text/css">
		.box {
	        /*非IE的主流浏览器识别的垂直居中的方法*/
	        display: table-cell;
	        vertical-align:middle;
	
	        /*设置水平居中*/
	        text-align:center;
	
	        /* 针对IE的Hack */
	        *display: block;
	        *font-size: 524px;/*约为高度的0.873，600*0.873 约为524*/
	        *font-family:Arial;/*防止非utf-8引起的hack失效问题，如gbk编码*/
	
	        width:800px;
	        height:600px;
	        border: 1px solid #eee;
		}
		.box img {
		    /*设置图片垂直居中*/
	        vertical-align:middle;
		}
		</style>
	</head>
	<body style="padding: 0;margin: 0" >
		<div class="box">
			<img align="middle" alt="文件浏览预览"/>
		</div>
		<script type="text/javascript">
		$(document).ready(function () {
			var obj = window.dialogArguments;
			
			var url = obj.url;
			if(url != null && url != ""){
				if(url.indexOf("img?img")== -1  ){
					url = "<%=mainWeb%>/service/img?img="+url;
				}else{
					url = "<%=mainWeb%>/service/res?res="+url;
				}
			}
				
				$(".box >img").attr("src",url+"&viewType=2");
			
			
		});
		</script>
	</body>
</html>