package com.zheyue.encrypt.coder;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author FD
 * @date 2016/11/30
 */
public class AESCoder {

    public static final String KET_ALGORITHM = "AES";

    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static Key toKey(byte[] key) {
        SecretKey secretKey = new SecretKeySpec(key, KET_ALGORITHM);
        return secretKey;
    }

    public static byte[] decrypt(byte[] data, byte[] key) throws Exception{

        Key k = toKey(key);
//        Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    public static byte[] encrypt(byte[] data, byte[] key) throws Exception{
        Key k = toKey(key);
//        Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception{
        String data = "你是猪你是猪哇哈哈";
        String key = "ffffffffdddddddd";
        System.out.println("密钥  "+key);
        System.out.println("原文  "+data);
        byte[] data2 = encrypt(data.getBytes(), key.getBytes());
        System.out.println("密文  " + Base64.encodeBase64String(data2));

        byte[] data3 = decrypt(data2, key.getBytes());
        System.out.println("原文  " + new String(data3));
    }

}
