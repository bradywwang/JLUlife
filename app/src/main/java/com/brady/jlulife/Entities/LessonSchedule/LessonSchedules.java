package com.brady.jlulife.Entities.LessonSchedule;

/**
 * Created by brady on 15-11-7.
 */
public class LessonSchedules {
    private Classroom classroom;
    private TimeBlock timeBlock;
    private String lsschId;

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public TimeBlock getTimeBlock() {
        return timeBlock;
    }

    public void setTimeBlock(TimeBlock timeBlock) {
        this.timeBlock = timeBlock;
    }

    public String getLsschId() {
        return lsschId;
    }

    public void setLsschId(String lsschId) {
        this.lsschId = lsschId;
    }
}
