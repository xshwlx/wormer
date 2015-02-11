define(
    [  'layout/modules/commonconst/commonModal/selectModalController'
    ], function(navItemHtml, leftBtnHtml, rightBtnHtml, liHtml) {
        var NavBarModel = Backbone.Model.extend({
            defaults: {
                navBarName: ""
            },
            initialize: function() {
            }
        });
        var navBarModel = new NavBarModel();
        var NavBarView = Backbone.View
            .extend({
                el: $('#navDiv'),
                events: {
                    'click #navBarList .navitem': 'onNavBarClicked',
                    'click .hideitem': 'activeHideItem',
                    'click #log': 'showLog',
                    'click #aboutBtn': 'loadAbout'
                },
                _navLengthArray: [],
                _activeItemIndex: 0,
                _minLength: 0,
                initialize: function() {
                    var userName = window.localStorage['username'];
                    var navBarImg = window.localStorage['navBarImg'];
                    $('#navLogo').attr('src', navBarImg);
                    $("#userNameSpan").html(userName);

                    var navId = 0;
                    var totalLength = 0;
                    this._navLengthArray = [];
                    this._activeItemIndex = 0;
                    this._minLength = 0;
                    var len = $('#navBarList li').length;
                    var itemLi = $('.navitem').first();

                    while (itemLi&&itemLi.length) {
                        itemLi.addClass("navItem" + navId);
                        hideRightLi.addClass("navItemR" + navId);
                        hideLeftLi.addClass("navItemL" + navId);
                        var width = itemLi.width();
                        this._navLengthArray.push(width);
                        totalLength = totalLength + width;
                        itemLi = itemLi.next();
                        hideRightLi = hideRightLi.next();
                        hideLeftLi = hideLeftLi.next();
                        navId++;
                    }
                    this._minLength = totalLength;
                    this.initScreenSize();
                    var _view = this;
                    $(window).bind('resize', function() {
                        _view.resizeWindow();
                    });
                },
                onNavBarClicked: function(e) {
                    var target = $(e.target);
                    if (target.context.localName != 'a') {
                        target = target.children('a');
                    }
                    var name = target.html();
                    if (this.model.get("navBarName") != name) {
                        this.model.set("navBarName", name);
                        target.closest('ul').find('.active')
                            .removeClass('active');
                        target.closest('li').addClass('active');
                        this._activeItemIndex = target.closest('ul')
                            .find('.active').index() - 1;
                        console.log("navbar:" + name);
                    } else {
                        var contextArray = [];
                        contextArray[0] = coord.context
                            .get('brdCrbArray')[0];
                        coord.context.set({
                            'brdCrbArray': contextArray,
                            'resetPage': true
                        });
                    }
                },
                activeNavBar: function(index) {
                    $("#navBarList").find('.active').removeClass(
                        'active');
                    $($("#navBarList").children('li').get(index + 1))
                        .addClass('active');
                    this._activeItemIndex = index;
                },

                resizeWindow: function() {
                    console.log("resize");
                    coord.adjustDiv();
                    this.calculScreenSize();
                },
                initScreenSize: function() {
                    $("#navBtnLeftLi").hide();
                    $("#navBtnRightLi").hide();
                    $("#navBtnLeftMenu li").hide();
                    $("#navBtnRightMenu li").hide();
                    var currWidth = $('#navDiv').width() - $("#navBtnRightLi").width() - 30;
                    var i = this._activeItemIndex,
                        len = this._navLengthArray.length + 1;
                    while ((currWidth > 0) && (i < len)) {
                        currWidth -= this._navLengthArray[i];
                        i++;
                    };
                    if (i < len) {
                        i = i - 1;
                        $("#navBtnRightLi").show();
                        for (var len = this._navLengthArray.length; i < len; i++) {
                            $("#navBarList .navItem" + i).hide();
                            $("#navBtnRightMenu .navItemR" + i).show();
                        }
                    }

                },
                calculScreenSize: function() {
                    $("#navBtnLeftMenu li").hide();
                    $("#navBtnRightMenu li").hide();
                    $("#navBtnRightLi").hide();
                    $("#navBtnLeftLi").hide();
                    var currWidth = $('#navDiv').width() - 20;
                    if (currWidth < this._minLength + 30) {
                        currWidth = currWidth - $("#navBtnRightLi").width() - 30;
                        var len = this._navLengthArray.length,
                            _activeItemIndex = this._activeItemIndex;
                        var i = _activeItemIndex,
                            j = _activeItemIndex - 1;
                        while ((currWidth > 0) && (i < len)) {
                            currWidth -= this._navLengthArray[i];
                            $("#navBarList .navItem" + i).show();
                            i++;
                        }
                        if (i == len) {
                            while ((currWidth > 0) && (j >= 0)) {
                                currWidth -= this._navLengthArray[j];
                                $("#navBarList .navItem" + j).show();
                                j--;
                            }
                            if (j >= 0) {
                                j = j + 1;
                                $("#navBtnLeftLi").show();
                                for (; j >= 0; j--) {
                                    $("#navBarList .navItem" + j)
                                        .hide();
                                    $("#navBtnLeftMenu .navItemL" + j)
                                        .show();
                                }
                                // if(j == _activeItemIndex){
                                // i = len -1;
                                // $("#navBtnRightLi").show();
                                // while (i > j) {
                                // $("#navBarList .navItem" + i).hide();
                                // $("#navBtnRightMenu .navItemR" +
                                // i).show();
                                // i--;
                                // }
                                // }
                            }
                        } else {
                            $("#navBtnRightLi").show();
                            i = i - 1;
                            while (i < len) {
                                $("#navBarList .navItem" + i).hide();
                                $("#navBtnRightMenu .navItemR" + i)
                                    .show();
                                i++;
                            }
                            if (j >= 0) {
                                $("#navBtnLeftLi").show();
                                j = j + 1;
                                for (; j >= 0; j--) {
                                    $("#navBarList .navItem" + j)
                                        .hide();
                                    $("#navBtnLeftMenu .navItemL" + j)
                                        .show();
                                }
                            }
                        }
                        $("#navBarList .navItem" + _activeItemIndex)
                            .show();
                        $(
                            "#navBtnLeftMenu .navItemL" + _activeItemIndex).hide();
                        $(
                            "#navBtnRightMenu .navItemR" + _activeItemIndex).hide();
                    } else {
                        $("#navBarList .navitem").show();
                        $("#navBtnRightLi").hide();
                        $("#navBtnLeftLi").hide();
                    }
                },
                activeHideItem: function(e) {
                    var $target = $(e.target);
                    var name = $target.html();
                    var i = $target.closest('li').index();
                    this._activeItemIndex = i;
                    console.log(this._activeItemIndex);
                    $('#navBarList').find('.active').removeClass(
                        'active');
                    $('#navBarList .navItem' + i).addClass('active');
                    this.calculScreenSize();
                    this.model.set("navBarName", name);
                },
                loadAbout:function(){
                    $('#frameAlertDiv #modalLabel').html(coord.lang.shared.about);
                    $('#frameAlertDiv .modal-body').css('text-align','center');
                    $('#frameAlertDiv #modalContent').html(coord.lang.shared.aboutContent);
                    $('#frameAlertDiv #okBtn').html(coord.lang.shared.ok);
                    $('#frameAlertDiv #okBtn').show();
                    $('#frameAlertDiv #cancelBtn').hide();
                },
                showLog: function() {
                    var vis = $('#logWin').is(":visible");
                    if (!vis) {
                        var logModalPath = 'modules/navbar/log/';
                        require([logModalPath + "init"], function(
                            pageObj) {
                            pageObj.view.isCurrentPage = false;
                            pageObj.view.curPagePath = logModalPath;
                            pageObj.view.logRender(logModalPath + 'init.html', 'logModal');
                        }, function(err) {
                            console.log("err:" + err);
                        });
                    }
                }
            });
        var navBarView = new NavBarView({
            model: navBarModel
        });
        return {
            view: navBarView
        };
    });