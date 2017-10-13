package com.fengdui.test.shiro.server.httpinvoker;

import com.zheyue.authserver.remote.RemoteService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by fd on 2016/9/8.
 */
public class RemoteServiceImpl implements RemoteService {

//    @Autowired
//    private SessionDAO sessionDAO;

    public String getName() {
        return "fd";
    }

    public Session getSession(Serializable sessionId) {
//        return sessionDAO.readSession(sessionId);
        return null;
    }

    public Serializable createSession(Session session) {
        return null;
    }

    public void updateSession(Session session) {

    }

    public void deleteSession(Session session) {

    }
}
