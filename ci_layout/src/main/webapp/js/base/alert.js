define(['text!layout/alert.html'], function (alertHtml) {
    (function (coord) {
        coord.alertHtml = alertHtml;
        coord.alert = function (msg, option) {
            if (!msg) {
                msg = 'Info';
            }
            var radomPara = "alert" + Math.ceil(Math.random() * 1000);
            $('.cloud-alert').append(coord.alertHtml);
            $('.cloud-alert').find('.alert-info').last().attr('id', radomPara);
            $('#' + radomPara).find('span').first().html(msg);
            if (option && option.failed) {
                $('#' + radomPara).find('.icon-white').removeClass('icon-ok').addClass('icon-remove');
                $('#' + radomPara).closest('.alert-info').addClass('label-important');
                $('#' + radomPara).fadeOut(10000, function () {
                    $('#' + radomPara).remove();
                });
            } else if (option && option.log) {
                if (option.log == 1 || option.log == 2) {
                    $('#' + radomPara).find('.icon-white').removeClass('icon-ok').addClass('icon-remove');
                    $('#' + radomPara).closest('.alert-info').addClass('label-info');
                }
                if (option.log == 3) {
                    $('#' + radomPara).find('.icon-white').removeClass('icon-ok').addClass('icon-remove');
                    $('#' + radomPara).closest('.alert-info').addClass('label-warning');
                }
                $('#' + radomPara).fadeOut(12000, function () {
                    $('#' + radomPara).remove();
                });
            } else {
                $('#' + radomPara).closest('.alert-info').addClass('label-success');
                $('#' + radomPara).fadeIn(500, function () {
                    $('#' + radomPara).fadeOut(8000, function () {
                        $('#' + radomPara).remove();
                    });
                });
            }
            $('#' + radomPara).bind('mouseover', function () {//悬浮时显示
                $('#' + radomPara).stop();
                $('#' + radomPara).fadeTo(200, 1)
            });
            $('#' + radomPara).bind('mouseout', function () {//离开时淡化
                $('#' + radomPara).stop();
                $('#' + radomPara).fadeOut(2000, function () {
                    $('#' + radomPara).remove();
                });
            });

        };
    })(window.coord ? window.coord : window.coord = {});
});