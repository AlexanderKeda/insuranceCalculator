package org.javaguru.travel.insurance.core.services.travel.calculate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import org.javaguru.travel.insurance.core.domain.entities.SelectedRiskEntity;
import org.javaguru.travel.insurance.core.repositories.entities.SelectedRisksEntityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SelectedRiskEntityFactory {

    private final SelectedRisksEntityRepository selectedRisksEntityRepository;

    List<SelectedRiskEntity> createSelectedRiskEntities (
            AgreementEntity agreementEntity,
            AgreementDTO agreementDTO
    ) {
        return agreementDTO.selectedRisks().stream()
                .map(riskIc -> createSelectedRiskEntity(agreementEntity, riskIc))
                .toList();
    }

    private SelectedRiskEntity createSelectedRiskEntity(
            AgreementEntity agreementEntity,
            String riskIc
    ) {
        var selectedRiskEntity = new SelectedRiskEntity(
                null,
                agreementEntity,
                riskIc
        );
        return selectedRisksEntityRepository.save(selectedRiskEntity);
    }



}
