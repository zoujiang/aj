<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>成绩管理</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
    <script type="text/javascript" src="js/jobEdit.js?t=<%=System.currentTimeMillis() %>"></script>
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
			        <!-- <select name="type" id="type" class="form-control m-b" style="margin-top: 15px;">
                    	<option value="">类型</option>
                        <option value="code">全程</option>
                        <option value="phone">半程</option>
                        <option value="idcard">迷你</option>
                        <option value="name">亲子</option>
                    </select> -->
                    
                    <select id="group" class="selectpicker form-control w70" title="赛事组别">
					</select>
					        
			    </div> 
			    <%--
			    <div class="form-group">  
			        <label class="control-label">导入名称：</label>  
			        <input type="text" required="required" id="jobName" name="jobName" class="form-control name m-b valid" aria-required="true" aria-invalid="false">
			    </div> 
			     --%>
			    <div class="form-group">  
			        <label class="control-label">上传成绩附件</label>  
			        <div class="row">
				        <div class="input-append col-sm-3">
				        	&nbsp;&nbsp;&nbsp;
				        	<%--
							<img src="images/upload.jpg" width=40px	height=30px>
				        	 --%>
							<input id="uploadFile" name="file" type="file" class="uploadButton"></img>
						</div>
			    		<div id="shopHedImg3Show" class="col-sm-3">
						</div>
					</div>
					
			    </div> 
			    
			     <div class="form-group">  
			                        请先下载模板（
			        <a href="${ctx }/res/xls/bancheng.xls" target="_blank">半程模板</a>，
			        <a href="${ctx }/res/xls/quancheng.xls" target="_blank">10公里模板</a>，
			        <a href="${ctx }/res/xls/mini.xls" target="_blank">迷你模板</a>，
			        <a href="${ctx }/res/xls/qinzi.xls" target="_blank">亲子模板</a>）
                    <br/>然后填写好Excel成绩数据后提交上传，成绩上传后，对于有成绩的进行排名.
                    <br/>注：时间成绩区域要设置为"文本"格式，Excel可能默认为时间格式.
			    </div> 
			    
			    <div class="modal-footer">  
		    		<button id="btn_score" type="button" class="btn btn-primary">成绩导入 </button> 
		    		<button id="btn_score_crt" type="button" class="btn btn-primary" style="margin-top: -6px;">成绩导入并生成证书</button> 
		    		<button id="btn_close" type="button" class="btn btn-default" data-dismiss="modal" style="margin-top: -6px;">关闭 </button>  
				</div>
					  
			</form>   
			
		</div>
		
		
    </div>  
</div>  
</body>

</html>
