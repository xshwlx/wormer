define(['jquery', ], function($) {
	(function(paasController) {

		paasController.cloneObj = function(obj) {
			if (!obj) {
				return obj;
			}
			var o = obj.constructor === Array ? [] : {};
			for (var i in obj) {
				if (obj.hasOwnProperty(i)) {
					o[i] = typeof obj[i] === "object" ? paasController.cloneObj(obj[i]) : obj[i];
				}
			}
			return o;
		};

		/**
		 * 判断按钮是否可用，不可用返回true
		 */
		paasController.isBtnDisabled = function(e) {
			var target = $(e.target);
			if (target.context.localName != 'a') {
				target = target.closest('a');
			}
			if (target.hasClass('disabled')) {
				return true;
			} else if (target.attr('disabled')) {
				return true;
			}else{
				return false;
			}
		};
		/**
		*判断必填字段是否为空
		*/
		paasController.validInputParams = function(obj, strings){
			var paramsWarm ="";
			for (var i = 0; i < strings.length; i++) {
				if (!obj[strings[i]]) {
					paramsWarm +=strings[i]+" ";
				}
			}
			if (paramsWarm !="") {
				coord.alert(coord.lang.validate.notNull+":"+paramsWarm,{"failed":true});
				return false;
			}
			return true;
		};
		/**
		*判断必填字段是否为空
		*/
		paasController.validInputParamsDesc = function(obj, strings,titles){
			var paramsWarm ="";
			for (var i = 0; i < strings.length; i++) {
				if (!obj[strings[i]]) {
					paramsWarm +=titles[i]+" ";
				}
			}
			if (paramsWarm !="") {
				coord.alert(paramsWarm+" "+coord.lang.validate.notNull,{"failed":true});
				return false;
			}
			return true;
		};

	})(window.paasController ? window.paasController : window.paasController = {});
});