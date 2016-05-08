package net.smgui.entity;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

/**
 * Created by FD on 2016/5/3.
 */
public class JavaSource extends SimpleJavaFileObject{
    private StringBuilder code;

    public JavaSource(String name) {
        super(URI.create("String:///"+name.replace('.', '/')+Kind.SOURCE.extension), Kind.SOURCE);
        code = new StringBuilder();
    }

    public CharSequence getCharContent() {
        return code;
    }

    public void append(String str) {
        code.append(str);
    }
}
