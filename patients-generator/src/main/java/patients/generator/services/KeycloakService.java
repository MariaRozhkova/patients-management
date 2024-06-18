package patients.generator.services;

public interface KeycloakService {

    String fetchAccessToken(String username, String password); // not secure
}
