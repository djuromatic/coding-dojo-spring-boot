package com.assignment.spring.service;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.dto.WeatherInfoDto;
import com.assignment.spring.dto.WindDto;
import com.assignment.spring.entity.QWeatherEntity;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.entity.WeatherInfoEntity;
import com.assignment.spring.entity.Wind;
import com.assignment.spring.exceptions.NotFoundException;
import com.assignment.spring.mapper.WeatherInfoMapper;
import com.assignment.spring.mapper.WeatherMapper;
import com.assignment.spring.mapper.WindMapper;
import com.assignment.spring.repository.WeatherInfoRepository;
import com.assignment.spring.repository.WeatherRepository;
import com.assignment.spring.repository.WindRepository;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private Environment env;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private WindRepository windRepository;

    @Autowired
    private WeatherInfoRepository weatherInfoRepository;

    @Transactional
    public WeatherEntity getWeatherByCity(String city, String unit) throws NotFoundException {
        WeatherEntity response = WeatherMapper.INSTANCE.entity(getWeatherApiResponse(city).getBody());
        for (WeatherInfoEntity wie : response.getWeatherInfo()) {
            wie.setWeather(response);
        }
        response.getCoord().setWeather(response);
        response.getWind().setWeather(response);
        return weatherRepository.save(convertTempCelsius(response, unit));
    }

    public Optional<WeatherEntity> getSavedReport(int id) {
        return weatherRepository.findById(id);
    }

    public List<WeatherEntity> getAllReports() {
        return weatherRepository.findAll();
    }

    public List<WeatherEntity> getByName(String name) {
        JPAQuery<WeatherEntity> query = new JPAQuery<>(entityManager);
        QWeatherEntity qWeatherEntity = QWeatherEntity.weatherEntity;
        return query
                .from(qWeatherEntity)
                .where(qWeatherEntity.country.eq(name))
                .fetch();
    }

    public ResponseEntity<WeatherResponse> getWeatherApiResponse(String city) {
        String appid = env.getProperty("openweather.appid");
        String apipath = env.getProperty("openweather.apipath");
        try {
            String url = apipath.replace("{city}", city).replace("{appid}", appid);
            ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);
            return response;
        } catch (RuntimeException ex) {
            throw new NotFoundException("City not found: " + city);
        }
    }

    public WindDto getWindForWeather(Integer id) {
        Wind wind = windRepository.findByWeatherId(id);
        return WindMapper.INSTANCE.windToDto(wind);
    }

    public List<WeatherInfoDto> getWeatherInfoForWeather(Integer id) {
        return WeatherInfoMapper.INSTANCE.weathersToWeathersDto(weatherInfoRepository.findAllByWeatherId(id));
    }

    private WeatherEntity convertTempCelsius(WeatherEntity entity, String unit) {
        if (unit.equals(Units.CELSIUS.getUnit())) {
            entity.setUnit(Units.CELSIUS.getUnit());
            entity.setTemperature((double) Math.round((entity.getTemperature() - 32) / 1.8));
        } else {
            entity.setUnit(Units.FAHRENHEIT.getUnit());
        }
        return entity;
    }

}
