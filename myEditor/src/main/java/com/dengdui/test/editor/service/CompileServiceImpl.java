package com.dengdui.test.editor.service;

import com.dengdui.test.editor.util.*;
import net.smgui.util.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.tools.*;
import java.io.*;
import java.util.*;


@Service
public class CompileServiceImpl implements CompileService{

    public String getRealPath(String path) {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(path);
    }
    public String importFile(MultipartFile fileSrc) throws Exception{

        StringBuilder filePath = new StringBuilder();
        filePath.append(getRealPath(""));
        filePath.append(UUID.randomUUID());
        filePath.append(fileSrc.getOriginalFilename());
        System.out.println(filePath.toString());
        FileUtil.copy(fileSrc, new File(filePath.toString()));
        return filePath.toString();
    }

    public String savaJava(String javaSrc, String path) {
        FileOutputStream outputStream = null;
        try {
            path = path+File.separator+"Main.java";
            byte[] bytes = javaSrc.getBytes("utf-8");
            outputStream = new FileOutputStream(path);
            outputStream.write(bytes);

//            FileInputStream input = new FileInputStream(path);
//            BufferedReader bf = new BufferedReader(new InputStreamReader(input, "utf-8"));
//            String ss;
//            while ((ss = bf.readLine()) != null) {
//                System.out.println(ss);
//            }
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public String compile(String command) throws Exception{
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (Exception e) {
           return "系统正在维护中";
        }
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = process.getErrorStream();
            StringBuilder sb = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(is, "gbk"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                System.out.println(line);
                sb.append("\n");
            }
            process.waitFor();
            return sb.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (null != is) {
                    is.close();
                    is = null;
                }
                if (null != br) {
                    br.close();
                    br = null;
                }
                if (null != process) {
                    process.destroy();
                    process = null;
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }



//    public String compileJava(String path) throws Exception {
////        String command = "javaxc -encoding utf-8 "+ path;
////        return compile(command);
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//        OutputStream outStream = new ByteArrayOutputStream();
//        OutputStream errStream = new ByteArrayOutputStream();
//        int result = compiler.run(null, null, errStream, "-encoding", "utf-8", path);
//        StringBuilder sb = new StringBuilder();
//        String s = "";
//        if(result > 0) {
//            s = errStream.toString();
//        }
//        return  s;
//    }

    public String compileJava(String source) throws Exception {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        MyFileManager fileManager = new MyFileManager(compiler.getStandardFileManager(diagnostics, null, null));
        List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
        jfiles.add(new SourceJavaFileObject("Main", source));

        List<String> options = new ArrayList<String>();
        options.add("-encoding");
        options.add("UTF-8");
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, jfiles);
        boolean success = task.call();

        if (success) {
            ClassJavaFileObject jco = fileManager.getFileObject();
            DynamicClassLoader dynamicClassLoader = new DynamicClassLoader();
            Class clazz = dynamicClassLoader.loadClass("Main", jco);
            ContextUtil.map.put("Main", clazz);
            return "编译成功";
        } else {
            String error = "";
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                error = error + compilePrint(diagnostic);
            }
            return error;
        }
    }
    private String compilePrint(Diagnostic diagnostic) {
        System.out.println("Code:" + diagnostic.getCode());
        System.out.println("Kind:" + diagnostic.getKind());
        System.out.println("Position:" + diagnostic.getPosition());
        System.out.println("Start Position:" + diagnostic.getStartPosition());
        System.out.println("End Position:" + diagnostic.getEndPosition());
        System.out.println("Source:" + diagnostic.getSource());
        System.out.println("Message:" + diagnostic.getMessage(null));
        System.out.println("LineNumber:" + diagnostic.getLineNumber());
        System.out.println("ColumnNumber:" + diagnostic.getColumnNumber());
        StringBuffer res = new StringBuffer();
//        res.append("Code:[" + diagnostic.getCode() + "]\n");
//        res.append("Kind:[" + diagnostic.getKind() + "]\n");
//        res.append("Position:[" + diagnostic.getPosition() + "]\n");
//        res.append("Start Position:[" + diagnostic.getStartPosition() + "]\n");
//        res.append("End Position:[" + diagnostic.getEndPosition() + "]\n");
//        res.append("Source:[" + diagnostic.getSource() + "]\n");
//        res.append("第"+diagnostic.getLineNumber()+"行错误:");
        res.append(diagnostic.getMessage(null)+"\n");
//        res.append("ColumnNumber:[" + diagnostic.getColumnNumber() + "]\n");
        return res.toString();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int x = scanner.nextInt();
            System.out.println(x);
        }
    }
}
