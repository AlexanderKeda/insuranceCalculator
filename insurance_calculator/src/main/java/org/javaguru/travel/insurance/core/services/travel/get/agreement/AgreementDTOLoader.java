package org.javaguru.travel.insurance.core.services.travel.get.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.entities.SelectedRiskEntity;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import org.javaguru.travel.insurance.core.repositories.entities.SelectedRisksEntityRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgreementDTOLoader {

    private final AgreementEntityRepository agreementEntityRepository;
    private final SelectedRisksEntityRepository selectedRisksEntityRepository;
    private final PersonDTOLoader personDTOLoader;

    AgreementDTO loadAgreement(String uuid) {
        var agreementEntityOpt = agreementEntityRepository.findByUuid(uuid);
        if (agreementEntityOpt.isEmpty()) {
            throw new RuntimeException("Agreement by uuid not found");
        }
        var agreementEntity = agreementEntityOpt.get();
        var selectedRisksEntities = selectedRisksEntityRepository.findByAgreementId(agreementEntity.getId());
        var risks = selectedRisksEntities.stream().map(SelectedRiskEntity::getRiskIc).toList();
        var persons = personDTOLoader.getPersonsByAgreementId(agreementEntity.getId());
        return new AgreementDTO(
                agreementEntity.getUuid(),
                agreementEntity.getDateFrom(),
                agreementEntity.getDateTo(),
                agreementEntity.getCountry(),
                risks,
                persons,
                agreementEntity.getPremium()
        );
    }

}
