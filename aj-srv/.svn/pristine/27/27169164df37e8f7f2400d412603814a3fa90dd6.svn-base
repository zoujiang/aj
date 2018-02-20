<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/pages/sys/layout/util/jsp/tag.jsp"%>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<form  id="passForm" action="${ctx }/userctrl/updatePass.do" method="post">
			<table class="tb_n">
				<tr>
					<td class="td_l">原密码：</td>
					<td class="td_r"><input type="password" name="oldPwd" id="oldPwd" class="easyui-validatebox" data-options="required:true,validType:['account','length[1,20]']"/></td>
				</tr>
				<tr>
					<td class="td_l">新密码：</td>
					<td class="td_r"><input type="password" name="newPwd" id="newPwd" class="easyui-validatebox" data-options="required:true,validType:['account','length[1,20]']"/></td>
				</tr>
				<tr>
					<td class="td_l">确认密码：</td>
					<td  class="td_r"><input type="password" name="checkPwd" id="checkPwd" class="easyui-validatebox" data-options="required:true,validType:['equalTo[\'#newPwd\']'],invalidMessage:'两次输入密码不一致。'"/></td>
				</tr>
			</table>
		</form>
	</div>
</div>
