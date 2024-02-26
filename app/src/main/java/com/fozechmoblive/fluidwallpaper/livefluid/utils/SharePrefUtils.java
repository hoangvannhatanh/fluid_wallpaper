package com.fozechmoblive.fluidwallpaper.livefluid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharePrefUtils {

    private static SharedPreferences mSharePref;
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        if (mSharePref == null)
            mSharePref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getString(String key, String defaultValues) {
        if (mSharePref == null) init(mContext);
        return mSharePref.getString(key, defaultValues);
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
    public static void forceRated(Context context) {
        SharedPreferences pre = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean("rated", true);
        editor.commit();
    }
}
