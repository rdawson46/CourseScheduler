
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ryan Crooks
 */
public class ScheduleEntry {
    private final String semester;
    private final String courseCode;
    private final String studentID;
    private final String status;
    private final Timestamp timeStamp;

    public ScheduleEntry(String semester, String courseCose, String studentID, String status, Timestamp timeStamp) {
        this.semester = semester;
        this.courseCode = courseCose;
        this.studentID = studentID;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }
    
}
