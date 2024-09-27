package dataAcces;

import Model.User;

/**
 * La interfaz UserDAO define los métodos para manejar la persistencia
 * de objetos User, permitiendo distintas implementaciones.
 */
public interface UserDAO {
    void saveUser(User user);
    User loadUser();
}
