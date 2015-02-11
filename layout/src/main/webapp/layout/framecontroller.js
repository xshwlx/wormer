/**
 * 菜单框架控制
 */
define([
        'layout/navbar/init',
        'layout/sidebar/init',
        'layout/breadcrumb/init',
        'layout/modules/commonconst/Constants'
    ], function (navBar, sideBar, brdCrb, menu) {

        var initController = function () {

            var controller = {};
            controller.page = {};
            controller.navBar = navBar.view;
            controller.sideBar = sideBar.view;
            controller.brdCrb = brdCrb.view;

            controller.navBarActive = function (navBarName, option) {
            };
            /**
             * 定义Model
             */
            var ControllerModel = Backbone.Model.extend({
                defaults: {
                    responseClick: false
                },
                initialize: function () {
                }
            });

            var controllerModel = new ControllerModel();
            /**
             * 定义路由
             */
            var CloudRouter = Backbone.Router.extend({
                routes: {
                    'page/*contextPath': 'loadPage',
                    ':path': 'reloadHome'
                },
                /**
                 * 初始加载一级菜单
                 * @param navBarName
                 * @param option
                 * @returns {*}
                 */
                setFrishMenuBrdCrb: function(navBarName, option) {
                    for (var len = coord.menu.length; len--;) {
                        //menu 中一级菜单
                        var navItem = coord.menu[len];
                        //根据一级菜单点击过滤出二级菜单（所有级别菜单）
                        if(navItem.nodes){//包含下级菜单

                            for(var x in navItem.nodes){
                                for(var y in navItem.nodes[x]){
                                    if(navItem.nodes[x].module == navBarName || navItem.nodes[x].module == navBarName){
                                        var brdCrbArray = [];
                                        if (option) {
                                            brdCrbArray = coord.context.get('brdCrbArray');
                                            brdCrbArray[0] = {
                                                name: navItem.nodes[x].name,
                                                module: navItem.nodes[x].module,
                                                url : navItem.nodes[x].url
                                            };
                                        } else {
                                            brdCrbArray.push({
                                                name: navItem.nodes[x].name,
                                                module: navItem.nodes[x].module,
                                                url : navItem.nodes[x].url
                                            });
                                        }
                                        coord.context.set({
                                            'brdCrbArray': brdCrbArray
                                        });

                                        controller.sideBar.model.set({
                                            length: navItem.nodes[x].length,
                                            sideBarArray: navItem.nodes
                                        });
                                        var indexArray = [];
                                        indexArray[0] = len;
                                        indexArray[1] = x;
                                        indexArray[2] = y;
                                        return indexArray;
                                    }
                                }
                            }

                        } ;
                        if (navItem.name == navBarName || navItem.module == navBarName) {
                            var brdCrbArray = [];
                                if (option) {
                                    brdCrbArray = coord.context.get('brdCrbArray');
                                    brdCrbArray[0] = {
                                        name: navItem.name,
                                        module: navItem.module,
                                        url : navItem.url
                                    };
                                } else {
                                    brdCrbArray.push({
                                        name: navItem.name,
                                        module: navItem.module,
                                        url : navItem.url
                                    });
                                }


                            coord.context.set({
                                'brdCrbArray': brdCrbArray
                            });

                            controller.sideBar.model.set({
                                length: navItem.nodes.length,
                                sideBarArray: navItem.nodes
                            });
                            return len;
                        }
                    }
                },
                loadPage: function (contextPath) {
                    var pathArray = contextPath.split('/');
                    var indexOfNavBar = this.setFrishMenuBrdCrb(pathArray[1],true); //2
                    controller.sideBar.activeSideBar(pathArray[1]);//2
                    var _module_base_path = 'layout/modules';
                    var _module_file = "/init";
                    var _init_file = "/index.html";
                    var filePath = _module_base_path + '/' + contextPath + _init_file,
                        modulePath = _module_base_path + '/' + contextPath + _module_file;
//                    if (controller.page.undelegateEvents) {
//                        controller.page.undelegateEvents();
//                        controller.page.isCurrentPage = false;
//                        controller.page.leavePage();
//                    }
                    //页面跳转
                    require([modulePath], function(pageObj) {
                        controller.page = pageObj.view;
                        controller.page.isCurrentPage = true;
                        controller.page.curPagePath = 'modules/pages/' + contextPath + '/';
                        controller.page.render(filePath,contextPath);
                        controller.brdCrb.changeBrdCrb();
                    }, function(err) {
                        console.log("err:" + err);
                    });
                },
                reloadHome: function (path) {
                    controller.router.navigate("page/home", {
                        trigger: true,
                        replace: false
                    });
                }
            });

            controller.router = new CloudRouter();
            Backbone.history.start();
            /**
             * 定义视图
             */
            var ControllerView = Backbone.View
                .extend({
                    el: $('body'),
                    events: {
                        'click #logoutBtn': 'logout'
                    },

                    initialize: function () {
                        var _this = this;
                        //监听sideBar 点击事件
                        this.listenTo(controller.sideBar.model, 'change', this.onSideBarClick);
                        //页面变化事件监控
                        this.listenTo(coord.context, "change", this.contextChanged);

                    },

                    /**
                     *  加载左侧菜单栏
                     */
                    initSideBar : function(){
                          loadPage("");
                    },
                    /*
                     * when click the navbar,load the page and the sidebar
                     */
                    onNavBarClick: function () {

                    },

                    /*
                     * when click the sidebar,load the page and the sidebar
                     */
                    onSideBarClick: function () {
                        if (controller.sideBar.model.get('clickTrigger')) {
                            controller.sideBar.model.set('clickTrigger', false);
                            console.log('sidebar curSideBar changed');
                            var sideBarActiveUrl = controller.sideBar.model.get('curSideBarUrl');
                            var sideBarActiveModule = controller.sideBar.model.get('curSideBarModule');
                            var sideBarActiveName = controller.sideBar.model.get('curSideBarName');
                            controller.sideBar.setSideBarBrdCrb({name: sideBarActiveName, module: sideBarActiveModule, url: sideBarActiveUrl});
                            this.loadPage();
                        }
                    },

                    /* link to page between different navbar */
                    linkToPage: function (obj) {
                        var moduleStr = obj.module;
                        var nameStr = obj.name;
                        this.stopListening(controller.navBar.model);
                        this.stopListening(controller.sideBar.model);
                        if (moduleStr) {
                            var moduleArray = moduleStr.split('/');
                            var nameArray = nameStr.split('/');
                            var arrayIndex = 0;
                            if (moduleArray[1]) {
                                var indexOfNavBar = controller.sideBar
                                    .setNarBarBrdCrb(moduleArray[1]);
                                if (!moduleStr
                                    .StartsWith('/uapsysconfig/')) {
                                    controller.navBar.model.set("navBarName", coord.menu[indexOfNavBar].name);
                                    controller.navBar.activeNavBar(indexOfNavBar);
                                }
                                if (moduleArray[2]) {
                                    var sideBarItem = controller.sideBar.activeSideBar(moduleArray[2]);
                                    if (sideBarItem) {
                                        controller.sideBar.setSideBarBrdCrb(sideBarItem);
                                    } else {
                                        this.addModuleToBrdCrb(nameArray[arrayIndex], moduleArray[2]);
                                        arrayIndex = arrayIndex + 1;
                                    }
                                    var i = 3,
                                        j = arrayIndex;
                                    while (moduleArray[i] || nameArray[j]) {
                                        this.addModuleToBrdCrb(nameArray[j], moduleArray[i]);
                                        i++;
                                        j++;
                                    }
                                } else {
                                    var defaultSideBar = controller.sideBar.getDefaultSideBar(coord.menu[indexOfNavBar].nodes);
                                    controller.sideBar.defaultSideBar();
                                    controller.sideBar.setSideBarBrdCrb(defaultSideBar);
                                }
                            }
                        }
                        this.listenTo(controller.navBar.model, 'change', this.onNavBarClick);
                        this.listenTo(controller.sideBar.model, 'change', this.onSideBarClick);
                    },

                    addModuleToBrdCrb: function (name, module) {
                        if (!name) {
                            name = "";
                        }
                        if (!module) {
                            module = "";
                        }
                        var array = coord.context.get('brdCrbArray');
                        array.push({
                            name: name,
                            module: module
                        });
                        coord.context.set('brdCrbArray', array);
                    },

                    /* load the page */
                    loadPage: function (str) {
                        var brdcrbArray = coord.context.get('brdCrbArray');
                        var contextPath = "";
                        var _module_base_path = 'layout/pages';
                        var _module_file = "/init";
                        var _init_file = "/index.html";
                        for(x in brdcrbArray){
                            contextPath += (brdcrbArray[x] + '/');
                        }
                        var contextPathHash = contextPath.slice(0, contextPath.length - 1);
                        controller.router.loadPage(contextPathHash);
                    },

                    contextChanged: function () {
                        this.stopListening(coord.context);
                        var changedAttr = coord.context.changed;
                        if (changedAttr['resetPage']) {
                            this.onNavBarClick();
                            coord.context.set('resetPage', '');
                        }
                        if (changedAttr['toPage']) {
                            if (changedAttr['pageLink']) {
                                this.linkToPage(changedAttr['pageLink']);
                                coord.context.set('pageLink', '');
                            }
                            if (changedAttr['pageDetailLink']) {
                                this
                                    .linkToPage(changedAttr['pageDetailLink']);
                                coord.context.set('pageDetailLink', '');
                            }
                            coord.context.set('toPage', false);
                            this.loadPage();
                        }
                        if (changedAttr['refreshPage']) {
                            coord.context.set('refreshPage', false);
                            this.loadPage('refresh');
                        }
                        if (changedAttr['brdCrbItem']) {
                            var str = changedAttr['brdCrbItem'];
                            coord.context.set('brdCrbItem', '');
                            controller.brdCrb.addBrdCrb(str);
                        }
                        if (changedAttr['brdCrbItemRemove']) {
                            coord.context
                                .set('brdCrbItemRemove', false);
                            controller.brdCrb.removeLastItem();
                        }
                        /**
                         * modal
                         */
                        if (changedAttr['modalItem']) {
                            var str = changedAttr['modalItem'];
                            controller.loadPluginLangFile(str, str);
                            coord.context.set('modalItem', '');
                        }
                        /**
                         * 加载连接页面多语
                         */
                        if (changedAttr['linkPage']) {
                            var str = changedAttr['linkPage'];
                            controller.loadPluginLangFile(str, str);
                            coord.context.set('linkPage', '');
                        }
                        if (changedAttr['linkPageAppend']) {
                            var str = changedAttr['linkPageAppend'];
                            controller.loadPluginLangFile(str, str);
                            coord.context.set('linkPageAppend', '');
                        }

                        this.listenTo(coord.context, 'change',
                            this.contextChanged);
                        console.log("context change");
                    },

                    logout: function (option) {
                        console.log('click logout');
                        window.localStorage.clear();
                        window.localStorage.clear();
                        window.location.href = 'index.html';
                        window.location.hash = '';
                        if (option == 'timeout') {
                            window.localStorage['timeout'] = true;
                        }
                    },

                    // 订阅异步消息
                    createMessageChannel: function (subscriber) {
//				var fetch = {};
//				var curtime = new Date();
//				fetch.syncPoint = curtime
//					.format("yyyy-MM-dd hh:mm:ss");
//				fetch.timeOut = 300 * 1000;
//				fetch.sessionID = coord.context
//					.getUser('sessionkey');
//				fetch.subscriber = subscriber;
//				this.remoteSync(fetch);
                    },

                    remoteSync: function (fetch) {
                        var _this = this;
                        _this.isCurrentPage = true;
                        var jsonString = JSON.stringify(fetch);
                        Backbone
                            .sync(
                            'syncmessage',
                            jsonString,
                            true, function (json) {
                                if (json.code == Constants.RESPONSE_CODE_SUCCESS) {
                                    var fetchObj = json.data;
                                    if (!this
                                        .isEmptyObject(controller.page)) {
                                        controller.page
                                            .onCCMessage(fetchObj);
                                        fetch.sessionID = coord.context
                                            .getUser('sessionkey');
                                        fetch.syncPoint = fetchObj.syncPoint;
                                        fetch.filterIds = fetchObj.filterIds;
                                        _this
                                            .remoteSync(fetch);
                                    }
                                } else if (json.code == 3) {
                                    _this.logout();
                                } else if (json.code == 9) {
                                    _this.logout('timeout');
                                } else {
                                    console.log(json);
                                    _this.remoteSync();
                                }
                            }, function (json) {
                                console.log(json);
                                _this
                                    .createMessageChannel(fetch.subscriber);
                            }, _this);
                    },

                    isEmptyObject: function (obj) {
                        for (var n in obj) {
                            return false
                        }
                        return true;
                    }

                });

            controller.view = new ControllerView();
        };
        return {
            initController: initController
        };
    });