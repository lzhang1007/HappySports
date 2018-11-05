package com.android.orient.practice.kldf.kldf.util;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import static com.android.orient.practice.kldf.kldf.util.CacheUtil.getAppShared;

/**
 * @author zhanglei
 * @date 2017/4/18
 */
public final class DeviceInfoUtil {
    private static String imei = "";
    public static Application instance;

    static String getIMEI() {
        if (TextUtils.isEmpty(imei)) {
            try {
                Context context = instance;
                if (context != null && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    if (imei == null) {
                        imei = "";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imei;
    }

    public static String getVersion() {
        return getAppShared().getString("app_version", "1.36");
    }
}
