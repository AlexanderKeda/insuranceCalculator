package org.javaguru.travel.insurance.core.services.travel.export.agreement;

import org.javaguru.travel.insurance.core.api.command.TravelExportAgreementsToXmlCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.domain.entities.AgreementXmlExportEntity;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementXmlExportRepository;
import org.javaguru.travel.insurance.core.services.travel.get.agreement.TravelGetAgreementService;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.util.XmlFileExporter;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelExportAgreementToXmlServiceImplTest {

    @Mock
    private XmlFileExporter xmlFileExporterMock;
    @Mock
    private TravelGetAgreementService travelGetAgreementServiceMock;
    @Mock
    private AgreementXmlExportRepository agreementXmlExportRepositoryMock;
    @Mock
    private ValidationErrorFactory validationErrorFactoryMock;
    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldReturnValidationErrorWhenCommandIsNull() {
        var exportService = createTravelExportAgreementToXmlServiceImpl();
        when(travelGetAgreementServiceMock.getAgreement(new TravelGetAgreementCoreCommand("")))
                .thenReturn(new TravelGetAgreementCoreResult(List.of(new ValidationErrorDTO("code", "error"))));
        var result = exportService.export(null);
        assertTrue(result.hasErrors());
        assertEquals(List.of(new ValidationErrorDTO("code", "error")),
                result.errors());
        Mockito.verifyNoInteractions(xmlFileExporterMock);
        Mockito.verifyNoInteractions(agreementXmlExportRepositoryMock);
        Mockito.verifyNoInteractions(validationErrorFactoryMock);
    }

    @Test
    void shouldReturnValidationErrorWhenUuidNotFound() {
        var exportService = createTravelExportAgreementToXmlServiceImpl();
        when(travelGetAgreementServiceMock.getAgreement(new TravelGetAgreementCoreCommand("uuid")))
                .thenReturn(new TravelGetAgreementCoreResult(List.of(new ValidationErrorDTO("code", "error"))));
        var result = exportService.export(new TravelExportAgreementsToXmlCoreCommand("uuid"));
        assertTrue(result.hasErrors());
        assertEquals(List.of(new ValidationErrorDTO("code", "error")),
                result.errors());
        Mockito.verifyNoInteractions(xmlFileExporterMock);
        Mockito.verifyNoInteractions(agreementXmlExportRepositoryMock);
        Mockito.verifyNoInteractions(validationErrorFactoryMock);
    }

    @Test
    void shouldReturnValidationErrorWhenExportFailed() throws IOException {
        var exportService = createTravelExportAgreementToXmlServiceImpl();
        String uuid = "uuid";
        when(travelGetAgreementServiceMock.getAgreement(new TravelGetAgreementCoreCommand(uuid)))
                .thenReturn(new TravelGetAgreementCoreResult(agreementMock));
        doThrow(new IOException("error"))
                .when(xmlFileExporterMock)
                        .writeToXmlFile(agreementMock, Path.of("~/agreement-"+ uuid +".xml"));
        when(validationErrorFactoryMock.buildError(
                "ERROR_CODE_21",
                List.of(new Placeholder("UUID", uuid))
        ))
                .thenReturn(new ValidationErrorDTO("code", "error"));
        var result = exportService.export(new TravelExportAgreementsToXmlCoreCommand(uuid));
        assertTrue(result.hasErrors());
        assertEquals(List.of(new ValidationErrorDTO("code", "error")),
                result.errors());
        Mockito.verifyNoInteractions(agreementXmlExportRepositoryMock);
    }

    @Test
    void shouldSuccessWhenExportDone() throws IOException {
        var exportService = createTravelExportAgreementToXmlServiceImpl();
        String uuid = "uuid";
        when(travelGetAgreementServiceMock.getAgreement(new TravelGetAgreementCoreCommand(uuid)))
                .thenReturn(new TravelGetAgreementCoreResult(agreementMock));
        var result = exportService.export(new TravelExportAgreementsToXmlCoreCommand(uuid));
        Mockito.verify(xmlFileExporterMock, times(1))
                .writeToXmlFile(agreementMock, Path.of("~/agreement-"+ uuid +".xml"));
        Mockito.verify(agreementXmlExportRepositoryMock, times(1))
                        .save(new AgreementXmlExportEntity(null, uuid, true));
        assertFalse(result.hasErrors());
        Mockito.verifyNoInteractions(validationErrorFactoryMock);
    }


    private TravelExportAgreementToXmlServiceImpl createTravelExportAgreementToXmlServiceImpl() {
        return new TravelExportAgreementToXmlServiceImpl(
                "~",
                xmlFileExporterMock,
                travelGetAgreementServiceMock,
                agreementXmlExportRepositoryMock,
                validationErrorFactoryMock);
    }

}