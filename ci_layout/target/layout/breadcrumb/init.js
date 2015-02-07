define(['text!layout/breadcrumb/item.html'], function(itemHtml) {
    var BrdCrbModel = Backbone.Model.extend({
        defaults: {},
        initialize: function() {

        }
    });
    var brdCrbModel = new BrdCrbModel();
    var BrdCrbView = Backbone.View.extend({
        el: $('#breadcrumbDiv'),
        events: {
            'click a': 'onClick'
        },
        initialize: function() {

        },
        onClick: function(e) {
            var curTarget = $(e.target);
            var len = $('#brdCrbList li').length;
            var index = $('#brdCrbList li').index(curTarget.closest('li'));
            var contextPath = coord.context.get("contextPath");
            if ((contextPath.Trim() == "configuration/domain/") && (index > 1)) {
                console.log(contextPath);
                var namePath = "";
                for (var i = 2; i < index; i++) {
                    namePath += $($('#brdCrbList li').get(i)).children("a").first().html();
                    namePath = namePath + '/';
                }
                namePath += $($('#brdCrbList li').get(index)).children("a").first().html();
                coord.context.set({
                    'domainPath': namePath
                });
            }

            if (index == 0) {
                var contextArray = [];
                if (contextPath.Trim().StartsWith("uapsysconfig/")) {
                    contextArray[0] = coord.context.get('brdCrbArray')[0];
                    contextArray[1] = coord.context.get('brdCrbArray')[1];
                    coord.context.set({
                        'brdCrbArray': contextArray,
                        'toPage': true
                    });
                }
                else{
                    contextArray[0] = coord.context.get('brdCrbArray')[0];
                    if (contextArray[0].module == 'applicationManagement') {
                        contextArray[1] = coord.context.get('brdCrbArray')[1];
                    }
                    coord.context.set({
                        'brdCrbArray': contextArray,
                        'resetPage': true
                    });
                }
            } else if (index < len - 1) {
                var contextArray = coord.context.get('brdCrbArray');
                while (contextArray.length - 1 > index) {
                    contextArray.pop();
                }
                coord.context.set({
                    'brdCrbArray': contextArray,
                    'toPage': true
                });
            } else {
                console.log("brdcrb click:current page");
            }

        },
        changeBrdCrb: function() {
            var brdCrbArray = coord.context.get('brdCrbArray');
            var contextPath = coord.context.get("contextPath");
            var contextArray = contextPath.split('/');
            var len = brdCrbArray.length;
            var str = "";
            _.each(brdCrbArray, function(item) {
                str += _.template(itemHtml, item);
            });
            str = Backbone.language(str, coord);
            $("#brdCrbList").empty();
            $("#brdCrbList").html(str);
            var item = $("#brdCrbList").find('li').first();
            var contextPathHash = brdCrbArray[0].module;

        },
        addBrdCrb:function(str){
            if(str){
                var liItem = _.template(itemHtml, {name:str});
                $("#brdCrbList").append(Backbone.language(liItem, coord));
            }
        },
        removeLastItem:function(){
            $("#brdCrbList").find('li').last().replaceWith('');
        }
    });
    var brdCrbView = new BrdCrbView({
        model: brdCrbModel
    });
    return {
        view: brdCrbView
    };
});