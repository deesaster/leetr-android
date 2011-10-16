package com.leetr.core;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.leetr.R;
import com.leetr.fragment.LeetrSplashFragment;

import java.util.Timer;
import java.util.TimerTask;

public class LoaderActivity extends FragmentActivity {

    private Resources mResources;

    public static boolean launchActivityForClassName(Activity activity, String className) {

        try {
            Class<?> classObj = Class.forName(className);
            Intent intent = new Intent(activity, classObj);
            activity.startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResources = getResources();

        if (mResources.getBoolean(R.bool.settings_splash_show)) {
            showSplash();
        } else {
            launchApp();
        }
    }

    private void showSplash() {
        setContentView(R.layout.leetr_app_layout);

        LeetrSplashFragment leetrSplashF = new LeetrSplashFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, leetrSplashF).commit();

        Timer splashTimer = new Timer();
        splashTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        launchApp();
                    }
                });

            }
        }, (long) mResources.getInteger(R.integer.settings_splash_duration_mil));
    }

    private void launchApp() {

        launchActivityForClassName(this, mResources.getString(R.string.settings_dashboard_activity_class));

        // then dump the loader, no need to waste resources
        finish();
    }
}

