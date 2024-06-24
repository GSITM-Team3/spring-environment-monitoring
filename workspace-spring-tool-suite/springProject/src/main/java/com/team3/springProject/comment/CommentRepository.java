package com.team3.springProject.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	//List<Comment> findAllByOrderByCreatedAtDesc();
	List<Comment> findByPostId(Long postId);
}
