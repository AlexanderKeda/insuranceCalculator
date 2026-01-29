package org.javaguru.travel.insurance.core.services.travel.export.agreement;

import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.api.command.TravelExportAgreementsToXmlCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelExportAgreementsToXmlCoreResult;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.domain.entities.AgreementXmlExportEntity;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementXmlExportRepository;
import org.javaguru.travel.insurance.core.services.travel.get.agreement.TravelGetAgreementService;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.util.XmlFileExporter;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Component
public class TravelExportAgreementToXmlServiceImpl implements TravelExportAgreementToXmlService {

    private final String directoryPath;
    private final XmlFileExporter xmlFileExporter;
    private final TravelGetAgreementService travelGetAgreementService;
    private final AgreementXmlExportRepository agreementXmlExportRepository;
    private final ValidationErrorFactory validationErrorFactory;

    public TravelExportAgreementToXmlServiceImpl(
            @Value("${agreement.xml.exporter.job.path:export}") String directoryPath,
            XmlFileExporter xmlFileExporter,
            TravelGetAgreementService travelGetAgreementService,
            AgreementXmlExportRepository agreementXmlExportRepository,
            ValidationErrorFactory validationErrorFactory
    ) {
        this.directoryPath = directoryPath;
        this.xmlFileExporter = xmlFileExporter;
        this.travelGetAgreementService = travelGetAgreementService;
        this.agreementXmlExportRepository = agreementXmlExportRepository;
        this.validationErrorFactory = validationErrorFactory;
        Path path = Path.of(directoryPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public TravelExportAgreementsToXmlCoreResult export(TravelExportAgreementsToXmlCoreCommand command) {
        TravelExportAgreementsToXmlCoreResult result;
        String uuid = (command == null || command.agreementUuid() == null)
                ? ""
                : command.agreementUuid();
        var getAgreementCoreResult = travelGetAgreementService.getAgreement(
                new TravelGetAgreementCoreCommand(uuid)
        );
        if (getAgreementCoreResult.hasErrors()) {
            result = new TravelExportAgreementsToXmlCoreResult(getAgreementCoreResult.errors());
        } else {
            try {
                xmlFileExporter.writeToXmlFile(getAgreementCoreResult.agreement(), createPath(uuid));
                saveExportedAgreementUuid(uuid);
                result = new TravelExportAgreementsToXmlCoreResult(List.of());
            } catch (IOException e) {
                result = new TravelExportAgreementsToXmlCoreResult(
                        List.of(validationErrorFactory
                                .buildError(
                                        "ERROR_CODE_21", List.of(new Placeholder("UUID", uuid
                                        ))))
                );
            }
        }
        return result;
    }

    private Path createPath(String uuid) {
        return Path.of(directoryPath +
                "/agreement-" +
                uuid +
                ".xml");
    }

    private AgreementXmlExportEntity saveExportedAgreementUuid(String uuid) {
        var agreementXmlExportEntity = new AgreementXmlExportEntity(null, uuid, true);
        return agreementXmlExportRepository.save(agreementXmlExportEntity);
    }
}
