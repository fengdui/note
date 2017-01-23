package com.fengdui.shiro.server.remote;

import org.apache.shiro.session.Session;

import java.io.Serializable;

/**
 * Created by fd on 2016/9/8.
 */
public interface RemoteService {
    public String getName();
    public Session getSession(Serializable sessionId);
    Serializable createSession(Session session);
    public void updateSession(Session session);
    public void deleteSession(Session session);
}
