package com.encrypt;

import android.util.Base64;

import java.security.MessageDigest;

/**
 * Created by zhanglei on 2017/4/17.
 */

public class EncryptUtil {
    private static final String pubKey = "30819D300D06092A864886F70D010101050003818B0030818702818100CED0DA2FABC76C3E0B5F19D19E7DD1A91A619BF745602530D07B5DF459E3A5A5E5FC63EC5C35F12D53EAC12FBBD5116A70BB6BAE1D9BAFE78CADC517F41DB22F4455190A572778636554A126AF0E7C1F180A45553E5A7E2AB2B378304597640154C302302B1692113B5DA7F0DADE4F17DC6A12094EFDCC7DA2A2A84425FB8FB3020111";

    public native String sessionKeyExchangeParam(String str, byte[] bArr, int i);

    static {
        try {
            System.loadLibrary("EncryptUtilJniAndroid");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String generatedKey(byte[] pSymKey) {
        return sessionKeyExchangeParam(pubKey, pSymKey, 0);
    }

    public static String md5(String str) {
        try {
            return Base64.encodeToString(MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8")), 2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
