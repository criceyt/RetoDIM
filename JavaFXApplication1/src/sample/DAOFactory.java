/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author crice
 */
public class DAOFactory {

    private static final String CONFIG_FILE = "src/sample/Config.properties";

    public static UserDAO getUserDAO() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(CONFIG_FILE));
            String daoType = props.getProperty("daoType");

            if ("file".equalsIgnoreCase(daoType)) {
                return new FileUserDAO();
            } else if ("database".equalsIgnoreCase(daoType)) {
                return new DatabaseUserDAO();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
