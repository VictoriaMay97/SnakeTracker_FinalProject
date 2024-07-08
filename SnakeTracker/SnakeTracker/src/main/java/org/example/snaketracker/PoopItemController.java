package org.example.snaketracker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.snaketracker.database.DatabaseConnector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.example.snaketracker.EditSnakeController.getImagePath;

public class PoopItemController {

    @FXML
    private Label lastPoopDateLabel;
    @FXML
    private TextField commentField;
    @FXML
    private ImageView imageView;

    public static final String defaultPoopImagePath = "/savedImages/default/default_poop.jpg";
    private int snakeID;
    private String imagePath;


    public void initialize() {
    }

    public void setSnakeID(int snakeID) {
        this.snakeID = snakeID;
        loadLastPoopDate();
        try {
            imageView.setImage(new Image(getImagePath(defaultPoopImagePath)));
        } catch (Exception e) {
        }
    }

    private void loadLastPoopDate() {
        String sql = "SELECT Date FROM poopentry WHERE SnakeID = ? ORDER BY Date DESC LIMIT 1";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, snakeID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lastPoopDateLabel.setText(resultSet.getDate("Date").toString());
            } else {
                lastPoopDateLabel.setText("Keine Daten verfügbar.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Fehler beim Laden der letzten Daten.", AlertType.ERROR);
        }
    }

    @FXML
    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) imageView.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                File savedImagesDirectory = new File("/savedImages/poop");
                if (!savedImagesDirectory.exists()) {
                    savedImagesDirectory.mkdirs();
                }
                File destFile = new File(savedImagesDirectory, file.getName());
                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                imagePath = destFile.toString();
                imageView.setImage(new Image(new FileInputStream(imagePath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void addPoopEntry() {
        String comment = commentField.getText();

        if (comment.isEmpty() || imagePath == null) {
            showAlert("Bite füllen Sie alle Felder aus.", AlertType.WARNING);
            return;
        }

        String sql = "INSERT INTO poopentry (Date, SnakeID, ImagePath, Comment) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            statement.setInt(2, snakeID);
            statement.setString(3, imagePath);
            statement.setString(4, comment);
            statement.executeUpdate();

            showAlert("Koteintrag erfolgreich.", AlertType.INFORMATION);
            loadLastPoopDate();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Koteintrag fehlgeschlagen.", AlertType.ERROR);
        }
    }

    public static void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
