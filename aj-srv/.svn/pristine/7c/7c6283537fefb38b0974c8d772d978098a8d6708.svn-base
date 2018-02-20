var sendCltMsg = function(cltUrl) {
        cltUrl = encodeURI(cltUrl);
        var browser = {
                        versions: function() {
                        var u = navigator.userAgent;
//                              var app = navigator.appVersion;
                        return {//移动终端浏览器版本信息 
                        trident: u.indexOf('Trident') > -1, //IE内核
                        presto: u.indexOf('Presto') > -1, //opera内核
                        webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                        gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                        mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
                        ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                        android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                        iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
                        iPad: u.indexOf('iPad') > -1, //是否iPad
                        webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
                        };
                        }(),
                        language: (navigator.browserLanguage || navigator.language).toLowerCase()
                        };
          if (true/*browser.versions.ios || browser.versions.iPhone || browser.versions.iPad*/) {
                 // alert('ios' + cltUrl);
                var iframe = document.createElement('iframe');
                    iframe.setAttribute('src', cltUrl);
                    document.documentElement.appendChild(iframe);
                    iframe.parentNode.removeChild(iframe);
                    iframe = null;
          }else if (browser.versions.android) {
                	window.location=cltUrl;
          }else{
                    window.location=cltUrl;
          }
           
        }

function WxcityAppTools() {
        var obj = {}; 
        var cltUrl;
        obj.getUserInfo = function() {
                    cltUrl = "wxcitycq://call&jsParam=&cltMethod=getUserInfo", 
                    sendCltMsg(cltUrl);
                };
        obj.getLoginUserInfo = function() {
                    cltUrl = "wxcitycq://call&jsParam=&cltMethod=getLoginUserInfo", 
                    sendCltMsg(cltUrl);
                };

        obj.redirect = function(andriodUrl,iosUrl) {
                    cltUrl = "wxcitycq://redirect&jsParam=&andriodParam=" + andriodUrl + "&iosParam=" + iosUrl;
                    sendCltMsg(cltUrl);
                };
        return obj;
}

var wxcscqAppTools = {
	bbs: {
		toTopic:function(topicId) {
			var android = 'AP500000000000010500∮∮bd4b98b81127424000c90ccb9b70a2fc∮∮青虫论坛∮∮com.cmcc.wificity∮∮com.feinno.wificommunity.TopicDetailsActivity∮∮{"id":"1","forumId":"1","author":"1","topicid":"' + topicId + '","newbbstype":"1"}'; 
	        var ios = 'AP500000000000010500∮∮bd4b98b81127424000c90ccb9b70a2fc∮∮' + topicId + '∮∮WifiForum_ForumInfoViewController∮∮{老版帖子ID}∮∮22∮∮2∮∮{老版板块ID}∮∮{标题}∮∮{用户名}';
	        
	        var tools = new WxcityAppTools();
	        tools.redirect(android, ios);
		} 
	}
}


function getUserInfo(data){
    alert(data);
}

/*

//各自页面去写这个方法
function getLoginUserInfo(data){
        alert("login:" + data);
        return data;
}
*/
