package org.javaguru.travel.insurance.core.api.command;

import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.List;

public record TravelExportAgreementsToXmlCoreResult(List<ValidationErrorDTO> errors) {
    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }
}
