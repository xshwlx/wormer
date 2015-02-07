define([], function () {

});

var syncPoint = new Date().format("yyyy-MM-dd hh:mm:ss");
var subscriber = "CLIMessage";
var cliLogHistory = [];

function filterLog(msgs) {
    if (msgs != null) {
        msgs.sort(function (a, b) {
            return a.msgId > b.msgId ? 1 : -1
        });
        var length = cliLogHistory.length;
        for (var i = 0; i < msgs.length; i++) {
            if (msgs[i].type == 'CLIMessage' || msgs[i].subscriber == 'CLIMessage') {
                cliLogHistory.push(msgs[i]);
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
    cliLogHistory.push(message)
    logHistory.push(msgs[i]);
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
            cliLogHistory = tempArray;
            logHistory.push(msgs[i]);
            break;
        } else {
            if (msgs[i].msgInfo && msgs[i].msgInfo != "") {
                str = "[" + msgs[i].msgTime + "] [user=" + msgs[i].user + "] " + msgs[i].msgInfo.replace(/\n/g, "<br/>") + "<br>" + str;
            }
            count++;
        }

    }
    return str;
}

function clear() {
    cliLogHistory = [];
    logHistory = [];
}