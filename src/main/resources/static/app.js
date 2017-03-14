Ext.Loader.setConfig({enabled: true});
Ext.application({
    // requires : 'Ext.container.Viewport',
    name: 'AM',
    appFolder: 'app',
    controllers: [
        'MyController'
    ],
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: [
                {
                    title: 'Hello Ext',
                    html : 'Hello! Welcome to Ext JS.'
                },
                {
                    xtype : 'panel',
                    region : 'south',
                    html : 'Hello'
                }
            ]
        });
    }
});