package com.leetr.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import com.leetr.R;
import com.leetr.fragment.LeetrActionBarFragment;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-04-28
 * Time: 10:54 PM
 */
public class LeetrDashboardActivity extends LeetrActivity {
    private LeetrActionBarFragment mLeetrActionBar;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.leetr_app_layout);

        FrameLayout header = (FrameLayout) findViewById(R.id.headerBar);
        header.setVisibility(View.VISIBLE);

        mLeetrActionBar = new LeetrActionBarFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.headerBar, mLeetrActionBar).commit();
    }
}
