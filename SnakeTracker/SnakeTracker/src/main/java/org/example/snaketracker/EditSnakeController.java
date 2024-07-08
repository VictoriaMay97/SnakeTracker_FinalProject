package org.example.snaketracker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.snaketracker.database.DatabaseConnector;
import org.example.snaketracker.logic.PDFReportGenerator;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.example.snaketracker.MainMenuController.CSS_PATH;
import static org.example.snaketracker.PoopItemController.showAlert;

public class EditSnakeController {

    @FXML
    private ImageView imageView;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> snakeTypeComboBox;
    @FXML
    private DatePicker birthdatePicker;
    @FXML
    private TextField morphField;
    @FXML
    private VBox weightVBox;
    @FXML
    private VBox poopVBox;
    @FXML
    private VBox mealVBox;
    @FXML
    private Button backButton;
    @FXML
    private Button createPDFButton;

    public final static String defaultSnakeImagePath = "/savedImages/default/default_schlange.jpg";
    private int snakeID;
    private String imagePath;
    private final Map<String, Integer> snakeTypeMap = new HashMap<>();

    public void initialize() {
        loadTypeComboBoxData();
    }

    public void loadSnakeData(int snakeID) {
        this.snakeID = snakeID;
        try (Connection connection = DatabaseConnector.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM snake WHERE SnakeID = " + snakeID);

            if (resultSet.next()) {
                nameField.setText(resultSet.getString("Name"));
                String type = getTypeNameById(resultSet.getInt("Type"));
                snakeTypeComboBox.setValue(type);
                birthdatePicker.setValue(resultSet.getDate("Birthdate").toLocalDate());
                morphField.setText(resultSet.getString("Morph"));
                imagePath = resultSet.getString("ImagePath");

                if (imagePath != null && !imagePath.isEmpty()) {
                    String fullImagePath = getImagePath(imagePath);
                    String fullDefaultSnakePath = getImagePath(defaultSnakeImagePath);
                    try {
                        imageView.setImage(new Image(new FileInputStream(fullImagePath)));

                    } catch (Exception e) {
                        try {
                            imageView.setImage(new Image(new FileInputStream(fullDefaultSnakePath)));

                        } catch (Exception e2) {
                        }
                    }
                }
            }

            loadWeightItems();
            loadPoopItems();
            loadMealItems();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadTypeComboBoxData() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT TypeID, Name FROM snaketypes");

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                int id = resultSet.getInt("TypeID");
                snakeTypeComboBox.getItems().add(name);
                snakeTypeMap.put(name, id);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getTypeNameById(int typeId) {
        for (Map.Entry<String, Integer> entry : snakeTypeMap.entrySet()) {
            if (entry.getValue().equals(typeId)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void loadWeightItems() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("items/WeightItem.fxml"));
            Parent weightItem = loader.load();
            weightVBox.getChildren().add(weightItem);
            WeightItemController controller = loader.getController();
            controller.setSnakeID(snakeID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPoopItems() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("items/PoopItem.fxml"));
            Parent poopItem = loader.load();
            poopVBox.getChildren().add(poopItem);
            PoopItemController controller = loader.getController();
            controller.setSnakeID(snakeID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMealItems() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("items/MealItem.fxml"));
            Parent mealItem = loader.load();
            mealVBox.getChildren().add(mealItem);
            MealItemController controller = loader.getController();
            controller.setSnakeID(snakeID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void replaceImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) imageView.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            imagePath = file.getAbsolutePath();
            try {
                imageView.setImage(new Image(new FileInputStream(imagePath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scenes/ManageSnake.fxml")));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            root.getStylesheets().add(CSS_PATH);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void submit() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String updateSQL = "UPDATE snake SET Name = ?, Type = ?, Birthdate = ?, Morph = ?, ImagePath = ? WHERE SnakeID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, nameField.getText());
            preparedStatement.setInt(2, snakeTypeMap.get(snakeTypeComboBox.getValue()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(birthdatePicker.getValue()));
            preparedStatement.setString(4, morphField.getText());
            preparedStatement.setString(5, imagePath);
            preparedStatement.setInt(6, snakeID);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            goBack();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openDefaultMailApp() {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.MAIL)) {
                try {
                    desktop.mail();
                } catch (IOException | UnsupportedOperationException | SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Mail-Funktion wird nicht unterstützt!", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Desktop wird nicht unterstützt!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void createPDF() {
        PDFReportGenerator.generatePdfReport((Stage) createPDFButton.getScene().getWindow(), snakeID);
    }

    public static String getImagePath(String imagePath) {
        String filePath = new File("").getAbsolutePath();
        return filePath.concat(imagePath);
    }
}
