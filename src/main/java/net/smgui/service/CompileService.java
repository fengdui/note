package net.smgui.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * CompileService
 *
 * @author FD
 * @date 2016/3/10 0010
 */
public interface CompileService {

    public String importFile(MultipartFile fileSrc) throws Exception;

    public String compile(String command) throws Exception;

    public String compileJava(File javaSrc);

}
