<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>用户中心测试用例</title>
		<style type="text/css">
			* {
				font-size: 12px;
				margin: 0;
				padding: 0;
			}
			
			fieldset {
				padding: 10px;
				margin: 10px;
				width: 370px;
				color: #333;
				border: #06c dashed 1px;
				background-color: #EEE;
			}
			
			legend {
				color: #06c;
				font-weight: 800;
				background: #fff;
			}
			
			ul {
				list-style-type: none;
				margin: 8px 0 4px 0;
				color: #880;
			}
			
			li {
				margin-top: 4px;
			}
		</style>
		<script type="text/javascript" src="<%=basePath%>/scripts/jquery/jquery-1.8.1.min.js"></script>
		<script type="text/javascript">
			var action = '<%=request.getParameter("act")%>'
			$(function(){
				var $form=$("#testForm");
				//$form.attr("action","<%=basePath%>service/userBindService");
				//alert($form.attr('action'));
				$form.submit(function(){
					return false;
				});
				
			//	$("#btn1").click(function(){
					if("clt_update"==action){
	                    $("#show_cltupdate").toggle();
	                }else if("area_list"==action){
						$("#show0").toggle();
					}else if("user_reg_sms_apply"==action){
						$("#show1").toggle();
					}else if("user_reg_sms_validate"==action){
						$("#show2").toggle();
					}else if("user_fast_reg"==action){
						$("#show3").toggle();
					}else if("user_mgr"==action){
						$("#show4").toggle();
					}else if("user_login"==action){
						$("#show5").toggle();
					}else if("user_info"==action){
						$("#show6").toggle();
					}else if("user_logout"==action){
						$("#show7").toggle();
					}else if("deli_addr_list"==action){
						$("#show8").toggle();
					}else if("deli_addr_dtl"==action){
						$("#show9").toggle();
					}else if("deli_addr_mgr"==action){
						$("#show10").toggle();
					}else if("aj_forget_pwd"==action){
						$("#show11").toggle();
					}
					else if("aj_user_logout"==action){
						$("#show12").toggle();
					}
					else if("aj_feedback"==action){
						$("#show13").toggle();
					}
					else if("aj_allcmt_list"==action){
						$("#show14").toggle();
					}
					else if("aj_cmt_mgr"==action){
						$("#show15").toggle();
					}
					else if("aj_photo_dig"==action){
						$("#show16").toggle();
					}
					else if("aj_photo_report"==action){
						$("#show17").toggle();
					}
					else if("aj_album_list"==action){
						$("#show18").toggle();
					}
					else if("aj_album_mgr"==action){
						$("#show19").toggle();
					}
					else if("aj_album_photo_mgr"==action){
						$("#show20").toggle();
					}
					else if("aj_album_photo_add"==action){
						$("#show21").toggle();
					}
					else if("aj_family_byId_add"==action){
						$("#show22").toggle();
					}
					else if("aj_family_query"==action){
						$("#show23").toggle();
					}
					else if("aj_family_mgr"==action){
						$("#show24").toggle();
					}
					else if("aj_baby_mgr"==action){
						$("#show25").toggle();
					}
					else if("aj_baby_query"==action){
						$("#show26").toggle();
					}
					else if("aj_children_mgr"==action){
						$("#show27").toggle();
					}
					else if("aj_children_query"==action){
						$("#show28").toggle();
					}
					else if("aj_pet_mgr"==action){
						$("#show29").toggle();
					}
					else if("aj_pet_query"==action){
						$("#show30").toggle();
					}
					else if("aj_pray_letter_add"==action){
						$("#show31").toggle();
					}
					else if("aj_marrieddate_save"==action){
						$("#show32").toggle();
					}
					else if("aj_marrieddate_query"==action){
						$("#show33").toggle();
					}
					else if("aj_collection_add"==action){
						$("#show34").toggle();
					}
					else if("aj_collections_query"==action){
						$("#show35").toggle();
					}
					else if("aj_collection_delete"==action){
						$("#show36").toggle();
					}
					else if("aj_baby_photos_query"==action){
						$("#show37").toggle();
					}
					else if("aj_baby_photos_mgr"==action){
						$("#show38").toggle();
					}
					else if("aj_update_pwd"==action){
						$("#show39").toggle();
					}
					else if("aj_zone_by_family_list"==action){
						$("#show40").toggle();
					}
					else if("aj_my_family_list"==action){
						$("#show41").toggle();
					}
					else if("aj_more_family_list"==action){
						$("#show42").toggle();
					}
					else if("aj_user_by_family_list"==action){
						$("#show43").toggle();
					}
					else if("aj_zone_by_family_list"==action){
						$("#show44").toggle();
					}
					else if("aj_zone_photo_list"==action){
						$("#show45").toggle();
					}
					else if("aj_my_zone_photo_list"==action){
						$("#show46").toggle();
					}
					else if("aj_family_relation_list"==action){
						$("#show47").toggle();
					}
					else if("aj_pray_do"==action){
						$("#show48").toggle();
					}
					else if("aj_pray_letter_list"==action){
						$("#show49").toggle();
					}
					else if("aj_confirm_relation_service"==action){
						$("#show50").toggle();
					}
					else if("aj_ad_list"==action){
						$("#ad_list").toggle();
					}
					else if("aj_ad_syn"==action){
						$("#ad_syn").toggle();
					}
					
					
				//	});
				
				
				
				
				
				$("#testForm > input[type='submit']").click(function(){
					if($("#testForm > textarea").val()==""){
						alert("不能为空哦~亲！");
						return false;
					}
					$("#textArea").val("");
					$.post(
					"<%=basePath%>service/proxy",
					$form.serialize(),
					function(data){
							$("#textArea").val(data);
						
					},"text");
				});
				
				$("#btn2").click(function(){
					$("ul").each(function(i,dom){
						//判断每一个div，其css中display是否为block
				         if($(this).css("display")=="block"){
								var text = $("#"+dom.id).text();
								$("#testForm > textarea").val(text.substring(text.indexOf('{')).trim());
				         }
					  });
				});
				
				
			});
		</script>
	</head>

	<%
		String flag = request.getParameter("flag");
		flag = flag == null ? "" : flag.trim();
	%>

	<body>
	<div style="float:left;clear: both;width: 500px;">
		<fieldset>
			<legend>
				<%=request.getParameter("name")%>
			</legend>
			<form id="testForm" name="testForm" action="#"
				method="post">
				请输入正确的JSON字符串：
				</br>
				<textarea rows="20" cols="70" name="jsonRequest"></textarea>
				</br>
				<input type="submit" value="提交">
				</br>
			</form>
		</fieldset>
	<fieldset >
			<legend>
				返回结果
			</legend>
				<textarea id="textArea" rows="9" cols="70"></textarea>
			</form>
		</fieldset>
	</div>
		<fieldset style="float:left;">
			<legend>
				注意的相关事项
			</legend>

			<ul>
				<li>
					（1）测试地址:http://ip:port/aam/service/proxy
				</li>
				<li>
					（2）JSON格式测试地址：http://www.bejson.com/go.php?u=http://www.bejson.com/index.php
				</li>
				<!-- 
				<li>
					（3）JSON测试例子：<input id="btn1" type="button" value="点击展开例子"/>
				</li> -->
				<li>
					（3）复制测试用例到左侧：<input id="btn2" type="button" value="点击复制测试用例到左侧"/>
				</li>
			</ul>
			 <!-- add by mymost -->
              <ul id="show_cltupdate" style="display: none">
                        <!--2015-4-22新增testcase   end -->
                        ---------------------------------------------客户端版本更新----------------------------------------------
                        <li>{</li>
                        <li>"serviceName":"aj_clt_update", </li>
                        <li>"callType":"001",</li>
                        <li>"params": {</li>
                        <li>"model":"11",</li>
                        <li>"channelId":"100",</li>
                        <li>"curVer":"1.0"</li>
                        <li>}</li>
                        <li>}</li>
             </ul>
                        
			<ul id="show0" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						<li>{					</li>
						<li>"serviceName":"aj_area_list",	</li>
						<li>"callType":"001",</li>
						<li>"params": {</li>
						<li>"showGd":"0",</li>
						<li>"pageSize":"100",</li>
						<li>"currentPage":"1"</li>
						<li>}				</li>
						<li>}					</li>
			</ul>
			<ul id="show1" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						<li>{</li>
						<li>"serviceName":"aj_user_reg_sms_apply",</li>
						<li>"callType":"001",</li>
						<li>"params": {</li>
						<li>"userTel":"15800299434"</li>
						<li>}</li>
						<li>}</li>
			</ul>
			<ul id="show2" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						<li>{					</li>
						<li>"serviceName":"aj_user_reg_sms_validate",	</li>
						<li>"callType":"001",</li>
						<li>"params": {</li>
						<li>"userTel":"15800299434",</li>
						<li>"smsCode":"123456"</li>
						<li>}				</li>
						<li>}					</li>
			</ul>
			<ul id="show3" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						<li>{					</li>
						<li>"serviceName":"aj_user_fast_reg",	</li>
						<li>"callType":"001",</li>
						<li>"params": {</li>
						<li>"userTel":"15800299434",</li>
						<li>"userPwd":"000000",</li>
						<li>"smsValidateToken":"000000",</li>
						<li>"appVer":"v1.0",</li>
						<li>"ucode":"1213232422313",</li>
						<li>"ip":"192.168.1.1",</li>
						<li>"phoneVer":"iphone",</li>
						<li>"phoneSysVer":"ios 8.0",</li>
						<li>"ua":"",</li>
						<li>"channelId":"100"</li>
						<li>}				</li>
						<li>}					</li>
			</ul>
			<ul id="show4" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						<li>{					</li>
						<li>"serviceName":"aj_user_mgr",	</li>
						<li>"callType":"001",</li>
						<li>"params": {</li>
						<li>"userId":"1",</li>
						<li>"nickName":"蔡文",</li>
						<li>"trueName":"蔡文",</li>
						<li>"sex":"0",</li>
						<li>"birthday":"1980-11-14",</li>
						<li>"address":"重庆渝北",</li>
						<li>"sign":"知识就是力量",</li>
						<li>"photo":""</li>
						<li>}				</li>
						<li>}					</li>
			</ul>
			<ul id="show5" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						<li>{					</li>
						<li>"serviceName":"aj_user_login",	</li>
						<li>"callType":"001",</li>
						<li>"params": {</li>
						<li>"userTel":"15800299434",</li>
						<li>"userPwd":"000000"</li>
						<li>}				</li>
						<li>}					</li>
			</ul>
			<ul id="show6" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						<li>{					</li>
						<li>"serviceName":"aj_user_Info",	</li>
						<li>"callType":"001",</li>
						<li>"params": {</li>
						<li>"userId":"U14121121010000001"</li>
						<li>}				</li>
						<li>}					</li>
			</ul>
			<ul id="show7" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						<li>{					</li>
						<li>"serviceName":"aj_user_logout",	</li>
						<li>"callType":"001",</li>
						<li>"params": {</li>
						<li>"userId":"U14121121010000001"</li>
						<li>}				</li>
						<li>}					</li>
			</ul>
		<ul id="show8" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						<li>{					</li>
						<li>"serviceName":"aj_deli_addr_list",	</li>
						<li>"callType":"001",</li>
						<li>"params": {</li>
						<li>"userId":"U14121121010000001",</li>
						<li>"pageSize":"20",</li>
						<li>"currentPage":"1"</li>
						<li>}				</li>
						<li>}					</li>
			</ul>
			<ul id="show9" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						<li>{					</li>
						<li>"serviceName":"aj_deli_addr_dtl",	</li>
						<li>"callType":"001",</li>
						<li>"params": {</li>
						<li>"userId":"U14121121010000001",</li>
						<li>"deliId":"DELI14121315331500001"</li>
						<li>}				</li>
						<li>}					</li>
			</ul>
			<ul id="show10" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						<li>{					</li>
						<li>"serviceName":"aj_deli_addr_mgr",	</li>
						<li>"callType":"001",</li>
						<li>"params": {</li>
						<li>"oper":"1",</li>
						<li>"deliId":"",</li>
						<li>"userId":"U14121121010000001",</li>
						<li>"deliName":"蔡文",</li>
						<li>"deliTel":"15800299434",</li>
						<li>"deliAreaAddr":"重庆渝北区",</li>
						<li>"deliDtlAddr":"人和街道HA6-1",</li>
						<li>}				</li>
						<li>}					</li>
			</ul>
			<ul id="show11" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
						   <li>  "serviceName": "aj_forget_pwd",</li>
						   <li>  "callType": "001",</li>
						   <li>  "params": {</li>
						   <li>     "newPssword": "111111",</li>
						   <li>     "smsValidateToken": "STKN15110500002100001",</li>
						   <li>     "ucode":"11111"</li>
						   <li>  }</li>
						   <li>}</li>
			</ul>
			<ul id="show12" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							  <li>   "serviceName": "aj_user_logout",</li>
							  <li>   "callType": "001",</li>
							   <li>  "params": {</li>
							   <li>      "userId": "1"</li>
							   <li>  }</li>
							 <li>}</li>
			</ul>
			<ul id="show13" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							   <li> "serviceName": "aj_feedback",</li>
							   <li> "callType": "001",</li>
							   <li> "params": {</li>
							    <li>    "userId": "1",</li>
							    <li>    "content": "2eaweada",</li>
							    <li>    "type": "1"</li>
							   <li> }</li>
							<li>}</li>
			</ul>
			<ul id="show14" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							<li>	"serviceName":"aj_allcmt_list",</li>
							<li>	"callType":"001",</li>
							<li>	"params":{</li>
							<li>	"userId":"1",</li>
							<li>	"photoId":"1",</li>
							<li>	"pageSize":"5",</li>
							<li>	"currentPage":"0"</li>
							<li>	}</li>
							<li>	}</li>
			</ul>
			<ul id="show15" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							<li>	"serviceName":"aj_cmt_mgr",</li>
							<li>	"callType":"001",</li>
							<li>	"params":{</li>
							<li>	"userId":"1",</li>
							<li>	"oper":"1",</li>
							<li>	"cmtType":"1",</li>
							<li>	"cmtId":"",</li>
							<li>	"cmtContent":"djaskdjasdas",</li>
							<li>	"cmtTarget":"1",</li>
							<li>	"replyCmtId":""</li>
							<li>	}</li>
							<li>	}</li>
			</ul>
			<ul id="show16" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							<li>	"serviceName":"aj_photo_dig",</li>
							<li>	"callType":"001",</li>
							<li>	"params":{</li>
							<li>	"userId":"1",</li>
							<li>	"oper":"0",</li>
							<li>	"photoId":"1"</li>
							<li>	}</li>
							<li>	}</li>
			</ul>
			<ul id="show17" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							<li>	"serviceName":"aj_photo_report",</li>
							<li>	"callType":"001",</li>
							<li>	"params":{</li>
							<li>	"userId":"1",</li>
							<li>	"photoId":"1"</li>
							<li>	}</li>
							<li>	}</li>
			</ul>
			<ul id="show18" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							<li>	"serviceName":"aj_album_list",</li>
							<li>	"callType":"001",</li>
							<li>	"params":{</li>
							<li>	"userId":"1",</li>
							<li>	"albumType":"1",</li>
							<li>	"pageSize":5,</li>
							<li>	"currentPage":0</li>
							<li>	}</li>
							<li>	}</li>
			</ul>
			<ul id="show19" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
								<li>"serviceName":"aj_album_mgr", </li>
								<li>"callType":"001", </li>
								<li>"params":{ </li>
								<li>"userId":"1", </li>
								<li>"oper":"1", </li>
								<li>"albumType":"1", </li>
								<li>"albumId":"", </li>
								<li>"albumName":"一日游",</li>
								<li>"albumDesc":"hainanyiriyou",</li>
								<li>"albumUrl":"http:dasdasdas.jpg"</li>
								<li>} </li>
								<li>} 
								</li>
			</ul>
			<ul id="show20" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_album_photo_mgr", </li>
							<li>	"callType":"001", </li>
							<li>	"params":{ </li>
							<li>	"userId":"1", </li>
							<li>	"oper":"1", </li>
							<li>	"photoIds":"1"</li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show21" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_album_photo_add", </li>
							<li>	"callType":"001", </li>
							<li>	"params":{ </li>
							<li>	"userId":"1", </li>
							<li>	"albumId":"2", </li>
							<li>	"photoUrl":"1"</li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show22" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							<li>    "serviceName": "aj_family_byId_add",</li>
							 <li>   "callType": "001",</li>
							 <li>   "params": {</li>
							  <li>      "userId": "1",</li>
							  <li>      "callId": "1",</li>
							  <li>      "isPrivate":"1",</li>
							   <li>     "description":"dasdasdasdas",</li>
							   <li>     "ajNo":"AJN00010",</li>
							    <li>    "userTel":""</li>
							   <li>  }</li>
							  <li>} </li>
			</ul>
			<ul id="show23" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							  <li>  "serviceName": "aj_family_query",</li>
							  <li>  "callType": "001",</li>
							  <li>  "params": {</li>
							   <li>     "userId": "1",</li>
							   <li>    "targetUserId": "9"</li>
							    <li> }</li>
							   <li> }</li>
			</ul>
			<ul id="show24" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							  <li>  "serviceName": "aj_family_mgr",</li>
							  <li>  "callType": "001",</li>
							   <li> "params": {</li>
							   <li>     "userId": "1",</li>
							   <li>     "targetUserId": "9",</li>
							    <li>    "oper":"3"</li>
							   <li>  }</li>
								<li>}</li>
			</ul>
			<ul id="show25" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
								<li>"serviceName":"aj_baby_mgr", </li>
								<li>"callType":"001", </li>
								<li>"params":{ </li>
								<li>"userId":"1", </li>
								<li>"oper":"1", </li>
								<li>"babyId":"",</li>
								<li>"nickName":"dd123",</li>
								<li>"conceptionDate":"20150228",</li>
								<li>"bornDate":"20151228",</li>
								<li>"photo":"http:dajsdasda.jpg"</li>
								<li>} </li>
								<li>} </li>
			</ul>
			<ul id="show26" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							  <li>   "serviceName": "aj_baby_query",</li>
							   <li>  "callType": "001",</li>
							    <li> "params": {</li>
							     <li>    "userId": "1",</li>
							     <li>    "babyId": "5"    }</li>
							 <li>} </li>
			</ul>
			<ul id="show27" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							 <li>   "serviceName": "aj_children_mgr",</li>
							  <li>  "callType": "001",</li>
							  <li>  "params": {</li>
							   <li>     "userId": "1",</li>
							   <li>     "oper": "1",</li>
							   <li>     "hasAjNo": "0",</li>
							   <li>     "photoUrl": "hdasdas/wdsa/sdas.jpg",</li>
							    <li>    "nickName": "大儿子",</li>
							    <li>    "trueName": "第三",</li>
							    <li>    "birthday": "2013-01-01 12:00:00",</li>
							    <li>    "six": "0"</li>
							   <li> }</li>
							<li>}</li>
			</ul>
			<ul id="show28" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							 <li>	    "serviceName": "aj_children_query",</li>
								 <li>    "callType": "001",</li>
								  <li>   "params": {</li>
								   <li>      "userId": "1",</li>
								    <li>     "childrenId": "7"</li>
								 <li> }</li>
								 <li>}</li>
			</ul>
			<ul id="show29" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_pet_mgr", </li>
							<li>	"callType":"001", </li>
							<li>	"params":{ </li>
							<li>	"userId":"1", </li>
							<li>	"oper":"1", </li>
							<li>	"petId":"",</li>
							<li>	"nickName":"dd123",</li>
							<li>	"six":"0",</li>
							<li>	"birthday":"20151228",</li>
							<li>	"photoUrl":"http:dajsdasda.jpg"</li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show30" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_pet_query", </li>
							<li>	"callType":"001", </li>
							<li>	"params":{ </li>
							<li>	"userId":"1", </li>
							<li>	"petId":"11"</li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show31" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_pray_letter_add", </li>
							<li>	"callType":"001", </li>
							<li>	"params":{ </li>
							<li>	"userId":"1", </li>
							<li>	"babyId":"5",</li>
							<li>	"content":"快快长大！",</li>
							<li>	"audio":"",</li>
							<li>	"picUrl":""</li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show32" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							<li>	    "serviceName": "aj_marrieddate_save",</li>
							<li>	    "callType": "001",</li>
							<li>	    "params": {</li>
							<li>	        "userId": "1",</li>
							<li>	        "marrieddate": "2015-07-07"</li>
							<li>	    }</li>
							<li>	} </li>
			</ul>
			<ul id="show33" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							<li>    "serviceName": "aj_marrieddate_query",</li>
							 <li>   "callType": "001",</li>
							<li>    "params": {</li>
							<li>        "userId": "1"</li>
							<li>    }</li>
							<li>} </li>
			</ul>
			<ul id="show34" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							<li>    "serviceName": "aj_collection_add",</li>
							<li>    "callType": "001",</li>
							<li>    "params": {</li>
							<li>        "userId": "1",</li>
							 <li>       "type": "1",</li>
							 <li>       "target": "1",</li>
							 <li>       "photoUrl": "dasdas.kss"</li>
							 <li>   }</li>
							<li>} </li>
			</ul>
			<ul id="show35" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							  <li>  "serviceName": "aj_collections_query",</li>
							  <li>  "callType": "001",</li>
							  <li>  "params": {</li>
							   <li>     "userId": "1","type":"1","target":"1"</li>
							   <li> }</li>
								<li>} </li>
			</ul>
			<ul id="show36" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							<li>    "serviceName": "aj_collection_delete",</li>
							 <li>   "callType": "001",</li>
							 <li>   "params": {</li>
							 <li>      "userId": "1","collectionIds":"1,2"</li>
							  <li>  }</li>
							<li>} </li>
			</ul>
			<ul id="show37" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							 <li>   "serviceName": "aj_baby_photos_query",</li>
							 <li>   "callType": "001",</li>
							 <li>   "params": {</li>
							  <li>      "userId": "1",</li>
							  <li>      "babyId": "7"</li>
							  <li>  }</li>
							<li>}</li>
			</ul>
			<ul id="show38" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							<li>    "serviceName": "aj_baby_photos_mgr",</li>
							<li>    "callType": "001",</li>
							 <li>   "params": {</li>
							 <li>       "userId": "1",</li>
							 <li>       "babyId": "7",</li>
							 <li>       "oper": "1",</li>
							 <li>       "age": "0-1",</li>
							 <li>       "photoId": "",</li>
							 <li>       "photoUrl": "0-1-1-1.jpg"</li>
							  <li>  }</li>
							<li>	}</li>
			</ul>
			<ul id="show39" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{</li>
							<li>    "serviceName": "aj_update_pwd",</li>
							<li>    "callType": "001",</li>
							 <li>   "params": {</li>
							 <li>       "userId": "1",</li>
							 <li>       "oldPassword": "111111",</li>
							 <li>       "newPssword": "000000",</li>
							 <li>       "ucode": "454544444444"</li>
							  <li>  }</li>
							<li>	}</li>
			</ul>
			<ul id="show40" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_zone_by_family_list", </li>
							<li>	"callType":"001", </li>
							<li>	"params": { </li>
							<li>	"userId":"1", </li>
							<li>	"familyId":"f23e8528-7923-455e-b97f-49788372f7e8" </li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show41" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_my_family_list", </li>
							<li>	"callType":"001", </li>
							<li>	"params": { </li>
							<li>	"userId":"1" </li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show42" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_more_family_list", </li>
							<li>	"callType":"001", </li>
							<li>	"params": { </li>
							<li>	"userId":"1" </li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show43" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_user_by_family_list", </li>
							<li>	"callType":"001", </li>
							<li>	"params": { </li>
							<li>	"userId":"1",</li>
							<li>	"familyId":"b3408f4a-bfcf-452b-a697-18aeb3741617" </li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show44" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_zone_by_family_list", </li>
							<li>	"callType":"001", </li>
							<li>	"params": { </li>
							<li>	"userId":"1",</li>
							<li>	"familyId":"b3408f4a-bfcf-452b-a697-18aeb3741617" </li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show45" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_zone_photo_list", </li>
							<li>	"callType":"001", </li>
							<li>	"params": { </li>
							<li>	"userId":"1",</li>
							<li>	"zoneId":"1", </li>
							<li>	"pageSize":"10", </li>
							<li>	"currentPage":"0" </li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show46" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_my_zone_photo_list", </li>
							<li>	"callType":"001", </li>
							<li>	"params": { </li>
							<li>	"userId":"1",</li>
							<li>	"zoneId":"1", </li>
							<li>	"pageSize":"10", </li>
							<li>	"currentPage":"0" </li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show47" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_family_relation_list", </li>
							<li>	"callType":"001", </li>
							<li>	"params": { </li>
							<li>	"userId":"1",</li>
							<li>	"key":"xxxxxxxxx" </li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show48" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_pray_do", </li>
							<li>	"callType":"001", </li>
							<li>	"params": { </li>
							<li>	"userId":"1",</li>
							<li>	"babyId":"12" </li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show49" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_pray_letter_list", </li>
							<li>	"callType":"001", </li>
							<li>	"params": { </li>
							<li>	"userId":"1",</li>
							<li>	"babyId":"12", </li>
							<li>	"pageSize":"10", </li>
							<li>	"currentPage":"0" </li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			<ul id="show50" style="display: none">
			---------------------------------------------测试列表接口----------------------------------------------
						
						   <li>{ </li>
							<li>	"serviceName":"aj_confirm_relation_service", </li>
							<li>	"callType":"001", </li>
							<li>	"params": { </li>
							<li>	"userId":"24",</li>
							<li>	"relationId":"51", </li>
							<li>	"oper":"0" </li>
							<li>	} </li>
							<li>	} </li>
			</ul>
			
			
			  <ul id="ad_list" style="display: none">
    --------------------广告列表获取接口--------------------
         <li>{                                   </li>
         <li>"serviceName":"aj_ad_list", </li>
         <li>"callType":"001",</li>
         <li>"params": {</li>
         <li>"channel":"0001015",</li>
         <li>"code":"1",</li>
         <li>"userId":"22"</li>
          <li>}                        </li>
         <li>}                           </li>
   </ul>
   <ul id="ad_syn" style="display: none">
   --------------------广告浏览统计接口--------------------
	    <li>{</li>
		<li>    "serviceName": "aj_ad_syn",</li>
		<li>    "callType": "001",</li>
		<li>    "params": {</li>
		<li>        "userId": "user1111",</li>
		<li>        "backParam": "{\"ad_content_id\":\"AD15042501092100221\",\"ad_content_type\":\"merchant\",\"ad_name\":\"首页头部广告1\",\"ad_position_code\":1,\"ad_position_id\":\"AD15042500402200031\",\"ad_position_name\":\"OTO订餐系统-首页广告条(Android)\",\"ad_relation_id\":\"AD15042501353100111\",\"ad_seq\":\"AD1\",\"ad_status\":0,\"ad_url\":\"\",\"call_type\":\"001\",\"channel_id\":\"0001015\",\"id\":\"\",\"new\":true,\"relation_id\":\"m1\",\"relation_title\":\"CSC新牌坊店\",\"userid\":\"\"}",</li>
		<li>        "ucode": "ucode-1111",</li>
		<li>        "ip": "ip-111",</li>
		<li>        "areaCode": "areaCode-重庆",</li>
		<li>        "phoneVer": "phoneVer-iPhone Simulator",</li>
		<li>        "phoneSysVer": "phoneSysVer-ios8.0",</li>
		<li>        "phoneNum": "phoneNum-111113"</li>
		<li>    }</li>
		<li>}</li>		
   </ul> 
   
   
		</fieldset>
		
	</body>
</html>
