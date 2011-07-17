package com.leetr.core.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import com.leetr.core.WorkQueue;
import com.leetr.core.interfaces.ILeetrUtility;
import com.leetr.core.listener.OnCacheBitmapLoadListener;
import com.leetr.core.listener.OnFileSaveListener;

import java.io.*;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-14
 * Time: 5:14 PM
 *
 * TODO Implement cache cleansing
 */
public class FileCacheUtility extends WorkQueue implements ILeetrUtility {
//    private final static String TAG = "FileCacheUtility";
    private final static int NUM_THREADS = 2;
    private final static String CACHE_DIR = "cache";

    private Context mContext;

    public FileCacheUtility(Context context) {
        super(NUM_THREADS);

        mContext = context;
        createCacheDir();
    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {
        deleteFileCache();
    }

    protected void deleteFileCache() {
        File cacheDir = new File(getCacheDirPath());
        File[] files = cacheDir.listFiles();

        for (File file : files) {
            file.delete();
        }
    }

    protected void createCacheDir() {
        File file = new File(getCacheDirPath());
        if (!file.exists()) {
            file.mkdirs();
        }

        File noMedia = new File(getCacheDirPath() + "/.nomedia");
        if (!noMedia.exists()) {
            try {
                noMedia.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveBitmap(Bitmap bitmap, String filename, OnFileSaveListener listener) {
        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;

        int dotIndex = filename.lastIndexOf(".");
        String ext = filename.substring(dotIndex + 1);

        if (ext != null && (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg"))) {
            compressFormat = Bitmap.CompressFormat.JPEG;
        }

        postRunnable(new SaveBitmapRunnable(bitmap, compressFormat, getCacheDirPath(), filename, listener));
    }

    public void loadBitmap( String filename, OnCacheBitmapLoadListener listenerCache) {
        postRunnable(new LoadBitmapRunnable(getCacheDirPath(), filename, listenerCache));
    }

    public boolean fileExists(String fileName) {
        File file = new File(getCacheDirPath() + "/" + fileName);
        return file.exists();
    }

    protected String getCacheDirPath() {
        String cacheDir = "/" + CACHE_DIR;

        return Environment.getExternalStorageDirectory().toString() + cacheDir;
    }

    private class LoadBitmapRunnable implements Runnable {

        private String mDir;
        private String mFilename;
        private OnCacheBitmapLoadListener mListenerCache;

        public LoadBitmapRunnable(String dir, String filename, OnCacheBitmapLoadListener listenerCache) {
            mDir = dir;
            mFilename = filename;
            mListenerCache = listenerCache;
        }

        @Override
        public void run() {
            try {
                Bitmap b = BitmapFactory.decodeFile(mDir + "/" + mFilename);

                if (mListenerCache != null) {
                    mListenerCache.onCacheBitmapLoad(b, mFilename);
                }

            } catch (Exception e) {
                e.printStackTrace();

                if (mListenerCache != null) {
                    mListenerCache.onCacheBitmapLoadFail(e.getMessage(), mFilename);
                }
            }
        }
    }

    private class SaveBitmapRunnable implements Runnable {
        private Bitmap mBitmap;
        private Bitmap.CompressFormat mCompressFormat;
        private String mDir;
        private String mFilename;
        private OnFileSaveListener mListener;

        public SaveBitmapRunnable(Bitmap bitmap, Bitmap.CompressFormat compressFormat, String dir, String filename, OnFileSaveListener listener) {
            mBitmap = bitmap;
            mCompressFormat = compressFormat;
            mDir = dir;
            mFilename = filename;
            mListener = listener;
        }

        @Override
        public void run() {
            OutputStream outStream;

            File file = new File(mDir + "/" + mFilename);

            try {
                outStream = new FileOutputStream(file);
                mBitmap.compress(mCompressFormat, 100, outStream);
                outStream.flush();
                outStream.close();

                if (mListener != null) {
                    mListener.onFileSaveSuccess(mFilename, mDir);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();

                if (mListener != null) {
                    mListener.onFileSaveFail(mFilename, mDir, e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();

                if (mListener != null) {
                    mListener.onFileSaveFail(mFilename, mDir, e.getMessage());
                }
            }
        }
    }
}
