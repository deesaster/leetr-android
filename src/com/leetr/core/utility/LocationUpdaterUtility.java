package com.leetr.core.utility;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import com.leetr.core.Settings;
import com.leetr.core.interfaces.ILeetrUtility;
import com.leetr.core.listener.OnLocationUpdateListener;
import com.leetr.location.base.ILastLocationFinder;
import com.leetr.location.base.LocationUpdateRequester;
import com.leetr.location.LocationFactory;

import java.util.ArrayList;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-07-08
 * Time: 10:44 PM
 */
public class LocationUpdaterUtility implements ILeetrUtility {
    public static final String KEY_LOCATION_CHANGED = "leetr.base.core.utility.LOCATION_CHANGED";

    private LocationManager mLocationManager;
    private Criteria mCriteria;
    private ILastLocationFinder mLastLocationFinder;
    private LocationUpdateRequester mLocationUpdateRequester;
    private Location mLastLocation;
    private Context mContext;
    private PendingIntent mLocationListenerPendingIntent;
    private PendingIntent mLocationListenerPassivePendingIntent;

    private ArrayList<OnLocationUpdateListener> mListeners = new ArrayList<OnLocationUpdateListener>();

    private LocationListener mOneShotLastLocationUpdateListener = new LocationListener() {
        public void onLocationChanged(Location l) {
            setLastLocation(l);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }
    };

    public LocationUpdaterUtility(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//
        mCriteria = new Criteria();
        mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        mCriteria.setPowerRequirement(Criteria.POWER_LOW);

        mLastLocationFinder = LocationFactory.getLastLocationFinder(context);
        mLastLocationFinder.setChangedLocationListener(mOneShotLastLocationUpdateListener);

        mLocationUpdateRequester = LocationFactory.getLocationUpdateRequester(mLocationManager);

        // Setup the location update Pending Intents
        Intent activeIntent = new Intent(KEY_LOCATION_CHANGED);
        mLocationListenerPendingIntent = PendingIntent.getBroadcast(context, 0, activeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //TODO add passive location listener
//        Intent passiveIntent = new Intent(this, PassiveLocationChangedReceiver.class);
//        mLocationListenerPassivePendingIntent = PendingIntent.getBroadcast(this, 0, passiveIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        findLastLocation();
    }

    public Location getLastLocation() {
        return mLastLocation;
    }

    private void setLastLocation(Location l) {
        mLastLocation = l;

        for (OnLocationUpdateListener listener : mListeners) {
            listener.onLocationChanged(l);
        }
    }

    public void addListener(OnLocationUpdateListener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);

            if (mListeners.size() == 1) {
                enableLocationUpdates();
            }
        }
    }

    public void removeListener(OnLocationUpdateListener listener) {
        if (mListeners.contains(listener)) {
            mListeners.remove(listener);
        }

        if (mListeners.size() == 0) {
            disableLocationUpdates();
        }
    }

    protected void findLastLocation() {
        AsyncTask<Void, Void, Void> findLastLocationTask = new AsyncTask<Void, Void, Void>() {
            private Location mLocation;

            @Override
            protected void onPostExecute(Void aVoid) {
                setLastLocation(mLocation);
            }

            @Override
            protected Void doInBackground(Void... params) {

                mLocation = mLastLocationFinder.getLastBestLocation(Settings.getInteger("settings_location_max_update_distance"),
                        System.currentTimeMillis() - Settings.getInteger("settings_location_max_update_time"));

                return null;
            }
        };
        findLastLocationTask.execute();
    }

    protected void enableLocationUpdates() {
        //using pending intents
        mContext.registerReceiver(mLocationChangedReceiver, new IntentFilter(KEY_LOCATION_CHANGED));
        mLocationUpdateRequester.requestLocationUpdates(Settings.getInteger("settings_location_max_update_time"),
                Settings.getInteger("settings_location_max_update_distance"), mCriteria, mLocationListenerPendingIntent);

//        //using location listener
//        mLocationUpdateRequester.requestLocationUpdates(Settings.getInteger("settings_location_max_update_time"),
//                Settings.getInteger("settings_location_max_update_distance"), mCriteria, mLocationListener);
    }

    protected void disableLocationUpdates() {
        //using pending intents
        mContext.unregisterReceiver(mLocationChangedReceiver);
        mLocationManager.removeUpdates(mLocationListenerPendingIntent);

//        //using location listener
//        mLocationManager.removeUpdates(mLocationListener);
    }

    @Override
    public void start() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void shutdown() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private BroadcastReceiver mLocationChangedReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)) {
                Location location = (Location) intent.getExtras().get(LocationManager.KEY_LOCATION_CHANGED);
                setLastLocation(location);
            }
        }
    };

//    private LocationListener mLocationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//            setLastLocation(location);
//        }
//
//        @Override
//        public void onStatusChanged(String s, int i, Bundle bundle) {
//            //To change body of implemented methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void onProviderEnabled(String s) {
//            //To change body of implemented methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void onProviderDisabled(String s) {
//            //To change body of implemented methods use File | Settings | File Templates.
//        }
//    };
}
