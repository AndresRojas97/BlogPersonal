package com.example.blogpersonal.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad User - Representa un usuario del blog
 * Tabla en BD: users (plural por convención)
 */
@Entity
@Table(name = "users")
public class User {

    // ==================== CAMPOS ====================

    /**
     * ID único del usuario
     * Generado automáticamente por PostgreSQL
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario único
     * Obligatorio, único, máximo 50 caracteres
     * Se usa para login e identificación pública
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * Contraseña del usuario
     * Obligatoria, máximo 100 caracteres
     * TODO: Debe encriptarse con BCrypt en el servicio
     */
    @Column(nullable = false, length = 100)
    private String password;

    /**
     * Email del usuario
     * Obligatorio, único, máximo 50 caracteres
     * Se usa para login y notificaciones
     */
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    /**
     * Rol del usuario en el sistema
     * Ejemplos: "USER", "ADMIN", "MODERATOR"
     * Se usa para autorización y permisos
     */
    private String role;

    /**
     * Nombre real del usuario
     */
    private String firstName;

    /**
     * Apellido del usuario
     */
    private String lastName;

    /**
     * Biografía o descripción del usuario
     * @Lob permite texto largo sin límite
     * Usado en el perfil público
     */
    @Lob
    private String description;

    /**
     * Lista de posts creados por este usuario
     * Relación bidireccional: Un usuario → Muchos posts
     *
     * mappedBy = "user": La relación está mapeada en Post.user
     * cascade = ALL: Si se elimina el usuario, se eliminan sus posts
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    /**
     * Lista de comentarios escritos por este usuario
     * Relación bidireccional: Un usuario → Muchos comentarios
     *
     * mappedBy = "user": La relación está mapeada en Comment.user
     * cascade = ALL: Si se elimina el usuario, se eliminan sus comentarios
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    // ==================== CONSTRUCTORES ====================

    /**
     * Constructor vacío
     * Requerido por JPA
     */
    public User() {
    }

    /**
     * Constructor para crear nuevos usuarios
     * @param username Nombre de usuario único
     * @param email Email único
     * @param password Contraseña (debe encriptarse antes de guardar)
     * @param firstName Nombre
     * @param lastName Apellido
     * @param role Rol del usuario
     * @param description Biografía
     *
     * Nota: No incluye id, posts ni comments porque:
     *       - id es autogenerado
     *       - posts y comments se llenan después de crear el usuario
     */
    public User(String username, String email, String password,
                String firstName, String lastName, String role, String description) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.description = description;
    }

    // ==================== GETTERS Y SETTERS ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
