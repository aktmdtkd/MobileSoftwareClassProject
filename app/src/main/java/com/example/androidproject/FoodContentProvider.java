package com.example.androidproject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class FoodContentProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.course.ContentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/_date" + "/_when";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String _DATE = "_date";
    static final String WHEN = "_when";
    static final String _TIME = "_time";
    static final String IMAGE = "image";
    static final String LOCATION = "location";
    static final String SCORE = "score";
    static final String MEMO = "memo";
    static final String TOTAL_CALORIE = "total_calorie";
    static final String MENU_NAME1 = "menu_name1";
    static final String MENU_NAME2 = "menu_name2";
    static final String MENU_NAME3 = "menu_name3";
    static final String AMOUNT1 = "amount1";
    static final String AMOUNT2 = "amount2";
    static final String AMOUNT3 = "amount3";
    static final String COST1 = "cost1";
    static final String COST2 = "cost2";
    static final String COST3 = "cost3";
    public FoodlistDBManger dbManager;
    public FoodContentProvider() {
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return dbManager.delete(selection, selectionArgs);
    }
    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowid = dbManager.insert(values);
        return null;
    }
    @Override
    public boolean onCreate() {
        dbManager = FoodlistDBManger.getInstance(getContext());
        return true;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String
                                sortOrder) {
        return dbManager.query(projection, selection,
                selectionArgs, null, null, sortOrder);
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase sqlDB = dbManager.getWritableDatabase();
        int rowsUpdated = 0;
        rowsUpdated = sqlDB.update(FoodlistDBManger.FOODMANAGER_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsUpdated;
    }

}
