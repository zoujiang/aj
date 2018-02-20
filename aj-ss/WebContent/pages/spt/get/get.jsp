<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>发放物品</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <script type="text/javascript" src="js/get.js?t=<%=System.currentTimeMillis() %>"></script>
    
<%
String thisId = request.getParameter("id");
%>
<script type="text/javascript">

var thisId = "<%=thisId%>";


</script>
</head>

<body class="gray-bg" >
    <div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox float-e-margins" style="margin-bottom: 0px;">
    	<div class="ibox-content row">
			<form id="appForm">  
					 	<div class="form-group">  
					        <label class="control-label">参赛号码/证件号码：</label>  
					        <textarea id='psLine' name="psLine" maxlength="300" placeholder="半角逗号分割多个" class="form-control name m-b" ></textarea>
					        <input id='ps_check_before' type="hidden"/>
					        <input id='ps_check_ok' type="hidden"/>
					    </div> 
					     <div class="form-group">  
					        <label class="control-label"></label>  
					                       逗号（,）分割多个
					    </div> 
					    
					    <div class="modal-footer">  
				    		<button id="btn_check" type="button" class="btn btn-primary">核对信息 </button> 
				    		<button id="btn_get" type="button" class="btn btn-primary" style="margin-top: -6px;">一键领物</button> 
				    		<button id="btn_get_dis" type="button" class="btn btn-default" data-dismiss="modal" style="margin-top: -6px;">一键领物</button> 
						</div>
					  
					</form>   
			
		</div>
		
		<div class="ibox-content row"  style="display:none" id="div_check">
		<!-- 
		<div class="modal-body" style="display:none" id="div_check">
		<div style="padding: 0px 10px;display:none" id="div_check">
		 -->
			<label class="control-label">信息核对</label>
			<table id="table_check"></table>
			
			<div class="modal-footer">  
	    		<button id="btn_check_ok" type="button" class="btn btn-primary">核对完成 </button> 
			</div>
		</div>
		 
		<div class="modal-footer">  
		    <button id="btn_close" type="button" class="btn btn-default" data-dismiss="modal">关闭 </button> 
    		<%--
    		<button id="btn_save" type="button" class="btn btn-primary">确认 </button>   
    		 --%>
		</div>
    </div>  
</div>  
</body>

</html>
