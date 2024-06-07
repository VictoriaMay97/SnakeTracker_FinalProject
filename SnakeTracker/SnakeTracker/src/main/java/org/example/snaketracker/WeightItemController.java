package org.example.snaketracker;

import javafx.fxml.FXML;
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

public class WeightItemController {

    @FXML
    private Label lastWeightDateLabel;
    @FXML
    private TextField weightField;

    private int snakeID;

    public void initialize() {
    }

    public void setSnakeID(int snakeID) {
        this.snakeID = snakeID;
        loadLastWeightDate();
    }

    private void loadLastWeightDate() {
        String sql = "SELECT Date FROM weightentry WHERE SnakeID = ? ORDER BY Date DESC LIMIT 1";

        try (Connection connection = new DatabaseConnector().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, snakeID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lastWeightDateLabel.setText(resultSet.getDate("Date").toString());
            } else {
                lastWeightDateLabel.setText("Keine Daten verfügbar.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addWeightEntry() {
        String weightText = weightField.getText().replace(",", ".");

        if (weightText.isEmpty()) {
            showAlert("Bitte das Feld ausfüllen.", AlertType.WARNING);
            return;
        }

        double weightValue;
        try {
            weightValue = Double.parseDouble(weightText);
        } catch (NumberFormatException e) {
            showAlert("Bitte gültiges Gewicht eingeben.", AlertType.WARNING);
            return;
        }

        String sql = "INSERT INTO weightentry (Date, SnakeID, Weight) VALUES (?, ?, ?)";

        try (Connection connection = new DatabaseConnector().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            statement.setInt(2, snakeID);
            statement.setDouble(3, weightValue);
            statement.executeUpdate();

            showAlert("Gewicht erfolgreich eingetragen.", AlertType.INFORMATION);
            loadLastWeightDate();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Gewicht konnte nicht eingetragen werden.", AlertType.ERROR);
        }
    }

    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
