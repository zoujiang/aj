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


    <title>商户管理平台-添加商户</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<style type="text/css">
		.progress,.progress-bar,.sr-only{height:20px; font-size:0;line-height:0}
		.progress{overflow:hidden; width:500px;-khtml-border-radius:6px;-ms-border-radius:6px;-o-border-radius:6px;-moz-border-radius:6px;-webkit-border-radius:6px;border-radius:6px;background-color: gray;}
		.progress-bar{width:0%;background-color:green;}
		.sr-only{display:inline-block; background-color: yellow;}
	
	</style>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>
	

	<script type="text/javascript">
	
		function AppMgr(){
			 this.initDatas = function(){
				 $.ajax({
		             type: "GET",
		             url: "<%=path %>/admin/shop/category/all",
		             dataType: "json",
		             success: function(data){
		            	 var arr = data.data;
		            	 var html = "";
		            	  $.each( arr, function(index, c)
		            	  { 
		            		  html += "<option value='"+c.id+"'>"+c.name+"</option>";
		            	  });
		            	  $("#shopCategoryId").html(html);
		         	 }
		    	});
		    	 $.ajax({
		             type: "GET",
		             url: "<%=path %>/admin/shop/brand/all",
		             dataType: "json",
		             success: function(data){
		            	 var arr = data.data;
		            	 var html = "";
		            	  $.each( arr, function(index, c)
		            	  { 
		            		  html += "<option value='"+c.id+"'>"+c.brand_name+"</option>";
		            	  });
		            	  $("#brandId").html(html);
		         	 }
		    	});
				 $.ajax({
		             type: "GET",
		             url: "<%=path %>/admin/shop/bank/all",
		             dataType: "json",
		             success: function(data){
		            	 var arr = data.data;
		            	 var html = "";
		            	  $.each( arr, function(index, c)
		            	  { 
		            		  html += "<li onclick=\"setBankVal('"+c.id+"','"+c.name+"')\"><a>"+c.name+"</a></li>";
		            	  });
		            	  $(html).appendTo("#bankUL");
		         	 }
		    	});
				 $('#showStationGc').click(function(e){
						AppMgr.edit(e);
					});
		  	 }
			//编辑
			AppMgr.edit = function(id,valid,e){
				var gps = $("#gps").val();
				layer_show("选择经纬度", getProjectName() +"/pages/map/amap.jsp?gps="+gps,"800","420");
			}
		 }
		 $(document).ready(function() {
		  		new AppMgr().initDatas();
			});
	
	    function setBankVal(v, n){
	    	
	    	$("#bankId").val(v);
	    	$("#bankName").html(n);
	    	
	    }
		function saveUser(){
			
			var shopName = $("#shopName").val();
			if(shopName == ""){
				layer.msg("商户名称不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var brandId = $("#brandId").val();
			if(brandId == ""){
				layer.msg("商户品牌不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var logo = $("#logo").val();
			if(logo == ""){
				layer.msg("商户logo不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var gps = $("#gps").val();
			if(gps == ""){
				layer.msg("商户坐标地址不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var percentage = $("#percentage").val();
			if(percentage == ""){
				layer.msg("分成比例不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			/*var bankId = $("#bankId").val();
			if(bankId == ""){
				layer.msg("分成账号银行不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var cardNo = $("#cardNo").val();
			if(cardNo == ""){
				layer.msg("分成账号不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			*/
			var serviceBeginTime = $("#serviceBeginTime").val();
			if(serviceBeginTime == ""){
				layer.msg("服务开始时间不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var serviceEndTime = $("#serviceEndTime").val();
			if(serviceEndTime == ""){
				layer.msg("服务结束时间不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var zoneSize = $("#zoneSize").val();
			if(zoneSize == ""){
				layer.msg("空间数不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var tel = $("#tel").val();
			if(tel == ""){
				layer.msg("客户电话不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
		
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/shop/baseinfo/add",
			     dataType: "json",
			     success: function(obj){
			    	 if(obj.success){
							layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
								var index = parent.layer.getFrameIndex(window.name);
								//parent.$('#apv_table').bootstrapTable('refresh');
								parent.$("button[name='refresh']").click();
								parent.layer.close(index);
					  		});
						}
						else{
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
		 function addLine(){
			 
			 var osp = $("#oldShowPics").val(); 
			 var i=0;
			 if(osp != null && osp != ""){
				 i = osp.split(",").length;
			 }
			 var j = $("input[name='showPics']").length
			if( (i +  j)> 4){
				layer.msg("最大可上传5张", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				return;
			}
				var id=Math.round(Math.random()*100000);
		        var fid="pic"+id;
				var html = "<div class='row' id='"+fid+"'><div class='form-group col-sm-6'> <label class='col-sm-4 control-label'></label> <div class='col-sm-8'><input type='file'  name='tsmpPic' accept='image/*' class='form-control'/></div> </div>"; 
				html += "<div class='form-group col-sm-6'> <div class='col-sm-8' style='margin-left: -20px;margin-top: 8px;'><input type='button' value='+' onclick='addLine()' style='height:19px;'>&nbsp;&nbsp;&nbsp;<input type='button' value='-' onclick='deleteLine(\""+fid+"\")' style='height:19px;'></div>  </div></div>";
				
				$("#pic1").after(html);
			}
		function deleteLine(id)
		{
		  $("#"+id).remove();
		}
		function changeZoneSize(v){
			if(v == ""){
				v = 0;
			}
			$("#zoneSizeText").text("(总共"+ v +"G)");
		}
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content row">
               	 	<form class="form-horizontal" id="userForm" action="<%=path %>/admin/shop/category/add" enctype="multipart/form-data" method="post">
               	 		<input type="hidden" name="id" id="roleId" value="<%= roleId %>">
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><b style="color:red;">*</b>商户名称：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="shopName" id="shopName">  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><b style="color:red;">*</b>商户品牌：</label> 
	               				<div class="col-sm-8">
	               					<select class="form-control" name="brandId" id="brandId">
								    </select>
	               				</div>
						    </div>
                        </div>
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">注册人姓名：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="registName" id="registName">  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">联系电话：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="registTel" id="registTel">  
	               				</div>
	                        </div>
                        </div>
                         <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">身份证号码：</label> 
	               				<div class="col-sm-8">
	               					<input  type="text" name="idCard" class="form-control">
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
						    	<label class="col-sm-4 control-label"><b style="color:red;">*</b>商户地址：</label> 
	               				<div class="col-sm-8">
	               					<input  type="text" name="address" class="form-control">
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><b style="color:red;">*</b>商户类型：</label> 
	               				<div class="col-sm-8">
	               					<select class="form-control" name="shopCategoryId" id="shopCategoryId">
								    </select>
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><b style="color:red;">*</b>坐标地址：</label>
	                            <div class="col-sm-7">
	               					<input class="form-control" type="text" name="gps" id="gps">
	               				</div>
	               					<img id="showStationGc"  style="cursor:pointer;margin-top: 8px;" alt="点击配置坐标" title="点击配置坐标" src="<%=path %>/pages/map/imags/world.png" />
	                        </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">结算账号名称：</label> 
	               				<div class="col-sm-8">
	               					<input class="form-control" type="text" name="bankCardName" >
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><b style="color:red;">*</b>分成比例：</label>
	                            <div class="col-sm-8">
	               					<input class="form-control" type="text" placeholder="例如:0.3"  name="percentage" id="percentage">
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">分成账号：</label> 
	               				<div class="input-group m-b" style="padding-left: 15px;">
                                        <div class="input-group-btn">
                                        <input type="hidden" name="bankId" id="bankId">
                                            <button data-toggle="dropdown" class="btn btn-white dropdown-toggle" type="button" id="bankName">选择银行 <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu" id="bankUL">
                                            </ul>
                                        </div>
                                        <input type="text" class="form-control" name="cardNo" id="cardNo" style="width: 114px;">
                                    </div> </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><b style="color:red;">*</b>客服电话：</label>
	                            <div class="col-sm-8">
	               					<input class="form-control" type="text" id="tel" name="tel" >
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><b style="color:red;">*</b>服务开始时间：</label> 
	               				<div class="col-sm-8">
	               					<!-- <input class="form-control" type="text" placeholder="2017-01-01"  name="serviceBeginTime" id="serviceBeginTime"> -->
	               					<input id="serviceBeginTime" class="laydate-icon form-control layer-date" placeholder="服务开始时间"  style="width: 207px;" name="serviceBeginTime" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><b style="color:red;">*</b>服务结束时间：</label>
	                            <div class="col-sm-8">
	               				<!--	<input class="form-control" type="text" placeholder="2018-01-01"  name="serviceEndTime" id="serviceEndTime">-->
	               					<input id="serviceEndTime" class="laydate-icon form-control layer-date" placeholder="服务结束时间"  style="width: 207px;" name="serviceEndTime" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">推荐排序：</label> 
	               				<div class="col-sm-8">
	               					<input class="form-control" type="text" name="sort" >
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><b style="color:red;">*</b>空间数：</label>
	                            <div class="col-sm-7">
	               					<input class="form-control" type="text" placeholder="10"  name="zoneSize" id="zoneSize" onchange="changeZoneSize(this.value)">
	               					
	               				</div>
	               					<span style="line-height: 35px">G</span>
	                        </div>
                       
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">是否推荐：</label> 
	               				<div class="col-sm-8">
	               					<input  type="radio" name="isRecommend" value="0"> 是&nbsp;&nbsp;&nbsp;&nbsp;
	               					<input  type="radio" name="isRecommend" value="1" checked="checked"> 否
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                        </div>
                        </div>
                        <div class="row" id="pic1">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">商户横条图：</label> 
	               				<div class="col-sm-8">
				                       <input type="file" id="tsmpPic" name="showPics" accept="image/*" class="form-control"/> &nbsp;&nbsp;&nbsp;
	               				</div>
						    </div>
	               			<div class="form-group col-sm-6"> 
	               				<div class="col-sm-12" style="margin-left: -20px;margin-top: 8px;">
				                       <input type="button" value="+" onclick="addLine()" style="height:19px;float: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (支持jpg，png，大小100k以内)
	               				</div>
						    </div>
                        </div>
                        <div class="row">
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><b style="color:red;">*</b>商户LOGO：</label>
	                            <div class="col-sm-8">
	               					<input class="form-control" type="file" id="logo" name="logo" >
	               				</div>
	                        </div>
	                        <div class="form-group col-sm-6"> 
	               				<div class="col-sm-12" style="margin-left: -20px;margin-top: 8px;">
				                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (支持png，大小50k以内)
	               				</div>
						    </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">商户介绍：</label> 
	               				<div class="col-sm-8">
	               					<textarea rows="5" cols="50" name="description"></textarea>
	               				</div>
						    </div>
						   
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">空间使用：</label> 
	               				<div class="col-sm-8">
	               					<div class="progress">
	               						<div class="progress-bar">
	               							<span class="sr-only" style="width:25%"></span>
	               						</div>
	               					</div>
	               					<div style="float: left;"><span id="zoneSizeText">（总共10G）</span></div>
	               				</div>
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
