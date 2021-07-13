package com.assignment.spring.entity;

import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
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

    private Double fahrenheit;

    @Transient
    @QueryType(PropertyType.NUMERIC)
    private Double celsius;

    @OneToOne(mappedBy = "weather", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Coord coord;

    @OneToMany(mappedBy = "weather", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<WeatherInfoEntity> weatherInfo;

    @OneToOne(mappedBy = "weather", cascade = CascadeType.ALL)
    private Wind wind;


    public WeatherEntity(String city, String country, Double fahrenheit) {
        this.city = city;
        this.country = country;
        this.fahrenheit = fahrenheit;
    }

    public Double getCelsius() {
        if(fahrenheit != null) {
            return (double) Math.round((fahrenheit - 32) / 1.8);
        }
        return null;
    }
}
