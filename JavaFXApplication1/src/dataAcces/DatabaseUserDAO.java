package dataAcces;

import Model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase DatabaseUserDAO es una implementación de UserDAO que utiliza
 * una base de datos para almacenar y recuperar información de usuario.
 */
public class DatabaseUserDAO implements UserDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/usuarios_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "abcd*1234";

    // Crear un logger para la clase DatabaseUserDAO
    private static final Logger logger = Logger.getLogger(DatabaseUserDAO.class.getName());

    /**
     * Constructor que inicializa la conexión a la base de datos y crea la tabla
     * de usuario si no existe.
     */
    public DatabaseUserDAO() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // Crear tabla si no existe
            String sql = "CREATE TABLE IF NOT EXISTS user (" +
                         "name VARCHAR(255), " +
                         "lastName VARCHAR(255), " +
                         "email VARCHAR(255), " +
                         "age INT)";
            stmt.execute(sql);
            logger.log(Level.INFO, "Tabla de usuarios creada o ya existente.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear la tabla de usuarios.", e);
        }
    }

    /**
     * Método que guarda un usuario en la base de datos.
     *
     * @param user El usuario a guardar.
     */
    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO user (name, lastName, email, age) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getAge());
            pstmt.executeUpdate();
            logger.log(Level.INFO, "Usuario {0} guardado en la base de datos.", user.getName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al guardar el usuario en la base de datos.", e);
        }
    }

    /**
     * Método que carga un usuario desde la base de datos.
     *
     * @return Un objeto User con los datos del usuario o null si no se encuentra.
     */
    @Override
    public User loadUser() {
        User user = null;
        String sql = "SELECT * FROM user LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                user = new User(
                        rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getInt("age")
                );
                logger.log(Level.INFO, "Usuario cargado desde la base de datos.");
            } else {
                logger.log(Level.WARNING, "No se encontró ningún usuario en la base de datos.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al cargar el usuario desde la base de datos.", e);
        }
        return user;
    }
}
