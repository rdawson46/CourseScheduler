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


public class StudentQueries {
    
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student){
        connection = DBConnection.getConnection();
        
        try{
            addStudent = connection.prepareStatement("insert into app.student (studentID, firstName, LastName) values (?, ?, ?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
            
        }
        catch(SQLException sqlexception){
            sqlexception.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getAllStudents(){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<>();
        
        try{
            getAllStudents = connection.prepareStatement("select * from app.student order by studentID");
            resultSet = getAllStudents.executeQuery();
            
            while (resultSet.next()){
                String id = resultSet.getString(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                
                StudentEntry student = new StudentEntry(id, firstName, lastName);
                
                students.add(student);
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return students;
    }
    
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        
        try{
            getStudent = connection.prepareStatement("select * from app.student where studentid = ?");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            
            String id = "";
            String firstName = "";
            String lastName = "";
            
            while (resultSet.next()){
                id = resultSet.getString(1);
                firstName = resultSet.getString(2);
                lastName = resultSet.getString(3);
            }
            
            return new StudentEntry(id, firstName, lastName);
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return new StudentEntry("","","");
    }
    
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        
        try{
            dropStudent = connection.prepareStatement("delete from app.student where studentid = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
