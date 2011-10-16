package com.leetr.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.leetr.R;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-21
 * Time: 11:13 PM
 */
public class LeetrWebFragment extends LeetrFragment {
    public static final String KEY_URL = "url";
    private View mLayout;
    private String mUrl;
    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.leetr_web_fragment, container, false);
        return mLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null && args.containsKey(KEY_URL)) {
            mUrl = args.getString(KEY_URL);
        }

        initUiComponents();
    }

    private void initUiComponents() {
        mWebView = (WebView) mLayout.findViewById(R.id.webView);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setInitialScale(1);

        if (mUrl != null && mUrl.length() > 0) {
            mWebView.loadUrl(mUrl);
        }
    }
}
