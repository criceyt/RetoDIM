package userInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

/**
 * La clase Main es la clase principal de la aplicación JavaFX.
 * Extiende la clase Application para proporcionar la funcionalidad
 * de inicio de la aplicación.
 */
public class Main extends Application {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * Configuración del logger para escribir en un archivo de log.
     */
    private static void configureLogger() {
        try {
            // Crear un FileHandler para el archivo "app.log"
            FileHandler fileHandler = new FileHandler("app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());

            // Agregar el FileHandler al logger
            logger.addHandler(fileHandler);

            // Establecer el nivel de registro (INFO, WARNING, SEVERE, etc.)
            logger.setLevel(Level.ALL);

        } catch (IOException e) {
            System.err.println("No se pudo configurar el logger: " + e.getMessage());
        }
    }

    /**
     * Método que se llama al iniciar la aplicación.
     *
     * @param primaryStage El escenario principal donde se muestra la interfaz gráfica.
     * @throws Exception Si ocurre un error durante la carga de la interfaz.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            primaryStage.setTitle("JavaFX Application");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            // Registrar la excepción con nivel SEVERE
            logger.log(Level.SEVERE, "Error al iniciar la aplicación", e);
        }
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        // Configurar el logger antes de iniciar la aplicación
        configureLogger();
        
        try {
            launch(args);
        } catch (Exception e) {
            // Registrar cualquier excepción que ocurra durante el launch
            logger.log(Level.SEVERE, "Excepción en el método main", e);
        }
    }
}
