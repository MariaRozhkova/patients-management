package org.hospital.management.patients.services;

import org.hospital.management.patients.dtos.PatientCreateDto;
import org.hospital.management.patients.dtos.PatientDto;
import java.util.List;
import java.util.UUID;

public interface PatientService {

    List<PatientDto> findAll();

    PatientDto findById(UUID id);

    PatientDto create(PatientCreateDto patient);

    PatientDto update(UUID id, PatientCreateDto patientUpdateDto);

    PatientDto deleteById(UUID id);
}
