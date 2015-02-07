define([
    'underscore',
    'backbone'], function () {
    (function (coord) {
        var Context = Backbone.Model.extend({
            defaults: {
                data: {},
                lang: "",
                brdCrbArray: [],
                zoneId: "",
                resetPage: false,
                toPage: false,
                refreshPage: false,
                pageLink: '',
                pageDetailLink: '',
                contextPath: '',
                brdCrbItem: '',
                brdCrbItemRemove: false,
                modalItem: '',
                linkPage: '',
                linkPageAppend: '',
                modalItemRemove: false,
            },

            user: {
                user_code: '',
                user_pwd: ''
            },

            initialize: function () {
            },

            setUser: function (obj, value) {
                if (typeof obj == "string") {
                    this.user[obj] = value;
                } else {
                    for (key in obj) {
                        this.user[key] = obj[key];
                    }
                }
            },

            getUser: function (key) {
                if (key) {
                    return this.user[key];
                }
                return this.user;
            },

            setData: function (obj, value) {
                if (typeof obj == "string") {
                    this.attributes.data[obj] = value;
                } else {
                    for (key in obj) {
                        this.attributes.data[key] = obj[key];
                    }
                }
            },

            getData: function (key) {
                if (key) {
                    return this.data[key];
                }
                return this.data;
            }
        });
        coord.context = new Context();
    })(window.coord ? window.coord : window.coord = {});
});