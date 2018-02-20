<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>马拉松-添加选手号码生成规则</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>
	
	<script type="text/javascript">
		function saveUser(){
			var url = getProjectName()+"/admin/athlete/edit";
    		var queryString = $('#applyForm').formSerialize();
			var obj = ajaxSubmit(url,queryString);
			if(obj.success){
				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
					var index = parent.layer.getFrameIndex(window.name);
					parent.$('#athlete_table').bootstrapTable('refresh');
					//parent.$("button[name='refresh']").click();
					parent.layer.close(index);
		  		});
			}
			else{
				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
			}

			$("#btn_save").attr("disabled", false);
		}
		
		function initData(){
	    	//$("#group").selectpicker({});  //初始化
	    	$.ajax({
	   	        type: "POST",
	   	        url: getProjectName() + "/admin/athlete/getAthleteEdit",
	   	        dataType: "json",
	   	     	async: false,
	   	        data:{
	   	        	applyId:"<%= request.getParameter("applyId")%>",
	   	        	groupId:"<%= request.getParameter("groupId")%>"
	   	        },
	   	        success: function(data){
	   	        	if(data == null || data == ''){
	   					return;
	   				}
	   	        	if(data.success){
	   	        		$("#truename").val(data.apply.truename);
	   	        		$("#gamecode").val(data.apply.gamecode);
	   	        		$("#certtype").val(data.apply.certtype);
	   	        		$("#certno").val(data.apply.certno);
	   	        		$("#birthday").val(data.apply.birthday);
	   	        		$("#bloodtype").val(data.apply.bloodtype);
	   	        		$("#tshirt").val(data.apply.tshirt);
	   	        		$("#mobile").val(data.apply.mobile);
	   	        		$("#email").val(data.apply.email);
	   	        		$("#address").val(data.apply.address);
	   	        		$("#contactname").val(data.apply.contactname);
	   	        		$("#contacttel").val(data.apply.contacttel);
	   	        		
	   	        		$("#childname").val(data.apply.childname);
	   	        		$("#childcerttype").val(data.apply.childcerttype);
	   	        		$("#childcertno").val(data.apply.childcertno);
	   	        		$("#childbirthday").val(data.apply.childbirthday);
	   	        		$("#childbloodtype").val(data.apply.childbloodtype);
	   	        		$("#childtshirt").val(data.apply.childtshirt);
	   	        	}
	   	        }
	   		});	
	    }
		
		$(function(){
			var birthday={
				elem:"#birthday",
				format:"YYYY/MM/DD",
				max:laydate.now(),
				istime:false,
				istoday:false,
				event:"focus",
				choose:function(datas){
				}
			};
			var childbirthday={
				elem:"#childbirthday",
				format:"YYYY/MM/DD",
				max:laydate.now(),
				istime:false,
				istoday:false,
				event:"focus",
				choose:function(datas){
				}
			};
			/*
			var childbirthday={
					elem:"#childbirthday",
					format:"YYYY/MM/DD",
					max:laydate.now(),
					istime:false,
					istoday:false,
					event:"focus",
					choose:function(datas){
					}
				};*/
			laydate(birthday);
			laydate(childbirthday);
			initData();
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
               	 	<form class="form-horizontal" id="applyForm">
               	 		<input name="applyId" type="hidden" value="<%= request.getParameter("applyId")%>">
               	 		<input name="groupId" type="hidden" value="<%= request.getParameter("groupId")%>">
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">选手姓名：</label> 
	               				<div class="col-sm-8">
	               					 <input type="text" class="form-control" name="truename" id="truename">  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">参赛号码：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="gamecode" id="gamecode" disabled>
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">证件类型：</label> 
	               				<div class="col-sm-8">
	               					<select class="form-control w70" name="certtype" id="certtype">
		                                <option value="2">身份证</option>
		                                <option value="4">护照</option>
		                            </select>
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">证件号码：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="certno" id="certno">  
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
                        	<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label" style="float: left;">生日：</label>  
						        <div class="col-sm-8">
						        	<input type="text" class="laydate-icon form-control layer-date col-sm-12" name="birthday" id="birthday" >
						        </div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">血型：</label>
	                            <div class="col-sm-8">
	               					<select class="form-control w70" name="bloodtype" id="bloodtype">
		                                <option value="A">A型</option>
		                                <option value="B">B型</option>
		                                <option value="AB">AB型</option>
		                                <option value="O">O型</option>
		                            </select>
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
                        	<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label" style="float: left;">T恤尺寸：</label>  
						        <div class="col-sm-8">
						        	<select class="form-control w70" name="tshirt" id="tshirt">
		                                <option value="8">S（成人）</option>
		                                <option value="10">M（成人）</option>
		                                <option value="9">L（成人）</option>
		                                <option value="15">XL（成人）</option>
		                            </select>
						        </div>
						    </div>
						    <div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">小孩姓名：</label> 
	               				<div class="col-sm-8">
	               					 <input type="text" class="form-control" name="childname" id="childname">  
	               				</div>
						    </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">小孩证件类型：</label> 
	               				<div class="col-sm-8">
	               					<select class="form-control w70" name="childcerttype" id="childcerttype">
		                                <option value="2">身份证</option>
		                                <option value="4">护照</option>
		                            </select>
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">小孩证件号码：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="childcertno" id="childcertno">  
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
                        	<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label" style="float: left;">小孩生日：</label>  
						        <div class="col-sm-8">
						        	<input type="text" class="laydate-icon form-control layer-date col-sm-12" name="childbirthday" id="childbirthday" >
						        </div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">小孩血型：</label>
	                            <div class="col-sm-8">
	               					<select class="form-control w70" name="childbloodtype" id="childbloodtype">
		                                <option value="A">A型</option>
		                                <option value="B">B型</option>
		                                <option value="AB">AB型</option>
		                                <option value="O">O型</option>
		                            </select>
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
                        	<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label" style="float: left;">小孩T恤：</label>  
						        <div class="col-sm-8">
						        	<select class="form-control w70" name="childtshirt" id="childtshirt">
		                                <option value="16">100cm（儿童）</option>
		                                <option value="17">110cm（儿童）</option>
		                                <option value="18">120cm（儿童）</option>
		                                <option value="19">130cm（儿童）</option>
		                                <option value="20">140cm（儿童）</option>
		                            </select>
						        </div>
						    </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">手机号码：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="mobile" id="mobile">  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">电子邮箱：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="email" id="email">  
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-12"> 
	               				<label class="col-sm-2 control-label" style="float: left;">通讯地址：</label>  
						        <div class="col-sm-9">
						        	<input type="text" class="form-control col-sm-12" name="address" id="address" >
						        </div>
						    </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">紧急联系人：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" name="contactname" id="contactname">  
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">联系人电话：</label>
	                            <div class="col-sm-8">
	               					<input type="text" class="form-control" name="contacttel" id="contacttel">  
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
