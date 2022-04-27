package com.aek.phonestationmanager.core.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppProvider extends ContentProvider {
    private final String TAG = "LOG_" + ConstantsProvider.PROVIDER_NAME;

    public static class ConstantsProvider {
        public static final String PROVIDER_NAME = AppProvider.class.getName();
        public static final String PATH = "user";
        public static final String URL = "content://" + PROVIDER_NAME + "/" + PATH;
        public static final Uri URI_USER_TABLE = Uri.parse(URL);

        static final UriMatcher URI_MATCHER;

        static {
            URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
            URI_MATCHER.addURI(ConstantsProvider.PROVIDER_NAME, ConstantsProvider.PATH, 1);
        }
    }

    public static class ConstantsUser {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String PASS = "pass";
        public static final String TOKEN = "token";
    }

    public static class ConstantsSqlite {
        public static SQLiteDatabase sqLiteDatabase;
        public static final String DB_NAME = ConstantsProvider.PATH + ".db";
        public static final String TABLE_NAME = ConstantsProvider.PATH;
        public static final int DB_VERSION = 2;
        public static final String CREATE_TABLE;

        static SQLiteQueryBuilder sqLiteQueryBuilder;

        static {
            CREATE_TABLE = String.format("CREATE TABLE %1$s (" +
                            "%2$s INTEGER PRIMARY KEY," +
                            "%3$s TEXT NOT NULL," +
                            "%4$s TEXT NOT NULL," +
                            "%5$s TEXT NOT NULL" +
                            ");",
                    TABLE_NAME,
                    ConstantsUser.ID,
                    ConstantsUser.NAME,
                    ConstantsUser.PASS,
                    ConstantsUser.TOKEN);
        }
    }


    @Override
    public boolean onCreate() {

        ConstantsSqlite.sqLiteDatabase = SQLiteDB.getInstance(getContext()).getWritableDatabase();
        return ConstantsSqlite.sqLiteDatabase != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        if (ConstantsSqlite.sqLiteQueryBuilder != null)
            ConstantsSqlite.sqLiteQueryBuilder = null;

        ConstantsSqlite.sqLiteQueryBuilder = new SQLiteQueryBuilder();
        ConstantsSqlite.sqLiteQueryBuilder.setTables(ConstantsSqlite.TABLE_NAME);

        Cursor cursor = ConstantsSqlite.sqLiteQueryBuilder.query(ConstantsSqlite.sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    public void notifyChange(Uri uri, ContentObserver observer) {
        getContext().getContentResolver().notifyChange(uri, observer);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        try {

            switch (ConstantsProvider.URI_MATCHER.match(uri)) {
                case 1:
                    long insertId = ConstantsSqlite.sqLiteDatabase.insert(ConstantsSqlite.TABLE_NAME, "", contentValues);
                    if (insertId > 0) {
                        Uri insertUri = ContentUris.withAppendedId(uri, insertId);
                        notifyChange(insertUri, null);
                        return insertUri;
                    }
            }
        } catch (Exception e) {
            Log.e(TAG, "insert: " + e.toString());
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        try {
            int deleteRows = ConstantsSqlite.sqLiteDatabase.delete(ConstantsSqlite.TABLE_NAME, s, strings);
            notifyChange(uri, null);
            return deleteRows;
        } catch (Exception e) {
            Log.e(TAG, "delete: " + e.toString());
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        try {
            int updateRows = ConstantsSqlite.sqLiteDatabase.update(ConstantsSqlite.TABLE_NAME, contentValues, s, strings);
            if (updateRows > 0) {
                notifyChange(uri, null);
                return updateRows;
            }
        } catch (Exception e) {
            Log.e(TAG, "update: " + e.toString());
        }
        return 0;
    }


    public static class SQLiteDB extends SQLiteOpenHelper {
        private static SQLiteDB _sqliteDB;

        public static SQLiteDB getInstance(Context context) {
            if (_sqliteDB == null)
                _sqliteDB = new SQLiteDB(context);
            return _sqliteDB;
        }


        private SQLiteDB(@Nullable Context context) {
            super(context, AppProvider.ConstantsSqlite.DB_NAME, null, AppProvider.ConstantsSqlite.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(AppProvider.ConstantsSqlite.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ConstantsSqlite.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }


}
