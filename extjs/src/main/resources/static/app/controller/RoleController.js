Ext.define('AM.controller.RoleController', {
    extend : 'Ext.app.Controller',
    views :['RoleView', 'RoleAddView'],
    stores : ['RoleStore'],
    // models :['RoleModel'],
    init : function () {
        // console.log('Initialized Users! This happens before the Application launch function is called');

        this.control({
            'roleView button[action=查询]' : {
                click : this.queryBtnClicked
            },
            'roleView button[action=新建]' : {
                click : this.addBtnClicked
            },
            'roleAddView button[text=确定]' : {
                click : this.addRole
            }
        });
    },
    queryBtnClicked : function (btn) {
        var store = btn.up('roleView').down('grid').getStore();
        // console.log(store);
        var roleName = btn.up('roleView').down('grid').down('textfield').getValue();
        // console.log(ip);
        // var operation = Ext.create('Ext.data.Operation', {
        //     action : 'read',
        //     roleName : roleName
        // });
        // store.getProxy().read(operation);
        var queryMap = {};
        queryMap['roleName'] = roleName;
        store.getProxy().extraParams = queryMap;
        store.loadPage(1);
        // Ext.MessageBox.alert("后台接口未完成，返回的数据是假的");
    },
    addBtnClicked : function (btn) {
        console.log("点击添加按钮");
        var grid = btn.up('grid');
        var window = this.getView('RoleAddView').create();
        //退出后刷新页面，添加后退出可以直接看到结果
        window.on('close', function(window) {
            grid.getStore().load();
        });
        window.show();
    },
    addRole : function (btn) {
        var window = btn.up('window');
        var form = window.down('form');
        var values = form.getValues();
        console.log(values);
        Ext.Ajax.request({
            url : 'role/add',
            method : 'POST',
            jsonData : values,
            success : function(resp) {
                var record = Ext.JSON.decode(resp.responseText);
                if (!record.success) {
                    Ext.MessageBox.alert('出错提示', record.error);
                } else {
                    window.close();
                    Ext.MessageBox.alert('新建', '新建成功');
                }
            },
            failure : function(resp) {
                var result = Ext.JSON.decode(resp.responseText);
                if (!result.success) {
                    Ext.MessageBox.alert('出错提示', result.error);
                } else {
                    Ext.MessageBox.alert('运行错误',
                        resp.responseText);
                }
            }
        });
    }
});