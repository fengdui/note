Ext.define('AM.store.IpStore', {
    extend : 'Ext.data.Store',
    fields : ['ip', 'address'],
    // model : 'IpModel'

    proxy: {
        type: 'ajax',
        // url : 'http://ip.taobao.com/service/getIpInfo.php',
        url : 'ip/query',
        reader: {
            type: 'json',
            root: 'data'
        }
    },

    data : [
        {ip : '111', address : '杭州'},
        {ip : '222', address : '嘉兴'}
    ]
});