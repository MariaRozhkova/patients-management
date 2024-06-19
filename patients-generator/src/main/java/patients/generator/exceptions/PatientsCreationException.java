package patients.generator.exceptions;

public class PatientsCreationException extends RuntimeException{

    public PatientsCreationException(String message) {
        super(message);
    }

    public PatientsCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
