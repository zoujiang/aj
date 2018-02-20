<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>个人状态查询</title>
    <link rel="stylesheet" href="css/wy-cx.css"/>
    <link rel="stylesheet" href="<%=basePath %>resources/layer/2.1/skin/layer.css"/>
    <link rel="stylesheet" href="<%=basePath %>resources/layer/2.1/skin/layer.ext.css"/>
	<script type="text/javascript" src="<%=basePath %>resources/hplus4.1/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>resources/layer/2.1/layer.js"></script>
	<script type="text/javascript">
		$(function(){
			$(".wy-submit").click(function(){
				if($("input[name='certNo']").val() == ""){
					layer.msg("请输入证件号码", {title:'提示', btn: ['确定'],icon: 6}, function(index){});
					return;
				}
				if($("input[name='mobile']").val() == ""){
					layer.msg("请输入手机号码", {title:'提示', btn: ['确定'],icon: 6}, function(index){});
					return;
				}
				$.ajax({
		   	        type: "POST",
		   	        url:"<%=basePath %>service/web/exist",
		   	        dataType: "json",
		   	        data:{
		   	        	certNo:$("input[name='certNo']").val(),
		   	        	mobile:$("input[name='mobile']").val()
		   	        },
		   	        success: function(data){
		   	        	if(data == null || data == ''){
		   					return;
		   				} 
		   	        	if(data.success){
		   	        		//layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){});
		   	        		location.href = "<%=basePath %>service/web/toInfoPage?groupId="+data.groupId+"&certNo="+$("input[name='certNo']").val()+"&mobile="+$("input[name='mobile']").val();
		   	        	} else {
		   	        		//layer.msg(data.message, {title:'提示', btn: ['确定'],icon: 6}, function(index){});
		   	        		location.href = "<%=basePath %>pages/spt/web/noexist.jsp";
		   	        	}
		   	        }
		   		});	
			});
		});
	</script>
</head>
<body>
<!-- 页头 -->
<header id="head">
    <div class="container">
        <div class="logo">
            <img src="images/wy-mls01.png" alt=""/>
        </div>
    </div>
</header>

<section id="main">
    <div class="wy-main">
        <div class="wy-main-dh">
            <a href=""><img src="images/wy-mls02.gif" alt=""/></a><!--logo/-->
        </div>
        <div class="wy-main-dh02">
            <ul>
                <li><a href="">首页</a></li>
                <li><a href="">赛事信息</a></li>
                <li><a href="">新闻公告</a></li>
                <li><a href="">合作伙伴</a></li>
                <li><a href="">关于我们</a></li>
            </ul>
        </div>
    </div>
    <div class="wy-main02">
        <p>您的位置:&nbsp;&nbsp;<a href="">首页></a><a href="">报名查询</a></p>
    </div>
    <div class="wy-main03">
        <div class="wy-fonts">个人状态查询</div>

        <!-- 信息部分  改变 -->
        <div class="wy-input">
            <span>证件号码</span>
            <input type="text" name="certNo" class="">

            <span style="margin-top:10px;">手机号码</span>
            <input type="text" name="mobile" style="margin-top:10px;">
        </div>
        <p style="margin-top:10px;">&nbsp;</p>
        <a  class="wy-submit">提交查询</a>
    </div>
</section>
<!-- 主办单位介绍 -->
<footer id="footer">
    <div class="wy-foot">

    </div>
    <div class="wy-foot-js">
        <p>主办单位：重庆市体育局 重庆日报报业集团 重庆南岸区人民政府 乐视体育文化发展（北京）有限公司 </p>
        <p>承办单位：重庆乐视体育产业发展有限公司</p>
        <p>联合承办单位：咪咕互动娱乐有限公司</p>
    </div>
</footer>

</body>
</html>