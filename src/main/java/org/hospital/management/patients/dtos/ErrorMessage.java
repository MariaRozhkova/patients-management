package org.hospital.management.patients.dtos;

import java.util.UUID;

public record ErrorMessage(UUID logId, String message) {

}
