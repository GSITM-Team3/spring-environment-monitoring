package com.team3.springProject.beach;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Controller
@RequestMapping("/beach")
public class BeachController {

	private final BeachService beachService;
	private static final Logger logger = Logger.getLogger(BeachController.class.getName());

	@GetMapping("")
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

	@GetMapping("/coordinates")
	@ResponseBody
	public ResponseEntity<?> getBeachCoordinates(@RequestParam(name="beachName") String beachName) {
	    try {
	        double latitude;
	        double longitude;

	        if ("곽지".equals(beachName)) {
	            latitude = 33.450639510098995;
	            longitude = 126.30467921624465;
	        } else if ("금능".equals(beachName)) {
	            latitude = 33.39044610770367;
	            longitude = 126.23588938855988;
	        } else if ("김녕".equals(beachName)) {
	            latitude = 33.55740325435361;
	            longitude = 126.75931839508372;
	        } else if ("사계".equals(beachName)) {
	            latitude = 33.228646591595734;
	            longitude = 126.30860545728355;
	        } else if ("삼양".equals(beachName)) {
	            latitude = 33.525858913738276;
	            longitude = 126.58554112536463;
	        } else if ("세화".equals(beachName)) {
	            latitude = 33.525132396923084;
	            longitude = 126.86026266642433;
	        } else if ("소금막".equals(beachName)) {
	            latitude = 33.3312033800073;
	            longitude = 126.8425499383526;
	        } else if ("쇠소깍".equals(beachName)) {
	            latitude = 33.25133149846314;
	            longitude = 126.62298641971624;
	        } else if ("신양섭지".equals(beachName)) {
	            latitude = 33.43509900907118;
	            longitude = 126.9234717781453;
	        } else if ("월정".equals(beachName)) {
	            latitude = 33.55644101171576;
	            longitude = 126.79581589328733;
	        } else if ("이호테우".equals(beachName)) {
	            latitude = 33.497366881138994;
	            longitude = 126.45269034339074;
	        } else if ("중문색달".equals(beachName)) {
	            latitude = 33.24503318920924;
	            longitude = 126.41149817163394;
	        } else if ("평대".equals(beachName)) {
	            latitude = 33.53352316155188;
	            longitude = 126.84086267568917;
	        } else if ("표선".equals(beachName)) {
	            latitude = 33.327464106454045;
	            longitude = 126.83747646613715;
	        } else if ("하도".equals(beachName)) {
	            latitude = 33.51305046033628;
	            longitude = 126.89923431912975;
	        } else if ("함덕".equals(beachName)) {
	            latitude = 33.54305392422481;
	            longitude = 126.66924703830941;
	        } else if ("협재".equals(beachName)) {
	            latitude = 33.394300971913886;
	            longitude = 126.23965246742786;
	        } else if ("화순금모래".equals(beachName)) {
	            latitude = 33.24001713719621;
	            longitude = 126.33359589949859;
	        } else {
	            return ResponseEntity.notFound().build(); // 요청된 해변 이름이 매칭되지 않으면 404 반환
	        }

	        Coordinates coordinates = new Coordinates(latitude, longitude);
	        return ResponseEntity.ok().body(coordinates);

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching coordinates");
	    }
	}


    // 좌표 객체 클래스
	@Getter
	@Setter
    public static class Coordinates {
        private double latitude;
        private double longitude;

        public Coordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

}
