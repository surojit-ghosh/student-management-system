package com.capgemini.dao;

import com.capgemini.exception.InvalidStudentDataException;
import com.capgemini.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLStudentDAO implements StudentDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    public void addStudent(Student student) throws InvalidStudentDataException {
        validateStudent(student);

        String sql = "INSERT INTO students (name, email, age, mobile) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getMobile());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✓ Student added successfully to MySQL!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("mobile"));
                students.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return students;
    }

    @Override
    public void updateStudent(String mobile, String email)  {
        String sql = "UPDATE students SET email=?, WHERE mobile=?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, mobile);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Student updated successfully in MySQL!");
            } else {
                System.out.println("No student found with mobile: " + mobile);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✓ Student deleted successfully from MySQL!");
            } else {
                System.out.println("No student found with ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
