package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyPersonsValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private EmptyPersonsValidation emptyPersonsValidation;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldSucceedWhenCountryIsNotEmpty() {
        var person = new PersonDTO("", "", "code", null, "");
        when(agreementMock.persons())
                .thenReturn(List.of(person));
        assertEquals(Optional.empty(), emptyPersonsValidation.validate(agreementMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenCountryIsEmpty() {
        when(agreementMock.persons())
                .thenReturn(List.of());
        when(errorFactoryMock.buildError("ERROR_CODE_17"))
                .thenReturn(new ValidationErrorDTO("", ""));
        var errorOpt = emptyPersonsValidation.validate(agreementMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationErrorDTO("", ""), errorOpt.get());
    }

    @Test
    void shouldReturnErrorWhenCountryIsNull() {
        when(agreementMock.persons())
                .thenReturn(null);
        when(errorFactoryMock.buildError("ERROR_CODE_17"))
                .thenReturn(new ValidationErrorDTO("", ""));
        var errorOpt = emptyPersonsValidation.validate(agreementMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationErrorDTO("", ""), errorOpt.get());
    }

}