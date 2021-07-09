package com.assignment.spring.mapper;

import com.assignment.spring.dto.WindDto;
import com.assignment.spring.entity.Wind;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper
public interface WindMapper {

    WindMapper INSTANCE = Mappers.getMapper(WindMapper.class);
    @Mappings({})
    Wind dtoToWind (Wind windDto);
    @InheritInverseConfiguration
    WindDto windToDto (Wind wind);
}
