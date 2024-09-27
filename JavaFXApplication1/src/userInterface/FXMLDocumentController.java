package userInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase FXMLDocumentController maneja la lógica de la interfaz gráfica
 * definida en el archivo FXML. Controla la interacción del usuario y la carga
 * de datos.
 */
public class FXMLDocumentController {

    @FXML
    private Button button;

    @FXML
    private Label label;   // Nombre
    @FXML
    private Label label1;  // Apellido
    @FXML
    private Label label11; // Email
    @FXML
    private Label label12; // Edad
    @FXML
    private Label label2;  // Valor del nombre
    @FXML
    private Label label21; // Valor del apellido
    @FXML
    private Label label22; // Valor del email
    @FXML
    private Label label23; // Valor de la edad

    // URL de la base de datos
    private static final String DB_URL = "jdbc:mysql://localhost:3306/usuarios_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "abcd*1234";

    // Logger para registrar eventos e información
    private static final Logger logger = Logger.getLogger(FXMLDocumentController.class.getName());

    /**
     * Método que maneja la acción del botón para cargar datos.
     *
     * @param event El evento de acción que dispara este método.
     */
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try (InputStream configInput = getClass().getResourceAsStream("/dataAcces/Config.properties")) {
            if (configInput == null) {
                logger.log(Level.SEVERE, "No se pudo encontrar el archivo de configuración.");
                return;
            }

            Properties configProperties = new Properties();
            configProperties.load(configInput);

            // Determinar el origen de datos
            String dataSource = configProperties.getProperty("dataSource");

            if ("file".equalsIgnoreCase(dataSource)) {
                // Leer datos del archivo de datos
                loadFromDataFile("src/dataAcces/data.properties");
            } else if ("db".equalsIgnoreCase(dataSource)) {
                // Leer datos de la base de datos
                loadFromDatabase();
            } else {
                logger.log(Level.WARNING, "Origen de datos desconocido en Config.properties: {0}", dataSource);
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al leer el archivo de configuración.", e);
        }
    }

    /**
     * Método para cargar datos desde un archivo de propiedades.
     *
     * @param filePath La ruta del archivo de propiedades desde donde se
     * cargarán los datos.
     */
    private void loadFromDataFile(String filePath) {
        try (InputStream dataInput = getClass().getResourceAsStream("/data.properties")) {
            if (dataInput == null) {
                logger.log(Level.SEVERE, "No se pudo encontrar el archivo de datos: " + filePath);
                return;
            }

            Properties dataProperties = new Properties();
            dataProperties.load(dataInput);

            // Obtener los valores del archivo de datos
            String nombre = dataProperties.getProperty("nombre");
            String apellido = dataProperties.getProperty("apellido");
            String email = dataProperties.getProperty("email");
            String edad = dataProperties.getProperty("edad");

            // Mostrar los valores en los Labels
            label2.setText(nombre);
            label21.setText(apellido);
            label22.setText(email);
            label23.setText(edad);

            logger.log(Level.INFO, "Datos cargados desde archivo: {0}", filePath);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al leer el archivo de datos: " + filePath, e);
        }
    }

    /**
     * Método para cargar datos desde la base de datos y mostrarlos en la
     * interfaz.
     */
    private void loadFromDatabase() {
        String query = "SELECT * FROM user LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                // Obtener los valores de la base de datos
                String nombre = rs.getString("name");
                String apellido = rs.getString("lastName");
                String email = rs.getString("email");
                int edad = rs.getInt("age");

                // Mostrar los valores en los Labels
                label2.setText(nombre);
                label21.setText(apellido);
                label22.setText(email);
                label23.setText(String.valueOf(edad));

                logger.log(Level.INFO, "Datos cargados desde la base de datos.");
            } else {
                logger.log(Level.WARNING, "No se encontraron registros en la base de datos.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al consultar la base de datos.", e);
        }
    }
}
