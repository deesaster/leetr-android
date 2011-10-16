package com.leetr.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.leetr.R;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-06-23
 * Time: 10:51 PM
 */
public class LeetrDbHelper {
    private static final String TAG = "LeetrDbHelper";

    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;

    public static LeetrDbHelper open(Context context) {
        LeetrDbHelper leetrDbHelper = new LeetrDbHelper(context);
        leetrDbHelper.openDb();
        return leetrDbHelper;
    }

    public LeetrDbHelper(Context context) {

        String dbName = context.getString(R.string.settings_db_name);
        String[] createQueries = context.getResources().getStringArray(R.array.settings_db_create_queries);

        if (createQueries != null && createQueries.length > 0) {
            mDbHelper = new DatabaseHelper(context, dbName, null, createQueries.length, createQueries);
        } else {
            throw new RuntimeException("check your db create queries in settings.xml");
        }
    }

    public SQLiteDatabase openDb() {
        if (mDbHelper != null) {
            if (mDb == null || !mDb.isOpen()) {
                mDb = mDbHelper.getWritableDatabase();
            }
            return mDb;
        } else {
            throw new RuntimeException("Db helper is not initialized, something went wrong.");
        }
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }

    public void close() {
        if (mDb != null) {
            mDb.close();
        }

        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        private final String[] mQueries;

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String[] queries) {
            super(context, name, factory, version);

            mQueries = queries;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            for (String query : mQueries) {
                db.execSQL(query);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion);

            for (int i = oldVersion; i < mQueries.length; i++) {
                db.execSQL(mQueries[i]);
            }
        }
    }
}
