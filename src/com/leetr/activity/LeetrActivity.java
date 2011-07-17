package com.leetr.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.leetr.R;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-03
 * Time: 8:34 PM
 */
public class LeetrActivity extends FragmentActivity {
    private GoogleAnalyticsTracker mGATracker;

    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        initAnalytics();
    }

    @Override
    protected void onDestroy() {
        mGATracker.dispatch();
        mGATracker.stop();

        super.onDestroy();
    }

    protected void initAnalytics() {
        mGATracker = GoogleAnalyticsTracker.getInstance();
        mGATracker.start(getString(R.string.settings_google_analytics_code), this);
        mGATracker.trackPageView(getAnalyticsPageName());
    }

    protected String getAnalyticsPageName() {
        return getLocalClassName();
    }

    protected void analyticsTrackEvent(String category, String action, String label, int value) {
        mGATracker.trackEvent(category, action, label, value);
    }
}
