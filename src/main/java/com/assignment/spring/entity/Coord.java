package com.assignment.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Data
public class Coord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private double lon;
    private double lat;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="WEATHER_ID", referencedColumnName = "ID")
    @JsonIgnore
    private WeatherEntity weather;
}
