package com.assignment.spring.mapper;

import com.assignment.spring.dto.WeatherInfoDto;
import com.assignment.spring.entity.WeatherInfoEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface WeatherInfoMapper {

    WeatherInfoMapper INSTANCE = Mappers.getMapper(WeatherInfoMapper.class);

    @Mappings({})
    WeatherInfoDto weatherInfoToDto(WeatherInfoEntity entity);

    @InheritInverseConfiguration
    WeatherInfoEntity dtoToWeatherInfo(WeatherInfoDto dto);


    @Mappings({})
    List<WeatherInfoDto> weathersToWeathersDto(List<WeatherInfoEntity> entities);

    @InheritInverseConfiguration
    List<WeatherInfoEntity> dtoToEntitis(List<WeatherInfoDto> dto);
}
