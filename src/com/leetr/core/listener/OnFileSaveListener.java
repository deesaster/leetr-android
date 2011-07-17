package com.leetr.core.listener;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-14
 * Time: 5:47 PM
 */
public interface OnFileSaveListener {
    public void onFileSaveSuccess(String filename, String dir);
    public void onFileSaveFail(String filename, String dir, String errorMessage);
}
