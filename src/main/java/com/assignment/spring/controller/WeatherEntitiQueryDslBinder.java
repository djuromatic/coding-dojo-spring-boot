package com.assignment.spring.controller;

import com.assignment.spring.entity.QWeatherEntity;
import com.assignment.spring.service.WeatherEntityCustomQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public class WeatherEntitiQueryDslBinder implements QuerydslBinderCustomizer<QWeatherEntity> {

    @Autowired
    private WeatherEntityCustomQuery queryService;

    @Override
    public void customize(QuerydslBindings querydslBindings, QWeatherEntity qWeatherEntity) {
        querydslBindings.bind(qWeatherEntity.celsius).first((path, value) -> queryService.celsiuseToFahrenheitQuery(QWeatherEntity.weatherEntity.fahrenheit, value));
    }
}
