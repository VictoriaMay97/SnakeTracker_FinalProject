package org.example.snaketracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainMenuController {

    private static final String CSS_PATH = "styles.css";
    @FXML

    public void switchToRegisterSnake(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scenes/RegisterSnake.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(CSS_PATH);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void switchToManageSnake(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scenes/ManageSnake.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(CSS_PATH);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void exitProgram(ActionEvent event) {
        System.exit(0);
    }
}
