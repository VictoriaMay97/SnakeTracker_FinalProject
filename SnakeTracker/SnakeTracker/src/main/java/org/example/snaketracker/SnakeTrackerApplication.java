package org.example.snaketracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.snaketracker.database.DatabaseConnector;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;

public class SnakeTrackerApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SnakeTrackerApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml"))));
        stage.setTitle("SnakeTracker");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}