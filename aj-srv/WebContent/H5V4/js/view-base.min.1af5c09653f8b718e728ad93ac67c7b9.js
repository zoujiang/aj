function EVA_Player_Cover(e, t) {
	this.max = 1,
	this.pos = 0,
	this.title = "加载中...",
	this.enough_to_display = !1,
	this.root = $node("wrapper_cover"),
	this.root.style.width = e + "px",
	this.root.style.height = t + "px",
	this.show = function(i, n, o) {
		function a(e) {
			e < c.length && ($node(c[e]).style.display = "block", $node(c[e]).className = "effect_fadeIn", window.setTimeout(function() {
				a(e + 1)
			},
			300))
		}
		this.root.className = "",
		this.enough_to_display = !1,
		$node("album_title").innerText = i,
		$node("wrapper_player_cover_bkgnd").style.height = t - 52 + "px";
		var r = $node("wrapper_player_cover_bkgnd").getElementsByTagName("img")[0],
		l = e,
		s = t - 52,
		_ = new Image;
		_.onload = function() {
			r.src = o;
			var e = calcImgClip(l, s, _.width, _.height);
			r.style.left = 0 - e.clipLeft + "px",
			r.style.top = 0 - e.clipTop + "px",
			r.style.width = e.objWidth + "px",
			r.style.height = e.objHeight + "px",
			r.style.clip = "rect(" + e.clipTop + "px " + Math.floor(e.clipLeft + l) + "px " + Math.floor(e.clipTop + s) + "px " + e.clipLeft + "px)"
		},
		_.src = o,
		this.root.style.display = "block";
		var c = ["album_title"];
		a(0);
		var u = this;
		window.setTimeout(function() {
			u.enough_to_display = !0
		},
		2e3)
	},
	this.setRange = function(e) {
		this.max = e,
		this.updateProgress(this.pos)
	},
	this.updateProgress = function(e) {
		this.pos = e,
		$("#div_player_loadingText").text(this.title + "(" + this.pos + "/" + this.max + ")"),
		e == this.max && ($node("div_player_loadingText").style.opacity = 0)
	},
	this.checkReadyToHide = function(e) {
		var t = this;
		1 == this.enough_to_display ? (this.root.addEventListener("webkitAnimationEnd",
		function() {
			t.root.style["pointer-events"] = "none"
		},
		!1), TTXP_Animation_Mgr.animate(this.root, {
			animates: [{
				name: "kf_fadeOut",
				duration: 2,
				delay: 0,
				timing: "ease"
			}],
			end: function(e) {
				e.style.display = "none",
				$(e).remove()
			}
		}), e()) : setTimeout(function() {
			t.checkReadyToHide(e)
		},
		100)
	},
	this.hide = function(e) {
		this.checkReadyToHide(e)
	}
}
function EVA_Player(player_div_id, maxWidth, maxHeight) {
	var __LOG_TAG__ = "TTXP_Album_Player",
	canvasWidth_ = maxWidth,
	canvasHeight_ = maxHeight,
	player_canvas_id_ = player_div_id,
	real_playerCreator_ = "",
	realPlayerObj_ = null,
	loader_ = new EVA_Player_Cover(maxWidth, maxHeight),
	img_previewer,
	template_;
	this.applyTemplate = function(e) {
		LOG.trace(__LOG_TAG__, "apply template(file:" + e.script + ", Obj:" + e.object + ")"),
		template_ = e;
		var t = $node(player_canvas_id_);
		t.innerHTML = "";
		var i = g_images[0].src;
		loader_.show(g_album_info.title, g_album_info.create_time, i),
		img_previewer = new ImagePreviewer(t, canvasWidth_, canvasHeight_),
		real_playerCreator_ = e.object,
		check_body_scroll()
	},
	this.setBkMusic = function(e, t) {
		void 0 != e && "" != e && (TTXP_BGM.load(e), t === !0 && TTXP_BGM.play())
	},
	this.addMedia = function(e) {
		__addImgByUrl(e.url),
		TTXP.imgMgr.append(e)
	},
	this.previewImage = function(e) {
		img_previewer.preview(e, g_images)
	},
	this.play = function() {
		LOG.trace(__LOG_TAG__, "play");
		var e = [];
		e.push({
			url: template_.script,
			type: "js"
		});
		for (var t = 0; t < g_images.length && 10 > t; ++t) e.push({
			type: "image",
			url: g_images[t].src
		});
		loader_.setRange(e.length),
		PreLoad.load({
			files: e,
			progress: function(e, t, i) {
				LOG.trace(__LOG_TAG__, "[PreLoad] " + (e + t) + "/" + i),
				loader_.updateProgress(e + t)
			},
			fail: function(e) {
				LOG.error(__LOG_TAG__, "[PreLoad] fail:" + e)
			},
			complete: function(e, t, i) {
				LOG.trace(__LOG_TAG__, "[PreLoad] " + e + ", " + t + "," + i),
				__createStage(),
				loader_.hide(__realPlay)
			}
		})
	};
	var __addImgByUrl = function(e) {
		g_images.push({
			src: e
		})
	},
	__createStage = function() {
		LOG.trace(__LOG_TAG__, "create stage");
		for (var album_info = {
			canvas_id: player_canvas_id_,
			max_width: canvasWidth_,
			max_height: canvasHeight_,
			title: g_album_info.title,
			music: g_album_info.music,
			create_time: g_album_info.create_time,
			images: []
		},
		i = 0; i < g_images.length; ++i) album_info.images.push({
			src: g_images[i].src
		});
		console.log(JSON.stringify(album_info));
		var func_str = "realPlayerObj_ = new " + real_playerCreator_ + "(album_info);";
		try {
			eval(func_str)
		} catch(e) {
			console.log("exception when create template, msg:" + e.message + ", desc:" + e.description)
		}
	},
	__realPlay = function() {
		LOG.trace(__LOG_TAG__, "real begin play"),
		realPlayerObj_.play(),
		TTXP_Create_Guide.show(),
		$(".editor_btn_section").each(function(e) {
			var t = this;
			window.setTimeout(function() {
				$(t).show()
			},
			10 + 100 * e)
		})
	}
}
function EVA_CSS_Loader(e, t, i, n) {
	var o, a, r, l = !1,
	s = !1,
	_ = function() {
		1 != s && 1 != l && (l = !0, log("trace", "[CSSLoader]load " + e + " finished, ready to go!"), clearInterval(o), clearTimeout(a), r && void 0 != r && (r.parentNode.removeChild(r), r = void 0), t())
	},
	c = function() {
		1 != s && 1 != l && (s = !0, log("error", "[CSSLoader]load " + e + " failed!"), clearInterval(o), clearTimeout(a), r && void 0 != r && (r.parentNode.removeChild(r), r = void 0), i())
	},
	u = function(e, t) {
		return e.currentStyle ? e.currentStyle[t] : e.ownerDocument.defaultView.getComputedStyle(e, null)[t]
	};
	this.load = function() {
		var t = document.createElement("link");
		t.rel = "stylesheet",
		t.type = "text/css",
		t.href = e,
		a = setTimeout(function() {
			log("error", "[CSSLoader]load " + e + " timeout.!"),
			c()
		},
		3e4),
		r = document.createElement("div"),
		r.id = n,
		document.body.appendChild(r),
		o = setInterval(function() {
			"none" === u(r, "display") && _()
		},
		50);
		var i = document.head || document.getElementsByTagName("head")[0];
		i.appendChild(t)
	}
}
function loadCSSFile(e, t, i, n) {
	var o = new EVA_CSS_Loader(e, t, i, n);
	o.load()
}
function preventTouch(e) {
	e.preventDefault()
}
function enableBodyTouch(e) {
	e ? $unlisten(document, "touchmove", preventTouch) : $listen(document, "touchmove", preventTouch)
}
function onDocReady() {
	traceTimeCost("doc ready");
	var e = document.body.scrollWidth,
	t = document.body.scrollHeight;
	$("#div_player").width(e).height(t),
	TTXP.init(),
	TTXP_BGM.init({
		obj: $("#music_bkgnd")[0],
		ready: function() {},
		play: function() {
			$("#wraper_music").show(),
			$("#btn_play_music").attr("class", "c_btn_play_music_playing")
		},
		pause: function() {
			$("#btn_play_music").attr("class", "c_btn_play_music_paused")
		}
	}),
	TTXP_UI.LoadingToast.show("数据加载中...");
	var i = getUrlParams();
	getAlbumInfo(i.id,
	function(i, n, o) {
		if (traceTimeCost("getAlbumInfo"), console.log("res get album info"), TTXP_UI.LoadingToast.hide(), i === !0) {
			g_album_info = o,
			isEditMode(g_album_info) ? _runEditMode() : _runPlayerMode(),
			g_albumPlayer = new EVA_Player("div_player", e, t);
			var a = g_album_info.template.music;
			void 0 != g_album_info.music && "" != g_album_info.music && (a = g_album_info.music),
			g_albumPlayer.setBkMusic(a, !1);
			for (var r = 0; r < g_album_info.media_list.length; ++r) g_albumPlayer.addMedia(g_album_info.media_list[r]);
			g_albumPlayer.applyTemplate(g_album_info.template),
			g_albumPlayer.play(),
			document.title = g_album_info.title,
			weixin_share_api.setShareImg(g_album_info.media_list[0].url),
			weixin_share_api.setAlbumTitle(g_album_info.title),
			g_album_info.share_desc && weixin_share_api.setShareDesc(g_album_info.share_desc),
			TTXP_Favor.init(get_ttxp_openid(), g_album_info),
			wxAPI.init(!1,
			function(e) {
				traceTimeCost("wx api ret"),
				console.log("wx ready, ret:" + e),
				1 == e && wx.showOptionMenu(),
				weixin_share_api.init(!1),
				__play_bgm()
			},
			!1, "undefined" != typeof wx_jsapi_sig ? wx_jsapi_sig: null);
			traceTimeCost("play")
		} else showException("您所查看的亲脉分享地址错误!"),
		QOS_Error("GetAlbumInfoFail")
	},
	!1),
	TTXP_BIZ_MGR.init(function() {
		var e = "",
		t = TTXP_BIZ_MGR.id();
		TTXP.setBizId(t),
		i.guide_type && "1" === i.guide_type ? e = "": isAlbumOwner() ? QOS_Stat("VIEW_Host", 0) : (e = TTXP_BIZ_MGR.getFollowGuide(t), QOS_Stat("VIEW_Guest", 0)),
		weixin_share_api.setShareJump(e),
		$("#div_player_mask").attr("href", TTXP_BIZ_MGR.getFollowGuide(t)).find(".mp_name").text(TTXP_BIZ_MGR.name()),
		"TTXP" == t || isAlbumOwner() ? $("#div_player_btn_more").remove() : $("#div_player_btn_more").attr("href", TTXP_BIZ_MGR.getFollowGuide(t))
	}),
	reportData("pageView", "eva_album.html")
}
function getAlbumInfo(e, t) {
	if (console.log("get album info, uid=" + getUID() + ", album_id=" + e), "undefined" != typeof g_album_info_res) if ("0" == g_album_info_res.ret) {
		var i = g_album_info_res.data;
		i.title = TTXP_Dirty.filter(decodeURIComponent(i.title)),
		i.share_desc = TTXP_Dirty.filter(decodeURIComponent(i.share_desc ? i.share_desc: "")),
		i.id = e,
		t(!0, "", i)
	} else console.log("Request album info failed, ret:%s, msg:%s", g_album_info_res.ret, g_album_info_res.msg),
	t(!1, g_album_info_res.msg, {});
	else t(!1, "", {})
}
function _runEditMode() {
	var e = document.body.scrollHeight;
	$("#div_editor").height(e).show(),
	TTXP_ALBUM_MUSIC_LIST.init(),
	TTXP_ALBUM_TMPL_LIST.init(),
	AlbumEditPageMgr.push("div_change_template", "editor_change_template", "换模板", e - 40, null, onTemplateContentShow),
	AlbumEditPageMgr.push("div_change_music", "music_list_wrapper", "选音乐", e - 40, onAlbumMusicSelect, onMusicContentShow),
	AlbumEditPageMgr.push("div_change_content", "editor_change_content", "改标题", e - 40, onSaveTitle, onTextContentShow),
	AlbumEditPageMgr.push("div_change_picture", "edit_page_image", "加照片", e - 40, onSaveImage, onImageContentShow),
	$(".editor_btn_section").on("click",
	function() {
		onEditBtnClk(this)
	}),
	$("#editor_content_closebtn").on("click",
	function(e) {
		onCloseClk()
	}),
	$("#editor_content_confirm").on("click",
	function(e) {
		onConfirmClk()
	})
}
function _runPlayerMode() {
	TTXP_Create_Guide.enable(!0),
	$("#div_editor").remove()
}
function onPlayMusic() {
	1 == TTXP_BGM.paused() ? TTXP_BGM.play() : TTXP_BGM.pause()
}
function log(e, t) {
	console.log("[" + e + "]" + t)
}
function reportData(e, t) {
	console.log("reportData, type:" + e + ", value:" + t);
	var i = getUrlParams(),
	n = new XMLHttpRequest,
	o = "http://qos.ttxuanpai.com/data/report";
	n.open("POST", o, !0),
	n.setRequestHeader("Content-type", "application/json");
	var a = {
		type: e,
		data: {
			album_id: i.id,
			value: t
		}
	};
	n.send(JSON.stringify(a))
}
function traceTimeCost(e) {
	var t = new Date,
	i = t.getTime() - __time_begin.getTime();
	return console.log("[PERF]#" + e + ": " + i + "ms"),
	i
}
function isEditMode(e) {
	if (e && 0 == e.status) {
		var t = getUrlParams();
		return ! (t && "true" == t.readonly)
	}
	return ! 1
}
function showException(e) {
	$("#btn_create_album").on("touchstart",
	function() {
		TTXP_DA.click("album.view.error.create")//,
		//location.href = "create.php?biz=" + get_biz_id()
	}),
	$("#div_error").height(document.body.scrollHeight).show(),
	$("#txt_error_desc").text(e),
	TTXP_BGM.stop()
}
function isAlbumOwner() {
	return g_album_info && 0 == g_album_info.status
}
function __play_bgm() {
	TTXP_BGM.set_ready(),
	TTXP_BGM.play(),
	document.removeEventListener("touchstart", __play_bgm, !1)
}
function begin_play_bgm() {
	document.addEventListener("WeixinJSBridgeReady",
	function() {
		console.log("WeixinJSBridgeReady"),
		__play_bgm()
	},
	!1),
	document.addEventListener("YixinJSBridgeReady",
	function() {
		console.log("YixinJSBridgeReady"),
		__play_bgm()
	},
	!1),
	document.addEventListener("touchstart", __play_bgm(), !1)
}
function check_body_scroll() {
	g_album_info && "2.0" == g_album_info.template.version && (enableBodyTouch(!0), $("#div_player").css("overflow-y", "auto"))
}
function get_biz_id() {
	var e = getUrlParams();
	return "biz" in e ? e.biz: "TTXP"
}
function ImagePreviewer(e, t, i) {
	function n() {
		console.log("close img viewer"),
		c.className = "img_viewer_hide",
		c.style["pointer-events"] = "none"
	}
	function o() {
		var e = r(u.width, u.height);
		a(e)
	}
	function a(e) {
		u.style.left = parseInt((t - e.width) / 2) + "px",
		u.style.top = parseInt((i - e.height) / 2) + "px",
		u.style.width = e.width + "px",
		u.style.height = e.height + "px"
	}
	function r(e, t) {
		var i = e,
		n = t,
		o = i,
		a = n;
		return (i > s || n > _) && (o = Math.floor(_ * i / n), s >= o ? a = _: (a = Math.floor(n * s / i), o = s)),
		{
			width: o,
			height: a
		}
	}
	function l(e) {
		for (var t in g_images) if (g_images[t].src == e) return g_images[t];
		return null
	}
	var s = i > t ? t - 4 : t - 30,
	_ = i > t ? i - 4 : i - 30,
	c = document.createElement("div");
	c.id = "img_viewer",
	c.style.width = t + "px",
	c.style.height = i + "px",
	c.innerHTML = '<img src="">',
	c.onclick = n;
	var u = c.getElementsByTagName("img")[0];
	u.onload = o,
	e.appendChild(c),
	this.preview = function(e, t) {
		var i = l(e);
		console.log("preview image:" + i.src),
		c.style.display = "block",
		c.className = "img_viewer_show",
		c.style["pointer-events"] = "auto",
		u.src = i.src;
		var n = r(i.width, i.height);
		a(n)
	}
}
function EVA_JS_Loader(e, t, i) {
	this.load = function() {
		console.log("trace", "load js file:" + e),
		jQuery.getScript(e).done(function() {
			t()
		}).fail(function() {
			var n = document.createElement("script");
			n.setAttribute("type", "text/javascript"),
			n.setAttribute("src", e),
			n.onload = function() {
				console.log("Js File load successed"),
				t()
			},
			n.onerror = function() {
				console.log("Js File load failed!"),
				i()
			};
			var o = document.head || document.getElementsByTagName("head")[0];
			o.appendChild(n)
		})
	}
}
function loadJSFile(e, t, i) {
	var n = new EVA_JS_Loader(e, t, i);
	n.load()
}
var TTXP_BGM = new
function() {
	function e() {
		LOG.trace(i, "begin play"),
		r = !1,
		t.obj.play();
		var e, n, o;
		window.setTimeout(function() {
			r ? (QOS_Stat("WithBGM_1", 0), window.clearTimeout(e), window.clearTimeout(n), window.clearTimeout(o)) : QOS_Err("NoBGM_1", {
				file: t.obj.src
			})
		},
		1e3),
		e = window.setTimeout(function() {
			r ? (QOS_Stat("WithBGM_3", 0), window.clearTimeout(n), window.clearTimeout(o)) : QOS_Err("NoBGM_3", {
				file: t.obj.src
			})
		},
		3e3),
		n = window.setTimeout(function() {
			r ? (QOS_Stat("WithBGM", 0), window.clearTimeout(o)) : QOS_Err("NoBGM", {
				file: t.obj.src
			})
		},
		5e3),
		o = window.setTimeout(function() {
			r ? QOS_Stat("WithBGM_10", 0) : QOS_Err("NoBGM_10", {
				file: t.obj.src
			})
		},
		1e4)
	}
	var t, i = "BGM",
	n = !1,
	o = !1,
	a = !1,
	r = !1,
	l = !1;
	this.init = function(e) {
		t = e,
		t.obj = new Audio,
		$(t.obj).bind("ended",
		function() {
			LOG.trace(i, "on play ended"),
			o && (t.obj.load(), t.obj.play())
		}).bind("error",
		function() {
			LOG.error(i, "on play error"),
			QOS_Err("LoadMusicFail", {
				file: t.obj.src
			})
		}).bind("playing",
		function() {
			LOG.trace(i, "begin really play, play ing"),
			r = !0,
			t && t.play && t.play()
		}).bind("canplay",
		function() {
			LOG.trace(i, "can play"),
			a = !0
		});
		var n = this;
		window.setTimeout(function() {
			l || (console.log("bgm over time, auto play"), n.set_ready(), n.play())
		},
		1e4)
	},
	this.set_ready = function() {
		LOG.trace(i, "set ready"),
		n = !0,
		n || t && t.ready && t.ready()
	},
	this.load = function(e) {
		LOG.trace(i, "load:" + e),
		a = !1,
		"" != e && (o = !0, t.obj.src = e, t.obj.load())
	},
	this.play = function() {
		LOG.trace(i, "request play"),
		l || (e(), l = !0)
	},
	this.stop = function() {
		this.pause()
	},
	this.pause = function() {
		LOG.trace(i, "pause"),
		t.obj.pause(),
		t && t.pause && t.pause(),
		l = !1
	},
	this.paused = function() {
		return t.obj.paused
	}
},
g_images = [],
TTXP_BIZ_MGR = new
function() {
	var e = {};
	this.init = function(t) {
		jQuery.getScript("http://mp.ttxuanpai.com/static/biz_config.js?t=" + parseInt(Date.now() / 3e5)).done(function() {
			try {
				for (var i = 0; i < TTXP_BIZ_CONFIG.length; ++i) e[TTXP_BIZ_CONFIG[i].id] = TTXP_BIZ_CONFIG[i];
				t()
			} catch(n) {
				QOS_Error("GetBizConfig")
			}
		}).fail(function() {
			QOS_Error("GetBizConfig"),
			t()
		})
	},
	this.getFollowGuide = function(t) {
		if (t in e) {
			if ("follow_guide" in e[t]) return e[t].follow_guide
		} else QOS_Error("InvalidBiz");
		return "http://mp.weixin.qq.com/s?__biz=MzAwNTI1MDE0MA==&mid=100000311&idx=1&sn=76fac526861f05e68474415331b21ca9#rd"
	},
	this.randomParter = function(t) {
		if (t in e) {
			var i = e[t];
			if ("partners" in i && i.partners.length > 0) {
				var n = parseInt(Math.random() * i.partners.length);
				return i.partners[n]
			}
			return t
		}
		return t
	},
	this.id = function() {
		var e = getUrlParams();
		return "biz" in e ? e.biz: "TTXP"
	},
	this.name = function() {
		var t = this.id(),
		i = "宝宝印象";
		return t in e && "name" in e[t] && (i = e[t].name),
		i
	}
},
TTXP_Dirty = new
function() {
	var e = ["转发", "朋友圈", "转朋友", "转给", "发给", "发朋友"];
	this.filter = function(t) {
		for (var i = 0; i < e.length; ++i) {
			for (var n = new RegExp(e[i], "g"), o = "", a = 0; a < e[i].length; ++a) o += "*";
			t = t.replace(n, o)
		}
		return t
	}
},
g_albumPlayer;
$(document).ready(function() {
	enableBodyTouch(!1),
	onDocReady()
});
var TTXP_Favor = new
function() {
	var e, t;
	this.init = function(a, r) {
		e = r,
		t = a,
		i(),
		$("#btn_favor").click(function() {
			var t = 1;
			n() && (t = 0),
			o(t),
			e.favor.status = t,
			i()
		})
	};
	var i = function() {
		$("#btn_favor").attr("class", n() ? "favor": "favor_none")
	},
	n = function() {
		return e && e.favor && e.favor.status && 1 == e.favor.status
	},
	o = function(t) {
		$.ajax({
			type: "POST",
			url: "http://api.ttxuanpai.com/cgi-bin/favor",
			dataType: "json",
			data: JSON.stringify({
				uid: get_kawa_openid(),
				openid_ttxp: get_ttxp_openid(),
				id: e.id,
				type: "album",
				favor: t,
				biz: TTXP_BIZ_MGR.id()
			}),
			success: function(e, t) {
				console.log("res favor album, data:" + JSON.stringify(e)),
				"0" == e.ret ? QOS_Stat("FavorSuccess", 0) : QOS_Error("TTXP_Favor_Album_Fail")
			},
			fail: function() {
				QOS_Error("TTXP_Favor_Album_Fail")
			},
			error: function() {}
		}),
		TTXP_DA.click("set"),
		QOS_Stat("set", 0)
	}
},
TTXP_Create_Guide = new
function() {
	var e = !1;
	this.enable = function(t) {
		e = t
	},
	this.show = function() {
		e && ($("#div_player_mask").show(), $("#div_player_footer").addClass("effect_fadeIn").show(), t())
	};
	var t = function() {
		window.setTimeout(function() {
			var e = $("#div_player_footer").find(".guide_ornament").show(),
			t = ["kf_fadeInUp", "kf_fadeIn", "kf_bounceInUp"],
			i = ["kf_tada", "kf_shake"];
			TTXP_Animation_Mgr.animate(e[0], {
				animates: [{
					name: t[parseInt(Math.random() * t.length)],
					duration: 1.5,
					delay: 0,
					timing: "ease"
				},
				{
					name: i[parseInt(Math.random() * i.length)],
					duration: 1.5,
					delay: 1,
					timing: "ease"
				},
				{
					name: "kf_fadeOut",
					duration: 2.5,
					delay: 3,
					timing: "ease"
				}],
				end: function() {}
			})
		},
		3e3 + 7e3 * Math.random())
	}
},
TTXP = new
function() {
	var e = "",
	t = 5,
	i = !1;
	this.init = function() {
		n()
	};
	var n = function() {
		$("#playendtips_replay").click(function() {
			$("#wrapper_play_end").hide(),
			TTXP_DA.click("album.view.play.endtip.replay")
		}),
		$("#playendtips_stop").click(function() {
			TTXP_DA.click("album.view.play.endtip.stop"),
			window.close(),
			wx.closeWindow()
		})
	},
	o = function() {
		$("#playendtips_replay").text("继续播放(" + t + "s)"),
		0 == t ? $("#wrapper_play_end").hide() : (t--, window.setTimeout(o, 1e3))
	};
	this.showPlayEndTips = function() {
		if (i = !0, !isEditMode(g_album_info)) {
			$("#playendtips_title").text(g_album_info.title);
			var e = new Date;
			e.setTime(1e3 * g_album_info.create_time),
			$("#playendtips_date").text(e.getFullYear() + "年" + (e.getMonth() + 1) + "月" + e.getDate() + "日"),
			$("#wrapper_play_end").show(),
			t = 10,
			o()
		}
	},
	this.isPlayEndTipsShowed = function() {
		return i
	},
	this.setBizId = function(t) {
		e = t
	},
	this.getBizId = function() {
		return e
	},
	this.imgMgr = new
	function() {
		var e = "TTXP_ImageMgr",
		t = [],
		i = 0;
		this.append = function(i) {
			LOG.trace(e, "append:" + i),
			i && i.url && i.url.length > 0 && t.push({
				url: i.url,
				desc: TTXP_Dirty.filter(i.desc ? decodeURIComponent(i.desc) : "")
			})
		},
		this.getNextImg = function() {
			return t.length < 1 ? null: t[i++%t.length]
		},
		this.isAllImgDisplayed = function() {
			return i > t.length
		},
		this.getImgCount = function() {
			return t.length
		},
		this.getDisplayedImgCount = function() {
			return i
		},
		this.getImg = function(e) {
			return e < t.length ? t[e] : null
		}
	}
},
weixin_share_api = new
function() {
	var e = "",
	t = "http://cdn.ttxuanpai.com/images/icon.png",
	i = "天天炫拍",
	n = "天天炫拍",
	o = "精彩的图片，打开看看吧~";
	this.init = function(n) {
		console.log("init wexin_share_api");
		var a = location.href.replace(/uid=[^&]+&?/, "").replace(/openid=[^&]+&?/, "").replace(/openid_ttxp=[^&]+&?/, ""),
		r = function(i, n) {
			console.log("share result, type=" + i + ", result=" + n + ", img=" + t + ", url=" + a),
			1 == n ? (reportData(i, "1"), QOS_Report("followGuide", i), "" != e && window.setTimeout(function() {
				location.href = e
			},
			100)) : reportData(i, "-1")
		};
		wx.onMenuShareTimeline({
			title: i,
			link: a,
			imgUrl: t,
			success: function() {
				r("shareToTimeline", !0)
			},
			cancel: function() {
				r("shareToTimeline", !1)
			}
		}),
		wx.onMenuShareAppMessage({
			title: i,
			desc: o,
			link: a,
			imgUrl: t,
			type: "link",
			dataUrl: "",
			success: function() {
				r("shareToFriends", !0)
			},
			cancel: function() {
				r("shareToFriends", !1)
			}
		}),
		wx.onMenuShareQQ({
			title: i,
			desc: o,
			link: a,
			imgUrl: t,
			success: function() {
				r("shareToQQ", !0)
			},
			cancel: function() {
				r("shareToQQ", !1)
			}
		}),
		wx.onMenuShareWeibo({
			title: i,
			desc: o,
			link: a,
			imgUrl: t,
			success: function() {
				r("shareToWeibo", !0)
			},
			cancel: function() {
				r("shareToWeibo", !1)
			}
		}),
		wx.onMenuShareQZone({
			title: i,
			desc: o,
			link: a,
			imgUrl: t,
			success: function() {
				r("shareToQZone", !0)
			},
			cancel: function() {
				r("shareToQZone", !1)
			}
		})
	},
	this.setShareJump = function(t) {
		console.log("Set wx share jump url:" + t),
		e = t
	},
	this.setShareImg = function(e) {
		console.log("setShareImg:" + e),
		t = e
	},
	this.setAlbumTitle = function(e) {
		console.log("setAlbumTitle:" + e),
		i = n = e
	},
	this.setShareDesc = function(e) {
		e && void 0 != e && "" != e && (o = e)
	}
};