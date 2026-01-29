package org.javaguru.travel.insurance.core.validations.person;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PersonCodeFormatValidation implements TravelPersonFieldsValidation {

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return (person.personCode() == null || person.personCode().isBlank() || isFormatCorrect(person.personCode()))
                ? Optional.empty()
                : Optional.of(buildError());
    }

    private boolean isFormatCorrect(String personCode) {
        return personCode.matches("\\d{6}-\\d{5}");
    }

    private ValidationErrorDTO buildError() {
        return validationErrorFactory.buildError("ERROR_CODE_22");
    }
}
