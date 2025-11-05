package com.example.blogpersonal;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = true)
    private String title;
    @Lob
    private String content;

    //@JoinColumn(name = "user_id"): Esto quiere decir que en la tabla post habra una columna llamada user_id que es la foreign key
    //@ManyToOne: "Muchos Post (esta clase) pertenecen a Un User (la variable user)".
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //En mappedBy le estamos diciendo a JPA que vaya a la clase Comment busque la variable post para buscar los comentarios almacenados en esta tabla.
    //En cascade = CascadeType ALL cuando guardas un post tambien guardara los comentarios de dicho post o como tambien si eliminas el post tambien eliminara los comentarios de ese post
    //orphanRemoval = true, al eliminar un comentario lo eliminas de la lista Comment, pero no de la base de datos, por lo tanto, esto asegura que tambien sera eliminado de la base de datos.


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @CreationTimestamp
    private LocalDateTime updatedAt;

    public Post() {
    }

    public Post(Long id, String title, User user, String content) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.content = content;
    }

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


}
