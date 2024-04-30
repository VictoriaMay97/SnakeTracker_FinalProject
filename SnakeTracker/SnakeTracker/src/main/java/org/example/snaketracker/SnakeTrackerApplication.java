package org.example.snaketracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeTrackerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SnakeTrackerApplication.class.getResource("mainMenu.fxml"));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("mainMenu.fxml")));
        stage.setTitle("SnakeTracker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}