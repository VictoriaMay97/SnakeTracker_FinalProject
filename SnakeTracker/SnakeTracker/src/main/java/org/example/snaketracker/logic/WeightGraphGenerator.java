package org.example.snaketracker.logic;

import org.example.snaketracker.database.DatabaseConnector;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class WeightGraphGenerator {

    public static String generateWeightGraph(int snakeID) {
        String graphPath = "weight_graph.png";
        TimeSeries series = new TimeSeries("Gewicht");

        try (Connection connection = new DatabaseConnector().getConnection()) {
            String sql = "SELECT Date, Weight FROM weightentry WHERE SnakeID = ? ORDER BY Date";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, snakeID);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    LocalDate date = resultSet.getDate("Date").toLocalDate();
                    double weight = resultSet.getDouble("Weight");
                    series.add(new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()), weight);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection(series);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Gewichtszunahme",
                "Datum",
                "Gewicht (kg)",
                dataset,
                true,
                true,
                false
        );

        // Format the date axis to show only the date
        DateAxis axis = (DateAxis) chart.getXYPlot().getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));

        try {
            BufferedImage chartImage = chart.createBufferedImage(600, 400);
            File file = new File(graphPath);
            ChartUtils.saveChartAsPNG(file, chart, 500, 350);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return graphPath;
    }
}
