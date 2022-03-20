/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ryan Crooks
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addSchedule;
    private static PreparedStatement getSchedule;
    private static PreparedStatement getCount;
    private static PreparedStatement dropStudent;
    private static PreparedStatement dropCourse;
    private static PreparedStatement updateEntry;
    private static ResultSet resultSet;
    
    
    public static void addScheduleEntry(ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try{
            addSchedule = connection.prepareStatement("insert into app.schedule (semester, courseID, studentID, status, timestamp) values (?, ?, ?, ?, ?)");
            addSchedule.setString(1, entry.getSemester());
            addSchedule.setString(2, entry.getCourseCode());
            addSchedule.setString(3, entry.getStudentID());
            addSchedule.setString(4, entry.getStatus());
            addSchedule.setTimestamp(5, entry.getTimeStamp());
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList();
        
        try{
            getSchedule = connection.prepareStatement("select courseid, status, timestamp from app.schedule where semester = ? AND studentid = ? order by courseid");
            getSchedule.setString(1, semester);
            getSchedule.setString(2, studentID);
            resultSet = getSchedule.executeQuery();
            
            while(resultSet.next()){
                String courseID = resultSet.getString(1);
                String status = resultSet.getString(2);
                Timestamp timeStamp = resultSet.getTimestamp(3);
                schedule.add(new ScheduleEntry(semester, courseID, studentID, status, timeStamp));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
        
        return schedule;
    }
    
    public static int getScheduleByCount(String semester, String courseCode){
        connection = DBConnection.getConnection();
        int count = 0;
        
        try{
            getCount = connection.prepareStatement("select count(studentid) from app.schedule where semester = ? AND courseid = ?");
            getCount.setString(1, semester);
            getCount.setString(2, courseCode);
            resultSet = getCount.executeQuery();
            
            if (resultSet.next()){
                count = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return count;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> studentsByCourse = new ArrayList<>();
        
        try{
            getSchedule = connection.prepareStatement("select studentid, timestamp from app.schedule where semester = ? AND courseid = ? AND status = ? order by courseid");
            getSchedule.setString(1, semester);
            getSchedule.setString(2, courseCode);
            getSchedule.setString(3, "s");
            resultSet = getSchedule.executeQuery();
            
            while(resultSet.next()){
                String studentID = resultSet.getString(1);
                Timestamp timeStamp = resultSet.getTimestamp(2);
                studentsByCourse.add(new ScheduleEntry(semester, courseCode, studentID, "s", timeStamp));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return studentsByCourse;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseID){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> studentsByCourse = new ArrayList<>();
        
        try{
            getSchedule = connection.prepareStatement("select studentid, timestamp from app.schedule where semester = ? AND courseid = ? AND status = ? order by courseid");
            getSchedule.setString(1, semester);
            getSchedule.setString(2, courseID);
            getSchedule.setString(3, "w");
            resultSet = getSchedule.executeQuery();
            
            while(resultSet.next()){
                String studentID = resultSet.getString(1);
                Timestamp timeStamp = resultSet.getTimestamp(2);
                studentsByCourse.add(new ScheduleEntry(semester, courseID, studentID, "w", timeStamp));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return studentsByCourse;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
        connection = DBConnection.getConnection();
        
        try{
            dropStudent = connection.prepareStatement("delete from app.schedule where semester = ? AND studentid = ? AND courseid = ?");
            dropStudent.setString(1, semester);
            dropStudent.setString(2, studentID);
            dropStudent.setString(3, courseCode);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        
        try{
            dropCourse = connection.prepareStatement("delete from app.schedule where semester = ? AND courseid = ?");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry){
        connection = DBConnection.getConnection();
        
        try{
            updateEntry = connection.prepareStatement("update app.schedule set status = 's' where semester = ? AND courseid = ? AND  studentid = ?");
            updateEntry.setString(1, semester);
            updateEntry.setString(2, entry.getCourseCode());
            updateEntry.setString(3, entry.getStudentID());
            updateEntry.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
