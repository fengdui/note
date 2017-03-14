Ext.define('AM.controller.UserController', {
			extend : 'Ext.app.Controller',
			// alias : ['widget.userController'],
			// views : ['UserView', 'UserAddView'],
			// stores : ['UserStore'],

			init : function() {
                console.log('Initialized Users! This happens before the Application launch function is called');
				// this.control({
				// 			'userView' : {
				// 				beforerender : this.beforeRender
				// 			},
				// 			'userView button[action=search]' : {
				// 				click : this.searchBtnClicked
				// 			},
				// 			'userView grid button[text=新建]' : {
				// 				click : this.addBtnClicked
				// 			},
				// 			'userAddView button[text=确定]' : {
				// 				click : this.addUser
				// 			}
				// 		});
			},
			beforeRender : function(tab) {
				var grid = tab.down('grid'), store = grid.getStore();
				var queryMap = {};
				var userName = tab.down('textfield[name=userName]').getValue();
				queryMap['userName'] = userName;
				store.getProxy().extraParams = queryMap;
				store.loadPage(1);
			},
			searchBtnClicked : function(btn) {
				var tab = btn.up('userView').down('grid'), store = tab
						.getStore();
				var queryMap = {};
				var userName = tab.down('textfield[name=userName]').getValue();
				queryMap['userName'] = userName;
				store.getProxy().extraParams = queryMap;
				store.loadPage(1);
			},
			addUser : function(btn) {
				var window = btn.up('window');
				var form = window.down('form');
				var values = form.getValues();
				Ext.Ajax.request({
							url : 'user/add',
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
			},
			addBtnClicked : function(btn) {
				var me = this;
				var grid = btn.up('grid');
				var tab = grid.up('userView');
				var window = this.getView('UserAddView').create();
				window.on('close', function(window) {
							grid.getStore().load();
						});

				window.show();
			}
		});