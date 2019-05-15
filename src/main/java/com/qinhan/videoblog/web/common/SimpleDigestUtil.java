package com.qinhan.videoblog.web.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 简单消息摘要工具类
 * Created by Lucas on 2017/7/19.
 */
public class SimpleDigestUtil {

    public static final String SHA256 = "SHA-256";
    public static final String MD5 = "MD5";

    public static String encrypt(String data, String algorithmName) {
        if (data == null || data.length() == 0 || algorithmName == null) {
            return null;
        }
        MessageDigest digest = null;
        String result = null;
        try {
            digest = MessageDigest.getInstance(algorithmName);
            digest.update(data.getBytes());
            byte[] bytes = digest.digest();
            return byte2hex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp;
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }


    public static String encryptSHA(String data) {
        return encrypt(data, SHA256);
    }

    public static String encryptMD5(String data) {
        return encrypt(data, MD5);
    }

}
