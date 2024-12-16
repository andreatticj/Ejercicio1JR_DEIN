module eu.andreatt.ejercicio1jr_dein {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires net.sf.jasperreports.core;

    requires slf4j.api;
    requires slf4j.simple;


    exports eu.andreatt.ejercicio1jr_dein.application;
    opens eu.andreatt.ejercicio1jr_dein.application to javafx.fxml;
}