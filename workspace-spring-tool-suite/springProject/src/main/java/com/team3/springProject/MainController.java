package com.team3.springProject;

import org.springframework.web.bind.annotation.GetMapping;

public class MainController {

	@GetMapping("/") // 메인 홈화면 : 실시간 날씨..
	public String root() {
		return "redirect:/news_weather";
	}
}
