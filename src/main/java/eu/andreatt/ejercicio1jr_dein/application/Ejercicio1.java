package eu.andreatt.ejercicio1jr_dein.application;

import eu.andreatt.ejercicio1jr_dein.bbdd.ConexionBD;
import javafx.scene.control.Alert;
import java.io.InputStream;
import java.util.HashMap;

public class Ejercicio1 {

    public static void main(String[] args) {

        // comenzamos con el lanzamiento del informe
        try {
            // abrimos la base de datos
            ConexionBD con = new ConexionBD();
            //podemos crear un conjunto de parámetros si quisieramos pasárselo al informe
            HashMap<String, Object> parameters = new HashMap<String, Object>();

            InputStream reportStream = con.getClass().getResourceAsStream("/eu/andreatt/ejercicio1jr_dein/jasper/ejercicio1.jasper");
            if (reportStream == null) {
                System.out.println("El archivo no esta ahí");
            }else {
                System.out.println("El archivo se ha encontrado");
            }
            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);

            //Atentos a la ruta del Jasper y a cómo enlazamos el archivo compilado por JasperReports
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, con.getConexion());
            //Preparamos un visor, no intentaremos usar el salvar a PDF; para eso el SO ya nos da las impresoras A PDF
            JasperViewer viewer = new JasperViewer(jprint, false);
            //lanzamos la visión
            viewer.setVisible(true);
        } catch (Exception e) {
            //como estamos en JavaFX tratamos la alerta aquí y comentamos el stack de consola.
            // en desarrollo sería conveniente tenerlo descomentado
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR");
            alert.setContentText("Ha ocurrido un error al abrir el informe. Pida ayuda en el foro");
            alert.showAndWait();
            //e.printStackTrace();
        }
    }
}