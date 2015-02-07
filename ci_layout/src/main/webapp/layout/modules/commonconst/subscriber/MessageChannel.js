define([], function() {
	(function(paasController) {
		paasController.createMessageChannel = function(subscriber) {
			var fetch = {};
			var curtime = new Date();
			fetch.syncPoint = curtime
				.format("yyyy-MM-dd hh:mm:ss");
			fetch.timeOut = 300 * 1000;
			fetch.sessionID = coord.context
				.getUser('sessionkey');
			fetch.subscriber = subscriber;
			paasController.remoteSync(fetch);
		};
		paasController.remoteSync = function(_this,fetch) {
			_this.isCurrentPage = true;
			var jsonString = JSON.stringify(fetch);
			Backbone.sync('syncmessage', jsonString, true, function(json) {
				if (json.code == Constants.RESPONSE_CODE_SUCCESS) {
					var fetchObj = json.data;
					_this.onCCMessage(fetchObj);
					fetch.sessionID = coord.context.getUser('sessionkey');
					fetch.syncPoint = fetchObj.syncPoint;
					fetch.filterIds = fetchObj.filterIds;
					paasController.remoteSync(fetch);	
				} else {
					console.log(json);
					paasController.remoteSync();
				}
			}, function(json) {
				console.log(json);
				paasController.createMessageChannel(fetch.subscriber);
			});
		};

	})(window.paasController ? window.paasController : window.paasController = {});
});