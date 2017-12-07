package com.android.orient.practice.kldf.kldf.util;


public class StepCacheUtil {
    public static Integer getDeviceType() {
        return Integer.valueOf(CacheUtil.getMemberShared().getInt("deviceType", 0));
    }

    public static void setDeviceType(Integer deviceType) {
        CacheUtil.putMemberShared("deviceType", deviceType);
    }

    public static String getBraceletMac() {
        String encBraceletMac = CacheUtil.getMemberShared().getString("braceletMac", "");
        if ("".equals(encBraceletMac)) {
            return encBraceletMac;
        }
        return AESUtil.decrypt(encBraceletMac);
    }

    public static void setBraceletMac(String braceletMac) {
        CacheUtil.putMemberShared("braceletMac", AESUtil.encrypt(braceletMac));
    }

    public static Integer getBindStep() {
        String encBindStep = CacheUtil.getMemberShared().getString("bindStep", "");
        if ("".equals(encBindStep)) {
            return Integer.valueOf(0);
        }
        return Integer.valueOf(Integer.parseInt(AESUtil.decrypt(encBindStep)));
    }

    public static void setBindStep(Integer bindStep) {
        CacheUtil.putMemberShared("bindStep", AESUtil.encrypt(String.valueOf(bindStep)));
    }

    public static String getBraceletDate() {
        return CacheUtil.getMemberShared().getString("braceletDate", "");
    }

    public static void setBraceletDate(String braceletDate) {
        CacheUtil.putMemberShared("braceletDate", braceletDate);
    }

    public static Integer getLastSyncStep() {
        return Integer.valueOf(CacheUtil.getMemberShared().getInt("lastSyncStep", 0));
    }

    public static void setLastSyncStep(Integer lastSyncStep) {
        CacheUtil.putMemberShared("lastSyncStep", lastSyncStep);
    }

    public static Long getBindDateLong() {
        return Long.valueOf(CacheUtil.getMemberShared().getLong("braceletDateLong", 0));
    }

    public static void setBindDateLong(Long braceletDateLong) {
        CacheUtil.putMemberShared("braceletDateLong", braceletDateLong);
    }

    public static Integer getTotalStep() {
        String encTotalStep = CacheUtil.getMemberShared().getString("totalStep", "");
        if ("".equals(encTotalStep)) {
            return Integer.valueOf(0);
        }
        return Integer.valueOf(Integer.parseInt(AESUtil.decrypt(encTotalStep)));
    }

    public static void setTotalStep(Integer totalStep) {
        CacheUtil.putMemberShared("totalStep", AESUtil.encrypt(String.valueOf(totalStep)));
    }

    public static Integer getMobileStep() {
        String encMobileStep = CacheUtil.getMemberShared().getString("mobileStep", "");
        if ("".equals(encMobileStep)) {
            return Integer.valueOf(0);
        }
        return Integer.valueOf(Integer.parseInt(AESUtil.decrypt(encMobileStep)));
    }

    public static void setMobileStep(Integer mobileStep) {
        CacheUtil.putMemberShared("mobileStep", AESUtil.encrypt(String.valueOf(mobileStep)));
    }

    public static Integer getBraceletStep() {
        String encBraceletStep = CacheUtil.getMemberShared().getString("braceletStep", "");
        if ("".equals(encBraceletStep)) {
            return Integer.valueOf(0);
        }
        return Integer.valueOf(Integer.parseInt(AESUtil.decrypt(encBraceletStep)));
    }

    public static void setBraceletStep(Integer braceletStep) {
        CacheUtil.putMemberShared("braceletStep", AESUtil.encrypt(String.valueOf(braceletStep)));
    }

    public static Integer getBraceletOldStep() {
        String encBraceletOldStep = CacheUtil.getMemberShared().getString("braceletOldStep", "");
        if ("".equals(encBraceletOldStep)) {
            return Integer.valueOf(0);
        }
        return Integer.valueOf(Integer.parseInt(AESUtil.decrypt(encBraceletOldStep)));
    }

    public static void setBraceletOldStep(Integer braceletOldStep) {
        CacheUtil.putMemberShared("braceletOldStep", AESUtil.encrypt(String.valueOf(braceletOldStep)));
    }

    public static Integer getBraceletTotalStep() {
        String encBraceletTotalStep = CacheUtil.getMemberShared().getString("braceletTotalStep", "");
        if ("".equals(encBraceletTotalStep)) {
            return Integer.valueOf(0);
        }
        return Integer.valueOf(Integer.parseInt(AESUtil.decrypt(encBraceletTotalStep)));
    }

    public static void setBraceletTotalStep(Integer braceletTotalStep) {
        CacheUtil.putMemberShared("braceletTotalStep", AESUtil.encrypt(String.valueOf(braceletTotalStep)));
    }

    public static Float getStride() {
        return CacheUtil.getMemberShared().getFloat("stride", 1f);
    }

    public static void setStride(Float stride) {
        CacheUtil.putMemberShared("stride", stride);
    }

//    public static Long getLastSyncTime() {
//        return Long.valueOf(CacheUtil.getMemberShared().getLong(StepAccelerometer.KEY_LAST_SYNC_TIME, 0));
//    }
//
//    public static void setLastSyncTime(Long lastSyncTime) {
//        CacheUtil.putMemberShared(StepAccelerometer.KEY_LAST_SYNC_TIME, lastSyncTime);
//    }

    public static Long getCurrentDateLong() {
        return CacheUtil.getMemberShared().getLong("currentDateLong", 0);
    }

    public static void setCurrentDateLong(Long currentDateLong) {
        CacheUtil.putMemberShared("currentDateLong", currentDateLong);
    }

    public static Integer getMobileLastSyncStep() {
        return CacheUtil.getMemberShared().getInt("mobileLastSyncStep", 0);
    }

    public static void setMobileLastSyncStep(Integer mobileLastSyncStep) {
        CacheUtil.putMemberShared("mobileLastSyncStep", mobileLastSyncStep);
    }

    public static Long getLastGetTime() {
        return Long.valueOf(CacheUtil.getMemberShared().getLong("lastGetTime", 0));
    }

    public static void setLastGetTime(Long lastGetTime) {
        CacheUtil.putMemberShared("lastGetTime", lastGetTime);
    }

    public static String getDeviceName() {
        return CacheUtil.getAppShared().getString("_deviceName", "ID107");
    }

    public static void setDeviceName(String deviceName) {
        CacheUtil.putMemberShared("_deviceName", deviceName);
    }

    public static String getEnerge() {
        return CacheUtil.getAppShared().getString("_energe", "80");
    }

    public static void setEnerge(String energe) {
        CacheUtil.putMemberShared("_energe", energe);
    }
}
