package com.assignment.spring.repository;

import com.assignment.spring.entity.WeatherInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfoEntity, Integer> {

    List<WeatherInfoEntity> findAllByWeatherId(Integer id);
}
