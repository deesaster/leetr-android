package com.leetr.core;

import android.content.Context;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-09-30
 * Time: 9:03 PM
 */
public class Device {
    public static String id(Context context) {
        return android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
    }
}
