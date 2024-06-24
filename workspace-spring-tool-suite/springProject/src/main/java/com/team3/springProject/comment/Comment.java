package com.team3.springProject.comment;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne
    //@Column(nullable = false)
    //private UserTable userId;

    //@Column(nullable = false)
    //private Post postId;

    @Column(columnDefinition="TEXT", length = 200)
    private String content;

    private LocalDateTime createdAt;

}
