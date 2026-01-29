package org.javaguru.travel.insurance.core.underwriting.calculators;

import org.springframework.stereotype.Component;

@Component
class TravelThirdPartyLiabilityRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    @Override
    public String getRiskIc() {
        return "TRAVEL_THIRD_PARTY_LIABILITY";
    }
}
