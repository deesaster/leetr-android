package com.leetr.core.listener;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-03
 * Time: 10:38 PM
 */
public interface OnHttpStringListener {
    public void onStringLoaded(String s);

    public void onStringLoadFailed();
}
