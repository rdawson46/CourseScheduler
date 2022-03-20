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


public class CourseQueries {
    
    private static Connection connection;
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement getCourseCodeList;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement dropCourse;
    private static ResultSet resultSet;
    
    public static ArrayList<CourseEntry> getAllCourses(String semester){
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList();
        
        try{
            getAllCourses = connection.prepareStatement("select courseid, description, seats from app.course where semester = ? order by courseid");
            getAllCourses.setString(1, semester);
            resultSet = getAllCourses.executeQuery();
            
            while(resultSet.next()){
                String courseID = resultSet.getString(1);
                String description = resultSet.getString(2);
                int seats = resultSet.getInt(3);
                
                CourseEntry course = new CourseEntry(semester, courseID, description, seats);
                courses.add(course);
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
        return courses;
    }
    
    public static void addCourse(CourseEntry course){
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (semester, courseID, description, seats) values (?, ?, ?, ?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<String> getAllCourseCodes(){
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<>();
        
        try{
            getCourseCodeList = connection.prepareStatement("select courseCode from app.course order by courseCode");
            resultSet = getCourseCodeList.executeQuery();
            
            while(resultSet.next()){
                courseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return courseCodes;
    }
    
    public static int getCourseSeats(String semester, String courseCode){
        connection = DBConnection.getConnection();
        int seats = 0;
        try{
            getCourseSeats = connection.prepareStatement("select seats from app.course where semester = ? AND courseid = ?");
            getCourseSeats.setString(1, semester);
            getCourseSeats.setString(2, courseCode);
            resultSet = getCourseSeats.executeQuery();
            
            if (resultSet.next()){
                seats = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return seats;
    }
    
    public static void dropCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        
        try{
            dropCourse = connection.prepareStatement("delete from app.course where semester = ? AND courseid = ?");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
