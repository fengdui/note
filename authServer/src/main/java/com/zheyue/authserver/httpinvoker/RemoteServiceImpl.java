package com.zheyue.authserver.httpinvoker;

import com.zheyue.remote.httpinvoker.RemoteService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by fd on 2016/9/8.
 */
public class RemoteServiceImpl implements RemoteService {

    @Autowired
    private SessionDAO sessionDAO;

    public Session getSession(Serializable sessionId) {
        return sessionDAO.readSession(sessionId);
    }

    public Serializable createSession(Session session) {
        return null;
    }

    public void updateSession(Session session) {

    }

    public void deleteSession(Session session) {

    }
}
