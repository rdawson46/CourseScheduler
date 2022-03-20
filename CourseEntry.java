/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ryan Crooks
 */

public class CourseEntry {
    private final String semester;
    private final String courseCode;
    private final String description;
    private final int seats;

    public CourseEntry(String semester, String courseCode, String description, int seats) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.description = description;
        this.seats = seats;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getDescription() {
        return description;
    }

    public int getSeats() {
        return seats;
    }
    
    
}
