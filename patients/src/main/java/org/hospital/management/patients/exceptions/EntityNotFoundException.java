package org.hospital.management.patients.exceptions;


/**
 * Exception is thrown when entity not found.
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
