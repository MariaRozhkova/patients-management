package org.hospital.management.patients.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.hospital.management.patients.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * A repository for {@link Patient}.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    @Query(value = "select * from patients_management.patients " +
        "where (created_at, id) > (:createdAt, :id) " +
        "order by created_at, id " +
        "limit :pageSize",
        nativeQuery = true)
    List<Patient> findAllPageable(
        @Param("createdAt") LocalDateTime createdAt,
        @Param("id") UUID id,
        @Param("pageSize") int pageSize
    );
}
