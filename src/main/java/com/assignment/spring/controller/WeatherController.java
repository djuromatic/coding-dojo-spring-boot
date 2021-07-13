package com.assignment.spring.controller;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.dto.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.exceptions.BadRequestException;
import com.assignment.spring.mapper.WeatherMapper;
import com.assignment.spring.service.Units;
import com.assignment.spring.service.WeatherService;
import com.querydsl.core.types.Predicate;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {


    @Autowired
    private WeatherService weatherService;


    @GetMapping("/weather")
    public WeatherResponseDto weather(@RequestParam(name = "city") String city) throws NotFoundException {
        if (city.isEmpty()) throw new BadRequestException("City should not be empty");
        return weatherService.getWeatherByCity(city);
    }

    @GetMapping("/report")
    public WeatherResponseDto getWeather(
            @QuerydslPredicate(root = WeatherEntity.class) Predicate predicate
    ) {
        return WeatherMapper.INSTANCE.entityToDto(weatherService.getOneWeather(predicate));
    }

    @GetMapping("/reports")
    public List<WeatherResponseDto> getWeatherList(
            @QuerydslPredicate(root = WeatherEntity.class, bindings = WeatherEntitiQueryDslBinder.class) Predicate predicate
    ) {
        return WeatherMapper.INSTANCE.entityToDto(weatherService.getWeatherList(predicate));
    }

    @GetMapping("/response")
    public ResponseEntity<WeatherResponse> getWeatherApiResponse(@RequestParam(name = "city") String city) {
        return weatherService.getWeatherApiResponse(city);
    }

}
