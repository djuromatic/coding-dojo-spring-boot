package com.assignment.spring.mapper;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.entity.WeatherEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeatherMapper {

    WeatherMapper INSTANCE = Mappers.getMapper(WeatherMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "city"),
            @Mapping(source = "sys.country", target = "country"),
            @Mapping(source = "main.temp", target = "temperature"),
            @Mapping(source = "coord", target = "coord"),
            @Mapping(source = "weather", target = "weatherInfo"),
            @Mapping(source = "wind", target = "wind")
    })
    WeatherEntity entity(WeatherResponse response);

    @InheritInverseConfiguration
    WeatherResponse response(WeatherEntity entity);
}
