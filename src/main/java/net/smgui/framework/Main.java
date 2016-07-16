package net.smgui.framework;

import java.io.*;
import java.nio.Buffer;

/**
 * Created by fd on 2016/7/5.
 */
public class Main {

    public static void qiege(int n) {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            File file = new File("D:\\fd\\fd\\fd.jpg");
            fis = new FileInputStream("D:\\fd\\fd\\fd.jpg");
            int len;
            int pv = (int)file.length()/n;
            byte[] bytes = new byte[pv];
            int pre = 0;
            int sege = 0;
            while ((len = fis.read(bytes)) > 0) {
                fos = new FileOutputStream("D:\\fd\\fd\\fd-"+sege+++".jpg");
//                fos.write();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }try {
            if (null != fis) {
                fis.close();
            }
            if(null != fos){
                fos.close();
            }
        } catch (IOException e) {
        }
    }

    public static void hebing(int n) {

    }
    public static void main(String[] args) {

        qiege(6);
        hebing(6);
    }

}
