package patients.generator.services.impl;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import patients.generator.dtos.PatientCreateDto;
import patients.generator.enums.Gender;
import patients.generator.exceptions.PatientsCreationException;
import patients.generator.services.PatientsService;

@RequiredArgsConstructor
@Slf4j
public class PatientsServiceImpl implements PatientsService {

    private static final String PATIENTS_SERVICE_URL = "http://localhost:8083/patients";
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String CONTENT_TYPE_HEADER_NAME = "Content-type";
    private static final String CONTENT_TYPE_HEADER_VALUE = "application/json";
    private static final int CREATED_HTTP_STATUS_CODE = 201;
    private static final int USERS_COUNT = 100;

    private final Gson gson;
    private final HttpClient httpClient;

    public void createPatients(String accessToken) {
        for (int i = 0; i < USERS_COUNT; i++) {
            createPatient(i, accessToken);
        }
    }

    private void createPatient(int i, String accessToken) {
        var birthDate = LocalDateTime.of(1990, 1, 1, 0, 0, 0)
            .plusDays(i);
        var patientToCreate = new PatientCreateDto(
            "patient" + i,
            Gender.randomGender(),
            birthDate
        );
        var requestBody = gson.toJson(patientToCreate);

        try {
            createPatient(accessToken, requestBody, patientToCreate.getName());
        } catch (IOException | InterruptedException ex) {
            throw new PatientsCreationException("Error occurred while creating patient", ex);
        }
    }

    private void createPatient(
        String accessToken,
        String requestBody,
        String name
    ) throws IOException, InterruptedException {
        var httpResponse = httpClient.send(
            prepareHttpRequest(requestBody, accessToken),
            HttpResponse.BodyHandlers.ofString()
        );
        var statusCode = httpResponse.statusCode();
        if (statusCode == CREATED_HTTP_STATUS_CODE) {
            log.info("Created patient with name = {}", name);
        } else {
            String message = String.format(
                "Patient with name = %s was not created. Response status code: %s",
                name,
                statusCode
            );
            throw new PatientsCreationException(message);
        }
    }

    private HttpRequest prepareHttpRequest(String requestBody, String accessToken) {
        return HttpRequest.newBuilder()
            .uri(URI.create(PATIENTS_SERVICE_URL))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .header(
                AUTHORIZATION_HEADER_NAME,
                "Bearer " + accessToken
            )
            .header(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE)
            .build();
    }
}
