package com.example.riya.studentsrecordapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by riya on 2/10/16.
 */
public final class StudentsRecordDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "studentsrecord.db";
    public static final String TABLE_NAME = "students";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME_ROLLNO = "rollno";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_MARKS = "marks";
    public static final String COLUMN_NAME_RATING = "performanceRating";

    public StudentsRecordDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_ROLLNO + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +COLUMN_NAME_MARKS + TEXT_TYPE + COMMA_SEP + COLUMN_NAME_RATING + TEXT_TYPE + ")";


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean insertStudent(String rollno, String name, String marks, int rating) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_NAME, name);
        contentValues.put(COLUMN_NAME_ROLLNO, rollno);
        contentValues.put(COLUMN_NAME_MARKS, marks);
        contentValues.put(COLUMN_NAME_RATING, rating);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

   // public Cursor getAllPersons() {
    //    SQLiteDatabase db = this.getReadableDatabase();
     //   Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
      //  return res;
   // }

    public Integer deleteStudent(String rollno) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                COLUMN_NAME_ROLLNO + " = ? ",
                new String[] { Integer.toString(Integer.parseInt(rollno)) });
    }

    public boolean updateStudent(String rollno, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_MARKS, marks);
        db.update(TABLE_NAME, contentValues, COLUMN_NAME_ROLLNO + " = ? ", new String[] { Integer.toString(Integer.parseInt(rollno)) } );
        return true;
    }
}

