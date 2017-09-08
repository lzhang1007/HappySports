package com.android.orient.sports.happysports.kldf.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyUtil {

    class AnonymousClass1 implements OnEditorActionListener {
        private final /* synthetic */ Activity context;

        AnonymousClass1(Activity activity) {
            this.context = activity;
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 6 && (event == null || event.getKeyCode() != 66)) {
                return false;
            }
            ((InputMethodManager) this.context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.context.getCurrentFocus().getApplicationWindowToken(), 2);
            return true;
        }
    }

    @SuppressLint({"DefaultLocale"})
    public static void json2object(JSONObject jsonObject, Object obj) throws Exception {
        String ftype = "";
        for (Field f : obj.getClass().getDeclaredFields()) {
            String name = f.getName();
            if (jsonObject.has(name) && !jsonObject.isNull(name)) {
                ftype = f.getType().toString().toLowerCase();
                if (ftype.indexOf("string") != -1) {
                    invokeMethod(obj, getSeterr(f.getName()), new Object[]{jsonObject.getString(name)});
                } else if (ftype.indexOf("long") != -1) {
                    invokeMethod(obj, getSeterr(f.getName()), new Object[]{Long.valueOf(jsonObject.getLong(name))});
                } else if (ftype.indexOf("int") != -1 || ftype.indexOf("integer") != -1) {
                    invokeMethod(obj, getSeterr(f.getName()), new Object[]{Integer.valueOf(jsonObject.getInt(name))});
                } else if (ftype.indexOf("double") != -1) {
                    invokeMethod(obj, getSeterr(f.getName()), new Object[]{Double.valueOf(jsonObject.getDouble(name))});
                } else if (ftype.indexOf("date") != -1) {
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObject.getString(name));
                    invokeMethod(obj, getSeterr(f.getName()), new Object[]{date});
                }
            }
        }
        if (obj.getClass().getGenericSuperclass() != null) {
            for (Field f2 : obj.getClass().getSuperclass().getDeclaredFields()) {
                String name = f2.getName();
                if (jsonObject.has(f2.getName()) && !jsonObject.isNull(name)) {
                    ftype = f2.getType().toString().toLowerCase();
                    if (ftype.indexOf("string") != -1) {
                        invokeMethod(obj, getSeterr(f2.getName()), new Object[]{jsonObject.getString(name)});
                    } else if (ftype.indexOf("long") != -1) {
                        invokeMethod(obj, getSeterr(f2.getName()), new Object[]{Long.valueOf(jsonObject.getLong(name))});
                    } else if (ftype.indexOf("int") != -1 || ftype.indexOf("integer") != -1) {
                        invokeMethod(obj, getSeterr(f2.getName()), new Object[]{Integer.valueOf(jsonObject.getInt(name))});
                    } else if (ftype.indexOf("double") != -1) {
                        invokeMethod(obj, getSeterr(f2.getName()), new Object[]{Double.valueOf(jsonObject.getDouble(name))});
                    } else if (ftype.indexOf("date") != -1) {
                        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObject.getString(name));
                        invokeMethod(obj, getSeterr(f2.getName()), new Object[]{date});
                    }
                }
            }
        }
    }

    @SuppressLint({"DefaultLocale"})
    private static String getSeterr(String f) {
        return "set" + f.substring(0, 1).toUpperCase() + f.substring(1, f.length());
    }

    public static Object invokeMethod(Object obj, String methodName, Object[] args) {
        try {
            return Reflection.getInstance().invokeMethod(obj, methodName, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object invokeMethod(Object obj, String methodName) {
        try {
            return obj.getClass().getMethod(methodName).invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint({"DefaultLocale"})
    public static Map<String, String> object2Map(Object src) throws Exception {
        Field[] fields = src.getClass().getDeclaredFields();
        Map<String, String> map = new HashMap();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!("serialVersionUID".equals(fieldName) || "List".equals(field.getType().getSimpleName()))) {
                Object value = invokeMethod(src, "get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1));
                if (value != null && (value instanceof String)) {
                    map.put(fieldName, (String) value);
                }
            }
        }
        return map;
    }

    public static boolean isServiceWork(Context context, String serviceName) {
        boolean isWork = false;
        List<RunningServiceInfo> myList = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            if (((RunningServiceInfo) myList.get(i)).service.getClassName().toString().equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    public static boolean isActivityWork(Context context, String activityName) {
        List<RunningTaskInfo> list = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(100);
        if (activityName == null) {
            return false;
        }
        for (RunningTaskInfo info : list) {
            if (!activityName.equals(info.topActivity.getClassName())) {
                if (activityName.equals(info.baseActivity.getClassName())) {
                }
            }
            return true;
        }
        return false;
    }

    public static void keyEditor(Activity context, EditText editText) {
//        editText.setOnEditorActionListener(new AnonymousClass1(context));
    }

    public static void baiduPageMtj(Context context, boolean isforeground, int title) {
//        if (isforeground) {
//            StatService.onPageStart(context, context.getResources().getString(title));
//        } else {
//            StatService.onPageEnd(context, context.getResources().getString(title));
//        }
    }
}
