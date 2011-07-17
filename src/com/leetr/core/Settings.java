package com.leetr.core;

import android.content.Context;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-07-08
 * Time: 10:17 PM
 */
public class Settings {
    private static Settings instance;

    private Context mContext;

    public static void initialize(Context context) {
        instance = new Settings(context);
    }

    public static int getInteger(int resId) {
        return instance.getIntegerByResId(resId);
    }

    public static int getInteger(String name) {
        return instance.getIntegerByName(name);
    }

    private Settings(Context context) {
        mContext = context;
    }

    public int getIntegerByResId(int resId) {
        return mContext.getResources().getInteger(resId);
    }

    public int getIntegerByName(String name) {
        int resId = mContext.getResources().getIdentifier(name, "integer", mContext.getPackageName());
        return getIntegerByResId(resId);
    }
}
