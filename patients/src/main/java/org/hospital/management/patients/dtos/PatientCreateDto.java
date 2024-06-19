package org.hospital.management.patients.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hospital.management.patients.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientCreateDto {

    @NotBlank(message = "The name is required")
    private String name;

    @NotNull(message = "The gender is required")
    private Gender gender;

    @NotNull(message = "The birthDate is required")
    @Past(message = "The birthDate must be in the past")
    private LocalDateTime birthDate;
}
