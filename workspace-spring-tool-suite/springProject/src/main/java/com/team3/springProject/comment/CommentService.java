package com.team3.springProject.comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team3.springProject.DataNotFoundException;
import com.team3.springProject.like.LikeTableRepository;
import com.team3.springProject.post.Post;
import com.team3.springProject.userTable.UserRepository;
import com.team3.springProject.userTable.UserTable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	
//	public CommentService(CommentRepository commentRepository) {
//		this.commentRepository = commentRepository;
//
//	}

	public List<Comment> getCommentsByPostIdOrderedByCreatedAtDesc(Long postId) {
		List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
		return comments;
	}

	public void createComment(Post postId, String content, UserTable userTable) {
		Comment comment = new Comment();
		comment.setPost(postId);
		comment.setContent(content);
		comment.setCreatedAt(LocalDateTime.now());
		comment.setUserTable(userTable);
		commentRepository.save(comment);
	}

	public Comment getComment(Long id) {
		Optional<Comment> comment = this.commentRepository.findById(id);
		if (comment.isPresent()) {
			return comment.get();
		} else {
			throw new DataNotFoundException("댓글이 존재하지 않습니다.");
		}
	}

	public void delete(Comment comment) {
		this.commentRepository.delete(comment);
	}
	
	@Transactional
	public void deleteCommentByUser(String loginId) {
    	Optional<UserTable> userTable2=userRepository.findByLoginId(loginId);
    	commentRepository.deleteByUserTable(userTable2.get());
    }
}