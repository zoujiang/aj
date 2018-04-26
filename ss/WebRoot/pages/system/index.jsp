<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>幼教汇后台管理系统</title>

	<%@ include file="/pages/comm/jsp/inc.jsp"%>
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/layer/layer.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/plugins/pace/pace.min.js"></script>
	
	<script type="text/javascript">
		function initMenu(){
			var data = ajaxSubmit("${ctx}/admin/user/getTree",'');
			if(data == null || data == ''){
				return;
			}
			var str="";
			//alert(data);
			//alert(JSON.parse(data[]));
			//data = data.children;
			data = data[0].children;
    	   	$.each(data, function (n, value) {
    	   		str+="<li class="active">";
    		   	str+="<a";
    		   	if(value.children == null || value.children == ""){
    		   		str += " href=\"${ctx}"+value.attributes.href+"?id="+value.code+"\"";
    		   		str += " class = \"J_menuItem\""  
    		   	}
    		   str += ">"
    		   str +="<i class=\"fa ";
    		   if(value.iconCls != null && value.iconCls != ""){
    			   str += value.iconCls;
    		   }
    		   str +="\"></i><span class=\"nav-label\">"+value.text+"</span>";
    		   if(value.children != null && value.children !=""){
    			   str += "<span class=\"fa arrow\"></span></a>";
    			   str += "<ul class=\"nav nav-second-level\">";
    			   $.each(value.children, function(i, secval){
    				   str+="<li>";
    				   str+="<a";
    				   if(secval.children ==null || secval.children == ""){
    					   str += " class=\"J_menuItem\" href=\"${ctx}"+secval.attributes.href+"\"";
    				   }
    				   str +=">"+secval.text;
    				   str += "</a>";
    				   str+="</li>";
    			   });
    			   str += "</ul>";
    		   }
    		   str += "<li>";
    	   });
    	   	
    	   $("#side-menu").append(str);
		}
		function logOut(){
			location.href="${ctx }/admin/user/logout";
		}
		
		$(function(){
			initMenu();
		});
	</script>
	<link href="${ctx }/pages/system/welcome/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx }/resources/hplus4.1/css/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
	<link href="${ctx }/resources/hplus4.1/css/style_002.css" rel="stylesheet">
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper" >
		 <nav class="navbar-default navbar-static-side" role="navigation" >
            <div class="nav-close"><i class="fa fa-times-circle"></i></div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu" style="background: #d6f1fb;">
                    <li class="nav-header" style="background: #e2f7ff">
                        <div class="dropdown profile-element">
                            <span ><img alt="image" class="img-circle" width="80px" height="80px" src="${ctx }/pages/system/img/cqmls.png" /></span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               		<span class="block m-t-xs" >
                               			<strong class="font-bold" >${Login_User_Info.name }</strong>
                               		</span>
                                </span>
                            </a>                            
                        </div>
                        <div class="logo-element" >${Login_User_Info.name } 
                        </div>
                    </li>
                </ul>
            </div>
        </nav>    
                
            <%-- 
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                            <span style="text-align: center;"><img alt="我的头像" class="img-circle"  width="60" height="60" src="${ctx }/admin/shop/getShopHeadPic" /></span>
                               		<span class="block m-t-xs">
                               			<strong class="font-bold">${user.name }</strong>
                               		</span>
                    </li>
                       <span  style="text-align: center;"><strong class="font-bold">休息休息${user.name }</strong>
                        </span>
                </ul> --%>
                
           
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header">
                    	<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                       <!--  <li class="dropdown hidden-xs">
                            <a class="right-sidebar-toggle" aria-expanded="false">
                                <i class="fa fa-tasks"></i> 主题
                                
                            </a>
                        </li> -->
                        <li class=" dropdown hidden-xs">
                           <a  class="J_menuItem"   href="javascript:void(0);" onclick="javascript:logOut();" >
                           		<i class="fa fa-cart-arrow-down"></i> 退出登录
                           	</a>
	                     </li>
                    </ul>
                </nav>
            </div>
           <!-- <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                    	<a href="javascript:;" class="active J_menuTab" data-id="index_v1.html">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                 <div class="btn-group roll-nav roll-right">                   
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>                        
                    </ul>
                </div> 
               
            </div>-->
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx }/pages/system/welcome/index.jsp"  frameborder="0" data-id="index_v1.html" seamless></iframe>
            </div>
            
            <div class="footer">
                <div class="pull-right">&copy; 2018 <a href="#" >幼教汇</a>
                </div>
            </div>
             
        </div>
        <!--右侧部分结束-->
        <!--右侧边栏开始-->
        <div id="right-sidebar">
            <div class="sidebar-container">
                <ul class="nav nav-tabs navs-3">
                    <li class="active">
                        <a data-toggle="tab" href="#tab-1">
                            <i class="fa fa-gear"></i> 主题
                        </a>
                    </li>
                   
                </ul>

                <div class="tab-content">
                    <div id="tab-1" class="tab-pane active">
                        <div class="sidebar-title">
                            <h3> <i class="fa fa-comments-o"></i> 主题设置</h3>
                            <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
                        </div>
                        <div class="skin-setttings">
                            <div class="title">主题设置</div>
                            <div class="setings-item">
                                <span>收起左侧菜单</span>
                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
                                        <label class="onoffswitch-label" for="collapsemenu">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="setings-item">
                                <span>固定顶部</span>
                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar">
                                        <label class="onoffswitch-label" for="fixednavbar">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="setings-item">
                                <span>固定宽度</span>
                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout">
                                        <label class="onoffswitch-label" for="boxedlayout">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="title">皮肤选择</div>
                            <div class="setings-item default-skin nb">
                                <span class="skin-name ">
                         			<a href="#" class="s-skin-0">默认皮肤</a>
                    			</span>
                            </div>
                            <div class="setings-item blue-skin nb">
                                <span class="skin-name ">
                        			<a href="#" class="s-skin-1">蓝色主题</a>
                    			</span>
                            </div>
                            <div class="setings-item yellow-skin nb">
                                <span class="skin-name ">
                        			<a href="#" class="s-skin-3">黄色/紫色主题</a>
                    			</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--右侧边栏结束-->
    </div>
    <script type="text/javascript" src="${ctx }/resources/hplus4.1/js/hplus.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/hplus4.1/js/contabs.min.js"></script>
</body>
</html>
