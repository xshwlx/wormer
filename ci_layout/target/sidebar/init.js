define(["text!layout/sidebar/index.html"], function(sideHtml) {
    var SideBarModel = Backbone.Model.extend({
        defaults: {
            length: 0,
            sideBarArray: [],
            curSideBarName: "",
            curSideBarModule: "",
            clickTrigger:false
        },
        initialize: function() {
        }
    });
    var sideBarModel = new SideBarModel();
    var SideBarView = Backbone.View.extend({
        el: $('#sideBarList'),
        events: {
            'click li': 'onClick'
        },
        initialize: function() {
            this.render();
        },
        //获取menu对象
        getMenu : function(id){
               for(x in coord.menu){
                   var menuId = coord.menu[x].id;
                   if(id == menuId){
                          return  coord.menu[x];
                   }else{
                       if( coord.menu[x].nodes){
                              for(y in coord.menu[x].nodes){
                                      if(coord.menu[x].nodes[y].id == id){
                                          return coord.menu[x].nodes[y];
                                      }
                              }

                       }
                   }
               }

        },
        onClick: function(e) {
            var target = $(e.target);
            if(target.context.localName != 'a'){
                target = target.closest('a');
            }
            coord.context.set('data',{});
           //获取菜单名称
            var name = target.find('lable').first().html();
            var menuID  = target.parent().attr("id");
            //根据id 获取menu对象
            var menu = this.getMenu(menuID);
            var contextArray = [];
            var nodeArray = [];
            var modelPath;
            var menuArray = coord.menu;
            if(menu.nodes){//点击一级节点
                    //默认加载一级节点下的首个二级节点
                    var fristNode = menu.nodes[0];
                    modelPath = fristNode.modelPath
                    nodeArray[0] = menu;
                    nodeArray[1] = fristNode;
            }else{
                   //直接加载二级节点
                   modelPath  = menu.modelPath;
                   nodeArray  = coord.context.get('nodeArray');
                   nodeArray[1] = menu;

            }
            //数据结构
            contextArray =  modelPath.split("/");
            this.model.set("curSideBarName",name);
                 coord.context.set({
                    'nodeArray':nodeArray,
                    'brdCrbArray': contextArray,
                    'toPage': true
                });
        },
        render: function() {
            // 二级 三级节点模板
            var sideItemHtml = '<li id="{{id}}"><a class="dropdown-toggle"><i class="{{icon}}"></i><span class="menu-text"><lable>{{ name }}</lable></span> <b class="arrow icon-angle-down"></b></a>';
            var sideThirdItemHtml = '<ul class="submenu"><li id="{{id}}"><a><i class="icon-double-angle-right"></i><lable>{{ name }}</lable><span class="arrow" ></span></a></li></ul>';
                //var $itemUrl = ;
                    var str = "";
                    _.each(coord.menu, function(item) {
                        str += _.template(sideItemHtml, item);
                        if(item.nodes){
                            _.each(item.nodes, function(itemNode) {
                                str += _.template(sideThirdItemHtml, itemNode);
                            });
                        }
                        str +="</li>";
                    });
                    var sideBar = $("#sideBarList");
                    $("#sideBarList").html(str);
                    coord.context.set('showSide', true);
                    $('#sideBarDiv').show();
        },
        defaultSideBar: function() {
            $('#sideBarList').find('.active').removeClass('active');
            $('#sideBarList').find('li').first().addClass('active');
            var array = this.model.get("sideBarArray");
            if(array && array.length){
                this.model.set({
                    curSideBarName: array[0].name,
                    curSideBarModule: array[0].module,
                    curSideBarUrl: array[0].url
                });
            }
        },
        activeSideBar: function(module) {
            var array = this.model.get('sideBarArray');
            for (var len = this.model.get('length'); len--;) {
                if(array[len].nodes){
                    for(var x in array[len].nodes){
                        var nodeModule = array[len].nodes[x].module;
                        var nodeArray = nodeModule.split("/");
                        if(nodeArray.length > 1){
                            nodeModule = nodeArray[nodeArray.length - 1];
                        }
                        if (nodeModule == module){
                            this.model.set({
                                curSideBarName: array[len].nodes[x].name,
                                curSideBarModule: array[len].nodes[x].module,
                                curSideBarUrl: array[len].nodes[x].url
                            });
                            $("#sideBarList").find('.active').removeClass('active');
                            $($("#sideBarList li").get(len)).addClass('active');
                            return array[len];
                        }
                    }
                }else if (array[len].module == module) {
                    this.model.set({
                        curSideBarName: array[len].name,
                        curSideBarModule: array[len].module,
                        curSideBarUrl: array[len].url
                    });
                    $(".submenu").find('.active').removeClass('active');
                    $($("#sideBarList li").get(len)).addClass('active'); //设置了一级菜单激活
                    return array[len];
                }
            }
        },
        /*
         * @para navBarName : module or name
         * @para option : not set brdCrbArray  when true
         */
        setNarBarBrdCrb: function(navBarName, option) {
            for (var len = coord.menu.length; len--;) {
                //menu 中一级菜单
                var navItem = coord.menu[len];
                //根据一级菜单点击过滤出二级菜单（所有级别菜单）
                if(navItem.nodes){//包含下级菜单

                    for(var x in navItem.nodes){
                        for(var y in navItem.nodes[x].nodes){
                            if(navItem.nodes[x].nodes[y].name == navBarName || navItem.nodes[x].nodes[y].module == navBarName){
                                var brdCrbArray = [];
                                if (option) {
                                    brdCrbArray = coord.context.get('brdCrbArray');
                                    brdCrbArray[0] = {
                                        name: navItem.nodes[x].nodes[y].name,
                                        module: navItem.nodes[x].nodes[y].module,
                                        url : navItem.nodes[x].nodes[y].url
                                    };
                                } else {
                                    brdCrbArray.push({
                                        name: navItem.nodes[x].nodes[y].name,
                                        module: navItem.nodes[x].nodes[y].module,
                                        url : navItem.nodes[x].nodes[y].url
                                    });
                                }
                                coord.context.set({
                                    'brdCrbArray': brdCrbArray
                                });

                                this.model.set({
                                    length: navItem.nodes[x].nodes[y].length,
                                    sideBarArray: navItem.nodes[x].nodes[y].nodes
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
                    if (navBarName == "uapsysconfig") {
                        brdCrbArray = coord.context.get('brdCrbArray');
                        brdCrbArray.push({
                            name: navItem.name,
                            module: navItem.module,
                            url : navItem.url
                        });
                    } else {
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
                    }

                    coord.context.set({
                        'brdCrbArray': brdCrbArray
                    });

                    this.model.set({
                        length: navItem.nodes.length,
                        sideBarArray: navItem.nodes
                    });
                    return len;
                }
            }
        },

        getDefaultSideBar: function(navBarItems) {
            var defaultSideBar = {
                name: "",
                module: ""
            };
            if (navBarItems.length) {
                defaultSideBar = navBarItems[0];
            }
            return defaultSideBar;
        },
        /*
         * @para option: not set brdCrbArray  when true
         */
        setSideBarBrdCrb: function(obj, option) {
            if (obj.name) {
                var array = [];
                if (coord.context.get('brdCrbArray').length > 2
                    && coord.context.get('brdCrbArray')[0].module == 'applicationManagement'
                    && coord.context.get('brdCrbArray')[1].module == 'uapApplication'
                    && coord.context.get('brdCrbArray')[0].module!=obj.module ) {
                    array = coord.context.get('brdCrbArray');
                    if (coord.context.get('brdCrbArray').length == 4) {
                        array[3]=obj;
                    }else{
                        array.push(obj);
                    }
                } else {
                    if (option) {
                        array = coord.context.get('brdCrbArray');
                        array[1] = obj;
                    } else {
                        array[0] = coord.context.get('brdCrbArray')[0];
                        array.push(obj);
                    }
                }

                coord.context.set('brdCrbArray', array);
            }
        }
    });
    var sideBarView = new SideBarView({
        model: sideBarModel
    });
    return {
        view: sideBarView
    };
});