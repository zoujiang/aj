<%@ page contentType="text/html; charset=utf-8" language="java" %>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统目录</title>
<link type="text/css" rel="stylesheet" href="./javascripts/gzq/xtree/css/xtree2.css">
<link rel="StyleSheet" href="./styles/gstyle/dtree.css" type="text/css" />
<script language="javascript" src="./javascripts/gzq/xtree/js/xtree2.js"></script>


<SCRIPT LANGUAGE="JavaScript">
<!--
     //初始化FXTree
     var t = new WebFXTree('用户中心系统接口测试',changeRootUrls);

	 //改变右框架的URL；
     function changeUrl(url){
        parent.mainFrame.window.location=url;
     }  

	 function changeUrls(){

 
		 //改变右边frame的内容;
		
         //动态加载子节点;
     

         if(t.getSelected().hasChildren()){
			 t.getSelected().expand();
			 return;
		 }
		  parent.mainFrame.window.location=t.getSelected().getId();
		 
	 }

	 function changeRootUrls(){
         //加载子节点;
		 //改变右边frame的内容;
		
	 }

	 //控制一个节点
	 function operatorChild(id,text,flag){

		 if(flag=="1"){
			 //新增节点;
			 var node =new WebFXTreeItem(text,changeUrls);
			 node.setId(id);
		     t.getSelected().add(node);
			 if(t.getSelected().getExpanded()==false){
				 t.getSelected().expand();
			 }
		 }else if(flag=="2"){
			 //修改节点
			 var elements =t.getSelected().getChildNodes();
			 for(var i=0;i<elements.length;i++){
				 if(elements[i].getId()==id){
                     elements[i].setText(text);
					 break;
				 }

			 }
             
		 }else if(flag=="3"){
			 var objs =id.split(",");
			 var elements =t.getSelected().getChildNodes();
			 //删除节点;
			 for(var i=0 ;i<objs.length;i++){
                 for(var j =0;j<elements.length;j++){
                     if(elements[j].getId()==objs[i]){
						 t.getSelected().remove(elements[j]);
						 break;
					 }
				 }

			 }
		 }
	 }
	 function logOut(){
		parent.window.location ="logout.jsp";
	 }
	 
//-->
</SCRIPT>
</head>

<body>


