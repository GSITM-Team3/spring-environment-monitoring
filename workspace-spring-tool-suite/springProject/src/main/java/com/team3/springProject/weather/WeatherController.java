package com.team3.springProject.weather;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public String showWeatherPage(Model model) {
        // Initial values for nx, ny (default: 제주도 북부 앞바다)
        int nx = 56;
        int ny = 33;
        
        // Fetch initial weather data
        WeatherData weatherData = weatherService.getWeatherForecast(nx, ny);
        model.addAttribute("weatherData", weatherData);

        return "weather";
    }

    @GetMapping("/forecast")
    public String fetchWeatherForecast(int nx, int ny, Model model) {
        WeatherData weatherData = weatherService.getWeatherForecast(nx, ny);
        model.addAttribute("weatherData", weatherData);

        return "fragments/weather :: weather-fragment";
    }
}
