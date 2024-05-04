package com.example.phproject.direction.repository;

import com.example.phproject.direction.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction,Long> {
}
