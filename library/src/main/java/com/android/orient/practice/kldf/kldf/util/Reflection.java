package com.android.orient.practice.kldf.kldf.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

public class Reflection {
    private static Reflection ref = new Reflection();

    public static Reflection getInstance() {
        return ref;
    }

    public Object getProperty(Object owner, String fieldName) throws Exception {
        return owner.getClass().getField(fieldName).get(owner);
    }

    public Object getStaticProperty(String className, String fieldName) throws Exception {
        Class ownerClass = Class.forName(className);
        return ownerClass.getField(fieldName).get(ownerClass);
    }

    public Object invokeMethod(Object owner, String methodName, Object[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Class ownerClass = owner.getClass();
        Class[] argsClass = new Class[args.length];
        int j = args.length;
        for (int i = 0; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        return ownerClass.getMethod(methodName, argsClass).invoke(owner, args);
    }

    public Object invokeMethodValuNull(Object owner, String methodName, Class arg) throws Exception {
        return owner.getClass().getMethod(methodName, new Class[]{arg}).invoke(owner, new Object[1]);
    }

    public Object invokeStaticMethod(String className, String methodName, Object[] args) throws Exception {
        Class ownerClass = Class.forName(className);
        Class[] argsClass = new Class[args.length];
        int j = args.length;
        for (int i = 0; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        return ownerClass.getMethod(methodName, argsClass).invoke(null, args);
    }

    public Object newInstance(String className, Object[] args) throws Exception {
        Class newoneClass = Class.forName(className);
        Class[] argsClass = new Class[args.length];
        int j = args.length;
        for (int i = 0; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        return newoneClass.getConstructor(argsClass).newInstance(args);
    }

    public boolean isInstance(Object obj, Class cls) {
        return cls.isInstance(obj);
    }

    public Object getByArray(Object array, int index) {
        return Array.get(array, index);
    }
}
