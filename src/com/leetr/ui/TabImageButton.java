package com.leetr.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import com.leetr.R;
import com.leetr.ui.base.ITab;

public class TabImageButton extends ImageButton implements ITab {
    private int mTabId;
    private Drawable mPressedDrawable, mUnpressedDrawable;

    public TabImageButton(Context context, int tabId, Drawable unpressed, Drawable pressed) {
        super(context);
        setTabId(tabId);

        mPressedDrawable = pressed;
        mUnpressedDrawable = unpressed;
        setImageDrawable(mUnpressedDrawable);
    }

    public TabImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TabImageButton);
        int unpressedDrawableId = a.getResourceId(R.styleable.TabImageButton_unpressedDrawable, 0);
        int pressedDrawableId = a.getResourceId(R.styleable.TabImageButton_pressedDrawable, 0);
        a.recycle();

        if (unpressedDrawableId > 0) {
            mUnpressedDrawable = context.getResources().getDrawable(unpressedDrawableId);
        }
        if (pressedDrawableId > 0) {
            mPressedDrawable = context.getResources().getDrawable(pressedDrawableId);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        setImageDrawable(enabled ? mUnpressedDrawable : mPressedDrawable);
    }

    public int getTabId() {
        return mTabId;
    }

    public void setTabId(int tabId) {
        mTabId = tabId;
    }
}
