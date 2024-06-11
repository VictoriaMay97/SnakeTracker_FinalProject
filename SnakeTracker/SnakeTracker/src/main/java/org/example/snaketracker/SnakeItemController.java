package org.example.snaketracker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.example.snaketracker.database.DatabaseConnector;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SnakeItemController {

    @FXML
    private ImageView imageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label weightLabel;
    @FXML
    private Button chooseButton;

    private int snakeID;

    public void setSnakeData(int snakeID, String name, String type, double weight, String imagePath) {
        this.snakeID = snakeID;
        nameLabel.setText("Name: " + name);
        typeLabel.setText("Art: " + type);
        weightLabel.setText("Gewicht: " + weight + " kg");
        if (imagePath != null && !imagePath.isEmpty()) {
            imageView.setImage(new Image(getImagePath(imagePath)));
        }
        chooseButton.setOnAction(event -> {
            try {
                chooseSnake(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void chooseSnake(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/EditSnake.fxml"));
        Parent root = loader.load();

        EditSnakeController controller = loader.getController();
        controller.loadSnakeData(snakeID);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void deleteSnake(javafx.event.ActionEvent event) {
        deleteSnakeFromDatabase(snakeID);
        Node source = (Node) event.getSource();
        source.getParent().getParent().setVisible(false);
    }

    private void deleteSnakeFromDatabase(int snakeID) {
        String sql = "DELETE FROM snake WHERE SnakeID = ?";
        try (Connection conn = new DatabaseConnector().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, snakeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getImagePath(String imagePath){
        String filePath = new File("").getAbsolutePath();
        return filePath.concat(imagePath);

    }

}


