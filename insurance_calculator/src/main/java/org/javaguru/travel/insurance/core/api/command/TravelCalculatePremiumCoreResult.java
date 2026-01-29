package org.javaguru.travel.insurance.core.api.command;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.List;

public record TravelCalculatePremiumCoreResult(List<ValidationErrorDTO> errors, AgreementDTO agreement) {

    public TravelCalculatePremiumCoreResult(List<ValidationErrorDTO> errors) {
        this(errors, null);
    }

    public TravelCalculatePremiumCoreResult(AgreementDTO agreement) {
        this(null, agreement);
    }

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }
}
