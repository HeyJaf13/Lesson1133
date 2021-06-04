package jm.task.core.jdbc.util;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {



    private static final String URL = "jdbc:mysql://localhost:3306/lesson1113?verifyServerCertificate=false&useSSL=false&requireSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    public static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

