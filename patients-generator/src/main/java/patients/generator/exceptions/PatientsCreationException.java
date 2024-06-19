package patients.generator.exceptions;

/**
 * Exception is thrown when error occurred while creating patient via API.
 */
public class PatientsCreationException extends RuntimeException{

    public PatientsCreationException(String message) {
        super(message);
    }

    public PatientsCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
