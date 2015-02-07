define(['text!modules/confirm.html'], function (confirmHtml) {
    (function (paasController) {
        paasController.confirmHtml = confirmHtml;
        paasController.confirm = function (option, callback, onlyOk) {
            var optEmpty = false;
            for (var item in option) {
                optEmpty = true;
            }
            if (!optEmpty || (typeof option == "function")) {
                if ((typeof option == "function")) {
                    callback = option;
                }
                option = {
                    title: 'Info',
                    content: 'Content',
                    btnName: 'Ok'
                }
            }
            var $confirmHtml = $(confirmHtml);
            if (option.title) {
                var $confirmHtml = $(confirmHtml).attr('title', option.title).addClass("cloud-confirm");
            }
            if (option.content) {
                $confirmHtml.find('p').append(option.content);
            }
            $confirmHtml.dialog({
                resizable: false,
                minHeight: 200,
                minWith: 400,
                modal: true,
            });
            if (onlyOk) {
                $confirmHtml.dialog("option", "buttons", [
                    {
                        text: option.btnName,
                        click: function () {
                            $(this).dialog("close");
                        }
                    }
                ]);
            } else {
                $confirmHtml.dialog("option", "buttons", [
                    {
                        text: option.btnName,
                        click: function () {
                            if (callback && (typeof callback == "function")) {
                                callback();
                            }
                            $(this).dialog("close");
                        }
                    },
                    {
                        text: 'Cancel',
                        click: function () {
                            $(this).dialog("close");
                        }
                    }
                ]);
            }
        };
    })(window.paasController ? window.paasController : window.paasController = {});
});