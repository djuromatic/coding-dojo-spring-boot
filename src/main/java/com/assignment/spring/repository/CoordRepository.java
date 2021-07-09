package com.assignment.spring.repository;

import com.assignment.spring.entity.Coord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordRepository extends JpaRepository<Coord, Integer> {

}
