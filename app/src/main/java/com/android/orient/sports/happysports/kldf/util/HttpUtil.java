package com.android.orient.sports.happysports.kldf.util;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Log;

import com.android.internal.http.multipart.FilePart;
import com.android.internal.http.multipart.MultipartEntity;
import com.android.internal.http.multipart.Part;
import com.android.internal.http.multipart.StringPart;
import com.android.orient.sports.happysports.kldf.constant.Constant;
import com.android.orient.sports.happysports.kldf.constant.HttpConstant;
import com.encrypt.EncryptUtil;
import com.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by zhanglei on 2017/4/17.
 */

public class HttpUtil {
    private static final int TIME_OUT = 8000;

    private static Map<String, Boolean> cacheUrl = new HashMap<String, Boolean>();

    static {
        cacheUrl.put(HttpConstant.GET_INDEXBANNER, true);
        cacheUrl.put(HttpConstant.GET_INDEXDYNAMIC, Boolean.TRUE);
        cacheUrl.put(HttpConstant.GET_DISCOVER, Boolean.TRUE);
        cacheUrl.put(HttpConstant.GET_MEMBERDETAIL, Boolean.TRUE);
        cacheUrl.put(HttpConstant.GET_MEMBERSCORE, Boolean.TRUE);
        cacheUrl.put(HttpConstant.GET_ICON, Boolean.TRUE);
        cacheUrl.put(HttpConstant.GET_NAVIGATION, Boolean.TRUE);
    }

