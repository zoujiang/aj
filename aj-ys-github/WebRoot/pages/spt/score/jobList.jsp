<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>成绩导入</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	<script type="text/javascript" src="js/jobList.js?t=<%=System.currentTimeMillis() %>"></script>
   
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
		                <%--
		                <div class="input-group" style="margin-top: 5px;">
			                <button id="btn_exp" type="button" class="btn btn-outline btn-default" title="导出">
		                        <i class="glyphicon glyphicon-export" aria-hidden="true" ></i>
		                    </button>
	                    </div>
		                 --%>
		                
		                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                <div class="form-group">
							    <button onclick="AppMgr.batch();" class="btn btn-outline btn-default" type="button" style="margin-top: 5px;">成绩导入</button>
							    <button onclick="AppMgr.doImp();" class="btn btn-outline btn-default" type="button" style="margin-top: 5px;">生成成绩</button>
							    <button onclick="AppMgr.doCert();" class="btn btn-outline btn-default" type="button" style="margin-top: 5px;">生成证书</button>
		                </div>
		                
		            </div>
		        </div>
                <table id="div_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
