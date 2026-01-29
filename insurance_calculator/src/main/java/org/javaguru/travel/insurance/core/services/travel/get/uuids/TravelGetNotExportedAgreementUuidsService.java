package org.javaguru.travel.insurance.core.services.travel.get.uuids;

import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreResult;

public interface TravelGetNotExportedAgreementUuidsService {

    TravelGetNotExportedAgreementUuidsCoreResult getNotExportedAgreementUuids(
            TravelGetNotExportedAgreementUuidsCoreCommand command
    );

}
