package patients.generator.services;

/**
 * Service to work with patients.
 */
public interface PatientsService {

    /**
     * Creates patients via REST Api.
     */
    void createPatients(String accessToken);
}
