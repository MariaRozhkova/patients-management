package org.hospital.management.patients.exceptions;

/**
 * Exception is thrown when provided pagination token is incorrect.
 */
public class IncorrectPaginationTokenException extends RuntimeException {

    public IncorrectPaginationTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
