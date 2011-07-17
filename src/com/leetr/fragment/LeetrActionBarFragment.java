package com.leetr.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import leetr.base.R;

import java.util.ArrayList;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-04-28
 * Time: 10:49 PM
 */
public class LeetrActionBarFragment extends LeetrFragment {

    private boolean mActivityCreated;
    private ArrayList<View> mDelayedButtons = new ArrayList<View>();
    private View mLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.leetr_action_bar, container, false);
        return mLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivityCreated = true;

        if (mDelayedButtons.size() > 0) {
            ViewGroup buttonContainer = (ViewGroup) mLayout.findViewById(R.id.contextActionsContainer);

            for (View btn : mDelayedButtons) {
                buttonContainer.addView(btn);
            }

            mDelayedButtons.clear();
        }
    }


    public void addButton(Context context, int resId, View.OnClickListener listener) {
        ImageButton btn = new ImageButton(context);
        btn.setImageResource(resId);
        btn.setOnClickListener(listener);

        if (mActivityCreated) {
            ViewGroup buttonContainer = (ViewGroup) mLayout.findViewById(R.id.contextActionsContainer);
            buttonContainer.addView(btn);
        } else {
            mDelayedButtons.add(btn);
        }
    }
}
