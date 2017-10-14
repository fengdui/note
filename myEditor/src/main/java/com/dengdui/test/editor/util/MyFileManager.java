package com.dengdui.test.editor.util;

import lombok.Data;

import javax.tools.*;
import java.io.IOException;

/**
 * Created by FD on 2016/5/4.
 */
public class MyFileManager extends ForwardingJavaFileManager {

    private ClassJavaFileObject fileObject;

    public ClassJavaFileObject getFileObject() {
        return fileObject;
    }

    public void setFileObject(ClassJavaFileObject fileObject) {
        this.fileObject = fileObject;
    }

    public MyFileManager(StandardJavaFileManager javaFileManager) {
        super(javaFileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location,
                                               String className, JavaFileObject.Kind kind, FileObject sibling)
            throws IOException {
        fileObject = new ClassJavaFileObject(className, kind);
        return fileObject;
    }
}