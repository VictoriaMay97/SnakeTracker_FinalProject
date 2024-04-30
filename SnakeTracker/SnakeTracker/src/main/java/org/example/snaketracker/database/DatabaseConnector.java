package org.example.snaketracker.database;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class DatabaseConnector {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "snaketracker";
        String databaseUser = "root";
        String databasePassword = "Password";
        String url = "jdbc:mysql://lcoalhost/" + databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser,databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;

    }
}
