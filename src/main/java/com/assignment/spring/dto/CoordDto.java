package com.assignment.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordDto {

    private Integer id;
    private double lon;
    private double lat;
}
