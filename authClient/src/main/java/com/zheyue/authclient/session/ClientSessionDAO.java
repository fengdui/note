package com.zheyue.authclient.session;

import com.zheyue.remote.httpinvoker.RemoteService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by fd on 2016/9/9.
 */
public class ClientSessionDAO extends CachingSessionDAO{

    @Autowired
    private RemoteService remoteService;

    @Override
    protected void doUpdate(Session session) {
        remoteService.updateSession(session);
    }

    @Override
    protected void doDelete(Session session) {
        remoteService.deleteSession(session);
    }

    @Override
    protected Serializable doCreate(Session session) {
        return remoteService.createSession(session);
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        return remoteService.getSession(serializable);
    }
}
