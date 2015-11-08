package com.brady.jlulife.Entities.LessonSchedule;

/**
 * Created by brady on 15-11-7.
 */
public class LessonSegment {
    private int lssgId;
    private Lesson lesson;
    private String fullName;

    public int getLssgId() {
        return lssgId;
    }

    public void setLssgId(int lssgId) {
        this.lssgId = lssgId;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
