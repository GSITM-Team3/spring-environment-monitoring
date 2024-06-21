package com.team3.springProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	@GetMapping("hello") // localhost:8080/hello
	public String hello() {
		return "hello";
	}
}
