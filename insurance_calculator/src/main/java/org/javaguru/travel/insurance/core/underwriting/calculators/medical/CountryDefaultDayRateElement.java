package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.MODULE)
class CountryDefaultDayRateElement implements MedicalRiskElement {

    private final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Override
    public BigDecimal calculate(AgreementDTO agreement, PersonDTO person) {
        var dayRateOpt = countryDefaultDayRateRepository
                .findByCountryIc(agreement.country());
        if (dayRateOpt.isEmpty()) {
            throw new RuntimeException("Country day rate not found by countryIC=" + agreement.country());
        }
        return dayRateOpt
                .get()
                .getDefaultDayRate();
    }
}
