package com.team3.springProject.post;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {

	@NotEmpty(message="위치를 선택해주세요.")
	private String location;
	
	@NotEmpty(message="내용을 입력해주세요.")
	private String content;
	
	@NotEmpty(message="이미지를 선택해주세요.")
	private String imagePath;
}
