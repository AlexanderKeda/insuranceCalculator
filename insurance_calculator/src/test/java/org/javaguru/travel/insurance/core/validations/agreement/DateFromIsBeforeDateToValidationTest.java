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
class DateFromIsBeforeDateToValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private DateFromIsBeforeDateToValidation dateFromIsBeforeDateToValidation;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldNotReturnErrorWhenDateFromIsBeforeDateTo() {
        when(agreementMock.agreementDateFrom()).thenReturn(LocalDate.now());
        when(agreementMock.agreementDateTo()).thenReturn(LocalDate.now().plusDays(1));

        var errorOptional = dateFromIsBeforeDateToValidation
                .validate(agreementMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenDateFromIsAfterDateTo() {
        when(agreementMock.agreementDateFrom()).thenReturn(LocalDate.now().plusDays(1));
        when(agreementMock.agreementDateTo()).thenReturn(LocalDate.now());
        when(validationErrorFactory.buildError("ERROR_CODE_8"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_8", "Description"));
        var errorOptional = dateFromIsBeforeDateToValidation
                .validate(agreementMock);

        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_8", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

    @Test
    void shouldNotThrowExceptionWhenDateFromIsNull() {
        when(agreementMock.agreementDateFrom()).thenReturn(null);
        assertDoesNotThrow(() -> dateFromIsBeforeDateToValidation.validate(agreementMock));
    }

    @Test
    void shouldNotThrowExceptionWhenDateToIsNull() {
        when(agreementMock.agreementDateTo()).thenReturn(null);
        assertDoesNotThrow(() -> dateFromIsBeforeDateToValidation.validate(agreementMock));
    }

}