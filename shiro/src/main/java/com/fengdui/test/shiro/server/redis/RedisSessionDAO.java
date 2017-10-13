package com.fengdui.test.shiro.server.redis;

import com.zheyue.authserver.json.FastJsonUtil;
import com.zheyue.authserver.redis.RedisClient;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by fd on 2016/9/8.
 */
public class RedisSessionDAO extends CachingSessionDAO {

    @Autowired
    private RedisClient redisClient;

    @Override
    protected void doUpdate(Session session) {
        redisClient.expire(FastJsonUtil.serialize(session.getId()));
    }

    @Override
    protected void doDelete(Session session) {
        redisClient.del(FastJsonUtil.serialize(session.getId()));
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session, sessionId);
        redisClient.set(FastJsonUtil.serialize(sessionId), FastJsonUtil.serialize(session));
        redisClient.expire(FastJsonUtil.serialize(sessionId));
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        return (Session)FastJsonUtil.deserialize(redisClient.get(FastJsonUtil.serialize(serializable)));
    }
}
