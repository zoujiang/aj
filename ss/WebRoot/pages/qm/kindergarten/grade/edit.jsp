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


    <title>管理平台-编辑幼儿园班级</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/laydate/laydate.js"></script>


	<script type="text/javascript">
	 function AppMgr(){
		 var kindergartenId = "";
		 var firstTeacher = "";
		 var secondTeacher = "";
		 var nurse = "";
		 this.initDatas = function(){
		
			 var date=new Date;
			 var year=date.getFullYear();
			 var serHtml = "";
			 for(var i=0 ; i<5; i++){
				 serHtml += "<option value="+(year -i)+">" + (year -i) +"</option>" ;
			 }
			 $("#series").html(serHtml);
			 
			 
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/kindergarten/grade/find",
	             data: "id=<%=id %>",
	             dataType: "json",
	             success: function(data){
	                  if(data.success){
	                	  kindergartenId = data.message.kindergartenId;
	                	  initKindergarten(kindergartenId);
	                	  firstTeacher = data.message.firstTeacher;
	                	  secondTeacher = data.message.secondTeacher;
	                	  nurse = data.message.nurse;
	                	  queryTeacherByKinder(kindergartenId, firstTeacher, secondTeacher, nurse);
	                	  $("#declaration").val(data.message.declaration);
	                	  if(data.message.logo != null && data.message.logo !=''){
	                		  $("#preView").attr("href", data.message.logo);
	                		  $("#preView").show();
	                	  }
	                	  $("#series").find("option[value='"+data.message.series+"']").attr("selected",true);
	                	  var classNo = data.message.classNo;
	                	  if(classNo != null && classNo != ""){
	                		  if(classNo == '一班' || classNo == '二班' ||classNo == '三班' || classNo == '四班' || classNo == '五班'|| classNo == '0'){
	                		 	  $("#classNo_select").find("option[value='"+classNo+"']").attr("selected",true);
	                		  }else{
	                			 $("#classNo_select").hide();
		             			 $("#classNo_select").attr("disabled",true);
	                			 $("#classNo_input").attr("disabled",false); 
	             				 $("#classNo_input").show();
	             				 $("#classNo_input").val(classNo);
	                		  }
	                	  }
	                	  var rule = data.message.rule;
	                	  var rules = rule.split(";");
	                	  $("input[name='rule']").removeAttr("checked");
	                	  for(var i = 0; i<rules.length; i++){
	                		  if(rules[i] != null && rules[i] != ""){
		                		  $("input[name='rule']").each(function(){   
		                	            if ($(this).val() == rules[i]) {  
		                	                this.checked=true;  
		                	            }   
		                	        });  
	                		  }
	                	  }
	                	  
	                	 setClassName();
	                  }else{
	                	  layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
							});
	                  }
	         	 }
	    	});
	  	 }
	 }
	 
	 
	 function initKindergarten(kindergartenId){
		 $.ajax({
             type: "GET",
             url: "<%=path %>/admin/kindergarten/kindergarten/all",
             dataType: "json",
             success: function(data){
            	 var arr = data.data;
            	 var html = "";
            	  $.each( arr, function(index, content)
            	  { 
            		  if(content.id == kindergartenId){
            		  		html += "<option selected='selected' value='"+content.id+"'>"+content.name+"</option>";
            		  }else{
	            		    html += "<option value='"+content.id+"'>"+content.name+"</option>";
            		  }
            	  });
            	  $("#kindergartenId").html(html);
         	 }
    	});
	 }
	 function queryTeacherByKinder(kinder, firstTeacher, secondTeacher, nurse){
		 
		 if(kinder != ""){
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/kindergarten/teacher/list?type=4&offset=0&limit=10000&kindergartenId="+kinder,
	             dataType: "json",
	             success: function(data){
	            	 var arr = data.rows;
	            	 var html = "<option value=''>-- 请选择 --</option>";
	            	  $.each( arr, function(index, content)
	            	  { 
	            		  if(content.id == firstTeacher){
	            			  html += "<option selected='selected' value='"+content.id+"'>"+content.name+"</option>";
	            	   	  }else{
	            	   		 html += "<option value='"+content.id+"'>"+content.name+"</option>";
	            	   	  }
	            		 
	            	  });
	            	  $("#firstTeacher").html(html);
	         	 }
	    	});
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/kindergarten/teacher/list?type=5&offset=0&limit=10000&kindergartenId="+kinder,
	             dataType: "json",
	             success: function(data){
	            	 var arr = data.rows;
	            	 var html = "<option value=''>-- 请选择 --</option>";
	            	  $.each( arr, function(index, content)
	            	  { 
	            		  if(content.id == secondTeacher){
	            			  html += "<option selected='selected' value='"+content.id+"'>"+content.name+"</option>";
	            	   	  }else{
	            	   		 html += "<option value='"+content.id+"'>"+content.name+"</option>";
	            	   	  }
	            	  });
	            	  $("#secondTeacher").html(html);
	         	 }
	    	});
			 $.ajax({
	             type: "GET",
	             url: "<%=path %>/admin/kindergarten/teacher/list?type=6&offset=0&limit=10000&kindergartenId="+kinder,
	             dataType: "json",
	             success: function(data){
	            	 var arr = data.rows;
	            	 var html = "<option value=''>-- 请选择 --</option>";
	            	  $.each( arr, function(index, content)
	            	  { 
	            		  if(content.id == nurse){
	            			  html += "<option selected='selected' value='"+content.id+"'>"+content.name+"</option>";
	            	   	  }else{
	            	   		 html += "<option value='"+content.id+"'>"+content.name+"</option>";
	            	   	  }
	            	  });
	            	  $("#nurse").html(html);
	         	 }
	    	});
		 }
	 }
	 function selectOpt(value){
		 var selectedOption=value.options[value.selectedIndex];  
		 if(selectedOption.value=="0"){  
			 $("#classNo_input").attr("disabled",false); 
			 $("#classNo_input").show();
			 $("#classNo_select").hide();
			 $("#classNo_select").attr("disabled",true); 
		 }
		 setClassName();
	 }
	 function setClassName(){
		 
		 var className = "";
		 var series = $("#series").val();
		 if(series != ''){
			 className += series + "级";
		 }
		 var date=new Date;
		 var year=date.getFullYear();
		 className += $("input[name='rule']:checkbox:checked")[(year - series)].value;
		 
		 if($("#classNo_select").val() != '' && $("#classNo_select").val() != '0'){
			 className +=  $("#classNo_select").val();
		 }else if($("#classNo_select").val() == '0'){
			 className +=  $("#classNo_input").val();
		 }
		 $("#className").val(className);
	 }
	 $(document).ready(function() {
	  		new AppMgr().initDatas();
		});
	 
	 function saveUser(){
			
		 $("#btn_save").attr("disabled", true);
			var kindergartenId = $("#kindergartenId").val();
			if(kindergartenId == null || kindergartenId == ""){
				layer.msg("所属幼儿园不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var series = $("#series").val();
			if(series == null || series == ""){
				layer.msg("开学级数不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
			var firstTeacher = $("#firstTeacher").val();
			if(firstTeacher == null || firstTeacher == ''){
				layer.msg("主教不能为空", {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
				$("#btn_save").attr("disabled", false);
				return false;
			}
		
			$("#userForm").ajaxSubmit({
			     type: "post",
			     url: "<%=path %>/admin/kindergarten/grade/update",
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
               	 	<form class="form-horizontal" id="userForm" action="<%=path %>/admin/shop/account/add" method="post">
               	 		<input type="hidden" name="id" id="roleId" value="<%=id %>">
               	 		<div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><span style="color: red;">*</span>所属幼儿园：</label> 
	               				<div class="col-sm-8">
	               					<select class="form-control" name="kindergartenId" id="kindergartenId" onchange="queryTeacherByKinder(this.value)">
								    </select>
	               				</div>
						    </div>
						    <div class="form-group col-sm-6">
	                        </div>
                        </div>
                        <div class="row">
                        <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label"><span style="color: red;">*</span>开学级数：</label>
	                            <div class="col-sm-8">
	               					<select class="form-control" name="series" id="series" onchange="setClassName()">
	               					
	               					 </select>
	               				</div>
	                        </div>
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><span style="color: red;">*</span>班级排位：</label> 
	               				<div class="col-sm-8">
	               					<input type="text" class="form-control" style="display: none;" disabled="disabled" name="classNo" id="classNo_input" onchange="setClassName()"> 
	               					<select class="form-control" id="classNo_select" name="classNo" onchange="selectOpt(this)">
	               						<option value=""> 不排位 </option>
	               						<option value="一班"> 一班 </option>
	               						<option value="二班"> 二班 </option>
	               						<option value="三班"> 三班 </option>
	               						<option value="四班"> 四班 </option>
	               						<option value="五班"> 五班 </option>
	               						<option value="0"> 自定义 </option>
	               					</select>
	               				</div>
						    </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label"><span style="color: red;">*</span>主教：</label> 
	               				<div class="col-sm-8">
	               					<select class="form-control"  id="firstTeacher" name="firstTeacher">
	               					</select>
	               				</div>
						    </div>
						    <div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">副教：</label> 
	               				<div class="col-sm-8">
	               					<select class="form-control"  id="secondTeacher" name="secondTeacher">
	               					</select>
	               				</div>
						    </div>
                        </div>
                        <div class="row">
	               			<div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">保育员：</label> 
	               				<div class="col-sm-8">
	               					<select class="form-control"  id="nurse" name="nurse">
	               					</select>
	               				</div>
						    </div>
						    <div class="form-group col-sm-6"> 
	               				<label class="col-sm-4 control-label">班级LOGO：</label> 
	               				 <div class="col-sm-8">
	               					<input class="form-control" type="file" id="logo" name="logoImg" > <a id="preView" href="" target="_blank" style="display: none"><b>预览</b></a>
	               				</div>
						    </div>
                        </div>
                        <div class="row">
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">班级宣言：</label>
	                            <div class="col-sm-8">
	               					<textarea rows="5" cols="40" id="declaration" name="declaration" ></textarea>
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
						    <div class="form-group col-sm-8">
	                            <label class="col-sm-3 control-label">级数规则：</label>
	                            <div class="col-sm-9" style="margin-top: 5px;">
	               					<input type="checkbox" name="rule" value="小小班" onclick="setClassName()" checked="checked">小小班&nbsp;&nbsp;&nbsp;&nbsp;
	               					<input type="checkbox" name="rule" value="小班" onclick="setClassName()" checked="checked">小班&nbsp;&nbsp;&nbsp;&nbsp;
	               					<input type="checkbox" name="rule" value="中班" onclick="setClassName()" checked="checked">中班&nbsp;&nbsp;&nbsp;&nbsp;
	               					<input type="checkbox" name="rule" value="大班" onclick="setClassName()" checked="checked">大班&nbsp;&nbsp;&nbsp;&nbsp;
	               					<input type="checkbox" name="rule" value="大大班" onclick="setClassName()" checked="checked">大大班
	               				</div>
	                        </div>
                        </div>
                        <div class="row">
						    <div class="form-group col-sm-6">
	                            <label class="col-sm-4 control-label">班级名称：</label>
	                            <div class="col-sm-8">
	               					<input  type="text" class="form-control" readonly="readonly" id="className" name="className" >
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