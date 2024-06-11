package org.example.snaketracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.snaketracker.database.DatabaseConnector;
import javafx.fxml.FXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterSnakeController {

    private static final String CSS_PATH = "styles.css";

    @FXML
    private ComboBox<String> snakeTypeComboBox;
    @FXML
    private TextField snakeName;
    @FXML
    private ComboBox<String> chooseSexBox;
    @FXML
    private DatePicker chooseBirthdateBox;
    @FXML
    private TextField morphBox;
    @FXML
    private TextField weightBox;
    @FXML
    private ImageView snakeImage;

    private final Map<String, Integer> snakeTypeMap = new HashMap<>();
    private String imagePath;

    public void initialize() {
        loadTypeComboBoxData();
        loadSexComboBoxData();
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

    private void loadSexComboBoxData() {
        chooseSexBox.getItems().addAll("männlich", "weiblich");
    }

    @FXML
    private void submitData() {
        String snakeNameInput = snakeName.getText();
        String snakeTypeInput = snakeTypeComboBox.getValue();
        String snakeSexInput = chooseSexBox.getValue();
        LocalDate snakeBirthdateInput = chooseBirthdateBox.getValue();
        String snakeMorphInput = morphBox.getText();
        String weightInput = weightBox.getText().replace(",", ".");

        if (isInputValid(snakeNameInput, snakeTypeInput, snakeSexInput, snakeBirthdateInput, snakeMorphInput, weightInput)) {
            int sex = "männlich".equals(snakeSexInput) ? 1 : 0;
            double weight = Double.parseDouble(weightInput);
            insertDataIntoDatabase(snakeNameInput, snakeTypeInput, sex, snakeBirthdateInput, snakeMorphInput, imagePath, weight);
            showAlert("Erfolg!","Schlange angelegt.", Alert.AlertType.INFORMATION);

        } else {
            showAlert("Eingabefehler", "Bitte alle Felder korrekt ausfüllen.", Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean isInputValid(String name, String type, String sex, LocalDate birthdate, String morph, String weight) {
        return name != null && !name.isEmpty() &&
                type != null && !type.isEmpty() &&
                sex != null && !sex.isEmpty() &&
                birthdate != null &&
                morph != null && !morph.isEmpty() &&
                weight != null && !weight.isEmpty() &&
                imagePath != null && !imagePath.isEmpty();
    }

    private void insertDataIntoDatabase(String name, String typeName, Integer sex, LocalDate birthdate, String morph, String imagePath, double weight) {
        String snakeSql = "INSERT INTO snake (Name, Type, Sex, Birthdate, Morph, ImagePath, LastWeightID) VALUES (?, ?, ?, ?, ?, ?)";
        String weightSql = "INSERT INTO weightentry (Date, SnakeID, Weight) VALUES (?, ?, ?)";

        try {
            DatabaseConnector databaseConnector = new DatabaseConnector();
            Connection connection = databaseConnector.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement snakeStmt = connection.prepareStatement(snakeSql, Statement.RETURN_GENERATED_KEYS);
            int typeID = snakeTypeMap.get(typeName);
            snakeStmt.setString(1, name);
            snakeStmt.setInt(2, typeID);
            snakeStmt.setInt(3, sex);
            snakeStmt.setDate(4, Date.valueOf(birthdate));
            snakeStmt.setString(5, morph);
            snakeStmt.setString(6, imagePath);
            snakeStmt.executeUpdate();

            ResultSet generatedKeys = snakeStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int snakeID = generatedKeys.getInt(1);

                PreparedStatement weightStmt = connection.prepareStatement(weightSql);
                weightStmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                weightStmt.setInt(2, snakeID);
                weightStmt.setDouble(3, weight);
                weightStmt.executeUpdate();
                weightStmt.close();
            }

            snakeStmt.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                String savedImagesPath = "/savedImages/snakes";
                imagePath = savedImagesPath.concat("/").concat(file.getName());
                String absolutePath = (new File("").getAbsolutePath());
                savedImagesPath = absolutePath.concat(savedImagesPath);
                File savedImagesDirectory = new File(savedImagesPath);

                if (!savedImagesDirectory.exists()) {
                    savedImagesDirectory.mkdirs();
                }
                File destFile = new File(savedImagesDirectory, file.getName());
                System.out.println(destFile);
                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                Image image = new Image(destFile.toURI().toString());
                snakeImage.setImage(image);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scenes/MainMenu.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(CSS_PATH);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private String getImagePath(String imagePath){
        String filePath = new File("").getAbsolutePath();
        return filePath.concat(imagePath);

    }

}
