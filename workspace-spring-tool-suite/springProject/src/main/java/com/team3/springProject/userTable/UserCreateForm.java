package com.team3.springProject.userTable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
	
	@Size(min=6, max=12)
	@Pattern(regexp="^[a-zA-Z0-9]+$", message="아이디는 영문과 숫자를 조합하여 6~12자로 입력해주세요.")
	@NotEmpty(message="아이디 영문, 숫자 조합 6~12자")
	private String loginId;
	
	@Size(min=8, max=12)
	@Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W).{8,12}$", message="비밀번호는 영문, 숫자, 특수문자를 조합하여 8~12자로 입력해주세요.")
	@NotEmpty(message="비밀번호 영문, 숫자, 특수문자 조합 8~12자")
	private String password1;
	
	@NotEmpty(message="비밀번호 다시 입력")
	private String password2;
	
	@NotEmpty(message="이름 2자 이상")
	private String name;
	
	@NotEmpty(message="휴대폰번호 -없이 입력")
	@Pattern(regexp="^\\d{11}$", message="휴대폰번호는 -없이 숫자만 11자로 입력해주세요.")
	private String phoneNumber;
	
	private String gender;
}
