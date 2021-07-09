package com.assignment.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherInfoDto {

    private Integer id;

    private String main;
    private String description;
    private String icon;
}
