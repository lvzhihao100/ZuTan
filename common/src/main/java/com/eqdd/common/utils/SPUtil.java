package com.eqdd.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.eqdd.common.base.App;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

public class SPUtil {


    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "eqd_sp";


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param
     * @param key
     * @param object
     */
    public static void setParam(String key, Serializable object) {
        if (object == null) {
            return;
        }
        SharedPreferences.Editor editor = init();


        //创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //创建字节对象输出流
        ObjectOutputStream out = null;
        try {
            //然后通过将字对象进行64转码，写入key值为key的sp中
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            editor.putString(key, objectVal);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        editor.commit();
    }

    public static Object getParam(String key) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, "");
            if (TextUtils.isEmpty(objectVal)) {
                return null;
            }
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            //一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                return ois.readObject();
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    private static SharedPreferences.Editor init() {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.edit();
    }
    public static boolean getParam(String key, boolean defValue) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static boolean setParam(String key, boolean value) {
        SharedPreferences.Editor editor = init();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static int getParam(String key, int defValue) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }
    public static boolean setParam(String key, int value) {
        SharedPreferences.Editor editor = init();
        editor.putInt(key, value);
        return editor.commit();
    }
    public static String getParam(String key, String defValue) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }
    public static boolean setParam(String key, String value) {
        SharedPreferences.Editor editor = init();
        editor.putString(key, value);
        return editor.commit();
    }
    public static float getParam(String key, float defValue) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getFloat(key, defValue);
    }
    public static boolean setParam(String key, float value) {
        SharedPreferences.Editor editor = init();
        editor.putFloat(key, value);
        return editor.commit();
    }
    public static long getParam(String key, long defValue) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }
    public static boolean setParam(String key, long value) {
        SharedPreferences.Editor editor = init();
        editor.putLong(key, value);
        return editor.commit();
    }
}

