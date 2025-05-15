package se.lexicon.db;

import java.sql.*;

public class MySQLConnection {

    //fields
    private static final String URL = "jdbc:mysql://localhost:3306/todoit";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}