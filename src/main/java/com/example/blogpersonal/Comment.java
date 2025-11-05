package com.example.blogpersonal;


import jakarta.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    private String content;

    //@ManyToOne: Muchos Comment (esta clase) pertenecen a Un Post (la variable post)".
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;



}
