package com.brady.jlulife.Entities.LessonSchedule;

import com.brady.jlulife.Entities.LessonSchedule.LessonSchedules;
import com.brady.jlulife.Entities.LessonSchedule.LessonSegment;
import com.brady.jlulife.Entities.LessonSchedule.LessonTeachers;

import java.util.ArrayList;

/**
 * Created by brady on 15-11-7.
 */
public class TeachClassMaster {
    private int maxStudCnt;
    private ArrayList<LessonSchedules> lessonSchedules;
    private int studCnt;
    private ArrayList<LessonTeachers> lessonTeachers;
    private String name;
    private int tcmId;
    LessonSegment lessonSegment;

    public int getMaxStudCnt() {
        return maxStudCnt;
    }

    public void setMaxStudCnt(int maxStudCnt) {
        this.maxStudCnt = maxStudCnt;
    }

    public ArrayList<LessonSchedules> getLessonSchedules() {
        return lessonSchedules;
    }

    public void setLessonSchedules(ArrayList<LessonSchedules> lessonSchedules) {
        this.lessonSchedules = lessonSchedules;
    }

    public int getStudCnt() {
        return studCnt;
    }

    public void setStudCnt(int studCnt) {
        this.studCnt = studCnt;
    }

    public ArrayList<LessonTeachers> getLessonTeachers() {
        return lessonTeachers;
    }

    public void setLessonTeachers(ArrayList<LessonTeachers> lessonTeachers) {
        this.lessonTeachers = lessonTeachers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTcmId() {
        return tcmId;
    }

    public void setTcmId(int tcmId) {
        this.tcmId = tcmId;
    }

    public LessonSegment getLessonSegment() {
        return lessonSegment;
    }

    public void setLessonSegment(LessonSegment lessonSegment) {
        this.lessonSegment = lessonSegment;
    }
}
