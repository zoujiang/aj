<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>WebService接口测试</title>
		<style type="text/css">
			* {
				font-size: 12px;
				margin: 0;
				padding: 0;
			}
			
			fieldset {
				padding: 10px;
				margin: 10px;
				width: 370px;
				color: #333;
				border: #06c dashed 1px;
				background-color: #EEE;
			}
			
			legend {
				color: #06c;
				font-weight: 800;
				background: #fff;
			}
			
			ul {
				list-style-type: none;
				margin: 8px 0 4px 0;
				color: #880;
			}
			
			li {
				margin-top: 4px;
			}
		</style>
		<script type="text/javascript" src="<%=basePath%>/scripts/jquery/jquery-1.8.1.min.js"></script>
		<script type="text/javascript">
			$(function(){
				var $form=$("#testForm");
				$form.submit(function(){
					return false;
				});
				$("#testForm > input[type='submit']").click(function(){
					if($("#testForm > textarea").val()==""){
						alert("不能为空哦~亲！");
						return false;
					}
					$("#textArea").val("");
					$.post(
					"<%=basePath%>service/proxy",
					$form.serialize(),
					function(data){
						$("#textArea").val(data.toString());
						/*if(data.returnCode==1){
							$("#textArea").val("执行成功，在线成交金额为："+data.money+"元，其中积分与花费离线赠送。");
						}else if(data.returnCode==0){
							$("#textArea").val("执行成功，系统无匹配的活动或者方案。");
						}else if(data.returnCode==500){
							$("#textArea").val("执行出错，出错的信息是："+data.errMsg);
						}*/
						
					},"text");
				});
				
				$("#btn1").click(function(){
					$("#show").toggle();
				});
			});
		</script>
	</head>

	<%
		String flag = request.getParameter("flag");
		flag = flag == null ? "" : flag.trim();
	%>

	<body>
	<div style="float:left;clear: both;width: 500px;">
		<fieldset>
			<legend>
				获取活动应用列表
			</legend>
			<form id="testForm" name="testForm" action="#"
				method="post">
				请输入正确的JSON字符串：
				</br>
				<textarea rows="20" cols="70" name="jsonRequest"></textarea>
				</br>
				<input type="submit" value="提交">
				</br>
			</form>
		</fieldset>
	<fieldset >
			<legend>
				返回结果
			</legend>
				<textarea id="textArea" rows="9" cols="70"></textarea>
			</form>
		</fieldset>
	</div>
		<fieldset style="float:left;">
			<legend>
				注意的相关事项
			</legend>

			<ul>
				<li>
					（1）获取活动应用列表测试地址:http://ip:port/mkt/service/proxy
				</li>
				<li>
					（2）JSON格式测试地址：http://www.bejson.com/go.php?u=http://www.bejson.com/index.php
				</li>
				<li>
					（3）JSON测试例子：<input id="btn1" type="button" value="点击展开例子"/>
				</li>
			</ul>
			<ul id="show" style="display: none">
			
						<li>{</li>
  	<li>"orderID": "1234567890",</li></li>
    	<li>"userID": "0f01d7df25c8439c8c3ad9673447e51c",</li>
    	<li>"orderDate": "2013-5-8 12:10:10",</li>
    	<li>"orderDesc": "商品描述",</li>
    	<li>"callType": "1",</li>
    	<li>"serviceName": "mkt_order_osc_req",</li>
    	<li>"orderItems": [</li>
        	<li>{</li>
           	<li>"appCode": "AP500000000000010094",</li>
           	<li>"appName": "山城挂号通",</li>
            <li>"tpCode": "C000575TP23001133",</li>
            <li>"tpName": "重庆人民医院",</li>
            <li>"merchantId": "TP23001133",</li>
            <li>"merchantName": "挂号渠道提供商",</li>
            <li>"ruleId": "1",</li>
            <li>"imageUrl": "http://www.baidu.com/img.jpg",</li>
            <li>"noticeUrl": "http://192.168.1.197:9093/ioms/app.jsp",</li>
            <li>"price": "100",</li>
            <li>"number": "3",</li>
            <li>"totalFee": "0.4",</li>
            <li>"appId": "AP500000000000010533",</li>
            <li>"appKey": "422d52a48ca25ad41108d4b1069ace81",</li>
            <li>"prodId": "test5",</li>
            <li>"prodName": "test5",</li>
            <li>"mobileSystem": "1",</li>
            <li>"prodDesc": {</li>
               <li> "医院": "第三军医大学西南医院",</li>
                <li>"科室": "关节外科中心门诊",</li>
                <li>"医生": "陈光兴",</li>
                <li>"就诊时间": "2013-01-23 白天"</li>
            <li>}
        <li>},</li>
        <li>{</li>
            <li>"appCode": "AP500000000000010095",</li>
            <li>"appName": "电影票活动",</li>
            <li>"tpCode": "C000575TP23001135",</li>
            <li>"tpName": "武汉天河电影院",</li>
            <li>"merchantId": "TP23001135",</li>
            <li>"merchantName": "挂号渠道提供商",</li>
            <li>"ruleId": "1",</li>
            <li>"imageUrl": "http://www.baidu.com/img.jpg",</li>
            <li>"noticeUrl": "http://192.168.1.197:9093/ioms/app.jsp",</li>
            <li>"price": "100",</li>
            <li>"number": "5",</li>
            <li>"totalFee": "0.4",</li>
            <li>"appId": "AP500000000000010107",</li>
            <li>"appKey": "422d52a48ca25ad41108d4b1069ace85",</li>
            <li>"prodId": "test4",</li>
            <li>"prodName": "test4",</li>
            <li>"mobileSystem": "1",</li>
            <li>"prodDesc": {</li>
                <li>"地点": "武汉光谷步行街",</li>
               <li>"电影院": "武汉天河电影院",</li>
                <li>"电影": "一代宗师",</li>
                <li>"上映时间": "2013-01-23 白天"</li>
            <li>}</li>
        <li>}</li>
   <li>]</li>
<li>}</li>
			</ul>
		</fieldset>
		
	</body>
</html>
