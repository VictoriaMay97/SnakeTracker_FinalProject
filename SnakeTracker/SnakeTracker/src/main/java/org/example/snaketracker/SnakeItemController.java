package org.example.snaketracker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private Button editButton;

    @FXML
    private Button deleteButton;

    private int snakeID;

    public void setSnakeData(int snakeID, String name, String type, double weight, String imagePath) {
        this.snakeID = snakeID;
        nameLabel.setText("Name: " + name);
        typeLabel.setText("Type: " + type);
        weightLabel.setText("Weight: " + weight + " kg");
        if (imagePath != null && !imagePath.isEmpty()) {
            imageView.setImage(new Image(imagePath));
        }
        editButton.setOnAction(event -> editSnake());
        deleteButton.setOnAction(event -> deleteSnake());
    }

    private void editSnake() {
        // Implement the edit functionality
        System.out.println("Edit snake with ID: " + snakeID);
    }

    private void deleteSnake() {
        // Implement the delete functionality
        System.out.println("Delete snake with ID: " + snakeID);
    }
}
