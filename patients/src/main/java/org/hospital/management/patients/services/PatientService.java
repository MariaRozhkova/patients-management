package org.hospital.management.patients.services;

import java.util.UUID;
import org.hospital.management.patients.dtos.PaginationResponse;
import org.hospital.management.patients.dtos.PatientCreateDto;
import org.hospital.management.patients.dtos.PatientDto;

public interface PatientService {

    PaginationResponse<PatientDto> findAll(String nextPageToken, int pageSize);

    PatientDto findById(UUID id);

    PatientDto create(PatientCreateDto patient);

    PatientDto update(UUID id, PatientCreateDto patientUpdateDto);

    PatientDto deleteById(UUID id);
}
