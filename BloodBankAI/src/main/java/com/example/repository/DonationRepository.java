package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long> {

}