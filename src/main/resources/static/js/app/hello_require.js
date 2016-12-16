/**
 * Created by FD on 2016/12/15.
 */

requirejs(['/js/lib/vue.js', '/js/lib/aliyun-oss-sdk.js'], function(Vue, fd) {
    //alert(hello_define.color)
    //alert(hello_define.size)
    var vm = new Vue({
        // 选项
        methods: {
            hello : function() {
                alert("你好");
            }
        }
    });
    console.log(vm);
    return vm;
    //var client = new fd.Wrapper({
    //    region: '',
    //    accessKeyId: '',
    //    accessKeySecret: '',
    //    stsToken: '',
    //    bucket: ''
    //});
    //console.log(client);
    //return client;
});

//requirejs(['/js/app/oss_define.js'], function(oss_define) {
//    //alert(hello_define.color)
//    //alert(hello_define.size)
//    oss_define.
//    console.log("fd");
//});

