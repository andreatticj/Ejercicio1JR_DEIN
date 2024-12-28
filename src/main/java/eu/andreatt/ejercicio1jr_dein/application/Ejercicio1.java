package eu.andreatt.ejercicio1jr_dein.application;

import eu.andreatt.ejercicio1jr_dein.bbdd.ConexionBD;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.util.HashMap;

public class Ejercicio1 extends Application {

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Open the database connection
            ConexionBD con = new ConexionBD();

            // Parameters for the report
            HashMap<String, Object> parameters = new HashMap<>();

            // Load the report file
            InputStream reportStream = getClass().getResourceAsStream("/eu/andreatt/ejercicio1jr_dein/informes/ejercicio1.jasper");
            if (reportStream == null) {
                throw new RuntimeException("El archivo .jasper no se encuentra en la ruta especificada.");
            }

            // Load and compile the Jasper report
            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);

            // Fill the report with data
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, con.getConexion());

            // Show the report viewer
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception for debugging
            showErrorDialog("Error: " + e.getMessage());
        }
    }


    private void showErrorDialog(String message) {
        // Show an error dialog on the JavaFX Application Thread
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("ERROR");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
