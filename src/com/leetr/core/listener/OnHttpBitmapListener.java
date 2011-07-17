package com.leetr.core.listener;

import android.graphics.Bitmap;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-03
 * Time: 10:36 PM
 */
public interface OnHttpBitmapListener {
    public void onHttpBitmapLoad(Bitmap b, String url);

    public void onHttpBitmapLoadFailed(String errorMessage);
}
