package com.team3.springProject.weather;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class WeatherData {
	@Getter
	@Setter
    @JsonProperty("temperature")
    private List<WeatherItem> temperature;

	@Getter
	@Setter
    @JsonProperty("wind")
    private List<WeatherItem> wind;
	@Getter
	@Setter
    @JsonProperty("windDirection")
    private List<WeatherItem> windDirection;
	@Getter
	@Setter
    @JsonProperty("wave")
    private List<WeatherItem> wave;
    
    public static class WeatherItem {
    	@Getter
    	@Setter
        @JsonProperty("fcstTime")
        private String fcstTime;
    	@Getter
    	@Setter
        @JsonProperty("fcstValue")
        private String fcstValue;
    	@Getter
    	@Setter
        @JsonProperty("category")
        private String category;
    }
}
