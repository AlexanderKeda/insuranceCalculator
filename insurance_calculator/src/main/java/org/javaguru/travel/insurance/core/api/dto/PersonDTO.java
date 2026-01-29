package org.javaguru.travel.insurance.core.api.dto;

import java.time.LocalDate;
import java.util.List;

public record PersonDTO(
        String personFirstName,
        String personLastName,
        String personCode,
        String medicalRiskLimitLevel,
        List<RiskDTO> risks,
        LocalDate personBirthDate
) {

    public PersonDTO {
        risks = risks != null
                ? List.copyOf(risks)
                : null;
    }

    public PersonDTO(String personFirstName,
                     String personLastName,
                     String personCode,
                     LocalDate personBirthDate,
                     String medicalRiskLimitLevel) {
        this(personFirstName, personLastName,personCode , medicalRiskLimitLevel, List.of(), personBirthDate);
    }

    public PersonDTO withRisks(List<RiskDTO> risks) {
        return new PersonDTO(
                this.personFirstName,
                this.personLastName,
                this.personCode,
                this.medicalRiskLimitLevel,
                risks,
                this.personBirthDate);
    }

}
