package org.hospital.management.patients.exceptions;

public class IncorrectPaginationTokenException extends RuntimeException {

    public IncorrectPaginationTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
