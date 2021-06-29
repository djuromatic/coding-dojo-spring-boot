package com.assignment.spring.controller;

import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {


    @Autowired
    private WeatherService weatherService;

    @RequestMapping("/weather")
    public WeatherEntity weather(@RequestParam(name = "city") String city) {
        return weatherService.getWeatherByCity(city);
    }

    @RequestMapping("/report")
    public List<WeatherEntity> getAllReports() {
        return weatherService.getAllReports();
    }

    @RequestMapping("/report/{id}")
    public WeatherEntity getSavedReport(@PathVariable(name = "id") int id) {
        return weatherService.getSavedReport(id).get();
    }
}
