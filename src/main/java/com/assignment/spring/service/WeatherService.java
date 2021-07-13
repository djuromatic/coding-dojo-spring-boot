package com.assignment.spring.service;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.dto.WeatherResponseDto;
import com.assignment.spring.entity.QWeatherEntity;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.entity.WeatherInfoEntity;
import com.assignment.spring.exceptions.NotFoundException;
import com.assignment.spring.mapper.WeatherMapper;
import com.assignment.spring.repository.WeatherRepository;
import com.querydsl.core.types.Predicate;
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

    @Transactional
    public WeatherResponseDto getWeatherByCity(String city) throws NotFoundException {
        WeatherEntity response = WeatherMapper.INSTANCE.entity(getWeatherApiResponse(city).getBody());
        for (WeatherInfoEntity wie : response.getWeatherInfo()) {
            wie.setWeather(response);
        }
        response.setFahrenheit((double) Math.round(response.getFahrenheit()));
        response.getCoord().setWeather(response);
        response.getWind().setWeather(response);
        return WeatherMapper.INSTANCE.entityToDto(weatherRepository.save(response));
    }

    public WeatherEntity getOneWeather(Predicate predicate) {
        JPAQuery<WeatherEntity> query = new JPAQuery<>(entityManager);
        QWeatherEntity qWeatherEntity = QWeatherEntity.weatherEntity;
        return query
                .from(qWeatherEntity)
                .where(predicate)
                .fetchFirst();
    }

    public List<WeatherEntity> getWeatherList(Predicate predicate) {
        JPAQuery<WeatherEntity> query = new JPAQuery<>(entityManager);
        QWeatherEntity qWeatherEntity = QWeatherEntity.weatherEntity;
        return query
                .from(qWeatherEntity)
                .where(predicate)
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
}
