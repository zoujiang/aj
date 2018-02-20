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


    <title>商户管理平台-支付方式管理</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
	function AppMgr(){
		 this.initDatas = function(){
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/shop/payAble/init",
	             dataType: "json",
	             success: function(data){
	            	 if(data.success){
		            	 var d = data.data;
		            	 
		            	 $("#id").val(d.id);
		            	 $("#accountZFB").val(d.account_zfb);
		            	 $("#secretZFB").val(d.secret_zfb);
		            	 $("#accountWX").val(d.account_wx);
		            	 $("#secretWX").val(d.secret_wx);
		            	 if(d.payTypeAndroidZFB == 0){
			            	 $("#payTypeAndroidZFB").attr("checked","checked");
		            	 }
		            	 if(d.payTypeAndroidWX == 0){
			            	 $("#payTypeAndroidWX").attr("checked","checked");
		            	 }
		            	 if(d.payTypeIOSZFB == 0){
			            	 $("#payTypeIOSZFB").attr("checked","checked");
		            	 }
		            	 if(d.payTypeIOSWX == 0){
			            	 $("#payTypeIOSWX").attr("checked","checked");
		            	 }
		            	 $(":radio[name='Android_zfb'][value='" + d.Android_zfb + "']").prop("checked", "checked");
		            	 $(":radio[name='Android_wx'][value='" + d.Android_wx + "']").prop("checked", "checked");
		            	 $(":radio[name='IOS_zfb'][value='" + d.Ios_zfb + "']").prop("checked", "checked");
		            	 $(":radio[name='IOS_wx'][value='" + d.Ios_wx + "']").prop("checked", "checked");

		            	 $("#Android_zfb_v").val(d.AndroidZfbV);
		            	 $("#Android_wx_v").val(d.AndroidWxV);
		            	 $("#IOS_zfb_v").val(d.IosZfbV);
		            	 $("#IOS_wx_v").val(d.IosWxV);
		            	 $("#serviceAmount").val(d.service_amount);
	            	 }
	         	 }
	    	});
			 
	  	 }
	 }
	 $(document).ready(function() {
	  		new AppMgr().initDatas();
		});
		function saveUser(){
			 var Android_zfb_v =$("#Android_zfb_v").val();
			 var Android_wx_v =$("#Android_wx_v").val();
			 var IOS_zfb_v =$("#IOS_zfb_v").val();
			 var IOS_wx_v =$("#IOS_wx_v").val();
			if(Android_zfb_v == null || Android_zfb_v == ""
				||Android_wx_v == null || Android_wx_v == ""
				||IOS_zfb_v == null || IOS_zfb_v == ""
				||IOS_wx_v == null || IOS_wx_v == ""){
					layer.msg("手续费分成比例必填", {title:'提示', btn: ['确定'],icon: 6}, function(index){
						});
						$("#btn_save").attr("disabled", false);
						return false;
				}
		
			$("#form").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/shop/payAble/add",
			     dataType: "json",
			     success: function(obj){
			    	if(obj.success){
							layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
								//var index = parent.layer.getFrameIndex(window.name);
								//parent.$("button[name='refresh']").click();
								//parent.layer.close(index);
								window.location.reload();
					  		});
					}else{
						layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
						});
					}
			     }
			 });
			
			$("#btn_save").attr("disabled", false);
			
		}
		 $(function(){
			$("#btn_save").click(function(){
				$("#btn_save").attr("disabled", true);
				saveUser();
			});
		}); 
	</script>
  </head>
  
