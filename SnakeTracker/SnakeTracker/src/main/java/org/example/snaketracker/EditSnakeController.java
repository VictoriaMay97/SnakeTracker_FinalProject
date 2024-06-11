package org.example.snaketracker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.snaketracker.database.DatabaseConnector;
import org.example.snaketracker.logic.PDFReportGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class EditSnakeController {
    private static final String CSS_PATH = "styles.css";
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

    private int snakeID;
    private String imagePath;
    private final Map<String, Integer> snakeTypeMap = new HashMap<>();

    public void initialize() {
        loadTypeComboBoxData();
    }

    public void loadSnakeData(int snakeID) {
        this.snakeID = snakeID;
        try {
            DatabaseConnector databaseConnector = new DatabaseConnector();
            Connection connection = databaseConnector.getConnection();
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
                    imageView.setImage(new Image(new FileInputStream(fullImagePath)));
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
        try {
            DatabaseConnector databaseConnector = new DatabaseConnector();
            Connection connection = databaseConnector.getConnection();
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
            connection.close();
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
            Parent root = FXMLLoader.load(getClass().getResource("scenes/ManageSnake.fxml"));
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
        try {
            DatabaseConnector databaseConnector = new DatabaseConnector();
            Connection connection = databaseConnector.getConnection();
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
    private void createPDF() {
        PDFReportGenerator.generatePdfReport((Stage) createPDFButton.getScene().getWindow(), snakeID);
    }
    private String getImagePath(String imagePath){
        String filePath = new File("").getAbsolutePath();
        return filePath.concat(imagePath);
    }
}
