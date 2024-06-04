package org.example.snaketracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.snaketracker.database.DatabaseConnector;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class ManageSnakeController {

    @FXML
    private VBox snakeListVBox;

    public void initialize() {
        loadSnakes();
    }

    private void loadSnakes() {
        try {
            DatabaseConnector databaseConnector = new DatabaseConnector();
            Connection connection = databaseConnector.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT s.SnakeID, s.Name, st.Name AS TypeName, we.Weight, s.ImagePath " +
                            "FROM snake s " +
                            "JOIN snaketypes st ON s.Type = st.TypeID " +
                            "JOIN weightentry we ON s.SnakeID = we.SnakeID"
            );

            while (resultSet.next()) {
                int snakeID = resultSet.getInt("SnakeID");
                String name = resultSet.getString("Name");
                String type = resultSet.getString("TypeName");
                double weight = resultSet.getDouble("Weight");
                String imagePath = resultSet.getString("ImagePath");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("SnakeItem.fxml"));
                Node snakeItemNode = loader.load();

                SnakeItemController controller = loader.getController();
                controller.setSnakeData(snakeID, name, type, weight, imagePath);

                snakeListVBox.getChildren().add(snakeItemNode);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
