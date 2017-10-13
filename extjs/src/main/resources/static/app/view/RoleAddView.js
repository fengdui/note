var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define('AM.view.RoleAddView', {
    extend : 'Ext.window.Window',
    alias : 'widget.roleAddView',
    title : '新建角色',
    layout : 'border',
    border:false,
    autoShow : false,
    autoScroll : true,
    autoHeight : true,
    height :140,
    width : 330,
    draggable : false,//拖放
    modal : false,//模态
    initComponent : function () {

        this.items = [ {
            xtype : 'form',
            bodyStyle : {
                padding : '10px'
            },
            border : false,
            region : 'center',
            width : '100%',
            height : '100%',
            items : [{
                fieldLabel : '角色',
                xtype : 'textfield',
                allowBlank : false,
                labelAlign : "right",
                afterLabelTextTpl : required,
                name : 'role'
            }, {
                fieldLabel : '描述',
                xtype : 'textfield',
                allowBlank : false,
                labelAlign : "right",
                afterLabelTextTpl : required,
                name : 'description'
            }]
        } ];

        this.buttons = [ {
            text : '确定',
            action : 'add'
        }, {
            text : '取消',
            scope : this,
            handler : function() {
                this.close();
            }
        } ];
        this.callParent(arguments);
    }
});