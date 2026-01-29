package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementDateFromInFutureValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private AgreementDateFromInFutureValidation agreementDateFromInFutureValidation;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldNotReturnErrorWhenDateFromIsValid() {
        when(agreementMock.agreementDateFrom()).thenReturn(LocalDate.now());
        var errorOptional = agreementDateFromInFutureValidation
                .validate(agreementMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenDateFromIsInThePast() {
        when(agreementMock.agreementDateFrom()).thenReturn(LocalDate.now().minusDays(1));
        when(validationErrorFactory.buildError("ERROR_CODE_6"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_6", "Description"));
        var errorOptional = agreementDateFromInFutureValidation
                .validate(agreementMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_6", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

    @Test
    void shouldNotThrowExceptionWhenDateFromIsNull() {
        when(agreementMock.agreementDateFrom()).thenReturn(null);
        assertDoesNotThrow(() -> agreementDateFromInFutureValidation.validate(agreementMock));
    }

}