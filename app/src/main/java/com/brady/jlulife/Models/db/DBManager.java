package com.brady.jlulife.Models.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.brady.jlulife.Entities.CourseSpec;
import com.brady.jlulife.Entities.Score.Course;
import com.brady.jlulife.Utils.ConstValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brady on 15-11-13.
 */
public class DBManager {
    private DBHelper mhelper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        mhelper = new DBHelper(context, "jlulife.db", null, 2);
        db = mhelper.getWritableDatabase();
    }


    public void addAllCourses(List<CourseSpec> courseSpecList) {
        db.beginTransaction();
        try {
            for (CourseSpec c : courseSpecList) {
                db.execSQL("INSERT INTO course VALUES(null,?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{c.getCourseId(), c.getCourseName(), c.getClassRoom(), c.getTeacherName(), c.getWeek(), c.getStartTime(), c.getEndTime(), c.getBeginWeek(), c.getEndWeek(), c.getIsSingleWeek(), c.getIsDoubleWeek()});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public List<CourseSpec> queryAllCourses() {
        List<CourseSpec> courseSpecs = new ArrayList<CourseSpec>();
        CourseSpec courseSpec;
        Cursor c = db.query("course", null, null, null, null, null, null);
        while (c.moveToNext()) {
            courseSpec = new CourseSpec();
            courseSpec.setId(c.getInt(c.getColumnIndex("id")));
            courseSpec.setCourseId(c.getInt(c.getColumnIndex("courseid")));
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

    public void deleteAllItems() {
        db.execSQL("DELETE FROM course");
    }

    public CourseSpec queryCourse(int courseId) {
        CourseSpec spec = new CourseSpec();
        Cursor c = db.query("course", null, "id = " + courseId, null, null, null, null);
        if(c.moveToNext()) {
            spec.setId(c.getInt(c.getColumnIndex("id")));
            spec.setCourseId(c.getInt(c.getColumnIndex("courseid")));
            spec.setBeginWeek(c.getInt(c.getColumnIndex("beginWeek")));
            spec.setClassRoom(c.getString(c.getColumnIndex("classroom")));
            spec.setCourseName(c.getString(c.getColumnIndex("coursename")));
            spec.setEndTime(c.getInt(c.getColumnIndex("endTime")));
            spec.setEndWeek(c.getInt(c.getColumnIndex("endWeek")));
            spec.setIsDoubleWeek(c.getInt(c.getColumnIndex("isDoubleWeek")));
            spec.setIsSingleWeek(c.getInt(c.getColumnIndex("isSingleWeek")));
            spec.setStartTime(c.getInt(c.getColumnIndex("startTime")));
            spec.setTeacherName(c.getString(c.getColumnIndex("teachername")));
            spec.setWeek(c.getInt(c.getColumnIndex("week")));
        }
        return spec;
    }

    public void addCourse(CourseSpec c) {
        db.beginTransaction();
        try {
            if(c.getCourseId()==0){
                db.execSQL("INSERT INTO course VALUES(null,null,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{c.getCourseName(), c.getClassRoom(), c.getTeacherName(), c.getWeek(), c.getStartTime(), c.getEndTime(), c.getBeginWeek(), c.getEndWeek(), c.getIsSingleWeek(), c.getIsDoubleWeek()});
            }else {
                db.execSQL("INSERT INTO course VALUES(null,?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{c.getCourseId(),c.getCourseName(), c.getClassRoom(), c.getTeacherName(), c.getWeek(), c.getStartTime(), c.getEndTime(), c.getBeginWeek(), c.getEndWeek(), c.getIsSingleWeek(), c.getIsDoubleWeek()});
            }
                db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void deleteCourse(int courseId){
        db.beginTransaction();
        try {
            db.delete("course","id = "+courseId,null);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void editCourse(int courseId,CourseSpec c){
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("isSingleWeek",c.getIsSingleWeek());
            values.put("isDoubleWeek",c.getIsDoubleWeek());
            values.put("coursename",c.getCourseName());
            values.put("teachername",c.getTeacherName());
            values.put("classroom",c.getClassRoom());
            values.put("beginWeek",c.getBeginWeek());
            values.put("endWeek",c.getEndWeek());
            values.put("startTime", c.getStartTime());
            values.put("endTime", c.getEndTime());
            values.put("week", c.getWeek());
            int i =db.update("course", values, "id=?", new String[]{String.valueOf(courseId)});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }
}
