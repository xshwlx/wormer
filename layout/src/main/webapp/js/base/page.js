define(['jquery', 'jqueryUi',
    'base', 'util', 'alert', 'progressBar', 'logger'
], function () {
    var PageModel = Backbone.Model.extend({
        defaults: {},
        initialize: function () {
        }
    });

    var PageView = Backbone.View.extend({
        el: $('body'),
        events: {},
        isCurrentPage: false,
        curPagePath: '',
        parentPageView: '',
        /* for pagination */
        pageCount: 1,
        activePageNum: 1,
        firstPageNum: 1,
        numPerPage: 10, // record number per page
        numModalPerPage: 5,
        maxPagiNum: 5, // pagination max nav page number
        subscriber: [],
        returnDatas: {},//page return data
        initialize: function () {
        },
        beforeRender: function (data) {
            return data;
        },
        addBrdCrb: function (brdCrbItem) {
            coord.context.set('brdCrbItem', brdCrbItem);
        },
        removeBrdCrb: function () {
            coord.context.set('brdCrbItemRemove', true);
        },
        /*
         * wangzhhr modal page
         */
        addModal: function (modalModuleName) {
            coord.context.set({
                'modalItem': modalModuleName
            });
        },
        addLinkPage: function (modalModuleName) {
            coord.context.set({
                'linkPage': modalModuleName
            });
        },
        addLinkAppendPage: function (modalModuleName) {
            coord.context.set({
                'linkPageAppend': modalModuleName
            });
        },
        addTaskToPage: function (taskObj) {
            coord.context.set({
                'taskObj': taskObj
            });
        },
        /*
         * load page
         */
        render: function (pageUrl, contextPath) {
            this.delegateEvents();
            this.contextPath = contextPath;
            var contextArray = contextPath.split('/');
            contextArray.pop();
            var _this = this;
            var len = contextArray.length;
            require(["text!" + pageUrl], function (data) {

                var temp = data;
                temp = Backbone.language(data, coord);
                if (_this.beforeRender && (typeof _this.beforeRender == "function")) {
                    temp = _this.beforeRender(temp);
                }
                $('#page-content').html(temp);
                _this.activePageNum = 1;
                _this.firstPageNum = 1;
                var showSide = coord.context.get('showSide');
                if (showSide) {
                    $('#sideBarDiv').show();
                } else {
                    $('#sideBarDiv').hide();
                }
                //this.replacePage();
                //coord.adjustDiv();
                if (_this.afterRender && (typeof _this.afterRender == "function")) {
                    _this.replacePage();
                    _this.roleProcess();
                    _this.afterRender();
                }
            });
        },
        /**
         每个角色加入的class如下：
         0 = SYS_ADMIN,
         1 = RENTER_ADMIN 租户管理员
         2 = SYS_USER 系统用户
         3 = RENTER_USER 租户用户
         角色权限处理，如果只有admin账户专有，那么加上class SYS_ADMIN，
         如果RENTER_ADMIN专有，加上class RENTER_ADMIN
         */
        roleProcess: function () {
            var role = coord.context.getUser().type;
            switch (role) {
                case '3'://renter user
                    $('.SYS_ADMIN').remove();
                    $('.RENTER_ADMIN').remove();
                    $('.SYS_USER').remove();
                    break;
                case '2'://sys user
                    $('.SYS_ADMIN').remove();
                    $('.RENTER_ADMIN').remove();
                    $('.RENTER_USER').remove();
                    break;
                case '1'://renteradmin
                    $('.SYS_ADMIN').remove();
                    break;
                default://admin
            }

        },
        replacePage: function () {
            $('#mainContent').removeClass('cloud-home cloud-pro cloud-content');
            if (this.contextPath == 'home') {
                $('#sidesHide').hide();
                $('#mainContent').addClass('cloud-home');
//					$('#sidesShow').hide();
                $('#sidesHide').hide();
            } else {
                if (this.contextPath.StartsWith('profile')) {
                    $('#mainContent').addClass('cloud-pro');
                } else {
                    $('#mainContent').addClass('cloud-content');
                }
                $(".sideBarShowClass").click();
            }

            var showSide = coord.context.get('showSide');
            if (true) {
                $('#sideBarDivID').show();
            } else {
                $('#sideBarDivID').hide();
            }
            coord.adjustDiv();
        },

        linkPageRender: function (pageUrl, option, callback) {
            this.delegateEvents();
            var _this = this;
            require(
                ["text!" + pageUrl], function (data) {
                    var temp = data;
                    temp = Backbone.language(data, coord);
                    $('#mainContent').html(temp);
                    if (_this.afterRender && (typeof _this.afterRender == "function")) {
                        _this.afterRender();
                        if (callback && (typeof callback == "function")) {
                            callback();
                        }
                    }
                });
        },
        linkPageAppendRender: function (pageUrl, option, callback) {
            this.delegateEvents();
            var _this = this;
            require(
                ["text!" + pageUrl], function (data) {
                    var temp = data;
                    temp = Backbone.language(data, coord);
                    $('#mainContent').append(temp);
                    if (_this.afterRender && (typeof _this.afterRender == "function")) {
                        _this.afterRender();
                        if (callback && (typeof callback == "function")) {
                            callback();
                        }
                    }
                });
        },

        modalRender: function (pageUrl, option) {
            this.delegateEvents();
            var _this = this;
            require(
                ["text!" + pageUrl], function (data) {
                    var temp = data;
                    temp = Backbone.language(data, coord);
                    if (option) {
                        $('#myModalSample').append(temp);
                    } else {
                        $('#myModalSample').html(temp);
                    }
                    // coord.adjustDiv();
                    if (_this.afterRender && (typeof _this.afterRender == "function")) {
                        _this.afterRender();
                    }
                });
        },
        logRender: function (pageUrl, contextPath) {
            this.delegateEvents();
            var _this = this;
            require(
                ["text!" + pageUrl], function (data) {
                    var temp = data;
                    temp = Backbone.language(data, coord);
                    $('#logContent').html(temp);
                    // coord.adjustDiv();
                    if (_this.afterRender && (typeof _this.afterRender == "function")) {
                        _this.afterRender();
                    }
                });
        },
        /*
         * to override after load the page @para curPage:
         * current page obj
         */
        afterRender: function () {

        },
        /*
         * load the template with the given data
         */
        template: function (obj, data, option) {
            Backbone.template(obj, data, option);
        },
        /*
         * return to last page @para data: the necessary data
         * for the return
         */
        returnPage: function (data) {
            var contextArray = coord.context.get('brdCrbArray');
            var contextData = coord.context.get('data');
            var returnDatas = coord.context.get('returnDatas');
            contextArray.pop();
            if (data && (typeof data == 'object')) {
                for (var i in data) {
                    contextData[i] = data[i];
                }
            }
            coord.context.set({
                'brdCrbArray': contextArray,
                'data': contextData,
                'returnDatas': returnDatas,
                'toPage': true
            });
        },
        /*
         * refresh current page
         */
        refreshPage: function (data) {
            var contextData = coord.context.get('data');
            if (data && (typeof data == 'object')) {
                for (var i in data) {
                    contextData[i] = data[i];
                }
            }
            coord.context.set({
                'data': contextData,
                'refreshPage': true
            });
        },


        /*
         * change the page @para name: the name showed in
         * breadcrumbs @para module: the folder name in file
         * system @para data: the necessary data for the new
         * page @ name and module undefined ----return to last
         * page @
         */
        toPage: function (name, module, data) {
            // var contextData = coord.context.get('data');
            var contextData = {};
            if (data && (typeof data == 'object')) {
                for (var i in data) {
                    contextData[i] = data[i];
                }
            }
            if (module && module.StartsWith('/')) {
                coord.context.set({
                    'data': contextData,
                    'toPage': true,
                    'pageLink': {
                        name: name,
                        module: module
                    }
                });
            } else {
                var contextArray = coord.context
                    .get('brdCrbArray');
                var nameArray = name.split('/');
                var len = nameArray.length;
                if (nameArray.length) {
                    for (var i = 0; i < len - 1; i++) {
                        contextArray.push({
                            name: nameArray[i],
                            module: nameArray[i]
                        });
                    }
                    contextArray.push({
                        name: nameArray[len - 1],
                        module: module
                    });
                } else {
                    contextArray.push({
                        name: name,
                        module: module
                    });
                }
                this.returnDatas.activePageNum = this.activePageNum;
                coord.context.set({
                    'brdCrbArray': contextArray,
                    'data': contextData,
                    'returnDatas': this.returnDatas,
                    'toPage': true
                });
            }
        },
        leavePage: function () {

        },
        toDetailPage: function (name, module, data) {
            // var contextData = coord.context.get('data');
            var contextData = {};
            if (data && (typeof data == 'object')) {
                for (var i in data) {
                    contextData[i] = data[i];
                }
            }
            if (module && module.StartsWith('/')) {
                coord.context.set({
                    'data': contextData,
                    'toPage': true,
                    'pageDetailLink': {
                        name: name,
                        module: module
                    }
                });
            }
        },
        /*
         * load a new div to the dest
         */
        loadDiv: function (dest, fileName, data, callback) {
            if (typeof data == 'function') {
                callback = data;
                data = "";
            }

            var _this = this;
            require(
                ['text!' + _this.curPagePath + fileName + '.html'], function (htmlFile) {
                    // var temp = data;
                    var temp = Backbone.language(htmlFile,
                        coord);
                    if (data) {
                        temp = Backbone
                            .template(temp, data);
                    }
                    $(dest).append(temp);
                    if (callback && (typeof callback == "function")) {
                        callback.call(_this);
                    }
                });
        },
        clearDiv: function (dest, fileName) {
            var _this = this;
            require(
                ['text!' + _this.curPagePath + fileName + '.html'], function (htmlFile) {
                    $(dest).html("");
                });
        },
        /*
         * @para apiName : api name @para options @para
         * callback: execute with the data
         */
        sync: function (apiName, options, isAsync, successCallback, errorCallback) {
            Backbone.sync(apiName, options, isAsync,
                successCallback, errorCallback, this);
        },
        syncDealCallBack: function (apiName, options, isAsync, successCallback, errorCallback) {
            if (typeof isAsync == 'function') {
                errorCallback = successCallback;
                successCallback = isAsync;
                isAsync = true;
            }
            Backbone.ajax(apiName, options, isAsync,
                successCallback, errorCallback, this, true);
        },
        require: function (array, callback) {
            require(array, callback.call(this));
        },
        loadModal: function (option, callback) {
            if (!option) {
                option = {
                    title: 'Info',
                    content: 'Content',
                    btnName: 'Ok'
                }
            }
            option.btnName = coord.lang.shared.ok;
            $('#frameAlertDiv #modalLabel').html(option.title);
            $('#frameAlertDiv #modalContent').html(option.content);
            $('#frameAlertDiv #cancelBtn').html(coord.lang.shared.cancel);
            $('#frameAlertDiv #okBtn').html(option.btnName);
            $('#frameAlertDiv #okBtn').show();
            $('#frameAlertDiv #cancelBtn').show();
            if (option.content.length > 30) {
                $('#frameAlertDiv .modal-body').css('text-align', 'left');
            } else {
                $('#frameAlertDiv .modal-body').css('text-align', 'center');
            }
            if (this.events['click #frameAlertDiv #okBtn']) {
                delete this.events['click #frameAlertDiv #okBtn'];
            }
            if (typeof callback == "string") {
                this.events['click #frameAlertDiv #okBtn'] = callback;
            } else if (typeof callback == "function") {
                this.events['click #frameAlertDiv #okBtn'] = callback;
            } else if (callback == true) {
                $('#frameAlertDiv #cancelBtn').hide();
            }
            this.delegateEvents();
            $('#frameAlertDiv').modal('show');
        },
        initPagination: function (count, clickFunction, cssclass) {
            var _this = this;
            if (this.activePageNum == 1) {
                var currReturnDatas = coord.context.get('returnDatas');
                if (typeof(currReturnDatas) != "undefined" && currReturnDatas != null) {
                    var returnActivePageNum = currReturnDatas.activePageNum;
                    this.activePageNum = returnActivePageNum || 1;
                    coord.context.set('returnDatas', {});
                }
            }
            var pageCount = Math.ceil(count / this.numPerPage) || 1;
            this.pageCount = pageCount;
            if (this.activePageNum > this.pageCount) {
                this.activePageNum = 1;
            }
            var pageIndex = '';
            var lastPageNum = (pageCount - this.maxPagiNum + 1 > this.firstPageNum) ? this.firstPageNum + this.maxPagiNum - 1 : pageCount;
            for (var i = this.firstPageNum; i <= lastPageNum; i++) {
                pageIndex += '<li><a>' + i + '</a></li>';
            }

            $pagination = $('.pagination');
            var pageTotal = '<div>' + coord.lang.shared.totalRecords + ':' + count + '</div>';
            var pagHtml = '<ul><li><a  id="pagePrev"><i class="icon-backward"></i> {{lang.pagination.prev}}</a>' + pageIndex + '<li><a id="pageNext">{{lang.pagination.next}} <i class="icon-forward"></i></a></li>' + '</ul>' + pageTotal;
            $pagination.html(Backbone.language(pagHtml, coord));
            this.changePaginationStatus();
            $pagination.find('li').bind('click', function (e) {
                _this.pagiClicked(e, clickFunction);
            });
        },
        changePaginationStatus: function () {
            $pagination = $('.pagination');
            $pagination.find('.active').removeClass('active');
            var index = (this.activePageNum % this.maxPagiNum == 0) ? this.maxPagiNum : (this.activePageNum % this.maxPagiNum);
            var newActive = $pagination.find('li').get(index);
            $(newActive).addClass('active');
            if (this.activePageNum == this.pageCount) {
                $('#pageNext').closest('li').addClass(
                    'disabled');
            }
            if (this.activePageNum == 1) {
                $('#pagePrev').closest('li').addClass(
                    'disabled');
            }
        },
        pagiClicked: function (e, clickFunction) {
            var target = $(e.target);
            if (target.context.localName == 'i') {
                target = target.closest('a');
            }
            if (target.context.localName == 'li') {
                target = target.children('a');
            }
            var changeFlag = false;
            switch (target.attr('id')) {
                case 'pagePrev':
                    if (this.activePageNum > 1) {
                        if ((this.activePageNum > this.maxPagiNum) && (this.activePageNum % this.maxPagiNum == 1)) {
                            this.firstPageNum = this.activePageNum - this.maxPagiNum;
                        }
                        this.activePageNum--;
                        changeFlag = true;
                    }
                    break;
                case 'pageNext':
                    if (this.activePageNum < this.pageCount) {
                        if (this.activePageNum % this.maxPagiNum == 0) {
                            this.firstPageNum = this.activePageNum - 0 + 1;
                        }
                        this.activePageNum++;
                        changeFlag = true;
                    }
                    break;
                default:
                    this.activePageNum = target.html();
                    changeFlag = true;
            }
            if (changeFlag) {
                this.changePaginationStatus();
                if (clickFunction && typeof clickFunction == "function") {
                    clickFunction();
                    return;
                }
                this.loadPageByPagi();
            }
        },
        loadPageByPagi: function () {
            if (typeof(this.pageUpdateShowDatas) == 'function') {
                this.pageUpdateShowDatas();
            } else {
                this.afterRender();
            }

        },
        queryJob: function (jobid, timeout, successCallback, errorCallback) {
            var _this = this;
            if (!jobid) {
                return;
            }
            coord.autoblockUI();
            var jobstatus = null;
            var iIntervalId = null;
            iIntervalId = setInterval(function (jobid) {
                Backbone
                    .sync(
                    'queryAsyncJobResult', {
                        jobId: jobid
                    }, function (json) {
                        // json.queryasyncjobresultresponse.jobstatus;
                        jobstatus = json.queryasyncjobresultresponse.jobstatus;
                        console
                            .log(json.queryasyncjobresultresponse.jobstatus);
                        if (jobstatus != 0) {
                            clearInterval(iIntervalId);
                        }
                        if (jobstatus == 1) {
                            coord.unblockUI();
                            coord.alert("Action Successful!");
                            console.log(json);
                            if (successCallback && (typeof successCallback == "function")) {
                                successCallback
                                    .call(
                                        _this,
                                        json);
                            }
                        }
                        if (jobstatus == 2) {
                            coord
                                .unblockUI();
                            coord
                                .alert(
                                json.queryasyncjobresultresponse.jobresult.errortext, {
                                    failed: true
                                });
                            if (errorCallback && (typeof errorCallback == "function")) {
                                errorCallback
                                    .call(
                                        _this,
                                        json);
                            }
                        }
                    });
            }, 3000, jobid);
        },

        getTime: function (format) {
            return new Date().format(format);
        },

        // 增加异步消息
        addSubscriber: function (msgId, read) {
            if (msgId)
                this.subscriber[msgId] = read;
        },
        // 删除异步消息
        removeSubscriber: function (msgId) {
            if (msgId)
                delete this.subscriber[msgId];
        },
        // 接收消息并处理
        onCCMessage: function (fetchObj) {
            var messageArray = fetchObj.messages;
            messageArray.sort(function (a, b) {
                return a.msgId > b.msgId ? 1 : -1
            });
            if ("TaskMessage" == fetchObj.subscriber) {
                this.onTaskMessage(messageArray);
            } else if ("LogMessage" == fetchObj.subscriber) {
                this.onLogMessage(messageArray);
            } else if ("ProgressMessage" == fetchObj.subscriber) {
                this.onProgressMessageDepl(messageArray);
            } else if ("CLIMessage" == fetchObj.subscriber) {
                this.onCLIMessage(messageArray);
            } else if ("PageDataMessage" == fetchObj.subscriber) {
                this.onPageDataMessage(messageArray);
            }

        },
        onTaskMessage: function (messageArray) {
            for (var i = 0; i < messageArray.length; i++) {
                var updateMethod = this.subscriber["TaskUpdateMessage"];
                if (updateMethod != null) {
                    updateMethod(messageArray[i]);
                }

                var detailMethod = this.subscriber["TaskDetail" + messageArray[i].taskId];
                if (detailMethod != null) {
                    detailMethod(messageArray[i]);
                }

                var taskMethod = this.subscriber[messageArray[i].taskId];
                if (taskMethod != null && this.isCurrentPage) {
                    taskMethod(messageArray[i]);
                    if (messageArray[i].completed)
                        this
                            .removeSubscriber(messageArray[i].taskId);
                }
            }
        },
        onLogMessage: function (messageArray) {
            filterLog(messageArray);
            var method = this.subscriber["LogMessage"];
            if (method != null) {
                method(messageArray);
            }
        },
        onCLIMessage: function (messageArray) {
            filterLog(messageArray);
            var method = this.subscriber["CLIMessage"];
            if (method != null) {
                method(messageArray);
            }
        },
        onPageDataMessage: function (messageArray) {
            filterLog(messageArray);
            var method = this.subscriber["PageDataMessage"];
            if (method != null) {
                method(messageArray);
            }
        },
        onProgressMessageDepl: function (messageArray) {
            filterProgress(messageArray);
            var method = this.subscriber["ProgressMessage"];
            if (method != null) {
                method(messageArray);
            }
        },
        onProgressMessage: function (messageArray) {
            for (var i = 0; i < messageArray.length; i++) {
                var updateMethod = this.subscriber["Progress" + messageArray[i].taskId];
                if (updateMethod != null) {
                    updateMethod(messageArray[i]);
                }
            }
        }
    });
    return {
        Model: PageModel,
        View: PageView
    };
});