package com.gmail.jasekurasz.bestbefore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jasekurasz on 7/6/15.
 */
public class dbTools extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "foodItems.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + dbEntry.TABLE_NAME + " (" +
            dbEntry._ID + " INTEGER PRIMARY KEY," +
            dbEntry.COLUMN_NAME_FOOD_NAME + TEXT_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
            dbEntry.COLUMN_NAME_IMAGE + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + dbEntry.TABLE_NAME;
    private static final String SQL_DELETE_ITEM =
            "DELETE FROM " + dbEntry.TABLE_NAME + " WHERE "
            + dbEntry._ID + "=";
    private static final String SQL_DELETE_ITEMS =
            "DELETE FROM " + dbEntry.TABLE_NAME;
    private static final String SQL_GET_ITEM =
            "SELECT FROM " + dbEntry.TABLE_NAME + " WHERE "
                    + dbEntry._ID + "=";
    private static final String SQL_GET_ITEMS =
            "SELECT * FROM " + dbEntry.TABLE_NAME;

    public static abstract class dbEntry implements BaseColumns {
        public static final String TABLE_NAME = "foodItems";
        public static final String COLUMN_NAME_FOOD_NAME = "name";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_IMAGE = "image";
    }

    public dbTools(Context appContext) {
        super(appContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void addItem(HashMap<String, String> queryValues) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(dbEntry.COLUMN_NAME_FOOD_NAME, queryValues.get("name"));
        values.put(dbEntry.COLUMN_NAME_DATE, queryValues.get("date"));
        values.put(dbEntry.COLUMN_NAME_IMAGE, queryValues.get("image"));

        db.insert(dbEntry.TABLE_NAME, null, values);

        db.close();
    }

    public int updateItem(HashMap<String, String> queryValues) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(dbEntry.COLUMN_NAME_FOOD_NAME, queryValues.get("name"));
        values.put(dbEntry.COLUMN_NAME_DATE, queryValues.get("date"));
        values.put(dbEntry.COLUMN_NAME_IMAGE, queryValues.get("image"));

        return db.update(dbTools.DATABASE_NAME, values, dbEntry._ID + " =?", new String[]{queryValues.get(dbEntry._ID)});

    }

    public void deleteItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String deleteQuery = dbTools.SQL_DELETE_ITEM + "'" + id +"'";

        db.execSQL(deleteQuery);
    }

    public void deleteAllItems() {
        SQLiteDatabase db = this.getWritableDatabase();

        String deleteQuery = dbTools.SQL_DELETE_ITEMS;

        db.execSQL(deleteQuery);
    }

    public ArrayList<HashMap<String, String>> getAllItems() {

        ArrayList<HashMap<String, String>> itemArrayList;

        itemArrayList = new ArrayList<>();

        String selectQuery = dbTools.SQL_GET_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                HashMap<String, String> itemMap = new HashMap<>();

                itemMap.put(dbEntry._ID, cursor.getString(0));
                itemMap.put(dbEntry.COLUMN_NAME_FOOD_NAME, cursor.getString(1));
                itemMap.put(dbEntry.COLUMN_NAME_DATE, cursor.getString(2));
                itemMap.put(dbEntry.COLUMN_NAME_IMAGE, cursor.getString(3));

                itemArrayList.add(itemMap);
            } while (cursor.moveToNext());
        }

        return itemArrayList;
    }

    public Cursor getAllItemsCursor() {

        String selectQuery = dbTools.SQL_GET_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    public HashMap<String, String> getItemInfo(String id) {
        HashMap<String, String> itemMap = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = dbTools.SQL_GET_ITEM + "'" + id +"'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                itemMap.put(dbEntry.COLUMN_NAME_FOOD_NAME, cursor.getString(1));
                itemMap.put(dbEntry.COLUMN_NAME_DATE, cursor.getString(2));
                itemMap.put(dbEntry.COLUMN_NAME_IMAGE, cursor.getString(3));
            } while (cursor.moveToNext());
        }
        return itemMap;
    }
}
