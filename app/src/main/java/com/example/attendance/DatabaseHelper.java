package com.example.attendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;





public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "attendance.db";
    public static final String TABLE_NAME = "attendance_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "STUDENT_ID";
    public static final String COL4 = "PASSWORD";
    public static final String COL5 = "EMAIL";
    public static final String COL6 = "GENDER";
    public static final String COL7 = "DEPARTMENT";
    public static final String COL8 = "SESSION";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String createTable = "CREATE TABLE" + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOCREMENT," + "NAME TEXT, STUDENT_ID TEXT," +
               // " PASSWORD TEXT, EMAIL TEXT, GENDER TEXT, DEPARTMENT TEXT, SESSION TEXT )";

        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + "NAME TEXT, STUDENT_ID TEXT," +
                " PASSWORD TEXT, EMAIL TEXT, GENDER TEXT, DEPARTMENT TEXT, SESSION TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    public boolean addData (String name, String student_id, String password, String email, String gender, String department, String session){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,name);
        contentValues.put(COL3,student_id);
        contentValues.put(COL4,password);
        contentValues.put(COL5,email);
        contentValues.put(COL6,gender);
        contentValues.put(COL7,department);
        contentValues.put(COL8,session);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor showData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    protected boolean updateData(String id, String name, String student_id,
                                 String password, String email, String gender, String department, String session){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL2, name);
        contentValues.put(COL3, student_id);
        contentValues.put(COL4, password);
        contentValues.put(COL5, email);
        contentValues.put(COL6, gender);
        contentValues.put(COL7, department);
        contentValues.put(COL8, session);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;

    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}
