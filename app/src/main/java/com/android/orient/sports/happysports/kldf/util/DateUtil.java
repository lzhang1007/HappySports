package com.android.orient.sports.happysports.kldf.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat longFmt = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat str2Fmt = new SimpleDateFormat("MM/dd HH:mm");
    private static final SimpleDateFormat str3Fmt = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat strFmt = new SimpleDateFormat("yyyy-MM-dd");

    public static String getCurrentDateStr() {
        try {
            return strFmt.format(new Date());
        } catch (Exception e) {
            return "";
        }
    }

    public static String getCurrentTimeStr() {
        try {
            return str2Fmt.format(new Date());
        } catch (Exception e) {
            return "";
        }
    }

    public static Long getCurrentDateLong() {
        try {
            return Long.valueOf(Long.parseLong(longFmt.format(new Date())));
        } catch (Exception e) {
            return Long.valueOf(0);
        }
    }
//
//    public static Long getDateLong(int year, int month, int day) {
////        try {
////            return Long.parseLong(String.valueOf(year) + (month < 10 ? Constant.BRACELET_UNBIND + month : Integer.valueOf(month)) + (day < 10 ? new StringBuilder(Constant.BRACELET_UNBIND).append(day).toString() : Integer.valueOf(day)));
////        } catch (Exception e) {
////            return Long.valueOf(0);
////        }
//    }
//
//    public static String getDate(int year, int month, int day) {
//        try {
//            return new StringBuilder(String.valueOf(year)).append("-").append(month < 10 ? new StringBuilder(Constant.BRACELET_UNBIND).append(month).toString() : Integer.valueOf(month)).append("-").append(day < 10 ? new StringBuilder(Constant.BRACELET_UNBIND).append(day).toString() : Integer.valueOf(day)).toString();
//        } catch (Exception e) {
//            return "";
//        }
//    }
//
//    public static String getLastSyncTime() {
//        if (StepCacheUtil.getLastSyncTime().longValue() == 0) {
//            return "从未同步";
//        }
//        long time = System.currentTimeMillis() - StepCacheUtil.getLastSyncTime().longValue();
//        if (time < 60000) {
//            return "刚刚";
//        }
//        if (time > 60000 && time < 3600000) {
//            return (time / 60000) + "分钟前";
//        }
//        if (time > 3600000 && time < 86400000) {
//            return (time / 3600000) + "小时前";
//        }
//        if (time <= 86400000 || time >= 1471228928) {
//            return "";
//        }
//        return (time / 86400000) + "天前";
//    }

    public static String getLastGetTime() {
        if (StepCacheUtil.getLastGetTime().longValue() == 0) {
            return "从未获取";
        }
        long time = System.currentTimeMillis() - StepCacheUtil.getLastGetTime().longValue();
        if (time < 60000) {
            return "刚刚";
        }
        if (time > 60000 && time < 3600000) {
            return (time / 60000) + "分钟前";
        }
        if (time > 3600000 && time < 86400000) {
            return (time / 3600000) + "小时前";
        }
        if (time <= 86400000 || time >= 1471228928) {
            return "";
        }
        return (time / 86400000) + "天前";
    }

    public static String getLastUpdateTime() {
        return "最后更新：今天" + str3Fmt.format(new Date());
    }
}
