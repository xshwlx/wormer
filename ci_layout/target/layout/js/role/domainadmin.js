define([], function (obj) {
    var getMenu = function (obj) {
        obj.menu = [
            {
                nodes: [],
                module: 'home'
            },
            {
                nodes: [
                    {
                        module: 'vm'
                    },
                    {
                        module: 'network'
                    },
                    {
                        module: 'volume'
                    }
                ],
                module: 'virtualization'
            },
            {
                nodes: [
                    {
                        module: 'template'
                    },
                    {
                        module: 'iso'
                    }
                ],
                module: 'template'
            },
            {
                nodes: [
                    {
                        module: 'snapshot'
                    }
                ],
                module: 'recovery'
            },
            {
                nodes: [
                    {
                        module: 'account'
                    }
                ],
                module: 'configuration'
            },
            {
                nodes: [
                    {
                        module: 'virtualization'
                    },
                    {
                        module: 'events'
                    },
                    {
                        module: 'jobs'
                    }
                ],
                module: 'monitor'
            },
            {
                nodes: [],
                module: 'project'
            }
        ];
    }

    return {
        getMenu: getMenu
    };
});