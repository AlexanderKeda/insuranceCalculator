package org.javaguru.travel.insurance.core.services.travel.get.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.TravelAgreementUuidValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelGetAgreementServiceImpl implements TravelGetAgreementService {

    private final TravelAgreementUuidValidator agreementUuidValidator;
    private final AgreementDTOLoader agreementDTOLoader;

    @Override
    public TravelGetAgreementCoreResult getAgreement(TravelGetAgreementCoreCommand command) {
        var errors = agreementUuidValidator.validate(command.uuid());
        return errors.isEmpty()
                ? buildCoreResult(command.uuid())
                : buildCoreResult(errors);
    }

    private TravelGetAgreementCoreResult buildCoreResult(List<ValidationErrorDTO> errors) {
        return new TravelGetAgreementCoreResult(errors);
    }

    private TravelGetAgreementCoreResult buildCoreResult(String uuid) {
        var agreement = agreementDTOLoader.loadAgreement(uuid);
        return new TravelGetAgreementCoreResult(agreement);
    }
}
