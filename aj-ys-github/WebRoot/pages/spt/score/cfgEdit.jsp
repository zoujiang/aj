<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>成绩管理</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <script type="text/javascript" src="js/cfgEdit.js?t=<%=System.currentTimeMillis() %>"></script>
    <script type="text/javascript" src="${ctx }/pages/comm/js/ajaxfileupload.js"></script>
    
<%
String thisId = request.getParameter("id");
%>
<script type="text/javascript">

var thisId = "<%=thisId%>";


</script>

<style type="text/css">
	/* .layui-layer-content{color: #000;}
	.upload{color: #00070c;text-decoration: none;position:relative;display:block;width:100px;height:30px;background:#FEFEFE;;border:1px solid #999;text-align:center;}
	.uploadButton{position:absolute;left:0;top:0;width:100%;height:100%;z-index:999;opacity:0;}
	 */
</style>

</head>

<body class="gray-bg" >
    <div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox float-e-margins" style="margin-bottom: 0px;">
    	<div class="ibox-content row">
			<form id="appForm">  
			    <div class="form-group">  
			        <label class="control-label">赛事组别：</label>
			        <input type="text" id="groupName" class="form-control name m-b valid" readonly> 
			    </div>  
			    <div class="form-group">  
			        <label class="control-label">是否需要成绩：</label>  
			        <select name="isNeedScore" id="isNeedScore" class="form-control m-b">
                    	<option value="1">是</option>
                        <option value="0">否</option>
                    </select>
			    </div> 
			    <div class="form-group" id="div_minScore" style="display:none;">  
			        <label class="control-label">达标成绩：（如：00:00:56）</label>  
			        <input type="text" id="minScore" name="minScore" class="form-control name m-b valid" >
			    </div> 
			    <div class="form-group">  
			        <label class="control-label">证书背景图片</label>  
			        <div class="row">
				        <div class="input-append col-sm-3">
				        	&nbsp;&nbsp;&nbsp;
							<input id="uploadFile" name="file" type="file" class="uploadButton"></img>
						</div>
						<br/>
			    		<div id="div_showView" class="col-sm-3">
						</div>
					</div>
					
			    </div> 
			    
			     <div class="form-group">  
			                        模板（
			        <a href="${ctx }/res/cert/bc19.jpg" target="_blank">半程模板</a>，
			        <a href="${ctx }/res/cert/qc210.jpg" target="_blank">10公里模板</a>，
			        <a href="${ctx }/res/cert/mn311.jpg" target="_blank">迷你模板</a>，
			        <a href="${ctx }/res/cert/qz4.jpg" target="_blank">亲子模板</a>
			                        ），<br/><font color="red">图片分辨率大小、动态打印的位置一定不能变</font>
			    </div> 
			    
			    <div class="modal-footer">  
		    		<button id="btn_score" type="button" class="btn btn-primary">确认 </button> 
		    		<button id="btn_close" type="button" class="btn btn-default" data-dismiss="modal" style="margin-top: -6px;">关闭 </button>  
				</div>
					  
			</form>   
			
		</div>
		
		
    </div>  
</div>  
</body>

</html>
