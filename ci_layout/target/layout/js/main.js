// Filename: main.js
// Require.js allows us to configure shortcut alias
// There usage will become more apparent further along in the tutorial.
require.config({
    enforceDefine: false,
    baseUrl: ".",
    paths: {
        'date': 'jslib/date',
        'jquery': 'jslib/jquery/jquery-1.8.3',
        'knockout': 'jslib/knockout/knockout-2.2.1',
        'jqueryBlockUI': 'jslib/jquery/jquery.blockUI',
        'cookie': 'jslib/jquery/jquery.cookies',
        'json': 'jslib/jquery/jquery.json',
        'jqueryUi': 'jslib/jquery/jquery-ui/jquery-ui-1.10.2.custom',
        'jqueryMD5': 'jslib/jquery/jquery.md5',
        'smartwizard': 'jslib/jquery/jquery.smartWizard-2.0',
        'jqgrid': 'jslib/jquery/jquery-jqGrid/jquery.jqGrid',
        'grid-cn': 'jslib/jquery/jquery-jqGrid/i18n/grid.locale-cn',
        'grid-en': 'jslib/jquery/jquery-jqGrid/i18n/grid.locale-en',
        'uploadify': 'jslib/jquery/jquery.uploadify',

        'underscore': 'jslib/underscore/underscore',
        'backbone': 'jslib/backbone/backbone',
        'bootstrap': 'jslib/bootstrap/bootstrap',
        'bootstrap-carousel': 'jslib/bootstrap/bootstrap-carousel',
        'css': 'jslib/require/css',
        'text': 'jslib/require/text',
        'base': 'js/base/base',
        'menu': 'js/base/role',
        'basepage': 'js/base/page',
        'context': 'js/base/context',
        'util': 'js/util',
        'ie-placeholder': "jslib/ie-placeholder",
        'alert': 'js/base/alert',
        'progressBar': 'js/base/progressBar',
        'initStatus': 'js/base/initStatus',
        'logger': 'js/base/Logger',
        'CLIlogger': 'js/base/CLILogger',
        'distributeData': 'js/base/pageDataMessage',
        'config': 'js/config',
        'paasController': 'js/base/paasControllerUtil',
        'zh': 'js/lang/zh'
    },
    shim: {
        'jquery': {
            exports: '$'
        },
        'jqueryBlockUI': {
            deps: ['jquery']
        },
        'json': {
            deps: ['jquery']
        },
        'cookie': {
            deps: ['jquery']
        },
        'jqueryUi': {
            deps: ['jquery']
        },
        'jqueryMD5': {
            deps: ['jquery']
        },
        'uploadify': {
            deps: ['jquery']
        },
        'underscore': {
            exports: "_"
        },
        'backbone': {
            deps: ['jquery', 'underscore'],
            exports: 'Backbone'
        },
        'base': {
            deps: ['jquery', 'underscore', 'backbone', 'util']
        },
        'bootstrap': {
            deps: ['jquery']
        },
        'bootstrap-carousel': {
            deps: ['jquery']
        },
        'util': {
            deps: ['jquery']
        },
        'ie-placeholder': {
            deps: ['jquery']
        }
    }
});

//Load the mainframe module and execute the initialiazation
require(['js/init'], function (MainFrame) {
    MainFrame.initialize();
});
