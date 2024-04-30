package org.example.snaketracker;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchSceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToMainMenu(ActionEvent event) throws IOException {
      root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
      stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }

    public void switchToRegisterSnake(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("registerSnake.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToManageSnake(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("manageSnake.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exitProgramm(ActionEvent event) throws IOException{
        System.exit(0);
    }
}

