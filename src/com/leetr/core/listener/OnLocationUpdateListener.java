package com.leetr.core.listener;

import android.location.Location;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-07-09
 * Time: 12:14 AM
 */
public interface OnLocationUpdateListener {
    public void onLocationChanged(Location l);
}
