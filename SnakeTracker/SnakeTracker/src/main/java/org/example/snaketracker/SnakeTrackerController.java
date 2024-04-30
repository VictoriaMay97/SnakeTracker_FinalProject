package org.example.snaketracker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.snaketracker.database.DatabaseConnector;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SnakeTrackerController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT UserName FROM UserAccount";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()){
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}