package org.hospital.management.patients.controllers;

import org.hospital.management.patients.dtos.PatientCreateDto;
import org.hospital.management.patients.dtos.PatientDto;
import org.hospital.management.patients.services.PatientService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDto>> findAll() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> findById(@PathVariable("id") UUID id) {
        var patient = patientService.findById(id);

        return ResponseEntity.ok(patient);
    }

    @PostMapping
    public ResponseEntity<PatientDto> create(@RequestBody @Valid PatientCreateDto patientCreateDto) {
        var createdPatient = patientService.create(patientCreateDto);

        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> update(
        @PathVariable("id") UUID id,
        @RequestBody @Valid PatientCreateDto patientUpdateDto
    ) {
        var updatedPatient = patientService.update(id, patientUpdateDto);

        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PatientDto> deleteById(@PathVariable("id") UUID id) {
        var deletedPatient = patientService.deleteById(id);

        return ResponseEntity.ok(deletedPatient);
    }
}
