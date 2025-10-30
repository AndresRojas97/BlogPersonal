package com.example.blogpersonal;


import jakarta.persistence.*;

//Con @Entity decimos que es una clase de base de datos
//Con @Table decimos como se va a llamar la tabla en este caso 'users' (se suelen nombrar en plural)
@Entity
@Table(name = "users")
public class User {

    //Con Column le ponemos reglas en este caso a las columnas de la tabla
    //nullable que no sea nulo
    //unique = true debe ser unica
    //length = maximo de caracteres 50
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    @Lob
    private String description;

    //Con @Id decimos que la variable id es la primary key
    //Con @GeneratedValue le decimos que la base de datos (PostgreSQL) se encargará de generar el ID automáticamente al insertar.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User() {
    }

    public User(String username, String email, String password, String role, String lastName, String firstName, Long id, String description) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
        this.description = description;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
