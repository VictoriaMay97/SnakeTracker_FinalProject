package org.example.snaketracker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import org.example.snaketracker.database.DatabaseConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

import static org.example.snaketracker.MainMenuController.CSS_PATH;

public class ManageSnakeController {

    @FXML
    private VBox snakeListVBox;
    @FXML
    private ScrollPane scrollPane;

    public void initialize() {
        loadSnakes();
        
    }

    private void loadSnakes() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            String query = "SELECT s.SnakeID, s.Name, st.Name AS Type, w.Weight, s.ImagePath " +
                    "FROM snake s " +
                    "JOIN snaketypes st ON s.Type = st.TypeID " +
                    "LEFT JOIN weightentry w ON s.SnakeID = w.SnakeID " +
                    "WHERE w.WeightID = (SELECT MAX(WeightID) FROM weightentry WHERE SnakeID = s.SnakeID)";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int snakeID = resultSet.getInt("SnakeID");
                String name = resultSet.getString("Name");
                String type = resultSet.getString("Type");
                double weight = resultSet.getDouble("Weight");
                String imagePath = resultSet.getString("ImagePath");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("items/SnakeItem.fxml"));
                Parent snakeItem = loader.load();

                SnakeItemController controller = loader.getController();
                controller.setSnakeData(snakeID, name, type, weight, imagePath);

                snakeListVBox.getChildren().add(snakeItem);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToMainMenu(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scenes/MainMenu.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(CSS_PATH);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
