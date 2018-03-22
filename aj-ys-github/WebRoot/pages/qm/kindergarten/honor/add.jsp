<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String ownerId = request.getParameter("ownerId");
String type = request.getParameter("type");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 
     <title>商户管理平台-照片管理</title>
	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	
    <link rel="shortcut icon" href="favicon.ico"> <link href="<c:url value='/resources/hplus4.1/css/bootstrap.min.css?v=3.3.6'></c:url>" rel="stylesheet">
    <link href="<c:url value='/resources/bootstrap-fileinput/css/fileinput.css'></c:url>" rel="stylesheet">
    <script src="<c:url value='/resources/hplus4.1/js/bootstrap.min.js?v=3.3.6' ></c:url>"></script>
    <script src="<c:url value='/resources/hplus4.1/js/content.min.js?v=1.0.0' ></c:url>"></script>
    <script type="text/javascript" src="<c:url value='/resources/bootstrap-fileinput/js/fileinput.js'></c:url>" ></script>
    <script type="text/javascript" src="<c:url value='/resources/bootstrap-fileinput/js/locales/zh.js'></c:url>"  ></script>
    
    <script type="text/javascript">
    
    $(document).ready(function () {
    	$("#f_upload").fileinput({
            theme: 'fa',
            language:'zh',
            uploadUrl: '<%=path %>/admin/fileUploadCommon/files/fileUpload', // you must set a valid URL here else you will get an error
            overwriteInitial: false,
            uploadExtraData:{"ownerId": '<%=ownerId %>', "type":'<%=type %>', "cate":'2'},
            showRemove:false,
            maxFileSize: 1000000,
            maxFilesNum: 100,
            allowedFileTypes: ['image'],
            slugCallback: function (filename) {
            	return filename.replace('(', '_');
            }
        }).on("fileuploaded", function (event, data, previewId, index) {
        	var form = data.form, files = data.files, extra = data.extra,
            response = data.response, reader = data.reader;
            console.log("index:"+index);//打印出返回的json
            console.log(response.success);//打印出返回的json
            console.log(response.url);//打印出返回的json
            console.log(response.type);//打印出路径
        }).on('fileloaded', function(event, data, previewId, index, reader) { 
        	console.info(data);
        }).on('fileerror', function(event, data, msg) {
        	console.info("!!!"+data);
        	if(data == null || data == ""){
        		console.info("文件格式错误");
        	}
        }).on("filebatchselected", function(event, files) {
        	console.info("files!!!"+files);
        	if(files == null || files == ""){
        		console.info("文件格式错误");
        	}
        }).on('filebatchuploaderror', function(event, data, msg) {
        	console.info("filebatchuploaderror:"+msg);
        	 $(".kv-fileinput-error.file-error-message").html("");
        });
            	
    });
	function returnToListPage(){
		
		var index = parent.layer.getFrameIndex(window.name);
		//parent.$('#apv_table').bootstrapTable('refresh');
		parent.$("button[name='refresh']").click();
		parent.layer.close(index);
		
	}

	</script>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeIn">
     	<div class="ibox float-e-margins" style="margin-bottom: 0px;">
             <div class="ibox-content row">
    			<form class="form-horizontal" id="userForm" method="post" enctype="multipart/form-data">
				   <input id="f_upload" type="file" name="file" multiple="multiple" />
    		</form>
		    <div class="hr-line-dashed"></div>
			<div class="text-center">
			 	<button id = "btn_save" onclick="returnToListPage()" type="button" class="btn btn-secondary radius">确定</button>
			      </div>
		    </div>
   		 </div>
    </div>
    
   
</body>
</html>
