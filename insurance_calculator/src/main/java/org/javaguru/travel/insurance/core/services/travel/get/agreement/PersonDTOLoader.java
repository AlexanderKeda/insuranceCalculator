package org.javaguru.travel.insurance.core.services.travel.get.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.domain.entities.AgreementPersonEntity;
import org.javaguru.travel.insurance.core.domain.entities.AgreementPersonRiskEntity;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementPersonEntityRepository;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementPersonRiskEntityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PersonDTOLoader {

    private final AgreementPersonEntityRepository agreementPersonEntityRepository;
    private final AgreementPersonRiskEntityRepository agreementPersonRiskEntityRepository;

    List<PersonDTO> getPersonsByAgreementId (Long agreementId) {
        var agreementPersonEntities = agreementPersonEntityRepository.findByAgreementId(agreementId);
        return agreementPersonEntities.stream()
                .map(this::getPersonData)
                .toList();
    }

    private PersonDTO getPersonData (AgreementPersonEntity agreementPersonEntity) {
        var personEntity = agreementPersonEntity.getPerson();
        var agreementPersonRiskEntities = agreementPersonRiskEntityRepository
                .findByAgreementPersonId(agreementPersonEntity.getId());
        var risks = agreementPersonRiskEntities.stream()
                .map(this::transformAgreementPersonRiskEntity2RiskDTO)
                .toList();
        return new PersonDTO(
                personEntity.getFirstName(),
                personEntity.getLastName(),
                personEntity.getPersonCode(),
                agreementPersonEntity.getMedicalRiskLimitLevel(),
                risks,
                personEntity.getBirthDate()
        );
    }

    private RiskDTO transformAgreementPersonRiskEntity2RiskDTO (AgreementPersonRiskEntity agreementPersonRiskEntity) {
        return new RiskDTO(
                agreementPersonRiskEntity.getRiskIc(),
                agreementPersonRiskEntity.getPremium()
        );
    }

}
