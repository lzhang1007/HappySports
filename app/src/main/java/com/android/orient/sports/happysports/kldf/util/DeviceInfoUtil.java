package com.android.orient.sports.happysports.kldf.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.android.orient.sports.happysports.kldf.BaseApplication;

import static com.android.orient.sports.happysports.kldf.util.CacheUtil.getAppShared;

/**
 * Created by zhanglei on 2017/4/18.
 */

public final class DeviceInfoUtil {
    private static String imei = "";

    public static String getIMEI() {
        if (TextUtils.isEmpty(imei)) {
            try {
                Context context = BaseApplication.getInstance();
                imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                if (imei == null) {
                    imei = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imei;
    }

    public static String getVersion() {
        return getAppShared().getString("app_version", "1.352");
    }
}
