
package com.studentmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:h2:~/studentdb";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void initializeDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS students (
              id INT AUTO_INCREMENT PRIMARY KEY,
              name VARCHAR(100) NOT NULL,
              age INT NOT NULL
            )
        """;
        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
            System.out.println("Database initialized.");
        } catch (SQLException e) {
            System.err.println("Init DB failed: " + e.getMessage());
        }
    }
}
