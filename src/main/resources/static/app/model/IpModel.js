Ext.define('AM.model.IpModel', {
    extend : 'Ext.data.Model',
    fields : ['ip', 'address'],
    proxy: {
        type: 'rest',
        url : 'http://ip.taobao.com/service/getIpInfo.php',
        reader: {
            type: 'json',
            root: 'users'
        }
    }
});