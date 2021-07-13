package com.assignment.spring.mapper;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.dto.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface WeatherMapper {

    WeatherMapper INSTANCE = Mappers.getMapper(WeatherMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "city"),
            @Mapping(source = "sys.country", target = "country"),
            @Mapping(source = "main.temp", target = "fahrenheit"),
            @Mapping(source = "coord", target = "coord"),
            @Mapping(source = "weather", target = "weatherInfo"),
            @Mapping(source = "wind", target = "wind")
    })
    WeatherEntity entity(WeatherResponse response);

    @InheritInverseConfiguration
    WeatherResponse response(WeatherEntity entity);

    WeatherResponseDto entityToDto(WeatherEntity entity);

    WeatherEntity dtoToEntity(WeatherResponseDto dto);

    List<WeatherResponseDto> entityToDto(List<WeatherEntity> entities);

    List<WeatherEntity> dtoToEntity(List<WeatherResponseDto> dtos);

}
