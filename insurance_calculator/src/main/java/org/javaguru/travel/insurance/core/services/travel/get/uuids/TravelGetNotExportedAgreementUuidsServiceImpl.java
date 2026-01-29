package org.javaguru.travel.insurance.core.services.travel.get.uuids;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreResult;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelGetNotExportedAgreementUuidsServiceImpl
        implements TravelGetNotExportedAgreementUuidsService {

    private final AgreementEntityRepository agreementEntityRepository;

    @Override
    public TravelGetNotExportedAgreementUuidsCoreResult getNotExportedAgreementUuids(TravelGetNotExportedAgreementUuidsCoreCommand command) {
        return buildCoreResult();
    }

    private TravelGetNotExportedAgreementUuidsCoreResult buildCoreResult() {
        var agreementUuids = agreementEntityRepository.getNotExportedAgreementUuids();
        return new TravelGetNotExportedAgreementUuidsCoreResult(null, agreementUuids);
    }
}
