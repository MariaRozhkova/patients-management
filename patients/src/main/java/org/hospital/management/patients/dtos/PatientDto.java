package org.hospital.management.patients.dtos;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hospital.management.patients.enums.Gender;

/**
 * Dto to store patient dto.
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PatientDto {

    private UUID id;
    private String name;
    private Gender gender;
    private LocalDateTime birthDate;
}
