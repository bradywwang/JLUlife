package com.brady.jlulife.Entities;

/**
 * Created by brady on 15-11-8.
 */
public class CourseSpec {
    private int id;
    private String courseName;
    private String classRoom;
    private String teacherName;
    private int courseId;
    private int week;
    private int startTime;
    private int endTime;
    private int beginWeek;
    private int endWeek;
    private int isSingleWeek;
    private int isDoubleWeek;


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return courseName + '@'+classRoom ;
    }

    public String getString() {
        return "CourseSpec{" +
                "courseName='" + courseName + '\'' +
                ", classRoom='" + classRoom + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", week=" + week +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", beginWeek=" + beginWeek +
                ", endWeek=" + endWeek +
                ", isSingleWeek=" + isSingleWeek +
                ", isDoubleWeek=" + isDoubleWeek +
                '}';
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getBeginWeek() {
        return beginWeek;
    }

    public void setBeginWeek(int beginWeek) {
        this.beginWeek = beginWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public int getIsSingleWeek() {
        return isSingleWeek;
    }

    public void setIsSingleWeek(int isSingleWeek) {
        this.isSingleWeek = isSingleWeek;
    }

    public int getIsDoubleWeek() {
        return isDoubleWeek;
    }

    public void setIsDoubleWeek(int isDoubleWeek) {
        this.isDoubleWeek = isDoubleWeek;
    }

    public CourseSpec() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
