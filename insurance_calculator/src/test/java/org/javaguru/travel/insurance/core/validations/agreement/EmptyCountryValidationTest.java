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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyCountryValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private EmptyCountryValidation emptyCountryValidation;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldSucceedWhenCountryIsNotEmpty() {
        when(agreementMock.country())
                .thenReturn("LATVIA");
        assertEquals(Optional.empty(), emptyCountryValidation.validate(agreementMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenCountryIsEmpty() {
        when(agreementMock.country())
                .thenReturn("");
        when(errorFactoryMock.buildError("ERROR_CODE_10"))
                .thenReturn(new ValidationErrorDTO("", ""));
        Optional<ValidationErrorDTO> errorOpt = emptyCountryValidation.validate(agreementMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationErrorDTO("", ""), errorOpt.get());
    }

    @Test
    void shouldReturnErrorWhenCountryIsNull() {
        when(agreementMock.country())
                .thenReturn(null);
        when(errorFactoryMock.buildError("ERROR_CODE_10"))
                .thenReturn(new ValidationErrorDTO("", ""));
        Optional<ValidationErrorDTO> errorOpt = emptyCountryValidation.validate(agreementMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationErrorDTO("", ""), errorOpt.get());
    }

}