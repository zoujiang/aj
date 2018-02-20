<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>学校管理</title>		 
	</head>
	<body>
		<!-- begin of 学校列表 -->
		<div class="easyui-layout" fit="true" style = "min-height: 80%; border-radius: 5px 5px 5px 5px;border: 6px solid rgb(222, 222, 222);
width: 800;background: none repeat scroll 0% 0% rgb(255, 255, 255);">
<div id="content" region="center" id="right_area" 
			style="padding: 0px;margin : 2px 4px 4px 2px;border-radius: 5px 5px 5px 5px;border: 1px solid #ECECEC;" fit="false" border="true">
			
		<div id="schooltoolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="schoolAction.addSchool()">新增学校</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit"
				plain="true" onclick="schoolAction.updateSchool()">修改学校</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove"
				plain="true" onclick="schoolAction.deleteSchool()">删除</a>
		</div>
		<table id="schoolDatagrid">
		</table>
		<!-- end of 学校列表 -->

		<!-- begin of 学校编辑 -->
		<div id="schoolDialog" class="easyui-dialog"
			data-options="modal:true,closable:true,maximizable:false"
			style="width: 600px; height: 240px; padding: 20px 10px 0px;"
			title="学校" buttons="#school-dlg-buttons" closed="true">
			<form id="schoolForm" method="post" modelAttribute="school">
				<input type="hidden" name="schoolId" />
				<table style="font-size: 14px;">
					<tr>
						<td>
							学校名称：
						</td>
						<td>
							<input type="text" name="schoolName" style="width: 200px;"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="school-dlg-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="schoolAction.schoolSaveOrUpdate()">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
				onclick="javascript:$('#schoolDialog').dialog('close')">关闭</a>
		</div>
		<!-- end of 学校编辑 -->
		<!--begin of script -->
		<script type="text/javascript" charset="GBK">
	//初始化
	$(function(){
		$.ajaxSetup({cache:false});
		//初始化 --> 解决Dialog的Debug
		var schoolDialogDebug = $("#pageContent #schoolDialog");
		for(var i =1;i< schoolDialogDebug.length;i++){
			$(schoolDialogDebug[i]).dialog("destroy");
		}
		var configRoleDialogDebug = $("#pageContent #configRoleDialog");
		for(var i = 1;i < configRoleDialogDebug.length;i++){
			$(configRoleDialogDebug[i]).dialog("destroy");
		}
	
		//初始化 --> 学校 --> 定义学校列表列
		$('#schoolDatagrid').datagrid({
			remoteSort:false,
			toolbar:"#schooltoolbar",
			fit:true,
			border:false,
			idField:"id",
			rownumbers:true,
			fitColumns:true,
			singleSelect:true,
			url:"<%=mainWeb%>/admin/school/getSchools",
			columns:[[
				{field:'schoolName',title:'学校名称',width:100,align:'center',sortable:true}
			]]
		});
		
	});
	
	//学校 --> 增，删，改
	var schoolAction;
	$(function(){
		//学校 --> 初始化schoolAction类
		schoolAction = new SchoolAction();
				
		//学校 --> 全局变量 --> URL
		var url = "";
				
		//学校 --> 定义学校类(SchoolAction)
		function SchoolAction(){
			//学校 --> 新增学校 --> 弹出对话框
			this.addSchool = function(){
				$('#schoolForm').form('clear');  
				$('#schoolDialog').dialog('open').dialog('setTitle','新增学校');
			}
			
			//学校 --> 修改学校 --> 弹出对话框
			this.updateSchool = function(){
				var row = $('#schoolDatagrid').datagrid('getSelected');   
				if (row){
					$.get("<%=mainWeb%>/admin/school/getSchoolById",{'schoolId':row.schoolId},function(data){
						//填充form数据			
						$("#schoolForm input[name='schoolId']").val(data.schoolId);			
						$('#schoolDialog').dialog('open').dialog('setTitle','修改学校');
						$("#schoolForm input[name='schoolName']").val(data.schoolName);
					   	url = '<%=mainWeb%>/admin/school/updateSchool';
					});					
				}else{
					$.messager.alert('提示','请选择要修改的学校!','warning');
				} 		
			}
			
			//学校 --> 删除学校
			this.deleteSchool = function(){
				var row = $('#schoolDatagrid').datagrid('getSelected');   
				if (row){
				    $.messager.confirm('删除','是否确定删除?',function(r){   
			            if (r){   
			                $.post('<%=mainWeb%>/admin/school/deleteSchool',{schoolId:row.schoolId},function(result){
			                	$('#schoolDatagrid').datagrid('reload');  //刷新学校列表信息   
			                },'json');		                  
			            }   
				    });
				}else{
					$.messager.alert('提示','请选择要删除的学校!','warning');
				}    
			}
			
		//用户 --> 保存用户
		this.schoolSaveOrUpdate = function(){
				$('#schoolForm').form('submit',{
					url: '<%=mainWeb%>/admin/school/saveSchool',
				    onSubmit:function(){		    	
				    	return validateSchoolForm();   
				    },   
				    success:function(data){
				    	data=$.trim(data.replace(/<\/?.+?>/g,""));
				        if(data=='SaveFailse'){
				    	$.messager.alert('提示', '保存失败。');
					    }
					    if(data=='Failse'){
					    	$.messager.alert('提示', '学校已经存在');
					    }
					    if(data=='Success'){
					    	$.messager.alert('提示','学校保存成功!','info');//保存成功给出提示信息
					    	$("#schoolDialog").dialog('close');	//关闭用户对话框
					    	$('#schoolDatagrid').datagrid('reload');  //刷新用户列表信息
					    }
				    	
				    }   
				}); 
			}
			
			//学校 --> 保存 --> 验证 --> 验证学校表单数据的一致性
			function validateSchoolForm(){
				if($.trim($("#schoolForm input[name='schoolName']").val()) == ''){
					$.messager.alert('提示','学校名称未填写!','warning'); //手机号码未填写
					return false;					
				}else{			
					return true;
				}
			} 
			//公共方法 --> 获取选中的Ids
			function getSelectedIDS(targetDatagrid){
				var rows = $(targetDatagrid).datagrid('getSelections');
				var ids='';
				if(rows){
					for(var i = 0;i<rows.length;i++){
						ids += rows[i].id;
						if(i != rows.length-1){
							ids += ",";
						}
					}
				}
				return ids;
			}
		}
		
	});
</script>
	</body>
</html>