<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/pages/sys/layout/util/jsp/tag.jsp"%>
<style>
	input[readonly]{
		border: none;
	}
</style>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<form  id="personalForm" action="${ctx }/userctrl/updateUser.do" method="post">
			<input type="hidden" name="id"/>
			<input type="hidden" name="oper"/>
			<table class="tb_n">
				<tr>
					<td class="td_l">姓名：</td>
					<td class="td_r"><input type="text" name="name" class="easyui-validatebox" data-options="required:true,validType:['username','length[1,100]']"/></td>
					<td class="td_l">帐号：</td>
					<td class="td_r"><input type="text" name="account" class="easyui-validatebox" data-options="required:true,validType:['account','length[1,20]']"/></td>
				</tr>
				<tr>
					<td class="td_l">邮箱：</td>
					<td class="td_r"><input type="text" name="email" class="easyui-validatebox" data-options="validType:['email','length[1,100]']"/></td>
					<td class="td_l">手机：</td>
					<td class="td_r"><input type="text" name="mobile" class="easyui-validatebox" data-options="validType:['mobile']"/></td>
				</tr>
				<tr>
					<td class="td_l">地址：</td>
					<td colspan="3" class="td_r_l"><input type="text" name="address" class="easyui-validatebox" data-options="validType:['length[1,200]']"/></td>
				</tr>
				<tr>
					<td class="td_l" style="vertical-align: top;">备注：</td>
					<td colspan="3" class="td_r_l"><textarea name="remark" style="overflow-y: auto;"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</div>