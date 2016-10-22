package com.zheyue.authclient.session;

import com.zheyue.authclient.remote.RemoteService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
        RemoteService remoteService = (RemoteService)applicationContext.getBean("remoteService");
        String str = remoteService.getName();
        System.out.println(str);
    }
}
