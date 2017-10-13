package net.smgui.framework.proxy;

import java.lang.reflect.Method;

/**
 * Created by fd on 2016/7/14.
 */
public class AspectProxy implements Proxy {

    public Object doProxy(ProxyChain proxyChain) throws Throwable {

        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        
        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params);
            }
            else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            error(cls, method, params, e);
        }
        finally {
            end();    
        }
        return result;
    }

    public void begin() {
    }

    public void end() {
    }

    public void before(Class<?> cls, Method method, Object[] params) {
    }

    public void after(Class<?> cls, Method method, Object[] params) {
    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }

    public void error(Class<?> cls, Method method, Object[] params, Exception e) {
    }
}
