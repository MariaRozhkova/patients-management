package org.hospital.management.patients.services.impl;

import org.hospital.management.patients.dtos.PatientCreateDto;
import org.hospital.management.patients.dtos.PatientDto;
import org.hospital.management.patients.entities.Patient;
import org.hospital.management.patients.exceptions.EntityNotFoundException;
import org.hospital.management.patients.repositories.PatientRepository;
import org.hospital.management.patients.services.PatientService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PatientDto> findAll() {
        return patientRepository.findAll()
            .stream()
            .map(patient -> modelMapper.map(patient, PatientDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public PatientDto findById(UUID id) {
        return patientRepository.findById(id)
            .map(patient -> modelMapper.map(patient, PatientDto.class))
            .orElseThrow(() -> new EntityNotFoundException("Patient with id = '" + id + "' not found"));
    }

    @Override
    public PatientDto create(PatientCreateDto patient) {
        var patientToSave = modelMapper.map(patient, Patient.class);
        var savedPatient = patientRepository.save(patientToSave);

        return modelMapper.map(savedPatient, PatientDto.class);
    }

    @Override
    public PatientDto update(UUID id, PatientCreateDto patientToUpdate) {
        var savedPatient = patientRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Patient with id = '" + id + "' not found"));
        savedPatient.setName(patientToUpdate.getName());
        savedPatient.setGender(patientToUpdate.getGender());
        savedPatient.setBirthDate(patientToUpdate.getBirthDate());

        patientRepository.save(savedPatient);

        return modelMapper.map(savedPatient, PatientDto.class);
    }

    @Override
    public PatientDto deleteById(UUID id) {
        var deletedPatient = findById(id);
        patientRepository.deleteById(id);

        return deletedPatient;
    }
}
