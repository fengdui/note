package net.smgui.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;


public interface CompileService {

    public String importFile(MultipartFile fileSrc) throws Exception;

    public String savaJava(String javaSrc, String path) throws Exception;

    public String compile(String command) throws Exception;

    public String compileJava(String path) throws Exception;

}
