<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>证书生成规则</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	<script type="text/javascript" src="js/cfgList.js?t=<%=System.currentTimeMillis() %>"></script>
   
</head>

<body class="gray-bg" style="height: 100%;">
    <div class="wrapper wrapper-content animated fadeInRight" style="height: auto;">
        <div class="ibox float-e-margins"  style="height: 100%;">
            <div class="ibox-content"  style="height: 100%;">
            	<div class="btn-group hidden-xs" id="toolbar" style="margin-top: 10px;">
		            <div class="form-inline" style="float: left; margin-left: 10px; margin-bottom: 15px;">
		                
					    &nbsp;&nbsp;&nbsp;&nbsp;赛事组别：
		                
		                <div class="form-group">
		                  	<select id="group" class="selectpicker form-control w70" title="赛事组别">
					        </select>
		                </div>
		                
		                <div class="input-group" style="margin-top: 5px;">
		                	<div class="input-group-btn">
					            <button id="btn_search" class="btn btn-default" type="button"  title="查询"><i class="glyphicon glyphicon-search"></i></button>
					        </div>
		                </div>
		                
		                
		            </div>
		        </div>
                <table id="div_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
