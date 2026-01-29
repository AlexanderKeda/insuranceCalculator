package org.javaguru.travel.insurance.core.validations.agreement.uuid;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class EmptyUuidValidation implements TravelAgreementUuidValidation {

    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(String uuid) {
        return uuid == null || uuid.isBlank()
                ? Optional.of(errorFactory.buildError("ERROR_CODE_19"))
                : Optional.empty();
    }
}
