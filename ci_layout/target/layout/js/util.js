(function (coord) {

    coord.controller = "/mainController";
    coord.busy = false;


    coord.queryAsyncJobResultInterval = 3000;
    String.prototype.StartsWith = function (str) {
        return this.substr(0, str.length) == str;
    }
    String.prototype.EndsWith = function (str) {
        return this.substr(this.length - str.length) == str;
    }
    String.prototype.Trim = function () {
        return this.replace(/(^\s*)|(\s*$)/g, "");
    }
    $.extend({
        getUrlVars: function () {
            var vars = [], hash;
            var signIndex = window.location.href.indexOf('#'), hashes;
            var startIndex = window.location.href.indexOf('?') + 1;
            (signIndex == -1) ? hashes = window.location.href.slice(startIndex).split('&') : hashes = window.location.href.slice(startIndex, signIndex).split('&');
            for (var i = 0; i < hashes.length; i++) {
                hash = hashes[i].split('=');
                vars.push(hash[0]);
                vars[hash[0]] = hash[1];
            }
            return vars;
        },
        getUrlVar: function (name) {
            return $.getUrlVars()[name];
        }
    });

    coord.autoblockUI = function (msg) {
        coord.busy = true;
        if (msg) {
            messageHtml = '<div><img src="css/images/loading.gif"><br/><p>' + msg + '</p></div>';
        } else {
            messageHtml = '<div><img src="css/images/loading.gif">';
        }
        $.blockUI({
            message: messageHtml,
            css: {
                color: '#FFFFFF'
            }
        });
        $('.blockOverlay').attr('title', 'Click to unblock').click(coord.unblockUI);

    }
    coord.blockUI = function (msg) {
        coord.busy = true;
        if (msg) {
            messageHtml = '<div><img src="css/images/loading.gif"><br/><p>' + msg + '</p></div>';
        } else {
            messageHtml = '<div><img src="css/images/loading.gif">';
        }
        $.blockUI({
            message: messageHtml,
            css: {
                color: '#FFFFFF'
            }
        });
    }
    coord.unblockUI = function () {
        $.unblockUI();
        coord.busy = false;
    }
    coord.convertBytes = function (bytes) {
        if (bytes == null || bytes == 0) {
            return "";
        } else if (bytes < 1024 * 1024) {
            return (bytes / 1024).toFixed(2) + " KB";
        } else if (bytes < 1024 * 1024 * 1024) {
            return (bytes / 1024 / 1024).toFixed(2) + " MB";
        } else if (bytes < 1024 * 1024 * 1024 * 1024) {
            return (bytes / 1024 / 1024 / 1024).toFixed(2) + " GB";
        } else {
            return (bytes / 1024 / 1024 / 1024 / 1024).toFixed(2) + " TB";
        }
    }
    coord.convertHz = function (hz) {
        if (hz == null) return "";
        if (hz < 1000) {
            return hz + " MHz";
        } else {
            return (hz / 1000).toFixed(2) + " GHz";
        }
    }
    coord.toLocalDate = function (UtcDate) {
        var localDate = "";
        if (UtcDate != null && UtcDate.length > 0) {
            var disconnected = new Date(UtcDate);
            disconnected.setISO8601(UtcDate);
            localDate = disconnected.getTimePlusTimezoneOffset(8);
        }
        return localDate;
    }
    coord.adjustDiv = function () {
        if ($('#vrouterdetail').length == 0) {
            if ($(window).width() > 979) {
                var height = $(window).height() - 330;
                if (height > 265) {
                    $('.cloud-content-div').css('min-height', height);
                } else {
                    $('.cloud-content-div').css('min-height', 265);
                }

            } else {
                $('.cloud-content-div').css('min-height', 1);
            }
        }
        if ($('#lbvmlistdig').length) {
            var winWidth = $(window).width();
            var lbwidth = $('#lbvmlistdig').width();
            var conWidth = $('#mainContent').width();
            var marginLeft = conWidth - 30 - lbwidth / 2 - winWidth * 0.48;
            $('#lbvmlistdig').css('margin-left', marginLeft);
        }
        if ($('#configstickdig').length) {
            var winWidth = $(window).width();
            var lbwidth = $('#configstickdig').width();
            var conWidth = $('#mainContent').width();
            var marginLeft = conWidth - 30 - lbwidth / 2 - winWidth * 0.48;
            $('#configstickdig').css('margin-left', marginLeft);
        }
    }
    coord.tag = {trHtml: "<tr><td><span class='key'>{{key}}</span></td><td><span class='value'>{{value}}</span><i class='icon-remove'></i></td></tr>"};
})(window.coord ? window.coord : window.coord = {});