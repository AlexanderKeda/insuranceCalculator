package org.javaguru.travel.insurance.core.services.travel.export.agreement;

import org.javaguru.travel.insurance.core.api.command.TravelExportAgreementsToXmlCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelExportAgreementsToXmlCoreResult;

public interface TravelExportAgreementToXmlService {

    TravelExportAgreementsToXmlCoreResult export(TravelExportAgreementsToXmlCoreCommand command);
}
