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
class PersonLastNameFormatValidation implements TravelPersonFieldsValidation {

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return (person.personLastName() == null || person.personLastName().isBlank() || isFormatCorrect(person.personLastName()))
                ? Optional.empty()
                : Optional.of(buildError());
    }

    private boolean isFormatCorrect(String personLastName) {
        return personLastName.matches("[A-Za-z- ]{1,200}");
    }

    private ValidationErrorDTO buildError() {
        return validationErrorFactory.buildError("ERROR_CODE_24");
    }
}
