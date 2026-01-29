package org.javaguru.travel.insurance.core.validations.agreement.uuid;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgreementExistenceValidation implements TravelAgreementUuidValidation {

    private final ValidationErrorFactory errorFactory;
    private final AgreementEntityRepository agreementEntityRepository;

    @Override
    public Optional<ValidationErrorDTO> validate(String uuid) {
        return uuid != null && !uuid.isBlank() && !(agreementEntityRepository.existsByUuid(uuid))
                ? Optional.of(buildAgreementExistenceValidationError(uuid))
                : Optional.empty();
    }

    private ValidationErrorDTO buildAgreementExistenceValidationError(String uuid) {
        return errorFactory.buildError(
                "ERROR_CODE_20",
                List.of(new Placeholder("NOT_EXISTING_UUID", uuid))
        );
    }
}
