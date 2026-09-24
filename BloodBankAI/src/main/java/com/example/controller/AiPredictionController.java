package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.AiPrediction;
import com.example.service.AiPredictionService;

@RestController
@RequestMapping("/api/ai-predictions")
public class AiPredictionController {

    private final AiPredictionService aiPredictionService;

    public AiPredictionController(
            AiPredictionService aiPredictionService) {

        this.aiPredictionService = aiPredictionService;
    }

    // Add AI Prediction
    @PostMapping
    public AiPrediction addPrediction(
            @RequestBody AiPrediction prediction) {

        return aiPredictionService.addPrediction(prediction);
    }

    // Get All AI Predictions
    @GetMapping
    public List<AiPrediction> getAllPredictions() {

        return aiPredictionService.getAllPredictions();
    }

    // Get AI Prediction By ID
    @GetMapping("/{id}")
    public AiPrediction getPredictionById(
            @PathVariable Long id) {

        return aiPredictionService.getPredictionById(id);
    }

    // Update AI Prediction
    @PutMapping("/{id}")
    public AiPrediction updatePrediction(
            @PathVariable Long id,
            @RequestBody AiPrediction prediction) {

        return aiPredictionService.updatePrediction(
                id, prediction);
    }

    // Delete AI Prediction
    @DeleteMapping("/{id}")
    public String deletePrediction(
            @PathVariable Long id) {

        aiPredictionService.deletePrediction(id);

        return "AI prediction deleted successfully";
    }

    // AI Blood Demand Prediction
    @GetMapping("/predict")
    public AiPrediction predictDemand(
            @RequestParam String bloodGroup,
            @RequestParam String city) {

        return aiPredictionService.predictDemand(
                bloodGroup, city);
    }
}