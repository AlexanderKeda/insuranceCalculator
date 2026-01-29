package org.javaguru.travel.insurance.core.underwriting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelPremiumUnderwritingImp implements TravelPremiumUnderwriting {

    private final SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;

    @Override
    public TravelPremiumCalculationResult calculatePremium(AgreementDTO agreement, PersonDTO person) {
        List<RiskDTO> riskPremiums = selectedRisksPremiumCalculator.calculatePremiumForAllRisks(agreement, person);
        BigDecimal totalPremium = getTotalPremium(riskPremiums);
        return new TravelPremiumCalculationResult(totalPremium, riskPremiums);
    }

    private BigDecimal getTotalPremium(List<RiskDTO> riskPremiums) {
        return riskPremiums.stream()
                .map(RiskDTO::premium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
