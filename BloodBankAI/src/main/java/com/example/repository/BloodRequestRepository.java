package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.BloodRequest;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {

}