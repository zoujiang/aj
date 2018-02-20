<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String roleId = request.getParameter("roleId");
if(roleId == null || "".equals(roleId)){
	roleId = "";
}
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-续费</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="jquery.qrcode.min.js"></script>


	<script type="text/javascript">
	function AppMgr(){
		 this.initDatas = function(){
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/shop/renewal/initShopInfo",
	             dataType: "json",
	             success: function(data){
	            	 if(data.success){
		            	var shopName = data.shopName;
		            	$("#shopName").text(shopName);
		            	var serviceEndTime = data.serviceEndTime;
		            	$("#serviceEndTime").text(serviceEndTime);
		            	var left = data.left;
		            	$("#left").text(left);
		            	var total_fee = data.total_fee;
		            	$("#service_amount").text(total_fee);
	            	 }
	         	 }
	    	});
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/shop/renewal/initWx",
	             dataType: "json",
	             success: function(data){
	            	 if(data.success){
		            	var code_url = data.code_url;
		            	$('#wxImg').qrcode(code_url);
		            	$('#out_trade_no').val(data.out_trade_no)
	            	 }
	         	 }
	    	});
			 
	  	 }
	 }
	 $(document).ready(function() {
	  		new AppMgr().initDatas();
		});
	 var int=self.setInterval("queryWxPayStatus()",3000)
	 function queryWxPayStatus(){
		 console.info("queryPayStatus....");
		 var out_trade_no= $('#out_trade_no').val();
		 if(out_trade_no != ""){
			 
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/shop/renewal/queryOrderStatus?out_trade_no="+out_trade_no,
	             dataType: "json",
	             success: function(data){
	            	 if(data.success){
	            		self.clearTimeout(int);  
	            		layer.msg("您已成功通过微信支付购买了一年的服务时间，请前往商家信息页面查看", {title:'提示', time: 10000, btn: ['确定'],icon: 6}, function(index){
	    				});
	            	 }
	         	 }
	    	});
		 }
	   }
	 
	 
	</script>
  </head>
  
<body class="gray-bg" >
  	<div class="wrapper wrapper-content animated fadeInRight">
         <div class="ibox float-e-margins" style="margin-bottom: 0px;">
            <div class="ibox-content row">
            	<form id="form" action="">
            		<input type="hidden" name="id" id="id">
	               	<div style="width: 800px; margin: 0 auto;border:2px solid #FCC; line-height: 35px;">
	               		<div style="text-align: center;margin-top: 2px;">续费管理</div>
	               		<div style="margin-top: -1px;">
	               		<input type="hidden" id="out_trade_no" >
	               			<div style="border:1px solid #FCC">尊敬的商家 <span id="shopName"></span> 您好：<br/>您服务到期时间为:<span id="serviceEndTime"></span>,距离今天还剩:<span id="left"></span>天；您可以通过扫描下方任意二维码购买一年的服务时间（支付金额：<span id="service_amount"></span> 元）。<br/></div>
	               			<div >
	               				<table style="width: 600px;margin-top: 20px;margin-bottom:20px; margin-left:70px; border: 1;">
	               					<tr padding-top: 2px;">
	               						<td align="center"><div style="width: 256px;height: 256px;background-color: lightgray;" id="wxImg"></div></td>
	               						<td align="center"><div style="width: 256px;height: 256px;background-color: lightgray;" id="zfbImg"></div></td>
	               					</tr>
	               					<tr padding-top: 2px;">
	               						<td align="center">微信</td>
	               						<td align="center">支付宝</td>
	               					</tr>
	               				</table>
	               			</div>
	               		</div>
	               	</div>
               	</form>
               	<div class="hr-line-dashed"></div>
      	</div>
      </div>
  </body>
</html>
