package com.example.blogpersonal.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad Post - Representa una publicación/artículo del blog
 * Tabla en BD: post
 */
@Entity
@Table(name = "post")
public class Post {

    // ==================== CAMPOS ====================

    /**
     * ID único del post
     * Generado automáticamente por PostgreSQL
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título del post
     * Máximo 50 caracteres, puede ser null
     */
    @Column(length = 50, nullable = true)
    private String title;

    /**
     * Contenido completo del post
     * @Lob permite texto largo sin límite (tipo TEXT en BD)
     */
    @Lob
    @Column(nullable = false)
    private String content;

    /**
     * Autor del post
     * Relación: Muchos posts → Un usuario
     * Crea columna "user_id" como Foreign Key
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Lista de comentarios de este post
     * Relación bidireccional: Un post → Muchos comentarios
     *
     * @OneToMany: Un post tiene muchos comentarios
     * mappedBy = "post": La relación está mapeada en Comment.post
     * cascade = ALL: Operaciones en post se propagan a comentarios
     * orphanRemoval = true: Si un comentario se elimina de la lista,
     *                       también se elimina de la BD
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    /**
     * Fecha y hora de creación del post
     * Se asigna automáticamente al crear el registro
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de última actualización
     * Se actualiza automáticamente cada vez que se edita el post
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ==================== CONSTRUCTORES ====================

    /**
     * Constructor vacío
     * Requerido por JPA
     */
    public Post() {
    }

    /**
     * Constructor para crear nuevos posts
     * @param title Título del post
     * @param content Contenido del post
     * @param user Autor del post
     *
     * Nota: No incluye id, comments, createdAt ni updatedAt
     *       porque son autogenerados o se llenan después
     */
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    // ==================== GETTERS Y SETTERS ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}