define(['jquery'], function ($) {
    return{
        add: function (id, append2Id) {
            var options = $("#" + id + " option:selected");//获取当前选中的项
            var remove = options.remove();//删除下拉列表中选中的项
            remove.appendTo('#' + append2Id);//追加给对方
        },

        //remove =  function(id,append2Id){
//			var $removeOptions = $('#select2 option:selected');
//			$removeOptions.appendTo('#select1');//删除和追加可以用appendTo()直接完成
        //}

        addAll: function (id, append2Id) {
            var options = $("#" + id + " option");//获取当前选中的项
            var remove = options.remove();//删除下拉列表中选中的项
            remove.appendTo('#' + append2Id);//追加给对方
        },


        dblclick: function (id, append2Id) {
            var options = $("#" + id + " option:selected");//获取当前选中的项
            //var options = $('option:selected', this);
            options.appendTo('#' + append2Id);
        },

        clear: function (id) {//清空所有option
            $("#" + id).empty();
        },
        getAllOptionText: function (id, flag) {
            var options = $("#" + id + " option");//获取当前选中的项
            var res = "";
            var currFlag = "";
            options.each(function () {
                res = res + currFlag + $(this).text();
                currFlag = flag;
            });
            return res;
        },
        getOptionText: function (id, value) {
            var options = $("#" + id + " option");//获取当前选中的项
            var res = "";
            options.each(function () {
                if ($(this).val() == value) {
                    res = $(this).text();
                }
            });
            return res;
        },

        getStatusByClass: function (classStr) {
            $('.' + classStr).each(function () {
                var status = $(this).text();
                if (status == 0) {
                    $(this).text(coord.lang.shared.active);
                } else {
                    $(this).text(coord.lang.shared.pause);
                    //$(this).closest('.row').find('#action').first().hide();
                }

            });
        },

        getStatusById: function (id) {
            var status = $('#' + id).text();
            if (status == 0) {
                $('#' + id).text(coord.lang.shared.active);
            } else {
                $('#' + id).text(coord.lang.shared.pause);
            }
        },

        keyToValueByClass: function (classVal, obj) {
            $('.' + classVal).each(function () {
                var textVal = $(this).text();
                if (typeof(textVal) != "undefined" && typeof(obj[textVal.Trim()]) != "undefined") {
                    $(this).text(obj[textVal.Trim()]);
                }
            });
        },
        keyToImgByClass: function (classVal, obj) {
            $('.' + classVal).each(function () {
                var textVal = $(this).attr("lang");
                if (typeof(textVal) != "undefined" && typeof(obj[textVal.Trim()]) != "undefined") {
                    $(this).attr("src", obj[textVal.Trim()]);
//					if(textVal.Trim() == 1){
//						$(this).parent().attr("id","stopagent");
//					}
                }
            });
        },
        keyToAttrValueByClass: function (classVal, obj, attr) {
            $('.' + classVal).each(function () {
                var textVal = $(this).attr("lang");
                if (typeof(textVal) != "undefined" && typeof(obj[textVal.Trim()]) != "undefined") {
                    $(this).attr(attr, obj[textVal.Trim()]);
                }
            });
        },
        keyToValueById: function (idVal, obj) {
            var textVal = $('#' + idVal).text();
            if (typeof(textVal) != "undefined" && typeof(obj[textVal.Trim()]) != "undefined") {
                $('#' + idVal).text(obj[textVal.Trim()]);
            }
        },

        objToOption: function (idVal, obj) {
            var resOption = "";
            for (var p in obj) {
                if (typeof(obj[p]) != "undefined" && typeof(obj[p]) != "function") {
                    resOption = resOption + "<option value='" + p + "'>" + obj[p] + "</option>";
                }
            }
            $("#" + idVal).append(resOption);
        },

        refershVersion: function () {
            $('.product_version').each(function () {
                var productVersion = $(this).text();
                var productLineVersion = $(this).closest('.row').find('.product_line_version').first().text();
                if (productVersion == "") {
                    $(this).text(productLineVersion);
                } else {
                    //
                }

            });
        }


    };

});
