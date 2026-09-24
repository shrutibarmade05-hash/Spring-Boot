package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.entity.BloodRequest;
import com.example.service.BloodRequestService;

@RestController
@RequestMapping("/api/blood-requests")
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;

    public BloodRequestController(BloodRequestService bloodRequestService) {
        this.bloodRequestService = bloodRequestService;
    }

    // Add Blood Request
    @PostMapping
    public BloodRequest addBloodRequest(@RequestBody BloodRequest request) {
        return bloodRequestService.addBloodRequest(request);
    }

    // Get All Blood Requests
    @GetMapping
    public List<BloodRequest> getAllBloodRequests() {
        return bloodRequestService.getAllBloodRequests();
    }

    // Get Blood Request By ID
    @GetMapping("/{id}")
    public BloodRequest getBloodRequestById(@PathVariable Long id) {
        return bloodRequestService.getBloodRequestById(id);
    }

    // Update Blood Request
    @PutMapping("/{id}")
    public BloodRequest updateBloodRequest(
            @PathVariable Long id,
            @RequestBody BloodRequest request) {

        return bloodRequestService.updateBloodRequest(id, request);
    }

    // Delete Blood Request
    @DeleteMapping("/{id}")
    public String deleteBloodRequest(@PathVariable Long id) {

        bloodRequestService.deleteBloodRequest(id);

        return "Blood request deleted successfully";
    }

    // Emergency Blood Request
    @PostMapping("/emergency")
    public BloodRequest createEmergencyRequest(
            @RequestBody BloodRequest request) {

        return bloodRequestService.createEmergencyRequest(request);
    }
}