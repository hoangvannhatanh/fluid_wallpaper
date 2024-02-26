package com.fozechmoblive.fluidwallpaper.livefluid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

public class SharePrefUtils {

    private static SharedPreferences mSharePref;
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        if (mSharePref == null)
            mSharePref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void clearAll() {
        if (mSharePref == null) init(mContext);

        mSharePref.edit().clear().apply();
        mSharePref.edit().apply();
    }

    public static void removeValueWithKey(String key) {
        mSharePref.edit().remove(key).apply();
    }

    public static void putString(String key, String value) {
        if (mSharePref == null) init(mContext);

        SharedPreferences.Editor editor = mSharePref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key, String defaultValues) {
        if (mSharePref == null) init(mContext);
        return mSharePref.getString(key, defaultValues);
    }

    public static void putInt(String key, int value) {
        if (mSharePref == null) init(mContext);
        SharedPreferences.Editor editor = mSharePref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(String key, int defaultValues) {
        if (mSharePref == null) init(mContext);
        return mSharePref.getInt(key, defaultValues);
    }

    public static void putFloat(String key, float value) {
        if (mSharePref == null) init(mContext);
        SharedPreferences.Editor editor = mSharePref.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloat(String key, float defaultValues) {
        if (mSharePref == null) init(mContext);
        return mSharePref.getFloat(key, defaultValues);
    }

    public static void putLong(String key, long value) {
        if (mSharePref == null) init(mContext);
        SharedPreferences.Editor editor = mSharePref.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(String key, long defaultValues) {
        if (mSharePref == null) init(mContext);
        return mSharePref.getLong(key, defaultValues);
    }

    public static void putBoolean(String key, boolean value) {
        if (mSharePref == null) init(mContext);
        SharedPreferences.Editor editor = mSharePref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key, boolean defaultValues) {
        if (mSharePref == null) init(mContext);
        return mSharePref.getBoolean(key, defaultValues);
    }

    public static void putObject(String key, Object value) {
        if (mSharePref == null) init(mContext);
        Gson gson = new Gson();
        String json = gson.toJson(value);
        SharedPreferences.Editor editor = mSharePref.edit();
        editor.putString(key, json);
        editor.apply();
    }

    public static Object getObject(String key, Class<?> classObj) {
        if (mSharePref == null) init(mContext);
        Gson gson = new Gson();
        String json = mSharePref.getString(key, "");
        return gson.fromJson(json, classObj);
    }

    public static int getCountOpenApp(Context context) {
        SharedPreferences pre = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return pre.getInt("counts", 1);
    }

    public static void increaseCountOpenApp(Context context) {
        SharedPreferences pre = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putInt("counts", pre.getInt("counts", 1) + 1);
        editor.commit();
    }
    public static void forceRated(Context context) {
        SharedPreferences pre = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean("rated", true);
        editor.commit();
    }
}
