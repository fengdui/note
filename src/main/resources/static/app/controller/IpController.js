Ext.define('AM.controller.IpController', {
    extend : 'Ext.app.Controller',
    views :['IpView'],
    stores : ['IpStore'],
    // models :['IpModel'],
    init : function () {
        // console.log('Initialized Users! This happens before the Application launch function is called');

        this.control({
            'ipView button' : {
                click : this.queryBtnClicked
            }
        });
    },
    queryBtnClicked : function (btn) {
        var store = btn.up('ipView').down('grid').getStore();
        // console.log(store);
        var ip = btn.up('ipView').down('grid').down('textfield').getValue();
        // console.log(ip);
        var operation = Ext.create('Ext.data.Operation', {
            action : 'read',
            ip : ip
        });
        store.getProxy().read(operation);
        store.load({});
    }
});