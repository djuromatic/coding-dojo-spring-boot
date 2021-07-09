package com.assignment.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wind {

    @Id
    @GeneratedValue
    private Integer id;
    private double speed;
    private Integer deg;
    private Float gust;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="WEATHER_ID", referencedColumnName = "ID")
    private WeatherEntity weather;
}
