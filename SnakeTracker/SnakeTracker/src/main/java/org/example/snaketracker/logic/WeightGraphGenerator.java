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
import java.util.LinkedList;

public class WeightGraphGenerator {

    public static String generateWeightGraph(int snakeID) {
        String weightGraphPath = "weight_graph.png";
        TimeSeries series = new TimeSeries("Gewicht");

        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT Date, Weight FROM weightentry WHERE SnakeID = ? ORDER BY Date DESC LIMIT 5";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, snakeID);
                ResultSet resultSet = statement.executeQuery();

                LinkedList<LocalDate> dates = new LinkedList<>();
                LinkedList<Double> weights = new LinkedList<>();

                while (resultSet.next()) {
                    dates.addFirst(resultSet.getDate("Date").toLocalDate());
                    weights.addFirst(resultSet.getDouble("Weight"));
                }

                for (int i = 0; i < dates.size(); i++) {
                    series.add(new Day(dates.get(i).getDayOfMonth(), dates.get(i).getMonthValue(), dates.get(i).getYear()), weights.get(i));
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

        DateAxis axis = (DateAxis) chart.getXYPlot().getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));

        try {
            BufferedImage chartImage = chart.createBufferedImage(600, 400);
            File file = new File(weightGraphPath);
            ChartUtils.saveChartAsPNG(file, chart, 500, 350);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return weightGraphPath;
    }
}
