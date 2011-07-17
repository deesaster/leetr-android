package com.leetr.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-14
 * Time: 11:40 AM
 *
 * TODO add preloading at certain scroll position
 */
public abstract class EndlessArrayAdapter<T> extends ArrayAdapter<T> {

    public static final long ID_ITEM = 1;
    public static final long ID_FOOTER = 2;

    private int mViewResId;
    private int mFooterViewResId;

    private int mItemViewId;
    private int mFooterViewId;

    private LayoutInflater mInflater;

    private boolean mLoading;

    public EndlessArrayAdapter(Context context, int resource, int textViewResourceId, int footerResourceId, List<T> objects) {
        super(context, resource, textViewResourceId, objects);

        mViewResId = resource;
        mFooterViewResId = footerResourceId;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void notifyDataSetChanged() {
        mLoading = false;
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (isMoreAvailable()) {
            return (super.getCount() + 1);
        }
        return super.getCount();
    }

    @Override
    public T getItem(int position) {
        if (getItemId(position) == ID_FOOTER) {
            return null;
        }

        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        if (position == (getCount() - 1) && isMoreAvailable()) {
            return ID_FOOTER;
        }
        return ID_ITEM;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        long itemId = getItemId(position);

        if (itemId == ID_FOOTER) {
            // TODO Add holder for footer view for faster loading
            row = mInflater.inflate(mFooterViewResId, parent, false);

            mFooterViewId = row.getId();

            if (mFooterViewId == -1) {
                mFooterViewId = (int) ID_FOOTER;
                row.setId(mFooterViewId);
            }

            populateFooterView(row);

            if (shouldLoadMoreAutomatically() && !mLoading) {
                onLoadMore();
            }
        } else {
            if (convertView == null || convertView.getId() == mFooterViewId) {
                Log.d("test", "inflating a new view");

                row = mInflater.inflate(mViewResId, parent, false);

                mItemViewId = row.getId();

                if (mItemViewId == -1) {
                    mItemViewId = (int) ID_ITEM;
                    row.setId(mItemViewId);
                }
            } else {
                row = convertView;
            }

            populateView(position, getItem(position), row);
        }

        return row;
    }

    protected abstract void populateView(int position, T item, View view);

    protected abstract void populateFooterView(View view);

    protected abstract boolean isMoreAvailable();

    protected abstract boolean shouldLoadMoreAutomatically();

    public void onLoadMore() {
        mLoading = true;
    }


}
