package org.javaguru.travel.insurance.core.underwriting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.underwriting.calculators.TravelRiskPremiumCalculator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SelectedRisksPremiumCalculator {

    private final List<TravelRiskPremiumCalculator> travelRiskPremiumCalculators;

    List<RiskDTO> calculatePremiumForAllRisks(AgreementDTO agreement, PersonDTO person) {
        return travelRiskPremiumCalculators.stream()
                .filter(riskCalculator -> agreement.selectedRisks().contains(riskCalculator.getRiskIc()))
                .map(riskPremiumCalculator -> new RiskDTO(
                        riskPremiumCalculator.getRiskIc(),
                        riskPremiumCalculator.calculatePremium(agreement, person)))
                .toList();
    }

}
