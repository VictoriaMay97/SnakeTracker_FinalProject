module org.example.snaketracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires mysql.connector.j;
    requires layout;
    requires kernel;
    requires io;
    requires java.desktop;
    requires org.jfree.jfreechart;

    opens org.example.snaketracker to javafx.fxml;
    exports org.example.snaketracker;
}