define(['jquery', 
	'text!modules/pages/commonconst/searchModal/searchServersModal/searchCount.html',
	], function($, SearchCountItem) {
	(function(paasController) {
		/**
		 * paasController.selectSearchModal 选择弹出框公共方法
		 * _pthis: 父页面this
		 * modalId：弹出modal的id
		 * modalFileName：弹出页面的module name，用于加载弹出页面的多语
		 * modalPath: 弹出页面的module的path（路径）
		 * 'modalParams':传递给弹出页面的参数的属性名称
		 * params: 传递给弹出页面的参数,数组格式
		 * modalChanged ：父页面监听弹出框页面的变化的属性名称
		 * callback 回调函数，用于处理弹出页面的返回值
		 * option:是否在一个页面弹出多个modal
		 * @ author:wangzhhr
		 **/
		paasController.selectSearchModal = function(_pthis, modalId, modalFileName, modalPath, htmlFileName, params, modalChanged, callback, option) {
			$('#' + modalId).remove();
			_pthis.addModal(modalFileName);
			var _this = _pthis;
			require([modalPath + "init"], function(pageObj) {
				pageObj.view.isCurrentPage = true;
				pageObj.view.curPagePath = modalPath;
				pageObj.view.modalRender(modalPath + htmlFileName + '.html',option);
				_this.stopListening(pageObj.view.model);
				pageObj.view.model.set(
					modalChanged, ''
				);
				pageObj.view.model.set({
					'modalParams': params
				});
				_this.listenTo(pageObj.view.model, 'change', function() {
					console.log(pageObj.view.model.changed);
					if (pageObj.view.model.changed[modalChanged]) {
						_this.stopListening(pageObj.view.model);
						if (callback && (typeof callback == "function")) {
							callback(pageObj.view.model.changed);
						}
					}
				});
			}, function(err) {
				console.log("err:" + err);
			});
		};
		
		/**
		 * paasController.searchServerModal 中间件高级查询弹出框
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.searchServerModal = function(_pthis,htmlFileName, params, callback) {
			var modalId = 'searchServersModal'; //modalId: 弹出modal的id
			var modalFileName = 'searchServersModal'; //modalFileName: 弹出页面的模块名称用于加载多语文件使用
			var modalPath = 'modules/pages/commonconst/searchModal/searchServersModal/'; // modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'searchParamVO'; //监听属性的名称
			paasController.selectSearchModal(_pthis, modalId, modalFileName, modalPath, htmlFileName, params, modalChanged, callback)
		};

		paasController.initSearchPagination = function(condition, dataArray, fileNmae, css){
			var searchInfoObj = {};
			searchInfoObj.searchCondition = "";
			searchInfoObj.searchResult =dataArray.length;
			for(item in condition){
				if (condition[item] !="") {
					searchInfoObj.searchCondition += coord.lang[fileNmae][item] + "=" + condition[item] +"; ";
				}
			}
			var temp = Backbone.language(SearchCountItem, coord);
			temp = Backbone.template(temp, searchInfoObj);
			$(css).html(temp);
		};

		
	
	})(window.paasController ? window.paasController : window.paasController = {});
});