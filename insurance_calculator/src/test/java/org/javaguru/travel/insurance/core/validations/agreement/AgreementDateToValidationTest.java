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
class AgreementDateToValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private AgreementDateToValidation agreementDateToValidation;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldNotReturnErrorWhenDateToIsValid() {
        when(agreementMock.agreementDateTo()).thenReturn(LocalDate.now());
        var errorOptional = agreementDateToValidation.validate(agreementMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenDateToIsNull() {
        when(agreementMock.agreementDateTo()).thenReturn(null);
        when(validationErrorFactory.buildError("ERROR_CODE_4"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_4", "Description"));
        var errorOptional = agreementDateToValidation.validate(agreementMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_4", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

}