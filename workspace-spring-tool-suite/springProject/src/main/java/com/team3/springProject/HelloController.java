package com.team3.springProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	@GetMapping("hello") // localhost:8080/hello
	@ResponseBody // 브라우저 화면에 바로 보여줘
	public String hello() {
		return "Hello World";
	}
}
