package com.brady.jlulife.Models.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by brady on 15-11-8.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS course");
        db.execSQL("CREATE TABLE course (id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "courseid INTEGER AUTO_INCREMENT, coursename varchar,classroom varchar," +
                "teachername varchar,week INTEGER,startTime INTEGER, endTime INTEGER, " +
                "beginWeek INTEGER, endWeek INTEGER, isSingleWeek INTEGER, isDoubleWeek INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS course");
        db.execSQL("CREATE TABLE course (id INTEGER PRIMARY KEY AUTOINCREMENT , courseid INTEGER AUTO_INCREMENT, coursename varchar,classroom varchar,teachername varchar,week INTEGER,startTime INTEGER, endTime INTEGER, beginWeek INTEGER, endWeek INTEGER, isSingleWeek INTEGER, isDoubleWeek INTEGER)");
    }
}
