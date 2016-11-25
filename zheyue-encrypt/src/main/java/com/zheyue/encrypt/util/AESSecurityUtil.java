package com.zheyue.encrypt.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author FD
 * @date 2016/11/23
 */
public class AESSecurityUtil {
    /** 密钥算法 */
    private static final String KEY_ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;
    /** 加密/解密算法/工作模式/填充方法 */
//    public static final String CIPHER_ALGORITHM = "AES/ECB/NoPadding";


    /**
     * 获取密钥
     * @return
     * @throws Exception
     */
    public static Key getKey() throws Exception{
        //实例化
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        //AES 要求密钥长度为128位、192位或256位
        kg.init(KEY_SIZE);
        //生成密钥
        SecretKey secretKey = kg.generateKey();
        return secretKey;
    }

    /**
     * 转化密钥
     * @param key 密钥
     * @return Key 密钥
     * @throws Exception
     */
    public static Key codeToKey(String key) throws Exception{
        byte[] keyBytes = Base64.decodeBase64(key);
        SecretKey secretKey = new SecretKeySpec(keyBytes,KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 解密
     * @param data 待解密数据
     * @param key 密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    private static String decrypt(byte[] data,byte[] key) throws Exception{
        //还原密钥
        Key k = new SecretKeySpec(key,KEY_ALGORITHM);
        /**
         * 实例化
         * 使用PKCS7Padding填充方式，按如下方式实现
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC");
         */
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        //初始化，设置解密模式
        cipher.init(Cipher.DECRYPT_MODE,k);
        //执行操作
        return new String(cipher.doFinal(data),"UTF-8");
    }

    /**
     * 解密
     * @param data 待解密数据
     * @param key 密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static String decrypt(String data,String key) throws Exception{
        return decrypt(Base64.decodeBase64(data), Base64.decodeBase64(key));
    }

    /**
     * 加密
     * @param data 待加密数据
     * @param key 密钥
     * @return bytes[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
        //还原密钥
        Key k = new SecretKeySpec(key, KEY_ALGORITHM);
        /**
         * 实例化
         * 使用PKCS7Padding填充方式，按如下方式实现
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC");
         */
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        //初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    public static String encrypt(String data,String key) throws Exception{
        byte[] dataBytes = data.getBytes("UTF-8");
        byte[] keyBytes = Base64.decodeBase64(key);
        return Base64.encodeBase64String(encrypt(dataBytes, keyBytes));
    }

    /**
     * 初始化密钥
     * @return
     * @throws Exception
     */
    public static String getKeyStr() throws Exception{
        return Base64.encodeBase64String(getKey().getEncoded());
    }

    public static void main(String[] args) throws Exception{
        String key = "VxDksHQiTvQt9MMPtMVXdA==";
        String wenjian = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<mainData>\n" +
                "<config>\n" +
                "     <operate>1</operate>                  <!--0:删除，1:新增，2:修改-->\n" +
                "</config>\n" +
                "<dataList type=\"personnel\">\n" +
                "     <data id=\"员工主数据主键\">            <!--默认主数据代码-->\n" +
                "       <code></code>                       <!--代码-->\n" +
                "       <name></name>                       <!--姓名-->\n" +
                "       <sex></sex>                         <!--性别-->\n" +
                "       <birthday></birthday>               <!--出生日期-->\n" +
                "       <education></education>             <!--文化程度-->\n" +
                "       <idNumber></idNumber>               <!--身份证号码-->\n" +
                "       <entryDate></entryDate>             <!--入职日期-->\n" +
                "       <departureDate></departureDate>     <!--离职日期-->\n" +
                "       <address></address>                 <!--住址-->\n" +
                "       <phoneNumber></phoneNumber>         <!--电话-->\n" +
                "       <mobilePhoneNumber></mobilePhoneNumber><!--移动电话-->\n" +
                "       <email></email>                     <!--电子邮件-->\n" +
                "       <position></position>               <!--职务-->\n" +
                "       <maritalStatus></maritalStatus>     <!--婚姻状况-->\n" +
                "       <partyAffiliation></partyAffiliation><!--政治面貌-->\n" +
                "       <username></username>               <!--用户名-->\n" +
                "       <sortNo></sortNo>                   <!--排序号-->\n" +
                "       <status></status>                   <!--状态-->\n" +
                "       <department></department>           <!--所属部门-->\n" +
                "       <company></company>                 <!--所属公司-->\n" +
                "    </data>\n" +
                "</dataList>\n" +
                "</mainData>";
        StringBuffer buffer = new StringBuffer();
        for(int index = 0; index < 20000; index ++){
            buffer.append(wenjian);
        }
//        String jimm = buffer.toString();

        String jimm = "fd123";
        String mw = AESSecurityUtil.encrypt(jimm, key);
        System.out.println("密文:" + mw);

        String jm = AESSecurityUtil.decrypt(mw, key);
        System.out.println("明文:" + jm);
    }
}