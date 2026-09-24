package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Donation;
import com.example.repository.DonationRepository;

@Service
public class DonationService {

    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Donation addDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public Donation getDonationById(Long id) {
        return donationRepository.findById(id).orElse(null);
    }

    public Donation updateDonation(Long id, Donation donation) {

        Donation existing = donationRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setDonorName(donation.getDonorName());
            existing.setBloodGroup(donation.getBloodGroup());
            existing.setQuantity(donation.getQuantity());
            existing.setDonationDate(donation.getDonationDate());
            existing.setHospitalName(donation.getHospitalName());
            existing.setCity(donation.getCity());

            return donationRepository.save(existing);
        }

        return null;
    }

    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }
}