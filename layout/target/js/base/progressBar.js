define(['base', 'text!layout/progressBar.html'],

    function (Base, progressHtml) {
        (function (coord) {
            coord.progressHtml = progressHtml;
            coord.progressBar = function (persent, msg) {
                if (!msg) {
                    msg = 'initializing....';
                }
                if (!persent) {
                    persent = 0;
                }
                $('.cloud-progressBar').append(coord.progressHtml);
                $('#progressMsg').html(msg);
                $('#progbar').progressbar({
                    value: persent
                });
            };
            coord.dyprogressBar = function (subscriber) {
                var count = 0;
                var complete = false;
                var installing = false;
                init(subscriber);

                function init(sub) {
                    $('.progressBar').remove();
                    var msg = 'initializing...';
                    var persent = 0;
                    $('.cloud-progressBar').append(coord.progressHtml);
                    $('#waiting').show();
                    $('#install').hide();
                    $('#progressMsg').html(msg);
                    $('#progbar').progressbar({
                        value: persent
                    });
                    $('#fileMsg').html(msg);
                    $('#filebar').progressbar({
                        value: persent
                    });
                    $('#jarMsg').html(msg);
                    $('#jarbar').progressbar({
                        value: persent
                    });
                    $('#moduleMsg').html(msg);
                    $('#modulebar').progressbar({
                        value: persent
                    });
                    $('#productMsg').html(msg);
                    $('#productbar').progressbar({
                        value: persent
                    });
                    createMessageChannel(sub);
                }

                function createMessageChannel(sub) {
                    var fetch = {};
                    fetch.sessionID = coord.context.getUser('sessionkey');
                    fetch.subscriber = sub;
                    syncLog(fetch);
                }

                function syncLog(fetch) {
                    var jsonString = JSON.stringify(fetch);
                    console.log(fetch)
                    Backbone.sync('syncmessage', jsonString, true,

                        function (json) {
                            console.log(json);
                            var ft = json.data;
                            var msgs = ft.messages;
                            show(msgs);
                            syncPoint = ft.syncPoint;
                            fetch.syncPoint = syncPoint;
                            fetch.filterIds = ft.filterIds;
                            fetch.subscriber = ft.subscriber;
                            fetch.sessionID = coord.context.getUser('sessionkey');
                            if (!complete) {
                                syncLog(fetch);
                            }
                        }, function (json) {
                            if (!removed) {
                                console.log(json);
                                createMessageChannel();
                            }
                        }, this);
                }

                function show(msgs) {
                    if (msgs != null) {
                        msgs.sort(function (a, b) {
                            return a.msgId > b.msgId ? 1 : -1
                        });
                        for (var i = 0; i < msgs.length; i++) {
                            if (msgs[i].type == "ProgressMessage") {
                                update(msgs[i]);
                            }
                        }
                    }
                }

                function update(msg) {
                    if (msg.detailType == 3) {
                        coord.alert("Install failed" + msg.logMsg);
                        $('#filebar').progressbar("destroy");
                        $('#jarbar').progressbar("destroy");
                        $('#modulebar').progressbar("destroy");
                        $('#productbar').progressbar("destroy");
                        $('#waiting').show();
                        $('#install').hide();
                        $('.progressBar').remove();
                    } else if (msg.detailType == 1) {
                        if (!installing) {
                            $('#waiting').hide();
                            $('#install').show();
                            installing = true;
                        }
                        var info = JSON.parse(msg.logMsg);
                        $('#fileMsg').html('info:' + info[0].message);
                        $('#filebar').progressbar('option', 'value',
                            info[0].value);
                        $('#jarMsg').html('info:' + info[1].message);
                        $('#jarbar').progressbar('option', 'value',
                            info[1].value);
                        $('#moduleMsg').html('info:' + info[2].message);
                        $('#modulebar').progressbar('option', 'value',
                            info[2].value);
                        $('#productMsg').html('info:' + info[3].message);
                        $('#productbar').progressbar('option', 'value',
                            info[3].value);
                        if (info[3].value >= 100) {
                            complete = true;
                            $('#filebar').progressbar("destroy");
                            $('#jarbar').progressbar("destroy");
                            $('#modulebar').progressbar("destroy");
                            $('#productbar').progressbar("destroy");
                            $('.progressBar').remove();
                            coord.alert("install complete!");
                        }
                    }
                }
            };
        })(window.coord ? window.coord : window.coord = {});
    });