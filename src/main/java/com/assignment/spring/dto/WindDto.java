package com.assignment.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WindDto {

    private Integer id;
    private double speed;
    private Integer deg;
    private Float gust;
}
