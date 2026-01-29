package org.javaguru.travel.insurance.core.services.travel.calculate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgreementPersonsPremiumCalculator {
    private final TravelPremiumUnderwriting underwriting;

    List<PersonDTO> calculate(AgreementDTO agreement) {
        return agreement.persons().stream()
                .map(person -> calculatePersonRiskPremiums(agreement, person))
                .toList();
    }

    private PersonDTO calculatePersonRiskPremiums(AgreementDTO agreement, PersonDTO person) {
        var personResult = underwriting.calculatePremium(agreement, person);
        return person.withRisks(personResult.riskPremiums());
    }

}
