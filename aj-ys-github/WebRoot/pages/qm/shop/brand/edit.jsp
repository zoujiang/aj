<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = request.getParameter("id");

%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-编辑商户分类</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
	 function AppMgr(){
		 this.initDatas = function(){
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/shop/brand/find",
	             data: "id=<%=id %>",
	             dataType: "json",
	             success: function(data){
	                  if(data.success){
	                	  $("#brandName").val(data.message.brand_name);
	                	  $("#sortIndex").val(data.message.sort_index);
	                	  if(data.message.status == 0){
	                		  $("#ra0").attr("checked","checked");
	                	  }else{
	                		  $("#ra1").attr("checked","checked");
	                	  }
	                	  $("#type_"+data.message.type).attr("selected", "selected");
	                	  if(data.message.is_recommend == 0){
	                		  $("#recommend_0").attr("checked","checked");
	                	  }else{
	                		  $("#recommend_1").attr("checked","checked");
	                	  }
	                	  console.info("data.message.brand_icon:"+data.message.brand_icon)
	                	//  $("#icon").val(data.message.brand_icon);
	                	  if(data.message.brand_icon != null && data.message.brand_icon != ""){
	                		  $("#preView").show();
	                		  $("#preView").attr("href", data.message.brand_icon );
	                	  }

	                  }else{
	                	  layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
							});
	                  }
	         	 }
	    	});
			 
	  	 }
	 }
	 $(document).ready(function() {
	  		new AppMgr().initDatas();
		});
	 
	 function saveUser(){
			
			var name = $("#brandName").val();
			if(name == ""){
				layer.msg("品牌名称不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var sortIndex = $("#sortIndex").val();
		    if(sortIndex.length > 5){
				layer.msg("排序长度不能超过5位数", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/shop/brand/update",
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
	 
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content row">
               	 	<form class="form-horizontal" id="userForm" action="<%=path %>/admin/shop/category/add" enctype="multipart/form-data" method="post">
               	 		<input type="hidden" name="id" id="roleId" value="<%=id %>">
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">品牌名称：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="brandName" id="brandName">  
	               				</div>
						    </div>

							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label">类型：</label>
								<div class="col-sm-7">
									<select class="form-control" name="type">
										<option name="type" id="type_1"  value="1">相馆</option>
										<option name="type" id="type_2" value="2">幼儿园</option>
										<option name="type" id="type_3" value="3">卡券</option>
									</select>
								</div>
							</div>

                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">排序：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="sortIndex" id="sortIndex">  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">状态：</label>
	                            <div class="col-sm-8">
	               					<input type="radio" id="ra0" name="status" value="0" checked="checked">正常&nbsp;&nbsp;&nbsp;&nbsp;
	               					<input type="radio" id="ra1" name="status" value="1">冻结
	               				</div>
	                        </div>
                        </div>
						<div class="row">
							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label">品牌图标：</label>
								<div class="col-sm-8">
									<input type="file" class="form-control" name="file" id="icon"><a id="preView" href="" target="blank" style="display:none;">预览</a>
									<span class="help-block m-b-none" style="font-size: 6px;color: lightgray;">建议尺寸220x164,大小50k以内</span>
								</div>
							</div>
							<div class="form-group col-sm-6">
								<label class="col-sm-4 control-label">是否推荐：</label>
								<div class="col-sm-7" style="line-height: 35px;">
									<input type="radio" name="isRecommend" id="recommend_1" value="1" checked="checked">是&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="isRecommend" id="recommend_0" value="0">否
								</div>
							</div>
						</div>
               		</form>
               		<div class="hr-line-dashed"></div>
               		<div class="text-center">
               		 	<button id = "btn_save" onclick="saveUser()" type="button" class="btn btn-primary">保存</button>
                       </div>
                </div>
            </div>
      </div>
  </body>
</html>
