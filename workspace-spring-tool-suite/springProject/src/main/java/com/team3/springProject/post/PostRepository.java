package com.team3.springProject.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.team3.springProject.userTable.UserTable;

public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

	List<Post> findAllByOrderByCreatedAtDesc();

	Page<Post> findByUserTable_LoginIdContainingIgnoreCase(String loginId, Pageable pageable);

	Page<Post> findByLocationContainingIgnoreCase(String location, Pageable pageable);

	void deleteByUserTable(UserTable userTable);
}
