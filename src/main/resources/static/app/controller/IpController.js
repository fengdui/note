Ext.define('AM.controller.IpController', {
    extend : 'Ext.app.Controller',
    views :['IpView'],
    stores : ['IpStore'],
    // models :['IpModel'],
    init : function () {
        console.log('Initialized Users! This happens before the Application launch function is called');

        // this.control({
        //
        // });
    }
});