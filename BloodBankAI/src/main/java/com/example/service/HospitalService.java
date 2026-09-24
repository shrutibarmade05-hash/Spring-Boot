package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Hospital;
import com.example.repository.HospitalRepository;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public Hospital addHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Hospital getHospitalById(Long id) {
        return hospitalRepository.findById(id).orElse(null);
    }

    public Hospital updateHospital(Long id, Hospital hospital) {

        Hospital existing = hospitalRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(hospital.getName());
            existing.setPhone(hospital.getPhone());
            existing.setEmail(hospital.getEmail());
            existing.setCity(hospital.getCity());
            existing.setAddress(hospital.getAddress());
            existing.setContactPerson(hospital.getContactPerson());

            return hospitalRepository.save(existing);
        }

        return null;
    }

    public void deleteHospital(Long id) {
        hospitalRepository.deleteById(id);
    }
}