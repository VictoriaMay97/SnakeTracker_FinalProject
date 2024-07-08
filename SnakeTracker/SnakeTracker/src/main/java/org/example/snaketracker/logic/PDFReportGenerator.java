package org.example.snaketracker.logic;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
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
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        String formattedDate = currentDate.format(formatter);
        fileChooser.setInitialFileName("Schlangenbericht_" + formattedDate + ".pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (PdfWriter writer = new PdfWriter(file);
                 PdfDocument pdf = new PdfDocument(writer);
                 Document document = new Document(pdf)) {

                Connection connection = DatabaseConnector.getConnection();

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
                mainTable.setMarginBottom(20);

                if (!imagePath.isEmpty()) {
                    Image image = new Image(ImageDataFactory.create(imagePath));
                    image.setHeight(140);
                    image.setWidth(140);
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

                mainTable.addCell(infoTable);
                document.add(mainTable);


                Table mealEntriesTable = createMealEntriesTable(connection, snakeID);
                document.add(mealEntriesTable.setMarginBottom(20));

                Table poopEntriesTable = createPoopEntriesTable(connection, snakeID);
                document.add(poopEntriesTable.setMarginBottom(20));

                String weightGraphPath = WeightGraphGenerator.generateWeightGraph(snakeID);
                if (weightGraphPath != null) {
                    Image graphImage = new Image(ImageDataFactory.create(weightGraphPath));
                    graphImage.setAutoScale(true);
                    document.add(graphImage.setMarginBottom(20));
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

    private static Table createPoopEntriesTable(Connection connection, int snakeID) throws SQLException {
        String poopSQL = "SELECT Date, Comment FROM poopentry WHERE SnakeID = ? ORDER BY Date DESC LIMIT 5";
        try (PreparedStatement poopStatement = connection.prepareStatement(poopSQL)) {
            poopStatement.setInt(1, snakeID);
            ResultSet poopResultSet = poopStatement.executeQuery();

            Table table = new Table(new float[]{1, 2});
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell(new Cell().add(new Paragraph("Datum").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Kommentar").setBold()));

            while (poopResultSet.next()) {
                table.addCell(new Cell().add(new Paragraph(poopResultSet.getDate("Date").toString())));
                table.addCell(new Cell().add(new Paragraph(poopResultSet.getString("Comment"))));
            }

            return table;
        }
    }

    private static Table createMealEntriesTable(Connection connection, int snakeID) throws SQLException {
        String mealSQL = "SELECT Date, TookFood, FoodType, Weight FROM mealentry WHERE SnakeID = ? ORDER BY Date DESC LIMIT 5";
        try (PreparedStatement mealStatement = connection.prepareStatement(mealSQL)) {
            mealStatement.setInt(1, snakeID);
            ResultSet mealResultSet = mealStatement.executeQuery();

            Table table = new Table(new float[]{1, 1, 1, 1});
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell(new Cell().add(new Paragraph("Datum").setBold().setTextAlignment(TextAlignment.CENTER)));
            table.addHeaderCell(new Cell().add(new Paragraph("Gefressen").setBold().setTextAlignment(TextAlignment.CENTER)));
            table.addHeaderCell(new Cell().add(new Paragraph("Art der Mahlzeit").setBold().setTextAlignment(TextAlignment.CENTER)));
            table.addHeaderCell(new Cell().add(new Paragraph("Gewicht(g)").setBold().setTextAlignment(TextAlignment.CENTER)));

            while (mealResultSet.next()) {
                LocalDate date = mealResultSet.getDate("Date").toLocalDate();
                boolean tookFood = mealResultSet.getBoolean("TookFood");
                String foodType = mealResultSet.getString("FoodType");
                int weight = mealResultSet.getInt("Weight");

                Cell cellDate = new Cell().add(new Paragraph(date.toString()).setTextAlignment(TextAlignment.CENTER));
                Cell cellTookFood = new Cell().add(new Paragraph(tookFood ? "Ja" : "Nein")).setTextAlignment(TextAlignment.CENTER);
                Cell cellFoodType = new Cell().add(new Paragraph(foodType)).setTextAlignment(TextAlignment.CENTER);
                Cell cellWeight = new Cell().add(new Paragraph(String.valueOf(weight))).setTextAlignment(TextAlignment.CENTER);

                table.addCell(cellDate);
                table.addCell(cellTookFood);
                table.addCell(cellFoodType);
                table.addCell(cellWeight);
            }

            return table;
        }
    }

}

