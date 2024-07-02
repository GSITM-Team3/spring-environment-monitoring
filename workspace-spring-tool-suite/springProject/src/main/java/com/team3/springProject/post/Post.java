package com.team3.springProject.post;

import java.time.LocalDateTime;
import java.util.List;

import com.team3.springProject.comment.Comment;
import com.team3.springProject.like.LikeTable;
import com.team3.springProject.userTable.UserTable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Post {

	@Id // 글 고유 id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne // Many: Post, One: userTable
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private UserTable userTable;

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

	// 추가: 현재 사용자가 이 게시물에 좋아요를 눌렀는지 여부를 나타내는 필드
	@Transient
	private boolean loggedInUserLiked;

	// 추가: 현재 이 게시물에 좋아요가 몇개인지 여부
	@Transient
	private int likeCount;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<LikeTable> likes;
}
