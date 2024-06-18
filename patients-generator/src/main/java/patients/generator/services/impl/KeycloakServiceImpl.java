package patients.generator.services.impl;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import patients.generator.dtos.KeycloakAuthResponse;
import patients.generator.exceptions.KeycloakServiceException;
import patients.generator.services.KeycloakService;

@Slf4j
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private static final String KEYCLOAK_SERVICE_URL =
        "http://localhost:8080/realms/PatientsManagementRealm/protocol/openid-connect/token";
    private static final String CONTENT_TYPE_HEADER_NAME = "Content-type";
    private static final String CONTENT_TYPE_HEADER_VALUE = "application/x-www-form-urlencoded";
    private static final String CLIENT_ID = "patients-management-app";
    private static final String GRANT_TYPE = "password";

    private final Gson gson;
    private final HttpClient httpClient;

    @Override
    public String fetchAccessToken(String username, String password) {
        var requestBody = prepareRequestBody(username, password);

        try {
            var httpResponse = httpClient.send(
                prepareHttpRequest(requestBody),
                HttpResponse.BodyHandlers.ofString()
            );

            int statusCode = httpResponse.statusCode();
            if (statusCode == 200) {
                var authResponse = gson.fromJson(
                    httpResponse.body(),
                    KeycloakAuthResponse.class
                );

                return authResponse.getAccessToken();
            }

            throw new KeycloakServiceException("Error occurred while fetching accessToken");
        } catch (IOException | InterruptedException e) {
            throw new KeycloakServiceException("Error occurred while fetching accessToken", e);
        }
    }

    private String prepareRequestBody(String username, String password) {
        var parameters = new HashMap<String, String>();
        parameters.put("client_id", CLIENT_ID);
        parameters.put("username", username);
        parameters.put("password", password);
        parameters.put("grant_type", GRANT_TYPE);

        return parameters.entrySet()
            .stream()
            .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
            .collect(Collectors.joining("&"));
    }

    private HttpRequest prepareHttpRequest(String requestBody) {
        return HttpRequest.newBuilder()
            .uri(URI.create(KEYCLOAK_SERVICE_URL))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .header(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE)
            .build();
    }
}
