package com.team3.springProject.post;

import java.time.LocalDateTime;
import java.util.List;

import com.team3.springProject.comment.Comment;
import com.team3.springProject.userTable.UserTable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Post {

	@Id // 글 고유 id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//   @ManyToOne //Many: Post, One: userTable
//   private UserTable userTable;

	@Column // 위치 정보
	private String location;

	@Column(columnDefinition = "TEXT") // 글 내용
	private String content;

	private LocalDateTime createdAt; // 글 생성일
	private LocalDateTime updatedAt; // 글 수정일

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<Comment> comment;

	@Column // 이미지 경로
	private String imagePath;

	// select
}
