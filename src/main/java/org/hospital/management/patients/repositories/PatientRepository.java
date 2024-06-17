package org.hospital.management.patients.repositories;

import org.hospital.management.patients.entities.Patient;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

}
