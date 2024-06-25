package com.team3.springProject.post;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {

	@NotEmpty(message="위치를 선택해주세요.")
	private String location;
	
	@NotEmpty(message="내용을 입력해주세요.")
	private String content;
	
	@NotNull(message="이미지 파일을 선택해주세요.")
	private MultipartFile imageFile;
	
	private String imagePath; // 이미지의 경로를 저장할 필드
}
