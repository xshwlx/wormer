/* 
 * 初始化框架
 */
define(['knockout',
    'jquery', 'json',
    'underscore', 'backbone',
    'bootstrap', 'bootstrap-carousel', 'util', 'base', 'ie-placeholder', 'context',
    'alert'
], function (ko) {


    var initialize = function () {

        coord.ko = ko;
        /**
         * 定义数据对象模型
         */
        var PageModel = function () {

        };
        /**
         *页面视图
         */
        var PageView = Backbone.View.extend({
            el: $('body'),
            /**
             * 事件
             */
            events: {
                'click #login': 'loginClicked'
            },
            initialize: function () {
            },

            hideAlert: function () {
                $(".cloud-login-alert").each(function () {
                    $(this).fadeOut(1000, function () {
                        $(this).replaceWith('');
                    });
                });
            },
            /**
             * 登陆操作
             */
            loginKeyDown: function (e) {
                if (e.type == 'keydown' && e.keyCode === 13) {
                    this.loginClicked();
                }
            },

            loginClicked: function () {
                var username = $("#username").val().Trim().match('^\\w+$');
                var password = $("#password").val().Trim().match('^\\w+$');
                var _this = this;
                if (username && password) {
                    $('#login').html("正在登陆");
                    this.loginAjax(username, password, function (json) {
                        if (json.code == 0) {
                            //登陆成功
                            // window.localStorage['login'] = true;
                            var session = json.data;
                            //客户端存储 sessionID
                            window.localStorage['sessionkey'] = session.sessionId;
                            //加载组件数据
                            _this.loadPlugins(session.sessionId);
                            //加载菜单框架
                            _this.loadFrame();
                        } else {
                            _this.loginError(json.message);
                        }
                    }, function () {
                        this.loginError('nonexist')
                    });
                } else {
                    this.loginError();
                }
                // });
            },
            /**
             *登陆后台请求
             */
            loginAjax: function (username, password, successCallback, errorCallback) {
                var username = encodeURIComponent(username);
                var password = encodeURIComponent(password);
                var sessionid = " ";
                var action = "login";
                var jsonParam = JSON.stringify({
                    "user_code": username,
                    "sessionid": sessionid,
                    "user_pwd": password
                });
                var _this = this;
                $.ajax({
                    type: "POST",
                    data: {
                        'action': action,
                        'sessionid': sessionid,
                        'jsonParam': jsonParam
                    },
                    url: coord.controller,
                    async: false,
                    dataType: "json",
                    success: function (json, success, response) {
                        successCallback(json);
                    },
                    error: function (e) {
                        console.log(e);
                        errorCallback.call(_this, e.responseText);
                    }
                });
            },
            //加载菜单
            loadPlugins: function (sessionid) {
                var actionName = "loadPlugins";
                var jsonParam = "";
                var _this = this;
                $.ajax({
                    type: "POST",
                    data: {
                        action: actionName,
                        sessionid: sessionid,
                        jsonParam: jsonParam
                    },
                    url: coord.controller,
                    async: false,
                    dataType: "json",
                    success: function (menujson, success, response) {
                        coord.menu = menujson;
                    },
                    error: function (e) {
                        console.log(e);
                        errorCallback.call(_this, e.responseText);
                    }
                });
            },
            /**
             * 登陆错误信息翻译
             * @param str
             */
            loginError: function (str) {
                var errorMsg = '';
                switch (str) {
                    case 'nonexist':
                        errorMsg = "用户不存在";
                        break;
                    case 'timeout':
                        errorMsg = "登陆超时";
                        break;
                    case 'locked':
                        errorMsg = "用户被锁定";
                        break;
                    default:
                        errorMsg = "登陆失败";
                }
                require(['text!loginerror.html'], function (dialog) {
                    $(".errorInfo").html(_.template(dialog, {
                        msg: errorMsg
                    }));
                });
            },

            loadFrame: function () {
                var NAV_BAR_PATH = "layout/navbar/index.html";
                var SIDE_BAR_PATH = "layout/sidebar/index.html";
                var BREADCRUMB_PATH = "layout/breadcrumb/index.html";
                var _this = this;
                require(['text!layout/frame.html', 'text!' + NAV_BAR_PATH, 'text!' + BREADCRUMB_PATH,
                    'text!' + SIDE_BAR_PATH
                ], function (frame, navData, brdData, sideData) {
                    if (frame) {
                        _this.undelegateEvents();
                        //加载框架页面
                        $('body').removeClass("login-body");
                        $('body').addClass("navbar-fixed")
                        $('body').html(Backbone.language(frame, coord));
                        $('body').show();
                        //加载导航栏
                        $("#navbar").html(Backbone.language(navData, coord));
                        $("#breadcrumbs").html(brdData);
                        $('#sidebar').html(Backbone.language(sideData, coord));
                        $('#sidebar').show();

                        require(['layout/framecontroller'], function(frameController) {
                            frameController.initController();
                        });
                    }
                });
            }
        });
        var pageView = new PageView();

    } //end init
    return {
        initialize: initialize
    };
});
