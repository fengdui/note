/**
 * Created by FD on 2016/12/16.
 */
alert("你好");

requirejs.config({

    baseUrl: '/static/js',

    paths: {
        //"jquery": "jquery.min",
        //"underscore": "underscore.min",
        //"backbone": "backbone.min"
        "vue": "lib/vue"
    }
});