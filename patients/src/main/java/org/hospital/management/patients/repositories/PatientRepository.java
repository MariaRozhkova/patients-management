package org.hospital.management.patients.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.hospital.management.patients.entities.Patient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    List<Patient> findAllByCreatedAtGreaterThanEqualAndIdGreaterThan(
        LocalDateTime createdAt,
        UUID id,
        Pageable pageable
    );
}
