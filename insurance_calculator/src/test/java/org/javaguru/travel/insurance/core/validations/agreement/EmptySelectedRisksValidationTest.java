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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptySelectedRisksValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private EmptySelectedRisksValidation emptyRisksValidation;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldNotReturnErrorWhenRisksIsNotEmpty() {
        when(agreementMock.selectedRisks()).thenReturn(List.of("risk1", "risk2"));
        var errorOptional = emptyRisksValidation.validate(agreementMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenRisksIsEmpty() {
        when(agreementMock.selectedRisks()).thenReturn(List.of());
        when(validationErrorFactory.buildError("ERROR_CODE_5"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_5", "Description"));
        var errorOptional = emptyRisksValidation.validate(agreementMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_5", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

    @Test
    void shouldReturnErrorWhenRisksIsNull() {
        when(agreementMock.selectedRisks()).thenReturn(null);
        when(validationErrorFactory.buildError("ERROR_CODE_5"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_5", "Description"));
        var errorOptional = emptyRisksValidation.validate(agreementMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_5", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }
}