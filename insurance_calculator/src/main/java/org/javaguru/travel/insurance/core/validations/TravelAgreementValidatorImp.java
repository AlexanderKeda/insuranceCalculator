package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelAgreementValidatorImp implements TravelAgreementValidator {

    private final TravelAgreementFieldsValidator agreementFieldsValidator;
    private final TravelPersonFieldsValidator personFieldsValidator;

    @Override
    public List<ValidationErrorDTO> validate(AgreementDTO agreement) {
        List<ValidationErrorDTO> agreementErrors = agreementFieldsValidator.validate(agreement);
        List<ValidationErrorDTO> personErrors = personFieldsValidator.validate(agreement);

        return concatenateLists(agreementErrors, personErrors);
    }

    private List<ValidationErrorDTO> concatenateLists(List<ValidationErrorDTO> list1, List<ValidationErrorDTO> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .toList();
    }

}
