package com.leetr.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import com.leetr.ui.base.ITab;

public class TabTextButton extends Button implements ITab {
    private int mTabId;

    public TabTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public TabTextButton(Context context, int tabId, String text) {
        super(context);
        setTabId(tabId);
        setText(text);
    }

    @Override
    public int getTabId() {
        return mTabId;
    }

    @Override
    public void setTabId(int tabId) {
        mTabId = tabId;
    }
}
