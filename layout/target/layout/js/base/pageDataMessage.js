define([], function () {

});

var syncPoint = new Date().format("yyyy-MM-dd hh:mm:ss");
var subscriber = "PageDataMessage";
var logHistory = [];

function filterLog(msgs) {
    if (msgs != null) {
        msgs.sort(function (a, b) {
            return a.msgId > b.msgId ? 1 : -1
        });
        var length = logHistory.length;
        for (var i = 0; i < msgs.length; i++) {
            if (msgs[i].type == 'PageDataMessage' || msgs[i].subscriber == 'PageDataMessage') {
                logHistory.push(msgs[i]);
                if (msgs[i].needAlert) {
                    coord.alert(msgs[i].msgInfo, {
                        'log': 1
                    });
                }
            }
        }
    }
}

function doFilter(message) {
    logHistory.push(message)
}

function genString(msgs) {
    var length = msgs.length;
    var count = 0;
    var str = "";
    var tempArray = [];
    for (var i = length - 1; i >= 0; i--) {
        if (typeof msgs[i].detailType == 'string') {
            msgs[i].detailType = 2;
        }
        if (count > max) {
            tempArray = msgs.slice(i, length);
            logHistory = tempArray;
            break;
        } else {
            if (msgs[i].msgInfo && msgs[i].msgInfo != "") {
                str = "[" + msgs[i].msgTime + "] [user=" + msgs[i].user + "] " + msgs[i].msgInfo + "<br>" + str;
            }
            count++;
        }

    }
    return str;
}

function clear() {
    logHistory = [];
}