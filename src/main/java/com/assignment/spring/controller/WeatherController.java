package com.assignment.spring.controller;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.dto.WeatherInfoDto;
import com.assignment.spring.dto.WindDto;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.exceptions.BadRequestException;
import com.assignment.spring.service.Units;
import com.assignment.spring.service.WeatherService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {


    @Autowired
    private WeatherService weatherService;


    @GetMapping("/weather")
    public WeatherEntity weather(@RequestParam(name = "city") String city, @RequestParam(required = false, name = "unit") String unit) throws NotFoundException {
        if (city.isEmpty()) throw new BadRequestException("City should not be empty");
        if (unit == null) {
            unit = Units.FAHRENHEIT.getUnit();
        }
        return weatherService.getWeatherByCity(city, unit);
    }

    @GetMapping("/report")
    public List<WeatherEntity> getAllReports() {
        return weatherService.getAllReports();
    }

    @GetMapping("/report/{id}")
    public WeatherEntity getSavedReport(@PathVariable(name = "id") Integer id) {
        return weatherService.getSavedReport(id).get();
    }

    @GetMapping("/country")
    public List<WeatherEntity> getByCountry(@RequestParam(name = "country") String country) {
        return weatherService.getByName(country);
    }

    @GetMapping("/response")
    public ResponseEntity<WeatherResponse> getWeatherApiResponse(@RequestParam(name = "city") String city) {
        return weatherService.getWeatherApiResponse(city);
    }

    @GetMapping("/wind")
    public WindDto getWindOfWeather(@RequestParam(name = "id") Integer id) {
        return weatherService.getWindForWeather(id);
    }

    @GetMapping("/weather-info")
    public List<WeatherInfoDto> getWeatherInfoForWeather(@RequestParam(name = "id") Integer id) {
        return weatherService.getWeatherInfoForWeather(id);
    }


}
