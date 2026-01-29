package org.javaguru.travel.insurance.core.validations.person;

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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyPersonCodeValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactoryMock;

    @InjectMocks
    private EmptyPersonCodeValidation emptyPersonCodeValidation;

    @Mock
    private PersonDTO personMock;

    @Mock
    private AgreementDTO agreementMock;

    @Test
    void shouldNotReturnErrorWhenPersonCodeIsValid() {
        when(personMock.personCode()).thenReturn("code");
        var errorOptional = emptyPersonCodeValidation.validate(agreementMock, personMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenPersonCodeIsNull() {
        when(personMock.personCode()).thenReturn(null);
        when(validationErrorFactoryMock.buildError("ERROR_CODE_18"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_18", "Description"));
        var errorOptional = emptyPersonCodeValidation.validate(agreementMock, personMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_18", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

    @Test
    void shouldReturnErrorWhenPersonCodeIsEmpty() {
        when(personMock.personCode()).thenReturn("");
        when(validationErrorFactoryMock.buildError("ERROR_CODE_18"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_18", "Description"));
        var errorOptional = emptyPersonCodeValidation.validate(agreementMock, personMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_18", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

}