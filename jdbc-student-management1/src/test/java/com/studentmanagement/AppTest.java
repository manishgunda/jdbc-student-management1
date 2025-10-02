
package com.studentmanagement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class AppTest {
    private static StudentDAO dao;

    @BeforeClass
    public static void setup() {
        // Initialize database and DAO
        DBConnection.initializeDatabase();
        dao = new StudentDAO();
    }

    @AfterClass
    public static void cleanup() throws SQLException {
        // Drop the table after tests
        try (Connection conn = DBConnection.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE students");
        }
    }

    @Test
    public void testAddAndGetAllStudents() {
        // Ensure starting with empty table
        List<Student> before = dao.getAllStudents();
        int initialSize = before.size();

        // Add a student
        Student s = new Student(0, "Test User", 30);
        assertTrue(dao.addStudent(s));

        // Verify size increased by 1
        List<Student> after = dao.getAllStudents();
        assertEquals(initialSize + 1, after.size());

        // Verify the added student’s details
        Student added = after.get(after.size() - 1);
        assertEquals("Test User", added.getName());
        assertEquals(30, added.getAge());
    }

    @Test
    public void testUpdateStudent() {
        // Add a student to update
        Student s = new Student(0, "Update User", 25);
        assertTrue(dao.addStudent(s));
        List<Student> list = dao.getAllStudents();
        Student toUpdate = list.get(list.size() - 1);

        // Update details
        toUpdate.setName("Updated Name");
        toUpdate.setAge(26);
        assertTrue(dao.updateStudent(toUpdate));

        // Fetch again and verify
        Student updated = dao.getAllStudents()
                             .stream()
                             .filter(st -> st.getId() == toUpdate.getId())
                             .findFirst()
                             .orElse(null);
        assertNotNull(updated);
        assertEquals("Updated Name", updated.getName());
        assertEquals(26, updated.getAge());
    }

    @Test
    public void testDeleteStudent() {
        // Add a student to delete
        Student s = new Student(0, "Delete User", 40);
        assertTrue(dao.addStudent(s));
        List<Student> list = dao.getAllStudents();
        int idToDelete = list.get(list.size() - 1).getId();

        // Delete and verify
        assertTrue(dao.deleteStudent(idToDelete));
        boolean exists = dao.getAllStudents()
                            .stream()
                            .anyMatch(st -> st.getId() == idToDelete);
        assertFalse(exists);
    }
}
