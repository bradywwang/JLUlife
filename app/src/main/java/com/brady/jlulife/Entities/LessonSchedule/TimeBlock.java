package com.brady.jlulife.Entities.LessonSchedule;

/**
 * Created by brady on 15-11-7.
 */
public class TimeBlock {
    private int classSet;
    private String name;
    private int endWeek;
    private int beginWeek;
    private int tmbId;
    private int dayOfWeek;

    public int getClassSet() {
        return classSet;
    }

    public void setClassSet(int classSet) {
        this.classSet = classSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public int getBeginWeek() {
        return beginWeek;
    }

    public void setBeginWeek(int beginWeek) {
        this.beginWeek = beginWeek;
    }

    public int getTmbId() {
        return tmbId;
    }

    public void setTmbId(int tmbId) {
        this.tmbId = tmbId;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
