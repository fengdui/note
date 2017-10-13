package com.fengdui.note.springaction.chapter_13_jmx;

/**
 * Hello
 *
 * @author FD
 * @date 2016/5/25
 */
public class Hello implements HelloMBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printHello() {
        System.out.println("Hello World, " + name);
    }

//    public static void main(String[] args) {
//        try {
////            MBeanServer server = MBeanServerFactory.createMBeanServer();
//            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
//            server.registerMBean(new Hello(), new ObjectName("fd:name=hello"));
//
////            HtmlAdaptorServer adapter = new HtmlAdaptorServer();
////            server.registerMBean(adapter, new ObjectName("fd:name=adapter"));
////
////            adapter.start();
//            while (true) {
//
//            }
//        } catch (InstanceAlreadyExistsException e) {
//            e.printStackTrace();
//        } catch (MBeanRegistrationException e) {
//            e.printStackTrace();
//        } catch (NotCompliantMBeanException e) {
//            e.printStackTrace();
//        } catch (MalformedObjectNameException e) {
//            e.printStackTrace();
//        }
//    }
}
