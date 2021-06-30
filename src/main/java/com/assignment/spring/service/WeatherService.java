package com.assignment.spring.service;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.exceptions.NotFoundException;
import com.assignment.spring.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherRepository weatherRepository;

    public WeatherEntity getWeatherByCity(String city) throws NotFoundException {
        String appid = env.getProperty("openweather.appid");
        String apipath = env.getProperty("openweather.apipath");
        try {
            String url = apipath.replace("{city}", city).replace("{appid}", appid);
            ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);
            return mapper(response.getBody());
        }catch (NullPointerException ex) {
            throw new NullPointerException(ex.getMessage());
        }catch (RuntimeException ex) {
            throw new NotFoundException("City not found: " + city);
        }
    }

    public Optional<WeatherEntity> getSavedReport(int id) {
        return weatherRepository.findById(id);
    }

    public List<WeatherEntity> getAllReports() {
        return weatherRepository.findAll();
    }

    private WeatherEntity mapper(WeatherResponse response) {
        WeatherEntity entity = new WeatherEntity();
        entity.setCity(response.getName());
        entity.setCountry(response.getSys().getCountry());
        entity.setTemperature(response.getMain().getTemp());
        return weatherRepository.save(entity);
    }
}
