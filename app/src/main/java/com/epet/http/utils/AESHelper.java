package com.epet.http.utils;

import android.support.annotation.NonNull;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 作者：yuer on 2017/8/23 17:50
 * 邮箱：heziyu222@163.com
 */

public class AESHelper {
    public static String LOGIN_PWD_AES_KEY = "bd3af6c6929728a3";
    /**
     * 文本加密方法
     *
     * @param text:String
     * @return String
     */
    public static final String Encrypt(String text) {
        return AESHelper.Encrypt(text, LOGIN_PWD_AES_KEY);
    }

    /**
     * 文本解密方法
     *
     * @param text:String
     * @return String
     */
    public static final String Decrypt(String text) {
        return AESHelper.Decrypt(text, LOGIN_PWD_AES_KEY);
    }

    /**
     * 1.加密
     *
     * @param text:加密文本
     * @param key:加密key
     */
    public static final String Encrypt(@NonNull String text, @NonNull String key) {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            return byte2hex(encrypted).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 2.解密
     *
     * @param pwd_txt:需要解密的密文
     * @param key:解密key
     */
    public static String Decrypt(@NonNull String pwd_txt, @NonNull String key) {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = hex2byte(pwd_txt);// 16進制字符串轉換成byte[]
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 16進制字符串-->byte[]
     */
    public static byte[] hex2byte(@NonNull String strhex) {
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    /**
     * byte[]-->16進制字符串
     */
    public static String byte2hex(@NonNull byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
}
