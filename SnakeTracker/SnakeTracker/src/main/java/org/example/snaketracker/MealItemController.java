package org.example.snaketracker;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.snaketracker.database.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MealItemController {

    @FXML
    private Label lastMealDateLabel;
    @FXML
    private ComboBox<String> tookFoodComboBox;
    @FXML
    private TextField foodTypeField;
    @FXML
    private TextField foodWeightField;

    private int snakeID;

    public void initialize() {
        tookFoodComboBox.setItems(FXCollections.observableArrayList("Ja", "Nein"));
    }

    public void setSnakeID(int snakeID) {
        this.snakeID = snakeID;
        loadLastMealDate();
    }

    private void loadLastMealDate() {
        String sql = "SELECT Date FROM mealentry WHERE SnakeID = ? ORDER BY Date DESC LIMIT 1";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, snakeID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lastMealDateLabel.setText(resultSet.getDate("Date").toString());
            } else {
                lastMealDateLabel.setText("Keine Daten verfügbar.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Fehler beim Laden der letzten Daten.", AlertType.ERROR);
        }
    }

    @FXML
    private void addMealEntry() {
        String tookFood = tookFoodComboBox.getValue();
        String foodType = foodTypeField.getText();
        String foodWeight = foodWeightField.getText();

        if (tookFood == null || foodType.isEmpty() || foodWeight.isEmpty()) {
            showAlert("Bite füllen Sie alle Felder aus.", AlertType.WARNING);
            return;
        }

        int tookFoodValue = tookFood.equals("Yes") ? 0 : 1;
        int foodWeightValue;

        try {
            foodWeightValue = Integer.parseInt(foodWeight);
        } catch (NumberFormatException e) {
            showAlert("Bite gültiges Gewicht eingeben.", AlertType.WARNING);
            return;
        }

        String sql = "INSERT INTO mealentry (Date, SnakeID, TookFood, FoodType, Weight) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            statement.setInt(2, snakeID);
            statement.setInt(3, tookFoodValue);
            statement.setString(4, foodType);
            statement.setInt(5, foodWeightValue);
            statement.executeUpdate();

            showAlert("Fütterung erfolgreich eingetragen.", AlertType.INFORMATION);
            loadLastMealDate();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Fütterung konnte nicht eingetragen werden.", AlertType.ERROR);
        }
    }

    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
