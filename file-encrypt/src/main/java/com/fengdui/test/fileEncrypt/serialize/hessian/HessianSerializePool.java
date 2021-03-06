package com.fengdui.test.fileEncrypt.serialize.hessian;

import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author FD
 * @date 2016/12/30
 * hessian序列化池 包含若干个HessianSerialize
 */
public class HessianSerializePool {

    private GenericObjectPool<HessianSerialize> hessianPool;
    private volatile static HessianSerializePool poolFactory = null;

    private HessianSerializePool() {
        hessianPool = new GenericObjectPool<HessianSerialize>(new HessianSerializeFactory());
    }

    public static HessianSerializePool getHessianPoolInstance() {
        if (poolFactory == null) {
            synchronized (HessianSerializePool.class) {
                if (poolFactory == null) {
                    poolFactory = new HessianSerializePool();
                }
            }
        }
        return poolFactory;
    }
    public HessianSerialize borrow() {
        try {
            return getHessianPool().borrowObject();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void restore(final HessianSerialize object) {
        getHessianPool().returnObject(object);
    }

    public GenericObjectPool<HessianSerialize> getHessianPool() {
        return hessianPool;
    }
}
