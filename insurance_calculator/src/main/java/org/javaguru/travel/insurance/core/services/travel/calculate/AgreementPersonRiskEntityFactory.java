package org.javaguru.travel.insurance.core.services.travel.calculate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.domain.entities.AgreementPersonEntity;
import org.javaguru.travel.insurance.core.domain.entities.AgreementPersonRiskEntity;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementPersonRiskEntityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgreementPersonRiskEntityFactory {

    private final AgreementPersonRiskEntityRepository agreementPersonRiskEntityRepository;

    List<AgreementPersonRiskEntity> createAgreementPersonRiskEntities(
            AgreementPersonEntity agreementPerson,
            PersonDTO personDTO
    ) {
        return personDTO.risks().stream()
                .map(riskDTO -> createAgreementPersonRiskEntity(agreementPerson, riskDTO))
                .toList();
    }

    private AgreementPersonRiskEntity createAgreementPersonRiskEntity(
            AgreementPersonEntity agreementPerson,
            RiskDTO riskDTO
    ) {
        var agreementPersonRiskEntity = new AgreementPersonRiskEntity(
                null,
                agreementPerson,
                riskDTO.riskIc(),
                riskDTO.premium()
                );
        return agreementPersonRiskEntityRepository.save(agreementPersonRiskEntity);
    }

}
