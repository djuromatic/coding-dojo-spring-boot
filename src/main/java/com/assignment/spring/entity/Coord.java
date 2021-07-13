package com.assignment.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
    @JoinColumn(name = "WEATHER_ID", referencedColumnName = "ID")
    private WeatherEntity weather;
}
