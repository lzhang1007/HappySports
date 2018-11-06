package com.android.orient.practice.kldf.kldf.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyUtil {

    @SuppressLint({"DefaultLocale"})
    public static void json2object(JSONObject jsonObject, Object obj) throws Exception {
        String ftype = "";
        for (Field f : obj.getClass().getDeclaredFields()) {
            String name = f.getName();
            if (jsonObject.has(name) && !jsonObject.isNull(name)) {
                ftype = f.getType().toString().toLowerCase();
                if (ftype.contains("string")) {
                    invokeMethod(obj, getSeterr(f.getName()), new Object[]{jsonObject.getString(name)});
                } else if (ftype.contains("long")) {
                    invokeMethod(obj, getSeterr(f.getName()), new Object[]{jsonObject.getLong(name)});
                } else if (ftype.contains("int") || ftype.contains("integer")) {
                    invokeMethod(obj, getSeterr(f.getName()), new Object[]{jsonObject.getInt(name)});
                } else if (ftype.contains("double")) {
                    invokeMethod(obj, getSeterr(f.getName()), new Object[]{jsonObject.getDouble(name)});
                } else if (ftype.contains("date")) {
                    @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObject.getString(name));
                    invokeMethod(obj, getSeterr(f.getName()), new Object[]{date});
                }
            }
        }
        if (obj.getClass().getGenericSuperclass() != null) {
            for (Field f2 : obj.getClass().getSuperclass().getDeclaredFields()) {
                String name = f2.getName();
                if (jsonObject.has(f2.getName()) && !jsonObject.isNull(name)) {
                    ftype = f2.getType().toString().toLowerCase();
                    if (ftype.contains("string")) {
                        invokeMethod(obj, getSeterr(f2.getName()), new Object[]{jsonObject.getString(name)});
                    } else if (ftype.contains("long")) {
                        invokeMethod(obj, getSeterr(f2.getName()), new Object[]{jsonObject.getLong(name)});
                    } else if (ftype.contains("int") || ftype.contains("integer")) {
                        invokeMethod(obj, getSeterr(f2.getName()), new Object[]{jsonObject.getInt(name)});
                    } else if (ftype.contains("double")) {
                        invokeMethod(obj, getSeterr(f2.getName()), new Object[]{jsonObject.getDouble(name)});
                    } else if (ftype.contains("date")) {
                        @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObject.getString(name));
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

    static void invokeMethod(Object obj, String methodName, Object[] args) {
        try {
            Reflection.getInstance().invokeMethod(obj, methodName, args);
        } catch (Exception e) {
            e.printStackTrace();
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
    static Map<String, String> object2Map(Object src) throws Exception {
        Field[] fields = src.getClass().getDeclaredFields();
        Map<String, String> map = new HashMap<>(fields.length);
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!("serialVersionUID".equals(fieldName) || "List".equals(field.getType().getSimpleName()))) {
                Object value = invokeMethod(src, "get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1));
                if ((value instanceof String)) {
                    map.put(fieldName, (String) value);
                }
            }
        }
        return map;
    }
}
