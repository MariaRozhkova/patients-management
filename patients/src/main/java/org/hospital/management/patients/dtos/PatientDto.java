package org.hospital.management.patients.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hospital.management.patients.enums.Gender;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

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
