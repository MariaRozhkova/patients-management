package org.hospital.management.patients.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaginationTokenDto(UUID id, LocalDateTime date) {

}
