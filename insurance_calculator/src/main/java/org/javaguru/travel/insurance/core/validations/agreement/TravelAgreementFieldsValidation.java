package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.List;
import java.util.Optional;

public interface TravelAgreementFieldsValidation {

    default Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        return Optional.empty();
    }

    default List<ValidationErrorDTO> validateList(AgreementDTO agreement) {
        return List.of();
    }

}
