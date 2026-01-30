package org.javaguru.doc.generator.core.dto;

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

}
