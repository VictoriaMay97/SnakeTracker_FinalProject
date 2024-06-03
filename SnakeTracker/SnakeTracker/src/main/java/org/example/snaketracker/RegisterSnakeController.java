package org.example.snaketracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.snaketracker.database.DatabaseConnector;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
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

    private final Map<String, Integer> snakeTypeMap = new HashMap<>();

    public void initialize() {
        loadTypeComboBoxData();
        loadSexComboBoxData();
    }

    private void loadTypeComboBoxData() {
        try {
            DatabaseConnector databaseConnector = new DatabaseConnector();
            Connection connection = databaseConnector.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, Name FROM snaketypes");

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                int id = resultSet.getInt("ID");
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

        if (isInputValid(snakeNameInput, snakeTypeInput, snakeSexInput, snakeBirthdateInput, snakeMorphInput)) {
            boolean sex = "Male".equals(snakeSexInput);
            insertDataIntoDatabase(snakeNameInput, snakeTypeInput, String.valueOf(sex), snakeBirthdateInput, snakeMorphInput);
        } else {
            // need to put invalid message
        }
    }

    private boolean isInputValid(String name, String type, String sex, LocalDate birthdate, String morph) {
        return name != null && !name.isEmpty() &&
                type != null && !type.isEmpty() &&
                sex != null && !sex.isEmpty() &&
                birthdate != null &&
                morph != null && !morph.isEmpty();
    }

    private void insertDataIntoDatabase(String name, String type, String sex, LocalDate birthdate, String morph) {
        String sql = "INSERT INTO snake (Name, Type, Sex, Birthdate, Morph) VALUES (?, ?, ?, ?, ?)";

        try {
            DatabaseConnector databaseConnector = new DatabaseConnector();
            Connection connection = databaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, sex);
            preparedStatement.setDate(4, Date.valueOf(birthdate));
            preparedStatement.setString(5, morph);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
