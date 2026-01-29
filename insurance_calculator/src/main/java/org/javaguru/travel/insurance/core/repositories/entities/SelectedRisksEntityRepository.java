package org.javaguru.travel.insurance.core.repositories.entities;

import org.javaguru.travel.insurance.core.domain.entities.SelectedRiskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectedRisksEntityRepository
        extends JpaRepository<SelectedRiskEntity, Long> {

    List<SelectedRiskEntity> findByAgreementId(Long agreementId);

}
