package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.agreement.TravelAgreementFieldsValidation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelAgreementFieldsValidator {

    private final List<TravelAgreementFieldsValidation> agreementFieldsValidations;

    List<ValidationErrorDTO> validate(AgreementDTO agreement) {
        List<ValidationErrorDTO> singleErrors = collectSingleErrors(agreement);
        List<ValidationErrorDTO> listErrors = collectListErrors(agreement);

        return concatenateLists(singleErrors, listErrors);
    }

    private List<ValidationErrorDTO> collectSingleErrors(AgreementDTO agreement) {
        return agreementFieldsValidations.stream()
                .map(validation -> validation.validate(agreement))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private List<ValidationErrorDTO> collectListErrors(AgreementDTO agreement) {
        return agreementFieldsValidations.stream()
                .map(validation -> validation.validateList(agreement))
                .flatMap(List::stream)
                .toList();
    }

    private List<ValidationErrorDTO> concatenateLists(List<ValidationErrorDTO> list1, List<ValidationErrorDTO> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .toList();
    }

}
