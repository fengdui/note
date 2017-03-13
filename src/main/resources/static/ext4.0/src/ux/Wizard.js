Ext.define('Ext.ux.Wizard', {
  extend : 'Ext.window.Window',
  alias : 'widget.wizard',
  cls : 'wizard',
  title : 'Wizard',
  autoHeight : true,
  border : false,
  layout : 'card',
  activeItem : 0,
  inWizard : false,
  includeSubTitle : false,

  render : function() {
    if (this.includeSubTitle) {
      this.setTitle(this.titlePrefix + ' (1 / ' + this.items.length + ') ' + this.getActiveItem().title);
    } else {
      this.setTitle(this.titlePrefix + ' (1 / ' + this.items.length + ')');
    }
    this.inWizard = true;
    this.callParent(arguments);
    this.fireEvent('wizardstarted', this);
    this.fireEvent('wizardpagechange', this);
  },

  listeners : {
    beforerender : function() {
      Ext.each(this.getLayout().getLayoutItems(), function(card) {
        card.preventHeader = true;
      });
    },
    wizardpagechange : function(wizard) {
      var toolbar = wizard.getDockedItems()[1];
      toolbar.child('#prev').setDisabled(wizard.activeItem <= 0);
      toolbar.child('#next').setDisabled(wizard.activeItem >= (wizard.items.length - 1));
      toolbar.child('#finish').setDisabled(!wizard.getActiveItem().finishable);

      // Update the title to have the correct page number
      if (this.includeSubTitle) {
        wizard.getActiveItem().preventHeader = true;
        wizard.setTitle(wizard.titlePrefix + ' (' + (wizard.activeItem + 1) + ' of ' + this.items.length + ') '
            + this.getActiveItem().title);
      } else {
        wizard.setTitle(wizard.titlePrefix + ' (' + (wizard.activeItem + 1) + ' of ' + this.items.length + ')');
      }
    }
  },

  buttons : [ {
    text : '&laquo; 上一步',
    itemId : 'prev',
    action : 'prevWizard',
    scope : this,
    handler : function(prev) {
      var wizard = prev.up('wizard');
      wizard.getLayout().setActiveItem(--wizard.activeItem);
      wizard.fireEvent('wizardpagechange', wizard);
      wizard.fireEvent('wizardprev', wizard);
    }
  }, {
    text : '下一步  &raquo;',
    itemId : 'next',
    action : 'nextWizard',
    scope : this,
    handler : function(next) {
      var wizard = next.up('wizard');
      wizard.getLayout().setActiveItem(++wizard.activeItem);
      wizard.fireEvent('wizardpagechange', wizard);
      wizard.fireEvent('wizardnext', wizard);
    }
  }, {
    text : '确定',
    itemId : 'finish',
    action : 'finishWizard',
    scope : this,
    handler : function(finish) {
      var wizard = finish.up('wizard');
      wizard.inWizard = false;
      wizard.fireEvent('wizardfinished', wizard);
    }
  }, {
    text : '取消',
    itemId : 'cancel',
    action : 'cancelWizard',
    scope : this,
    handler : function(cancel) {
      var wizard = cancel.up('wizard');
      wizard.fireEvent('wizardcancelled', wizard);
    }
  } ],

  getActiveItem : function() {
    return this.items.items[this.activeItem];
  }
});