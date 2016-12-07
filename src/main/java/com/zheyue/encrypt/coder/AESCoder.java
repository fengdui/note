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

    private static byte[] sbox = new byte[256];

    public static final String KET_ALGORITHM = "AES";

    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static Key toKey(byte[] key) {
        SecretKey secretKey = new SecretKeySpec(key, KET_ALGORITHM);
        return secretKey;
    }

    public static byte[] decrypt(byte[] data, byte[] key) throws Exception{

        Key k = toKey(key);
//        Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    public static byte[] encrypt(byte[] data, byte[] key) throws Exception{
        Key k = toKey(key);
//        Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception{
//        String data = "你是猪你是猪哇哈哈";
//        String key = "ffffffffdddddddd";
//        System.out.println("密钥  "+key);
//        System.out.println("原文  "+data);
//        byte[] data2 = encrypt(data.getBytes(), key.getBytes());
//        System.out.println("密文  " + Base64.encodeBase64String(data2));
//
//        byte[] data3 = decrypt(data2, key.getBytes());
//        System.out.println("原文  " + new String(data3));
        generateSbox();
        for (int i = 0; i < 256; i++) {
                System.out.format("%x", sbox[i]);
                System.out.println();
        }
//        System.out.println(Integer.toBinaryString(128*131));
//        System.out.println(Integer.toBinaryString(283));
//        System.out.println(Integer.toBinaryString(inv(194, 283)));
//        System.out.println(Integer.toBinaryString(inv(255, 283)));
    }

    //得到s盒 每一个字节计算逆元
    //得到逆元计算yi=xi + x(i+4)mod8 + x(i+5)mod8 + x(i+6)mod8 + x(i+7)mod8 + ci
    //假设输入字节的值为a=a7a6a5a4a3a2a1a0，则输出值为S[a7a6a5a4][a3a2a1a0]，S-1的变换也同理。
    //这里mod不是整数 而是多项式x^8+x^4+x^3+x+1 -> 100011011 -> 283
    private static void generateSbox() {
        byte p = 1, q = 1;
        do {
            p = (byte)(p ^ (p << 1) ^ ((p & 0x80) > 0 ? 0x1B : 0));
            System.out.println("p: " + p);
            q ^= q << 1;
            q ^= q << 2;
            q ^= q << 4;
            q ^= (q & 0x80) > 0 ? 0x09 : 0;
            sbox[p] = (byte)(q ^ (q << 4 | q >> 4)^(q << 3 | q >> 5) ^ (q << 2 | q >> 6) ^ (q << 1 | q >> 7) ^ 0x63);
        } while (p != 1);

        sbox[0] = (byte)0x63;
    }

    //费马小定理求逆元 求 ab mod n = 1的逆元b
//    private static int getInverse(int a, int n) {
//        return pow_mod(a, n-2, n);
//    }



    /**
     * (a*a^-1 = 1) mod n -> (a*a^-1+ k*n) mod n = 1
     * 扩展欧几里得求a的逆元 (a*x+by)mod n = 1 其中gcd(a, b) = 1 所以x就是a的逆元 b就是n y就是k(y这里求出来没啥用)
     * @param a 求a的逆元 已知
     * @param b 就是n 已知
     */
    private static void gcd(int a, int b, Int d, Int x, Int y) {

        //当b==0 gcd(a, b) = a, 所以(a*1+0*y)mod n = a -> x=1 y=0 d=a 然后递归回去逆推回去
        if(b == 0) {
            d.setX(a);
            x.setX(1);
            y.setX(0);
        }
        else {
            gcd(b, a % b, d, y, x);
            y.setX(y.getX() - x.getX() * (a/b));
        }
    }
    //计算模n下a的逆。如果不存在逆， 返回-1
    private static int inv(int a, int n) {
        Int d = new Int(0), x = new Int(0), y = new Int(0);
        gcd(a, n, d, x, y);
        return d.getX() == 1 ? (x.getX()+n)%n : -1;
    }

    //快速幂 求a^b mod n
    private static int pow_mod(int a, int b, int n) {
        int ans = 1;
        while (b > 0) {
            if ((b&1) == 1) {
                ans = ans*a;
                ans %= n;
            }
            a *= a;
            a %= n;
        }
        return ans;
    }

    static class Int {
        int x;
        public Int(int x) {
            this.x = x;
        }
        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }
    }
}
