<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>领物管理</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	<script type="text/javascript" src="js/list.js?t=<%=System.currentTimeMillis() %>"></script>
   
</head>

<body class="gray-bg" style="height: 100%;">
    <div class="wrapper wrapper-content animated fadeInRight" style="height: auto;">
        <div class="ibox float-e-margins"  style="height: 100%;">
            <div class="ibox-content"  style="height: 100%;">
            	<div class="btn-group hidden-xs" id="toolbar" style="margin-top: 10px;">
		            <div class="form-inline" style="float: left; margin-left: 10px; margin-bottom: 15px;">
		                
		                <div class="form-group">
		                   	 查询类型&nbsp;<select name="type" id="type" class="form-control m-b" style="margin-top: 15px;">
		                    	<option value="">查询类型</option>
                                <option value="code">参赛号码</option>
                                <option value="phone">手机号码</option>
                                <option value="idcard">证件号码</option>
                                <option value="name">跑者姓名</option>
                            </select>
		                    <input id="typeVal" name="typeVal" class="form-control" type="text" placeholder="输入查询值">
		                </div>
		                
		               <div class="form-group">
		                     	是否领物&nbsp;<select name="isGet" id="isGet" class="form-control m-b" style="margin-top: 15px;">
		                    	<option value="">全部</option>
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
		                </div>
		                <div class="form-group">
		                  	<select id="group" class="selectpicker form-control w70" title="赛事组别">
					        </select>
		                </div>
		                
		                <div class="input-group" style="margin-top: 5px;">
		                	<div class="input-group-btn">
					            <button id="btn_search" class="btn btn-default" type="button"  title="查询"><i class="glyphicon glyphicon-search"></i></button>
					        </div>
		                </div>
		                
		               <div class="input-group" style="margin-top: 5px;">
			                <button id="btn_exp" type="button" class="btn btn-outline btn-default" title="导出">
		                        <i class="glyphicon glyphicon-export" aria-hidden="true" ></i>
		                    </button>
	                    </div>
	                    
	                    <div class="input-group" style="margin-top: 5px;">
		                	<button type="button" class="btn btn-outline btn-default" onclick="javascript:AppMgr.batch();" title="发放物品">
		                        <i class="glyphicon glyphicon-plus" ></i> 
		                    </button>
		                </div>
		            </div>
		        </div>
                <table id="div_table"></table>
            </div>
        </div>
    </div>
</body>
</html>
