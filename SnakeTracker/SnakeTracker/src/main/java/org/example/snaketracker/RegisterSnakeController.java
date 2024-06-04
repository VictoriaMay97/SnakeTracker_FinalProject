package org.example.snaketracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.snaketracker.database.DatabaseConnector;
import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterSnakeController {

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
        String weightInput = weightBox.getText();

        if (isInputValid(snakeNameInput, snakeTypeInput, snakeSexInput, snakeBirthdateInput, snakeMorphInput, weightInput)) {
            int sex = "männlich".equals(snakeSexInput) ? 1 : 0;
            double weight = Double.parseDouble(weightInput);
            insertDataIntoDatabase(snakeNameInput, snakeTypeInput, sex, snakeBirthdateInput, snakeMorphInput, imagePath, weight);
        } else {
            // still needs Error Message
        }
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
        String snakeSql = "INSERT INTO snake (Name, Type, Sex, Birthdate, Morph, ImagePath) VALUES (?, ?, ?, ?, ?, ?)";
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
                File savedImagesDirectory = new File("savedImages");
                if (!savedImagesDirectory.exists()) {
                    savedImagesDirectory.mkdirs();
                }
                File destFile = new File(savedImagesDirectory, file.getName());
                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                imagePath = destFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
