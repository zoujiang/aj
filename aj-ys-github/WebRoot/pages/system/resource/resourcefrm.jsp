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


    <title>商户管理平台-资源管理</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

	<%@ include file="/pages/comm/jsp/inc.jsp"%>

	<script type="text/javascript">
		function getTree() {
			var data = ajaxSubmit("${ctx}/admin/resource/getRess",'');
			return data;
		}
		
		function checkNode(){
			var tree = $('#treeview11').jstree(true);
			tree.open_all();
			//var node = tree.get_node("00000000000000000000000000001000");
			tree.check_node(["00000000000000000000000000001001", "00000000000000000000000000001000"]);
		}
		function saveRes(){
			var url = getProjectName()+"/admin/resource/editRes";
    		var queryString = $('#resForm').formSerialize();
			var obj = ajaxSubmit(url,queryString);
			if(obj.success){
				layer.msg(obj.message, {time:2000,title:'提示', btn: ['确定'],icon: 6}, function(index){
					disabledForm(true);
					$('#treeview11').jstree(true).refresh();
		  		});
			}
			else{
				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
				});
			}
		}
		function disabledForm(flag){
			$("#title").attr("disabled", flag);
			$("#code").attr("disabled", flag);
			$("#href").attr("disabled", flag);
			$("#btn_save").attr("disabled", flag);
		}
		$(function(){
			$("#treeview11").jstree({
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
			    "plugins" : [/*"checkbox",*/ "contextmenu"],
			    "contextmenu":{
			    	"items":{
			    		"create":null,
			            "rename":null,
			            "remove":null,
			            "ccp":null,
			            "新建":{
			                "label":"新建",
			                "icon":"glyphicon glyphicon-plus",
			                "action":function(data){
			                    var inst = jQuery.jstree.reference(data.reference);
			                    var obj = inst.get_node(data.reference);
			                    //var node = data.node;
			                    if(obj.id == "root"){
			                    	$("#parentId").val("");
			                    } else {
			                    	$("#parentId").val(obj.id);
			                    }
			    				$("#opt").val("add");
			    				$("#id").val("");
			    				$("#title").val("");
			    				$("#code").val("");
			    				$("#href").val("");
			    				disabledForm(false);
			                }
			            },
			            "编辑":{
			                "label":"编辑",
			                "icon":"glyphicon glyphicon-edit",
			                "action":function(data){
			                    var inst = jQuery.jstree.reference(data.reference),
			                    obj = inst.get_node(data.reference);
			                    if(obj.id == "root"){
			                    	return;
			                    }
			                    if(obj.parent == "root"){
			                    	$("#parentId").val("");
			                    } else {
			                    	$("#parentId").val(obj.parent);
			                    }
			    				$("#opt").val("edit");
			                    $("#id").val(obj.id);
			    				$("#title").val(obj.text);
			    				$("#code").val(obj.a_attr.code);
			    				$("#href").val(obj.a_attr.href);

			    				disabledForm(false);
			                }
			            },
			            "删除":{
			                "label":"删除",
			                "icon":"glyphicon glyphicon-minus",
			                "action":function(data){
			                    var inst = jQuery.jstree.reference(data.reference),
			                    node = inst.get_node(data.reference);
			                    var url =  getProjectName() + "/admin/resource/desRes";
			                    var obj = ajaxSubmit(url,{id:node.id});
			        			if(obj.success){
			        				layer.msg(obj.message, {time:2000,title:'提示', btn: ['确定'],icon: 6}, function(index){
			        					inst.refresh();
			        					$("#parentId").val("");
					    				$("#id").val("");
					    				$("#title").val("");
					    				$("#code").val("");
					    				$("#href").val("");
					    				disabledForm(true);
			    	   		  		});
			        			}
			        			else{
			        				layer.msg(obj.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){
			        		  		});
			        			}
			                }
			            }
			    	}
			    }
			}).on("activate_node.jstree", function (e, data) {
				$("#parentId").val(data.node.parent);
                $("#id").val(data.node.id);
				$("#title").val(data.node.text);
				$("#code").val(data.node.a_attr.code);
				$("#href").val(data.node.a_attr.href);
				disabledForm(true);
			}).on("loaded.jstree", function(e, data){
				/*var inst = data.instance;
				var obj = inst.get_node(e.target.firstChild.firstChild.lastChild);  
				inst.select_node(obj);*/ 
				//alert(obj.id);
				/*var tree = $('#treeview11').jstree(true);
				//var node = tree.get_node("00000000000000000000000000001000");
				tree.check_node(["00000000000000000000000000001001", "00000000000000000000000000001000"]);*/
			});
			// listen for event  
	       /* $('#treeview11').on('changed.jstree', function(e, data) {  
	            var r = [];  
	            var i, j;  
	            for (i = 0, j = data.selected.length; i < j; i++) {  
	                var node = data.instance.get_node(data.selected[i]);  
	                if (data.instance.is_leaf(node)) {  
	                    r.push(node.id);  
	                }  
	            }  
	        })  */
			/*$('#treeview11').bind("activate_node.jstree", function (e, data) {
				//alert(data.node.id);
			});*/
			/*$('#treeview11').bind("select_node.jstree", function (e, data) {
			    var nodes = $("#treeview11").jstree("get_checked",true);
			    var ids = [];
			    $.each(nodes, function(i, n){
			    	ids.push(n.id);
			    });
			    alert(ids.join(','));
			});*/
			$("#btn_save").click(function(){
				saveRes();
			});
			
		});
	</script>
  </head>
  
  <body class="gray-bg" >
    <div class="row wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins" style="margin-bottom: 0px;">
                <div class="ibox-content">
                 	<div class="col-sm-4" style="border: 1px solid #eee;">
	                 	<div id="treeview11" class="test"></div>
                	</div>
                	<div class="col-sm-8">
                	 	<form class="form-horizontal" id="resForm">
                	 		<input type="hidden" name = "id" id = "id">
                	 		<input type="hidden" name = "parentId" id = "parentId">
                	 		<input type="hidden" name = "opt" id = "opt">
                	 		<div class=""></div>
                			<div class="form-group"> 
                				<label class="col-sm-3 control-label">名称：</label> 
                				<div class="col-sm-8">
                					<input type="text" class="form-control" name="title" id="title" disabled="disabled">  
                				</div>
						    </div>
                			<div class="form-group"> 
                				<label class="col-sm-3 control-label">编码：</label>  
						        <div class="col-sm-8">
						        	<input type="text" class="form-control" name="code" id="code" disabled="disabled">
						        </div>
						    </div>
                			<div class="form-group"> 
                				<label class="col-sm-3 control-label">链接：</label>  
						        <div class="col-sm-8">
						        	<input type="text" class="form-control" name="href" id="href" disabled="disabled">
						        </div>
						    </div>
	                		<div class="clearfix"></div>
                		</form>
                		<div class="hr-line-dashed"></div>
                		<div class="text-center">
                		 	<!-- 
                		 	<button id = "btn_add" type="button" class="btn btn-primary" disabled="disabled">新增 </button>
                		 	<button id = "btn_edit" type="button" class="btn btn-primary" disabled="disabled">编辑 </button>
                		 	<button id = "btn_del" type="button" class="btn btn-primary" disabled="disabled">删除 </button>
                		 	 -->
                		 	<button id = "btn_save" type="button" class="btn btn-primary" disabled="disabled">保存 </button>
                        </div>
                	</div>
                	<div class="clearfix"></div>
                </div>
            </div>
      </div>
  </body>
</html>
