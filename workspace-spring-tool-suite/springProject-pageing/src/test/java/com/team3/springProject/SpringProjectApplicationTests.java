package com.team3.springProject;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.team3.springProject.comment.Comment;
import com.team3.springProject.comment.CommentRepository;
import com.team3.springProject.comment.CommentService;

@SpringBootTest
class SpringProjectApplicationTests {

	@Autowired
	private CommentRepository commentRepository;

	@Test
	void contextLoads() {
		Comment c1 = new Comment();
		c1.setContent("좋네요");
		c1.setCreatedAt(LocalDateTime.now());
		this.commentRepository.save(c1);
	}

}
