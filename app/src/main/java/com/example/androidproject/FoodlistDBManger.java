package com.example.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FoodlistDBManger extends SQLiteOpenHelper {
    static final String FOODMANAGE_DB = "FoodManage.db";
    static final String FOODMANAGER_TABLE = "foodmanager";
    Context context = null;
    private static FoodlistDBManger dbManager = null;

    static final String FOOD_CREATE_DB = " CREATE TABLE " + FOODMANAGER_TABLE +
            "(_date varchar(100), _when varchar(50), _time varchar(50), image text, location varchar(50)," +
            "score varchar(10), memo text, total_calorie varchar(100), "+
            "menu_name1 varchar(50), menu_name2 varchar(50), menu_name3 varchar(50)," +
            "amount1 varchar(50), amount2 varchar(50), amount3 varchar(50)," +
            "cost1 varchar(50), cost2 varchar(50), cost3 varchar(50)," +
            " primary key (_date,_when));";


    public static FoodlistDBManger getInstance(Context context) {
        if (dbManager == null) {
            dbManager = new FoodlistDBManger(context,
                    FOODMANAGE_DB, null, 1);
        }
        return dbManager;
    }

    public FoodlistDBManger(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, version);
        this.context = context;
        Log.d("table", FOODMANAGER_TABLE);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FOOD_CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS '" + FOODMANAGER_TABLE + "'");
        onCreate(db);
    }


    public long insert(ContentValues addValue) {
       return getWritableDatabase().insert(FOODMANAGER_TABLE,
                null, addValue);
    }


    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return getReadableDatabase().query(FOODMANAGER_TABLE,
                columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return getWritableDatabase().delete(FOODMANAGER_TABLE, whereClause, whereArgs);
    }
}
