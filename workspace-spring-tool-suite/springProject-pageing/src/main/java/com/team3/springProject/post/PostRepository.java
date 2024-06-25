package com.team3.springProject.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends
					JpaRepository<Post, Integer>{

	Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
	List<Post> findAllByOrderByCreatedAtDesc();
}
