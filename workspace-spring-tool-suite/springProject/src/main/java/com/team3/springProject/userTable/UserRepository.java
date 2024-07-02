package com.team3.springProject.userTable;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserTable, Long> {
	Optional<UserTable> findByLoginId(String loginId);

}
