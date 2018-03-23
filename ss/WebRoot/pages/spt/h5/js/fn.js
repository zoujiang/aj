//root font-size
(function (doc, win) {
	"use strict";
	var docEl = doc.documentElement,
		resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
		recalc = function () {
			var clientWidth = docEl.clientWidth;
			var htmlFontSize=100;//��ʼ��С
			var designWidth=640;//��Ƹ���
			if (!clientWidth) return;
			docEl.style.fontSize = htmlFontSize * (clientWidth / designWidth) + 'px';
			var  reality = Number(docEl.style.fontSize.substr(0,docEl.style.fontSize.length-2));
			var theory = htmlFontSize * (clientWidth / designWidth);
			 //alert(reality);
			// alert(theory);
			if(reality!=theory){
				docEl.style.fontSize=htmlFontSize *theory/reality*(clientWidth/ designWidth) + 'px';
			}
		};
	if (!doc.addEventListener) return;


	win.addEventListener(resizeEvt, recalc, false);
	doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);