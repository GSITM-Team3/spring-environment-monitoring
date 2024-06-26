package com.team3.springProject.userTable;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.team3.springProject.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserTable create(String loginId, String password, String name, String phoneNumber, String gender) {
		UserTable user = new UserTable();
		user.setLoginId(loginId);
		user.setPassword(passwordEncoder.encode(password));
		user.setName(name);
		user.setPhoneNumber(phoneNumber);
		user.setGender(gender);
		user.setCreatedAt(LocalDateTime.now());
		this.userRepository.save(user);
		return user;
	}

	public UserTable getUser(String loginId) {
		Optional<UserTable> userTable = this.userRepository.findByLoginId(loginId);
		if (userTable.isPresent()) {
			return userTable.get();
		} else {
			throw new DataNotFoundException("userId not found");
		}
	}

	public boolean isLoginIdDuplicate(String loginId) {
        try {
            return userRepository.findByLoginId(loginId).isPresent();
        } catch (Exception e) {
            e.printStackTrace(); // 오류 로그 출력
            throw e;
        }
    }
	
	public void updateUser(UserTable userTable, String newPassword, String name, String phoneNumber, String gender) {
		if (newPassword != null && !newPassword.isEmpty()) {
			userTable.setPassword(passwordEncoder.encode(newPassword));
		}
		userTable.setName(name);
		userTable.setPhoneNumber(phoneNumber);
		userTable.setGender(gender);
		userTable.setUpdatedAt(LocalDateTime.now());
		this.userRepository.save(userTable);
	}
}
