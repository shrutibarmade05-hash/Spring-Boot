package com.example.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.repository.DonorRepository;
import com.example.repository.PatientRepository;
import com.example.repository.HospitalRepository;
import com.example.repository.BloodRequestRepository;
import com.example.repository.DonationRepository;
import com.example.repository.BloodStockRepository;
import com.example.repository.AiPredictionRepository;

@Service
public class DashboardService {

    private final DonorRepository donorRepository;
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final BloodRequestRepository bloodRequestRepository;
    private final DonationRepository donationRepository;
    private final BloodStockRepository bloodStockRepository;
    private final AiPredictionRepository aiPredictionRepository;

    public DashboardService(
            DonorRepository donorRepository,
            PatientRepository patientRepository,
            HospitalRepository hospitalRepository,
            BloodRequestRepository bloodRequestRepository,
            DonationRepository donationRepository,
            BloodStockRepository bloodStockRepository,
            AiPredictionRepository aiPredictionRepository) {

        this.donorRepository = donorRepository;
        this.patientRepository = patientRepository;
        this.hospitalRepository = hospitalRepository;
        this.bloodRequestRepository = bloodRequestRepository;
        this.donationRepository = donationRepository;
        this.bloodStockRepository = bloodStockRepository;
        this.aiPredictionRepository = aiPredictionRepository;
    }

    public Map<String, Object> getDashboardSummary() {

        Map<String, Object> dashboard = new HashMap<>();

        dashboard.put("totalDonors", donorRepository.count());
        dashboard.put("totalPatients", patientRepository.count());
        dashboard.put("totalHospitals", hospitalRepository.count());
        dashboard.put("totalBloodRequests", bloodRequestRepository.count());
        dashboard.put("totalDonations", donationRepository.count());
        dashboard.put("totalBloodStockRecords", bloodStockRepository.count());
        dashboard.put("totalAiPredictions", aiPredictionRepository.count());

        long emergencyRequests = bloodRequestRepository.findAll()
                .stream()
                .filter(request -> request.getUrgency() != null
                        && request.getUrgency().equalsIgnoreCase("Emergency"))
                .count();

        dashboard.put("emergencyRequests", emergencyRequests);

        return dashboard;
    }

    public List<Map<String, Object>> getBloodGroupWiseStock() {

        List<String> bloodGroups = Arrays.asList(
                "A+",
                "A-",
                "B+",
                "B-",
                "AB+",
                "AB-",
                "O+",
                "O-"
        );

        List<Map<String, Object>> stockData = new java.util.ArrayList<>();

        for (String bloodGroup : bloodGroups) {

            Integer quantity =
                    bloodStockRepository.getTotalQuantityByBloodGroup(bloodGroup);

            Map<String, Object> data = new LinkedHashMap<>();

            data.put("bloodGroup", bloodGroup);
            data.put("quantity", quantity);

            stockData.add(data);
        }

        return stockData;
    }
}