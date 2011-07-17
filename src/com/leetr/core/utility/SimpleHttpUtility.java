package com.leetr.core.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.leetr.core.WorkQueue;
import com.leetr.core.interfaces.ILeetrUtility;
import com.leetr.core.listener.OnHttpBitmapListener;
import com.leetr.core.listener.OnHttpJacksonListener;
import com.leetr.core.listener.OnHttpStringListener;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-04
 * Time: 9:13 PM
 */
public class SimpleHttpUtility extends WorkQueue implements ILeetrUtility {
    private final static String TAG = "SimpleHttpUtility";
    private final static int NUM_THREADS = 5;

    public SimpleHttpUtility() {
        super(NUM_THREADS);
    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {
        // TODO add shutdown code
    }

    public void bitmapFromUrl(String url, OnHttpBitmapListener listener) {
        Log.d(TAG, "bitmapFromUrl");
        postRunnable(new BitmapFromUrlRunnable(url, listener));
    }

    public void stringFromUrl(String url, OnHttpStringListener listener) {
        Log.d(TAG, "stringFromUrl");
        postRunnable(new StringFromUrlRunnable(url, listener));
    }


    public void jacksonFromUrl(String url, Class<?> objectClass, OnHttpJacksonListener listener) {
        Log.d(TAG, "jacksonFromUrl");
        postRunnable(new JacksonFromUrlRunnable(url, objectClass, listener));
    }

    private class BitmapFromUrlRunnable implements Runnable {
        private String mUrl;
        private OnHttpBitmapListener mListener;

        public BitmapFromUrlRunnable(String url, OnHttpBitmapListener listener) {
            mUrl = url;
            mListener = listener;
        }

        @Override
        public void run() {
            URL bitmapUrl;

            try {
                bitmapUrl = new URL(mUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();

                if (mListener != null) {
                    mListener.onHttpBitmapLoadFailed(e.toString());
                }
                return;
            }

            try {
                HttpURLConnection conn = (HttpURLConnection) bitmapUrl.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();

                Bitmap b = BitmapFactory.decodeStream(is);

                if (mListener != null) {
                    mListener.onHttpBitmapLoad(b, mUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();

                if (mListener != null) {
                    mListener.onHttpBitmapLoadFailed(e.toString());
                }
            }
        }
    }

    private class StringFromUrlRunnable implements Runnable {
        private String mUrl;
        private OnHttpStringListener mListener;

        public StringFromUrlRunnable(String url, OnHttpStringListener listener) {
            mUrl = url;
            mListener = listener;
        }

        @Override
        public void run() {
            URL url;

            try {
                url = new URL(mUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();

                if (mListener != null) {
                    mListener.onStringLoadFailed();
                }
                return;
            }

            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();

                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                StringBuffer sb = new StringBuffer();
                String line;

                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }

                if (mListener != null) {
                    mListener.onStringLoaded(sb.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();

                if (mListener != null) {
                    mListener.onStringLoadFailed();
                }
            }
        }
    }

    private class JacksonFromUrlRunnable implements Runnable {
        private String mUrl;
        private OnHttpJacksonListener mListener;
        private Class<?> mObjectClass;

        public JacksonFromUrlRunnable(String url, Class<?> objectClass, OnHttpJacksonListener listener) {
            mUrl = url;
            mObjectClass = objectClass;
            mListener = listener;
        }

        @Override
        public void run() {
            URL url;

            try {
                url = new URL(mUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();

                if (mListener != null) {
                    mListener.onJacksonLoadFailed();
                }
                return;
            }

            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Object mappedObject = mapper.readValue(url, mObjectClass);

                if (mListener != null) {
                    mListener.onJacksonLoaded(mapper, mappedObject);
                }
            } catch (Exception e) {
                e.printStackTrace();

                if (mListener != null) {
                    mListener.onJacksonLoadFailed();
                }
            }
        }
    }
}
