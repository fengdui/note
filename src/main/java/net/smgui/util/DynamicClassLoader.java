package net.smgui.util;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by FD on 2016/5/4.
 */
public class DynamicClassLoader extends URLClassLoader {
    public DynamicClassLoader() {
        super(new URL[0]);
    }

    public Class findClassByClassName(String className) throws ClassNotFoundException {
        return this.findClass(className);
    }

    public Class loadClass(String fullName, ClassJavaFileObject jco) {
        byte[] classData = jco.getBytes();
        return this.defineClass(fullName, classData, 0, classData.length);
    }
}
