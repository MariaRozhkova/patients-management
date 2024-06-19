package patients.generator.exceptions;

/**
 * An exception can be thrown while working with Keycloak service.
 */
public class KeycloakServiceException extends RuntimeException {

    public KeycloakServiceException(String message) {
        super(message);
    }

    public KeycloakServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
