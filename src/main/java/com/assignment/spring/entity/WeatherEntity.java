package com.assignment.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "weather")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String city;

    private String country;

    private Double temperature;

    private String unit;

    @OneToOne(mappedBy = "weather", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Coord coord;

    @OneToMany(mappedBy = "weather", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<WeatherInfoEntity> weatherInfo;

    @OneToOne(mappedBy = "weather", cascade = CascadeType.ALL)
    private Wind wind;

    public WeatherEntity(String city, String country, Double temperature, String unit) {
        this.city = city;
        this.country = country;
        this.temperature = temperature;
        this.unit = unit;
    }
}
