package com.zheyue.encrypt.service;


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

    public Key getKey(byte[] key) {
        SecretKey secretKey = new SecretKeySpec(key, KET_ALGORITHM);
        return secretKey;
    }

    public Cipher getDecryptCipher(byte[] key) throws Exception {
        Key k = getKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher;
    }
    public Cipher getEncryptCipher(byte[] key) throws Exception {
        Key k = getKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher;
    }
    public byte[] decrypt(byte[] data, byte[] key) throws Exception{
        Cipher cipher = getDecryptCipher(key);
        return cipher.doFinal(data);
    }

    public byte[] encrypt(byte[] data, byte[] key) throws Exception{
        Cipher cipher = getEncryptCipher(key);
        return cipher.doFinal(data);
    }
}
