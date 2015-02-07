define(['jquery', ], function($) {
	(function(paasController) {
		/**
		 * paasController.selectCommonModal 选择弹出框公共方法
		 * _pthis: 父页面this
		 * modalId：弹出modal的id
		 * modalFileName：弹出页面的module name，用于加载弹出页面的多语
		 * modalPath: 弹出页面的module的path（路径）
		 * 'modalParams':传递给弹出页面的参数的属性名称
		 * params: 传递给弹出页面的参数,数组格式
		 * modalChanged：父页面监听弹出框页面的变化的属性名称
		 * callback 回调函数，用于处理弹出页面的返回值
		 * option:是否在一个页面弹出多个modal
		 * @ author:wangzhhr
		 **/
		paasController.selectCommonModal = function(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback, option) {
			$('#' + modalId).remove();
			_pthis.addModal(modalFileName);
			var _this = _pthis;
			require([modalPath + "init"], function(pageObj) {
				pageObj.view.isCurrentPage = true;
				pageObj.view.curPagePath = modalPath;
				pageObj.view.modalRender(modalPath + 'init.html',option);
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
		 * paasController.taskInfoModal 任务信息
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:dingtsh
		 **/
		paasController.taskInfoModal = function(_pthis, params, callback) {
			var modalId = 'taskInfoModal'; //modalId: 弹出modal的id
			var modalFileName = 'taskInfoModal'; //modalFileName: 弹出页面的模块名称用于加载多语文件使用
			var modalPath = 'modules/pages/commonconst/commonModal/taskInfoModal/'; // modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'modalParamVO'; //监听属性的名称
			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};
		
		/**
		 * paasController.selectWasProfileModal 选择已有的概要文件
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.selectWasProfileModal = function(_pthis, params, callback) {
			var modalId = 'profileModal'; //modalId: 弹出modal的id
			var modalFileName = 'wasProfileModal'; //modalFileName: 弹出页面的模块名称用于加载多语文件使用
			var modalPath = 'modules/pages/commonconst/commonModal/wasProfileModal/'; // modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'modalParamVO'; //监听属性的名称
			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};
		/**
		 * paasController.selectSoftwareMachineModal 选择已安装软件，获得软件路径和安装的节点
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.selectSoftwareMachineModal = function(_pthis, params, callback) {
			var modalId = 'swByTypeModal'; //modalId: 弹出modal的id
			var modalFileName = 'softwareModal'; //modalFileName: 弹出页面的module name,加载多语资源使用
			var modalPath = 'modules/pages/commonconst/commonModal/softwareModal/'; //modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'softwareParamVO'; //监听属性的名称
			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};
		/**
		 * paasController.selectRemoteFilePathModal 选择远程节点的路径
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数,数组格式
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.selectRemoteFilePathModal = function(_pthis, params, callback,option) {
			var modalId = 'remoteFileModal'; //modalId: 弹出modal的id
			var modalFileName = 'remoteFileModal'; //modalFileName: 弹出页面的module name
			var modalPath = 'modules/pages/commonconst/commonModal/remoteFileModal/'; //modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'remoteFilePath'; //监听属性的名称
			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback,option)
		};
		/**
		 * paasController.selectInstallableSWModal 选择可安装的软件
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数,数组格式
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.selectInstallableSWModal = function(_pthis, params, callback) {
			var modalId = 'swModal'; //modalId: 弹出modal的id
			var modalFileName = 'installableSWModal'; //modalFileName: 弹出页面的module name
			var modalPath = 'modules/pages/commonconst/commonModal/installableSWModal/'; //modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'installswarray'; //监听属性的名称
			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};
		/**
		 * paasController.selectInstallableSWModal 选择可安装的软件
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数,数组格式
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.selectInstallableAgentModal = function(_pthis, params, callback) {
			var modalId = 'agentModal'; //modalId: 弹出modal的id
			var modalFileName = 'installableAgentModal'; //modalFileName: 弹出页面的module name
			var modalPath = 'modules/pages/commonconst/commonModal/installableAgentModal/'; //modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'installswarray'; //监听属性的名称
			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};
		/**
		 * paasController.selectServersModal 选择服务器弹出框
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数,数组格式
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.selectServersModal = function(_pthis, params, callback) {
			var modalId = 'serverModal'; //modalId: 弹出modal的id
			var modalFileName = 'serversModal'; //modalFileName: 弹出页面的module name
			var modalPath = 'modules/pages/commonconst/commonModal/serversModal/'; //modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'modalParamArray'; //监听属性的名称
			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};

		/**
		 * paasController.selectDBServersModal 选择数据源服务器弹出框
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数,数组格式
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.selectDBServersModal = function(_pthis, params, callback) {
			var modalId = 'serverModal'; //modalId: 弹出modal的id
			var modalFileName = 'serversModal'; //modalFileName: 弹出页面的module name
			var modalPath = 'modules/pages/commonconst/commonModal/serversModal/'; //modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'modalParamVO'; //监听属性的名称
			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};
		/**
		 * paasController.selectApplicationModal 选择应用弹出框
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数,字符串
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.selectApplicationModal = function(_pthis, params, callback) {
			var modalId = 'appModal'; //modalId: 弹出modal的id
			var modalFileName = 'applicationModal'; //modalFileName: 弹出页面的module name
			var modalPath = 'modules/pages/commonconst/commonModal/applicationModal/'; //modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'applicationParamVO'; //监听属性的名称
			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};
		/**
		 * paasController.selectAppTemplateModal 选择应用模版弹出框
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数,字符串
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.selectAppTemplateModal = function(_pthis, params, callback) {
			var modalId = 'appTemplateModal'; //modalId: 弹出modal的id
			var modalFileName = 'appTemplateModal'; //modalFileName: 弹出页面的module name
			var modalPath = 'modules/pages/commonconst/commonModal/appTemplateModal/'; //modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'appTemplateParamVO'; //监听属性的名称
			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};
		/**
		 * paasController.selectAppTemplateModal 选择应用模版弹出框
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数,字符串
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.selectMachineListModal = function(_pthis, params, callback) {
			var modalId = 'machinelistModal'; //modalId: 弹出modal的id
			var modalFileName = 'computerListModal'; //modalFileName: 弹出页面的module name
			var modalPath = 'modules/pages/commonconst/commonModal/computerListModal/'; //modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'machineArray'; //监听属性的名称
			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};
		/**
		 * paasController.selectRenterModal 选择应用模版弹出框
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数,字符串
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.selectRenterModal = function(_pthis, params, callback) {
			var modalId = 'renterlistModal'; //modalId: 弹出modal的id
			var modalFileName = 'renterAccountModal'; //modalFileName: 弹出页面的module name
			var modalPath = 'modules/pages/commonconst/commonModal/renterAccountModal/'; //modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'renterParamVO'; //监听属性的名称
				paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};
		
		paasController.uploadModal = function(_pthis, params, callback) {
			var modalId = 'uploadModal'; //modalId: 弹出modal的id
			var modalFileName = 'uploadModal'; //modalFileName: 弹出页面的module name
			var modalPath = 'modules/pages/commonconst/commonModal/uploadModal/'; //modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'modalParamVO'; //监听属性的名称

			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};

		/**
		 * paasController.selectSystemModal 选择已有的应用系统
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数,数组格式
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.selectSystemModal = function(_pthis, params, callback) {
			var modalId = 'systemModal'; //modalId: 弹出modal的id
			var modalFileName = 'systemListModal'; //modalFileName: 弹出页面的module name
			var modalPath = 'modules/pages/commonconst/commonModal/systemListModal/'; //modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'sysArray'; //监听属性的名称
			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};
		
		/**
		 * paasController.selectSystemModal 选择已有的补丁
		 * _pthis: 父页面this
		 * params: 传递给弹出页面的参数,数组格式
		 * callback 回调函数，用于处理弹出页面的返回值
		 * @ author:wangzhhr
		 **/
		paasController.selectPatchModal = function(_pthis, params, callback) {
			var modalId = 'patchModal'; //modalId: 弹出modal的id
			var modalFileName = 'patchModal'; //modalFileName: 弹出页面的module name
			var modalPath = 'modules/pages/commonconst/commonModal/patchModal/'; //modalPath: 弹出页面的module的path（路径）
			var modalChanged = 'patchArray'; //监听属性的名称
			paasController.selectCommonModal(_pthis, modalId, modalFileName, modalPath, params, modalChanged, callback)
		};
		
		/**
		*paasController.modalDrag 点击弹出框的header footer实现弹出框的移动
		*modalId：弹出框modal的id
		*/
		paasController.modalDrag = function(event, modalId) {
			var isMove = true;
			var left = $('#myModalSample #'+modalId).offset().left;
			var top = $('#myModalSample #'+modalId).offset().top;
			var abs_x = event.pageX - left;
			var abs_y = event.pageY - top;
			$(document).mousemove(function(event) {
				if (isMove) {
					var obj = $('#myModalSample #'+modalId);
					obj.css({
						'left': event.pageX - abs_x,
						'top': event.pageY - abs_y,
						'margin-left': 0,
						'margin-top': 0
					});
				}
			})
				.mouseup(function() {
				isMove = false;
			});
		};
		/**
		*paasController.selectRow 单击modal行选中一行信息
		*modalRow：行选择器选择出来的jquery对象
		*/
		paasController.selectRow = function(e) {
			var target = $(e.target);
			if (target.context.localName == 'input' || target.context.localName == 'img') {
				return;
			}
			var $row = target.closest('.row');
			if ($row.find('input[type=radio]').length !=0) {
				if ($row.find('input[type=radio]:checked').length == 0) {
					$row.find('input[type=radio]').attr('checked','checked')
				}else{
					$row.find('input[type=radio]').removeAttr('checked')
				}
			}else if ($row.find('input[type=checkbox]').length !=0) {
				if ($row.find('input[type=checkbox]:checked').length == 0) {
					$row.find('input[type=checkbox]').attr('checked','checked')
				}else{
					$row.find('input[type=checkbox]').removeAttr('checked')
				}
			}

		};

		
	})(window.paasController ? window.paasController : window.paasController = {});
});
