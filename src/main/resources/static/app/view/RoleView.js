Ext.define('AM.view.RoleView', {
    extend: 'Ext.panel.Panel',
    alias : 'widget.roleView',//app.js xtype需要使用别名
    height : 200,
    layout: 'fit',
    title : '角色查询',
    items :[{
        xtype: 'grid',
        store: 'RoleStore',
        //columns表示一行有多列
        columns: [{
            text: '角色名称',
            dataIndex: 'role',
            align : 'center',
            flex : 1
        }, {
            text: '描述',
            dataIndex : 'description', //对应store
            align : 'center',//居中
            flex : 1 //等分
        }],
        tbar :{
            xtype : 'toolbar',
            items : [{
                xtype : 'textfield',
                name : 'roleName',
                labelAlign : 'right',
                fieldLabel : '请输入角色名'
            }, {
                xtype : 'tbseparator'
                // itemId : 'add_separator'
            },
                {
                    xtype : 'button',
                    text : '查询角色',
                    tooltip : '点击查询角色信息',
                    action : '查询'
            },
                {
                    xtype : 'button',
                    text : '新建角色',
                    tooltip : '新建角色',
                    action : '新建'
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