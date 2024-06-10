package org.example.snaketracker.logic;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.snaketracker.database.DatabaseConnector;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PDFReportGenerator {

    public static void generatePdfReport(Stage stage, int snakeID) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Snake_Report_" + snakeID + ".pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (PdfWriter writer = new PdfWriter(file);
                 PdfDocument pdf = new PdfDocument(writer);
                 Document document = new Document(pdf)) {

                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedDate = currentDate.format(formatter);

                DatabaseConnector databaseConnector = new DatabaseConnector();
                Connection connection = databaseConnector.getConnection();

                Text title = new Text("Schlangenbericht - " + formattedDate).setBold().setFontSize(18);
                Paragraph titleParagraph = new Paragraph(title).setMarginBottom(40);
                document.add(titleParagraph);

                String name = "", type = "", morph = "", imagePath = "";
                String sex = "Not specified";
                LocalDate birthdate = null;

                String snakeSQL = "SELECT * FROM snake WHERE SnakeID = ?";
                try (PreparedStatement snakeStatement = connection.prepareStatement(snakeSQL)) {
                    snakeStatement.setInt(1, snakeID);
                    ResultSet snakeResultSet = snakeStatement.executeQuery();

                    if (snakeResultSet.next()) {
                        name = snakeResultSet.getString("Name");
                        type = getTypeNameById(connection, snakeResultSet.getInt("Type"));
                        birthdate = snakeResultSet.getDate("Birthdate").toLocalDate();
                        morph = snakeResultSet.getString("Morph");
                        imagePath = snakeResultSet.getString("ImagePath");
                        String filePath = new File("").getAbsolutePath();
                        imagePath = filePath.concat(imagePath);
                        sex = snakeResultSet.getInt("Sex") == 1 ? "männlich" : "weiblich";
                    }
                }

                Table mainTable = new Table(new float[]{1, 2});
                mainTable.setWidth(UnitValue.createPercentValue(100));
                mainTable.setMarginBottom(30);

                if (imagePath != null && !imagePath.isEmpty()) {
                    Image image = new Image(ImageDataFactory.create(imagePath));
                    image.setHeight(180);
                    image.setWidth(180);
                    Paragraph imageParagraph = new Paragraph().add(image).setTextAlignment(TextAlignment.CENTER);
                    mainTable.addCell(imageParagraph);
                } else {
                    mainTable.addCell(new Paragraph("Kein Bild verfügbar"));
                }

                Table infoTable = new Table(new float[]{1, 1});
                infoTable.setWidth(UnitValue.createPercentValue(100));

                infoTable.addCell(new Paragraph("Name:"));
                infoTable.addCell(new Paragraph(name));
                infoTable.addCell(new Paragraph("Art:"));
                infoTable.addCell(new Paragraph(type));
                infoTable.addCell(new Paragraph("Geburtsdatum:"));
                infoTable.addCell(new Paragraph(birthdate != null ? birthdate.toString() : "Keine Daten"));
                infoTable.addCell(new Paragraph("Geschlecht:"));
                infoTable.addCell(new Paragraph(sex));
                infoTable.addCell(new Paragraph("Gewicht:"));
                infoTable.addCell(new Paragraph(getLastWeight(connection, snakeID)));
                infoTable.addCell(new Paragraph("Morphe:"));
                infoTable.addCell(new Paragraph(morph));
                infoTable.addCell(new Paragraph("Letzter Kot:"));
                infoTable.addCell(new Paragraph(getLastPoopDate(connection, snakeID)));
                infoTable.addCell(new Paragraph("Letzte Fütterung:"));
                infoTable.addCell(new Paragraph(getLastMealDate(connection, snakeID)));

                mainTable.addCell(infoTable);
                document.add(mainTable);

                String graphPath = WeightGraphGenerator.generateWeightGraph(snakeID);
                if (graphPath != null) {
                    Image graphImage = new Image(ImageDataFactory.create(graphPath));
                    graphImage.setAutoScale(true);
                    document.add(graphImage.setMarginTop(20));
                }

                connection.close();

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private static String getTypeNameById(Connection connection, int typeId) throws SQLException {
        String sql = "SELECT Name FROM snaketypes WHERE TypeID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, typeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("Name");
            }
        }
        return "Unknown";
    }

    private static String getLastWeight(Connection connection, int snakeID) throws SQLException {
        String lastWeightSQL = "SELECT Weight, Date FROM weightentry WHERE SnakeID = ? ORDER BY Date DESC LIMIT 1";
        try (PreparedStatement lastWeightStatement = connection.prepareStatement(lastWeightSQL)) {
            lastWeightStatement.setInt(1, snakeID);
            ResultSet lastWeightResultSet = lastWeightStatement.executeQuery();

            if (lastWeightResultSet.next()) {
                return lastWeightResultSet.getDouble("Weight") + " kg on " + lastWeightResultSet.getDate("Date").toString();
            } else {
                return "Keine Daten vorhanden.";
            }
        }
    }

    private static String getLastPoopDate(Connection connection, int snakeID) throws SQLException {
        String lastPoopSQL = "SELECT Date FROM poopentry WHERE SnakeID = ? ORDER BY Date DESC LIMIT 1";
        try (PreparedStatement lastPoopStatement = connection.prepareStatement(lastPoopSQL)) {
            lastPoopStatement.setInt(1, snakeID);
            ResultSet lastPoopResultSet = lastPoopStatement.executeQuery();

            if (lastPoopResultSet.next()) {
                return lastPoopResultSet.getDate("Date").toString();
            } else {
                return "Keine Daten vorhanden.";
            }
        }
    }

    private static String getLastMealDate(Connection connection, int snakeID) throws SQLException {
        String lastMealSQL = "SELECT Date FROM mealentry WHERE SnakeID = ? ORDER BY Date DESC LIMIT 1";
        try (PreparedStatement lastMealStatement = connection.prepareStatement(lastMealSQL)) {
            lastMealStatement.setInt(1, snakeID);
            ResultSet lastMealResultSet = lastMealStatement.executeQuery();

            if (lastMealResultSet.next()) {
                return lastMealResultSet.getDate("Date").toString();
            } else {
                return "Keine Daten vorhanden.";
            }
        }
    }

}
