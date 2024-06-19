package org.hospital.management.patients.services;

import java.util.UUID;
import org.hospital.management.patients.dtos.PaginationResponse;
import org.hospital.management.patients.dtos.PatientCreateDto;
import org.hospital.management.patients.dtos.PatientDto;
import org.hospital.management.patients.entities.Patient;

/**
 * Service for working with {@link Patient}.
 */
public interface PatientService {

    /**
     * Finds all instances of {@link Patient} by next page token and page size.
     */
    PaginationResponse<PatientDto> findAll(String nextPageToken, int pageSize);

    /**
     * Finds an instance of {@link Patient} by id.
     */
    PatientDto findById(UUID id);

    /**
     * Creates an instance of {@link Patient}.
     */
    PatientDto create(PatientCreateDto patient);

    /**
     * Updates an instance of {@link Patient} if patient exists.
     */
    PatientDto update(UUID id, PatientCreateDto patientUpdateDto);

    /**
     * Deletes an instance of {@link Patient} by id.
     */
    PatientDto deleteById(UUID id);
}
