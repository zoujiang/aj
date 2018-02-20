<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%
String num = request.getParameter("num");
String content = "";
if(num == null||num.equals("")){
	content = "";
}else{
	content = "目前有"+num+"人在跟我一起玩，";
}
System.out.println("content="+content);
String shareTitle="";
String sharemessage="我今天已经获得了1GB的流量了，"+content+"终极礼包马上就要到手啦！你也来玩吧！";
String endshareTitle=java.net.URLEncoder.encode(shareTitle,"UTF-8") ;
String endsharemessage=java.net.URLEncoder.encode(sharemessage,"UTF-8") ;
String sharePic="http://http://218.206.27.198:18087/wcs/pages/nationalDayTreasure/images/share.jpg";
%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>分享</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="Cache-Control" content="no-Cache" />
		<meta name="viewport"
			content="width=device-width; initial-scale=0.5; minimum-scale=0.5; maximum-scale=1.0">
		<meta name="MobileOptimized" content="320">
		<meta name="format-detection" content="telephone=no" />
		<link href="<c:url value='css/global.css' />" rel="stylesheet"
			type="text/css" />
			<script src="/wcs/scripts/jquery.js"></script>
		<style type="text/css">
a,a:hover {
	text-decoration: none;
	color: #333
}
</style>
	</head>
	<script type="text/javascript">
	var viewPortScale;
	viewPortScale = 0.5;

	var detectBrowser = function(name) {
		if (navigator.userAgent.toLowerCase().indexOf(name) > -1) {
			return true;
		} else {
			return false;
		}
	};

	if (detectBrowser('ucbrowser')) {
		document.getElementById('viewport').setAttribute(
				'content',
				'user-scalable=no,width=device-width, minimum-scale=0.5, initial-scale='
						+ viewPortScale);
	} else if (detectBrowser('iphone')) {
		document
				.getElementById('viewport')
				.setAttribute('content',
						'user-scalable=no,width=device-width, minimum-scale=0.666, initial-scale=0.5');
	}
</script>

	<body onload="init();">
<script type="text/javascript">
	function init() {
		$("#contentWord").append("<%=sharemessage%>");		
	}
</script>
		<div class="main">
			<a href="javascript:;" onclick="window.history.go(-1)" class="fh"><div
					class="bar">
					<span><%=shareTitle%></span>
				</div>
			</a>
			<div class="banner">
				<img src="<c:url value='images/banner.jpg' />">
			</div>
			<div class="f26 mt40 blue">
				<a id="contentWord" style="text-decoration: underline;" ></a>
			</div>
			<div class="mt40 fxbox t_c">
				<p>
					分享到
				</p>
				<ul>
					<li>
						<a
							href="http://s.jiathis.com/?webid=douban&url=http%3A%2F%2F218.206.27.198:18087%2Fwcs%2Fpages%2FnationalDayAct%2Findex.jsp&title=<%=endshareTitle%>&jtss=1&pic=<%=sharePic%>&summary=<%=endsharemessage%>"
							target="_blank"><img src="<c:url value='images/1.png' />">
							<br>豆瓣</a>
					</li>
					<li>
						<a
							href="http://s.jiathis.com/?webid=cqq&url=http%3A%2F%2F218.206.27.198:18087%2Fwcs%2Fpages%2FnationalDayAct%2Findex.jsp&title=<%=endshareTitle%>&jtss=1&pic=<%=sharePic%>&summary=<%=endsharemessage%>"
							target="_blank"><img src="<c:url value='images/2.png' />">
							<br>QQ好友</a>
					</li>
					<li>
						<a
							href="http://s.jiathis.com/?webid=tsina&url=http%3A%2F%2F218.206.27.198:18087%2Fwcs%2Fpages%2FnationalDayAct%2Findex.jsp&title=<%=endshareTitle%>&jtss=1&pic=<%=sharePic%>&summary=<%=endsharemessage%>"
							target="_blank"><img src="<c:url value='images/3.png' />">
							<br>新浪微博</a>
					</li>
					<li class="nob">
						<a
							href="http://s.jiathis.com/?webid=tqq&url=http%3A%2F%2F218.206.27.198:18087%2Fwcs%2Fpages%2FnationalDayAct%2Findex.jsp&title<%=endshareTitle%>&jtss=1&pic=<%=sharePic%>&summary=<%=endsharemessage%>"
							target="_blank"><img src="<c:url value='images/4.png' />">
							<br>腾讯微博</a>
					</li>
					<li>
						<a
							href="http://s.jiathis.com/?webid=feixin&url=http%3A%2F%2F218.206.27.198:18087%2Fwcs%2Fpages%2FnationalDayAct%2Findex.jsp&title=<%=endshareTitle%>&jtss=1&pic=<%=sharePic%>&summary=<%=endsharemessage%>"
							target="_blank"><img src="<c:url value='images/8.png' />">
							<br>飞信</a>
					</li>
					<li>
						<a
							href="http://s.jiathis.com/?webid=tieba&url=http%3A%2F%2F218.206.27.198:18087%2Fwcs%2Fpages%2FnationalDayAct%2Findex.jsp&title=<%=endshareTitle%>&jtss=1&pic=<%=sharePic%>&summary=<%=endsharemessage%>"
							target="_blank"><img src="<c:url value='images/7.png' />">
							<br>百度贴吧</a>
					</li>
					<li>
						<a
							href="http://s.jiathis.com/?webid=tianya&url=http%3A%2F%2F218.206.27.198:18087%2Fwcs%2Fpages%2FnationalDayAct%2Findex.jsp&title=<%=endshareTitle%>&jtss=1&pic=<%=sharePic%>&summary=<%=endsharemessage%>"
							target="_blank"><img src="<c:url value='images/6.png' />">
							<br>天涯社区</a>
					</li>
					<li class="nob">
						<a
							href="http://s.share.baidu.com/?click=1&url=http%3A%2F%2F218.206.27.198:18087%2Fwcs%2Fpages%2FnationalDayAct%2Findex.jsp&uid=0&to=qzone&type=text&pic=<%=sharePic%>&title=<%=endshareTitle%>&key=&desc=<%=endsharemessage%>"
							target="_blank"><img src="<c:url value='images/5.png' />">
							<br>QQ空间</a>
					</li>
				</ul>
			</div>

		</div>
	</body>
</html>
