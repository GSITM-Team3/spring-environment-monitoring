package com.team3.springProject.beach;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BeachService {

	private String apiUrl1 = "https://open.jejudatahub.net/api/proxy/aa7t3a3tabDtbD38a87ta8ttttt388ab/ottooo773btprtrj6p0t1tt0eebjrj3t?beachName=금능&measureItem=대장균";
	private String apiUrl2 = "https://open.jejudatahub.net/api/proxy/aa7t3a3tabDtbD38a87ta8ttttt388ab/ottooo773btprtrj6p0t1tt0eebjrj3t?beachName=금능&measureItem=장구균";
	
	@Autowired
    private RestTemplate restTemplate;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public List<List<Object>> fetchBeachDataForChart() {
        List<List<Object>> chartData = new ArrayList<>();

        try {
            // 외부 API 호출 및 JSON 응답 받기
            String jsonResponse1 = restTemplate.getForObject(apiUrl1, String.class);
            String jsonResponse2 = restTemplate.getForObject(apiUrl2, String.class);

            // JSON 문자열을 ApiResponse 객체로 매핑
            ApiResponse apiResponse1 = objectMapper.readValue(jsonResponse1, ApiResponse.class);
            ApiResponse apiResponse2 = objectMapper.readValue(jsonResponse2, ApiResponse.class);

            // ApiResponse에서 data 배열 추출
            List<Beach> beachList1 = apiResponse1.getData();
            List<Beach> beachList2 = apiResponse2.getData();
            
            // 새로운 배열 형태로 데이터 처리 로직 추가
            // 첫번째 줄
            List<Object> dataForFirstList = new ArrayList<>();
            dataForFirstList.add("실행시기");
            dataForFirstList.add("대장균");
            dataForFirstList.add("장구균");
            chartData.add(dataForFirstList);
            // 두번째줄부터 다섯번째 줄
            chartData.add(createDataRow("개장 전", beachList1, beachList2, "1차", "좌측"));
            chartData.add(createDataRow("개장 중 1차", beachList1, beachList2, "2차", "좌측"));
            chartData.add(createDataRow("개장 중 2차", beachList1, beachList2, "3차", "좌측"));
            chartData.add(createDataRow("폐장 후", beachList1, beachList2, "4차", "좌측"));

            return chartData;
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            return null; // 추후 예외처리 로직 추가
        }
    }

    private List<Object> createDataRow(String label, List<Beach> beachList1, List<Beach> beachList2, String measureType, String measurePoint) {
        List<Object> dataRow = new ArrayList<>();
        dataRow.add(label);
        dataRow.add(findMeasureValue(beachList1, measureType, measurePoint));
        dataRow.add(findMeasureValue(beachList2, measureType, measurePoint));
        return dataRow;
    }

    private int findMeasureValue(List<Beach> beachList, String measureType, String measurePoint) {
        for (Beach beach : beachList) {
            if (beach.getMeasureType().equals(measureType) && beach.getMeasurePoint().equals(measurePoint)) {
                String measureValue = beach.getMeasureValue();
                if (measureValue.equals("10미만")) {
                    return 5; // 10미만인 경우 5로 변환
                } else {
                    try {
                        return Integer.parseInt(measureValue);
                    } catch (NumberFormatException e) {
                        // 문자열이 숫자로 변환되지 않는 경우 0 반환
                        return 0;
                    }
                }
            }
        }
        return 0; // 해당하는 데이터가 없는 경우 0으로 반환
    }
}
