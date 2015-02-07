define(['jquery', ], function($) {
	(function(paasController) {
		paasController.backToAppSystemPage = function(_this,paramVO){
			var sysconfigBrdcrbstring ='/';
			for (var i = 2; i < paramVO.brdcrbarray.length; i++) {
				sysconfigBrdcrbstring += paramVO.brdcrbarray[i].module+'/';
			};
			sysconfigBrdcrbstring = sysconfigBrdcrbstring.slice(0, sysconfigBrdcrbstring.length - 1);
			_this.toPage('','/applicationManagement/uapApplication');
			_this.toPage(paramVO.vsName,sysconfigBrdcrbstring,{"code":paramVO.vsCode});	
		};

		paasController.sysconfigLeavePage = function(_this){
			
			
		};

	})(window.paasController ? window.paasController : window.paasController = {});
});