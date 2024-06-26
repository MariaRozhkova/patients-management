package patients.generator.dtos;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import patients.generator.enums.Gender;

/**
 * Dto for creating patient.
 */
@Data
@AllArgsConstructor
public class PatientCreateDto {

    private String name;
    private Gender gender;
    private LocalDateTime birthDate;
}
