package com.team3.springProject.weather;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherData getWeatherForecast(int nx, int ny) {
        String apiUrl = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=BwRRCTdEQ7idml0%2BdEyFhiztVzt3BtRLE6D3OlrXacWxIVB1E22%2BicHATKLJkbFl9OR6yDfWQpQNqhzpO6HSjQ%3D%3D&numOfRows=300&pageNo=1&dataType=JSON&base_date=20240624&base_time=0500&nx=" + nx + "&ny=" + ny;

        // Perform API call and parse response into WeatherData object
        return restTemplate.getForObject(apiUrl, WeatherData.class);
    }
}