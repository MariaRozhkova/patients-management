package org.hospital.management.patients.controllers;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hospital.management.patients.dtos.PaginationResponse;
import org.hospital.management.patients.dtos.PatientCreateDto;
import org.hospital.management.patients.dtos.PatientDto;
import org.hospital.management.patients.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @PreAuthorize("hasAnyRole('PATIENT', 'PRACTITIONER')")
    public ResponseEntity<PaginationResponse<PatientDto>> findAll(
        @RequestParam(name = "size", required = false, defaultValue = "10") int pageSize,
        @RequestParam(name = "nextPageToken", required = false) String nextPageToken
    ) {
        return ResponseEntity.ok(patientService.findAll(nextPageToken, pageSize));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATIENT', 'PRACTITIONER')")
    public ResponseEntity<PatientDto> findById(@PathVariable("id") UUID id) {
        var patient = patientService.findById(id);

        return ResponseEntity.ok(patient);
    }

    @PostMapping
    @PreAuthorize("hasRole('PRACTITIONER')")
    public ResponseEntity<PatientDto> create(@RequestBody @Valid PatientCreateDto patientCreateDto) {
        var createdPatient = patientService.create(patientCreateDto);

        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PRACTITIONER')")
    public ResponseEntity<PatientDto> update(
        @PathVariable("id") UUID id,
        @RequestBody @Valid PatientCreateDto patientUpdateDto
    ) {
        var updatedPatient = patientService.update(id, patientUpdateDto);

        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PRACTITIONER')")
    public ResponseEntity<PatientDto> deleteById(@PathVariable("id") UUID id) {
        var deletedPatient = patientService.deleteById(id);

        return ResponseEntity.ok(deletedPatient);
    }
}
