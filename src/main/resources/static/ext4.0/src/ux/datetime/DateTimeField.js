if (Ext.Date) {
    Ext.Date.monthNames = [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ];

    Ext.Date.dayNames = [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ];

    Ext.Date.formatCodes.a = "(this.getHours() < 12 ? '上午' : '下午')";
    Ext.Date.formatCodes.A = "(this.getHours() < 12 ? '上午' : '下午')";

    parseCodes = {
        g : 1,
        c : "if (/(上午)/i.test(results[{0}])) {\n" + "if (!h || h == 12) { h = 0; }\n" + "} else { if (!h || h < 12) { h = (h || 0) + 12; }}",
        s : "(上午|下午)",
        calcAtEnd : true
    };

    Ext.Date.parseCodes.a = Ext.Date.parseCodes.A = parseCodes;
}
Ext.define('Ext.ux.datetime.DateTimeField', {
    extend : 'Ext.form.field.Date',
    alias : 'widget.datetimefield',
    requires : [ 'Ext.ux.datetime.DateTimePicker' ],
    renderType : 'now',
    /**
     * @cfg {String} format The default date format string which can be
     *      overriden for localization support. The format must be valid
     *      according to {@link Ext.Date#parse}.
     */
    format : "Y-m-d H:i:s",

    /**
     * @cfg {String} altFormats Multiple date formats separated by "|" to try
     *      when parsing a user input value and it does not match the defined
     *      format.
     */
    altFormats : "Y-m-d H:i:s",

    createPicker : function() {
        var me = this, format = Ext.String.format;

        // 修改picker为自定义picker
        return Ext.create('widget.dateTimePicker', {
            pickerField : me,
            //width : 205,
            ownerCt : me.ownerCt,
            renderTo : document.body,
            renderType : me.renderType,
            floating : true,
            hidden : true,
            focusOnShow : true,
            minDate : me.minValue,
            maxDate : me.maxValue,
            disabledDatesRE : me.disabledDatesRE,
            disabledDatesText : me.disabledDatesText,
            disabledDays : me.disabledDays,
            disabledDaysText : me.disabledDaysText,
            format : me.format,
            showToday : me.showToday,
            startDay : me.startDay,
            minText : format(me.minText, me.formatDate(me.minValue)),
            maxText : format(me.maxText, me.formatDate(me.maxValue)),
            listeners : {
                scope : me,
                select : me.onSelect
            },
            keyNavConfig : {
                esc : function() {
                    me.collapse();
                }
            }
        });
    },

    /**
     * @private
     */
    onExpand : function() {
        var value = this.getValue();
        // 多传一个参数，从而避免时分秒被忽略。
        this.picker.setValue(Ext.isDate(value) ? value : new Date(), true);
    }
});