package com.team3.springProject.userTable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
	
	@Size(min=3, max=25)
	@NotEmpty(message="아이디를 입력해주세요.")
	private String loginId;
	
	@NotEmpty(message="비밀번호를 입력해주세요.")
	private String password1;
	
	@NotEmpty(message="비밀번호를 한번 더 입력해주세요.")
	private String password2;
	
	@NotEmpty(message="이름을 입력해주세요.")
	private String name;
	
	@NotEmpty(message="핸드폰 번호를 입력해주세요.")
	private String phoneNumber;
	
	private String gender;
}
