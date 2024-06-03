package org.example.snaketracker.database;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class DatabaseConnector {
    public static Connection databaseLink;

    public Connection getConnection(){
        final String databaseUser = "root";
        final String databasePassword = "Password";
        final String url = "jdbc:mysql://localhost:3306/snaketracker";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser,databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;

    }
}
