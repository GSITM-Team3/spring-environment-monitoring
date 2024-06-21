package com.team3.springProject.beach;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/beach")
public class BeachController {
	
	private final BeachService chartAPIService;
	private static final Logger logger = Logger.getLogger(BeachController.class.getName());
	
	@GetMapping("/data")
	public String getDataFromApi(Model model) {
	    List<List<Object>> chartData = chartAPIService.fetchBeachDataForChart();
	    logger.info("ChartData: " + chartData.toString()); // 데이터 로그 확인

	    model.addAttribute("chartData", chartData); // Thymeleaf로 전달할 데이터 이름 지정

	    return "beach";
	}
	
}