<body class="gray-bg" >
  	<div class="wrapper wrapper-content animated fadeInRight">
         <div class="ibox float-e-margins" style="margin-bottom: 0px;">
            <div class="ibox-content row">
            	<form id="form" action="">
            		<input type="hidden" name="id" id="id">
	               	<div style="width: 800px; margin: 0 auto;border:2px solid #FCC; line-height: 35px;">
	               		<div style="text-align: center;margin-top: 2px;">支付方式管理</div>
	               		<div style="margin-top: -1px;">
	               			<div style="border:1px solid #FCC">收款账号</div>
	               			<div >
	               				<table style="width: 500px;margin-top: 2px;">
	               					<tr style="width: 150px;padding-top: 2px;">
	               						<td align="center">支付宝账号</td>
	               						<td><input type="text" class="form-control" name="accountZFB" id="accountZFB"></td>
	               					</tr>
	               					<tr>
	               						<td align="center">支付宝密钥</td>
	               						<td><input type="text" class="form-control" name="secretZFB" id="secretZFB"></td>
	               					</tr>
	               					<tr>
	               						<td align="center">微信账号</td>
	               						<td><input type="text" class="form-control" name="accountWX" id="accountWX"></td>
	               					</tr>
	               					<tr>
	               						<td align="center">微信密钥</td>
	               						<td><input type="text" class="form-control" name="secretWX" id="secretWX"></td>
	               					</tr>
	               				</table>
	               			</div>
	               			<div style="border:1px solid #FCC;margin-top: 2px;">支付方式选择</div>
	               			<div>
	               				<table style="margin-left: 50px;">
	               					<tr>
	               						<td style="width: 100px;">Android</td>
	               						<td style="width: 100px;"><input type="checkbox" name="payTypeAndroidZFB" id="payTypeAndroidZFB" value="0">&nbsp;支付宝</td>
	               						<td style="width: 100px;"><input type="checkbox" name="payTypeAndroidWX" id="payTypeAndroidWX" value="0">&nbsp;微信支付</td>
	               					</tr>
	               					<tr>
	               						<td style="width: 100px;">苹果IOS</td>
	               						<td style="width: 100px;"><input type="checkbox" name="payTypeIOSZFB" id=payTypeIOSZFB value="0">&nbsp;支付宝</td>
	               						<td style="width: 100px;"><input type="checkbox" name="payTypeIOSWX" id="payTypeIOSWX" value="0">&nbsp;微信支付</td>
	               					</tr>
	               				</table>
	               			</div>
	               			<div style="border:1px solid #FCC">收款方配置、手续费配置（如某项不需收取手续费，请输入数字零： 0）</div>
	               			<div>
	               				<table>
	               					<tr>
	               						<td  style="width: 130px;text-align: center;">访问方式</td>
	               						<td style="width: 130px;text-align: center;">支付方式</td>
	               						<td style="width: 300px;text-align: center;">手续费分成方式</td>
	               						<td style="width: 220px;text-align: center;">手续费分成比例</td>
	               					</tr>
	               					<tr>
	               						<td align="center">Android</td>
	               						<td align="center">支付宝</td>
	               						<td  align="center">
	               							<input type="radio" name="Android_zfb" value="0" checked="checked">&nbsp;百分比（小数表示）
	               							<input type="radio" name="Android_zfb" value="1">&nbsp;固定值（单位：元）
	               						</td>
	               						<td align="center"><input type="text" class="form-control" name="Android_zfb_v" id="Android_zfb_v"> </td>
	               					</tr>
	               					<tr>
	               						<td  align="center">Android</td>
	               						<td align="center">微信支付</td>
	               						<td align="center">
	               							<input type="radio" name="Android_wx" value="0" checked="checked">&nbsp;百分比（小数表示）
	               							<input type="radio" name="Android_wx" value="1">&nbsp;固定值（单位：元）
	               						</td>
	               						<td align="center"><input type="text" class="form-control" name="Android_wx_v" id="Android_wx_v"> </td>
	               					</tr>
	               					<tr>
	               						<td align="center">苹果IOS</td>
	               						<td align="center">支付宝</td>
	               						<td align="center">
	               							<input type="radio" name="IOS_zfb" value="0" checked="checked">&nbsp;百分比（小数表示）
	               							<input type="radio" name="IOS_zfb" value="1">&nbsp;固定值（单位：元）
	               						</td>
	               						<td align="center"><input type="text" class="form-control" name="IOS_zfb_v" id="IOS_zfb_v"> </td>
	               					</tr>
	               					<tr>
	               						<td align="center">苹果IOS</td>
	               						<td align="center">微信支付</td>
	               						<td align="center">
	               							<input type="radio" name="IOS_wx" value="0" checked="checked">&nbsp;百分比（小数表示）
	               							<input type="radio" name="IOS_wx" value="1">&nbsp;固定值（单位：元）
	               						</td>
	               						<td align="center"><input type="text" class="form-control" name="IOS_wx_v" id="IOS_wx_v"> </td>
	               					</tr>
	               				</table>
	               			</div>
	               		</div>
	               		<div>
	               		
		               		<div style="text-align: center;margin-top: 2px;border:1px solid #FCC">续费金额设置</div>
	               			<table style="width: 500px;margin-top: 2px;">
	               					<tr style="width: 150px;padding-top: 2px;">
	               						<td align="center">年服务费(元)
	               						</td>
	               						<td><input type="text" class="form-control" name="serviceAmount" id="serviceAmount">
	               						</td>
	               					</tr>
	               					<tr style="width: 150px;padding-top: 2px;">
	               						<td align="center"></td>
	               						<td>
	               						<span class="help-block m-b-none">备注:商户按此金额支付后增加一年服务时间。</span>
	               						</td>
	               						 
	               					</tr>
	               				</table>
	               		</div>
	               	</div>
               	</form>
               	<div class="hr-line-dashed"></div>
               		<div class="text-center">
               		 	<button id = "btn_save" type="button" class="btn btn-primary">保存</button>
                       </div>
      		</div>
      	</div>
      </div>
  </body>
</html>
