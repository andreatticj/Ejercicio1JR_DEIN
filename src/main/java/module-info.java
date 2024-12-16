module eu.andreatt.ejercicio1jr_dein {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires net.sf.jasperreports.core;

    opens eu.andreatt.ejercicio1jr_dein.application to javafx.fxml;
    exports eu.andreatt.ejercicio1jr_dein.application;
}