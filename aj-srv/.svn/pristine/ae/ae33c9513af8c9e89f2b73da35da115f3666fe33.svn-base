//初始化 -> 获取用户的资源
//添加Tab到contentRegion contentRegion

function addTabToContentRegion(id,title,href){
		//获得left.jsp平级的welcome.jsp的document对象
		this.frameElement.parentElement.children['main'].src=mainWeb+href;
		
//		var div1=this.frameElement.parentElement.children['main'].contentDocument.body.firstElementChild;
//		$(div1).css("border","6px solid rgb(222, 222, 222)");
}
$(function(){
	$.post(mainWeb + '/admin/user/getResourcesOfUser',
	function(data){
		for(var i = 0;data.children != null && i<data.children.length;i++){
			var node1 = data.children[i];
			var htmlText = "";
			for(var j = 0;node1.children != null && j<node1.children.length;j++){					
				var node2 = node1.children[j];					
				htmlText += " <h3";
				htmlText += " onclick=addTabToContentRegion('"+node2.id+"','"+node2.text+"','"+node2.attributes.href+"');";
				htmlText += " >";
				htmlText += node2.text;
				htmlText += " </h3>";
			}
			$('#ayscNavigation').accordion('add',{
				title:node1.text,
				content:htmlText
			});
		}
		$("#ayscNavigation h3").bind("mouseover",function(){
	 		$(this).css("background","url('../images/admin/HT-nav-hoverbg.png')");
	 	}).bind("mouseout",function(){
	 		$(this).css("background","url('../images/admin/HT-nav-normalbg.png')");
	 	});
		$("#ayscNavigation h3").click(function(e){
			e.stopPropagation();
			return false;
		});
	},
	'json'
);
});


