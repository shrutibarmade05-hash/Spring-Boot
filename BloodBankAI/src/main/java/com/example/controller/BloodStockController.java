package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.entity.BloodStock;
import com.example.service.BloodStockService;

@RestController
@RequestMapping("/api/blood-stock")
public class BloodStockController {

    private final BloodStockService bloodStockService;

    public BloodStockController(BloodStockService bloodStockService) {
        this.bloodStockService = bloodStockService;
    }

    // Add Blood Stock
    @PostMapping
    public BloodStock addBloodStock(@RequestBody BloodStock bloodStock) {
        return bloodStockService.addBloodStock(bloodStock);
    }

    // Get All Blood Stock
    @GetMapping
    public List<BloodStock> getAllBloodStock() {
        return bloodStockService.getAllBloodStock();
    }

    // Get Blood Stock By ID
    @GetMapping("/{id}")
    public BloodStock getBloodStockById(@PathVariable Long id) {
        return bloodStockService.getBloodStockById(id);
    }

    // Update Blood Stock
    @PutMapping("/{id}")
    public BloodStock updateBloodStock(
            @PathVariable Long id,
            @RequestBody BloodStock bloodStock) {

        return bloodStockService.updateBloodStock(id, bloodStock);
    }

    // Delete Blood Stock
    @DeleteMapping("/{id}")
    public String deleteBloodStock(@PathVariable Long id) {

        bloodStockService.deleteBloodStock(id);

        return "Blood stock deleted successfully";
    }

    // Check Stock Status
    @GetMapping("/status")
    public String getStockStatus(@RequestParam int quantity) {

        return bloodStockService.getStockStatus(quantity);
    }
}