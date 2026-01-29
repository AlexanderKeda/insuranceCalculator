package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.repositories.AgeCoefficientRepository;
import org.javaguru.travel.insurance.core.util.AgeCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class AgeCoefficientElement implements MedicalRiskElement {

    private final boolean medicalRiskAgeCoefficientEnabled;
    private final AgeCalculator ageCalculator;
    private final AgeCoefficientRepository ageCoefficientRepository;

    AgeCoefficientElement(
            @Value("${medical.risk.age.coefficient.enabled:false}") boolean medicalRiskAgeCoefficientEnabled,
            AgeCalculator ageCalculator,
            AgeCoefficientRepository ageCoefficientRepository
    ) {
        this.medicalRiskAgeCoefficientEnabled = medicalRiskAgeCoefficientEnabled;
        this.ageCalculator = ageCalculator;
        this.ageCoefficientRepository = ageCoefficientRepository;
    }

    @Override
    public BigDecimal calculate(AgreementDTO agreement, PersonDTO person) {
        return isMedicalRiskAgeCoefficientEnabled()
                ? getAgeCoefficient(person)
                : getDefaultCoefficient();
    }

    private BigDecimal getAgeCoefficient(PersonDTO person) {
        var ageCoefficientOpt = ageCoefficientRepository
                .findByAge(ageCalculator.calculate(person.personBirthDate()));
        if (ageCoefficientOpt.isEmpty()) {
            throw new RuntimeException("Age coefficient not found by birth date=" + person.personBirthDate());
        }
        return ageCoefficientOpt
                .get()
                .getCoefficient();
    }

    private boolean isMedicalRiskAgeCoefficientEnabled() {
        return medicalRiskAgeCoefficientEnabled;
    }

    private BigDecimal getDefaultCoefficient() {
        return BigDecimal.ONE;
    }
}
