package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.entity.BloodStock;

public interface BloodStockRepository extends JpaRepository<BloodStock, Long> {

    @Query("SELECT COALESCE(SUM(b.quantity), 0) FROM BloodStock b WHERE LOWER(b.bloodGroup) = LOWER(:bloodGroup)")
    Integer getTotalQuantityByBloodGroup(String bloodGroup);

}