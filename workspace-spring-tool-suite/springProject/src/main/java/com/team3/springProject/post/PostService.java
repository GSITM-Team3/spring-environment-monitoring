package com.team3.springProject.post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team3.springProject.DataNotFoundException;
import com.team3.springProject.like.LikeTableRepository;
import com.team3.springProject.userTable.UserRepository;
import com.team3.springProject.userTable.UserTable;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;

	// Post list 전체 조회
	public List<Post> getPostList() {
		return this.postRepository.findAllByOrderByCreatedAtDesc();
		// return this.postRepository.findAll();
	}

	public Page<Post> searchPostsById(String keyword, int page) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createdAt"));
		PageRequest pageable = PageRequest.of(page, 3, Sort.by(sorts));
		return this.postRepository.findByUserTable_LoginIdContainingIgnoreCase(keyword, pageable);
	}

	public Page<Post> searchPostsByLocation(String keyword, int page) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createdAt"));
		PageRequest pageable = PageRequest.of(page, 3, Sort.by(sorts));
		return this.postRepository.findByLocationContainingIgnoreCase(keyword, pageable);
	}

	// 하나만 조회
	public Post getPost(Long id) {
		Optional<Post> post = this.postRepository.findById(id);
		if (post.isPresent()) {
			return post.get();
		} else {
			throw new DataNotFoundException("게시물이 존재하지 않습니다.");
		}
	}

	// 게시글 등록
	public void createPost(String location, String content, String imagePath, UserTable userTable) {
		Post post = new Post();

		post.setLocation(location);
		post.setContent(content);
		post.setImagePath(imagePath);
		post.setCreatedAt(LocalDateTime.now());
		post.setUserTable(userTable);
		this.postRepository.save(post);
	}

	// 수정 : 내용, 이미지, 위치
	public void updatePost(Post post, String location, String content, String imagePath) {
		post.setLocation(location);
		post.setContent(content);
		post.setImagePath(imagePath);
		post.setUpdatedAt(LocalDateTime.now()); // 수정한 날짜
		this.postRepository.save(post);
	}

	// 삭제
	public void delete(Post post) {
		this.postRepository.delete(post);
	}

	public Post getComment(Long postId) {
		Optional<Post> post = this.postRepository.findById(postId);
		if (post.isPresent()) {
			return post.get();
		} else {
			throw new DataNotFoundException("comment not found");
		}
	}

	// 페이징
	public Page<Post> getList(int page) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createdAt"));
		PageRequest pageable = PageRequest.of(page, 3, Sort.by(sorts));
		return this.postRepository.findAllByOrderByCreatedAtDesc(pageable);
	}

	// 좋아요 기능
	public Optional<Post> getOptionalPost(Long postId) {
		return this.postRepository.findById(postId);
	}

	//탈퇴 메서드
	@Transactional
    public void deletePostByUser(String loginId) {
    	Optional<UserTable> userTable2=userRepository.findByLoginId(loginId);
    	postRepository.deleteByUserTable(userTable2.get());
    }
}