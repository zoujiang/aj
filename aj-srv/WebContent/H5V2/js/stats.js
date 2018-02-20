(function(d) {
	d.MtaH5 = d.MtaH5 || {};
	MtaH5.hack = function() {
		var d = document.getElementsByName("MTAH5"),
		n = {
			conf: {
				autoReport: 1,
				senseHash: 1
			}
		};
		if (0 == d.length) for (var r = document.getElementsByTagName("script"), l = 0; l < r.length; l++) if ("undefined" !== typeof r[l].attributes.name && "MTAH5" == r[l].attributes.name.nodeValue) {
			d = [];
			d.push(r[l]);
			break
		}
		if (0 < d.length && ("undefined" !== typeof d[0].attributes.sid && (n.conf.sid = d[0].attributes.sid.nodeValue), "undefined" !== typeof d[0].attributes.cid && (n.conf.cid = d[0].attributes.cid.nodeValue), "object" === typeof _mtac)) for (var t in _mtac) _mtac.hasOwnProperty(t) && (n.conf[t] = _mtac[t]);
		return n
	}
})(this); (function(d, u) {
	function n(a) {
		a = window.localStorage ? localStorage.getItem(a) || sessionStorage.getItem(a) : (a = document.cookie.match(new RegExp("(?:^|;\\s)" + a + "=(.*?)(?:;\\s|$)"))) ? a[1] : "";
		return a
	}
	function r(a, b, f) {
		if (window.localStorage) f ? localStorage.setItem(a, b) : sessionStorage.setItem(a, b);
		else {
			var c = window.location.host,
			d = {
				"com.cn": 1,
				"js.cn": 1,
				"net.cn": 1,
				"gov.cn": 1,
				"com.hk": 1,
				"co.nz": 1
			},
			e = c.split(".");
			2 < e.length && (c = (d[e.slice( - 2).join(".")] ? e.slice( - 3) : e.slice( - 2)).join("."));
			document.cookie = a + "=" + b + ";path=/;domain=" + c + (f ? ";expires=" + f: "")
		}
	}
	function l(a) {
		var b, f, c, d = {};
		void 0 === a ? (c = window.location, a = c.host, b = c.pathname, f = c.search.substr(1), c = c.hash) : (c = a.match(/\w+:\/\/((?:[\w-]+\.)+\w+)(?:\:\d+)?(\/[^\?\\\"\'\|\:<>]*)?(?:\?([^\'\"\\<>#]*))?(?:#(\w+))?/i) || [], a = c[1], b = c[2], f = c[3], c = c[4]);
		void 0 !== c && (c = c.replace(/\"|\'|\<|\>/ig, "M"));
		if (f) for (var e = f.split("&"), h = 0, m = e.length; h < m; h++) if ( - 1 != e[h].indexOf("=")) {
			var k = e[h].indexOf("="),
			g = e[h].slice(0, k),
			k = e[h].slice(k + 1);
			d[g] = k
		}
		return {
			host: a,
			path: b,
			search: f,
			hash: c,
			param: d
		}
	}
	function t(a) {
		return (a || "") + Math.round(2147483647 * (Math.random() || .5)) * +new Date % 1E10
	}
	function v() {
		var a = l(),
		b = {
			dm: a.host,
			pvi: "",
			si: "",
			url: a.path,
			arg: encodeURIComponent(a.search || "").substr(0, 512),
			ty: 1
		};
		b.pvi = function() {
			var a = n("pgv_pvi");
			a || (b.ty = 0, a = t(), r("pgv_pvi", a, "Sun, 18 Jan 2038 00:00:00 GMT;"));
			return a
		} ();
		b.si = function() {
			var a = n("pgv_si");
			a || (a = t("s"), r("pgv_si", a));
			return a
		} ();
		b.url = function() {
			var b = a.path;
			d.senseHash && (b = a.hash ? b + a.hash.replace(/#/i, "_") : b);
			return b
		} ();
		return b
	}
	function x() {
		var a = l(document.referrer),
		b = l();
		return {
			rdm: a.host,
			rurl: a.path,
			rarg: encodeURIComponent(a.search || "").substr(0, 512),
			adt: b.param.ADTAG || b.param.adtag || b.param.CKTAG || b.param.cktag || b.param.PTAG || b.param.ptag
		}
	}
	function w() {
		try {
			var a = navigator,
			b = screen || {
				width: "",
				height: "",
				colorDepth: ""
			},
			d = {
				scr: b.width + "x" + b.height,
				scl: b.colorDepth + "-bit",
				lg: (a.language || a.userLanguage).toLowerCase(),
				tz: (new Date).getTimezoneOffset() / 60
			}
		} catch(c) {
			return {}
		}
		return d
	}
	function y(a) {
		a = a || {};
		for (var b in a) a.hasOwnProperty(b) && (d[b] = a[b]);
		if (d.sid) {
			a = [];
			for (var f = 0,
			c = [v(), x(), {
				r2: d.sid
			},
			w(), {
				random: +new Date
			}], l = c.length; f < l; f++) for (b in c[f]) c[f].hasOwnProperty(b) && a.push(b + "=" + (c[f][b] || ""));
			var e = function(a) {
				a = Ta.src = ("https:" == document.location.protocol ? "https://pingtas": "http://pingtcss") + ".qq.com/pingd?" + a.join("&");
				var b = new Image;
				Ta[d.sid] = b;
				b.onload = b.onerror = b.onabort = function() {
					b = b.onload = b.onerror = b.onabort = null;
					Ta[d.sid] = !0
				};
				b.src = a
			};
			e(a);
			d.performanceMonitor && (b = function() {
				var a;
				if (window.performance) {
					a = window.performance.timing;
					var b = {
						value: a.domainLookupEnd - a.domainLookupStart
					},
					c = {
						value: a.connectEnd - a.connectStart
					},
					g = {
						value: a.responseStart - (a.requestStart || a.responseStart + 1)
					},
					p = a.responseEnd - a.responseStart;
					a.domContentLoadedEventStart ? 0 > p && (p = 0) : p = -1;
					a = {
						domainLookupTime: b,
						connectTime: c,
						requestTime: g,
						resourcesLoadedTime: {
							value: p
						},
						domParsingTime: {
							value: a.domContentLoadedEventStart ? a.domInteractive - a.domLoading: -1
						},
						domContentLoadedTime: {
							value: a.domContentLoadedEventStart ? a.domContentLoadedEventStart - a.fetchStart: -1
						}
					}
				} else a = "";
				for (var b = [], c = [], g = 0, p = [v(), {
					r2: d.cid
				},
				w(), {
					random: +new Date
				}], f = p.length; g < f; g++) for (var q in p[g]) p[g].hasOwnProperty(q) && c.push(q + "=" + (p[g][q] || ""));
				for (q in a) a.hasOwnProperty(q) && ("domContentLoadedTime" == q ? c.push("r3=" + a[q].value) : b.push(a[q].value));
				c.push("ext=pfm=" + b.join("_"));
				e(c)
			},
			"undefined" !== typeof window.performance && "undefined" !== typeof window.performance.timing && 0 != window.performance.timing.loadEventEnd ? b() : window.attachEvent ? window.attachEvent("onload", b) : window.addEventListener && window.addEventListener("load", b, !1))
		}
	}
	u.MtaH5 = u.MtaH5 || {};
	u.Ta = u.Ta || {};
	MtaH5.pgv = y;
	Ta.clickStat = MtaH5.clickStat = function(a, b) {
		var f = MtaH5.hack ? MtaH5.hack() : "",
		c = {};
		f.conf &&
		function() {
			var a = f.conf,
			b;
			for (b in a) a.hasOwnProperty(b) && (c[b] = a[b])
		} ();
		if (c.cid) {
			var l = [],
			e = v(),
			h = {
				r2: d.sid
			};
			e.dm = "taclick";
			e.url = a;
			h.r2 = c.cid;
			h.r5 = function(a) {
				a = "undefined" === typeof a ? {}: a;
				var b = [],
				c;
				for (c in a) a.hasOwnProperty(c) && b.push(c + "=" + a[c]);
				return b.join(";")
			} (b);
			for (var m = 0,
			e = [e, x(), h, w(), {
				random: +new Date
			}], h = e.length; m < h; m++) for (var k in e[m]) e[m].hasOwnProperty(k) && l.push(k + "=" + (e[m][k] || ""));
			var l = MtaH5.src = ("https:" == document.location.protocol ? "https://pingtas": "http://pingtcss") + ".qq.com/pingd?" + l.join("&"),
			g = new Image;
			MtaH5["click_" + c.sid] = g;
			g.onload = g.onerror = g.onabort = function() {
				g = g.onload = g.onerror = g.onabort = null;
				MtaH5[c.sid] = !0
			};
			g.src = l
		}
	};
	Ta.clickShare = MtaH5.clickShare = function(a) {
		var b = MtaH5.hack ? MtaH5.hack() : "",
		f = {},
		c = l(),
		c = c.param.CKTAG || c.param.ckatg,
		n = "undefined" === typeof c ? [] : c.split(".");
		b.conf &&
		function() {
			var a = b.conf,
			c;
			for (c in a) a.hasOwnProperty(c) && (f[c] = a[c])
		} ();
		if (f.cid) {
			var c = [],
			e = v(),
			h = {
				r2: d.sid
			};
			e.dm = "taclick_share";
			e.url = "mtah5-share-" + a;
			h.r2 = f.cid;
			h.r5 = function(a, b) {
				var c = [];
				2 === a.length && a[0] == b && c.push(a[0] + "=" + a[1]);
				return c.join(";")
			} (n, "mtah5_share");
			a = 0;
			e = [e, x(), h, w(), {
				random: +new Date
			}];
			for (h = e.length; a < h; a++) for (var m in e[a]) e[a].hasOwnProperty(m) && c.push(m + "=" + (e[a][m] || ""));
			m = MtaH5.src = ("https:" == document.location.protocol ? "https://pingtas": "http://pingtcss") + ".qq.com/pingd?" + c.join("&");
			var k = new Image;
			MtaH5["click_" + f.sid] = k;
			k.onload = k.onerror = k.onabort = function() {
				k = k.onload = k.onerror = k.onabort = null;
				MtaH5[f.sid] = !0
			};
			k.src = m
		}
	};
	var z = MtaH5.hack ? MtaH5.hack() : {};
	z.conf &&
	function() {
		var a = z.conf,
		b;
		for (b in a) a.hasOwnProperty(b) && (d[b] = a[b])
	} ();
	d.autoReport && y()
})({},
this);