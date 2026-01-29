package org.javaguru.travel.insurance.core.services.travel.calculate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import org.javaguru.travel.insurance.core.domain.entities.AgreementPersonEntity;
import org.javaguru.travel.insurance.core.domain.entities.PersonEntity;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementPersonEntityRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgreementPersonEntityFactory {

    private final AgreementPersonEntityRepository agreementPersonEntityRepository;

    AgreementPersonEntity createAgreementPersonEntity(
            AgreementEntity agreementEntity,
            PersonEntity personEntity,
            PersonDTO personDTO
    ) {
        var agreementPersonEntity = new AgreementPersonEntity(
                null,
                agreementEntity,
                personEntity,
                personDTO.medicalRiskLimitLevel()
        );
        return agreementPersonEntityRepository.save(agreementPersonEntity);
    }
}
