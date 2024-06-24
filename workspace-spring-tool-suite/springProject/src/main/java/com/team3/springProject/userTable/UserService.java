package com.team3.springProject.userTable;

import java.time.LocalDateTime;
import java.util.Optional;

//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	//private final PasswordEncoder passwordEncoder;
	
	public UserTable create(String loginId, String password, String name,
			String phoneNumber, String gender) {
		UserTable user=new UserTable();
		user.setLoginId(loginId);
		//user.setPassword(passwordEncoder.encode(password));
		user.setName(name);
		user.setPhoneNumber(phoneNumber);
		user.setGender(gender);
		user.setCreatedAt(LocalDateTime.now());
		this.userRepository.save(user);
		return user;
	}
	
	public UserTable getUser(String loginId) {
		Optional<UserTable> userTable=this.userRepository.findByLoginId(loginId);
		return userTable.orElse(null);
	}
}
