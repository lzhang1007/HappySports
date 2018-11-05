package com.android.orient.practice.kldf.kldf.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.android.orient.practice.kldf.kldf.constant.HttpConstant;

import org.json.JSONObject;


/**
 * Created by zhanglei on 2017/4/17.
 */

public final class CacheUtil {
    private static final String NO_CACHE = "{result:\"111\", info:\"没有缓存数据\"}";
    private static Context context;

    public static void initCache(Context context) {
        CacheUtil.context = context;
    }

    public static SharedPreferences getAppShared() {
        return context.getSharedPreferences("VHS_GYT_APP", 0);
    }

    public static SharedPreferences getMemberShared() {
        return context.getSharedPreferences(getMemberId(), 0);
    }

    public static void clearAppShared() {
        setAutoStart(false);
        setToken("");
        setMemberId("");
    }

    public static SharedPreferences getAppShared(String key) {
        return context.getSharedPreferences(key, 0);
    }

    public static void putAppShared(String key, Object val) {
        Editor editor = getAppShared().edit();
        if (val instanceof String) {
            editor.putString(key, (String) val);
        } else if (val instanceof Integer) {
            editor.putInt(key, ((Integer) val).intValue());
        } else if (val instanceof Long) {
            editor.putLong(key, ((Long) val).longValue());
        } else if (val instanceof Float) {
            editor.putFloat(key, ((Float) val).floatValue());
        } else if (val instanceof Boolean) {
            editor.putBoolean(key, ((Boolean) val).booleanValue());
        }
        editor.commit();
    }

    public static void putMemberShared(String key, Object val) {
        Editor editor = getMemberShared().edit();
        if (val instanceof String) {
            editor.putString(key, (String) val);
        } else if (val instanceof Integer) {
            editor.putInt(key, ((Integer) val).intValue());
        } else if (val instanceof Long) {
            editor.putLong(key, ((Long) val).longValue());
        } else if (val instanceof Float) {
            editor.putFloat(key, ((Float) val).floatValue());
        } else if (val instanceof Boolean) {
            editor.putBoolean(key, ((Boolean) val).booleanValue());
        }
        editor.commit();
    }

    public static void setWelcome(boolean flag) {
        Editor editor = getAppShared("WELCOME").edit();
        editor.putBoolean("welcome", flag);
        editor.commit();
    }

    public static boolean getWelcome() {
        return getAppShared("WELCOME").getBoolean("welcome", false);
    }

    public static void setToken(String token) {
        putAppShared(HttpConstant.TOKEN, AESUtil.encrypt(token));
    }

    public static String getToken() {
        return AESUtil.decrypt(getAppShared().getString(HttpConstant.TOKEN, ""));
    }

    public static void setIsLoginHome(boolean flag) {
        putAppShared("isLoginHome", Boolean.valueOf(flag));
    }

    public static boolean getIsLoginHome() {
        return getAppShared().getBoolean("isLoginHome", false);
    }

    public static void setAcceptMsg(boolean flag) {
        putAppShared("acceptMsg", Boolean.valueOf(flag));
    }

    public static boolean getAcceptMsg() {
        return getAppShared().getBoolean("acceptMsg", false);
    }

    public static void setAutoStart(boolean flag) {
        putAppShared("autoStart", Boolean.valueOf(flag));
    }

    public static boolean getAutoStart() {
        return getAppShared().getBoolean("autoStart", false);
    }

    public static void setIsUpdateUser(boolean flag) {
        putAppShared("isUpdateUser", Boolean.valueOf(flag));
    }

    public static boolean getIsUpdateUser() {
        return getAppShared().getBoolean("isUpdateUser", false);
    }

    public static void setMemberId(String memberId) {
        putAppShared("memberId", AESUtil.encrypt(memberId));
    }

    public static String getMemberId() {
        return AESUtil.decrypt(getAppShared().getString("memberId", ""));
    }

    public static void setAccount(String account) {
        putAppShared("account", AESUtil.encrypt(account));
    }

    public static String getAccount() {
        return AESUtil.decrypt(getAppShared().getString("account", "zhanglei@orientsec.com.cn"));
    }

    public static void setStartPageUrl(String startPageUrl) {
        putAppShared("startPageUrl", startPageUrl);
    }

    public static String getStartPageUrl() {
        return getAppShared().getString("startPageUrl", "");
    }

    public static void setLat(String lat) {
        putAppShared("latitude", lat);
    }

    public static String getLat() {
        return getAppShared().getString("latitude", "0.0");
    }

    public static void setLng(String lng) {
        putAppShared("longitude", lng);
    }

    public static String getLng() {
        return getAppShared().getString("longitude", "0.0");
    }

    public static void setCompanyId(String companyId) {
        putMemberShared("companyId", AESUtil.encrypt(companyId));
    }

    public static String getCompanyId() {
        return AESUtil.decrypt(getAppShared().getString("companyId", ""));
    }

    public static void setLoginNum(Integer loginNum) {
        putMemberShared("loginNum", loginNum);
    }

    public static Integer getLoginNum() {
        return Integer.valueOf(getMemberShared().getInt("loginNum", 0));
    }

    public static void setChannelId(String channelId) {
        putAppShared("channelId", AESUtil.encrypt(channelId));
    }

    public static String getChannelId() {
        return AESUtil.decrypt(getAppShared().getString("channelId", ""));
    }

    public static void setIndexBanner(String indexBanner) {
        putMemberShared("indexBanner", AESUtil.encrypt(indexBanner));
    }

    public static JSONObject getIndexBanner() {
        return str2JSON(getMemberShared().getString("indexBanner", NO_CACHE));
    }

    public static void setIndexDynamic(String indexDynamic) {
        putMemberShared("indexDynamic", AESUtil.encrypt(indexDynamic));
    }

    public static JSONObject getIndexDynamic() {
        return str2JSON(getMemberShared().getString("indexDynamic", NO_CACHE));
    }

    public static void setDiscover(String discover) {
        putMemberShared("discover", AESUtil.encrypt(discover));
    }

    public static JSONObject getDiscover() {
        return str2JSON(getMemberShared().getString("discover", NO_CACHE));
    }

    public static void setMemberDetail(String memberDetail) {
        putMemberShared("memberDetail", AESUtil.encrypt(memberDetail));
    }

    public static JSONObject getMemberDetail() {
        return str2JSON(getMemberShared().getString("memberDetail", NO_CACHE));
    }

    public static void setMemberScore(String memberScore) {
        putMemberShared("memberScore", AESUtil.encrypt(memberScore));
    }

    public static JSONObject getMemberScore() {
        return str2JSON(getMemberShared().getString("memberScore", NO_CACHE));
    }

    public static void setIcon(String icon) {
        putMemberShared("icon", AESUtil.encrypt(icon));
    }

    public static JSONObject getIcon() {
        return str2JSON(getMemberShared().getString("icon", NO_CACHE));
    }

    public static void setNavigation(String navigation) {
        putMemberShared("navigation", AESUtil.encrypt(navigation));
    }

    public static JSONObject getNavigation() {
        return str2JSON(getMemberShared().getString("navigation", NO_CACHE));
    }

    private static JSONObject str2JSON(String jsonString) {
        try {
            if (!NO_CACHE.equals(jsonString)) {
                jsonString = AESUtil.decrypt(jsonString);
            }
            return new JSONObject(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setIsToast(boolean flag) {
        putAppShared("isToast", Boolean.valueOf(flag));
    }

    public static boolean getIsToast() {
        return getAppShared().getBoolean("isToast", false);
    }

}
