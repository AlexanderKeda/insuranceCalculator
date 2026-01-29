package org.javaguru.travel.insurance.jobs;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.api.command.TravelExportAgreementsToXmlCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelExportAgreementsToXmlCoreResult;
import org.javaguru.travel.insurance.core.services.travel.export.agreement.TravelExportAgreementToXmlService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgreementXmlExporter {

    private final TravelExportAgreementToXmlService travelExportAgreementToXmlService;

    @Async("agreementXmlExportExecutor")
    public CompletableFuture<TravelExportAgreementsToXmlCoreResult> exportAgreementToXml(String uuid) {
        var command = new TravelExportAgreementsToXmlCoreCommand(uuid);
        var result = travelExportAgreementToXmlService.export(command);
        if (result.hasErrors()) {
            result.errors().forEach(error -> log.warn(error.description()));
        } else {
            log.info("agreement {} was exported successfully", uuid);
        }
        return CompletableFuture.completedFuture(result);
    }


}
