package com.assignment.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WeatherResponseDto {

    private Integer id;
    private String city;
    private String country;
    private Double fahrenheit;
    private Double celsius;
    private String unit;
    private CoordDto coord;
    private List<WeatherInfoDto> weatherInfo;
    private WindDto wind;

}
