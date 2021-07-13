package com.assignment.spring.controller;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.dto.WeatherInfoDto;
import com.assignment.spring.dto.WeatherResponseDto;
import com.assignment.spring.dto.WindDto;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.mapper.WeatherMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeatherControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ConfigurableEnvironment env;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private List<Integer> createdIds;

    @BeforeAll
    public void init() {
        String uri = "/weather?city=";
        List<String> cities = Arrays.asList("beograd", "novi sad", "stockholm", "amsterdam");
        createdIds = cities.stream()
                .map(x -> testRestTemplate.getForObject(urlBuilder(uri + x), WeatherEntity.class))
                .map(x -> x.getId())
                .collect(Collectors.toList());
        Assertions.assertNotNull(createdIds);
    }

    @Test
    public void shouldThrowNotFoundException() {
        String uri = "/weather?city=test";
        Exception exception = testRestTemplate.getForObject(urlBuilder(uri), Exception.class);
        String expectedMessage = "City not found: test";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowBadRequestException() {
        String uri = "/weather?city=";
        Exception exception = testRestTemplate.getForObject(urlBuilder(uri), Exception.class);
        String expectedMessage = "City should not be empty";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldExistInDataSource() {
        String uri = "/report?id=" + createdIds.get(0);
        WeatherResponseDto response = testRestTemplate.getForObject(urlBuilder(uri), WeatherResponseDto.class);
        Assertions.assertEquals(createdIds.get(0), response.getId());
    }

    @Test
    public void shouldGetLIstOfWeatherEntity() {
        List<WeatherResponseDto> reportList = testRestTemplate.getForObject(urlBuilder("/reports"), List.class);
        Assertions.assertEquals(createdIds.size(), reportList.size());
    }

    @Test
    public void shouldReturnListOfWeatherByCountry() {
        String uri = "/reports?country=RS";
        List response = testRestTemplate.getForObject(urlBuilder(uri), List.class);
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void shouldReturnWeatherResponse() {
        String uri = "/response?city=belgrade";
        WeatherResponse response = testRestTemplate.getForObject(urlBuilder(uri), WeatherResponse.class);
        Assertions.assertNotNull(response);
    }

    private String urlBuilder(String uri) {
        return "http://localhost:" + port + uri;
    }
}
