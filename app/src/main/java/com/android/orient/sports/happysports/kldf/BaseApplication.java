package com.android.orient.sports.happysports.kldf;

import android.app.Application;

import com.android.orient.sports.happysports.kldf.util.CacheUtil;

/**
 * Created by zhanglei on 2017/4/17.
 */

public class BaseApplication extends Application {

    public static Application instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CacheUtil.initCache(this);
    }
}
