package com.assignment.spring.controller;

import java.util.List;

import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {
    public static final List<WeatherEntity> WEATHER_ENTITIES = Arrays
            .asList(
                    new WeatherEntity("Novi Sad", "RS", 32.0, "c"),
                    new WeatherEntity("Beograd", "RS", 33.0, "c"),
                    new WeatherEntity("Amsterdan", "NL", 33.0, "c"));

    @MockBean
    private WeatherService service;

    @Autowired
    private MockMvc mvc;

    @Test
    public void getWeatherShouldReturnResult() throws Exception {
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setId(1);
        weatherEntity.setCity("Belgrade");
        weatherEntity.setCountry("RS");
        weatherEntity.setTemperature(100.0);

        when(service.getWeatherByCity(anyString(), anyString())).thenReturn(weatherEntity);

        mvc.perform(MockMvcRequestBuilders.get("/weather?city=belgrade"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.city").value("Belgrade"))
                .andExpect(jsonPath("$.country").value("RS"))
                .andExpect(jsonPath("$.temperature").value(100.0));
    }

    @Test
    public void shouldGetWeathersByCountry() throws Exception {
        when(service.getByName(anyString())).thenReturn(WEATHER_ENTITIES);
        mvc.perform(MockMvcRequestBuilders.get("/country").param("country", "RS"))
                .andExpect(status().isOk());
    }

}
