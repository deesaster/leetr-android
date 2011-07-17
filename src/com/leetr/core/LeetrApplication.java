package com.leetr.core;

import android.app.Application;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-06
 * Time: 9:06 PM
 */
public class LeetrApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Leetr.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        // this code never gets executed as per documentation
        // http://developer.android.com/reference/android/app/Application.html#onTerminate()
    }
}
