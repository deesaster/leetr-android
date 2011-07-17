package com.leetr.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.leetr.R;
import com.leetr.ui.base.ITab;

import java.util.ArrayList;

public class TabLayout extends LinearLayout {

    private Context mContext;
    private OnTabSelectedListener mTabListener;
    private OnClickListener mTabOnClickListener;
    private ArrayList<View> mTabs;
    private int mTabSelectorResId;
    private LayoutParams mTabLayoutParams;

    public static final View makeImageTab(Context context, int tabId, Drawable unpressed, Drawable pressed) {
        TabImageButton tab = new TabImageButton(context, tabId, unpressed, pressed);
        return tab;
    }

    public static final View makeImageTab(Context context, int tabId, int unpressedDrawableResourceId, int pressedDrawableResourceId) {
        return makeImageTab(context, tabId, context.getResources().getDrawable(unpressedDrawableResourceId), context.getResources().getDrawable(pressedDrawableResourceId));
    }

    public static final View makeTextTab(Context context, int tabId, String text) {
        TabTextButton tab = new TabTextButton(context, tabId, text);
        return tab;
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mTabs = new ArrayList<View>();

        initListeners();
        initUiComponents(attrs);
    }

    private void initListeners() {
        mTabOnClickListener = new OnClickListener() {

            public void onClick(View view) {
                handleTabPress(view, true);
            }
        };
    }

    private void initUiComponents(AttributeSet attrs) {
        mTabLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
        mTabLayoutParams.weight = 1;

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TabLayout);
        mTabSelectorResId = a.getResourceId(R.styleable.TabLayout_tabSelector, 0);
        a.recycle();
    }

    public void setTabSelector(int selectorResId) {
        mTabSelectorResId = selectorResId;
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        mTabListener = listener;
    }


    @Override
    public void onFinishInflate() {
        int numChildren = getChildCount();

        int firstTabId = -1; //for setting first selected tab

        for (int i = 0; i < numChildren; i++) {
            View child = getChildAt(i);

            if (child instanceof ITab) {
                child = setTabParameters(child);

                if (child.getId() > 0) {
                    int tabId = child.getId();
                    ((ITab) child).setTabId(tabId);

                    if (firstTabId == -1) {
                        firstTabId = tabId;
                    }

                    mTabs.add(child);
                }
            }
        }

        if (firstTabId > -1) {
            setSelectedTab(firstTabId);
        }

        super.onFinishInflate();
    }

    public void setSelectedTab(int tabId) {
        for (View tab : mTabs) {
            tab.setEnabled(((ITab) tab).getTabId() != tabId);
        }
    }

    public void addTextTab(int tabId, String text) {
        TabTextButton tab = new TabTextButton(mContext, tabId, text);
        addTab(tab);

    }

    private View setTabParameters(View tab) {
        tab.setLayoutParams(mTabLayoutParams);
        tab.setOnClickListener(mTabOnClickListener);
        tab.setBackgroundResource(mTabSelectorResId);
        return tab;
    }

    public void addTab(View tab) {
        tab = setTabParameters(tab);
        mTabs.add(tab);
        addView(tab);
    }

    public void addImageTab(int tabId, Drawable unpressed, Drawable pressed) {
        TabImageButton tab = new TabImageButton(mContext, tabId, unpressed, pressed);
        addTab(tab);
    }

    public void addImageTab(int tabId, int unpressedDrawableResourceId, int pressedDrawableResourceId) {
        addImageTab(tabId, mContext.getResources().getDrawable(unpressedDrawableResourceId),
                mContext.getResources().getDrawable(pressedDrawableResourceId));
    }

    private void handleTabPress(View pressedTab, boolean notifyListener) {
        for (View tab : mTabs) {
            tab.setEnabled(tab != pressedTab);
        }
        if (notifyListener && mTabListener != null) {
            mTabListener.onTabSelected(pressedTab, ((ITab) pressedTab).getTabId());
        }
    }

    public interface OnTabSelectedListener {
        public void onTabSelected(View v, int tabId);
    }
}
