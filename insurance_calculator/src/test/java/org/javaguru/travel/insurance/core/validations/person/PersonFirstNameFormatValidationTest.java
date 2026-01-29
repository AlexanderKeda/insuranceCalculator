package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonFirstNameFormatValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactoryMock;

    @InjectMocks
    private PersonFirstNameFormatValidation personFirstNameFormatValidation;

    @Mock
    private AgreementDTO agreementMock;

    @Mock
    private PersonDTO personMock;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"Ivan", "Alex-P", "David O"})
    void shouldSucceedWhenPersonFirstNameIsValidOrNullOrEmpty(String personFirstName) {
        when(personMock.personFirstName()).thenReturn(personFirstName);
        var result = personFirstNameFormatValidation.validate(agreementMock, personMock);
        assertTrue(result.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactoryMock);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ivan-3", "Петя", "David_O"})
    void shouldReturnErrorWhenPersonFirstNameIsInvalid(String personFirstName) {
        var error = new ValidationErrorDTO("code", "description");
        when(personMock.personFirstName()).thenReturn(personFirstName);
        when(validationErrorFactoryMock.buildError("ERROR_CODE_23"))
                .thenReturn(error);
        var result = personFirstNameFormatValidation.validate(agreementMock, personMock);
        assertTrue(result.isPresent());
        assertEquals(error, result.get());
    }

    @Test
    void shouldReturnErrorWhenPersonFirstNameIsTooLong() {
        String personFirstName = "n".repeat(210);
        var error = new ValidationErrorDTO("code", "description");
        when(personMock.personFirstName()).thenReturn(personFirstName);
        when(validationErrorFactoryMock.buildError("ERROR_CODE_23"))
                .thenReturn(error);
        var result = personFirstNameFormatValidation.validate(agreementMock, personMock);
        assertTrue(result.isPresent());
        assertEquals(error, result.get());
    }

}