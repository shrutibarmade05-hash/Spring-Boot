package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Donor;

public interface DonorRepository extends JpaRepository<Donor, Long> {

    List<Donor> findByBloodGroupIgnoreCaseAndCityIgnoreCaseAndAvailable(
            String bloodGroup,
            String city,
            boolean available);
}