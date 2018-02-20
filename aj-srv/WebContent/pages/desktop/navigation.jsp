<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <title></title>
    
	<style type="text/css">
	#ayscNavigation h3{
		height: 28px;
		line-height: 28px;
		padding:0px;
		margin:0px;
		font-size:12px;
		padding-left:20px;
		font-weight:normal;
		cursor:pointer;
		background:url("<%=mainWeb%>/images/admin/HT-nav-normalbg.png") repeat-x ;
	}
	</style>
	<script type="text/javascript">
	//初始化 -> 获取用户的资源
	$(function(){
		$.post(mainWeb + '/admin/user/getResourcesOfUser',
			function(data){
				for(var i = 0;data.children != null && i<data.children.length;i++){
					var node1 = data.children[i];
					var htmlText = "";
					for(var j = 0;node1.children != null && j<node1.children.length;j++){					
						var node2 = node1.children[j];					
						htmlText += " <h3";
						htmlText += " onclick=addTabToContentRegion('"+node2.id+"','"+node2.text+"','"+node2.attributes.href+"')";
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
			 		$(this).css("background","url('<%=mainWeb%>/images/admin/HT-nav-hoverbg.png')");
			 	}).bind("mouseout",function(){
			 		$(this).css("background","url('<%=mainWeb%>/images/admin/HT-nav-normalbg.png')");
			 	});
			},
			'json'
		);
	});
	
	
	//添加Tab到ContentRegion
	function addTabToContentRegion(id,title,href){
		var tabs = $('#contentRegion').tabs('tabs');
		var isExists = false;
		for(var i = 0;i<tabs.length;i++){
			if(tabs[i][0].id == id){
				isExists = true;
				break;
			}
		} 
		//alert("aaa" + mainWeb+href+"/aa/" + id);
		if(isExists){
			$('#contentRegion').tabs('select',title);
		}else{
			 $('#contentRegion').tabs('add',{
				id:id,  
			  	title:title,  
			  	content:"<iframe src = " + mainWeb+href + " width='100%' height='100%' />",
			  	closable:true
			});
		}
	 }
	</script>
</head>  
  <body>
	<div class="easyui-accordion" data-options="fit:true,border:false" id="ayscNavigation">
	</div>
  </body>
</html>
