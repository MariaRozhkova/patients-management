package org.hospital.management.patients.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

/**
 * Patient API.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("patients")
@Tag(name = "Patients", description = "Patients APIs")
public class PatientController {

    private final PatientService patientService;

    /**
     * Find all patients.
     */
    @Operation(summary = "Retrieves patients using pagination token")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            content = {@Content(
                schema = @Schema(implementation = PatientDto.class),
                mediaType = "application/json"
            )}
        ),
        @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('PATIENT', 'PRACTITIONER')")
    public ResponseEntity<PaginationResponse<PatientDto>> findAll(
        @RequestParam(name = "size", required = false, defaultValue = "10") int pageSize,
        @RequestParam(name = "nextPageToken", required = false) String nextPageToken
    ) {
        return ResponseEntity.ok(patientService.findAll(nextPageToken, pageSize));
    }

    /**
     * Find patient by id.
     */
    @Operation(summary = "Retrieves a patient by Id")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            content = {@Content(
                schema = @Schema(implementation = PatientDto.class),
                mediaType = "application/json"
            )}
        ),
        @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATIENT', 'PRACTITIONER')")
    public ResponseEntity<PatientDto> findById(@PathVariable("id") UUID id) {
        var patient = patientService.findById(id);

        return ResponseEntity.ok(patient);
    }

    /**
     * Creates patient.
     */
    @Operation(summary = "Creates a patient")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            content = {@Content(
                schema = @Schema(implementation = PatientDto.class),
                mediaType = "application/json"
            )}
        ),
        @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @PostMapping
    @PreAuthorize("hasRole('PRACTITIONER')")
    public ResponseEntity<PatientDto> create(@RequestBody @Valid PatientCreateDto patientCreateDto) {
        var createdPatient = patientService.create(patientCreateDto);

        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    /**
     * Updates patient.
     */
    @Operation(summary = "Updates a patient by id")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            content = {@Content(
                schema = @Schema(implementation = PatientDto.class),
                mediaType = "application/json"
            )}
        ),
        @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PRACTITIONER')")
    public ResponseEntity<PatientDto> update(
        @PathVariable("id") UUID id,
        @RequestBody @Valid PatientCreateDto patientUpdateDto
    ) {
        var updatedPatient = patientService.update(id, patientUpdateDto);

        return ResponseEntity.ok(updatedPatient);
    }

    /**
     * Deletes patient by id.
     */
    @Operation(summary = "Deletes a patient by id")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            content = {@Content(
                schema = @Schema(implementation = PatientDto.class),
                mediaType = "application/json"
            )}
        ),
        @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PRACTITIONER')")
    public ResponseEntity<PatientDto> deleteById(@PathVariable("id") UUID id) {
        var deletedPatient = patientService.deleteById(id);

        return ResponseEntity.ok(deletedPatient);
    }
}
