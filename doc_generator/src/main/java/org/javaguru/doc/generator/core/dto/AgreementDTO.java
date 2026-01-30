package org.javaguru.doc.generator.core.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AgreementDTO(String uuid,
                           LocalDate agreementDateFrom,
                           LocalDate agreementDateTo,
                           String country,
                           List<String> selectedRisks,
                           List<PersonDTO> persons,
                           BigDecimal agreementPremium) {

    public AgreementDTO {
        selectedRisks = selectedRisks != null
                ? List.copyOf(selectedRisks)
                : null;
        persons = persons != null
                ? List.copyOf(persons)
                : null;
    }
}
