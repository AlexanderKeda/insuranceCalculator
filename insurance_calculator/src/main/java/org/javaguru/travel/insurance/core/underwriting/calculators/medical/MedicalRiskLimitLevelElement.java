package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class MedicalRiskLimitLevelElement implements MedicalRiskElement {

    private final boolean medicalRiskLimitLevelEnabled;
    private final MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    MedicalRiskLimitLevelElement(
            @Value("${medical.risk.limit.level.enabled:false}") boolean medicalRiskLimitLevelEnabled,
            MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository
    ) {
        this.medicalRiskLimitLevelEnabled = medicalRiskLimitLevelEnabled;
        this.medicalRiskLimitLevelRepository = medicalRiskLimitLevelRepository;
    }

    @Override
    public BigDecimal calculate(AgreementDTO agreement, PersonDTO person) {
        return isMedicalRiskLimitLevelEnabled()
                ? getMedicalRiskLimitLevelCoefficient(agreement, person)
                : getDefaultCoefficient();
    }

    private boolean isMedicalRiskLimitLevelEnabled() {
        return medicalRiskLimitLevelEnabled;
    }

    private BigDecimal getMedicalRiskLimitLevelCoefficient(AgreementDTO agreement, PersonDTO person) {
        var medicalRiskLimitLevelOpt = medicalRiskLimitLevelRepository
                .findByMedicalRiskLimitLevelIc(person.medicalRiskLimitLevel());
        if (medicalRiskLimitLevelOpt.isEmpty()) {
            throw new RuntimeException("Country day rate not found by countryIC=" + agreement.country());
        }
        return medicalRiskLimitLevelOpt
                .get()
                .getCoefficient();
    }

    private BigDecimal getDefaultCoefficient() {
        return BigDecimal.ONE;
    }
}
