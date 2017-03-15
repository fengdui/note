Ext.define('AM.store.RoleStore', {
    extend : 'Ext.data.Store',
    fields : ['role', 'description'],
    // model : 'IpModel'
    autoLoad : true,
    proxy: {
        type: 'ajax',
        // url : 'http://ip.taobao.com/service/getIpInfo.php',
        url : 'role/query',
        reader: {
            type: 'json',
            root: 'data'
        }
    }

    // data : [
    //     {ip : '111', address : '杭州'},
    //     {ip : '222', address : '嘉兴'}
    // ]
});