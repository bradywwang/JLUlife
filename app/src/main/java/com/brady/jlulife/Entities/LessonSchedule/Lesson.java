package com.brady.jlulife.Entities.LessonSchedule;

/**
 * Created by brady on 15-11-7.
 */
public class Lesson {
    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    private CourseInfo courseInfo;
}
