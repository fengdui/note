package com.base.util;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 二进制,字节数组,字符,十六进制,BCD编码转换
 * 
 * @author Jackie
 * 
 */
public final class Converter {
  public final static char[] BToA = "0123456789abcdef".toCharArray();

  /**
   * 把16进制字符串转换成字节数组
   * 
   * @param hex
   * @return
   */
  public static byte[] hexStringToByte(String hex) {
    int len = (hex.length() / 2);
    byte[] result = new byte[len];
    char[] achar = hex.toCharArray();
    for (int i = 0; i < len; i++) {
      int pos = i * 2;
      result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
    }
    return result;
  }

  private static byte toByte(char c) {
    byte b = (byte) "0123456789ABCDEF".indexOf(c);
    return b;
  }

  /** */
  /**
   * 把字节数组转换成16进制字符串
   * 
   * @param bArray
   * @return
   */
  public static final String bytesToHexString(byte[] bArray) {
    StringBuffer sb = new StringBuffer(bArray.length);
    String sTemp;
    for (int i = 0; i < bArray.length; i++) {
      sTemp = Integer.toHexString(0xFF & bArray[i]);
      if (sTemp.length() < 2)
        sb.append(0);
      sb.append(sTemp.toUpperCase());
    }
    return sb.toString();
  }

