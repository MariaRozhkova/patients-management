package org.hospital.management.patients.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record PageTokenDto(UUID id, LocalDateTime date) {

}
