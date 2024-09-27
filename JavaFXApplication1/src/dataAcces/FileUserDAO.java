package dataAcces;

import Model.User;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase FileUserDAO es una implementación de UserDAO que utiliza
 * un archivo para almacenar y recuperar información de usuario.
 */
public class FileUserDAO implements UserDAO {

    private static final String FILE_PATH = "src/dataAcces/data.properties";

    // Logger para registrar eventos e información
    private static final Logger logger = Logger.getLogger(FileUserDAO.class.getName());

    /**
     * Método que guarda un usuario en un archivo.
     *
     * @param user El usuario a guardar.
     */
    @Override
    public void saveUser(User user) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
             
            out.writeObject(user);
            logger.log(Level.INFO, "Usuario guardado correctamente en el archivo.");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al guardar el usuario en el archivo.", e);
        }
    }

    /**
     * Método que carga un usuario desde un archivo.
     *
     * @return Un objeto User con los datos del usuario o null si no se encuentra.
     */
    @Override
    public User loadUser() {
        User user = null;
        try (FileInputStream fileIn = new FileInputStream(FILE_PATH);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
             
            user = (User) in.readObject();
            logger.log(Level.INFO, "Usuario cargado correctamente desde el archivo.");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al leer el archivo de usuario.", e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error: Clase User no encontrada.", e);
        }
        return user;
    }
}
