package com.brady.jlulife.Entities.LessonSchedule;

/**
 * Created by brady on 15-11-7.
 */
public class Classroom {
    private int roomId;
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
