package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.List;
import java.util.Optional;

public interface TravelPersonFieldsValidation {

    default Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return Optional.empty();
    }

    default List<ValidationErrorDTO> validateList(AgreementDTO agreement, PersonDTO person) {
        return List.of();
    }

}