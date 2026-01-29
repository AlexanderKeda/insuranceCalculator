package org.javaguru.travel.insurance.core.underwriting.calculators;

import org.springframework.stereotype.Component;

@Component
class TravelEvacuationRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    @Override
    public String getRiskIc() {
        return "TRAVEL_EVACUATION";
    }
}
