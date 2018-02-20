
function fileUpload(domid,e) {
	var obj = new Object();
	obj.domid = domid;
	
	com.eason.window.winUtils.showDialogWindowOfWH(400,200,e,mainWeb + "/pages/ad/pub/upload.jsp",obj,function(returnValue){
		
		if (returnValue == "" || returnValue == null  || returnValue == undefined) {
			return;
		}
		
		var obj = JSON.parse(returnValue);
		
		$("#" + obj.domid).val(obj.url);
		
		
	});
}


function viewFile(domid, event) {
	
	var url = $("#" + domid).val();
	if (url == "" || url == null  || url == undefined) {
		return;
	}
	
	previewImage(event, url);
	
}

function deleteFile(domid, event) {
	
	var url = $("#" + domid);
	if (url == "" || url == null  || url == undefined) {
		return;
	}
	url.val('');
	//previewImage(event, url);
	
}