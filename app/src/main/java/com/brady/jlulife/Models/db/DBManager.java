package com.brady.jlulife.Models.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.brady.jlulife.Entities.CourseSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brady on 15-11-13.
 */
public class DBManager {
    private DBHelper mhelper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        mhelper = new DBHelper(context,"jlulife.db",null,1);
        db = mhelper.getWritableDatabase();
    }


    public void addAllCourses(List<CourseSpec> courseSpecList){
        db.beginTransaction();
        try{
            for(CourseSpec c: courseSpecList){
                db.execSQL("INSERT INTO course VALUES(null,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{c.getCourseName(),c.getClassRoom(),c.getTeacherName(),c.getWeek(),c.getStartTime(),c.getEndTime(),c.getBeginWeek(),c.getEndWeek(),c.getIsSingleWeek(),c.getIsDoubleWeek()});
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }
    public List<CourseSpec> queryAllCourses(){
        List<CourseSpec> courseSpecs= new ArrayList<CourseSpec>();
        CourseSpec courseSpec;
        Cursor c = db.query("course",null,null,null,null,null,null);
        while (c.moveToNext()){
            courseSpec = new CourseSpec();
            courseSpec.setBeginWeek(c.getInt(c.getColumnIndex("beginWeek")));
            courseSpec.setClassRoom(c.getString(c.getColumnIndex("classroom")));
            courseSpec.setCourseName(c.getString(c.getColumnIndex("coursename")));
            courseSpec.setEndTime(c.getInt(c.getColumnIndex("endTime")));
            courseSpec.setEndWeek(c.getInt(c.getColumnIndex("endWeek")));
            courseSpec.setIsDoubleWeek(c.getInt(c.getColumnIndex("isDoubleWeek")));
            courseSpec.setIsSingleWeek(c.getInt(c.getColumnIndex("isSingleWeek")));
            courseSpec.setStartTime(c.getInt(c.getColumnIndex("startTime")));
            courseSpec.setTeacherName(c.getString(c.getColumnIndex("teachername")));
            courseSpec.setWeek(c.getInt(c.getColumnIndex("week")));
            courseSpecs.add(courseSpec);
        }
        c.close();
        return courseSpecs;
    }
    public void deleteAllItems(){
        db.execSQL("DELETE FROM course");
    }
}
