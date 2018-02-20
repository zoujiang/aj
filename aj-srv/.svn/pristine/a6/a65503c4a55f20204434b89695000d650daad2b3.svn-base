<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="/common/inc.jsp"%>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">

		var json = {
			"guessScreenings" : [ {
				"rounds" : 1,
				"guessTime" : [ {
					"start" : "yyyy-MM-dd HH:mm:ss",
					"end" : "yyyy-MM-dd HH:mm:ss"
				} ],
				"announcementAnswerTime" : "yyyy-MM-dd HH:mm:ss",
				"updateCustomGroupTime" : "yyyy-MM-dd HH:mm:ss",
				"answerCalculator" : "[table]T_RES_CALC_MGR.CALC_ID",
				"maxGuessNum" : 1,
				"nextGuessCondition" : "1",
				"notCustomers" : "msg",
				"notGuessTime" : "msg",
				"answerRightMsg" : {
					"enable" : true,
					"msg" : "msg"
				},
				"answerErrorMsg" : {
					"enable" : true,
					"msg" : "msg"
				}
			} ],
			"prize" : {
				"answerRightIntoNextNum" : 1,
				"isContinuous" : true,
				"prizeSet1" : {
					"enable" : true,
					"prize" : {
						"enable" : true,
						"prizeID" : "prizeID"
					},
					"msg" : {
						"enable" : true,
						"msg" : "msg"
					}
				},
				"prizeSet2" : {
					"enable" : true,
					"givingPizeTime" : "yyyy-MM-dd HH:mm:ss",
					"awards" : [ {
						"grade" : 1,
						"numOfPeople" : 1,
						"prize" : "prizeID"
					} ],
					"winningMsg" : {
						"enable" : true,
						"msg" : "msg"
					},
					"loseMsg" : {
						"enable" : true,
						"msg" : "msg"
					}
				}
			}
		};
		
		$(function(){
			WdatePicker(
					{
						maxDate:'#F{$dp.$D(\'d4332\',{M:-3,d:-2})||$dp.$DV(\'2020-4-3\',{M:-3,d:-2})}'
					})
			$("body p").append(JSON.stringify(json));
			alert(typeof JSON.stringify(json));
			var j =  JSON.parse(JSON.stringify(json));
			alert(j.prize.isContinuous);
		});
		
		
		
		function getPlanDtl() {
			return {
				"guessScreenings" : [ {
					"rounds" : 1,
					"guessTime" : [ {
						"start" : "yyyy-MM-dd HH:mm:ss",
						"end" : "yyyy-MM-dd HH:mm:ss"
					} ],
					"announcementAnswerTime" : "yyyy-MM-dd HH:mm:ss",
					"updateCustomGroupTime" : "yyyy-MM-dd HH:mm:ss",
					"answerCalculator" : "[table]T_RES_CALC_MGR.CALC_ID",
					"maxGuessNum" : 1,
					"nextGuessCondition" : "1",
					"notCustomers" : "msg",
					"notGuessTime" : "msg",
					"answerRightMsg" : {
						"enable" : true,
						"msg" : "msg"
					},
					"answerErrorMsg" : {
						"enable" : true,
						"msg" : "msg"
					}
				} ],
				"prize" : {
					"answerRightIntoNextNum" : 1,
					"isContinuous" : true,
					"prizeSet1" : {
						"enable" : true,
						"prize" : {
							"enable" : true,
							"prizeID" : "prizeID"
						},
						"msg" : {
							"enable" : true,
							"msg" : "msg"
						}
					},
					"prizeSet2" : {
						"enable" : true,
						"givingPizeTime" : "yyyy-MM-dd HH:mm:ss",
						"awards" : [ {
							"grade" : 1,
							"numOfPeople" : 1,
							"prize" : "prizeID"
						} ],
						"winningMsg" : {
							"enable" : true,
							"msg" : "msg"
						},
						"loseMsg" : {
							"enable" : true,
							"msg" : "msg"
						}
					}
				}
			}
		}
	</script>

  </head>
  
  <body>
  

     <img alt="图片" src="http://218.206.27.198:18085/ams/service/img?img=/act/1385092314799wap.jpg">
     <img alt="图片" src="http://218.206.27.194:27068/ams/service/img?img=/act/1383899807806actpic.jpg">
      <img alt="图片" src=" http://218.206.27.198:18085/ams/service/img?img=/img/1386145921564file_small.png">
 <img alt="图片" src=" http://218.206.27.198:18085/ams/service/img?img=/img/1386145952258file_small.png">
  <img alt="图片" src=" http://218.206.27.198:18085/ams/service/img?img=/img/13861453954041386145395731_small.jpg">
  
    <img alt="图片" src=" http://218.206.27.198:18085/ams/service/img?img=/img/1386148411785123_small.jpg">
    <img alt="图片" src=" http://localhost:8080/ams/admin/img?img=/img/1386150635074123_small.jpg">

   
  
   
   <!-- &viewType=2 -->
<form action="http://localhost:8080/ams/service/fileUpload?jsonParam={'serviceName': 'ams_file_upload_req','callType':'android','params':{'smallWidth':'50.5','smallHeigth':'50.5','fileType':'jpg'}}" method="post" enctype ="multipart/form-data" > 
<input id="runat=" name="file" type="file" />
 <input type="submit" name="Button1" value="Button" id="Button1" /></form>
 <p>
 <p>
  </body>
</html>