    private static JSONObject sendPost(String url, Map<String, String> params, List<String> fileList) {
        try {
            MultipartEntity mEntity;
            List<Part> parts = new ArrayList<>();
            if (params != null) {
                for (Entry<String, String> entry : params.entrySet()) {
                    parts.add(new StringPart(entry.getKey(), entry.getValue()));
                }
            }
            if (fileList != null) {
                int index = 0;
                for (String file : fileList) {
                    index++;
                    try {
                        parts.add(new FilePart("pictrueFile_" + index, new File(file)));
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            mEntity = new MultipartEntity((Part[]) parts.toArray());
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            if (!CacheUtil.getToken().equals("")) {
                httpPost.addHeader(HttpConstant.TOKEN, CacheUtil.getToken());
            }
            httpPost.addHeader("osversion", "Android" + VERSION.RELEASE);
            httpPost.addHeader("model", Build.MODEL);
            httpPost.addHeader("imei", DeviceInfoUtil.getIMEI());
            httpPost.addHeader("appversion", DeviceInfoUtil.getVersion());
            httpPost.setEntity(mEntity);
            HttpResponse response = null;
            response = client.execute(httpPost);
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == 200) {
                Header[] headers = response.getAllHeaders();
                String respToken = "";
                if (headers != null) {
                    for (Header header : headers) {
                        if (HttpConstant.TOKEN.equals(header.getName())) {
                            respToken = header.getValue();
                            break;
                        }
                    }
                }
                ByteArrayOutputStream bs2 = new ByteArrayOutputStream();
                InputStream is = response.getEntity().getContent();
                byte[] buf = new byte[2048];
                while (true) {
                    int l = is.read(buf);
                    if (l == -1) {
                        break;
                    }
                    bs2.write(buf, 0, l);
                }
                JSONObject jSONObject = new JSONObject(new String(bs2.toByteArray()));
                if (!(jSONObject.has(HttpConstant.TOKEN) || "".equals(respToken))) {
                    jSONObject.put(HttpConstant.TOKEN, respToken);
                }
                is.close();
                bs2.close();
                return jSONObject;
            } else if (responseCode != 404) {
                return new JSONObject("{result:\"0099\", info:\"" + Constant.NETWORK_BAD + "\"}");
            } else {
                return new JSONObject("{result:\"0099\", info:\"" + Constant.NETWORK_BAD + "\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JSONObject sendPost(String url, Map<String, String> params) {
        try {
            // init client
            BasicHttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, TIME_OUT);
            HttpConnectionParams.setSoTimeout(httpParams, TIME_OUT);
            HttpClient client = new DefaultHttpClient(httpParams);
            // generate httpPost
            byte[] pSymKey = new byte[16];
            HttpPost httpPost = generateHttpPost(url, params, pSymKey);
            String aesKey = new String(pSymKey);
            // execute get response
            HttpResponse response = client.execute(httpPost);
            // parse response
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == 200) {
                JSONObject jsonObject = parseResponse(response.getAllHeaders(), aesKey, response.getEntity().getContent());
                Log.d("pedometerTag", "response = " + jsonObject.toString());
                cacheResponse(url, jsonObject.toString(), params.get("currentPageNum"));
                return jsonObject;
            } else if (responseCode != 404) {
                return new JSONObject("{result:\"0099\", info:\"" + Constant.NETWORK_BAD + "\"}");
            } else {
                return new JSONObject("{result:\"0099\", info:\"" + Constant.NETWORK_BAD + "\"}");
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            return null;
        }
    }

    private static HttpPost generateHttpPost(String url, Map<String, String> params, byte[] pSymKey) {
        try {
            HttpPost httpPost = new HttpPost(url);
            if (!CacheUtil.getToken().equals("")) {
                httpPost.addHeader(HttpConstant.TOKEN, CacheUtil.getToken());
            }
            httpPost.addHeader("encrypt", "android");
            httpPost.addHeader("osversion", "Android" + VERSION.RELEASE);
            httpPost.addHeader("model", Build.MODEL);
            httpPost.addHeader("imei", DeviceInfoUtil.getIMEI());
            httpPost.addHeader("appversion", DeviceInfoUtil.getVersion());

            List<BasicNameValuePair> list = new ArrayList();
            if (params == null) {
                params = new HashMap();
            }
            list.add(new BasicNameValuePair("key", new EncryptUtil().generatedKey(pSymKey)));
            String aesKey = new String(pSymKey);
            if (!params.isEmpty()) {
                list.add(new BasicNameValuePair("data", AESUtil.encrypt(new Gson().toJson(params), aesKey)));
            }
            Log.d("pedometerTag", "request = " + params.toString());
            httpPost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
            return httpPost;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JSONObject parseResponse(Header[] headers, String aesKey, InputStream is) {
        try {
            String respToken = "";
            if (headers != null) {
                for (Header header : headers) {
                    if (HttpConstant.TOKEN.equals(header.getName())) {
                        respToken = header.getValue();
                        break;
                    }
                }
            }
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            while (true) {
                int len = is.read(buff);
                if (len == -1) {
                    break;
                }
                baos2.write(buff, 0, len);
            }
            String jsonString = new JSONObject(new String(baos2.toByteArray(), "utf-8")).getString("data");
            if (!("".equals(aesKey) || jsonString == null || jsonString.contains("result"))) {

                jsonString = AESUtil.decrypt(jsonString, aesKey);

            }
            JSONObject jsonObject = new JSONObject(jsonString);
            if (!(jsonObject.has(HttpConstant.TOKEN) || "".equals(respToken))) {
                jsonObject.put(HttpConstant.TOKEN, respToken);
            }
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void cacheResponse(String url, String jsonString, String currentPageNum) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            if (HttpConstant.SUCCESS.equals(jsonObject.getString("result")) && cacheUrl.containsKey(url)) {
                if (HttpConstant.GET_INDEXBANNER.equals(url)) {
                    CacheUtil.setIndexBanner(jsonString);
                }
                if (HttpConstant.GET_INDEXDYNAMIC.equals(url) && "1".equals(currentPageNum) && jsonObject.has("dyList")) {
                    JSONArray bannerList = jsonObject.getJSONArray("dyList");
                    if (!(bannerList == null || bannerList.length() == 0)) {
                        CacheUtil.setIndexDynamic(jsonString);
                    }
                }
                if (HttpConstant.GET_DISCOVER.equals(url)) {
                    CacheUtil.setDiscover(jsonString);
                }
                if (HttpConstant.GET_MEMBERDETAIL.equals(url)) {
                    CacheUtil.setMemberDetail(jsonString);
                }
                if (HttpConstant.GET_MEMBERSCORE.equals(url)) {
                    CacheUtil.setMemberScore(jsonString);
                }
                if (HttpConstant.GET_ICON.equals(url)) {
                    CacheUtil.setIcon(jsonString);
                }
                if (HttpConstant.GET_NAVIGATION.equals(url)) {
                    CacheUtil.setNavigation(jsonString);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void httpPost(String url, Map<String, String> params, List<String> fileList, HttpHandler obj) {
        new AnonymousClass1(url, params, fileList, obj).execute(new Void[0]);
    }

    public static void httpPost(String url, Map<String, String> params, HttpHandler obj) {
        new AnonymousClass2(url, params, obj).execute(new Void[0]);
    }

    public static void httpPost(String url, Object params, HttpHandler obj) {
        new AnonymousClass3(params, url, obj).execute(new Void[0]);
    }

    public static void httpPost(String url, Map<String, String> params, HttpHandler obj, JSONObject acrossParam) {
        new AnonymousClass4(params, url, acrossParam, obj).execute(new Void[0]);
    }

    public static void httpPost(String url, Map<String, String> params, JSONObject obj, int code) {
        new AnonymousClass5(params, url, obj, code).execute(new Void[0]);
    }

    static class AnonymousClass1 extends AsyncTask<Void, Void, JSONObject> {
        private final /* synthetic */ List<String> val$fileList;
        private final /* synthetic */ HttpHandler val$obj;
        private final /* synthetic */ Map<String, String> val$params;
        private final /* synthetic */ String val$url;

        AnonymousClass1(String str, Map map, List list, HttpHandler httpHandler) {
            this.val$url = str;
            this.val$params = map;
            this.val$fileList = list;
            this.val$obj = httpHandler;
        }

        protected JSONObject doInBackground(Void... arg0) {
            return HttpUtil.sendPost(this.val$url, this.val$params, this.val$fileList);
        }

        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            try {
                this.val$obj.callBack(this.val$url, result, this.val$obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class AnonymousClass2 extends AsyncTask<Void, Void, JSONObject> {
        private final /* synthetic */ HttpHandler val$obj;
        private final /* synthetic */ Map val$params;
        private final /* synthetic */ String val$url;

        AnonymousClass2(String str, Map<String, String> map, HttpHandler httpHandler) {
            this.val$url = str;
            this.val$params = map;
            this.val$obj = httpHandler;
        }

        protected JSONObject doInBackground(Void... arg0) {
            return HttpUtil.sendPost(this.val$url, this.val$params);
        }

        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            try {
                this.val$obj.callBack(this.val$url, result, this.val$obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class AnonymousClass3 extends AsyncTask<Void, Void, JSONObject> {
        private final /* synthetic */ HttpHandler val$obj;
        private final /* synthetic */ Object val$params;
        private final /* synthetic */ String val$url;

        AnonymousClass3(Object obj, String str, HttpHandler httpHandler) {
            this.val$params = obj;
            this.val$url = str;
            this.val$obj = httpHandler;
        }

        protected JSONObject doInBackground(Void... arg0) {
            Map<String, String> map = null;
            try {
                if (this.val$params != null) {
                    map = MyUtil.object2Map(this.val$params);
                }
                return HttpUtil.sendPost(this.val$url, map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            try {
                this.val$obj.callBack(this.val$url, result, this.val$obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class AnonymousClass4 extends AsyncTask<Void, Void, JSONObject> {
        private final /* synthetic */ Object val$acrossParam;
        private final /* synthetic */ HttpHandler val$obj;
        private final /* synthetic */ Map<String, String> val$params;
        private final /* synthetic */ String val$url;

        AnonymousClass4(Map<String, String> obj, String str, Object obj2, HttpHandler httpHandler) {
            this.val$params = obj;
            this.val$url = str;
            this.val$acrossParam = obj2;
            this.val$obj = httpHandler;
        }

        protected JSONObject doInBackground(Void... arg0) {
            Map<String, String> map = null;
            try {
                if (this.val$params != null) {
                    map = MyUtil.object2Map(this.val$params);
                }
                return HttpUtil.sendPost(this.val$url, map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            try {
                result.put("acrossParam", this.val$acrossParam);
                this.val$obj.callBack(this.val$url, result, this.val$obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class AnonymousClass5 extends AsyncTask<Void, Void, JSONObject> {
        private final /* synthetic */ int val$code;
        private final /* synthetic */ JSONObject val$obj;
        private final /* synthetic */ Map<String, String> val$params;
        private final /* synthetic */ String val$url;

        AnonymousClass5(Map<String, String> obj, String str, JSONObject obj2, int i) {
            this.val$params = obj;
            this.val$url = str;
            this.val$obj = obj2;
            this.val$code = i;
        }

        protected JSONObject doInBackground(Void... arg0) {
            Map<String, String> map = null;
            try {
                if (this.val$params != null) {
                    map = MyUtil.object2Map(this.val$params);
                }
                return HttpUtil.sendPost(this.val$url, map);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            try {
                String methodName = new StringBuilder(String.valueOf(this.val$url.substring(this.val$url.lastIndexOf("/") + 1, this.val$url.lastIndexOf(".")))).append("Back").toString();
                MyUtil.invokeMethod(this.val$obj, methodName, new Object[]{result, Integer.valueOf(this.val$code)});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
