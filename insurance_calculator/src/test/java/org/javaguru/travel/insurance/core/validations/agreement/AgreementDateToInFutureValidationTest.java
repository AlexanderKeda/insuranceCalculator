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
class AgreementDateToInFutureValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private AgreementDateToInFutureValidation agreementDateToInFutureValidation;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldNotReturnErrorWhenDateToIsValid() {
        when(agreementMock.agreementDateTo()).thenReturn(LocalDate.now());
        var errorOptional = agreementDateToInFutureValidation
                .validate(agreementMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenDateToIsInThePast() {
        when(agreementMock.agreementDateTo()).thenReturn(LocalDate.now().minusDays(1));
        when(validationErrorFactory.buildError("ERROR_CODE_7"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_7", "Description"));
        var errorOptional = agreementDateToInFutureValidation
                .validate(agreementMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_7", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

    @Test
    void shouldNotThrowExceptionWhenDateToIsNull() {
        when(agreementMock.agreementDateTo()).thenReturn(null);
        assertDoesNotThrow(() -> agreementDateToInFutureValidation.validate(agreementMock));
    }

}