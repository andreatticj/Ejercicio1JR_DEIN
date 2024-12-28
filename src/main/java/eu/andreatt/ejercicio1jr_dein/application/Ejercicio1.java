package eu.andreatt.ejercicio1jr_dein.application;

import eu.andreatt.ejercicio1jr_dein.bbdd.ConexionBD;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Ejercicio1 extends Application {

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        ConexionBD db;
        try {
            db = new ConexionBD();

            // Cargar el reporte desde la ruta
            InputStream reportStream = getClass().getResourceAsStream("/eu/andreatt/ejercicio1jr_dein/informes/ejercicio1.jasper");

            // Verifica si el archivo .jasper está presente
            if (reportStream == null) {
                throw new RuntimeException("El archivo .jasper no se encuentra en la ruta especificada.");
            }

            // Cargar el reporte de Jasper
            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);

            // Parámetros del reporte
            Map<String, Object> parameters = new HashMap<>();
            String imageBasePath = getClass().getResource("/eu/andreatt/ejercicio1jr_dein/imagenes/").toString();
            parameters.put("REPORT_IMAGE", imageBasePath);

            // Mostrar la ruta de la imagen en la consola para depuración
            System.out.println("Ruta base de la imagen: " + imageBasePath);

            // Llenar el reporte con los datos de la base de datos
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, db.getConexion());

            // Mostrar el reporte en una vista de Jasper
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorDialog("Error de conexión con la base de datos: " + e.getMessage());
        } catch (JRException e) {
            e.printStackTrace();
            showErrorDialog("Error al generar el reporte Jasper: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Error inesperado: " + e.getMessage());
        }
    }

    private void showErrorDialog(String message) {
        // Mostrar un diálogo de error en el hilo de la aplicación JavaFX
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("ERROR");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
