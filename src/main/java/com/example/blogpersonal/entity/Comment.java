package com.example.blogpersonal.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

/**
 * Entidad Comment - Representa un comentario en un post del blog
 * Tabla en BD: comment
 */
@Entity
@Table(name = "comment")
public class Comment {

    // ==================== CAMPOS ====================

    /**
     * ID único del comentario
     * Generado automáticamente por la base de datos (PostgreSQL)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Contenido del comentario
     * @Lob permite texto largo sin límite (tipo TEXT en BD)
     * Sin @Lob sería VARCHAR(255)
     */
    @Lob
    @Column(nullable = false)
    private String content;

    /**
     * Post al que pertenece este comentario
     * Relación: Muchos comentarios → Un post
     * Crea columna "post_id" como Foreign Key
     */
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /**
     * Usuario que escribió el comentario
     * Relación: Muchos comentarios → Un usuario
     * Crea columna "user_id" como Foreign Key
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Fecha y hora de creación del comentario
     * Se asigna automáticamente por Hibernate al crear el registro
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    // ==================== CONSTRUCTORES ====================

    /**
     * Constructor vacío
     * Requerido por JPA para crear instancias mediante reflexión
     */
    public Comment() {
    }

    /**
     * Constructor para crear nuevos comentarios
     * @param content Contenido del comentario
     * @param post Post donde se comenta
     * @param user Usuario que comenta
     *
     * Nota: No incluye id ni createdAt porque son autogenerados
     */
    public Comment(String content, Post post, User user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

    // ==================== GETTERS Y SETTERS ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
