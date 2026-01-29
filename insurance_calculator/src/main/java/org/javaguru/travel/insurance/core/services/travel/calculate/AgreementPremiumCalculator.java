package org.javaguru.travel.insurance.core.services.travel.calculate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgreementPremiumCalculator {
    private final AgreementPersonsPremiumCalculator personsPremiumCalculator;

    AgreementDTO calculateAgreementPremiums(AgreementDTO agreement) {
        var updatedPersons = personsPremiumCalculator.calculate(agreement);
        BigDecimal agreementPremium = getAgreementPremium(updatedPersons);
        return agreement.withPersonsAndPremiumAndUuid(updatedPersons, agreementPremium, UUID.randomUUID().toString());
    }

    private BigDecimal getAgreementPremium(List<PersonDTO> persons) {
        return getAgreementCalculatedRisksStream(persons)
                .map(RiskDTO::premium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Stream<RiskDTO> getAgreementCalculatedRisksStream(List<PersonDTO> persons) {
        return persons.stream()
                .map(PersonDTO::risks)
                .flatMap(Collection::stream);
    }
}