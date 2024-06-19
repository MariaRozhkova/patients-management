package org.hospital.management.patients.dtos;

import java.util.UUID;

/**
 * Dto to store error message.
 */
public record ErrorMessage(UUID logId, String message) {

}
