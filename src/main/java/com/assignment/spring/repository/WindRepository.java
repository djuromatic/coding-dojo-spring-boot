package com.assignment.spring.repository;

import com.assignment.spring.entity.Wind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WindRepository extends JpaRepository<Wind, Integer> {

    Wind findByWeatherId(Integer id);
}
