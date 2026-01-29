package org.javaguru.travel.insurance.core.api.dto;


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

    public AgreementDTO(
            LocalDate agreementDateFrom,
            LocalDate agreementDateTo,
            String country,
            List<String> selectedRisks,
            List<PersonDTO> persons
    ) {
        this("",
                agreementDateFrom,
                agreementDateTo,
                country,
                selectedRisks,
                persons,
                new BigDecimal("-1")
        );
    }

    public AgreementDTO withPersonsAndPremiumAndUuid(List<PersonDTO> persons,
                                                     BigDecimal agreementPremium,
                                                     String uuid) {
        return new AgreementDTO(
                uuid,
                this.agreementDateFrom,
                this.agreementDateTo,
                this.country,
                this.selectedRisks,
                persons,
                agreementPremium
        );
    }
}
