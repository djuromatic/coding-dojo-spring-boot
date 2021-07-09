package com.assignment.spring.repository;

import java.util.List;

import com.assignment.spring.entity.WeatherInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfoEntity, Integer> {

    List<WeatherInfoEntity> findAllByWeatherId(Integer id);
}
