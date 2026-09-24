package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.AiPrediction;
import com.example.entity.BloodRequest;
import com.example.repository.AiPredictionRepository;
import com.example.repository.BloodRequestRepository;

@Service
public class AiPredictionService {

    private final AiPredictionRepository aiPredictionRepository;
    private final BloodRequestRepository bloodRequestRepository;

    public AiPredictionService(
            AiPredictionRepository aiPredictionRepository,
            BloodRequestRepository bloodRequestRepository) {

        this.aiPredictionRepository = aiPredictionRepository;
        this.bloodRequestRepository = bloodRequestRepository;
    }

    // Add AI Prediction
    public AiPrediction addPrediction(AiPrediction prediction) {

        return aiPredictionRepository.save(prediction);
    }

    // Get All AI Predictions
    public List<AiPrediction> getAllPredictions() {

        return aiPredictionRepository.findAll();
    }

    // Get AI Prediction By ID
    public AiPrediction getPredictionById(Long id) {

        return aiPredictionRepository.findById(id).orElse(null);
    }

    // Update AI Prediction
    public AiPrediction updatePrediction(
            Long id,
            AiPrediction prediction) {

        AiPrediction existing =
                aiPredictionRepository.findById(id).orElse(null);

        if (existing != null) {

            existing.setBloodGroup(prediction.getBloodGroup());
            existing.setCity(prediction.getCity());
            existing.setPredictedQuantity(
                    prediction.getPredictedQuantity());
            existing.setPredictionDate(
                    prediction.getPredictionDate());
            existing.setRiskLevel(
                    prediction.getRiskLevel());

            return aiPredictionRepository.save(existing);
        }

        return null;
    }

    // Delete AI Prediction
    public void deletePrediction(Long id) {

        aiPredictionRepository.deleteById(id);
    }

    // AI Blood Demand Prediction
    public AiPrediction predictDemand(
            String bloodGroup,
            String city) {

        List<BloodRequest> requests =
                bloodRequestRepository.findAll();

        int totalQuantity = 0;
        int requestCount = 0;

        // Find historical blood requests
        for (BloodRequest request : requests) {

            if (request.getBloodGroup() != null
                    && request.getCity() != null
                    && request.getBloodGroup()
                            .equalsIgnoreCase(bloodGroup)
                    && request.getCity()
                            .equalsIgnoreCase(city)) {

                totalQuantity += request.getQuantity();
                requestCount++;
            }
        }

        int predictedQuantity;

        // If historical data exists
        if (requestCount > 0) {

            double averageDemand =
                    (double) totalQuantity / requestCount;

            // Predict 20% increase
            predictedQuantity =
                    (int) Math.ceil(averageDemand * 1.20);

        } else {

            // Default prediction
            predictedQuantity = 10;
        }

        // Calculate risk level
        String riskLevel;

        if (predictedQuantity >= 30) {

            riskLevel = "High";

        } else if (predictedQuantity >= 15) {

            riskLevel = "Medium";

        } else {

            riskLevel = "Low";
        }

        // Create prediction object
        AiPrediction prediction =
                new AiPrediction();

        prediction.setBloodGroup(bloodGroup);
        prediction.setCity(city);
        prediction.setPredictedQuantity(
                predictedQuantity);

        prediction.setPredictionDate(
                java.time.LocalDate.now().toString());

        prediction.setRiskLevel(riskLevel);

        // Save prediction in database
        return aiPredictionRepository.save(prediction);
    }
}