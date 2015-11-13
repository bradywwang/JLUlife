package com.brady.jlulife.Entities.LessonSchedule;

/**
 * Created by brady on 15-11-7.
 */
public class LessonValue {
    private TeachClassMaster teachClassMaster;
    private int tcsId;
    private String dateAccept;

    public TeachClassMaster getTeachClassMaster() {
        return teachClassMaster;
    }

    public void setTeachClassMaster(TeachClassMaster teachClassMaster) {
        this.teachClassMaster = teachClassMaster;
    }

    public int getTcsId() {
        return tcsId;
    }

    public void setTcsId(int tcsId) {
        this.tcsId = tcsId;
    }

    public String getDateAccept() {
        return dateAccept;
    }

    public void setDateAccept(String dateAccept) {
        this.dateAccept = dateAccept;
    }

    @Override
    public String toString() {
        return "LessonValue{" +
                "teachClassMaster=" + teachClassMaster +
                ", tcsId=" + tcsId +
                ", dateAccept='" + dateAccept + '\'' +
                '}';
    }
}
