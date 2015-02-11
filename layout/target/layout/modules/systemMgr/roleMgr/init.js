define([
    'basepage',
    'text!layout/modules/systemMgr/roleMgr/index.html'], function(BasePage,roleHtml) {
    var RoleModel = BasePage.Model.extend({
        defaults: {//属性数据默认值

        },
        initialize: function() {//模型初始化操作
        }
    });
    var roleModel = new RoleModel();
    var RoleView = BasePage.View.extend({
        el: $('#sideBarList'),
        events: {//视图事件监控

        },
        initialize: function() {//视图初始化

        },
        afterRender: function() {

        }
    });
    var roleView = new RoleView({
        model: roleModel
    });
    return {
        view: roleView
    };
});