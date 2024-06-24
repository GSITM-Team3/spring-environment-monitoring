package com.team3.springProject.comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.team3.springProject.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;

	public List<Comment> getCommentList(){
		return this.commentRepository.findAll();
	}

	public Comment getComment(Long id) {
		Optional<Comment> comment = this.commentRepository.findById(id);
		if(comment.isPresent()) {
			return comment.get();
		} else {
			throw new DataNotFoundException("댓글이 존재하지 않습니다.");
		}
	}

	public void createComment(String content) {
		Comment comment = new Comment();		
		comment.setContent(content);
		comment.setCreatedAt(LocalDateTime.now());
        this.commentRepository.save(comment);
	}
	
	public void delete(Comment comment) {
		this.commentRepository.delete(comment);
	}
}
