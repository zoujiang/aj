<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>商户管理平台-配置资源</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

	<%@ include file="/pages/comm/jsp/inc.jsp"%>

	<script type="text/javascript">
		function saveRoleRes(){
		 var nodes = $("#res_tree").jstree("get_checked",true);
		    var ids = [];
		    $.each(nodes, function(i, n){
		    	if(n.id !="root"){
		    		ids.push(n.id);
		    	}
		    });
			var url = getProjectName()+"/admin/role/setRoleRes";
			var obj = ajaxSubmit(url,{resourceIds:ids.join(','), roleId:"<%= request.getParameter("rid") %>"});
			if(obj.success){
				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
		  		});
			}
			else{
				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
					$("#btn_save").attr("disabled", false);
				});
			}
		}
		$(function(){
			$("#res_tree").jstree({
				"core":{
					"data":{
						"dataType":"json",
						"url":"${ctx}/admin/resource/getRess",
					}
				},
				"checkbox" : {
				    "keep_selected_style" : false,
	                "three_state": false,       
	                "cascade": 'undetermined' //有三个选项，up, down, undetermined; 使用前需要先禁用three_state  
	            },  
			    "plugins" : ["checkbox"]
			}).on("activate_node.jstree", function (e, data) {
			}).on("loaded.jstree", function(e, data){
				var data = ajaxSubmit("${ctx}/admin/role/getRoleResIds",{rid:"<%= request.getParameter("rid") %>"});
				var tree = $('#res_tree').jstree(true);
				tree.check_node(data);
				/*var inst = data.instance;
				var obj = inst.get_node(e.target.firstChild.firstChild.lastChild);  
				inst.select_node(obj);*/ 
				//alert(obj.id);
				/*var tree = $('#res_tree').jstree(true);
				//var node = tree.get_node("00000000000000000000000000001000");
				tree.check_node(["00000000000000000000000000001001", "00000000000000000000000000001000"]);*/
			});
			// listen for event  
	       /* $('#res_tree').on('changed.jstree', function(e, data) {  
	            var r = [];  
	            var i, j;  
	            for (i = 0, j = data.selected.length; i < j; i++) {  
	                var node = data.instance.get_node(data.selected[i]);  
	                if (data.instance.is_leaf(node)) {  
	                    r.push(node.id);  
	                }  
	            }  
	        })  */
			/*$('#res_tree').bind("activate_node.jstree", function (e, data) {
				//alert(data.node.id);
			});*/
			/*$('#res_tree').bind("select_node.jstree", function (e, data) {
			    var nodes = $("#res_tree").jstree("get_checked",true);
			    var ids = [];
			    $.each(nodes, function(i, n){
			    	ids.push(n.id);
			    });
			    alert(ids.join(','));
			});*/
			$("#btn_save").click(function(){
				saveRoleRes();
				$("#btn_save").attr("disabled", true);
			});
			
		});
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="row wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content">
                 	<div class="col-sm-4" style="border: 1px solid #eee;">
	                 	<div id="res_tree" class="test"></div>
	                 	<div class="hr-line-dashed"></div>
                		<div class="text-center">
                		 	<!-- 
                		 	<button id = "btn_add" type="button" class="btn btn-primary" disabled="disabled">新增 </button>
                		 	<button id = "btn_edit" type="button" class="btn btn-primary" disabled="disabled">编辑 </button>
                		 	<button id = "btn_del" type="button" class="btn btn-primary" disabled="disabled">删除 </button>
                		 	 -->
                		 	<button id = "btn_save" type="button" class="btn btn-primary">保存 </button>
                        </div>
                	</div>
                	<div class="clearfix"></div>
                </div>
            </div>
      </div>
  </body>
</html>
