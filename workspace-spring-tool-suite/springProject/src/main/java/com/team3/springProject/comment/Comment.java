package com.team3.springProject.comment;

import java.time.LocalDateTime;

import com.team3.springProject.post.Post;
import com.team3.springProject.userTable.UserTable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment { // 이건 그냥 공부하려고 해본거

	@Id // 댓글 아이디
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private long userId;
	// private long postId;
	
	@ManyToOne
	private Post post; // postId..?
	
	@Column(columnDefinition="TEXT")
	private String content;
	
	private LocalDateTime createdAt; //글 생성일
	
//	@ManyToOne //유저 정보
//	private UserTable userTable;
	
}
