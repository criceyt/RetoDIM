package dataAcces;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase DAOFactory es responsable de la creación de instancias
 * de UserDAO basándose en la configuración proporcionada.
 */
public class DAOFactory {

    private static final String CONFIG_FILE = "src/dataAcces/Config.properties";
    
    private static final Logger logger = Logger.getLogger(DAOFactory.class.getName());

    /**
     * Método que devuelve una instancia de UserDAO según la configuración.
     *
     * @return Una implementación de UserDAO (FileUserDAO o DatabaseUserDAO).
     */
    public static UserDAO getUserDAO() {
        Properties props = new Properties();

        // Usar try-with-resources para cerrar el FileInputStream automáticamente
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);

            String daoType = props.getProperty("daoType");

            if ("file".equalsIgnoreCase(daoType)) {
                logger.log(Level.INFO, "Se ha seleccionado FileUserDAO como implementación de UserDAO.");
                return new FileUserDAO();
            } else if ("database".equalsIgnoreCase(daoType)) {
                logger.log(Level.INFO, "Se ha seleccionado DatabaseUserDAO como implementación de UserDAO.");
                return new DatabaseUserDAO();
            } else {
                logger.log(Level.WARNING, "El tipo de DAO especificado en el archivo de configuración es desconocido: " + daoType);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar el archivo de configuración: " + CONFIG_FILE, e);
        }
        
        return null;
    }
}
