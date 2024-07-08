package org.example.snaketracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static org.example.snaketracker.MainMenuController.CSS_PATH;

public class SnakeTrackerApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("scenes/MainMenu.fxml")));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(CSS_PATH);
        stage.setTitle("SnakeTracker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
