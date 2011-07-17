/*
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.leetr.location;

import android.content.Context;
import android.location.LocationManager;
import com.leetr.location.base.ILastLocationFinder;
import com.leetr.location.base.LocationUpdateRequester;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-07-08
 * Time: 10:40 PM
 */
public class LocationFactory {
    public static boolean SUPPORTS_GINGERBREAD = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD;
    public static boolean SUPPORTS_HONEYCOMB = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB;
    public static boolean SUPPORTS_FROYO = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO;
    public static boolean SUPPORTS_ECLAIR = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ECLAIR;

    /**
     * Create a new LastLocationFinder instance
     *
     * @param context Context
     * @return LastLocationFinder
     */
    public static ILastLocationFinder getLastLocationFinder(Context context) {
        return SUPPORTS_GINGERBREAD ? new GingerbreadLastLocationFinder(context) : new LegacyLastLocationFinder(context);
    }

    /**
     * Create a new LocationUpdateRequester
     *
     * @param locationManager Location Manager
     * @return LocationUpdateRequester
     */
    public static LocationUpdateRequester getLocationUpdateRequester(LocationManager locationManager) {
        return SUPPORTS_GINGERBREAD ? new GingerbreadLocationUpdateRequester(locationManager) : new FroyoLocationUpdateRequester(locationManager);
    }
}
