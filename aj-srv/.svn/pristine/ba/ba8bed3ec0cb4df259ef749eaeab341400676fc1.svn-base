<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>资源管理</title>
		 
	</head>
	<body>
		<div class="easyui-layout" fit="true" border="false">
			<!-- begin of 增，删，改 -->
			<div region="north"
				style="height: 28px; padding-top: 1px; background-color: #EEF;"
				border="false">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add"
					plain="true" onclick="resourceAction.addResource()">新增资源</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit"
					plain="true" onclick="resourceAction.updateResource()">修改资源</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove"
					plain="true" onclick="resourceAction.deleteResource()">删除资源</a>
			</div>
			<!-- end of 增，删，改 -->

			<!-- begin of 资源树 -->
			<div region="west" split="true" title="资源树" style="width: 200px;">
				<ul id="resourceTree" class="easyui-tree" lines="true"
					url="<%=mainWeb%>/admin/resource/getResources">
				</ul>
			</div>
			<!-- end of 资源树 -->

			<!-- begin of 资源管理 -->
			<div region="center" title="资源管理" style="padding: 5px;">
				<!-- begin of 资源编辑 -->
				<form id="resourceForm" method="post" modelAttribute="resource">
					<input type="hidden" name="id" />
					<input type="hidden" name="parentId" />
					<table>
						<tr>
							<td>
								资源编码：
							</td>
							<td>
								<input type="text" name="code" style="width: 200px;"
									class="easyui-validatebox" required="true" missingMessage="必填项" />
							</td>
						</tr>
						<tr>
							<td>
								资源名称：
							</td>
							<td>
								<input type="text" name="title" style="width: 200px;"
									class="easyui-validatebox" required="true" missingMessage="必填项" />
							</td>
						</tr>
						<tr>
							<td>
								资源路径：
							</td>
							<td>
								<input type="text" name="href" style="width: 200px;" />
							</td>
						</tr>
						<tr>
							<td>
							</td>
							<td style="text-align: right;">
								<a id="resourceLinkbutton" href="#" class="easyui-linkbutton"
									iconCls="icon-ok"  disabled="true""
									onclick="resourceAction.resourceSaveOrUpdate()">保 存</a>
							</td>
						</tr>
					</table>
				</form>
				<!-- begin of 资源编辑 -->
			</div>
			<!-- end of 资源管理 -->
		</div>

		<script type="text/javascript">
   //资源的查看
	$(function(){
		//初始化 --> 禁用表单
		$("#resourceForm input").attr("disabled","disabled").css({ "color":"#ccc"});
		
		//单击节点 --> 填充Form信息
		function fillForm(node){
			if("root" != node.id){					
				$("#resourceForm input[name='id']").val(node.id);
				$("#resourceForm input[name='parentId']").val(node.attributes.parentId);
				$("#resourceForm input[name='code']").val(node.attributes.code);
				$("#resourceForm input[name='title']").val(node.text);
				$("#resourceForm input[name='href']").val(node.attributes.href);
				$("#resourceForm input").attr("disabled","disabled").css({ "color":"#ccc"});
			}else{
				$("#resourceForm input").val("");
			}
			//禁用【保存】按钮
			$("#resourceLinkbutton").linkbutton('disable');
		}
		
		//监听事件 --> 单击节点 --> 初始化时为节点添加单击事件
		$('#resourceTree').tree({
			onClick: function(node){
				//判断是否是根节点
				node.id = typeof(node.id) == "undefined" ? '' : node.id; 
				//填充Form信息
				fillForm(node);
			}
		});
		
		//监听事件 --> 展开节点 --> 为节点添加展开监听事件
		$('#resourceTree').tree({
			onExpand: function(node){
				//选中展开的节点
				$('#resourceTree').tree('select', node.target);
				//填充form信息
				fillForm(node);
			}
		});
		
		//监听事件 --> 收缩节点 --> 为节点添加收缩监听事件
		$('#resourceTree').tree({
			onCollapse: function(node){
				//选中展开的节点
				$('#resourceTree').tree('select', node.target);
				//填充form信息
				fillForm(node);	
			}
		});
	});
	
	//资源的增，删，改
	var resourceAction;
	$(function(){
		//定义ResourceAction类
		function ResourceAction(){
			//定义全局变量
			var node = null;
			var action = null;
			var operate = true;
			
			//资源树 --> 新增资源
			this.addResource=function(){
			   if(!operate) return;
			   action = 'add';
			   node = $('#resourceTree').tree('getSelected');
			   
			   $('#resourceForm').form('clear');
			   $("#resourceForm input").removeAttr("disabled").css({ "color":"#000"});
			   //设置新增资源的父节点
			   if(node != null && node.id != 'undefined' && node.id != 'root'){
			   		$("#resourceForm input[name='parentId']").val(node.id);
			   }
			   //启用【保存】按钮
			   $("#resourceLinkbutton").linkbutton('enable');	   
			}
			
			//资源树 --> 修改资源
			this.updateResource=function(){
				if(!operate) return;
			    action = 'update';
				node = $('#resourceTree').tree('getSelected');
				
				if(node == null|| node.id == 'undefined' || node.id == 'root'){
					$.messager.alert('提示','请选择要修改的资源!','info');
					return;
				}
				$("#resourceForm input").removeAttr("disabled").css({ "color":"#000"}); 
				//启用【保存】按钮
			   $("#resourceLinkbutton").linkbutton('enable');
			}
					
			//资源 --> 保存
			this.resourceSaveOrUpdate= function(){
				if(!operate) return;
				//文本框不可用时，阻止保存操作的执行
				if(($("#resourceForm input").attr("disabled") == "disabled")){
					return;
				}
				 
				//提交表单数据
				$('#resourceForm').form('submit',{
					url:'<%=mainWeb%>/admin/resource/saveResource',
					dataType: 'json',
				    onSubmit:function(){
				    	var flag = $(this).form('validate');
				    	operate = !flag;
				    	return  flag;
				    },   
				    success:function(data){
				    	$.messager.alert('提示','资源保存成功!','info');
				    	//保存后处理节点
				    	handleNode(data);
				    	operate = true;
				    } 
				}); 
				//禁用表单
				$("#resourceForm input").attr("disabled","disabled").css({ "color":"#ccc"});
				//禁用【保存】按钮
			   	$("#resourceLinkbutton").linkbutton('disable');
			}
			 
			//资源 --> 保存 --> 保存后处理节点
			function handleNode(data){
				data = eval('('+data+')');
		    	if(action=='add'){
		    		//新增时追加一个节点
		    		if(node == null || !node.id || node.id == 'root'){
		    			$('#resourceTree').tree('append', {
		    				parent: $('#resourceTree').tree("getRoot").target,
							data: data
						});
		    		}else{
		    			$('#resourceTree').tree('append', {
							parent: node.target,
							data: data
						});
		    		}
						
		    	}else{
		    		//修改时，修改资源的文本，编码，URL
		    		if (node){
		    			$('#resourceTree').tree('update', {
							target: node.target,
							text: $("#resourceForm input[name='title']").val(),
							attributes:{  
		            			href:$("#resourceForm input[name='href']").val(),
		            			code:$("#resourceForm input[name='code']").val()   
		       				}  
						});	
					}
		    	}
			}
			
			//资源 --> 删除资源
			this.deleteResource=function(){
				var node = $('#resourceTree').tree('getSelected');
				if(node == null||!node.id|| node.id=='root'){
					$.messager.alert('提示','请选择要删除的资源!','info');
				}else{
					$.messager.confirm('删除','是否确定删除？',function(r){   
				        if(r){
				        	$.ajax({
							  url: "<%=mainWeb%>/admin/resource/deleteResource",
							  type: "POST", 
				  			  data: {id:node.id},
							  complete: function(data, textStatus, jqXHR) {
							  	//在资源树中移除节点
							  	$('#resourceTree').tree('remove',node.target);
							  	//清空form中的值
							  	$('#resourceForm').form('clear');
							  }
							},"JSON");
				        }
				     })
				   }
				}
		}
		//初始化resourceAction
		resourceAction = new ResourceAction();
	});
		
	</script>
	</body>
</html>