  /** */
  /**
   * 把字节数组转换为对象
   * 
   * @param bytes
   * @return
   * @throws java.io.IOException
   * @throws ClassNotFoundException
   */
  public static final Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    ObjectInputStream oi = new ObjectInputStream(in);
    Object o = oi.readObject();
    oi.close();
    return o;
  }

  /** */
  /**
   * 把可序列化对象转换成字节数组
   *
   * @param s
   * @return
   * @throws java.io.IOException
   */
  public static final byte[] objectToBytes(Serializable s) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream ot = new ObjectOutputStream(out);
    ot.writeObject(s);
    ot.flush();
    ot.close();
    return out.toByteArray();
  }

  public static final String objectToHexString(Serializable s) throws IOException {
    return bytesToHexString(objectToBytes(s));
  }

  public static final Object hexStringToObject(String hex) throws IOException,
      ClassNotFoundException {
    return bytesToObject(hexStringToByte(hex));
  }

  /** */
  /**
   * @函数功能: BCD码转为10进制串(阿拉伯数据)
   * @输入参数: BCD码
   * @输出结果: 10进制串
   */
  public static String bcd2Str(byte[] bytes) {
    StringBuffer temp = new StringBuffer(bytes.length * 2);

    for (int i = 0; i < bytes.length; i++) {
      temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
      temp.append((byte) (bytes[i] & 0x0f));
    }
    return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1)
        : temp.toString();
  }

  /** */
  /**
   * @函数功能: 10进制串转为BCD码
   * @输入参数: 10进制串
   * @输出结果: BCD码
   */
  public static byte[] str2Bcd(String asc) {
    int len = asc.length();
    int mod = len % 2;

    if (mod != 0) {
      asc = "0" + asc;
      len = asc.length();
    }

    byte abt[] = new byte[len];
    if (len >= 2) {
      len = len / 2;
    }

    byte bbt[] = new byte[len];
    abt = asc.getBytes();
    int j, k;

    for (int p = 0; p < asc.length() / 2; p++) {
      if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
        j = abt[2 * p] - '0';
      } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
        j = abt[2 * p] - 'a' + 0x0a;
      } else {
        j = abt[2 * p] - 'A' + 0x0a;
      }

      if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
        k = abt[2 * p + 1] - '0';
      } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
        k = abt[2 * p + 1] - 'a' + 0x0a;
      } else {
        k = abt[2 * p + 1] - 'A' + 0x0a;
      }

      int a = (j << 4) + k;
      byte b = (byte) a;
      bbt[p] = b;
    }
    return bbt;
  }

  /**
   * @函数功能: BCD码转ASC码
   * @输入参数: BCD串
   * @输出结果: ASC码
   */
  public static String BCD2ASC(byte[] bytes) {
    StringBuffer temp = new StringBuffer(bytes.length * 2);

    for (int i = 0; i < bytes.length; i++) {
      int h = ((bytes[i] & 0xf0) >>> 4);
      int l = (bytes[i] & 0x0f);
      temp.append(BToA[h]).append(BToA[l]);
    }
    return temp.toString();
  }

  /**
   * MD5加密字符串，返回加密后的16进制字符串
   * 
   * @param origin
   * @return
   */
  public static String MD5EncodeToHex(String origin) {
    return bytesToHexString(MD5Encode(origin));
  }

  /** */
  /**
   * MD5加密字符串，返回加密后的字节数组
   * 
   * @param origin
   * @return
   */
  public static byte[] MD5Encode(String origin) {
    return MD5Encode(origin.getBytes());
  }

  /** */
  /**
   * MD5加密字节数组，返回加密后的字节数组
   * 
   * @param bytes
   * @return
   */
  public static byte[] MD5Encode(byte[] bytes) {
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("MD5");
      return md.digest(bytes);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return new byte[0];
    }

  }

  public static String encodeHexString(String sourceText) {

    byte[] rawData = sourceText.getBytes();
    StringBuffer hexText = new StringBuffer();
    String initialHex = null;
    int initHexLength = 0;

    for (int i = 0; i < rawData.length; i++) {
      int positiveValue = rawData[i] & 0x000000FF;
      initialHex = Integer.toHexString(positiveValue);
      initHexLength = initialHex.length();
      while (initHexLength++ < 2) {
        hexText.append("0");
      }
      hexText.append(initialHex);
    }
    return hexText.toString().toUpperCase();
  }

  public static String decodeHexString(String hexText) {

    String decodedText = null;
    String chunk = null;

    if (hexText != null && hexText.length() > 0) {
      int numBytes = hexText.length() / 2;

      byte[] rawToByte = new byte[numBytes];
      int offset = 0;
      for (int i = 0; i < numBytes; i++) {
        chunk = hexText.substring(offset, offset + 2);
        offset += 2;
        rawToByte[i] = (byte) (Integer.parseInt(chunk, 16) & 0x000000FF);
      }
      decodedText = new String(rawToByte);
    }
    return decodedText;
  }

  /**
   * 十进制转十六进制
   * 
   * @param dec
   * @return
   */
  public static String decToHex(String dec) {
    return binToHex(decToBin(dec));
  }

  /**
   * 十进制转八进制
   * 
   * @param dec
   * @return
   */
  public static String decToOct(String dec) {
    return binToOct(decToBin(dec));
  }

  /**
   * 十进制转二进制
   * 
   * @param dec
   * @return
   */
  public static String decToBin(String dec) {
    if (!isDecimal(dec)) {
      return "Invalid input!";
    }
    int val = Integer.parseInt(dec);
    if (val == 0) {
      return "0";
    }
    StringBuffer bin = new StringBuffer();
    for (; val != 1; val = val / 2) {
      bin.append((val % 2) + "");
    }
    bin.append("1");
    return reverseString(bin.toString());
  }

  /**
   * 八进制转十进制
   * 
   * @param oct
   * @return
   */
  public static String octToDec(String oct) {
    return binToDec(octToBin(oct));
  }

  /**
   * 八进制转十六进制
   * 
   * @param oct
   * @return
   */
  public static String octToHex(String oct) {
    return binToHex(octToBin(oct));
  }

  /**
   * 八进制转二进制
   * 
   * @param oct
   * @return
   */
  public static String octToBin(String oct) {
    if (!isOctal(oct)) {
      return "Invalid input!";
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < oct.length(); i++) {
      if (oct.charAt(i) == '0') {
        sb.append("000");
      } else if (oct.charAt(i) == '1') {
        sb.append("001");
      } else if (oct.charAt(i) == '2') {
        sb.append("010");
      } else if (oct.charAt(i) == '3') {
        sb.append("011");
      } else if (oct.charAt(i) == '4') {
        sb.append("100");
      } else if (oct.charAt(i) == '5') {
        sb.append("101");
      } else if (oct.charAt(i) == '6') {
        sb.append("110");
      } else if (oct.charAt(i) == '7') {
        sb.append("111");
      }
    }
    return sb.toString();
  }

  /**
   * 十六进制转十进制
   * 
   * @param hex
   * @return
   */
  public static String hexToDec(String hex) {
    return binToDec(hexToBin(hex));
  }

  /**
   * 十六进制转八进制
   * 
   * @param hex
   * @return
   */
  public static String hexToOct(String hex) {
    return binToOct(hexToBin(hex));
  }

  /**
   * 十六进制转二进制
   * 
   * @param hex
   * @return
   */
  public static String hexToBin(String hex) {
    hex = hex.toUpperCase();
    if (!isHexaDecimal(hex)) {
      return "Invalid input!";
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < hex.length(); i++) {
      if (hex.charAt(i) == '0') {
        sb.append("0000");
      } else if (hex.charAt(i) == '1') {
        sb.append("0001");
      } else if (hex.charAt(i) == '2') {
        sb.append("0010");
      } else if (hex.charAt(i) == '3') {
        sb.append("0011");
      } else if (hex.charAt(i) == '4') {
        sb.append("0100");
      } else if (hex.charAt(i) == '5') {
        sb.append("0101");
      } else if (hex.charAt(i) == '6') {
        sb.append("0110");
      } else if (hex.charAt(i) == '7') {
        sb.append("0111");
      } else if (hex.charAt(i) == '8') {
        sb.append("1000");
      } else if (hex.charAt(i) == '9') {
        sb.append("1001");
      } else if (hex.charAt(i) == 'A') {
        sb.append("1010");
      } else if (hex.charAt(i) == 'B') {
        sb.append("1011");
      } else if (hex.charAt(i) == 'C') {
        sb.append("1100");
      } else if (hex.charAt(i) == 'D') {
        sb.append("1101");
      } else if (hex.charAt(i) == 'E') {
        sb.append("1110");
      } else if (hex.charAt(i) == 'F') {
        sb.append("1111");
      }
    }
    return sb.toString();
  }

  /**
   * 判断是否是二进制格式
   * 
   * @param bin
   * @return
   */
  public static boolean isBinary(String bin) {
    return bin.matches("[0|1]+");

  }

  /**
   * 判断是否是八进制格式
   * 
   * @param bin
   * @return
   */
  public static boolean isOctal(String bin) {
    return bin.matches("[0-7]+");

  }

  /**
   * 判断是否是十六进制格式
   * 
   * @param bin
   * @return
   */
  public static boolean isHexaDecimal(String bin) {
    return bin.toUpperCase().matches("[0-9|A-F]+");

  }

  /**
   * 判断是否是十进制格式
   * 
   * @param bin
   * @return
   */
  public static boolean isDecimal(String bin) {
    return bin.matches("[0-9]+");

  }

  /**
   * 二进制转十进制
   * 
   * @param bin
   * @return
   */
  public static String binToDec(String bin) {
    if (!isBinary(bin)) {
      return "Invalid input!";
    }
    int nOfDigits = bin.length();
    long val = 0;
    bin = reverseString(bin);
    for (int i = 0; i < nOfDigits; i++) {
      try {
        val = val + Integer.parseInt(bin.charAt(i) + "") * ((long) Math.pow(2, i));
      } catch (NumberFormatException nfe) {
        return "Invalid input!";
      }
    }
    return val + "";
  }

  /**
   * 二进制转八进制
   * 
   * @param bin
   * @return
   */
  public static String binToOct(String bin) {
    if (!isBinary(bin)) {
      return "Invalid input!";
    }
    String[] tokens = splitByWidth(bin, 3, '0');
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < tokens.length; i++) {
      if (tokens[i].equals("000")) {
        sb.append("0");
      } else if (tokens[i].equals("001")) {
        sb.append("1");
      } else if (tokens[i].equals("010")) {
        sb.append("2");
      } else if (tokens[i].equals("011")) {
        sb.append("3");
      } else if (tokens[i].equals("100")) {
        sb.append("4");
      } else if (tokens[i].equals("101")) {
        sb.append("5");
      } else if (tokens[i].equals("110")) {
        sb.append("6");
      } else if (tokens[i].equals("111")) {
        sb.append("7");
      }
    }
    return sb.toString();
  }

  /**
   * 二进制转十六进制
   * 
   * @param bin
   * @return
   */
  public static String binToHex(String bin) {
    if (!isBinary(bin)) {
      return "Invalid input!";
    }
    String[] tokens = splitByWidth(bin, 4, '0');
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < tokens.length; i++) {
      if (tokens[i].equals("0000")) {
        sb.append("0");
      } else if (tokens[i].equals("0001")) {
        sb.append("1");
      } else if (tokens[i].equals("0010")) {
        sb.append("2");
      } else if (tokens[i].equals("0011")) {
        sb.append("3");
      } else if (tokens[i].equals("0100")) {
        sb.append("4");
      } else if (tokens[i].equals("0101")) {
        sb.append("5");
      } else if (tokens[i].equals("0110")) {
        sb.append("6");
      } else if (tokens[i].equals("0111")) {
        sb.append("7");
      } else if (tokens[i].equals("1000")) {
        sb.append("8");
      } else if (tokens[i].equals("1001")) {
        sb.append("9");
      } else if (tokens[i].equals("1010")) {
        sb.append("A");
      } else if (tokens[i].equals("1011")) {
        sb.append("B");
      } else if (tokens[i].equals("1100")) {
        sb.append("C");
      } else if (tokens[i].equals("1101")) {
        sb.append("D");
      } else if (tokens[i].equals("1110")) {
        sb.append("E");
      } else if (tokens[i].equals("1111")) {
        sb.append("F");
      }
    }
    return sb.toString();
  }

  // split string by specified width and fill empty spaces
  public static String[] splitByWidth(String str, int width, char fillChar) {
      String[] list = null;
      int strLen = str.length();
      StringBuffer sb = new StringBuffer(reverseString(str));
      int mod = strLen % width;
      if (!(mod == 0)) {
          for (int i = 0; i > width - mod; i++) {
              sb.append(fillChar);
          }
      }
      strLen = sb.length();
      list = new String[strLen / width];
      sb = new StringBuffer(reverseString(sb.substring(0)));
      StringBuffer tmp = new StringBuffer();
      int listCounter = 0;
      for (int i = 0; i < strLen; i++) {
          if (i % width == 0 && i != 0) {
              list[listCounter] = tmp.toString();
              listCounter++;
              tmp = new StringBuffer();
          }
          tmp.append(sb.charAt(i));
      }
      list[listCounter] = tmp.toString();
      listCounter++;
      return list;
  }

  /**
   * 字符串顺序反转
   * @param str
   * @return
   */
  public static String reverseString(String str) {
    str = str.trim();
    int strl = str.length();
    if (strl > 1) {
        StringBuffer sb = new StringBuffer();
        for (int i = strl - 1; i >= 0; i--) {
            sb.append(str.charAt(i) + "");
        }
        return sb.toString();
    }
    return str;
}

  public static void main(String[] args) {
    System.out.println(Converter.decToHex(new Integer(655350).toString()));
  }
}