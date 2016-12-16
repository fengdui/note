/**
 * Created by FD on 2016/12/16.
 */

define(['/js/app/oss_define'], function(oss_define) {

    return function(region, accessKeyId, accessKeySecret, securityToken, bucket) {
        var client = new OSS.Wrapper({
            region: region,
            accessKeyId: accessKeyId,
            accessKeySecret: accessKeySecret,
            stsToken: securityToken,
            bucket: bucket
        });
        return client;
    };
});