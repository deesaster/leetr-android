package com.leetr.core.listener;

import android.graphics.Bitmap;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-15
 * Time: 11:12 AM
 */
public interface OnCacheBitmapLoadListener {
    public void onCacheBitmapLoad(Bitmap b, String filename);
    public void onCacheBitmapLoadFail(String errorMessage, String filename);
}
