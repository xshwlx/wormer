define(['jquery',
    'modules/pages/commonconst/Constants', ], function ($) {
    (function (paasController) {
        paasController.initStatus = function (obj) {
            var tempstatus;
            if (obj.avmStatus) {
                tempstatus = obj.avmStatus;
            } else if (obj.appStatus) {
                tempstatus = obj.appStatus;
            } else if (obj.systemStatus) {
                tempstatus = obj.systemStatus;
            }
            if (tempstatus == Constants.STATUS_STARTED) {
                //obj.statusIcon = '<img  src="css/icons/status_ok.png" title="'+Constants.STATUS_STARTED+'"/>';
                obj.statusIcon = '<span class="label label-success">' + Constants.STATUS_STARTED + '</span>'
            }
            else if (tempstatus == Constants.STATUS_STOPPED) {
                //obj.statusIcon = '<img  src="css/icons/status-stopped.png" title="'+Constants.STATUS_STOPPED+'"/>';
                obj.statusIcon = '<span class="label label-important">' + Constants.STATUS_STOPPED + '</span>'
            }
            else if (tempstatus == Constants.STATUS_STARTING) {
                //obj.statusIcon = '<img  src="css/actionBar/action_busy1.gif" width="16px" height="16px" title="'+Constants.STATUS_STARTING+'"/>';
                obj.statusIcon = '<span class="label label-warning">' + Constants.STATUS_STARTING + '</span><img  src="css/actionBar/action_busy1.gif" width="16px" height="16px""/>'
            }
            else if (tempstatus == Constants.STATUS_STOPPING) {
                //obj.statusIcon = '<img  src="css/actionBar/action_busy1.gif" width="16px" height="16px" title="'+Constants.STATUS_STOPPING+'"/>';
                obj.statusIcon = '<span class="label label-warning">' + Constants.STATUS_STOPPING + '</span><img  src="css/actionBar/action_busy1.gif" width="16px" height="16px""/>'

            }
            else if (tempstatus == Constants.STATUS_REFRESHING_STATUS) {
                //obj.statusIcon = '<img  src="css/editor/refresh_12.png" title="'+Constants.STATUS_REFRESHING_STATUS+'"/>';
                obj.statusIcon = '<span class="label label-warning">' + Constants.STATUS_REFRESHING_STATUS + '</span><img  src="css/actionBar/action_busy1.gif" width="16px" height="16px""/>'
            }
            else if (tempstatus == Constants.STATUS_UNKNOWN) {
                //obj.statusIcon = '<img  src="css/ext/network-status-offline.png" title="'+Constants.STATUS_UNKNOWN+'"/>';
                obj.statusIcon = '<span class="label">' + Constants.STATUS_UNKNOWN + '</span>'
            }
            else if (tempstatus == Constants.STATUS_BEFORE_ALLOCATE) {
                obj.statusIcon = '<span class="label label-info">' + Constants.STATUS_BEFORE_ALLOCATE + '</span>'
                //obj.statusIcon = '<img  src="css/icons/status-offline.png" title="'+Constants.STATUS_BEFORE_ALLOCATE+'"/>';
            }
            else if (tempstatus == Constants.STATUS_NON_MATERIALIZED) {
                obj.statusIcon = '<span class="label label-warning">' + Constants.STATUS_NON_MATERIALIZED + '</span>'
                //obj.statusIcon = '<img  src="css/icons/status-away.png" title="'+Constants.STATUS_NON_MATERIALIZED+'"/>';
            }
            else if (tempstatus == Constants.STATUS_CREATE_PROFILE) {
                obj.statusIcon = '<span class="label label-warning">' + Constants.STATUS_CREATE_PROFILE + '</span><img  src="css/actionBar/action_busy1.gif" width="16px" height="16px""/>'
                //obj.statusIcon ='<span class="label label-info"><img src="css/images/loading_busy.gif"  width="16px" height="16px"/>'+ Constants.STATUS_CREATE_PROFILE +'</span>';
            } else if (tempstatus == Constants.STATUS_NON_INSTANCE) {
                obj.statusIcon = '<span class="label">' + Constants.STATUS_NON_INSTANCE + '</span>'
                //obj.statusIcon = '<img  src="css/icons/notinstance.png" title="'+Constants.STATUS_NON_INSTANCE+'" width="12px" height="12px"/>';
            }
            else {
                obj.statusIcon = '<span class="label label-info">' + tempstatus + '</span>';
            }
        };
        paasController.initServerSource = function (obj) {
            if (obj.softwareType == AVMachineVOConstant.TYPE_WEB) {
                obj.ServerIcon = '<img  src="css/icons/Web_32.png" title="' + obj.softwareType + '"/>';
            }
            if (obj.softwareType == AVMachineVOConstant.TYPE_MNG_SERVER) {
                obj.ServerIcon = '<img  src="css/icons/dmgr.gif" title="' + obj.softwareType + '"/>';
            }
            if (obj.softwareType == AVMachineVOConstant.TYPE_MW) {
                obj.ServerIcon = '<img  src="css/icons/Mw_32.png" title="' + obj.softwareType + '"/>';
            }
            if (obj.softwareType == AVMachineVOConstant.TYPE_DB) {
                obj.ServerIcon = '<img  src="css/icons/database_32.png" title="' + obj.softwareType + '"/>';
            } else if (obj.softwareType == 'NC') {
                obj.ServerIcon = '<img  src="css/icons/database_32.png" title="' + obj.softwareType + '"/>';
            }
        };
        paasController.initAppSource = function (obj) {
            obj.ServerIcon = '<img  width="32px" height ="32px" src="css/icons/application_32.png" title="' + obj.softwareType + '"/>';
        };
        paasController.initMachineSource = function (obj) {
            obj.statusIcon = '<img  src="css/icons/status-offline.png" title="' + obj.vmSystemType + '"/>';
            obj.ServerIcon = '<img  width="32px" height ="32px" src="css/icons/computer_32.png" title="' + obj.systemType + ':' + obj.agentIP + '"/>';
        };

        paasController.initBtnStatus = function (obj) {
            var statusIcon = {};
            var tempsBtmtatus = "";
            if (typeof obj == "object") {
                if (obj.avmStatus) {
                    tempsBtmtatus = obj.avmStatus;
                } else if (obj.appStatus) {
                    tempsBtmtatus = obj.appStatus;
                } else if (obj.systemStatus) {
                    tempsBtmtatus = obj.systemStatus;
                }
            } else if (typeof obj == "string") {
                tempsBtmtatus = obj;
            }
            if (tempsBtmtatus == Constants.STATUS_STARTING) {
                statusIcon.startStatusIcon = '<img class="cloud-icon-img" src="css/images/loading_busy.gif" width="16px" height="16px" /><span>' + coord.lang.shared.btnstarting + '</span>';
                statusIcon.stopStatusIcon = '<img class="cloud-icon-img" src="css/icons/stop.png" /><span>' + coord.lang.shared.btnstop + '</span>';
            } else if (tempsBtmtatus == Constants.STATUS_STOPPING) {
                statusIcon.startStatusIcon = '<img class="cloud-icon-img" src="css/icons/stop.png" /><span>' + coord.lang.shared.btnstart + '</span>';
                statusIcon.stopStatusIcon = '<img class="cloud-icon-img" src="css/images/loading_busy.gif" width="16px" height="16px"  /><span>' + coord.lang.shared.btnstopping + '</span>';
            } else {
                statusIcon.startStatusIcon = '<img class="cloud-icon-img" src="css/icons/start.png" /><span>' + coord.lang.shared.btnstart + '</span>';
                statusIcon.stopStatusIcon = '<img class="cloud-icon-img" src="css/icons/stop.png" /><span>' + coord.lang.shared.btnstop + '</span>';
            }
            return statusIcon;
        };
        paasController.initClusterBtnStatus = function (obj) {
            var statusIcon = "";
            var tempsBtmtatus = "";
            if (typeof obj == "string") {
                tempsBtmtatus = obj;
            }
            if (tempsBtmtatus == Constants.STATUS_STARTING) {
                statusIcon = '<img class="cloud-icon-img" src="css/images/loading_busy.gif" width="16px" height="16px" /><span>' + coord.lang.shared.btnstarting + '</span>';
            } else if (tempsBtmtatus == Constants.STATUS_STOPPING) {
                statusIcon = '<img class="cloud-icon-img" src="css/images/loading_busy.gif" width="16px" height="16px"  /><span>' + coord.lang.shared.btnstopping + '</span>';
            } else if (tempsBtmtatus == Constants.STATUS_STARTED) {
                statusIcon = '<img class="cloud-icon-img" src="css/icons/start.png" /><span>' + coord.lang.shared.btnstart + '</span>';
            } else if (tempsBtmtatus == Constants.STATUS_STOPPED) {
                statusIcon = '<img class="cloud-icon-img" src="css/icons/stop.png" /><span>' + coord.lang.shared.btnstop + '</span>';
            } else if (tempsBtmtatus == Constants.STATUS_SYNCHRONIZING) {
                statusIcon = '<img class="cloud-icon-img" src="css/images/loading_busy.gif" width="16px" height="16px" /><span>' + coord.lang.shared.btnsyning + '</span>';
            } else if (tempsBtmtatus == Constants.STATUS_DEPLOYING) {
                statusIcon = '<img class="cloud-icon-img" src="css/images/loading_busy.gif" width="16px" height="16px" /><span>' + coord.lang.shared.btndeploying + '</span>';
            }
            return statusIcon;
        };

    })(window.paasController ? window.paasController : window.paasController = {});
});