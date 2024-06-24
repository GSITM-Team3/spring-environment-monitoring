package com.team3.springProject.beach;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/beach")
public class BeachController {

	private final BeachService beachService;
	private static final Logger logger = Logger.getLogger(BeachController.class.getName());

	@GetMapping("/data")
	public String getDataFromApi(Model model) {
		// 기본적으로 "곽지" 해수욕장의 데이터를 가져와서 모델에 추가하고 view 이름 반환
		return fetchDataAndAddToModel("곽지", model);
	}

	@PostMapping("/select")
	@ResponseBody // 메서드의 반환 값이 HTTP 응답 본문으로 전송됨을 명시
	public String selectBeach(@RequestBody String selectedBeach) {
		// 선택된 해수욕장 문자열에서 앞뒤의 큰따옴표 제거
		selectedBeach = selectedBeach.replaceAll("^\"|\"$", "");
		logger.info("Selected Beach: " + selectedBeach); // 선택된 해수욕장 로그로 기록

		// 선택된 해수욕장의 데이터를 가져와서 JSON 형식의 문자열로 변환하여 반환
		return fetchDataAndReturnJson(selectedBeach);
	}

	// 선택된 해수욕장의 데이터를 가져와서 JSON 형식의 문자열로 변환하는 메서드
	private String fetchDataAndReturnJson(String selectedBeach) {
		// BeachService를 통해 선택된 해수욕장의 데이터를 가져옴
		List<List<Object>> chartData = beachService.fetchBeachDataForChart(selectedBeach);
		logger.info("ChartData: " + chartData.toString()); // 가져온 데이터를 로그로 기록

		// 가져온 데이터를 JSON 형식의 문자열로 변환하여 반환
		return convertChartDataToJson(chartData);
	}

	// List<List<Object>> 형식의 데이터를 JSON 형식의 문자열로 변환하는 메서드
	private String convertChartDataToJson(List<List<Object>> chartData) {
		ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper 객체 생성

		try {
			// chartData를 JSON 형식의 문자열로 변환하여 반환
			return objectMapper.writeValueAsString(chartData);
		} catch (JsonProcessingException e) {
			e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
			return "[]"; // 예외 발생 시 빈 배열로 초기화된 JSON 형식의 문자열 반환
		}
	}

	// 지정된 해수욕장의 데이터를 가져와서 모델에 추가하고 view 이름을 반환하는 메서드
	private String fetchDataAndAddToModel(String beachName, Model model) {
		// BeachService를 통해 지정된 해수욕장의 데이터를 가져옴
		List<List<Object>> chartData = beachService.fetchBeachDataForChart(beachName);
		logger.info("ChartData: " + chartData.toString()); // 가져온 데이터를 로그로 기록

		// 가져온 데이터를 JSON 형식의 문자열로 변환
		String chartDataJson = convertChartDataToJson(chartData);

		// Thymeleaf로 전달할 데이터를 모델에 추가
		model.addAttribute("chartData", chartDataJson);

		// view 이름 반환 (여기서는 "beach.html"로 가정)
		return "beach";
	}

}
