
var adapp = {};
//$(function() {
	
	

adapp = {

	/**
	 * 解析数据模板
	 *  @param tmpl：模板ID
	 *  @param data：JSON数据
	 *  @param id：内容显示区域ID
	 */
	transformTemplate : function(tmpl, data, id) {
		var html = baidu.template(tmpl, data);
		var obj = document.getElementById(id);
		if (obj) {
			$(obj).html(html);
		}
	},
	appendTemplate : function(tmpl, data, id){
		var html = baidu.template(tmpl, data);
		var obj = document.getElementById(id);
		if (obj) {
			$(obj).append(html);
		}
	},
	
	/**
	 *    root		 如:http://127.0.0.1/content/或者  /content/（不能跨域）
	 *    template   模板ID
	 *    div 		 显示区域ID
	 *    no 	     广告位编号
	 *    fun 回调方法
	 * ps: {root:"/wcs", template:"tid", div:"did", no:"ADL1120141029150355000001",callback:function(){}}
	 * 
	 * 
	 */
	load : function(ps) {
		if (ps.no == null || ps.no == '') {
			//return;
		}
		if (ps.root == null || ps.no == '') {
			ps.root = "/aj";
		}
		//ad_location_no = "";
		$.ajax({
			type : "post",
			url : ps.root + "/service/ad/list?no=" + ps.no,
			dataType : "json",
			success : function(data) {
				//alert(data);
				if (!data || data.total_num < 1) {
					return;
				}
				
				adapp.transformTemplate(ps.template, {
					data : data.adList
				}, ps.div);
				
				if (ps.callback && data.adList.length > 1) {
					ps.callback.call();
				}
			}
		});
			
	},
	loadspecial : function(ps) {
		$.ajax({
			type : "post",
			url : "/wcs" + ps.url,
			dataType : "json",
			success : function(data) {
				if (!data || data.total_num < 1) {
					return;
				}
				
				adapp.appendTemplate(ps.template, {
					data : data.data
				}, ps.div);
				
				if (ps.callback) {
					ps.callback.call();
				}
			}
		});
			
	},
	getQueryStr : function(localStr, str) {
		var rs = new RegExp("(^|)" + str + "=([^&]*)(&|$)", "gi")
				.exec(localStr), tmp;
		if (tmp = rs) {
			return tmp[2];
		}
		return "";
	}

};


