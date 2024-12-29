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

/**
 * Clase principal de la aplicación que extiende {@link javafx.application.Application}.
 * Esta clase genera un reporte utilizando JasperReports y se conecta a una base de datos para rellenar los datos.
 */
public class Ejercicio1 extends Application {

    /**
     * Función principal de la aplicación.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args); // Lanza la aplicación JavaFX
    }

    /**
     * Función sobrescrita de la clase {@link javafx.application.Application}.
     * Inicializa la conexión a la base de datos y genera el reporte Jasper.
     *
     * @param primaryStage Escenario principal de la aplicación JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        ConexionBD db;
        try {
            // Crear una instancia de conexión a la base de datos
            db = new ConexionBD();

            // Cargar el reporte desde un archivo .jasper en el paquete especificado
            InputStream reportStream = getClass().getResourceAsStream("/eu/andreatt/ejercicio1jr_dein/informes/ejercicio1.jasper");

            // Verificar si el archivo .jasper está presente
            if (reportStream == null) {
                throw new RuntimeException("El archivo .jasper no se encuentra en la ruta especificada.");
            }

            // Cargar el archivo del reporte Jasper
            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);

            // Parámetros que se pasarán al reporte Jasper
            Map<String, Object> parameters = new HashMap<>();
            String imageBasePath = getClass().getResource("/eu/andreatt/ejercicio1jr_dein/imagenes/").toString();
            parameters.put("REPORT_IMAGE", imageBasePath);

            // Mostrar la ruta base de la imagen en la consola para depuración
            System.out.println("Ruta base de la imagen: " + imageBasePath);

            // Llenar el reporte con los datos obtenidos de la conexión a la base de datos
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, db.getConexion());

            // Mostrar el reporte en una ventana utilizando JasperViewer
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

        } catch (SQLException e) {
            // Manejar errores relacionados con la conexión a la base de datos
            showErrorDialog("Error de conexión con la base de datos: " + e.getMessage());
        } catch (JRException e) {
            // Manejar errores relacionados con JasperReports
            showErrorDialog("Error al generar el reporte Jasper: " + e.getMessage());
        } catch (Exception e) {
            // Manejar cualquier otro tipo de error
            showErrorDialog("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Muestra un cuadro de diálogo de error con un mensaje especificado.
     *
     * @param message El mensaje de error a mostrar en el cuadro de diálogo.
     */
    private void showErrorDialog(String message) {
        // Crear y mostrar un cuadro de diálogo de error en el hilo de la aplicación JavaFX
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("ERROR");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
