package jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

/**
 * @author FD
 * @version v6.3.0
 * @date 2018/6/19
 */
public class DynamicProxyHello implements InvocationHandler {

    private Object proxy;
    private Object target;

    public Object bind(Object target,Object proxy){
        this.target=target;
        this.proxy=proxy;
        return Proxy.newProxyInstance(this.target.getClass().getClassLoader(),
                this.target.getClass().getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result=null;
        Class clazz=this.proxy.getClass();
        Method start=clazz.getDeclaredMethod("start",new Class[]{Method.class});
        start.invoke(this.proxy,start);
        method.invoke(this.target,args);
        Method end=clazz.getDeclaredMethod("end",new Class[]{Method.class});
        end.invoke(this.proxy,end);
        return result;
    }

    public static class DLogger implements ILogger{
        public void start(Method method) {
            System.out.println(new Date()+method.getName()+" say hello start...");
        }

        public void end(Method method) {
            System.out.println(new Date()+method.getName()+" say hello end...");
        }
    }
    public static class Hello implements IHello{

        public void sayHello(String str) {
            System.out.println("hello "+str);
        }
    }
    public interface IHello {
        void sayHello(String str);
    }
    public interface ILogger {
        void start(Method method);
        void end(Method method);
    }
    public static void main(String[] args) {
        IHello hello=(IHello) new DynamicProxyHello().bind(new Hello(), new DLogger());
        hello.sayHello("明天");
    }
}