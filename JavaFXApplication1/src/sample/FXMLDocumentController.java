/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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

    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            // Cargar archivo de configuración
            Properties configProperties = new Properties();
            FileInputStream configInput = new FileInputStream("src/sample/Config.properties");
            configProperties.load(configInput);

            // Determinar el origen de datos
            String dataSource = configProperties.getProperty("dataSource");

            if ("file".equalsIgnoreCase(dataSource)) {
                // Leer datos del archivo de datos
                loadFromDataFile("src/data.properties");
            } else if ("db".equalsIgnoreCase(dataSource)) {
                // Leer datos de la base de datos
                loadFromDatabase();
            }

            // Cerrar el flujo de entrada
            configInput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar datos desde el archivo de propiedades
    private void loadFromDataFile(String filePath) {
        try {
            Properties dataProperties = new Properties();
            FileInputStream dataInput = new FileInputStream(filePath);
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

            // Cerrar el flujo de entrada
            dataInput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar datos desde la base de datos
    private void loadFromDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM user LIMIT 1")) {

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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
