<%@page import="com.frame.core.util.StringUtil"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
 <meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<title>添加区域</title>
<%@ include file="/pages/comm/jsp/inc.jsp"%>
<style type="text/css">
	.layui-layer-content{color: #000;}
</style>
<script type="text/javascript">


function AppMgr(){

	AppMgr.save = function(e){
		$("#appForm").submit();
	};
	
	//初始化：
	this.initDatas = function(){
		
	$("#appForm").validate({
		onfocusout: function(element){
	        $(element).valid();
	    },
		rules: {
			areaName: {
                required: true
            },
            areaOrder: {
                required: true,
                number:true 
            }
        },
        errorPlacement: function(error, element) {
        	var msgbox = $(element);
        	//小tips
        	layer.tips($(error).text(), $(element), {
			  tips: 1 //还可配置颜色
			});
        	//new Opentip($(element), "Shown after 2 seconds", { delay: 2 });
        	//new Opentip(msgbox, $(error).text(), { style: "glass", target: true, tipJoint: "bottom", targetJoint: "top", containInViewport: true });
        },
        submitHandler: function(form) {
			var url = getProjectName()+"/admin/area/addArea";
    		var queryString = $('#appForm').formSerialize();
			var obj = ajaxSubmit(url,queryString);
			//console.log(obj);
			
			if(obj.success){
				layer.msg(obj.message, {time:200,title:'提示', btn: ['确定'],icon: 6}, function(index){
					 var index = parent.layer.getFrameIndex(window.name);
						//parent.$("button[name='refresh']").click();
						//parent.refreshTable();
						parent.$('#demo_table').bootstrapTable('refresh');
						parent.layer.close(index);
		  		});
			}
			else{
				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
					var index = parent.layer.getFrameIndex(window.name);
					parent.$('#demo_table').bootstrapTable('refresh');
					parent.layer.close(index);
		  		});
			}
    		}
	}); 
	}

	this.initActions = function(){
		// 搜索
		 $('#btn_save').click(function() {
			 
			AppMgr.save();
		}); 
	};
	
	this.init = function() {
	 	this.initDatas();
	 	this.initActions();
	};
} 

$(document).ready(function() {
	//alert("aaaaaaaaaaa");
		new AppMgr().init();
});

</script>
</head>
<body>
<!--start modal2-->  
   <div class="modal-dialog modal-lg">  
       <div class="modal-content">  
		<div class="modal-body">
			<form id="appForm">  
				<input type="hidden" name="areaId">
				<div class="form-group">  
			        <label class="control-label">名称：</label>  
			        <input type="text" class="form-control name m-b" name="areaName" id="areaName">  
			    </div>  
			    <div class="form-group">  
			        <label class="control-label">排序：</label>  
			        <input type="text" class="form-control price m-b" name="areaOrder" id="areaOrder">  
			        
			    </div>  
			    <!--  <div class="form-group">  
							<label class="control-label">区域图片：</label>
								<input type='file' name='areaPicFile'  id='areaPicFile' title="规格：245*150的等比例尺寸,如720*440" style="height: 20px; width: 200px" />
								<span id="picViewload1" title="点击预览图片" style="display: none">点击预览图片</span>
				</div>   -->
			</form>    
		</div>  
		<div class="modal-footer">  
		    <button id = "btn_save" type="button" class="btn btn-primary">保存 </button>  
		</div>
       </div>  
   </div>  
	<!--end modal2-->  
</body>
</html>