package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.person.TravelPersonFieldsValidation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelPersonFieldsValidator {

    private final List<TravelPersonFieldsValidation> personFieldsValidations;

    List<ValidationErrorDTO> validate(AgreementDTO agreement) {
        return agreement != null && agreement.persons() != null
                ? validatePersons(agreement)
                : List.of();
    }

    private List<ValidationErrorDTO> validatePersons(AgreementDTO agreement) {
        return agreement.persons().stream()
                .map(person -> collectPersonErrors(agreement, person))
                .flatMap(List::stream)
                .toList();
    }

    private List<ValidationErrorDTO> collectPersonErrors(AgreementDTO agreement ,PersonDTO person) {
        List<ValidationErrorDTO> singleErrors = collectSingleErrors(agreement, person);
        List<ValidationErrorDTO> listErrors = collectListErrors(agreement, person);

        return concatenateLists(singleErrors, listErrors);
    }

    private List<ValidationErrorDTO> collectSingleErrors(AgreementDTO agreement, PersonDTO person) {
        return personFieldsValidations.stream()
                .map(validation -> validation.validate(agreement, person))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private List<ValidationErrorDTO> collectListErrors(AgreementDTO agreement, PersonDTO person) {
        return personFieldsValidations.stream()
                .map(validation -> validation.validateList(agreement, person))
                .flatMap(List::stream)
                .toList();
    }

    private List<ValidationErrorDTO> concatenateLists(List<ValidationErrorDTO> list1, List<ValidationErrorDTO> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .toList();
    }

}
