package com.android.orient.practice.kldf.kldf.util;

import android.util.Base64;

import com.android.orient.practice.kldf.kldf.constant.Constant;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    private static final String LOCAL_KEY = "1qaz2wsx3edc4rfv";

    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        } else if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        } else {
            SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes("utf-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, skeySpec);
            return Base64.encodeToString(cipher.doFinal(sSrc.getBytes("utf-8")), 0);
        }
    }

    public static String encrypt(String sSrc) {
        try {
            if ("".equals(sSrc)) {
                return sSrc;
            }
            return encrypt(sSrc, LOCAL_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String decrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            try {
                System.out.print("Key为空null");
                return null;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        } else {
            SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes("utf-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, skeySpec);
            try {
                return new String(cipher.doFinal(Base64.decode(sSrc, 0)), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static String decrypt(String sSrc) {
        try {
            if ("".equals(sSrc)) {
                return sSrc;
            }
            return decrypt(sSrc, LOCAL_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.BRACELET_UNBIND;
        }
    }
}
