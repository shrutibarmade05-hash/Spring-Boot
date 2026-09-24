package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Patient;
import com.example.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Patient updatePatient(Long id, Patient patient) {

        Patient existing = patientRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(patient.getName());
            existing.setAge(patient.getAge());
            existing.setGender(patient.getGender());
            existing.setBloodGroup(patient.getBloodGroup());
            existing.setPhone(patient.getPhone());
            existing.setEmail(patient.getEmail());
            existing.setCity(patient.getCity());
            existing.setHospitalName(patient.getHospitalName());

            return patientRepository.save(existing);
        }

        return null;
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}