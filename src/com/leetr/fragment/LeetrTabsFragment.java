package com.leetr.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.leetr.R;
import com.leetr.ui.TabLayout;

import java.util.ArrayList;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-04-25
 * Time: 10:40 PM
 */
public class LeetrTabsFragment extends LeetrFragment {
    private final ArrayList<View> mTabs = new ArrayList<View>();
    private TabLayout mTabLayout;
    private int mSelectedTab = -1;
    private TabLayout.OnTabSelectedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.leetr_navigation_tabs, container, false);
        mTabLayout = (TabLayout) v.findViewById(R.id.tabHolder);
        mTabLayout.setTabSelector(R.drawable.leetr_grey_tab_selector);

        for (View tab : mTabs) {
            mTabLayout.addTab(tab);
        }

        if (mSelectedTab > -1) {
            mTabLayout.setSelectedTab(mSelectedTab);
        }

        if (mTabLayout != null) {
            mTabLayout.setOnTabSelectedListener(mListener);
        }

        return v;
    }

    public void addTab(View tab) {

        mTabs.add(tab);

        if (mTabLayout != null) {
            mTabLayout.addTab(tab);
        }
    }

    public void setSelectedTab(int tabId) {
        if (mTabLayout != null) {
            mTabLayout.setSelectedTab(tabId);
        } else {
            mSelectedTab = tabId;
        }
    }

    public void setOnTabSelectedListener(TabLayout.OnTabSelectedListener listener) {
        if (mTabLayout != null) {
            mTabLayout.setOnTabSelectedListener(listener);
        } else {
            mListener = listener;
        }
    }
}
