define([], function (obj) {
    var config = function (obj) {
        obj.menu = {
        };
        obj.login = {
            name: 'Name',
            password: 'Password',
            loginBtn: 'Login',
            logining: 'Logining',
            inputError: 'Input Error, only 0-9a-zA-Z_ allowed.',
            nonExist: 'Please check your name and password.',
            timeout: 'Timeout.Please login again.',
            offline: 'You are offline.',
            connectError: 'The connection is blocked.',
            serverError: 'Can not find server.',
            accountLocked: 'The account is locked/disabled.',
            ieVersion: 'Please update your IE.'
        };
        obj.logout = {
            msg: 'Are you sure to logout?'
        }
        obj.search = {
            placeholder: '',
            advanced: 'Advanced',
            searchTitle: 'Input search condition,please!',
            searchCondition: 'Condition',
            searchResult: 'Result',
            tiao: 'Count.'
        };
        obj.pagination = {
            prev: 'Previous',
            next: 'Next'
        };
        obj.home = {
            dataCenter: 'Data Center',
            computer: 'Computer',
            monitor: 'monitor',
            softwareOpration: 'softwareOpration',
            generalInstallation: 'generalInstallation',
            wasInstallation: 'wasInstallation',
            webserverInstallation: 'webserverInstallation',
            uapInstallation: 'uapInstallation',
            wlsInstallation: 'wlsInstallation',
            computerOpration: 'computerOpration',
            applicationOpration: 'applicationOpration',
            middlewareNode: 'middlewareNode',
            webServer: 'webServer',
            managerServer: 'managerServer',
            uapCluster: 'uapCluster',
            taskMonitor: 'Task Monitor',
        };
        /* the shared label */
        obj.shared = {
            about: 'About',
            aboutContent: 'UAP CI(V1.0)<br>All rights reserved.',
            search: 'search',
            task: 'Task',
            space: ' ',
            more: 'More',
            add: 'New',
            register: 'Register',
            edit: 'Edit',
            copy: 'Copy',
            detail: 'Detail',
            progress: 'progress',
            cancelProgress: 'cancelProgress',
            taskDetail: 'taskDetail',
            download: 'Download',
            delete: 'Delete',
            cancel: 'Cancel',
            return: 'Return',
            close: 'close',
            install: 'install',
            uninstall: 'uninstall',
            select: 'select',
            open: 'open',
            read: 'Read',
            save: 'Save',
            reset: 'Reset',
            retry: 'retry',
            refresh: 'refresh',
            set: 'set',
            clear: 'clear',
            ok: 'Yes',
            logout: 'Logout',
            profile: 'Profile',
            log: 'Log',
            maxLog: 'maxLog Count',
            name: 'Name',
            description: 'Description',
            id: 'ID',
            type: 'Type',
            cpu: 'CPU',
            mhz: '(MHz)',
            memory: 'Memory',
            rate: 'Rate',
            tag: 'tags',
            ha: 'HA',
            created: 'Created',
            host: 'Host',
            view: 'View',
            number: 'Number',
            time: 'time',
            value: 'value',
            action: 'action',
            capacity: 'Capacity',
            deleteAlert: 'Are your sure to delete it?',
            synchronous: 'synchronous',
            synchronousJenkinsAlert: 'Are your sure to synchronous it?',
            refreshJenkinsAlert: 'Are your sure to delete it?',
            downloadAlert: 'Are your sure to download it?',
            retryAlert: 'Are you sure to retry this task?',
            info: 'info',
            warn: 'warn',
            error: 'error',
            limit: 'limit',
            resource: 'resource',
            max: 'Max',
            success: 'Success',
            fail: 'Fail',
            update: 'Update',
            to: 'To',
            from: 'From',
            run: 'Running',
            stop: 'Stopped',
            scannewsoftware: 'Scan Software',
            listTask: 'List Task',
            back: 'Back',
            btnstart: 'Start',
            btnstop: 'Stop',
            btnstarting: 'Starting',
            btnstopping: 'Stopping',
            btnsyning: 'Syncronizing',
            btndeploying: 'Deploying',
            left: 'move to left',
            leftAll: 'meve all to left',
            right: 'move to right',
            rightAll: 'move all to right',
            normal: 'normal',
            unnormal: 'unnormal',
            active: 'active',
            pause: 'pause',
            status: 'status',
            modaltitle: 'please input conditions',
            nextStep: 'Next',
            labelPrevious: 'Previous',
            finish: 'Finish',
            totalRecords: 'total',
            batchInsert: 'batch insert',
            batchDownload: 'batch Download',
            yes: 'yes',
            no: 'no',
            please: 'please',
            msg: 'Select product line name,please!',
            product: 'Product',
            productLine: 'Product Line',
            selectProductLine: 'Select Product Line',
            selectProduct: 'Select Product',
            version: 'Version',
            status: 'Status',
            updateTime: 'Update Time',
            createTime: 'Create Time',
            creator: 'Creator',
            manager: 'Manager',
            run: 'run',
            autoFind: ' node autoFind'
        };
        obj.configuration = {
            uplLogo: 'Change Logo Image',
            loginHeaderImg: 'Login Header Image(177*81)',
            loginFooterImg: 'Login Footer Image(112*47)',
            navBarImg: 'Navbar Image(175*45)'
        };
        obj.validate = {
            notNull: 'not allow Null!',
            emailError: 'email format error!'
        };
        obj.dateTrans = {
            one: 'one',
            two: 'two',
            three: 'three',
            four: 'four',
            five: 'five',
            six: 'six',
            seven: 'seven',
            eight: 'eight',
            nine: 'nine',
            ten: 'ten',
            eleven: 'eleven',
            twelve: 'twelve',
            sunday: 'sunday',
        };
        obj.report = {
            corp: 'corporation ',
            dept: 'department ',
            level: 'level',
            proLine: 'product line',
            byProduct: 'by product',
            byDept: 'by department',
            selProduct: 'select product',
            selDept: 'select department',
            busSummary: 'module summary',
            busDetail: 'module detail',
            query: 'query',
            daily: 'daily',
            weekly: 'weekly',
            monthly: 'monthly',
            day: 'day',
            week: 'week',
            month: 'month',
            year: 'year',
            cobertura_base: 'base cobertura',
            cobertura_core: 'core cobertura',
            cobertura_if: 'interface cobertura',
        };
        obj.agent = {
            agent: 'agent',
            scaning: 'scaning',
            scan: 'scan now',
            sendAgent: 'sendAgent',
            deploydone: 'finish deploy',
            deploy: 'deploy',
            refNode: 'refresh Node',
            batch: 'batch',
            startSendAgent: 'start SendAgent',
            startDeploy: 'start Deploy',
            start: 'start',
            stop: 'stop',
        };
    };
    return {
        config: config
    };
});