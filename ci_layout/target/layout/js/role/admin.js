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
                    },
                    {
                        module: 'systemvm'
                    },
                    {
                        module: 'vrouter'
                    }
                ],
                module: 'virtualization'
            },
            {
                nodes: [
                    {
                        module: 'zone'
                    },
                    {
                        module: 'pod'
                    },
                    {
                        module: 'cluster'
                    },
                    {
                        module: 'host'
                    }
                ],
                module: 'infrastructure'
            },
            {
                nodes: [
                    {
                        module: 'computing'
                    },
                    {
                        module: 'system'
                    },
                    {
                        module: 'network'
                    },
                    {
                        module: 'storage'
                    }
                ],
                module: 'policy'
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
                    },
                    {
                        module: 'domain'
                    },
                    {
                        module: 'setting'
                    },
                    {
                        module: 'hypervisor'
                    }
                ],
                module: 'configuration'
            },
            {
                nodes: [
                    {
                        module: 'infrastructure'
                    },
                    {
                        module: 'virtualization'
                    },
                    {
                        module: 'host'
                    },
                    {
                        module: 'events'
                    },
                    {
                        module: 'jobs'
                    },
                    {
                        module: 'sysalerts'
                    },
                    {
                        module: 'hostalerts'
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