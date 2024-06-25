package com.team3.springProject.like;

import com.team3.springProject.post.Post;
import com.team3.springProject.userTable.UserTable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "like_entity")
@Setter
public class Like {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   
   @ManyToOne
   @JoinColumn(name = "user_id")
   private UserTable userTable;

   @ManyToOne
   @JoinColumn(name = "post_id")
   private Post post;

   @Column
   private LocalDateTime createdAt;
}
