package com.team3.springProject.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {

	@NotEmpty(message="댓글 내용 입력은 필수 항목입니다.")
	private String content;
}
