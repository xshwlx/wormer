define(['jquery',
    'underscore',
    'backbone', 'paasController'], function () {
    _.templateSettings = {
        interpolate: /\{\{(.+?)\}\}/g
    };
    Backbone.template = function (obj, data, option) {
        if (!data) {
            return "";
        }

        var str = "",
            array = [];

        if (data instanceof Array) {
            if (!data.length) {
                return "";
            }
            array = data;
        } else {
            array[0] = data;
        }
        _.each(array, function (item) {
            str += _.template(obj, item);//利用underscore库来编译模版
        });
        return str;
    };
    Backbone.ajax = function (apiName, str, isAsync, successCallback, errorCallback, viewObj, isDealCallBack) {
        var sessionid = coord.context.getUser('sessionkey');
        //var date = (new Date()).getTime();
        var action = apiName;
        var jsonParam = str;
        $.ajax({
            type: "POST",
            data: {action: action, jsonParam: jsonParam, sessionid: sessionid},
            url: "/chefu360/controller",
            dataType: "json",
            async: isAsync,
            success: function (json) {
                if (successCallback && (typeof successCallback == 'function')) {
                    if (isDealCallBack) {
                        successCallback.call(viewObj, json);
                    } else if (viewObj.isCurrentPage) {
                        successCallback.call(viewObj, json);
                    }
                }
            },
            error: function (e) {
                if (errorCallback && (typeof errorCallback == 'function')) {
                    if (isDealCallBack) {
                        errorCallback.call(viewObj, e);
                    } else if (viewObj.isCurrentPage) {
                        errorCallback.call(viewObj, e);
                    }
                }
            }
        });
    };

    Backbone.sync = function (apiName, jsonString, isAsync, successCallback, errorCallback, viewObj) {
        if (typeof isAsync == 'function') {
            errorCallback = successCallback;
            successCallback = isAsync;
            isAsync = true;
        }
        Backbone.ajax(apiName, jsonString, isAsync, successCallback, errorCallback, viewObj);
    };

    Backbone.language = function (text, data, settings) {
        var render;
        settings = _.defaults({}, settings, _.templateSettings);
        var noMatch = /(.)^/;
        var escapes = {
            "'": "'",
            '\\': '\\',
            '\r': 'r',
            '\n': 'n',
            '\t': 't',
            '\u2028': 'u2028',
            '\u2029': 'u2029'
        };

        var escaper = /\\|'|\r|\n|\t|\u2028|\u2029/g;
        // Combine delimiters into one regular expression via alternation.
        var matcher = new RegExp([
            (settings.escape || noMatch).source, (settings.interpolate || noMatch).source, (settings.evaluate || noMatch).source].join('|') + '|$', 'g');

        // Compile the template source, escaping string literals appropriately.
        var index = 0;
        var source = "__p+='";
        text.replace(matcher, function (match, escape, interpolate, evaluate, offset) {
            source += text.slice(index, offset)
                .replace(escaper, function (match) {
                    return '\\' + escapes[match];
                });

            if (escape) {
                source += "'+\n((__t=(" + escape + "))==null?'':_.escape(__t))+\n'";
            }
            if (interpolate) {
                // source += "'+\n((__t=(" + interpolate + "))==null?'':__t)+\n'";
                //source += "'+\n((__t=(__template." + interpolate + "))==null?'{{" + interpolate + "}}':__t)+\n'";
                var tempInterpolate = interpolate;
                if (interpolate.StartsWith('lang.')) {
                    //tempInterpolate = interpolate.replace(/\./g,"-");
                    tempInterpolate = interpolate;
                } else {
                    tempInterpolate = "{{" + interpolate + "}}";
                }
                source += "'+\n((__t=(__template." + interpolate + "))==null?'" + tempInterpolate + "':__t)+\n'";
            }
            if (evaluate) {
                source += "';\n" + evaluate + "\n__p+='";
            }
            index = offset + match.length;
            return match;
        });
        source += "';\n";

        // If a variable is not specified, place data values in local scope.
        // if (!settings.variable) source = 'with(obj||{}){\n' + source + '}\n';
        if (!settings.variable) source = 'var __template={};for(var __prop in obj){__template[__prop]=obj[__prop];}\n' + source + '\n';
        source = "var __t,__p='',__j=Array.prototype.join," +
            "print=function(){__p+=__j.call(arguments,'');};\n" + source + "return __p;\n";
        try {
            render = new Function(settings.variable || 'obj', '_', source);
        } catch (e) {
            e.source = source;
            throw e;
        }

        if (data) return render(data, _);
        var template = function (data) {
            return render.call(this, data, _);
        };

        // Provide the compiled function source as a convenience for precompilation.
        template.source = 'function(' + (settings.variable || 'obj') + '){\n' + source + '}';

        return template;
    };
    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, // month
            "d+": this.getDate(), // day
            "h+": this.getHours(), // hour
            "m+": this.getMinutes(), // minute
            "s+": this.getSeconds(), // second
            "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
            "S": this.getMilliseconds()
            // millisecond
        }
        if (/(y+)/.test(format))
            format = format.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(format))
                format = format.replace(RegExp.$1,
                    RegExp.$1.length == 1 ? o[k] : ("00" + o[k])
                        .substr(("" + o[k]).length));
        return format;
    };
    paasController.cloneObj = function (obj) {
        if (!obj) {
            return obj;
        }
        var o = obj.constructor === Array ? [] : {};
        for (var i in obj) {
            if (obj.hasOwnProperty(i)) {
                o[i] = typeof obj[i] === "object" ? paasController.cloneObj(obj[i]) : obj[i];
            }
        }
        return o;
    };
    String.prototype.replaceAll = function (reallyDo, replaceWith, ignoreCase) {
        if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
            return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi" : "g")), replaceWith);
        } else {
            return this.replace(reallyDo, replaceWith);
        }
    };

});