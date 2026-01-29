package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.agreement.uuid.TravelAgreementUuidValidation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelAgreementUuidValidatorImpl implements TravelAgreementUuidValidator {

    private final List<TravelAgreementUuidValidation> agreementUuidValidations;

    @Override
    public List<ValidationErrorDTO> validate(String uuid) {
        return agreementUuidValidations.stream()
                .map(validation -> validation.validate(uuid))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
