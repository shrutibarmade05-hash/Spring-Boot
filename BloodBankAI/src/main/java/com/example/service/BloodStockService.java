package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.BloodStock;
import com.example.repository.BloodStockRepository;

@Service
public class BloodStockService {

    private final BloodStockRepository bloodStockRepository;

    public BloodStockService(BloodStockRepository bloodStockRepository) {
        this.bloodStockRepository = bloodStockRepository;
    }

    // Add Blood Stock
    public BloodStock addBloodStock(BloodStock bloodStock) {
        return bloodStockRepository.save(bloodStock);
    }

    // Get All Blood Stock
    public List<BloodStock> getAllBloodStock() {
        return bloodStockRepository.findAll();
    }

    // Get Blood Stock By ID
    public BloodStock getBloodStockById(Long id) {
        return bloodStockRepository.findById(id).orElse(null);
    }

    // Update Blood Stock
    public BloodStock updateBloodStock(Long id, BloodStock bloodStock) {

        BloodStock existing =
                bloodStockRepository.findById(id).orElse(null);

        if (existing != null) {

            existing.setBloodGroup(bloodStock.getBloodGroup());
            existing.setQuantity(bloodStock.getQuantity());
            existing.setCity(bloodStock.getCity());
            existing.setLastUpdated(bloodStock.getLastUpdated());

            return bloodStockRepository.save(existing);
        }

        return null;
    }

    // Delete Blood Stock
    public void deleteBloodStock(Long id) {
        bloodStockRepository.deleteById(id);
    }

    // Check Blood Stock Status
    public String getStockStatus(int quantity) {

        if (quantity <= 10) {
            return "LOW";
        } else if (quantity <= 25) {
            return "MEDIUM";
        } else {
            return "SUFFICIENT";
        }
    }
}