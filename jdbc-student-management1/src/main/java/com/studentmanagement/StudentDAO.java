package com.studentmanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public boolean addStudent(Student s) {
        String sql = "INSERT INTO students(name,age) VALUES(?,?)";
        try (var c = DBConnection.getConnection();
             var p = c.prepareStatement(sql)) {
            p.setString(1, s.getName());
            p.setInt(2, s.getAge());
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Add failed: " + e.getMessage());
            return false;
        }
    }

    public List<Student> getAllStudents() {
        var list = new ArrayList<Student>();
        String sql = "SELECT * FROM students";
        try (var c = DBConnection.getConnection();
             var stmt = c.createStatement();
             var rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Student(
                  rs.getInt("id"),
                  rs.getString("name"),
                  rs.getInt("age")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Fetch failed: " + e.getMessage());
        }
        return list;
    }

    public boolean updateStudent(Student s) {
        String sql = "UPDATE students SET name=?,age=? WHERE id=?";
        try (var c = DBConnection.getConnection();
             var p = c.prepareStatement(sql)) {
            p.setString(1, s.getName());
            p.setInt(2, s.getAge());
            p.setInt(3, s.getId());
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update failed: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (var c = DBConnection.getConnection();
             var p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete failed: " + e.getMessage());
            return false;
        }
    }
}
