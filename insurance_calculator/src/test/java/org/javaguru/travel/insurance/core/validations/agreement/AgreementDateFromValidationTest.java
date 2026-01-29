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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementDateFromValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private AgreementDateFromValidation agreementDateFromValidation;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldNotReturnErrorWhenDateFromIsValid() {
        when(agreementMock.agreementDateFrom()).thenReturn(LocalDate.now());
        var errorOptional = agreementDateFromValidation.validate(agreementMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenDateFromIsNull() {
        when(agreementMock.agreementDateFrom()).thenReturn(null);
        when(validationErrorFactory.buildError("ERROR_CODE_3"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_3", "Description"));
        var errorOptional = agreementDateFromValidation.validate(agreementMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_3", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

}