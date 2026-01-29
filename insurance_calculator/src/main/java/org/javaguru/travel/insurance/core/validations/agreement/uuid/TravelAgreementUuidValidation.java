package org.javaguru.travel.insurance.core.validations.agreement.uuid;

import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.Optional;

public interface TravelAgreementUuidValidation {

    Optional<ValidationErrorDTO> validate (String uuid);

}
