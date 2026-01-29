package org.javaguru.travel.insurance.core.repositories.entities;

import org.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AgreementEntityRepository
        extends JpaRepository<AgreementEntity, Long> {

    boolean existsByUuid(String uuid);

    Optional<AgreementEntity> findByUuid(String uuid);

    @Query("SELECT a.uuid FROM AgreementEntity a")
    List<String> findAllUuids();

    @Query("""
            SELECT a.uuid
            FROM AgreementEntity a
            WHERE a.uuid NOT IN (
            SELECT exp.agreementUuid
            FROM AgreementXmlExportEntity exp
            WHERE exp.alreadyExported = true)
            """)
    List<String> getNotExportedAgreementUuids();
}
