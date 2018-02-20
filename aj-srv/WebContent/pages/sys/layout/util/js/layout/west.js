/**
 * 初始化菜单树
 * @param path
 * @returns
 */
var initTree = function(){
	var loadUrl = getProjectName() + '/admin/user/getTree';
	$('#layout_west_tree').tree({
		url : loadUrl,
		animate : true,
		lines : true,
		onClick : function(node) {
			var url;
			if (node.attributes && node.attributes.href) {
				url = getProjectName() + node.attributes.href;//"/menuctrl/showPage.do?page=" + node.attributes.url+"&resId="+node.id;
				//alert( node.id)
				layout_center_addTabFun({
					id:'a' + node.id,
					title : node.text,
					closable : false,
					cache:false,
					iconCls : '',
					href : url
				});
			}
		}
	}); 
};

/**
 * 初始化panel(在panel上添加操作)
 * @returns
 */
var initPanel = function(){
	
	$('#westPanel').panel({
		title:'功能导航',
		iconCls:'icon-sys',
		fit:true,
		border:false,
		tools : [{
			iconCls : 'icon-reload',
			handler : function() {
				$('#layout_west_tree').tree('reload');
			}
		}, {
			iconCls : 'icon-expand',
			handler : function() {
				var node = $('#layout_west_tree').tree('getSelected');
				if (node) {
					$('#layout_west_tree').tree('expandAll', node.target);
				} else {
					$('#layout_west_tree').tree('expandAll');
				}
			}
		}, {
			iconCls : 'icon-collapse',
			handler : function() {
				var node = $('#layout_west_tree').tree('getSelected');
				if (node) {
					$('#layout_west_tree').tree('collapseAll', node.target);
				} else {
					$('#layout_west_tree').tree('collapseAll');
				}
			}
		}, {
			iconCls : 'layout-button-left',
			handler : function() {
				$('#cc').layout('collapse','west');
			}
		}]
	});
};
