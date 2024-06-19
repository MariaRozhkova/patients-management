package patients.generator;

import com.google.gson.GsonBuilder;
import java.net.http.HttpClient;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import patients.generator.configs.LocalDateTimeAdapter;
import patients.generator.services.impl.KeycloakServiceImpl;
import patients.generator.services.impl.PatientsServiceImpl;

@Slf4j
public class PatientsGeneratorApplication {

    public static void main(String[] args) {
        var gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
        var httpClient = HttpClient.newHttpClient();

        var keycloakService = new KeycloakServiceImpl(gson, httpClient);

        var usernameFromArg = args[0];
        var passwordFromArg = args[1];
        var accessToken = keycloakService.fetchAccessToken(usernameFromArg, passwordFromArg);

        var patientsService = new PatientsServiceImpl(gson, httpClient);
        patientsService.createPatients(accessToken);
    }
}
