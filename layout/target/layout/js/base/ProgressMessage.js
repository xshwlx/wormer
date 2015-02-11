define([], function () {

});
var subscriber = "ProgressMessage";
var processMsg = [];

function filterProgress(msgs) {
    if (msgs != null) {
        msgs.sort(function (a, b) {
            return a.msgId > b.msgId ? 1 : -1
        });
        var length = processMsg.length;
        for (var i = 0; i < msgs.length; i++) {
            if (msgs[i].type == 'ProgressMessage' || msgs[i].subscriber == 'ProgressMessage') {
                processMsg[0] = (msgs[i]);
            }
        }
    }
}

function doFilter(message) {
    processMsg.push(message)
}

function clear() {
    processMsg = [];
}