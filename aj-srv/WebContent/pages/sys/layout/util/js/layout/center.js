/**
 * 增加标签
 * @param opts
 * @returns
 */

var layout_center_addTabFun = function(opts){
	
	var tabs = $('#layout_center_tabs').tabs('tabs');
	var id = opts.id;
	var title = opts.title;
	var href = opts.href;
	var isExists = false;
	for(var i = 0;i<tabs.length;i++){
		if(tabs[i][0].id == id){
			isExists = true;
			break;
		}
	} 
	//alert("aaa" + mainWeb+href+"/aa/" + id);
	if(isExists){
		$('#layout_center_tabs').tabs('select',title);
	}else{
		 $('#layout_center_tabs').tabs('add',{
			id:id,  
		  	title:title,  
		  	content:"<iframe src = '" + href + "' width='100%' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' scrolling='no' allowtransparency='yes' />",
		  	closable:true
		});
	}
 }

/**
 * 初始化tab标签
 * @param userName
 * @returns
 */
var initPanel = function(userName){
	
	$('#layout_center_tabs').tabs({
		fit:true,
		border:false,
		tools:[{
			iconCls:'icon-user',
			border:false,
			id:'loginInfo',
			text:'<strong>'+userName+'</strong>，欢迎你！'
		},{
			iconCls:'icon-reload',
			border:false,
			handler:function(){
				
				var href=$('#layout_center_tabs').tabs('getSelected').panel('options');
				
				if (href) {
					var index = $('#layout_center_tabs').tabs('getTabIndex', $('#layout_center_tabs').tabs('getSelected'));
					//alert(index);
					var refresh_tab = $('#layout_center_tabs').tabs('getTab', index);
					
					//var refresh_tab = cfg.tabTitle?$('#tabs').tabs('getTab',cfg.tabTitle):$('#tabs').tabs('getSelected'); 
					if(refresh_tab && refresh_tab.find('iframe').length > 0){ 
						var _refresh_ifram = refresh_tab.find('iframe')[0]; 
						var refresh_url = _refresh_ifram.src; 
						_refresh_ifram.contentWindow.location.href=refresh_url; 
					}
					
				}
			} 
		}]
	});
};