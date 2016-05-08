package net.smgui.util;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by FD on 2016/5/4.
 */
public class ClassJavaFileObject extends SimpleJavaFileObject{

    protected final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    public ClassJavaFileObject(String name, JavaFileObject.Kind kind) {
        super(URI.create(name + kind.extension), kind);
    }
    public byte[] getBytes() {
        return bos.toByteArray();
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return bos;
    }
}
