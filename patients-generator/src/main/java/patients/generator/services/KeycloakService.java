package patients.generator.services;

/**
 * Service for working with Keycloak server.
 */
public interface KeycloakService {

    /**
     * Fetches access token from Keycloak server based on username and password.
     */
    String fetchAccessToken(String username, String password);
}
