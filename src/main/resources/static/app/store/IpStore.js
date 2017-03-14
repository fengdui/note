Ext.define('AM.store.IpStore', {
    extend : 'Ext.data.Store',
    fields : ['ip', 'address'],
    // model : 'IpModel'
    data : [
        {ip : '111', address : '杭州'},
        {ip : '222', address : '嘉兴'}
    ]
});