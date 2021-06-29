package com.assignment.spring.controller;

import com.assignment.spring.entity.WeatherEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeatherControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private List<Integer> createdIds;

    @BeforeAll
    public void init() {
        String uri = "/weather?city=";
        List<String> cities = Arrays.asList("beograd", "stockholm", "amsterdam");
        createdIds = cities.stream()
                .map(x -> testRestTemplate.getForObject(urlBuilder(uri + x), WeatherEntity.class))
                .map(x -> x.getId())
                .collect(Collectors.toList());
    }

    @Test
    public void shouldExistInDataSource() {
        String uri = "/report/" + createdIds.get(0);
        WeatherEntity response = testRestTemplate.getForObject(urlBuilder(uri), WeatherEntity.class);
        Assertions.assertEquals(createdIds.get(0), response.getId());
    }

    @Test
    public void shouldGetLIstOfWeatherEntity() {
        List<WeatherEntity> reportList = testRestTemplate.getForObject(urlBuilder("/report"), List.class);
        Assertions.assertEquals(reportList.size(), createdIds.size());
    }

    private String urlBuilder(String uri) {
        return "http://localhost:" + port + uri;
    }
}
