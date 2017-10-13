package net.smgui.framework.proxy;

/**
 * Created by fd on 2016/7/13.
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
