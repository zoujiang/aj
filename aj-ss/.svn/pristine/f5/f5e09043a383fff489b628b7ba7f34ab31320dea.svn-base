$.validator.addMethod("account", function(value, element){
	var acc = /^[\w]+$/;
	return this.optional(element) || (acc.test(value));
}, "请输入不小于 {0}的由英文字母、数字及下划线组成的字符串");

$.validator.addMethod("password", function(value, element){
	var pwd = /^[\w]+$/;///^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/;
	return this.optional(element) || (pwd.test(value));
}, "请输入不小于 {0}的由英文字母、数字及下划线以及-_~!@#$%^&*.()[]{}<>?\/'\"组成的字符串");