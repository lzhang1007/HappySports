package com.android.orient.sports.happysports.utils;

import com.android.orient.practice.kldf.base.ServiceCallBack;
import com.android.orient.practice.kldf.kldf.constant.HttpConstant;
import com.android.orient.practice.kldf.kldf.entity.req.AddStepReq;
import com.android.orient.practice.kldf.kldf.entity.req.LoginReq;
import com.android.orient.practice.kldf.kldf.entity.resp.LoginResp;
import com.android.orient.practice.kldf.kldf.util.CacheUtil;
import com.android.orient.practice.kldf.kldf.util.DateUtil;
import com.android.orient.practice.kldf.kldf.util.HttpHandler;
import com.android.orient.practice.kldf.kldf.util.HttpUtilsKt;
import com.android.orient.practice.kldf.kldf.util.MyUtil;
import com.android.orient.practice.kldf.kldf.util.StepCacheUtil;
import com.encrypt.EncryptUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.android.orient.practice.kldf.kldf.util.CacheUtil.getAppShared;

/**
 * @author zhanglei
 * @date 2017/12/7
 */
public final class DataUpdateUtil {

    public static void sendLoginService(final String account, final String password, final String version, final ServiceCallBack callBack) {
        final LoginReq req = new LoginReq();
        req.setLoginName(account);
        req.setPassword(password);
        req.setChannelId(CacheUtil.getChannelId());
        req.setSign(EncryptUtil.md5(req.getLoginName() + req.getChannelId()));

        httpPost(HttpConstant.USER_LOGIN, req, (jSONObject) -> {
            try {
                if (HttpConstant.SUCCESS.equals(jSONObject.getString("result"))) {
                    LoginResp resp = new LoginResp();
                    MyUtil.json2object(jSONObject, resp);
                    CacheUtil.setMemberId(resp.getMemberId());
                    CacheUtil.setAccount(account);
                    CacheUtil.setToken(resp.getVhstoken());
                    CacheUtil.setIsLoginHome(true);
                    CacheUtil.putAppShared("password", password);
                    CacheUtil.putAppShared("app_version", version);
                    StepCacheUtil.setStride((float) resp.getStride());
                    StepCacheUtil.setBraceletMac(resp.getHandMac());
                    CacheUtil.setLoginNum(resp.getLoginNum());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, callBack);
    }

    public static void loginAndSyncStep(ServiceCallBack callBack) {
        String account = CacheUtil.getAccount();
        String password = getAppShared().getString("password", "");
        String version = getAppShared().getString("app_version", "1.42");
        if (account.isEmpty() || password.isEmpty() || version.isEmpty()) {
            sendLoginService(account, password, version, new ServiceCallBack() {
                @Override
                public void onStart() {
                }

                @Override
                public void onEnd() {

                }

                @Override
                public void onSuccess(JSONObject jSONObject) {
                    sendStepService(callBack);
                }

                @Override
                public void onFailed(String message) {

                }
            });
        }
    }

    private static void sendStepService(ServiceCallBack callBack) {
        Random random = new Random();
        int step = random.nextInt(5000) + 10000;
        sendStepService(step, callBack);
    }

    public static void sendStepService(int step, final ServiceCallBack callBack) {
        if (step > 35000) {
            return;
        }
        AddStepReq req = new AddStepReq();
        req.setTimestamp(String.valueOf(System.currentTimeMillis()));
        req.setSteps(getStepsInfo(step));
        req.setSign(EncryptUtil.md5(CacheUtil.getToken() + req.getTimestamp()));
        httpPost(HttpConstant.ADD_STEP, req, (jSONObject) -> {
            try {
                if (HttpConstant.SUCCESS.equals(jSONObject.getString("result"))) {
                    CacheUtil.putAppShared("token_date", "" + System.currentTimeMillis());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, callBack);
    }

    private static String getStepsInfo(int step) {
        CacheUtil.putAppShared("last_steps", step);
        return "[" +
                "{\"sportDate\":\"" + DateUtil.getCurrentDateStr() + "\"," +
                "\"step\":" + step + "," +
                "\"handMac\":\"" + StepCacheUtil.getBraceletMac() + "\"}" +
                "]";
    }

    private static void httpPost(String url, Object params, final HttpHandler httpHandler, final ServiceCallBack callBack) {
        if (callBack != null) {
            callBack.onStart();
        }
        Disposable disposable = Single.create((SingleOnSubscribe<JSONObject>) e -> {
            JSONObject jSONObject = HttpUtilsKt.post(url, params);
            try {
                if (null != httpHandler) {
                    httpHandler.callBack(jSONObject);
                }
                String resultCode = jSONObject.getString("result");
                if (HttpConstant.SUCCESS.equals(resultCode)) {
                    e.onSuccess(jSONObject);
                } else {
                    e.onError(new Exception(jSONObject.toString()));
                }

            } catch (Exception e1) {
                e.onError(e1);
                e1.printStackTrace();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    if (callBack != null) {
                        callBack.onEnd();
                        callBack.onSuccess(jsonObject);
                    }
                }, throwable -> {
                    if (callBack != null) {
                        callBack.onEnd();
                        callBack.onFailed(throwable.getMessage());
                    }
                });

    }
}
