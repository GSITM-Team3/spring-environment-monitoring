package com.team3.springProject.userTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {	
		Optional<UserTable> _userTable = this.userRepository.findByLoginId(loginId);
		if (_userTable.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		UserTable userTable = _userTable.get();
		// 사용자의 권한 정보 처리문
		List<GrantedAuthority> authorities = new ArrayList<>();
		if ("admin".equals(loginId)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		// User 객체 생성, 이 객체는 스프링 시큐리티에서 사용하며 User 생성자에는 id, pw, 권한 리스트가 전달된다.
		return new User(userTable.getLoginId(), userTable.getPassword(), authorities);
	}
}