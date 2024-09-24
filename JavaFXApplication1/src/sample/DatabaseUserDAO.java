/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author crice
 */
public class DatabaseUserDAO implements UserDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/usuarios_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "abcd*1234";

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(User user) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user (name, lastName, email, age) VALUES (?, ?, ?, ?)")) {
             
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getAge());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User loadUser() {
        User user = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM user LIMIT 1")) {

            if (rs.next()) {
                user = new User(
                        rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getInt("age")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}