<div class="dtree">
	<script type="text/javascript">
		   var node;
		   var  publish =new WebFXTreeItem('接口发布',changeUrls);
		   publish.setId('0');
		   t.add(publish);
		   var  sys =new WebFXTreeItem('系统通用',changeUrls);
		   sys.setId('1');
		   publish.add(sys);
		   node =new WebFXTreeItem('客户端版本更新接口',changeUrls);
		   node.setId('publish/testcase.jsp?act=clt_update&name=客户端版本更新接口');
		   sys.add(node);
		   node =new WebFXTreeItem('图片/资源上传',changeUrls);
		   node.setId('publish/testUpPicRes.jsp?act=pic&name=区域列表');
		   sys.add(node);
		   <%-- 我的 --%>
		   var  my =new WebFXTreeItem('我的',changeUrls);
		   my.setId('2');
		   publish.add(my);
		   node =new WebFXTreeItem('申请用户注册短信验证码',changeUrls);
		   node.setId('publish/testcase.jsp?act=user_reg_sms_apply&name=用户注册短信验证码申请');
		   my.add(node);
		   node =new WebFXTreeItem('验证用户注册短信验证码',changeUrls);
		   node.setId('publish/testcase.jsp?act=user_reg_sms_validate&name=用户注册短信验证码验证');
		   my.add(node);
		   <%--
		   node =new WebFXTreeItem('用户快速注册',changeUrls);
		   node.setId('publish/testcase.jsp?act=user_fast_reg&name=用户快速注册');
		   publish.add(node); --%>
		   node =new WebFXTreeItem('修改用户资料',changeUrls);
		   node.setId('publish/testcase.jsp?act=user_mgr&name=用户修改资料');
		   my.add(node);
		   node =new WebFXTreeItem('用户登陆',changeUrls);
		   node.setId('publish/testcase.jsp?act=user_login&name=用户登陆');
		   my.add(node);
		      node =new WebFXTreeItem('查询用户资料',changeUrls);
		   node.setId('publish/testcase.jsp?act=user_info&name=用户资料');
		   my.add(node);
		   node =new WebFXTreeItem('修改密码',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_update_pwd&name=修改密码');
		   my.add(node);
		   node =new WebFXTreeItem('忘记密码-设置密码',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_forget_pwd&name=忘记密码');
		   my.add(node);
		   node =new WebFXTreeItem('用户退出登陆',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_user_logout&name=用户退出登陆');
		   my.add(node);
		   node =new WebFXTreeItem('意见反馈',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_feedback&name=意见反馈');
		   my.add(node);
		   <%--
		   node =new WebFXTreeItem('用户收货地址列表',changeUrls);
		   node.setId('publish/testcase.jsp?act=deli_addr_list&name=用户收货地址列表');
		   publish.add(node);
		   
		   node =new WebFXTreeItem('收货地址详情',changeUrls);
		   node.setId('publish/testcase.jsp?act=deli_addr_dtl&name=收货地址详情');
		   publish.add(node);
		   
		   node =new WebFXTreeItem('用户管理收获地址',changeUrls);
		   node.setId('publish/testcase.jsp?act=deli_addr_mgr&name=用户管理收获地址');
		   publish.add(node);
		   
		   
		     node =new WebFXTreeItem('区域列表(测试)',changeUrls);
		   node.setId('publish/testcase.jsp?act=area_list&name=区域列表');
		   publish.add(node);  --%>
		   
		   <%-- 我的家人 --%>
		   var  jr =new WebFXTreeItem('我的家人',changeUrls);
		   jr.setId('3');
		   publish.add(jr);
		   node =new WebFXTreeItem('我的家人',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_my_family_list&name=我的家人');
		   jr.add(node);
		   node =new WebFXTreeItem('我的更多家人',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_more_family_list&name=我的更多家人');
		   jr.add(node);
		   node =new WebFXTreeItem('根据家庭ID查看家人',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_user_by_family_list&name=根据家庭ID查看家人');
		   jr.add(node);
		   node =new WebFXTreeItem('根据家庭ID查看空间',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_zone_by_family_list&name=根据家庭ID查看空间');
		   jr.add(node);
		   node =new WebFXTreeItem('空间管理（用9.2）',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_album_mgr&name=相册管理');
		   jr.add(node);
		   node =new WebFXTreeItem('查看他人空间照片',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_zone_photo_list&name=查看他人空间照片');
		   jr.add(node);
		   node =new WebFXTreeItem('查看照片所有评论',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_allcmt_list&name=查看照片所有评论');
		   jr.add(node);
		   node =new WebFXTreeItem('评论管理接口',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_cmt_mgr&name=评论管理接口');
		   jr.add(node);
		   node =new WebFXTreeItem('照片点赞或取消赞',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_photo_dig&name=照片点赞或取消赞');
		   jr.add(node);
		   node =new WebFXTreeItem('照片举报',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_photo_report&name=照片举报');
		   jr.add(node);
		   node =new WebFXTreeItem('查看我的空间照片',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_my_zone_photo_list&name=查看我的空间照片');
		   jr.add(node);
		   
		   <%-- 我的家庭 --%>
		   var  jt =new WebFXTreeItem('我的家庭',changeUrls);
		   jt.setId('4');
		   publish.add(jt);
		   node =new WebFXTreeItem('相册查询',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_album_list&name=相册查询');
		   jt.add(node);
		   node =new WebFXTreeItem('相册管理',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_album_mgr&name=相册管理');
		   jt.add(node);
		   node =new WebFXTreeItem('照片管理',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_album_photo_mgr&name=照片管理');
		   jt.add(node);
		   node =new WebFXTreeItem('相册照片添加',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_album_photo_add&name=相册照片添加');
		   jt.add(node);
		   node =new WebFXTreeItem('家庭关系',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_family_relation_list&name=家庭关系');
		   jt.add(node);
		   node =new WebFXTreeItem('按爱家手机号添加家庭成员',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_family_byId_add&name=按爱家手机号添加家庭成员');
		   jt.add(node);
		   node =new WebFXTreeItem('确认添加家庭关系',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_confirm_relation_service&name=确认添加家庭关系');
		   jt.add(node);
		   node =new WebFXTreeItem('查看家庭成员',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_family_query&name=查看家庭成员');
		   jt.add(node);
		   node =new WebFXTreeItem('家庭成员管理',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_family_mgr&name=家庭成员管理');
		   jt.add(node);
		   node =new WebFXTreeItem('胎儿管理',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_baby_mgr&name=胎儿管理');
		   jt.add(node);
		   node =new WebFXTreeItem('查询胎儿',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_baby_query&name=查询胎儿');
		   jt.add(node);
		   node =new WebFXTreeItem('孩子管理',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_children_mgr&name=孩子管理');
		   jt.add(node);
		   node =new WebFXTreeItem('查询孩子',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_children_query&name=查询孩子');
		   jt.add(node);
		   node =new WebFXTreeItem('宠物管理',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_pet_mgr&name=宠物管理');
		   jt.add(node);
		   node =new WebFXTreeItem('查询宠物',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_pet_query&name=查询宠物');
		   jt.add(node);
		   node =new WebFXTreeItem('爱的传承：新增祈愿信',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_pray_letter_add&name=爱的传承：新增祈愿信');
		   jt.add(node);
		   node =new WebFXTreeItem('爱的传承：查询祈愿信列表',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_pray_letter_list&name=爱的传承：查询祈愿信列表');
		   jt.add(node);
		   node =new WebFXTreeItem('爱的传承：传承',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_pray_do&name=爱的传承：传承');
		   jt.add(node);
		   node =new WebFXTreeItem('保存结婚日期',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_marrieddate_save&name=保存结婚日期');
		   jt.add(node);
		   node =new WebFXTreeItem('查询结婚日期',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_marrieddate_query&name=查询结婚日期');
		   jt.add(node);
		   node =new WebFXTreeItem('新增我的收藏',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_collection_add&name=新增我的收藏');
		   jt.add(node);
		   node =new WebFXTreeItem('查询我的收藏列表',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_collections_query&name=查询我的收藏列表');
		   jt.add(node);
		   node =new WebFXTreeItem('删除我的收藏',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_collection_delete&name=删除我的收藏');
		   jt.add(node);
		   node =new WebFXTreeItem('爱的传承：相册',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_baby_photos_query&name=爱的传承：相册');
		   jt.add(node);
		   node =new WebFXTreeItem('爱的传承：相册管理',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_baby_photos_mgr&name=爱的传承：相册管理');
		   jt.add(node);
		   
		   node =new WebFXTreeItem('广告列表获取接口',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_ad_list&name=广告列表获取接口');
		   jt.add(node);
		   node =new WebFXTreeItem('广告浏览统计接口',changeUrls);
		   node.setId('publish/testcase.jsp?act=aj_ad_syn&name=广告浏览统计接口');
		   jt.add(node);
		   
		   
		t.write();
		 //选中根节点;
		t.getTree().select();
		t.getSelected().expand();
	</script>

</div>

</body>

</html>