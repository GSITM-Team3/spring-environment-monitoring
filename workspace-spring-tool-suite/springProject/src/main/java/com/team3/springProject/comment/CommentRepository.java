package com.team3.springProject.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team3.springProject.userTable.UserTable;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByPostId(Long postId);
	List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);
	
	void deleteByUserTable(UserTable userTable);
}
