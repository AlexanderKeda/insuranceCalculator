package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.underwriting.calculators.TravelRiskPremiumCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.MODULE)
class TravelMedicalRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    private final List<MedicalRiskElement> medicalRiskElements;

    @Override
    public BigDecimal calculatePremium(AgreementDTO agreement, PersonDTO person) {
        return medicalRiskElements.stream()
                .map(medicalRiskElement -> medicalRiskElement.calculate(agreement, person))
                .reduce(BigDecimal.ONE, BigDecimal::multiply)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_MEDICAL";
    }

}
