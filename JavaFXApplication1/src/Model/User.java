package Model;

import java.io.Serializable;

/**
 * La clase User representa a un usuario con propiedades como nombre,
 * apellido, email y edad. Implementa la interfaz Serializable para
 * permitir la escritura y lectura de objetos de usuario.
 */
public class User implements Serializable {
    private String name;
    private String lastName;
    private String email;
    private int age;

    /**
     * Constructor que inicializa un nuevo objeto User con los valores proporcionados.
     *
     * @param name El nombre del usuario.
     * @param lastName El apellido del usuario.
     * @param email El correo electrónico del usuario.
     * @param age La edad del usuario.
     */
    public User(String name, String lastName, String email, int age) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    // Getters y setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}