package com.android.orient.practice.kldf.base;

import org.json.JSONObject;

/**
 * Created by zhanglei on 2017/12/7.
 */

public interface ServiceCallBack {
    void onStart();

    void onEnd();

    void onSuccess(JSONObject jSONObject);

    void onFailed(String message);
}
