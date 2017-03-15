Ext.define('AM.view.IpView', {
    extend: 'Ext.panel.Panel',
    alias : 'widget.ipView',//app.js xtype需要使用别名
    height : 200,
    layout: 'fit',
    title : 'ip查询',
    items :[{
        xtype: 'grid',
        store: 'IpStore',
        //columns表示一行有多列
        columns: [{
            text: 'ip地址',
            dataIndex: 'ip',
            align : 'center',
            flex : 1
        }, {
            text: '所在地区',
            dataIndex : 'address', //对应store
            align : 'center',//居中
            flex : 1 //等分
        }],
        tbar :{
            xtype : 'toolbar',
            items : [{
                xtype : 'textfield',
                name : 'ipAddress',
                labelAlign : 'right',
                fieldLabel : '请输入ip'
            }, {
                xtype : 'tbseparator',
                // itemId : 'add_separator'
            },
                {
                    xtype : 'button',
                    text : '解析ip',
                    tooltip : '点击分析ip归属地',
                    itemId : 'queryItem'
            }],
        },
        bbar: {
            xtype: 'pagingtoolbar',
            itemId: 'pagebar',
            // store: 'UserStore'
        }
    }],
    initComponent : function(){
        this.callParent(arguments);
    }
});