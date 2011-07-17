package com.leetr.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-03
 * Time: 8:34 PM
 */
public class LeetrActivity extends FragmentActivity {
//    private Fragment mHeaderBarFragment;
//    private Fragment mTopBarFragment;
//    private Fragment mContentFragment;
//    private Fragment mBottomBarFragment;

    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        initAnalytics();

//        setContentView(R.layout.leetr_app_layout);
//
//        initHeaderBarFragment();
//        initTopBarFragment();
//        initContentFragment();
//        initBottomBarFragment();
//
//        setFragments();
    }

    protected void initAnalytics() {

    }

    protected String getAnalyticsName() {
        return getLocalClassName();
    }

//    private void setFragments() {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        if (mHeaderBarFragment != null) {
//            FrameLayout header = (FrameLayout) findViewById(R.id.headerBar);
//            header.setVisibility(View.VISIBLE);
//
//            transaction.replace(R.id.headerBar, mHeaderBarFragment);
//        }
//
//        if (mTopBarFragment != null) {
//            FrameLayout topBar = (FrameLayout) findViewById(R.id.topBar);
//            topBar.setVisibility(View.VISIBLE);
//
//            transaction.replace(R.id.topBar, mTopBarFragment);
//        }
//
//        if (mContentFragment != null) {
//            FrameLayout content = (FrameLayout) findViewById(R.id.content);
//            content.setVisibility(View.VISIBLE);
//
//            transaction.replace(R.id.content, mContentFragment);
//        }
//
//        if (mBottomBarFragment != null) {
//            FrameLayout bottomBar = (FrameLayout) findViewById(R.id.bottomBar);
//            bottomBar.setVisibility(View.VISIBLE);
//
//            transaction.replace(R.id.bottomBar, mBottomBarFragment);
//        }
//
//        transaction.commit();
//    }
//
//    protected void initHeaderBarFragment() {
//        setHeaderBarFragment(new LeetrActionBarFragment());
//    }
//
//    protected void initTopBarFragment() {
//
//    }
//
//    protected void initContentFragment() {
//
//    }
//
//    protected void initBottomBarFragment() {
//
//    }
//
//    protected void setHeaderBarFragment(Fragment fragment) {
//        mHeaderBarFragment = fragment;
//    }
//
//    protected void setTopBarFragment(Fragment fragment) {
//        mTopBarFragment = fragment;
//    }
//
//    protected void setContentFragment(Fragment fragment) {
//        mContentFragment = fragment;
//    }
//
//    protected void setBottomBarFragment(Fragment fragment) {
//        mBottomBarFragment = fragment;
//    }
}
