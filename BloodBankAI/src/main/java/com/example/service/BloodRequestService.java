package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.BloodRequest;
import com.example.repository.BloodRequestRepository;

@Service
public class BloodRequestService {

    private final BloodRequestRepository bloodRequestRepository;

    public BloodRequestService(BloodRequestRepository bloodRequestRepository) {
        this.bloodRequestRepository = bloodRequestRepository;
    }

    // Add Blood Request
    public BloodRequest addBloodRequest(BloodRequest request) {
        return bloodRequestRepository.save(request);
    }

    // Get All Blood Requests
    public List<BloodRequest> getAllBloodRequests() {
        return bloodRequestRepository.findAll();
    }

    // Get Blood Request By ID
    public BloodRequest getBloodRequestById(Long id) {
        return bloodRequestRepository.findById(id).orElse(null);
    }

    // Update Blood Request
    public BloodRequest updateBloodRequest(
            Long id,
            BloodRequest request) {

        BloodRequest existing =
                bloodRequestRepository.findById(id).orElse(null);

        if (existing != null) {

            existing.setPatientName(request.getPatientName());
            existing.setBloodGroup(request.getBloodGroup());
            existing.setQuantity(request.getQuantity());
            existing.setHospitalName(request.getHospitalName());
            existing.setCity(request.getCity());
            existing.setUrgency(request.getUrgency());
            existing.setStatus(request.getStatus());

            return bloodRequestRepository.save(existing);
        }

        return null;
    }

    // Delete Blood Request
    public void deleteBloodRequest(Long id) {
        bloodRequestRepository.deleteById(id);
    }

    // Emergency Blood Request
    public BloodRequest createEmergencyRequest(
            BloodRequest request) {

        request.setUrgency("Emergency");
        request.setStatus("Pending");

        return bloodRequestRepository.save(request);
    }
}