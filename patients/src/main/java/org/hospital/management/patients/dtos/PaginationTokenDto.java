package org.hospital.management.patients.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Dto to store pagination token.
 */
public record PaginationTokenDto(UUID id, LocalDateTime date) {

}
