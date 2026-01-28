package org.javaguru.travel.insurance.core.services.travel.calculate;

import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.messagebroker.ProposalFileGenerationProducer;
import org.javaguru.travel.insurance.core.validations.TravelAgreementValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumServiceImplTest {

    @Mock
    private AgreementPremiumCalculator agreementPremiumCalculatorMock;
    @Mock
    private TravelAgreementValidator agreementValidatorMock;
    @Mock
    private AgreementEntityFactory agreementEntityFactoryMock;
    @Mock
    private ProposalFileGenerationProducer proposalFileGenerationProducer;

    @InjectMocks
    private TravelCalculatePremiumServiceImpl travelCalculatePremiumService;

    @Mock
    private TravelCalculatePremiumCoreCommand commandMock;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldReturnResultWhenNoErrors() {
        var agreement = new AgreementDTO(LocalDate.now(),
                LocalDate.now(),
                "",
                List.of(),
                List.of());
        when(commandMock.agreement()).thenReturn(agreementMock);
        when(agreementValidatorMock.validate(agreementMock))
                .thenReturn(List.of());
        when(agreementPremiumCalculatorMock.calculateAgreementPremiums(agreementMock))
                .thenReturn(agreement);
        var coreResult = travelCalculatePremiumService.calculatePremium(commandMock);
        assertEquals(new TravelCalculatePremiumCoreResult(agreement),
                coreResult);
    }

    @Test
    void shouldReturnResponseWithErrors() {
        when(commandMock.agreement()).thenReturn(agreementMock);
        when(agreementValidatorMock.validate(agreementMock))
                .thenReturn(List.of(new ValidationErrorDTO("field", "message")));
        var coreResult = travelCalculatePremiumService.calculatePremium(commandMock);
        assertTrue(coreResult.hasErrors());
    }

    @Test
    void agreementMustBeEmptyWhenResponseContainsError() {
        when(commandMock.agreement()).thenReturn(agreementMock);
        when(agreementValidatorMock.validate(agreementMock))
                .thenReturn(List.of(new ValidationErrorDTO("field", "message")));
        var coreResult = travelCalculatePremiumService.calculatePremium(commandMock);
        assertNull(coreResult.agreement());
    }

    @Test
    void shouldNotBeInteractionWithPersonsCalculatorWhenResponseContainsError() {
        when(commandMock.agreement()).thenReturn(agreementMock);
        when(agreementValidatorMock.validate(agreementMock))
                .thenReturn(List.of(new ValidationErrorDTO("field", "message")));
        travelCalculatePremiumService.calculatePremium(commandMock);
        Mockito.verifyNoInteractions(agreementPremiumCalculatorMock);
    }

}