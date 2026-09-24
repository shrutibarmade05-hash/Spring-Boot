package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.AiPrediction;

public interface AiPredictionRepository extends JpaRepository<AiPrediction, Long> {

}