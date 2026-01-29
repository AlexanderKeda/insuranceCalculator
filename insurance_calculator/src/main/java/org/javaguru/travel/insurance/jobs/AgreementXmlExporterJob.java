package org.javaguru.travel.insurance.jobs;

import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.api.command.TravelExportAgreementsToXmlCoreResult;
import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreResult;
import org.javaguru.travel.insurance.core.services.travel.get.uuids.TravelGetNotExportedAgreementUuidsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

@Slf4j
@Component
@ConditionalOnProperty(
        name = "agreement.xml.exporter.job.enabled",
        havingValue = "true",
        matchIfMissing = false
)
class AgreementXmlExporterJob {

    private final TravelGetNotExportedAgreementUuidsService travelGetNotExportedAgreementUuidsService;
    private final AgreementXmlExporter agreementXmlExporter;
    private final Semaphore semaphore;

    public AgreementXmlExporterJob(
            TravelGetNotExportedAgreementUuidsService travelGetNotExportedAgreementUuidsService,
            AgreementXmlExporter agreementXmlExporter,
            @Value("${agreement.xml.exporter.job.thread.count:1}") int fileExportExecutorPoolSize) {
        this.travelGetNotExportedAgreementUuidsService = travelGetNotExportedAgreementUuidsService;
        this.agreementXmlExporter = agreementXmlExporter;
        this.semaphore = new Semaphore(fileExportExecutorPoolSize * 6);
    }

    @Scheduled(fixedRate = 60000)
    void doJob() {
        log.info("AgreementXmlExporterJob started");
        var agreementUuids = getAgreementUuids(new TravelGetNotExportedAgreementUuidsCoreCommand());
        List<CompletableFuture<TravelExportAgreementsToXmlCoreResult>> futures = new ArrayList<>(agreementUuids.size());
        for(String uuid: agreementUuids) {
            try {
                semaphore.acquire();
                CompletableFuture<TravelExportAgreementsToXmlCoreResult> future =
                        agreementXmlExporter.exportAgreementToXml(uuid);
                future.whenComplete((res, ex) -> semaphore.release());
                futures.add(future);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("AgreementXmlExporterJob was interrupted", e);
                return;
            }
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .join();
        log.info("AgreementXmlExporterJob finished");
    }

    private List<String> getAgreementUuids(TravelGetNotExportedAgreementUuidsCoreCommand command) {
        TravelGetNotExportedAgreementUuidsCoreResult result = travelGetNotExportedAgreementUuidsService
                .getNotExportedAgreementUuids(command);
        if (result.hasErrors()) {
            result.errors().forEach(
                    err -> log.warn("Agreement UUIDs request error: code={}, description={}",
                            err.errorCode(),
                            err.description())
            );
            log.error("AgreementXmlExporterJob was stopped due to validation errors");
            return List.of();
        }
        return result.agreementUuids();
    }
}
