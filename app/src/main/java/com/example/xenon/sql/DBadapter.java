package com.example.xenon.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import junit.runner.Version;

/**
 * Created by xenon on 08.06.17.
 */

public class DBadapter {
    private static final String DEBUG_TAG = "SqLiteTodoManager";
    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;


    public static final String KEY_ID = "id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTO INCREMENT";
    public static final int ID_COLUMN = 0;
    public static final String KEY_DESCRIPTION = "description";
    public static final String DESCRIPTION_OPTIONS = "TEXT NOT NULL";
    public static final int DESCRIPTION_COLUMN = 1;
    public static final String PROCESSOR_CLOCK = "clock";
    public static final String NUMBER_OPTIONS = "INTEGER DEFAULT 0";
    public static final int NUMBER_COLUMN = 2;



    private static final String DB_NAME = "database.db";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "phones";

    private static final String DB_CREATE_TABLE =
            "CREATE TABLE " + DB_TABLE + "( " +
    KEY_ID + " " + ID_OPTIONS + ", " +
    KEY_DESCRIPTION + " " + DESCRIPTION_OPTIONS + ", " +
                    PROCESSOR_CLOCK + " " + NUMBER_OPTIONS + ");";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + DB_TABLE;

    public DBadapter(Context context) {
        this.context = context;
    }
    public DBadapter open(){
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION );
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }
    public void close() {
        dbHelper.close();
    }


    public long insertPhone(double number,String description) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_DESCRIPTION , description);
        newValues.put(PROCESSOR_CLOCK, number);
        return db.insert(DB_TABLE, null, newValues);
    }


    public boolean updateData(Phones contact) {
        long id = contact.getId();
        String description = contact.getDescription();
        double number = contact.getNumber();
        return updateData(id, description, number);
    }

    public boolean updateData(long id, String description, double number) {
        String where = KEY_ID + "= " + id;
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_DESCRIPTION, description);
        updateValues.put(PROCESSOR_CLOCK, number);
        return db.update(DB_TABLE, updateValues, where, null) > 0; //mod z todo table
    }
    public boolean deleteData(long id){
        String where = KEY_ID + "= " + id;
        return db.delete(DB_TABLE, where, null) > 0;
    }
    public Cursor getAllData() {
        String[] columns = {KEY_ID , KEY_DESCRIPTION, PROCESSOR_CLOCK};
        return db.query(DB_TABLE, columns, null, null, null, null, null);
    }

    public Phones getData(long id) {
        String[] columns = {KEY_ID , KEY_DESCRIPTION, PROCESSOR_CLOCK};
        String where = KEY_ID + "= " + id;
        Cursor cursor = db.query(DB_TABLE, columns, where, null, null, null, null);
        Phones contact = null;
        if(cursor != null && cursor.moveToFirst()) {
            String description = cursor.getString(DESCRIPTION_COLUMN);
            int number = cursor.getInt(NUMBER_COLUMN );
            contact = new Phones(id, description, number);
        }
        return contact;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);

        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_TABLE);   //tu modyfikacja z todo_table
            Log.d(DEBUG_TAG , "Database creating...");
            Log.d(DEBUG_TAG , "Table " + DB_TABLE + " ver." + DB_VERSION + " created");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE);
            Log.d(DEBUG_TAG , "Database updating...");
            Log.d(DEBUG_TAG , "Table " + DB_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG , "All data is lost.");
            onCreate(db);
        }
    }


}
