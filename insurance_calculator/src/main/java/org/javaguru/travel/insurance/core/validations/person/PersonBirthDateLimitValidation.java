package org.javaguru.travel.insurance.core.validations.person;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.AgeCalculator;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.MODULE)
class PersonBirthDateLimitValidation implements TravelPersonFieldsValidation {

    private final ValidationErrorFactory validationErrorFactory;
    private final AgeCalculator ageCalculator;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return (person.personBirthDate() != null
                && ageCalculator.calculate(person.personBirthDate()) > 150L)
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_14"))
                : Optional.empty();
    }
}
