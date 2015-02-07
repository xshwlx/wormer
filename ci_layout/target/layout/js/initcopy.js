/* 
 * initialization of mainframe
 */
define(['knockout',
    'jquery', 'cookie', 'jqueryBlockUI', 'json', 'jqueryMD5',
    'underscore', 'backbone',
    'bootstrap', 'util', 'base', 'ie-placeholder', 'date', 'context', 'alert'], function (ko) {

    var initialize = function () {
        coord.ko = ko;
        var PageModel = function () {
            this.numberOfClicks = coord.ko.observable(0);
            this.registerClick = function () {
                this.numberOfClicks(this.numberOfClicks() + 1);
            };
        };
        var PageView = Backbone.View.extend({
            el: $('body'),
            events: {
                'click #loginBtn': 'loginClicked',
                'keydown': 'loginKeyDown',
                'click input': 'hideAlert'
            },
            initialize: function () {
                var curTarget = this;
                window.location.hash = '';
                if (window.localStorage['login'] && window.localStorage['account'] && window.localStorage['accounttype']) {
                    coord.context.setUser('account', window.localStorage['account']);
                    coord.context.setUser('type', window.localStorage['accounttype']);
                    coord.context.setUser('sessionkey', window.localStorage['sessionkey']);
                    coord.context.set({
                        lang: window.localStorage['lang']
                    });
                    var lang = window.localStorage['lang'];
                    this.loadLangFile(lang, function () {
                        require(['menu'], function () {
                            console.log("storage login");
                            curTarget.loadFrame();
                        });
                    });
                } else {
                    var lang = this.getNavLang();
                    if (window.localStorage['timeout']) {
                        this.loadLangFile(lang, function () {
                            this.loginError('timeout');
                            window.localStorage['timeout'] = '';
                        });
                    }
                    $("#langSel").val(lang);
                    $('#loginBtn').removeClass('disabled');
                }
            },
            hideAlert: function () {
                $(".cloud-login-alert").each(function () {
                    $(this).fadeOut(1000, function () {
                        $(this).replaceWith('');
                    });
                });
            },
            loginKeyDown: function (e) {
                if (e.type == 'keydown' && e.keyCode === 13) {
                    this.loginClicked();
                }
            },
            loginClicked: function () {
                var lang = $("#langSel").val();
                var username = $("#username").val().Trim().match('^\\w+$');
                var password = $("#password").val().Trim().match('^\\w+$');
                var domain = $("#domainId").val().Trim().match('^\\w+$') || '/';
                coord.context.set({
                    lang: lang
                });
                var _this = this;
                this.loadLangFile(lang, function () {
                    if (username && username) {
                        this.loginAjax(username, password, domain, function (data) {
                            coord.context.setUser(data);
                            coord.context.setUser('sessionkey', encodeURIComponent(data.sessionkey));
                            require(['menu'], function () {
                                window.localStorage['login'] = true;
                                window.localStorage['account'] = data.account;
                                window.localStorage['accounttype'] = data.type;
                                window.localStorage['sessionkey'] = encodeURIComponent(data.sessionkey);
                                window.localStorage['jsessionid'] = $.cookie('JSESSIONID');
                                window.localStorage['lang'] = lang;
                                window.localStorage['username'] = data.username;
                                _this.loadFrame();
                            });
                        }, function () {
                            this.loginError('nonexist')
                        });
                    } else {
                        this.loginError();
                    }
                });
            },
            loginAjax: function (username, password, domain, successCallback, errorCallback) {
                var username = encodeURIComponent(username);
                var password = $.md5(password);
                var domain = encodeURIComponent(domain);
                var test1 = document.cookie;
                var _this = this;
                $.ajax({
                    type: "POST",
                    data: "command=login&username=" + username + "&password=" + password + "&domain=" + domain + "&response=json",
                    url: "http://20.10.130.244:8080/client/api?",
                    // url: "http://20.2.60.132:8080/client/api?",
                    async: false,
                    dataType: "json",
                    success: function (json, success, response) {

                        successCallback(json.loginresponse);
                    },
                    error: function (e) {
                        console.log(e);
                        errorCallback.call(_this, e.responseText);
                    }
                });
            },
            /*show login error message*/
            loginError: function (str) {
                var errorMsg = '';//coord.lang.login.inputError;
                // if (str == 'nonexist') {
                //   errorMsg = coord.lang.login.nonExist;
                // }else if (str == 'timeout') {
                //   errorMsg = coord.lang.login.timeout;
                // }
                switch (str) {
                    case 'nonexist':
                        errorMsg = coord.lang.login.nonExist;
                        break;
                    case 'timeout':
                        errorMsg = coord.lang.login.timeout;
                        break;
                    default:
                        errorMsg = coord.lang.login.inputError;
                }
                $("#loginErrorDiv").empty();
                require(['text!loginerror.html'], function (dialog) {
                    $("#loginErrorDiv").append(_.template(dialog, {
                        msg: errorMsg
                    }));
                });
            },
            /*
             * multi-language support
             */
            getLang: function (lang) {
                switch (lang) {
                    case 'English':
                        return 'en';
                    case 'Chinese':
                        return 'ch';
                    default:
                        return '';
                }
            },
            getNavLang: function () {
                var navLanguage = navigator.language || navigator.browserLanguage;
                var defLanguage = '';
                if (navLanguage.toLowerCase() == 'zh-cn') {
                    defLanguage = 'cn';
                } else if (navLanguage.toLowerCase() == "en-us") {
                    defLanguage = 'en';
                }
                return defLanguage;
            },
            loadLangFile: function (lang, callback) {
                $.extend(cloud, {
                    lang: {}
                });
                var langFile = 'js/lang/' + lang;
                var _this = this;
                require([langFile], function (lang) {
                    lang.config(coord.lang);
                    if (callback && typeof callback == 'function') {
                        callback.call(_this);
                    }
                });
            },
            loadFrame: function () {
                var NAV_BAR_PATH = "modules/navbar/init.html";
                var SIDE_BAR_PATH = "modules/sidebar/init.html";
                var BREADCRUMB_PATH = "modules/breadcrumb/init.html";
                var _this = this;

                require(['text!modules/frame.html', 'text!' + NAV_BAR_PATH, 'text!' + BREADCRUMB_PATH,
                    'text!' + SIDE_BAR_PATH], function (frame, navData, brdData, sideData) {
                    if (frame) {
                        _this.undelegateEvents();
                        $('body').removeClass('cloud-login-body');
                        $('body').addClass('cloud-frame-body');
                        $('body').html(Backbone.language(frame, cloud));
                        $("#navDiv").html(Backbone.language(navData, cloud));
                        $("#navDiv .navBarProfile").first().html(window.localStorage['username']);
                        $("#breadcrumbDiv").html(brdData);
                        $('#sideBarDiv').html(sideData);
                        $('#sideBarDiv').hide();
                        require(['modules/framecontroller'], function (frameController) {
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