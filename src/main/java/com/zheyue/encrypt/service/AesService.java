package com.zheyue.encrypt.service;


import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @author FD
 * @date 2016/11/30
 */

@Service
public class AesService {

    public static final String KET_ALGORITHM = "AES";

    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static final String key = "ffffffffdddddddd";

    public byte[] getEncryptKey() {
        return key.getBytes();
    }

    @Cacheable(key = "#userId", value = "userSecretKey")
    public Key getKey(int userId) {
        System.out.println("缓存了key " + userId);
        SecretKey secretKey = new SecretKeySpec(getEncryptKey(), KET_ALGORITHM);
        return secretKey;
    }

//    public Cipher getDecryptCipher(int userId) throws Exception {
//        Key k = getKey(userId);
//        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
//        cipher.init(Cipher.DECRYPT_MODE, k);
//        return cipher;
//    }
    @Cacheable(key = "#userId", value = "userCipher")
    public Cipher getEncryptCipher(int userId) throws Exception {
        System.out.println("缓存了Cipher "+ userId);
        Key k = getKey(userId);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher;
    }
//    public byte[] decrypt(byte[] data, int userId) throws Exception{
//        Cipher cipher = getDecryptCipher(userId);
//        return cipher.doFinal(data);
//    }

    public byte[] encrypt(byte[] data, int userId) throws Exception{
        Cipher cipher = ((AesService)AopContext.currentProxy()).getEncryptCipher(userId);
        return cipher.doFinal(data);
    }
}
