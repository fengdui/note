package net.smgui.util;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.net.URI;

/**
 * Created by FD on 2016/5/4.
 */
public class SourceJavaFileObject extends SimpleJavaFileObject{

    private CharSequence content;

    public SourceJavaFileObject(String className,
                                      CharSequence content) {
        super(URI.create(className+JavaFileObject.Kind.SOURCE.extension), Kind.SOURCE);
//        super(URI.create("string:///" + className.replace('.', '/')
//                + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
        this.content = content;
    }
    @Override
    public CharSequence getCharContent(
            boolean ignoreEncodingErrors) {
        return content;
    }
}
