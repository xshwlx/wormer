define(
['jquery', ], function($) {
	(function(paasController) {
		/**
		 * @ author:wangzhhr
		 * paasController.linkToPage 将某个组件的页面复用 
		 * _pthis: 父页面this
		 * pageId: 页面id，移除页面时需要使用
		 * pageFileName: 弹出页面的模块名，用于加载弹出页面的多语
		 * pagePath: 页面的路径
		 * srcPage: 页面的返回路径
		 * params: 页面需要的参数
		 * callback: 回调
		 * option: 
		 */
		paasController.linkToPage = function(_this, pageId,
			pageFileName, pagePath, srcPage, params, callback, option) {
			_this.isCurrentPage = false;
			$('#' + pageId).remove();
			_this.addLinkPage(pageFileName);
			require([pagePath + "init"], function(pageObj) {
				pageObj.view.isCurrentPage = true;
				pageObj.view.curPagePath = pagePath;
				pageObj.view.linkPageRender(pagePath + 'init.html',
					option);
				_this.stopListening(pageObj.view.model);
				pageObj.view.model.set({
					'linkPageParams': params,
					'srcPage': srcPage
				});
				if (callback && (typeof callback == "function")) {
					callback();
				}
			}, function(err) {
				console.log("err:" + err);
			});
		};
		paasController.linkToDiffNavPage = function(_pthis, srcPage,
			params, returnMethod) {
			var pageId = 'taskListContent';
			var pageFileName = 'taskManagement';
			var pagePath = 'modules/pages/systemMonitor/taskManagement/';
			paasController
				.linkToTaskPageAppend(_pthis, pageId, pageFileName, pagePath,
				srcPage, params, null, null, returnMethod)
		};

		paasController.linkToTaskPageAppend = function(_this, pageId,
			pageFileName, pagePath, srcPage, params, callback,
			option, returnMethod) {
			_this.isCurrentPage = false;
			$('#' + pageId).remove();
			_this.addLinkPage(pageFileName);
			require([pagePath + "init"], function(pageObj) {
				pageObj.view.isCurrentPage = true;
				pageObj.view.curPagePath = pagePath;
				pageObj.view.linkPageAppendRender(pagePath + 'init.html', option);
				_this.stopListening(pageObj.view.model);
				pageObj.view.model.set({
					'linkPageParams': params,
					'srcPage': srcPage,
					'returnMethod': returnMethod
				});
				if (callback && (typeof callback == "function")) {
					callback();
				}
			}, function(err) {
				console.log("err:" + err);
			});
		};
		/**
		 * @ author:wangzhhr
		 * paasController.linkToPageAppend 将某个组件的页面append到当前页面init.html的最后 
		 * _pthis: 父页面this
		 * pageId: 页面id，移除页面时需要使用
		 * pageFileName: 弹出页面的模块名，用于加载弹出页面的多语
		 * pagePath: 页面的路径
		 * params: 页面需要的参数
		 * callback: 回调
		 * option: 
		 */
		paasController.linkToPageAppend = function(_this, pageId, pageFileName, pagePath, params, callback, option) {
			$('#' + pageId).remove();
			_this.addLinkPage(pageFileName);
			require([pagePath + "init"], function(pageObj) {
				pageObj.view.isCurrentPage = true;
				pageObj.view.curPagePath = pagePath;
				pageObj.view.parentPageView = _this;
				pageObj.view.linkPageAppendRender(pagePath + 'init.html', option);
				_this.stopListening(pageObj.view.model);
				pageObj.view.model.set({
					'linkPageParams': params
				});
				if (callback && (typeof callback == "function")) {
					callback();
				}
			}, function(err) {
				console.log("err:" + err);
			});
		};
		/**
		 *link to page 应用仓库子页面
		 *_pthis父页面this指针
		 *params：参数
		 *callback：回调
		 */
		paasController.linkToAppDetailPage = function(_pthis, params, callback) {
			var pageId = 'offeringApplicationDetail';
			var pageFileName = 'applicationRepo';
			var pagePath = 'modules/pages/applicationManagement/applicationRepo/appdetail/';
			paasController.linkToPageAppend(_pthis, pageId,
				pageFileName, pagePath, params, callback)
		};

		/**
		 *link to page WAS 中间件子页面
		 *_pthis父页面this指针
		 *params：参数
		 *callback：回调
		 */
		paasController.linkToMWwasPage = function(_pthis, params, callback) {
			var pageId = 'offeringMiddlewareDetail';
			var pageFileName = 'middleware';
			var pagePath = 'modules/pages/serverManagement/middleware/wasdetail/';
			paasController.linkToPageAppend(_pthis, pageId,
				pageFileName, pagePath, params, callback)
		};
		/**
		 *link to page weblogic 中间件子页面
		 *_pthis父页面this指针
		 *params：参数
		 *callback：回调
		 */
		paasController.linkToMWwlsPage = function(_pthis, params, callback) {
			var pageId = 'offeringMiddlewareDetail';
			var pageFileName = 'middleware';
			var pagePath = 'modules/pages/serverManagement/middleware/wlsdetail/';
			paasController.linkToPageAppend(_pthis, pageId,
				pageFileName, pagePath, params, callback)
		};
		/**
		 *link to page UAP 中间件子页面
		 *_pthis父页面this指针
		 *params：参数
		 *callback：回调
		 */
		paasController.linkToMWUAPPage = function(_pthis, params, callback) {
			var pageId = 'offeringMiddlewareDetail';
			var pageFileName = 'middleware';
			var pagePath = 'modules/pages/serverManagement/middleware/uapdetail/';
			paasController.linkToPageAppend(_pthis, pageId,
				pageFileName, pagePath, params, callback)
		};
		/**
		 *link to page webserver 服务器子页面
		 *_pthis父页面this指针
		 *params：参数
		 *callback：回调
		 */
		paasController.linkToWebserverPage = function(_pthis, params, callback) {
			var pageId = 'offeringWebServerDetail';
			var pageFileName = 'webServer';
			var pagePath = 'modules/pages/serverManagement/webServer/wsdetail/';
			paasController.linkToPageAppend(_pthis, pageId,
				pageFileName, pagePath, params, callback)
		};
		/**
		 *link to page WAS 管理服务器子页面
		 *_pthis父页面this指针
		 *params：参数
		 *callback：回调
		 */
		paasController.linkToMSWasPage = function(_pthis, params, callback) {
			var pageId = 'offeringDmgrDetail';
			var pageFileName = 'managerServer';
			var pagePath = 'modules/pages/serverManagement/managerServer/wasdetail/';
			paasController.linkToPageAppend(_pthis, pageId,
				pageFileName, pagePath, params, callback)
		};
		/**
		 *link to page Weblogic 管理服务器子页面
		 *_pthis父页面this指针
		 *params：参数
		 *callback：回调
		 */
		paasController.linkToMSWlsPage = function(_pthis, params, callback) {
			var pageId = 'offeringDmgrDetail';
			var pageFileName = 'managerServer';
			var pagePath = 'modules/pages/serverManagement/managerServer/wlsdetail/';
			paasController.linkToPageAppend(_pthis, pageId,
				pageFileName, pagePath, params, callback)
		};
		/**
		 *link to page DB 数据源子页面
		 *_pthis父页面this指针
		 *params：参数
		 *callback：回调
		 */
		paasController.linkToDBPage = function(_pthis, params, callback) {
			var pageId = 'offeringDBDetail';
			var pageFileName = 'dataSourceInfo';
			var pagePath = 'modules/pages/serverManagement/dataSourceInfo/detail/';
			paasController.linkToPageAppend(_pthis, pageId,
				pageFileName, pagePath, params, callback)
		};

		/**
		 *linkToAppDeployPage:link to page Application deploy 连接到部署页签
		 *_pthis父页面this指针
		 *params：参数
		 *callback：回调
		 */
		paasController.linkToAppDeployPage = function(_pthis, params, callback) {
			var pageId = 'uapBuildInitContent';
			var pageFileName = 'appDeployment';
			var pagePath = 'modules/pages/uapsysconfig/appDeployment/';
			paasController.linkToPageAppend(_pthis, pageId,
				pageFileName, pagePath, params, callback)
		};

	})(window.paasController ? window.paasController : window.paasController = {});
});