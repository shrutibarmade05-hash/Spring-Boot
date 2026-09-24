package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Donor;
import com.example.repository.DonorRepository;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    // Add Donor
    public Donor addDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    // Get All Donors
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    // Get Donor By ID
    public Donor getDonorById(Long id) {
        return donorRepository.findById(id).orElse(null);
    }

    // Update Donor
    public Donor updateDonor(Long id, Donor donor) {

        Donor existing = donorRepository.findById(id).orElse(null);

        if (existing != null) {

            existing.setName(donor.getName());
            existing.setBloodGroup(donor.getBloodGroup());
            existing.setAge(donor.getAge());
            existing.setGender(donor.getGender());
            existing.setPhone(donor.getPhone());
            existing.setEmail(donor.getEmail());
            existing.setCity(donor.getCity());
            existing.setAddress(donor.getAddress());
            existing.setAvailable(donor.isAvailable());

            return donorRepository.save(existing);
        }

        return null;
    }

    // Delete Donor
    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }

    // AI Donor Matching
    public List<Donor> findMatchingDonors(
            String bloodGroup,
            String city) {

        return donorRepository
                .findByBloodGroupIgnoreCaseAndCityIgnoreCaseAndAvailable(
                        bloodGroup,
                        city,
                        true);
    }
}